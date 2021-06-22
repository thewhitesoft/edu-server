package ru.thewhite.edu.eduserver.employee.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.thewhite.edu.eduserver.employee.api.dto.CreateEmployeeDto;
import ru.thewhite.edu.eduserver.employee.entry.Employee;
import ru.thewhite.edu.eduserver.employee.repository.EmployeeRepository;
import ru.thewhite.edu.eduserver.token.repository.TokenRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author Maxim Seredkin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@Valid @RequestBody CreateEmployeeDto body) {
        return employeeRepository.save(Employee.builder()
                                               .fullName(body.getFullName())
                                               .email(body.getEmail())
                                               .password(body.getPassword())
                                               .phone(body.getPhone())
                                               .profileUrl(body.getProfileUrl())
                                               .gender(body.getGender())
                                               .salary(body.getSalary())
                                               .birthday(body.getBirthday())
                                               .comment(body.getComment())
                                               .token(tokenRepository.findById(body.getTokenId()).orElseThrow(() -> new RuntimeException("Токен не найден")))
                                               .build());
    }

    @GetMapping("list")
    public List<Employee> create(@RequestParam UUID tokenId) {
        return employeeRepository.findAllByTokenId(tokenId);
    }
}
