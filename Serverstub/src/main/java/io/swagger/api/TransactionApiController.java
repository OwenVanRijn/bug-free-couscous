package io.swagger.api;

import io.swagger.dto.TransactionsPageDTO;
import io.swagger.model.Body;
import io.swagger.model.TransactionEdit;
import io.swagger.model.TransactionGet;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.services.TransactionService;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")
@RestController
public class TransactionApiController implements TransactionApi {

    @Autowired
    TransactionService transactionService;

    private static final Logger log = LoggerFactory.getLogger(TransactionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "argument fields", required=true, schema=@Schema()) @Valid @RequestBody Body body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteTransaction(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> editTransaction(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") Integer id,@Parameter(in = ParameterIn.DEFAULT, description = "editable fields", schema=@Schema()) @Valid @RequestBody List<TransactionEdit> body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TransactionsPageDTO> getTransaction(@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "id", required = false) List<Long> id, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "IBAN", required = false) List<String> IBAN, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema( defaultValue="50")) @Valid @RequestParam(value = "limit", required = false, defaultValue="50") Integer limit, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema( defaultValue="1")) @Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page) {
        String accept = request.getHeader("Accept");

        return new ResponseEntity<TransactionsPageDTO>(transactionService.getTransactions(id, IBAN, limit, page, null), HttpStatus.OK);
        /*
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<TransactionGet>>(objectMapper.readValue("[ {\n  \"Transactions\" : [ {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  }, {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  } ],\n  \"PageCount\" : 7,\n  \"Count\" : 69\n}, {\n  \"Transactions\" : [ {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  }, {\n    \"amount\" : 10,\n    \"performed_by\" : {\n      \"role\" : \"Customer\",\n      \"name\" : \"Owen\"\n    },\n    \"IBAN_from\" : \"IBAN01\",\n    \"id\" : 10,\n    \"IBAN_to\" : \"IBAN02\",\n    \"type\" : \"Transaction\",\n    \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n  } ],\n  \"PageCount\" : 7,\n  \"Count\" : 69\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<TransactionGet>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<TransactionGet>>(HttpStatus.NOT_IMPLEMENTED);

         */
    }
}
