package com.example.paymentserver.Service;

import com.example.paymentserver.DAO.PaymentDAO;
import com.example.paymentserver.DTO.PaymentDTO;
import com.example.paymentserver.DTO.PaymentRequestDTO;
import com.example.paymentserver.DTO.PaymentResponseDTO;
import com.example.paymentserver.DTO.PaymentResponseDTOS;
import com.example.paymentserver.Entity.OrderEntity;
import com.example.paymentserver.Entity.PaymentEntity;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.IamportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO;
    private final OrderService orderService;
    private final IamportClient iamportClient;
    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    public PaymentServiceImpl(PaymentDAO paymentDAO,
                              OrderService orderService,
                              IamportClient iamportClient) {
        this.paymentDAO = paymentDAO;
        this.orderService = orderService;
        this.iamportClient = iamportClient;
    }

    @Override
    public PaymentResponseDTO createImportPayment(PaymentRequestDTO request) {
        try {
            // 결제 단건 조회(아임포트)
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            // 주문내역 조회
            OrderEntity orderEntity = orderService.findByOrderId(request.getOrderUid());

            // 결제 완료가 아니면
            if (!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 삭제
                orderService.deleteOrder(orderEntity);
                logger.info("결제 미완료: {}", request.getPaymentUid());
                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            Long price = orderEntity.getTotalPrice();
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if (iamportPrice != price) {
                // 주문 삭제
                orderService.deleteOrder(orderEntity);
                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));
                logger.info("결제금액 위변조 의심: {}", request.getPaymentUid());
                throw new RuntimeException("결제금액 위변조 의심");
            }

            return createPayment(orderEntity, request.getPaymentUid());
        } catch (IamportResponseException e) {
            logger.error("아임포트 응답 처리 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("입출력 예외 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaymentResponseDTO createPayment(OrderEntity orderEntity, String paymentUid) {
        if(paymentDAO.existsByPaymentUid(paymentUid)){
            return new PaymentResponseDTO("이미 결제가 완료되었습니다.", null);
        }

        PaymentEntity paymentEntity = toPayment(orderEntity, paymentUid);
        paymentDAO.createPayment(paymentEntity);

        return toPaymentResponseDTO(paymentEntity);
    }

    @Override
    public PaymentResponseDTO readPaymentByPaymentId(String paymentId) {
        PaymentEntity paymentEntity = paymentDAO.readPaymentById(paymentId);
        return toPaymentResponseDTO(paymentEntity);
    }

    @Override
    public PaymentResponseDTOS readPaymentByPurchaser(String purchaser) {
        List<PaymentEntity> paymentEntities = paymentDAO.readPaymentByPurchaser(purchaser);
        return toPaymentResponseDTOS(paymentEntities);
    }

    @Override
    public PaymentResponseDTO deletePayment(String paymentId) {
        paymentDAO.deletePaymentById(paymentId);
        return new PaymentResponseDTO("success", null);
    }

    public PaymentEntity toPayment(OrderEntity orderEntity, String paymentUid) {
        return PaymentEntity.builder()
                .purchaser(orderEntity.getPurchaser())
                .weeklyId(orderEntity.getWeeklyId())
                .paymentUid(paymentUid)
                .totalPrice(orderEntity.getTotalPrice())
                .dateTime(LocalDateTime.now())
                .build();
    }

    public PaymentDTO toPaymentDTO(PaymentEntity paymentEntity){
        return PaymentDTO.builder()
                .paymentId(paymentEntity.getPaymentId())
                .purchaser(paymentEntity.getPurchaser())
                .weeklyId(paymentEntity.getWeeklyId())
                .paymentUid(paymentEntity.getPaymentUid())
                .totalPrice(paymentEntity.getTotalPrice())
                .dateTime(paymentEntity.getDateTime())
                .build();
    }

    public List<PaymentDTO> toPaymentDTOS(List<PaymentEntity> paymentEntities){
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (PaymentEntity paymentEntity : paymentEntities) {
            paymentDTOS.add(toPaymentDTO(paymentEntity));
        }
        return paymentDTOS;
    }

    public PaymentResponseDTO toPaymentResponseDTO(PaymentEntity paymentEntity){
        return PaymentResponseDTO.builder()
                .result("success")
                .data(toPaymentDTO(paymentEntity))
                .build();
    }

    public PaymentResponseDTOS toPaymentResponseDTOS(List<PaymentEntity> paymentEntities){
        return PaymentResponseDTOS.builder()
                .result("success")
                .data(toPaymentDTOS(paymentEntities))
                .build();
    }
}
