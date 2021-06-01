package io.swagger.api;

import io.swagger.dto.TransactionPutDTO;
import io.swagger.dto.TransactionsPageDTO;
import io.swagger.dto.TransactionPostDTO;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.RestException;
import io.swagger.exceptions.UnauthorisedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.services.TransactionService;
import io.swagger.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")
@RestController
public class TransactionApiController implements TransactionApi {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(TransactionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<Void> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "argument fields", required=true, schema=@Schema()) @Valid @RequestBody TransactionPostDTO body) throws RestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User performingUser = userService.getUserByUsername(auth.getName());

        transactionService.createTransaction(body, performingUser);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteTransaction(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") Long id) throws RestException {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> editTransaction(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") Long id,@Parameter(in = ParameterIn.DEFAULT, description = "editable fields", schema=@Schema()) @Valid @RequestBody TransactionPutDTO body) throws RestException {
        transactionService.editTransaction(body, id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<TransactionsPageDTO> getTransaction(@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "id", required = false) List<Long> id, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "IBAN", required = false) List<String> IBAN, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema( defaultValue="50")) @Valid @RequestParam(value = "limit", required = false, defaultValue="50") Integer limit, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema( defaultValue="1")) @Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User performingUser = null;

        if (auth.getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            performingUser = userService.getUserByUsername(auth.getName());
        }

        return new ResponseEntity<TransactionsPageDTO>(transactionService.getTransactions(id, IBAN, limit, page, performingUser), HttpStatus.OK);
    }
}
