package pl.emgie.oraclemappingtype.model;

import java.io.Serializable;

public class Address implements Serializable {

    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    private char[] countryId;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public char[] getCountryId() {
        return countryId;
    }

    public void setCountryId(char[] countryId) {
        this.countryId = countryId;
    }
}
