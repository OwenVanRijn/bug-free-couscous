package io.swagger.api;

import io.swagger.model.CreateUser;
import io.swagger.model.CustomerUserUpdate;
import io.swagger.model.EmployeeUserUpdate;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<User> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "The CreateUser object only has the fields required to create a User.", required=true, schema=@Schema()) @Valid @RequestBody CreateUser body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"Role\" : \"Customer\",\n  \"Email\" : \"james@email.com\",\n  \"Limits\" : [ {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  }, {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  } ],\n  \"Address\" : {\n    \"country\" : \"Wakanda\",\n    \"city\" : \"Big City\",\n    \"street\" : \"Long Road\",\n    \"postalcode\" : \"1234AB\",\n    \"houseNumber\" : 10\n  },\n  \"FirstName\" : \"James\",\n  \"BankAccounts\" : [ {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  }, {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  } ],\n  \"PhoneNumber\" : \"+31 6 12345678\",\n  \"id\" : 1,\n  \"LastName\" : \"Ford\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteUser(@Parameter(in = ParameterIn.PATH, description = "The user id", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> editUser(@Parameter(in = ParameterIn.PATH, description = "The user id", required=true, schema=@Schema()) @PathVariable("id") Integer id,@Parameter(in = ParameterIn.DEFAULT, description = "The Employee can edit all User information.", required=true, schema=@Schema()) @Valid @RequestBody EmployeeUserUpdate body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"Role\" : \"Customer\",\n  \"Email\" : \"james@email.com\",\n  \"Limits\" : [ {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  }, {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  } ],\n  \"Address\" : {\n    \"country\" : \"Wakanda\",\n    \"city\" : \"Big City\",\n    \"street\" : \"Long Road\",\n    \"postalcode\" : \"1234AB\",\n    \"houseNumber\" : 10\n  },\n  \"FirstName\" : \"James\",\n  \"BankAccounts\" : [ {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  }, {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  } ],\n  \"PhoneNumber\" : \"+31 6 12345678\",\n  \"id\" : 1,\n  \"LastName\" : \"Ford\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> editUserCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "The Employee can edit all User information.", required=true, schema=@Schema()) @Valid @RequestBody CustomerUserUpdate body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"Role\" : \"Customer\",\n  \"Email\" : \"james@email.com\",\n  \"Limits\" : [ {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  }, {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  } ],\n  \"Address\" : {\n    \"country\" : \"Wakanda\",\n    \"city\" : \"Big City\",\n    \"street\" : \"Long Road\",\n    \"postalcode\" : \"1234AB\",\n    \"houseNumber\" : 10\n  },\n  \"FirstName\" : \"James\",\n  \"BankAccounts\" : [ {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  }, {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  } ],\n  \"PhoneNumber\" : \"+31 6 12345678\",\n  \"id\" : 1,\n  \"LastName\" : \"Ford\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> getUser(@Parameter(in = ParameterIn.PATH, description = "The user id", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"Role\" : \"Customer\",\n  \"Email\" : \"james@email.com\",\n  \"Limits\" : [ {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  }, {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  } ],\n  \"Address\" : {\n    \"country\" : \"Wakanda\",\n    \"city\" : \"Big City\",\n    \"street\" : \"Long Road\",\n    \"postalcode\" : \"1234AB\",\n    \"houseNumber\" : 10\n  },\n  \"FirstName\" : \"James\",\n  \"BankAccounts\" : [ {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  }, {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  } ],\n  \"PhoneNumber\" : \"+31 6 12345678\",\n  \"id\" : 1,\n  \"LastName\" : \"Ford\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"Role\" : \"Customer\",\n  \"Email\" : \"james@email.com\",\n  \"Limits\" : [ {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  }, {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  } ],\n  \"Address\" : {\n    \"country\" : \"Wakanda\",\n    \"city\" : \"Big City\",\n    \"street\" : \"Long Road\",\n    \"postalcode\" : \"1234AB\",\n    \"houseNumber\" : 10\n  },\n  \"FirstName\" : \"James\",\n  \"BankAccounts\" : [ {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  }, {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  } ],\n  \"PhoneNumber\" : \"+31 6 12345678\",\n  \"id\" : 1,\n  \"LastName\" : \"Ford\"\n}, {\n  \"Role\" : \"Customer\",\n  \"Email\" : \"james@email.com\",\n  \"Limits\" : [ {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  }, {\n    \"current\" : 0,\n    \"name\" : \"AbsoluteLimit\",\n    \"limit\" : 1000\n  } ],\n  \"Address\" : {\n    \"country\" : \"Wakanda\",\n    \"city\" : \"Big City\",\n    \"street\" : \"Long Road\",\n    \"postalcode\" : \"1234AB\",\n    \"houseNumber\" : 10\n  },\n  \"FirstName\" : \"James\",\n  \"BankAccounts\" : [ {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  }, {\n    \"amount\" : 1200,\n    \"IBAN\" : \"NL20RABO124235346\",\n    \"accountType\" : \"Current\",\n    \"name\" : \"Daily Account\",\n    \"id\" : 1,\n    \"transactions\" : [ {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    }, {\n      \"amount\" : 10,\n      \"performed_by\" : {\n        \"role\" : \"Customer\",\n        \"name\" : \"Owen\"\n      },\n      \"IBAN_from\" : \"IBAN01\",\n      \"id\" : 10,\n      \"IBAN_to\" : \"IBAN02\",\n      \"type\" : \"Transaction\",\n      \"timestamp\" : \"2015-07-20T15:49:04-07:00\"\n    } ]\n  } ],\n  \"PhoneNumber\" : \"+31 6 12345678\",\n  \"id\" : 1,\n  \"LastName\" : \"Ford\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
