package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.orderitem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
