package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;

public interface CustomerService {
    Customer save(CustomerDto customerDto);

    Customer update(CustomerDto customerDto);

    Customer findByUserName(String username);

    Customer changePass(CustomerDto customerDto);

}
