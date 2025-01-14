package api.endpoints;

import api.payload.Property;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserEndpoints {

    public static Response createUser(Property payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
        .when()
                .post(Routes.properties_post_url);
        return response;
    }

    public static Response readUser() {
        Response response = given()
                .when()
                .post(Routes.properties_get_url);
        return response;
    }
}
