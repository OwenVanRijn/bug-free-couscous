package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = false)
public class CreateUserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private Integer houseNumber;
    private String postalcode;
    private String city;
    private String country;

    public CreateUserDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}