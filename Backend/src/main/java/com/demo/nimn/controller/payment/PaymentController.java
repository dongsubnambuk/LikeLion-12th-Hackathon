package com.demo.nimn.controller.payment;

import com.demo.nimn.dto.payment.*;
import com.demo.nimn.service.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("")
    public PaymentDTO createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentService.createImportPayment(paymentRequestDTO);
    }

    @GetMapping("/{paymentId}")
    public PaymentDTO getPaymentByPaymentId(@PathVariable("paymentId") String paymentId){
        return paymentService.getPaymentByPaymentId(paymentId);
    }

    @GetMapping("")
    public List<PaymentDTO> getPaymentByPurchaser(@RequestParam("purchaser") String purchaser){
        return paymentService.getPaymentByPurchaser(purchaser);
    }

    @DeleteMapping("/{paymentId}")
    public Boolean deletePayment(@PathVariable("paymentId") String paymentId){
        return paymentService.deletePayment(paymentId);
    }
}
