package io.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Address;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class AddressPutDTO {

    @JsonProperty("street")
    private String street = null;

    @JsonProperty("houseNumber")
    private Integer houseNumber = null;

    @JsonProperty("postalcode")
    private String postalcode = null;

    @JsonProperty("city")
    private String city = null;

    @JsonProperty("country")
    private String country = null;

    /**
     * Get street
     * @return street
     **/
    @Schema(example = "Long Road", required = true, description = "")
    @NotNull

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public AddressPutDTO houseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    /**
     * Get houseNumber
     * @return houseNumber
     **/
    @Schema(example = "10", required = true, description = "")
    @NotNull

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public AddressPutDTO postalcode(String postalcode) {
        this.postalcode = postalcode;
        return this;
    }

    /**
     * Get postalcode
     * @return postalcode
     **/
    @Schema(example = "1234AB", required = true, description = "")
    @NotNull

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public AddressPutDTO city(String city) {
        this.city = city;
        return this;
    }

    /**
     * Get city
     * @return city
     **/
    @Schema(example = "Big City", required = true, description = "")
    @NotNull

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressPutDTO country(String country) {
        this.country = country;
        return this;
    }

    /**
     * Get country
     * @return country
     **/
    @Schema(example = "Wakanda", required = true, description = "")
    @NotNull

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Address {\n");

        sb.append("    street: ").append(toIndentedString(street)).append("\n");
        sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
        sb.append("    postalcode: ").append(toIndentedString(postalcode)).append("\n");
        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public void fillEmpty(Address src) {

        if(street.equals("")) {
            street = src.getStreet();
        }

        if(houseNumber.toString().equals("")) {
            houseNumber = src.getHouseNumber();
        }

        if(postalcode.equals("")) {
            postalcode = src.getPostalcode();
        }

        if(city.equals("")) {
            city = src.getCity();
        }

        if(country.equals("")) {
            country = src.getCountry();
        }
    }
}
