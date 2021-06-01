package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Address;
import io.swagger.model.CustomerUserUpdate;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;

public class CustomerEditUserDTO {
    @JsonProperty("Email")
    private String email = null;

    @JsonProperty("PhoneNumber")
    private String phoneNumber = null;

    @JsonProperty("Address")
    private AddressPutDTO address = null;

    public CustomerEditUserDTO email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     * @return email
     **/
    @Schema(example = "james@email.com")

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerEditUserDTO phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Get phoneNumber
     * @return phoneNumber
     **/
    @Schema(example = "+31 6 12345678")

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerEditUserDTO address(AddressPutDTO address) {
        this.address = address;
        return this;
    }

    /**
     * Get address
     * @return address
     **/
    @Schema(description = "")

    @Valid
    public AddressPutDTO getAddress() {
        return address;
    }

    public void setAddress(AddressPutDTO address) {
        this.address = address;
    }

    public void fillEmpty(User src) {
        if(email == null) {
            email = src.getEmail();
        }

        if(phoneNumber == null) {
            phoneNumber = src.getPhoneNumber();
        }

        address.fillEmpty(src.getAddress());
    }

    public Address getAddressObj(Address oldAddress) {
        return oldAddress.city(address.getCity()).country(address.getCountry()).houseNumber(address.getHouseNumber())
                .postalcode(address.getPostalcode()).street(address.getStreet());
    }
}
