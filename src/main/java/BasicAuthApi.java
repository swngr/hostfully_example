

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BasicAuthApi {

    private static final String BASE_URL = "https://qa-assessment.svc.hostfully.com";
    private static final String USERNAME = "candidate@hostfully.com";
    private static final String PASSWORD = "NaX5k1wFadtkFf";


    public BasicAuthApi() {
        RestAssured.baseURI = BASE_URL;
    }

    // Method to perform GET request with Basic Authentication
    public Response getWithBasicAuth(String endpoint) {
        return RestAssured
                .given()
                .auth()
                .preemptive()
                .basic(USERNAME, PASSWORD)
                .when()
                .get(endpoint);
    }



}
