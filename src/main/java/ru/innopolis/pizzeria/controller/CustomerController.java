package ru.innopolis.pizzeria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.pizzeria.dto.CustomerDto;
import ru.innopolis.pizzeria.service.CustomerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
@Tag(name = "Контроллер покупателей", description = "Контроллер CRUD работы с покупателями на сайте")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("")
    @Operation(summary = "Получение списка всех покупателей")
    public ResponseEntity<List<CustomerDto>> getCustomerFindAll() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.getAll());
    }

    @GetMapping("/findById/{customer-id}")
    @Operation(summary = "Получение покупателя по ID", description = "Задать ID для получения данных покупателя")
    public ResponseEntity<CustomerDto> getCustomersById(@PathVariable("customer-id") @Parameter(description = "Идентификатор покупателя") Long Id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.getCustomerById(Id));
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление покупателя", description = "Заполнить данные покупателя")
    public ResponseEntity<CustomerDto> addNewCustomer(@Valid @RequestBody CustomerDto customer) {
        return ResponseEntity
                .status(HttpStatus.CREATED)

                .body(customerService.addCustomer(customer));
    }


    @PutMapping("/update/{customer-id}")
    @Operation(summary = "Изменнение данных покупателя", description = "Введите изменяемые данные")
    public ResponseEntity<CustomerDto> updateCustomerId(@Valid @RequestBody CustomerDto customer,
                                                        @PathVariable("customer-id") @Parameter(description = "Идентификатор покупателя") Long id) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/delete/{customer-id}")
    @Operation(summary = "Удаление покупателя", description = "Задать ID покупателя")
    public ResponseEntity<List<CustomerDto>> hardDeleteById(@PathVariable("customer-id")  @Parameter(description = "Идентификатор покупателя") Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)

                .body(customerService.getAll());

    }

    @PutMapping("/softDelete/{customer-id}")
    public ResponseEntity<?> softDeleteById(@PathVariable("customer-id") @Parameter(description = "Идентификатор покупателя") Long id) {
        customerService.softDeleteCustomerById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.getAll());

    }
}
