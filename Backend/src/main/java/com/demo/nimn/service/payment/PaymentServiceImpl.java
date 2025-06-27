package com.demo.nimn.service.payment;

import com.demo.nimn.dao.payment.PaymentDAO;
import com.demo.nimn.dto.diet.Response.WeeklyDietDTO;
import com.demo.nimn.dto.payment.*;
import com.demo.nimn.entity.order.Order;
import com.demo.nimn.entity.payment.*;
import com.demo.nimn.service.auth.UserService;
import com.demo.nimn.service.diet.DietService;
import com.demo.nimn.service.order.OrderService;
import com.demo.nimn.service.review.ReviewService;
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
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO;
    private final UserService userService;
    private final OrderService orderService;
    private final DietService dietService;
    private final ReviewService reviewService;
    private final IamportClient iamportClient;
    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private static final String CHARACTERS = "0123456789";
    private static final int ID_LENGTH = 15;
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    public PaymentServiceImpl(PaymentDAO paymentDAO,
                              UserService userService,
                              OrderService orderService,
                              DietService dietService,
                              ReviewService reviewService,
                              IamportClient iamportClient) {
        this.paymentDAO = paymentDAO;
        this.userService = userService;
        this.orderService = orderService;
        this.dietService = dietService;
        this.reviewService = reviewService;
        this.iamportClient = iamportClient;
    }

    @Override
    public PaymentResponseDTO createImportPayment(PaymentRequestDTO request) {
        try {
            // 결제 단건 조회(아임포트)
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            // 주문내역 조회
            Order order = orderService.getOrder(request.getOrderId());

            // 결제 완료가 아니면
            if (!iamportResponse.getResponse().getStatus().equals("paid")) {
                logger.info("결제 미완료: {}", request.getPaymentUid());
                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            Long price = order.getTotalPrice();
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if (iamportPrice != price) {
                // 주문 취소
                orderService.cancelOrder(order.getOrderId());
                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));
                logger.info("결제금액 위변조 의심: {}", request.getPaymentUid());
                throw new RuntimeException("결제금액 위변조 의심");
            }
            // 결제 후 OrderStatus paid로 변경
            orderService.payOrder(order.getOrderId());

            createWeeklyDietReviews(order);

            return createPayment(order, request.getPaymentUid());
        } catch (IamportResponseException e) {
            logger.error("아임포트 응답 처리 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("입출력 예외 발생: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void createWeeklyDietReviews(Order order) {
        try {
            WeeklyDietDTO weeklyDietDTO = dietService.getWeeklyDietById(order.getWeeklyDietId());

            reviewService.createWeeklyDietReviews(weeklyDietDTO);
        } catch (Exception e) {
            logger.error("Failed to create weekly diet reviews for order: {}", order.getOrderId(), e);
        }
    }

    @Override
    public PaymentResponseDTO createPayment(Order order, String paymentUid) {
        if(paymentDAO.existsByPaymentUid(paymentUid)){
            return new PaymentResponseDTO("이미 결제가 완료되었습니다.", null);
        }

        Payment payment = toPayment(order, paymentUid);
        paymentDAO.createPayment(payment);

        return toPaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO readPaymentByPaymentId(String paymentId) {
        Payment payment = paymentDAO.readPaymentById(paymentId);
        return toPaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTOS readPaymentByPurchaser(String purchaser) {
        List<Payment> paymentEntities = paymentDAO.readPaymentByPurchaser(purchaser);
        return toPaymentResponseDTOS(paymentEntities);
    }

    @Override
    public UserDTO readNonPurchasersThisWeek(){
        // TODO-jh: 로직 수정 필요, 현재는 전체 유저에서 이번 주 구매한 유저를 제외한 모든 유저를 반환하고 있으나 이번 주에 식단을 주문한 유저 중에 결제를 안 한 유저를 반환해야 함.
        List<String> purchasersThisWeek = paymentDAO.findPurchasersThisWeek();
        List<String> allUsers = userService.getAllUsersEmail().getEmail();
        List<String> nonPurchasers = allUsers.stream()
                .filter(user -> !purchasersThisWeek.contains(user))
                .toList();
        return new UserDTO(nonPurchasers);
    }

    @Override
    public PaymentResponseDTO deletePayment(String paymentId) {
        paymentDAO.deletePaymentById(paymentId);
        return new PaymentResponseDTO("success", null);
    }

    private String generateUniquePaymentId() {
        String paymentId;
        do {
            paymentId = generateRandomId();
        } while (paymentDAO.existsByPaymentUid(paymentId)); // Check if UID already exists
        return paymentId;
    }

    // Generate a random 15-digit number
    private String generateRandomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public Payment toPayment(Order order, String paymentUid) {
        return Payment.builder()
                .paymentId(generateUniquePaymentId())
                .purchaser(order.getPurchaser())
                .weeklyId(order.getWeeklyDietId())
                .paymentUid(paymentUid)
                .totalPrice(order.getTotalPrice())
                .dateTime(LocalDateTime.now())
                .build();
    }

    public PaymentDTO toPaymentDTO(Payment payment){
        return PaymentDTO.builder()
                .paymentId(payment.getPaymentId())
                .purchaser(payment.getPurchaser())
                .weeklyId(payment.getWeeklyId())
                .paymentUid(payment.getPaymentUid())
                .totalPrice(payment.getTotalPrice())
                .dateTime(payment.getDateTime())
                .build();
    }

    public List<PaymentDTO> toPaymentDTOS(List<Payment> paymentEntities){
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment : paymentEntities) {
            paymentDTOS.add(toPaymentDTO(payment));
        }
        return paymentDTOS;
    }

    public PaymentResponseDTO toPaymentResponseDTO(Payment payment){
        return PaymentResponseDTO.builder()
                .result("success")
                .data(toPaymentDTO(payment))
                .build();
    }

    public PaymentResponseDTOS toPaymentResponseDTOS(List<Payment> paymentEntities){
        return PaymentResponseDTOS.builder()
                .result("success")
                .data(toPaymentDTOS(paymentEntities))
                .build();
    }
}
