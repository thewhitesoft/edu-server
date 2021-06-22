package ru.thewhite.edu.eduserver.employee.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import ru.thewhite.edu.eduserver.token.entity.Token;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Сотрудник
 *
 * @author Maxim Seredkin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(name = "employee_token_id_idx", columnList = "token_id"))
public class Employee {
    /** Идентификатор */
    @Id
    @GeneratedValue
    private UUID id;

    /** Токен владельца */
    @ManyToOne(optional = false)
    @JoinColumn(name = "token_id", nullable = false)
    private Token token;

    @NotBlank
    @Column(nullable = false)
    String fullName;

    @NotBlank
    @Email
    @Column(nullable = false)
    String email;

    @NotBlank
    @Pattern(regexp = "^.*[0-9].*$")
    @Pattern(regexp = "^.*[A-Z].*$")
    @Pattern(regexp = "^.*[a-z].*$")
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 6, max = 12)
    @Column(length = 12, nullable = false)
    String password;

    @Pattern(regexp = "^\\+7[0-9]{10}$")
    String phone;

    @URL
    String profileUrl;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @NotNull
    @Column(nullable = false)
    long salary;

    LocalDate birthday;

    @Size(max = 200)
    @Column(length = 200)
    String comment;
}
