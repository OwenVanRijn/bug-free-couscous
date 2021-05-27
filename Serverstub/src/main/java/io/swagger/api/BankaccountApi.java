/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.dto.CreateBankaccountDTO;
import io.swagger.model.BankAccount;
import io.swagger.model.BankAccountEdit;
import io.swagger.model.CreateBankaccount;
import io.swagger.model.CreateBankaccountSchema;
import io.swagger.model.DepositOrWithdraw;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-06T12:37:01.770Z[GMT]")
@Validated
public interface BankaccountApi {

    @Operation(summary = "Completes withdraw or deposit", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "operation succesful", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepositOrWithdraw.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "404", description = "IBAN not found") })
    @RequestMapping(value = "/Bankaccount",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<List<DepositOrWithdraw>> completeMoneyFlow(@Parameter(in = ParameterIn.DEFAULT, description = "Complete a deposit or withdraw as an employee", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdraw body);


    @Operation(summary = "create a bankaccount", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "operation succesful", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreateBankaccountSchema.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter") })
    @RequestMapping(value = "/Bankaccount",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<BankAccount> createBankaccount(@Parameter(in = ParameterIn.DEFAULT, description = "Create a bankaccount, this option is employee only", required=true, schema=@Schema()) @Valid @RequestBody CreateBankaccountDTO newBankaccount);


    @Operation(summary = "Delete a account", description = "Calling this allows the employee to delete a bankaccount", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "operation succesful"),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "404", description = "bankaccount not found") })
    @RequestMapping(value = "/Bankaccount/{IBAN}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteBankaccount(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to update", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN);


    @Operation(summary = "Edit a account", description = "Edits a bankaccount based on the provided information. Employees can enter a IBAN to edit all accounts, Customers can only edit their own account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees", "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "operation succesful"),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "404", description = "bankaccount not found") })
    @RequestMapping(value = "/Bankaccount/{IBAN}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<BankAccount> editBankaccount(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to edit", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "editable fields", schema=@Schema()) @Valid @RequestBody CreateBankaccountDTO editBankaccount);


    @Operation(summary = "Get bankaccount information", description = "Calling this allows the Customer to get their own bankaccount information", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "operation succesful", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "404", description = "bankaccount not found") })
    @RequestMapping(value = "/Bankaccount",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<BankAccount>> getBankaccountCustomer();


    @Operation(summary = "Get bankaccount information", description = "Calling this allows the employee to get all the bankaccount information based on the IBAN", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "operation succesful", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "404", description = "bankaccount not found") })
    @RequestMapping(value = "/Bankaccount/{IBAN}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Optional<BankAccount>> getBankaccountEmployee(@Parameter(in = ParameterIn.PATH, description = "IBAN of bankaccount to return", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN);

}

