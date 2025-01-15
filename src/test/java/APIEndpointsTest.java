
import api.endpoints.Routes;
import api.payload.PropertyPayload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.Headers;


import java.util.Arrays;

import static io.restassured.RestAssured.given;


public class APIEndpointsTest {

    PropertyPayload propertyPayload;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Routes.base_url;
        propertyPayload = new PropertyPayload();
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
    public void testPostProperties_Valid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();

        // Define request body using POJO class
        PropertyPayload requestBody = new PropertyPayload();

        requestBody.setId("f981cd14-e0b5-41bd-a612-d53cf8aa1111");
        requestBody.setAlias("sng_testprop25");
        requestBody.setCountryCode("1");
        requestBody.setCreatedAt(Arrays.asList(2024, 1, 14, 20, 3, 7, 253493000L));

        Response response = RestAssured
                .given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody) // Serialize POJO to JSON
                .when()
                .post(Routes.properties_endpoint);

        // Log the response
        response.then().log().all();

        // Assert that the status code is 201
        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not as expected!");
    }

    /**
     * Validate short Id number
     */
    @Test
    public void testPostProperties_shortId_Invalid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        String requestBody = "{\n" +
                "\"id\": \"f981cd14-e0b5-41bd-a612-\",\n" +
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
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Validate duplicated Alias
     */
    @Test
    public void testPostProperties_duplicatedAlias_Invalid() {
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
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Validate empty Country Code
     */
    @Test
    public void testPostProperties_EmptyCountryCode_Invalid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        String requestBody = "{\n" +
                "\"id\": \"f981cd14-e0b5-41bd-a612-d53cf8aa1111\",\n" +
                "        \"alias\": \"sng_testprop25\",\n" +
                "        \"countryCode\": \"\",\n" +
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
        Assert.assertEquals(response.getStatusCode(), 400, "Country Code should not be empty. Status code should be 400");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Validate empty Created At date
     */
    @Test
    public void testPostProperties_EmptyCreatedAt_Invalid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        String requestBody = "{\n" +
                "\"id\": \"f981cd14-e0b5-41bd-a612-d53cf8aa1111\",\n" +
                "        \"alias\": \"sng_testprop26\",\n" +
                "        \"countryCode\": 1,\n" +
                "        \"createdAt\": [] \n" +
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
        Assert.assertEquals(response.getStatusCode(), 400, "Crated At: should not be empty. Status code should be 400");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Validate empty body
     */
    @Test
    public void testPostProperties_EmptyBody_Invalid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        String requestBody = "{\n" +
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
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");
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


    /**
     * Property Retrieval
     */
    @Test
    public void storePropertyData() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();

        // Define request body using POJO class
        PropertyPayload requestBody = new PropertyPayload();

        requestBody.setId("f981cd14-e0b5-41bd-a612-d53cf8aa1111");
        requestBody.setAlias("sng_testprop25");
        requestBody.setCountryCode("1");
        requestBody.setCreatedAt(Arrays.asList(2024, 1, 14, 20, 3, 7, 253493000L));

        Response response = RestAssured
                .given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody) // Serialize POJO to JSON
                .when()
                .post(Routes.properties_endpoint);

        // Log the response
        response.then().log().all();

        // Assert that the status code is 201
        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not as expected!");

        // Assert specific field values in the response body
        String propertyId = response.jsonPath().getString("id");
        String propertyAlias = response.jsonPath().getString("alias");

        Assert.assertNotEquals(propertyId, "", "id should not be empty");
        Assert.assertNotNull(propertyId, "id should not be null");
        Assert.assertNotEquals(propertyAlias, "", "alias should not be empty");
        Assert.assertNotNull(propertyAlias, "alias should not be null");

        System.out.println("Response Alias: " + propertyAlias);
    }

}
