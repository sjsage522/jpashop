package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.order.Cancel;
import io.wisoft.jpashop.domain.order.Order;
import io.wisoft.jpashop.domain.order.OrderState;
import io.wisoft.jpashop.domain.orderitem.OrderItem;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.user.User;
import io.wisoft.jpashop.repository.OrderItemRepository;
import io.wisoft.jpashop.repository.OrderRepository;
import io.wisoft.jpashop.repository.StoreRepository;
import io.wisoft.jpashop.repository.UserRepository;
import io.wisoft.jpashop.util.StoreUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public List<Order> findAll(Long userId, LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAll(userId, from, to);
    }

    @Transactional
    public Order order(Long userId, Long storeId, LocalDateTime time, List<OrderItem> orderItems) {

        Store findStore = storeRepository.findById(storeId).orElseThrow();
        if (!StoreUtils.isNormalBusinessHours(time, findStore.getBusinessHours()))
            throw new IllegalStateException("영업중 상점이 아님");

        User findUser = userRepository.findById(userId).orElseThrow();

        Order newOrder = orderRepository.save(Order.from(findStore, findUser, orderItems, OrderState.NEW));

        orderItemRepository.saveAll(orderItems);

        return newOrder;
    }

    @Transactional
    public Order cancel(Long orderId, Long userId, String message) {

        String errorMessage = "Could not changed to cancel state";

        Order findOrder = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new IllegalStateException(errorMessage));

        if (!findOrder.getOrderState().equals(OrderState.NEW)) throw new IllegalStateException(errorMessage);

        cancelOrder(findOrder, message);

        return findOrder;
    }

    private void cancelOrder(Order order, String message) {
        order.changeOrderState(OrderState.CANCEL);

        Cancel cancel = new Cancel(message, LocalDateTime.now());
        order.setCancel(cancel);
    }
}
