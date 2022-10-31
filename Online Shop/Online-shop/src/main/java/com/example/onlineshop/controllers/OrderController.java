package com.example.onlineshop.controllers;

import com.example.onlineshop.models.*;
import com.example.onlineshop.service.*;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Table;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.spire.doc.fields.Field;
import org.springframework.web.bind.annotation.RequestParam;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.ManyToOne;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class OrderController {

    private CategoryServiceImp categoryServiceImp;
    private ProductServiceImpl productService;
    private ShoppingCartService shoppingCartService;
    private UserDetailsServiceImpl userDetailsService;
    private OrderDetailsService orderDetailsService;

    private AddressService addressService;

    private OrderProductService orderProductService;

    private OrderAddressService orderAddressService;
    private JavaMailSender javaMailSender;

    @GetMapping("/order")
    public String saveOrder(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        User user = userDetailsService.getUserByEmail(myUserDetails.getUsername());
        List<CartItem> cartItems = shoppingCartService.findByUser(user);
        if (cartItems.isEmpty()) {
            double total = 0.0;
            for (CartItem item : cartItems) {
                total += item.getQuantity() * item.getProduct().getPrice();
            }
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("total", total);
            return "redirect:/cart?emptyCart";
        } else {
            List<Address> addresses = addressService.getAllByUserId(user.getId());
            double total=0.0;
            double transport = 0.0;
            for (CartItem item : cartItems) {
                total += item.getQuantity() * item.getProduct().getPrice();
            }
            if (total < 200.0) {
                transport = 15.0;
                model.addAttribute("transport", transport);
            }
            model.addAttribute("addresses", addresses);
            model.addAttribute("user", user);
            model.addAttribute("total", total);
            model.addAttribute("totalProduct",cartItems.size());
            model.addAttribute("products",cartItems.stream().map(cartItem -> cartItem.getProduct()).collect(Collectors.toList()));
            model.addAttribute("transport", transport);


            return "order";
        }
    }
    @GetMapping("/order/success")
    @Transactional
    public String placeOrder(@AuthenticationPrincipal MyUserDetails userDetails, @RequestParam String transport, @RequestParam Long address, @RequestParam String pay) throws MessagingException {
        LocalDate date=LocalDate.now();
        User user=userDetailsService.getUserByEmail(userDetails.getUsername());
        List<CartItem> cartItems=shoppingCartService.findByUser(user);
        double total=0.0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        Address address1=addressService.getAddressById(address).get();
        OrderAddress orderAddress=new OrderAddress();
        orderAddress.setCity(address1.getCity());
        orderAddress.setCountry(address1.getCountry());
        orderAddress.setCounty(address1.getCounty());
        orderAddress.setNumber(address1.getNumber());
        orderAddress.setStreet(address1.getStreet());
        orderAddress.setPostalCode(address1.getPostalCode());
        orderAddress.setUser(user);

        orderAddressService.saveOrderAddress(orderAddress);
        List<OrderProduct> orderProducts=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setQuantity(cartItem.getQuantity());
            orderProduct.setUser(user);
            orderProducts.add(orderProduct);
            orderProductService.saveOrderProduct(orderProduct);
        }
        OrderDetails orderDetails=new OrderDetails();
        orderDetails.setOrderAddress(orderAddress);
        orderDetails.setOrderProducts(orderProducts);
        orderDetails.setUser(user);
        orderDetails.setPayType(pay);
        orderDetails.setTransportType(transport);
        orderDetails.setTime(date);
        orderDetails.setTotal(total);
        orderDetailsService.saveOrder(orderDetails);

        Document doc = new Document();

        //load the template file
        doc.loadFromFile("/Users/cosminmihu/Online-Shop/Online-shop/src/main/resources/static/Invoice-Template.docx");

        //replace text in the document
        doc.replace("#InvoiceNum", String.valueOf((int) (Math.random() * 3000)), true, true);
        doc.replace("#CompanyName", "-", true, true);
        doc.replace("#CompanyAddress", "-", true, true);
        doc.replace("#Country", "Romania", true, true);
        doc.replace("#Tel1", user.getPhoneNumber(), true, true);
        doc.replace("#ContactPerson", user.getFirstName()+' '+user.getLastName(), true, true);
        doc.replace("#ShippingAddress", address1.getStreet()+' '+address1.getCity()+' '+address1.getPostalCode(), true, true);
        doc.replace("#Tel2", user.getPhoneNumber(), true, true);

        //define purchase data
        String[][] purchaseData1=new String[cartItems.size()+1][3];
        int i=0;
        for(CartItem cartItem : cartItems){
            purchaseData1[i][0]=cartItem.getProduct().getName();
            purchaseData1[i][1]= String.valueOf(cartItem.getQuantity());
            purchaseData1[i][2]= String.valueOf(cartItem.getProduct().getPrice());
            i++;
        }
        if(total>200) {
            purchaseData1[cartItems.size()][0] = "Transport";
            purchaseData1[cartItems.size()][1] = "0";
            purchaseData1[cartItems.size()][2] = "0";
        }else {
            purchaseData1[cartItems.size()][0] = "Transport";
            purchaseData1[cartItems.size()][1] = "1";
            purchaseData1[cartItems.size()][2] = "15.0";
        }






        //write the purchase data to the document
        writeDataToDocument(doc, purchaseData1);

        //update fields
        doc.isUpdateFields(true);

        //save file in pdf format
        doc.saveToFile("/Users/cosminmihu/Online-Shop/Online-shop/src/main/resources/static/Invoice.pdf", FileFormat.PDF);

        shoppingCartService.deleteByUserId(user.getId());

        MimeMessagePreparator preparator = mimeMessage -> {

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setTo(user.getEmail());
            message.setFrom("cosminmihu03@gmail.com");
            message.setSubject("Factura comanda");
            message.setText("Factura");

            FileSystemResource file = new FileSystemResource(new File("/Users/cosminmihu/Online-Shop/Online-shop/src/main/resources/static/Invoice.pdf"));
            message.addAttachment("Invoice.pdf", file);
        };

        try {
            javaMailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }


        return "redirect:/?success";
    }
    private static void addRows(Table table, int rowNum) {
        for (int i = 0; i < rowNum; i++) {
            //insert specific number of rows by cloning the second row
            table.getRows().insert(2 + i, table.getRows().get(1).deepClone());
            //update formulas for Total
            for (Object object : table.getRows().get(2 + i).getCells().get(3).getParagraphs().get(0).getChildObjects()
            ) {
                if (object instanceof Field) {
                    Field field = (Field) object;
                    field.setCode(String.format("=B%d*C%d\\# \"0.00\"", 3 + i,3 + i));
                }
                break;
            }
        }
        //update formula for Total Tax
        //update formula for Balance Due
        for (Object object : table.getRows().get(5 + rowNum).getCells().get(3).getParagraphs().get(0).getChildObjects()
        ) {
            if (object instanceof Field) {
                Field field = (Field) object;
                field.setCode(String.format("=D%d+D%d\\# \"$#,##0.00\"", 3 + rowNum, 5 + rowNum));
            }
            break;
        }
    }
    private static void fillTableWithData(Table table, String[][] data) {
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                //fill data in cells
                table.getRows().get(r + 1).getCells().get(c).getParagraphs().get(0).setText(data[r][c]);
            }
        }
    }
    private static void writeDataToDocument(Document doc, String[][] purchaseData) {
        //get the third table
        Table table = doc.getSections().get(0).getTables().get(2);
        //determine if it needs to add rows
        if (purchaseData.length > 1) {
            //add rows
            addRows(table, purchaseData.length - 1);
        }
        //fill the table cells with value
        fillTableWithData(table, purchaseData);
    }

}
