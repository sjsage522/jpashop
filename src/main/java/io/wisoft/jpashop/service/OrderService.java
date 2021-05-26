package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.order.Order;
import io.wisoft.jpashop.repository.OrderRepository;
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

    public List<Order> findAll(Long userId, LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAll(userId, from, to);
    }
}
