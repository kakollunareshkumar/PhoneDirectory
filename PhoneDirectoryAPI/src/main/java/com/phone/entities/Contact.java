package com.phone.entities;

//name, phone number, and email

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="contacts")
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private   Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private  String name;

    @NotBlank(message = "phoneNumber is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must not be null and include 10 digits ")
    @Column(name = "phone_number",nullable = false,unique = true)
    private String phoneNumber;

    @Email(message ="Provide proper email address")
    @Column(nullable = false,unique = true)
    @NotBlank(message = "Email is required")
    private String email;
}
