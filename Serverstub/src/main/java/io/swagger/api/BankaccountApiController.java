package io.swagger.api;

import io.swagger.dto.BankaccountDTO;
import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.dto.TransactionDTO;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.RestException;
import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.services.BankaccountService;
import io.swagger.services.TransactionService;
import io.swagger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")
@RestController
public class BankaccountApiController implements BankaccountApi {

    private static final Logger log = LoggerFactory.getLogger(BankaccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private BankaccountService bankaccountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public BankaccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    // Employee PUT /api/Bankaccount
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<TransactionDTO> completeMoneyFlow(@Parameter(in = ParameterIn.DEFAULT, description = "Complete a deposit or withdraw as an employee", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdraw body) throws RestException {
        TransactionDTO transactionDTO = bankaccountService.DepositOrWithdraw(body);
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }

    // Employee POST /api/Bankaccount
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<BankaccountDTO> createBankaccount(@Parameter(in = ParameterIn.DEFAULT,
            description = "Create a bankaccount, this option is employee only", required=true, schema=@Schema()) @Valid @RequestBody CreateBankaccountDTO newBankaccount) {
        try{
        BankAccount bankAccount = bankaccountService.createBankaccount(newBankaccount);
        BankaccountDTO BDTO = new BankaccountDTO(bankAccount);
        return new ResponseEntity<>(BDTO, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Employee DELETE /api/Bankaccount/{IBAN}
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteBankaccount(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to delete", required=true,
            schema=@Schema()) @PathVariable("IBAN") String IBAN) throws RestException {
            bankaccountService.deleteByIBAN(IBAN);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    // Employee & Customer PUT /api/Bankaccount/{IBAN}
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE')")
    public ResponseEntity<BankaccountDTO> editBankaccount(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to edit", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "editable fields",
            schema=@Schema()) @Valid @RequestBody CreateBankaccountDTO editBankaccount) throws RestException{
            if (!bankaccountService.getBankaccountByIBANSafe(IBAN).isPresent()){ throw new BadRequestException("Invalid Iban!"); }
            BankaccountDTO BDTO = bankaccountService.editAccount(editBankaccount, IBAN);
            return new ResponseEntity<>(BDTO, HttpStatus.OK);
    }

    // Customer GET /api/Bankaccount
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<BankaccountDTO>> getBankaccountCustomer() throws RestException{
            List<BankaccountDTO> BDTOS = bankaccountService.getBankaccountsCustomer();
            return new ResponseEntity<>(BDTOS, HttpStatus.OK);
    }

    // Employee GET /api/Bankaccount/{IBAN}
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<BankaccountDTO> getBankaccountEmployee(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to return",
            required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) throws RestException{
            if (!bankaccountService.getBankaccountByIBANSafe(IBAN).isPresent()){ throw new BadRequestException("Invalid Iban!"); }
            BankAccount bankAccount = bankaccountService.getBankaccountByIBANSafe(IBAN).get();
            BankaccountDTO BDTO = new BankaccountDTO(bankAccount);
            return new ResponseEntity<>(BDTO, HttpStatus.OK);
    }
}
