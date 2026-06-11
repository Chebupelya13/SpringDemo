package com.example.demo.dao;

import com.example.demo.entity.File;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class FilesDao {
    private final SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(FilesDao.class);

    public FilesDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addFile(String filename, byte[] file) {
        File newFile = new File(filename, file);
        sessionFactory.getCurrentSession().persist(newFile);
    }

    public File getFile(String filename) {
        return sessionFactory.getCurrentSession()
                .createQuery("from File where filename=:filename", File.class)
                .setParameter("filename", filename)
                .getSingleResultOrNull();
    }

    public void deleteFile(String filename) {
        sessionFactory.getCurrentSession().createQuery("delete File where filename=:filename").executeUpdate();
    }

}
