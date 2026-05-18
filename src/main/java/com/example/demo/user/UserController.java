package com.example.demo.user;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Map<String, User> repository;

    public UserController(Map<String, User> repository) {
        this.repository = repository;
    }

    @Operation(description = "Создание записи о новом клиенте")
    @PostMapping(value = "/create_user")
    public HttpStatus createUser(
            @RequestBody
            User user
    ) {

        UsersDB db = UsersDB.getDB();
        db.addUser(user);

        return HttpStatus.CREATED;
    }

}
