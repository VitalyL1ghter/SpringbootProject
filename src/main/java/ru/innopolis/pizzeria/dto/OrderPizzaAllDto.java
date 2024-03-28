package ru.innopolis.pizzeria.dto;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OrderPizzaAllDto {

    private CustomerDto customer;

    private List<OrderPizzaDto> ordersPizza;
}
