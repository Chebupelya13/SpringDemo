package com.example.demo.entity;

import jakarta.persistence.*;


@Entity
@Table(name="files")
public class File {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String filename;

    @Column
    private byte[] photo;

    public File() {}

    public File(String filename, byte[] photo) {
        this.filename = filename;
        this.photo = photo;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
