package com.techgen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    @NotEmpty
    @Size(min = 3, message = "customer name should have at least 3 character")
    private String name;

    @NotEmpty(message = "city must not be empty")
    private String city;

    @NotEmpty
    @Size(min = 3, message = "customerId should have at least 5 character")
    private String customerId;
}
