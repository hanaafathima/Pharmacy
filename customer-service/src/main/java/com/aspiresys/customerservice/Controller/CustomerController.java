package com.aspiresys.customerservice.Controller;

import com.aspiresys.customerservice.Entity.Customer;
import com.aspiresys.customerservice.Entity.Login;
import com.aspiresys.customerservice.Service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy-management/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/new")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        log.info("Registered Successfully");
        return customerService.saveCustomer(customer);
    }

    @GetMapping(path="/login/{email}")
    public Login getCredentials(@PathVariable("email") String email)
    {
        return customerService.getCredentials(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("id") Long customerId){
        log.info("Got Customer details");
        return customerService.findCustomer(customerId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> listAllCustomers() {
        return customerService.listAllCustomers();
    }

    @PostMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomer(@RequestBody Customer customer) {
        return customerService.searchCustomer(customer);
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(name="id") Long customerId , @RequestBody Customer customer){
        log.info("Updated Customer details successfully");
       return customerService.updateCustomer(customerId , customer);
    }

    @DeleteMapping("/remove/{id}")
    public void deleteCustomer(@PathVariable("id") Long customerId) {
        log.info("Deleted Customer details successfully");
        customerService.deleteCustomer(customerId);
    }

}
