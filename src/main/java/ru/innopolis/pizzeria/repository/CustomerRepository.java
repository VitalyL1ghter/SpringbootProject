package ru.innopolis.pizzeria.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.innopolis.pizzeria.model.Customer;
import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {

    @Query(value = "select c from Customer c where c.softDeletedFlag=false")
    List<Customer> findAllButNotSoftDeleted();

}
