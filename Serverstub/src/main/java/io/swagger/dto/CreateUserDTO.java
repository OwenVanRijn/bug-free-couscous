package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.exceptions.BadRequestException;
import io.swagger.exceptions.RestException;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonPropertyOrder(alphabetic = false)
public class CreateUserDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String street;
    private Integer houseNumber;
    private String postalcode;
    private String city;
    private String country;

    public CreateUserDTO() {
    }
    @Schema(example = "Saywer")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Schema(example = "Ford")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Schema(example = "saywerford@email.com")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Schema(example = "2125051666")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Schema(example = "Main Road")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Schema(example = "256")
    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Schema(example = "19900")
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Schema(example = "New York")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Schema(example = "America")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Schema(example = "saywer123")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Schema(example = "welkom")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CreateUserDTO firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CreateUserDTO lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CreateUserDTO email(String email) {
        this.email = email;
        return this;
    }

    public CreateUserDTO phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CreateUserDTO street(String street) {
        this.street = street;
        return this;
    }

    public CreateUserDTO houseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public CreateUserDTO postalcode(String postalcode) {
        this.postalcode = postalcode;
        return this;
    }

    public CreateUserDTO city(String city) {
        this.city = city;
        return this;
    }

    public CreateUserDTO country(String country) {
        this.country = country;
        return this;
    }

    public CreateUserDTO username(String username) {
        this.username = username;
        return this;
    }

    public CreateUserDTO password(String password) {
        this.password = password;
        return this;
    }

    public void validate() throws RestException {
        if (firstName == null || lastName == null || username == null || password == null || email == null || country == null
        || phoneNumber == null || street == null || houseNumber == null || postalcode == null || city == null) {
            throw new BadRequestException("Not all User info is set");
        }

        if (firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("") || email.equals("")
                || country.equals("") || phoneNumber.equals("") || street.equals("") || postalcode.equals("") || city.equals("")) {
            throw new BadRequestException("Not all User info is valid");
        }
    }
}
