package com.example.demo.service;

import com.example.demo.dao.PhotoDao;
import com.example.demo.entity.Photo;
import com.example.demo.enums.PhotoType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PhotoService {

    private final PhotoDao photoDao;

    public PhotoService(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    public Photo getPhotoById(int photoId) {
        return photoDao.getPhotoById(photoId);
    }

//    public void addPhoto(String objectKey, PhotoType type) {
//        Photo photo = new Photo(objectKey, type);
//        photoDao.addPhoto(photo);
//    }

}
