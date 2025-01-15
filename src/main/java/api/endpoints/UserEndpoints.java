package api.endpoints;

import api.payload.PropertyPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserEndpoints {

    public static Response createUser(PropertyPayload payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
        .when()
                .post(Routes.properties_endpoint);
        return response;
    }

    public static Response readUser() {
        Response response = given()
                .when()
                .post(Routes.properties_endpoint);
        return response;
    }
}
