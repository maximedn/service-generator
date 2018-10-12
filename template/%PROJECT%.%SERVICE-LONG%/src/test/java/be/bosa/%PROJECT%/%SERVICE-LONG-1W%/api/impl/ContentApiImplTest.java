package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.impl;

import be.bosa.beapp.rest.common.logging.BeAppLogger;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Content;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.ContentService;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContentApiImplTest {

    private static final String ENDPOINT = "/content";
    private static final BeAppLogger LOGGER = BeAppLogger.create(ContentApiImplTest.class);

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private String basePath;

    @MockBean
    private ContentService contentService;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = basePath;
    }

    @Test
    public void getContent_() {
//        Content content = new Content();
//        when(contentService.getContent()).thenReturn(content);
//
//        io.restassured.response.Response response = given().when().get(ENDPOINT);
//
//        LOGGER.debug(response.prettyPrint());
//
//        response.then()
//                .assertThat().statusCode(Response.Status.OK.getStatusCode());

    }

    @Test
    public void postContent_() {

//        given().when()
//                .post(ENDPOINT)
//                .then()
//                .assertThat().statusCode(Response.Status.OK.getStatusCode());

    }

}
