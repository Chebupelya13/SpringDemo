package com.example.demo.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(description = "Операции с пользователями", name = "Пользователи")
public class UserController {

    private final UsersDB usersDB = UsersDB.getDB();

    @GetMapping
    public List<User> getAllUsers() {
        return usersDB.getAllUsers();
    }

    @GetMapping("/{firstName}")
    public ArrayList<User> getUserByName(
            @PathVariable
            String firstName
    ) {
        ArrayList<User> user = usersDB.getUsersByFirstName(firstName);

        return user;
    }

    @GetMapping("/{phone}")
    public ArrayList<User> getUserByPhone(
            @PathVariable
            String phone
    ) {
        ArrayList<User> user = usersDB.getUsersByPhone(phone);

        return user;
    }

    @GetMapping("/{passport}")
    public ResponseEntity<User> getUserByPassport(
            @PathVariable
            String passport
    ) {
        User user = usersDB.getUserByPassport(passport);

        if (user != null){
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(description = "Создание записи о новом клиенте")
    @PostMapping
    public HttpStatus createUser(
            @RequestBody
            User user
    ) {
        usersDB.addUser(user);

        System.out.println(user.getId());
        return HttpStatus.CREATED;
    }

}
