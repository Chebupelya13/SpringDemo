package com.example.demo.dao;

import com.example.demo.entity.Photo;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoDao {

    private final SessionFactory sessionFactory;

    public PhotoDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addPhoto(Photo photo) {
        sessionFactory.getCurrentSession().persist(photo);
    }

    public Photo getPhotoById(int photoId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Photo where id=:photoId", Photo.class)
                .setParameter("photoId", photoId)
                .getSingleResult();
    }

}
