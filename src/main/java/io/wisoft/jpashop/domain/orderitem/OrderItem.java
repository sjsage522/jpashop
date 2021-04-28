package io.wisoft.jpashop.domain.orderitem;

import io.wisoft.jpashop.domain.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(columnDefinition = "varchar(50)",
            name = "name")
    private String name;

    @Column(name = "unit_price")
    private int unitPrice;

    @Column(name = "unit_count")
    private int unitCount;

    protected OrderItem() {}

    private OrderItem(String name, int unitPrice, int unitCount) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.unitCount = unitCount;
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    public static OrderItem from(String name, int unitPrice, int unitCount) {
        return new OrderItem(name, unitPrice, unitCount);
    }
}
