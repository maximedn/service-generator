package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.impl;

import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.MetadataApi;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Metadata;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class MetadataApiImpl implements MetadataApi {

    private MetadataService metadataService;

    @Autowired
    public MetadataApiImpl(MetadataService metadataService) {
        this.metadataService = metadataService;
    }



    @Override
    public Metadata getMetadataByFilename_(@NotNull @Size(max = 255) String filename) throws Exception {
        return metadataService.getMetadataByFilename(filename);
    }
}
