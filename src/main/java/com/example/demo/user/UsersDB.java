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

    public User getFirst() {

        User user = users.get(0);

        if (user == null) {
            user = new User();
        }

        return user;

    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserByPassport(String passport) {
        for (User user: users){
            if (
                    user.getPassport().replace(" ", "")
                            .equals(
                                    passport.replace(" ", "")
                            )
            ) {
                return user;
            }
        }

        return null ;
    }

    public ArrayList<User> getUsersByFirstName (String firstName) {
        ArrayList<User> usersList = new ArrayList<User>();

        for(User user : users) {
            if (user.getFirstname().equals(firstName)){
                usersList.add(user);
            }
        }

        return usersList;
    }

    public ArrayList<UUID> getAllIds() {
        ArrayList<UUID> usersIds = new ArrayList<UUID>();

        for (User user : users) {
            usersIds.add(user.getId());
        }

        return usersIds;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

}
