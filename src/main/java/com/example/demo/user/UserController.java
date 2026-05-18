package com.example.demo.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(description = "Операции с пользователями", name = "Пользователи")
public class UserController {

    private final UsersDB db = UsersDB.getDB();

    @GetMapping("/{passport}")
    public ResponseEntity<User> getUserByPassport(
            @PathVariable
            String passport
    ) {
        User user = db.getUserByPassport(passport);

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
        db.addUser(user);

        System.out.println(user.getId());
        return HttpStatus.CREATED;
    }

}
