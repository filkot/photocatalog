package guru.qa.photocatalog.service;

import guru.qa.photocatalog.domain.Photo;

import java.util.List;

public interface PhotoService {

    List<Photo> allPhoto();
    Photo byId(String id);

    Photo photoByDescription(String description);
}
