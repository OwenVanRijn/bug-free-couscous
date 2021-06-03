package io.swagger.services;

import io.swagger.dto.CustomerEditUserDTO;
import io.swagger.dto.EmployeeEditUserDTO;
import io.swagger.dto.UsersPageDTO;
import io.swagger.exceptions.RestException;
import io.swagger.exceptions.UnauthorisedException;
import io.swagger.model.User;
import io.swagger.repositories.UserRepository;
import io.swagger.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
    }

    public UsersPageDTO getAllUsers(Integer limit, Integer page) {
        page--;
        Pageable p = PageRequest.of(page, limit);
        Page<User> u;

        u = userRepository.getUsers(p);

        return new UsersPageDTO(u.getTotalElements(), u.getTotalPages(),
                u.get().map(User::toUserDTO).collect(Collectors.toList()));
    }

    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) { return userRepository.findUserByEmail(email); }

    public User getUserByUsername(String username) { return userRepository.findByUsername(username); }

    public void deleteById(Integer id) { userRepository.deleteById(id); }

    public String login(String username, String password) throws UnauthorisedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            return jwtTokenProvider.createToken(username, user.getRole());
        } catch (Exception e) {
            throw new UnauthorisedException("Failed to login");
        }
    }

    public User editUserCustomer(CustomerEditUserDTO editUserDTO, User user) {
        editUserDTO.fillEmpty(user);

        user.setEmail(editUserDTO.getEmail());
        user.setPhoneNumber(editUserDTO.getPhoneNumber());
        user.setAddress(editUserDTO.getAddressObj(user.getAddress()));

        addressService.addAddress(user.getAddress());

        return userRepository.save(user);
    }

    public User editUserEmployee(EmployeeEditUserDTO editUserDTO, User user) {
        editUserDTO.fillEmpty(user);

        user.setFirstName(editUserDTO.getFirstName());
        user.setLastName(editUserDTO.getLastName());
        user.setEmail(editUserDTO.getEmail());
        user.setPhoneNumber(editUserDTO.getPhoneNumber());
        user.setAddress(editUserDTO.getAddressObj(user.getAddress()));

        addressService.addAddress(user.getAddress());

        return userRepository.save(user);
    }
}
