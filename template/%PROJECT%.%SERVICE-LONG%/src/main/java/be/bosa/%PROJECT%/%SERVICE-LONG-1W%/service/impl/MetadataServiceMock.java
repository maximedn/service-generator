package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.impl;

import be.bosa.beapp.rest.common.mock.MockService;
import be.bosa.beapp.rest.common.model.Problem;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Metadata;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Profile("mockdata")
public class MetadataServiceMock implements MetadataService {


    private MockService<Metadata> mockServiceMetadata;

    @Autowired
    public MetadataServiceMock(MockService<Metadata> mockServiceMetadata) {
        this.mockServiceMetadata = mockServiceMetadata;
    }

    @Override
    public Metadata getMetadataByFilename(String filename) throws Exception {
        Metadata mockedEntity = mockServiceMetadata.getMockedEntity("mockdata/metadata/ok/" + filename, Metadata.class);
        if (mockedEntity == null) {
            Problem problem = mockServiceMetadata.getMockedError("mockdata/metadata/error/" + filename);
            if(problem != null) {
                throw new Exception("Mocked error: " + problem.getDetail());
            }
        }
        return mockedEntity;
    }
}
