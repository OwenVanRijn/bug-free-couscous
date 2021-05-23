package io.swagger.services;

import io.swagger.model.Address;
import io.swagger.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressService() {
    }

    public void addAddress(Address address) {
        addressRepository.save(address);
    }
}
