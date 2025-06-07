package com.demo.nimn.controller.payment;

import com.demo.nimn.dto.payment.*;
import com.demo.nimn.service.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final  Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/newPayment")
    public PaymentResponseDTO createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentService.createImportPayment(paymentRequestDTO);
    }

    @GetMapping("/paymentId/{paymentId}")
    public PaymentResponseDTO readPaymentByPaymentId(@PathVariable("paymentId") String paymentId){
        return paymentService.readPaymentByPaymentId(paymentId);
    }

    @GetMapping("/purchaser/{purchaser}")
    public PaymentResponseDTOS readPaymentByPurchaser(@PathVariable String purchaser){
        return paymentService.readPaymentByPurchaser(purchaser);
    }

    @GetMapping("/unpaid-users")
    public UserDTO readUnpaidUsers(){
        return paymentService.readNonPurchasersThisWeek();
    }

    @DeleteMapping("/delete/{paymentId}")
    public PaymentResponseDTO deletePayment(@PathVariable String paymentId){
        return paymentService.deletePayment(paymentId);
    }
}
