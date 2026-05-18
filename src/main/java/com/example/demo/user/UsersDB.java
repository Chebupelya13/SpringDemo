package com.example.demo.user;

import java.util.ArrayList;
import java.util.UUID;

public class UsersDB {

    private static ArrayList<User> users = new ArrayList<User>();

    private static final UsersDB DB = new UsersDB();
    private UsersDB() {}
    public static UsersDB getDB() {
        return DB;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserById(UUID userId) {
        for (User user: users){
            if (user.getId() == userId) {
                return user;
            }
        }

        return new User();
    }

    public ArrayList<UUID> getAll() {
        ArrayList<UUID> usersIds = new ArrayList<UUID>();

        for (User user : users) {
            usersIds.add(user.getId());
        }

        return usersIds;
    }

}
