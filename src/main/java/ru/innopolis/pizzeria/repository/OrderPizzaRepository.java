package ru.innopolis.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.pizzeria.model.Customer;
import ru.innopolis.pizzeria.model.OrderPizza;

import java.util.List;

@Repository
public interface OrderPizzaRepository extends JpaRepository<OrderPizza, Long> {

    public List<OrderPizza> findAllByCustomer(Customer customer);
}
