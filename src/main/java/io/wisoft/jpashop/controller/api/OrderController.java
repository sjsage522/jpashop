package io.wisoft.jpashop.controller.api;

import io.wisoft.jpashop.domain.order.Cancel;
import io.wisoft.jpashop.domain.order.Order;
import io.wisoft.jpashop.domain.order.OrderState;
import io.wisoft.jpashop.domain.orderitem.OrderItem;
import io.wisoft.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문목록 조회 API
     * @param userId 주문한 사용자 id
     * @param from 주문 목록 조회 범위
     * @param to 주문 목록 조회 범위
     * @return 주문 목록
     */
    @GetMapping("/user/{userId}/orders/between/{from}/and/{to}")
    public ApiResult<List<OrderResponse>> findOrders(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime from,
            @PathVariable @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime to) {

        List<OrderResponse> orderResponses = orderService.findAll(userId, from, to)
                .stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());

        return ApiResult.succeed(orderResponses);
    }

    @Getter
    private static class OrderResponse {

        private Long id;
        private Long storeId;
        private List<OrderItemResponse> items = new ArrayList<>();
        private int totalPrice;
        private OrderState state;
        private Cancel cancel;
        private LocalDateTime completedAt;
        private LocalDateTime createdAt;

        public OrderResponse(Order order) {
            this.id = order.getId();
            this.storeId = order.getStore().getId();
            for (OrderItem orderItem : order.getOrderItems()) {
                items.add(new OrderItemResponse(orderItem));
            }
            this.totalPrice = order.getTotalPrice(order.getOrderItems());
            this.state = order.getOrderState();
            this.cancel = order.getCancel();
            this.completedAt = order.getCompletedAt();
            this.createdAt = order.getCreatedAt();
        }
    }

    @Getter
    private static class OrderItemResponse {

        private Long id;
        private String name;
        private int unitPrice;
        private int unitCount;

        public OrderItemResponse(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.name = orderItem.getName();
            this.unitPrice = orderItem.getUnitPrice();
            this.unitCount = orderItem.getUnitCount();
        }
    }
}
