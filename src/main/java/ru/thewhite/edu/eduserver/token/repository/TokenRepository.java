package ru.thewhite.edu.eduserver.token.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import ru.thewhite.edu.eduserver.token.entity.Token;

import java.util.UUID;

/**
 * @author Maxim Seredkin
 */
public interface TokenRepository extends CrudRepository<Token, UUID> {
}
