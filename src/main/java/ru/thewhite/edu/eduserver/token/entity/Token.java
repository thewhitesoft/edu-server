package ru.thewhite.edu.eduserver.token.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author Maxim Seredkin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    /** Идентификатор */
    @Id
    @GeneratedValue
    private UUID id;

    /** Владелец токена */
    @Column(unique = true)
    private String owner;
}
