package ru.innopolis.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.pizzeria.model.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}
