package ru.innopolis.pizzeria.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_pizza")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderPizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "order_pizza_id")
    private long orderPizzaId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "date_order")
    @CreatedDate
    private LocalDateTime dateOrder;

    @Column(name = "order_price")
    private Integer orderPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;


}

