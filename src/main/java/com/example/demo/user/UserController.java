package com.example.demo.user;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersDB db = UsersDB.getDB();

    @GetMapping
    public User getUser() {
        return db.getFirst();
    }

    @Operation(description = "Создание записи о новом клиенте")
    @PostMapping
    public HttpStatus createUser(
            @RequestBody
            User user
    ) {
        db.addUser(user);

        System.out.println(user.getId());
        return HttpStatus.CREATED;
    }

}
