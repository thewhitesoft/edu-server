package ru.thewhite.edu.eduserver.token.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author Maxim Seredkin
 */
@Value
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class CreateTokenDto {
    @NotNull
    String owner;
}
