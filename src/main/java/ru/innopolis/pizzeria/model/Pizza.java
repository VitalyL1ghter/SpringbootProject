package ru.innopolis.pizzeria.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "pizza")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Pizza  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pizza_id")
    private Long pizzaId;

    @Column(name = "pizza_name")
    private String pizzaName;

    @Column
    private String ingredients;

    @Column(name = "size")
    private String size;

    @Column(name = "cost_pizza")
    private Integer costPizza;


    @OneToMany(mappedBy = "pizza")
    private List<OrderPizza> orderPizza;

}
