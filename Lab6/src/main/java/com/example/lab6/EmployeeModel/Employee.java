package com.example.lab6.EmployeeModel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "Not valid ID")
    @Size(min = 3,message = "ID should be at least 3 numbers")
    private String ID;
    @NotEmpty(message = "Not valid Name")
    @Size(min = 5,message = "ID should be at least 5 character")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "Name must contain only characters (no numbers)")
    private String name;
    @Email(message = " Not valid email format")
    private String email;
    @Pattern(regexp = "^05\\d*$", message = "Phone Number Must start with '05'.")
    @Size(min =10,max = 10,message = "Phone Number Not valid")
    private String phoneNumber;
    @NotNull(message = "Not valid Age")
    @Min(value = 25,message = "age Must be more than 25")
    @Positive(message = "Age must be number")
    private int age;
    @NotEmpty(message = "Not valid position")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Must be either 'supervisor' or 'coordinator'.")
    private String position;
    @Value("false")
    private boolean onLeave;
    @PastOrPresent(message = "should be a date in the present or the past")
    private LocalDate hireDate;
    @Positive(message = "Annual Leave Not a positive number")
    private int annualLeave;


}
