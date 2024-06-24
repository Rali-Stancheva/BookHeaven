package com.example.library.services.impl;

import com.example.library.models.entities.Author;
import com.example.library.models.entities.AuthorImage;
import com.example.library.repositories.AuthorImageRepository;
import com.example.library.repositories.AuthorRepository;
import com.example.library.services.AuthorImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorImageServiceImpl implements AuthorImageService {

    private final AuthorImageRepository authorImageRepository;
    private final AuthorRepository authorRepository;
    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public AuthorImageServiceImpl(AuthorImageRepository authorImageRepository, AuthorRepository authorRepository, FileStorageService fileStorageService) {
        this.authorImageRepository = authorImageRepository;
        this.authorRepository = authorRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void saveGalleryImages(List<MultipartFile> files, Author author) throws IOException {
        if (files != null) {
            for (MultipartFile galleryFile : files) {
                if (!galleryFile.isEmpty()) {
                    String galleryFileName = galleryFile.getOriginalFilename();
                    Path galleryCopyLocation = Paths.get(uploadDir + File.separator + galleryFileName);
                    Files.copy(galleryFile.getInputStream(), galleryCopyLocation, StandardCopyOption.REPLACE_EXISTING);

                    AuthorImage authorImage = new AuthorImage();
                    authorImage.setImage(galleryFileName);
                    authorImage.setAuthor(author);

                    authorImageRepository.save(authorImage);
                }
            }
        }
    }

    @Override
    public void updateGalleryImages(List<MultipartFile> files, Author author) throws IOException {

        List<AuthorImage> existingImages = authorImageRepository.findByAuthor(author);

        for (AuthorImage image : existingImages) {
            fileStorageService.deleteFile(image.getImage());
            authorImageRepository.delete(image);
        }

        saveGalleryImages(files, author);

    }

    @Override
    public List<AuthorImage> getGalleryImagesByAuthor(Author author) {
        return authorImageRepository.findByAuthor(author);
    }


    @Override
    public void addAuthorGalleryImages(Long authorId, MultipartFile[] images) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id"));

        List<AuthorImage> authorImages = author.getImagesList();
        if (authorImages == null) {
            authorImages = new ArrayList<>();
        }

        for (MultipartFile image : images) {
            String fileName = fileStorageService.storeFile(image);
            AuthorImage actorImage = new AuthorImage(fileName, author);
            authorImages.add(actorImage);
        }
        author.setImagesList(authorImages);
        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthorGalleryImage(Long authorId, Long imageId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id"));

        AuthorImage imageToDelete = author.getImagesList().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Image with id " + imageId + " not found for author " + authorId));

        author.getImagesList().remove(imageToDelete);
        authorRepository.save(author);
    }
}
