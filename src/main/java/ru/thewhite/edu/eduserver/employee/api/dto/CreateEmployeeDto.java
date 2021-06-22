package ru.thewhite.edu.eduserver.employee.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;
import ru.thewhite.edu.eduserver.employee.entry.Gender;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Maxim Seredkin
 */
@Value
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class CreateEmployeeDto {
    @NotNull
    UUID tokenId;

    @NotBlank
    String fullName;

    @NotBlank
    @Email
    String email;

    @NotBlank
    @Pattern(regexp = "^.*[0-9].*$")
    @Pattern(regexp = "^.*[A-Z].*$")
    @Pattern(regexp = "^.*[a-z].*$")
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 6, max = 12)
    String password;

    @Pattern(regexp = "^\\+7[0-9]{10}$")
    String phone;

    @URL
    String profileUrl;

    @NotNull
    Gender gender;

    @AssertTrue
    @NotNull
    Boolean acceptManual;

    @NotNull
    Long salary;

    LocalDate birthday;

    @Size(max = 200)
    String comment;
}
