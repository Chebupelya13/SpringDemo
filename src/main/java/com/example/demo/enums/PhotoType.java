package com.example.demo.enums;

public enum PhotoType {
    PASSPORT("passports"),
    REGISTRATION("registrations"),
    AVATAR("avatars");

    private final String folderName;

    PhotoType(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}