package be.one16.barka.adapter.in;

import lombok.Data;

import java.util.UUID;

@Data
public class LeverancierDto {
    private String name;
    private String streetName;
    private int houseNumber;
    private String box;
    private int zipCode;
    private String city;
    private String country;
    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private String btw;
    private String remarks;
}
