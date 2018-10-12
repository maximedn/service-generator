package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.impl;

import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Metadata;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.MetadataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!mockdata")
public class MetadataServiceImpl implements MetadataService {
    @Override
    public Metadata getMetadataByFilename(String filename) {
        return null;
    }
}
