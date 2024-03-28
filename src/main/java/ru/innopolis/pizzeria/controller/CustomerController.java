package ru.innopolis.pizzeria.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.pizzeria.dto.CustomerDto;
import ru.innopolis.pizzeria.service.CustomerService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("")
    public ResponseEntity<List<CustomerDto>> getCustomerFindAll() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.getAll());
    }

    @GetMapping("/findById/{customer-id}")
    public ResponseEntity<CustomerDto> getCustomersById(@PathVariable("customer-id") Long Id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.getCustomerById(Id));
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerDto> addNewCustomer(@RequestBody CustomerDto customer) {
        return ResponseEntity
                .status(HttpStatus.CREATED)

                .body(customerService.addCustomer(customer));
    }

    @PutMapping("/update/{customer-id}")
    public ResponseEntity<CustomerDto> updateCustomerId(@RequestBody CustomerDto customer,
                                                        @PathVariable("customer-id") Long id) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/delete/{customer-id}")
    public ResponseEntity<List<CustomerDto>> hardDeleteById(@PathVariable("customer-id") Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)

                .body(customerService.getAll());

    }

    @PutMapping("/softDelete/{customer-id}")
    public ResponseEntity<?> softDeleteById(@PathVariable("customer-id") Long id) {
        customerService.softDeleteCustomerById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerService.getAll());

    }
}
