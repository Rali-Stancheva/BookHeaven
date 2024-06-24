package com.example.library.services;


import com.example.library.models.entities.Author;
import com.example.library.models.entities.AuthorImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AuthorImageService {
    void saveGalleryImages(List<MultipartFile> files, Author author) throws IOException;

    void updateGalleryImages(List<MultipartFile> files, Author author) throws IOException;

    List<AuthorImage> getGalleryImagesByAuthor(Author author);

    void addAuthorGalleryImages(Long authorId, MultipartFile[] images);

    void deleteAuthorGalleryImage(Long authorId, Long imageId);
}
