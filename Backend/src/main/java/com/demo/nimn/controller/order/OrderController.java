package com.demo.nimn.controller.order;

import com.demo.nimn.dto.order.OrderDTO;
import com.demo.nimn.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ORDER API", description = "주문 관리 API")
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다. 주문 생성 후 결제를 진행할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "주문 생성 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 데이터",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @Parameter(
                    description = "주문 생성 요청 데이터",
                    required = true,
                    schema = @Schema(implementation = OrderDTO.class)
            )
            @RequestBody OrderDTO orderDTO
    ) {
        try {
            OrderDTO created = orderService.createOrder(orderDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            logger.error("Order creation failed", e);
            return ResponseEntity.badRequest().build();
        }
    }
}