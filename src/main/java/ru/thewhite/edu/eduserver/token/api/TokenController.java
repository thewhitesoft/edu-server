package ru.thewhite.edu.eduserver.token.api;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.thewhite.edu.eduserver.token.api.dto.CreateTokenDto;
import ru.thewhite.edu.eduserver.token.entity.Token;
import ru.thewhite.edu.eduserver.token.repository.TokenRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Maxim Seredkin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {
    private final TokenRepository tokenRepository;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Token create(@Valid @RequestBody CreateTokenDto body) {
        return tokenRepository.save(Token.builder()
                                         .owner(body.getOwner())
                                         .build());
    }

    @GetMapping("list")
    public List<Token> create() {
        return Lists.newArrayList(tokenRepository.findAll());
    }
}
