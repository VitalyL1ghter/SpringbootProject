package ru.innopolis.pizzeria.dto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@Hidden
public class OrderPizzaAllDto {

    private CustomerDto customer;

    private List<OrderPizzaDto> ordersPizza;
}
