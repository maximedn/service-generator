package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.config;

import be.bosa.beapp.rest.common.config.JerseyCommonConfig;
import be.bosa.beapp.rest.common.mock.MockService;
import be.bosa.beapp.rest.common.mock.impl.MockServiceImpl;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.impl.ContentApiImpl;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.impl.MetadataApiImpl;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Content;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Metadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends JerseyCommonConfig {

    private static final String TITLE = "%SERVICE-NAME%";
    private static final String VERSION = "v1";
    private static final String BASE_PATH = "/%SERVICE-SHORT-1W%";
    private static final String RESOURCE_PACKAGE = "be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api";

    public JerseyConfig() {
        super(TITLE, VERSION, "/%PROJECT%", BASE_PATH, RESOURCE_PACKAGE);
        register(ContentApiImpl.class);
        register(MetadataApiImpl.class);
    }

    @Bean
    public MockService<Content> mockServiceContent() {
        return new MockServiceImpl<>();
    }

    @Bean
    public MockService<Metadata> mockServiceMetadata() {
        return new MockServiceImpl<>();
    }
}
