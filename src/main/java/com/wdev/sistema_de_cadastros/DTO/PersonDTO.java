package com.wdev.sistema_de_cadastros.DTO;

import jakarta.validation.constraints.*;

public class PersonDTO {
    @NotBlank(message = "The name field is mandatory")
    @Size(min=10)
    private String name;

    @NotBlank(message = "The email field is mandatory")
    @Email
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @Pattern(regexp = "\\d{1,2},\\d{1,2}", message = "The height must be in the format 'X,XX'”")
    private String height;

    public @NotBlank(message = "The name field is mandatory") @Size(min = 10) String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The name field is mandatory") @Size(min = 10) String name) {
        this.name = name;
    }

    public @NotBlank(message = "The email field is mandatory") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "The email field is mandatory") @Email String email) {
        this.email = email;
    }

    @Min(value = 18, message = "Age must be at least 18")
    public int getAge() {
        return age;
    }

    public void setAge(@Min(value = 18, message = "Age must be at least 18") int age) {
        this.age = age;
    }

    public @Pattern(regexp = "\\d{1,2},\\d{1,2}", message = "The height must be in the format 'X,XX'”") String getHeight() {
        return height;
    }

    public void setHeight(@Pattern(regexp = "\\d{1,2},\\d{1,2}", message = "The height must be in the format 'X,XX'”") String height) {
        this.height = height;
    }
};
