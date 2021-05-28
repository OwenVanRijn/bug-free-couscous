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

    public ResponseEntity<BankAccount> completeMoneyFlow(@Parameter(in = ParameterIn.DEFAULT, description = "Complete a deposit or withdraw as an employee", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdraw body) {
        try {
            if (body.getAmount() >= 0) {
                if (bankaccountService.getBankaccountByIBANSafe(body.getIBAN()).isPresent()) {
                    BankAccount bankAccount = bankaccountService.DepositOrWithdraw(body);
                    if (bankAccount == null){
                        return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    return new ResponseEntity<>(bankAccount, HttpStatus.OK);
                }
            }
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<BankAccount> createBankaccount(@Parameter(in = ParameterIn.DEFAULT,
            description = "Create a bankaccount, this option is employee only", required=true, schema=@Schema()) @Valid @RequestBody CreateBankaccountDTO newBankaccount) {
        try{
        BankAccount bankAccount = bankaccountService.createBankaccount(newBankaccount);
        return new ResponseEntity<>(bankAccount, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteBankaccount(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to delete", required=true,
            schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        try {
            bankaccountService.deleteByIBAN(IBAN);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    public ResponseEntity<List<BankAccount>> getBankaccountCustomer() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BankAccount>>(objectMapper.readValue("[ {\n  \"amount\" : 1200,\n  \"IBAN\" : \"NL20RABO124235346\",\n  \"accountType\" : \"Current\",\n  \"name\" : \"Daily Account\",\n  \"id\" : 1,\n  \"transactions\" : [ {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  }, {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  } ]\n}, {\n  \"amount\" : 1200,\n  \"IBAN\" : \"NL20RABO124235346\",\n  \"accountType\" : \"Current\",\n  \"name\" : \"Daily Account\",\n  \"id\" : 1,\n  \"transactions\" : [ {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  }, {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  } ]\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BankAccount>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BankAccount>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Optional<BankAccount>> getBankaccountEmployee(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to return",
            required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        try {
            Optional<BankAccount> bankAccount = bankaccountService.getBankaccountByIBANSafe(IBAN);
            if (!bankAccount.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
