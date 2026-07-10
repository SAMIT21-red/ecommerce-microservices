package com.app.ecom.services;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.entities.Address;
import com.app.ecom.entities.User;
import com.app.ecom.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;

    // Get All Users
    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    // Create User
    public UserResponse createUser(UserRequest userRequest) {

        User user = new User();
        updateUserFromRequest(user, userRequest);

        User savedUser = userRepository.save(user);

        return mapToUserResponse(savedUser);
    }

    // Get User By Id
    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    // Update User
    public Optional<UserResponse> updateUser(Long id, UserRequest userRequest) {

        return userRepository.findById(id)
                .map(existingUser -> {

                    updateUserFromRequest(existingUser, userRequest);

                    User updatedUser = userRepository.save(existingUser);

                    return mapToUserResponse(updatedUser);
                });
    }

    // =========================
    // Entity -> Response DTO
    // =========================
    private UserResponse mapToUserResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNo(user.getPhoneNo());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {

            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());

            response.setAddress(addressDTO);
        }

        return response;
    }

    // =========================
    // Request DTO -> Entity
    // =========================
    private void updateUserFromRequest(User user, UserRequest request) {

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNo(request.getPhoneNo());

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        if (request.getAddress() != null) {

            Address address = user.getAddress();

            if (address == null) {
                address = new Address();
            }

            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setZipcode(request.getAddress().getZipcode());

            user.setAddress(address);
        }
    }
}