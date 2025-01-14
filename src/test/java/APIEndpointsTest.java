
import api.payload.Property;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.Headers;

import static io.restassured.RestAssured.given;


public class APIEndpointsTest {

    Property propertyPayload;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://qa-assessment.svc.hostfully.com";
        propertyPayload = new Property();
    }


    /**
     * Validate that it's not possible to use GET property endpoint with wrong credentials
     */
    @Test
    public void testGetProperty_Unauthorized() {
        String username = "wrong_username";
        String password = "wrong_password";

        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders(username, password);

        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get("/properties")
                .then()
                .extract()
                .response();

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Validate that it's not possible to use POST property endpoint with wrong credentials
     */
    @Test
    public void testPostProperty_Unauthorized() {
        String username = "wrong_username";
        String password = "wrong_password";

        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders(username, password);

        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get("/properties")
                .then()
                .extract()
                .response();

        response.then().log().all();

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Validate that it's not possible to use GET bookings endpoint with wrong credentials
     */
    @Test
    public void testGetBookings_Unauthorized() {
        String username = "wrong_username";
        String password = "wrong_password";

        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders(username, password);

        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get("/bookings")
                .then()
                .extract()
                .response();

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Validate that it's not possible to use POST bookings endpoint with wrong credentials
     */
    @Test
    public void testPostBookings_Unauthorized() {
        String username = "wrong_username";
        String password = "wrong_password";

        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders(username, password);

        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get("/bookings")
                .then()
                .extract()
                .response();

        response.then().log().all();

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Validate that GET property endpoint is working fine
     */
    @Test
    public void testGetProperties() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();

        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get("/properties")
                .then()
                .extract()
                .response();


        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Validate that POST property endpoint is working fine
     */
    @Test
    public void testPostProperties_ValidInput() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();

        // Define request body
        String requestBody = "{\n" +
                "\"id\": \"f981cd14-e0b5-41bd-a612-d53cf8aa1111\",\n" +
                "        \"alias\": \"sng_testprop17\",\n" +
                "        \"countryCode\": 1,\n" +
                "        \"createdAt\": [\n" +
                "            2024,\n" +
                "            1,\n" +
                "            13,\n" +
                "            17,\n" +
                "            47,\n" +
                "            16,\n" +
                "            858649000\n" +
                "        ] \n" +
                "}";

        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/properties");

        response.then().log().all();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");
        System.out.println("POST Response: " + response.asString());

    }

    /**
     * Validate that GET bookings endpoint is working fine
     */
    @Test
    public void testGetBookings() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();

        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get("/bookings")
                .then()
                .extract()
                .response();


        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Validate that POST bookings endpoint is working fine
     */
    @Test
    public void testPostBookings() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();

        // Define request body
        String requestBody = "{\n" +
                "\"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                "  \"startDate\": \"2024-01-15\",\n" +
                "  \"endDate\": \"2024-01-15\",\n" +
                "  \"status\": \"SCHEDULED\",\n" +
                "  \"guest\": {\n" +
                "    \"firstName\": \"swngr\",\n" +
                "    \"lastName\": \"swngrLast\",\n" +
                "    \"dateOfBirth\": \"2025-01-14\"\n" +
                "  },\n" +
                "  \"propertyId\": \"ff288cc0-c049-4bc4-a96d-01637466bed4\"\n" +
                "}";

        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/bookings");

        response.then().log().all();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");
        System.out.println("POST Response: " + response.asString());
    }



//    }
//
//    @AfterSuite
//    void tearDown() {
//        System.out.println("TearDown");
//    }
//


}
