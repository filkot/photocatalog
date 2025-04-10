package guru.qa.photocatalog.domain;

import guru.qa.photocatalog.domain.graphql.PhotoGql;

import java.util.Date;

public record Photo(String description,
                    Date lastModifyDate,
                    String content) {

    public static Photo fromGqlPhoto(PhotoGql gql) {
        return new Photo(
                gql.description(),
                gql.lastModifyDate(),
                gql.content()
        );
    }
}
