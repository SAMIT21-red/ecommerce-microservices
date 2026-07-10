package com.ecommerce.user.dto;

import com.ecommerce.user.entities.UserRole;
import lombok.Data;

@Data
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private AddressDTO address;
    private UserRole role;   // <-- Add this

}
