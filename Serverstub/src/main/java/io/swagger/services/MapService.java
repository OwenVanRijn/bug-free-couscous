package io.swagger.services;

import io.swagger.dto.CreateUserDTO;
import io.swagger.model.Address;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.repositories.AddressRepository;
import io.swagger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

@Service
public class MapService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    public User createUser(CreateUserDTO createUserDTO) {
        if (userRepository.findUserByEmail(createUserDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email already in use");
        } else {
            User newUser = convertToUser(createUserDTO);
            addressRepository.save(newUser.getAddress());

            return userRepository.save(newUser);
        }
    }
    //todo check if any field is empty, if so throw exception
    private User convertToUser(CreateUserDTO createUserDTO) {
        User user = new User();
        Address address = new Address();
        user.firstName(createUserDTO.getFirstName()).lastName(createUserDTO.getLastName()).email(createUserDTO.getEmail())
                .phoneNumber(createUserDTO.getPhoneNumber())
                .username(createUserDTO.getUsername()).password(createUserDTO.getPassword());
        address.street(createUserDTO.getStreet()).houseNumber(createUserDTO.getHouseNumber()).postalcode(createUserDTO.getPostalcode())
                .city(createUserDTO.getCity()).country(createUserDTO.getCountry());
        user.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        user.setAddress(address);

        return user;
    }
}
