package guru.qa.photocatalog.service;

import guru.qa.photocatalog.data.PhotoEntity;
import guru.qa.photocatalog.data.PhotoRepository;
import guru.qa.photocatalog.domain.Photo;
import guru.qa.photocatalog.domain.graphql.PhotoGql;
import guru.qa.photocatalog.domain.graphql.PhotoInputGql;
import guru.qa.photocatalog.ex.PhotoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static guru.qa.photocatalog.domain.Photo.fromGqlPhoto;

@Component
public class DbPhotoService implements PhotoService {
    private final PhotoRepository photoRepository;

    @Autowired
    public DbPhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public List<Photo> allPhotos() {
        return photoRepository.findAll().stream()
                .map(fe -> {
                    return new Photo(
                            fe.getDescription(),
                            fe.getLastModifyDate(),
                            fe.getContent() != null ? new String(fe.getContent()) : ""
                    );
                }).toList();

    }

    @Override
    public List<PhotoGql> allGqlPhotos() {
        return photoRepository.findAll().stream()
                .map(fe -> {
                    return new PhotoGql(
                            fe.getId(),
                            fe.getDescription(),
                            fe.getLastModifyDate(),
                            fe.getContent() != null ? new String(fe.getContent()) : ""
                    );
                }).toList();
    }

    @Override
    public Page<PhotoGql> allGqlPhotos(Pageable pageable) {
        return photoRepository.findAll(pageable)
                .map(fe -> {
                            return new PhotoGql(
                                    fe.getId(),
                                    fe.getDescription(),
                                    fe.getLastModifyDate(),
                                    fe.getContent() != null ? new String(fe.getContent()) : "");
                        }
                );

    }

    @Override
    public Photo photoById(String id) {
        return fromGqlPhoto(photoGqlById(id));
    }

    @Override
    public Photo addPhoto(Photo photo) {
        return null;
    }

    @Override
    public PhotoGql addPhotoGql(PhotoInputGql photoGql) {
        PhotoEntity pe = new PhotoEntity();
        pe.setDescription(photoGql.description());
        pe.setLastModifyDate(new Date());
        pe.setContent(photoGql.content().getBytes(StandardCharsets.UTF_8));
        PhotoEntity saved = photoRepository.save(pe);
        return new PhotoGql(
                saved.getId(),
                saved.getDescription(),
                saved.getLastModifyDate(),
                Arrays.toString(saved.getContent())
        );
    }

    @Override
    public PhotoGql photoGqlById(String id) {
        return photoRepository.findById(UUID.fromString(id))
                .map(fe -> {
                            return new PhotoGql(
                                    fe.getId(),
                                    fe.getDescription(),
                                    fe.getLastModifyDate(),
                                    fe.getContent() != null ? new String(fe.getContent()) : "");
                        }

                ).orElseThrow(PhotoNotFoundException::new);
    }

    @Override
    public Photo photoByDescription(String description) {
        return null;
    }
}
