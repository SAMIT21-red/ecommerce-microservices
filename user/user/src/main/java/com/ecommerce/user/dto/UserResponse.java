package com.ecommerce.user.dto;

import com.ecommerce.user.entities.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private UserRole role;
    private AddressDTO address;
}
