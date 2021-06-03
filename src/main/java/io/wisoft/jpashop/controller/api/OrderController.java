package io.wisoft.jpashop.controller.api;

import io.wisoft.jpashop.config.CustomIncludeCollectionValidator;
import io.wisoft.jpashop.domain.order.Cancel;
import io.wisoft.jpashop.domain.order.Order;
import io.wisoft.jpashop.domain.order.OrderState;
import io.wisoft.jpashop.domain.orderitem.OrderItem;
import io.wisoft.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.wisoft.jpashop.controller.api.ApiResult.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final CustomIncludeCollectionValidator customIncludeCollectionValidator;

    /**
     * 주문목록 조회 API
     *
     * @param userId 주문한 사용자 id
     * @param from   주문 목록 조회 범위
     * @param to     주문 목록 조회 범위
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

        return succeed(orderResponses);
    }

    /**
     * 주문하기 API
     * @param userId 주문자
     * @param storeId 주문대상 상점
     * @param time 주문시각
     * @param requests 주문상품들
     * @param bindingResult 컬렉션 예외
     * @return 생성된 주문정보
     * @throws BindException
     */
    @PostMapping("/user/{userId}/store/{storeId}/order")
    public ApiResult<OrderResponse> order(
            @PathVariable Long userId,
            @PathVariable Long storeId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMddHHmmss") LocalDateTime time,
            @RequestBody List<OrderItemRequest> requests,
            BindingResult bindingResult
    ) throws BindException {

        if (time == null) time = LocalDateTime.now();
        if (requests.size() == 0) throw new IllegalStateException("order item must be minimum 1");

        customIncludeCollectionValidator.validate(requests, bindingResult);
        if (bindingResult.hasErrors()) throw new BindException(bindingResult);

        List<OrderItem> orderItems = requests
                .stream()
                .map(request -> OrderItem.from(request.getName(), request.getUnitPrice(), request.getUnitCount()))
                .collect(Collectors.toList());

        return succeed(new OrderResponse(orderService.order(userId, storeId, time, orderItems)));
    }

    @PatchMapping("/user/{userId}/order/{orderId}/cancel")
    public ApiResult<OrderResponse> cancel(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @RequestBody @Valid OrderCancelRequest request
    ) {

        String message = request.getMessage();
        Order cancelOrder = orderService.cancel(orderId, userId, message);

        return succeed(new OrderResponse(cancelOrder));
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

    @Getter
    private static class OrderItemRequest {

        @NotBlank(message = "name must be exist")
        @Size(min = 1, max = 50, message = "valid name size is 1 between 50")
        private String name;

        private int unitPrice;

        @Min(value = 1, message = "count must be greater than 0")
        private int unitCount;
    }

    @Getter
    private static class OrderCancelRequest {

        @NotBlank(message = "message must be not blank")
        private String message;
    }
}
