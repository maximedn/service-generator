package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.impl;

import be.bosa.beapp.rest.common.logging.BeAppLogger;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Metadata;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.MetadataService;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MetadataApiImplTest {
    private static final String ENDPOINT = "/metadata";
    private static final BeAppLogger LOGGER = BeAppLogger.create(MetadataApiImplTest.class);

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private String basePath;

    @MockBean
    private MetadataService metadataService;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = basePath;
    }

    @Test
    public void getMetadataByFilename_Ok() throws Exception {
        Metadata metadata = new Metadata().metadataId("1").metadataName("name");
        when(metadataService.getMetadataByFilename("test.file")).thenReturn(metadata);
        given().when().queryParam("filename", "test.file").get(ENDPOINT)
                .then().assertThat().statusCode(200).body("metadataId", equalTo("1"))
                .body("metadataName", equalTo("name"));
    }

    @Test
    public void getMetadataByFilename_NotFound() throws Exception {
        given().when().queryParam("filename", "test.file").get(ENDPOINT).then().assertThat().statusCode(404);
    }

    @Test
    public void getMetadataByFilename_filenameSize() {
        String payload = RandomStringUtils.randomAlphanumeric(255);
        given().when().queryParam("filename", payload).get(ENDPOINT).then().assertThat().statusCode(404);
        payload = RandomStringUtils.randomAlphanumeric(256);
        given().when().queryParam("filename", payload).get(ENDPOINT).then().assertThat().statusCode(400);
    }

    @Test
    public void getMetadataByFilename_Error() throws Exception {
        when(metadataService.getMetadataByFilename("test.file")).thenThrow(new Exception("Error !"));
        given().when().queryParam("filename", "test.file").get(ENDPOINT).then().assertThat().statusCode(500);
    }

}
