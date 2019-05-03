package ch.bfh.bti7081.s2019.blue.shared.dto;

public class AddressDto {

    private Integer id;

    private String streetName;

    private String city;

    private Integer postalCode;

    private String houseNr;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getPostalCode() {
        return this.postalCode;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getHouseNr() {
        return this.houseNr;
    }

}