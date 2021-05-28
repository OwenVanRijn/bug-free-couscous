package io.swagger.api;

import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.model.BankAccount;
import io.swagger.model.BankAccountEdit;
import io.swagger.model.CreateBankaccount;
import io.swagger.model.CreateBankaccountSchema;
import io.swagger.model.DepositOrWithdraw;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.services.BankaccountService;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")
@RestController
public class BankaccountApiController implements BankaccountApi {

    private static final Logger log = LoggerFactory.getLogger(BankaccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private BankaccountService bankaccountService;

    @org.springframework.beans.factory.annotation.Autowired
    public BankaccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    // Employee PUT /api/Bankaccount
    public ResponseEntity<BankAccount> completeMoneyFlow(@Parameter(in = ParameterIn.DEFAULT, description = "Complete a deposit or withdraw as an employee", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdraw body) {
        try {
            if (body.getAmount() >= 0) {
                if (bankaccountService.getBankaccountByIBANSafe(body.getIBAN()).isPresent()) {
                    BankAccount bankAccount = bankaccountService.DepositOrWithdraw(body);
                    if (bankAccount == null){
                        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    return new ResponseEntity<>(bankAccount, HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Employee POST /api/Bankaccount
    public ResponseEntity<BankAccount> createBankaccount(@Parameter(in = ParameterIn.DEFAULT,
            description = "Create a bankaccount, this option is employee only", required=true, schema=@Schema()) @Valid @RequestBody CreateBankaccountDTO newBankaccount) {
        try{
        BankAccount bankAccount = bankaccountService.createBankaccount(newBankaccount);
        return new ResponseEntity<>(bankAccount, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Employee DELETE /api/Bankaccount/{IBAN}
    public ResponseEntity<Void> deleteBankaccount(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to delete", required=true,
            schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        try {
            bankaccountService.deleteByIBAN(IBAN);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Employee & Customer PUT /api/Bankaccount/{IBAN}
    public ResponseEntity<BankAccount> editBankaccount(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to edit", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "editable fields",
            schema=@Schema()) @Valid @RequestBody CreateBankaccountDTO editBankaccount) {
        try{
            BankAccount bankAccount = bankaccountService.getBankaccountByIBANSafe(IBAN).get();
            bankAccount.name(editBankaccount.getName()).accountType(editBankaccount.getAccountType());
            bankaccountService.saveBankAccount(bankAccount);
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Customer GET /api/Bankaccount
    public ResponseEntity<Optional<BankAccount>> getBankaccountCustomer() {
        try {
            String IBAN = "NL01INHO0000000001"; // Needs to become IBAN of customer
            Optional<BankAccount> bankAccount = bankaccountService.getBankaccountByIBANSafe(IBAN);
            if (!bankAccount.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Employee GET /api/Bankaccount/{IBAN}
    public ResponseEntity<Optional<BankAccount>> getBankaccountEmployee(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to return",
            required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        try {
            Optional<BankAccount> bankAccount = bankaccountService.getBankaccountByIBANSafe(IBAN);
            if (!bankAccount.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
