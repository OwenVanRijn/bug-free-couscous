package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.dto.AddressPutDTO;
import io.swagger.dto.CreateUserDTO;
import io.swagger.dto.CustomerEditUserDTO;
import io.swagger.dto.EmployeeEditUserDTO;
import io.swagger.model.Address;
import io.swagger.model.User;
import io.swagger.services.AddressService;
import io.swagger.services.MapService;
import io.swagger.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MapService mapService;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<User> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "The CreateUser object only has the fields required to create a User.", required = true, schema = @Schema()) @Valid @RequestBody CreateUserDTO newUser) {
        if (userService.getUserByEmail(newUser.getEmail()).isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); //todo check how to return message or should i change identifier?
        } else {
            User user = mapService.createUser(newUser);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteUser(@Parameter(in = ParameterIn.PATH, description = "The user id", required = true, schema = @Schema()) @PathVariable("id") Integer id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<User> editUser(@Parameter(in = ParameterIn.PATH, description = "The user id", required = true, schema = @Schema()) @PathVariable("id") Integer id, @Parameter(in = ParameterIn.DEFAULT, description = "The Employee can edit all User information.", required = true, schema = @Schema()) @Valid @RequestBody EmployeeEditUserDTO editUser) {
        Optional<User> userData = userService.getUserById(id);

        if(userData.isPresent()) {
            User user = userData.get();
            return new ResponseEntity<>(userService.editUserEmployee(editUser, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/users")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<User> editUserCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "The Employee can edit all User information.", required = true, schema = @Schema()) @Valid @RequestBody CustomerEditUserDTO editUser) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());

        return new ResponseEntity<>(userService.editUserCustomer(editUser, user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<User> getUser(@Parameter(in = ParameterIn.PATH, description = "The user id", required = true, schema = @Schema()) @PathVariable("id") Integer id) {
        Optional<User> userData = userService.getUserById(id);
        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return", schema = @Schema(allowableValues = {}, maximum = "50"
    )) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<User> users = new ArrayList<>();

        if (auth.getAuthorities().toString().equals("[ROLE_CUSTOMER]")) {
            User user = userService.getUserByUsername(auth.getName());
            users.add(user);
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            users = userService.getAllUsers();
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }
    }
}
