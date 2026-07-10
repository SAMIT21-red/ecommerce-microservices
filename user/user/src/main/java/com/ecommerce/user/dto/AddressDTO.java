package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String city;
    private String street;
    private String state;
    private String country;
    private String zipcode;
}
