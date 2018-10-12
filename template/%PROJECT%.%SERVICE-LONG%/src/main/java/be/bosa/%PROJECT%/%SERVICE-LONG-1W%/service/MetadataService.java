package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service;

import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Metadata;

import java.io.IOException;

public interface MetadataService {
    Metadata getMetadataByFilename(String filename) throws Exception;
}
