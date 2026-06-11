package com.example.demo.entity;


import com.example.demo.enums.PhotoType;
import jakarta.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column
    private String path;

    @Column
    @Enumerated(EnumType.STRING)
    private PhotoType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Column
    private byte[] photo;

    public Photo() {}

    public Photo(String path, PhotoType type) {
        this.path = path;
        this.type = type;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(PhotoType type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public PhotoType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

}
