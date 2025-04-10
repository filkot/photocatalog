package guru.qa.photocatalog.service;

import guru.qa.photocatalog.domain.Photo;
import guru.qa.photocatalog.domain.graphql.PhotoGql;
import guru.qa.photocatalog.domain.graphql.PhotoInputGql;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PhotoService {

    List<Photo> allPhotos();

    List<PhotoGql> allGqlPhotos();

    Page<PhotoGql> allGqlPhotos(Pageable pageable);

    Photo photoById(String id);

    Photo addPhoto(Photo photo);

    PhotoGql addPhotoGql(PhotoInputGql photoGql);

    PhotoGql photoGqlById(String id);

    Photo photoByDescription(String description);
}
