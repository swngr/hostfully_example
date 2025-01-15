
import api.endpoints.Routes;
import api.payload.BookingPayload;
import api.payload.Guest;
import api.payload.PropertyPayload;
import helpers.BasicAuthentication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.Headers;


import java.util.Arrays;
import java.util.UUID;

import static io.restassured.RestAssured.given;


public class APIEndpointsTest {

    PropertyPayload propertyPayload;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Routes.base_url;
        propertyPayload = new PropertyPayload();
    }

    /**
     * Unable to access GET property endpoint due to wrong credentials
     */
    @Test
    public void testGetProperty_Unauthorized() {
        String username = "wrong_username";
        String password = "wrong_password";
        // Get authentication headers
        //Method overloading
        Headers headers = BasicAuthentication.getBasicAuthHeaders(username, password);
        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get(Routes.properties_endpoint)
                .then()
                .extract()
                .response();
        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Unable to access POST property endpoint due to wrong credentials
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
                .get(Routes.properties_endpoint)
                .then()
                .extract()
                .response();
        response.then().log().all();
        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Unable to access GET bookings endpoint due to wrong credentials
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
                .get(Routes.bookings_endpoint)
                .then()
                .extract()
                .response();
        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Unable to access POST bookings endpoint due to wrong credentials
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
                .get(Routes.bookings_endpoint)
                .then()
                .extract()
                .response();
        response.then().log().all();
        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Successful access to GET property endpoint
     */
    @Test
    public void testGetProperties() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get(Routes.properties_endpoint)
                .then()
                .extract()
                .response();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Functional Tests
     * Property Creation
     * Successful access to POST property endpoint
     */
    @Test
    public void testPostProperties_Valid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body using POJO class
        PropertyPayload requestBody = new PropertyPayload();
        requestBody.setId(UUID.randomUUID().toString());
        requestBody.setAlias("sng_testprop27");
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
     * Negative test case: short property Id number
     */
    @Test
    public void testPostProperties_shortId_Invalid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        PropertyPayload requestBody = new PropertyPayload();
        requestBody.setId("f981cd14-e0b5-41bd-a612-");
        requestBody.setAlias("sng_testprop27");
        requestBody.setCountryCode("1");
        requestBody.setCreatedAt(Arrays.asList(2024, 1, 14, 20, 3, 7, 253493000L));
        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(Routes.properties_endpoint);
        response.then().log().all();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Negative test case: duplicated Alias
     */
    @Test
    public void testPostProperties_duplicatedAlias_Invalid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        PropertyPayload requestBody = new PropertyPayload();
        requestBody.setId(UUID.randomUUID().toString());
        requestBody.setAlias("sng_testprop15");
        requestBody.setCountryCode("1");
        requestBody.setCreatedAt(Arrays.asList(2024, 1, 14, 20, 3, 7, 253493000L));
        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(Routes.properties_endpoint);
        response.then().log().all();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 500, "Status code should be 500");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Negative test case: empty Country Code
     */
    @Test
    public void testPostProperties_EmptyCountryCode_Invalid() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        PropertyPayload requestBody = new PropertyPayload();
        requestBody.setId(UUID.randomUUID().toString());
        requestBody.setAlias("sng_testprop30");
        requestBody.setCountryCode(null);
        requestBody.setCreatedAt(Arrays.asList(2024, 1, 14, 20, 3, 7, 253493000L));
        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(Routes.properties_endpoint);
        response.then().log().all();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 400, "Country Code should not be empty. Status code not as expected");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Negative test case: Created At date in the future
     */
    @Test
    public void testPostProperties_EmptyCreatedAt_Invalid() {

        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Define request body
        PropertyPayload requestBody = new PropertyPayload();
        requestBody.setId(UUID.randomUUID().toString());
        requestBody.setAlias("sng_testprop27");
        requestBody.setCountryCode(null);
        requestBody.setCreatedAt(Arrays.asList(2035, 1, 14, 20, 3, 7, 253493000L));
        Response response = given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(Routes.properties_endpoint);
        response.then().log().all();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 400, "Crated At date: should not be in the future. Status code is not as expected");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Negative test case: empty body
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
                .post(Routes.properties_endpoint);
        response.then().log().all();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");
        System.out.println("POST Response: " + response.asString());
    }

    /**
     * Successful access to GET bookings endpoint
     */
    @Test
    public void testGetBookings() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get(Routes.bookings_endpoint)
                .then()
                .extract()
                .response();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        System.out.println("GET Response: " + response.asString());
    }

    /**
     * Functional Tests
     * Booking creation -> SUCCESSFUL
     */
    @Test
    public void testPostBookings() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Creating the request body
        BookingPayload bookingPayload = new BookingPayload();
        bookingPayload.setId(UUID.randomUUID().toString());
        bookingPayload.setStartDate("2023-01-02");
        bookingPayload.setEndDate("2023-01-02");
        bookingPayload.setStatus("SCHEDULED");
        bookingPayload.setPropertyId("ff288cc0-c049-4bc4-a96d-01637466bed4"); //-->sng_testprop15
        // Creating the guest object
        Guest guest = new Guest();
        guest.setFirstName("Sw");
        guest.setLastName("Ngr");
        guest.setDateOfBirth("2025-01-14");
        // Setting the guest object in the request
        bookingPayload.setGuest(guest);
        Response response = RestAssured
                .given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bookingPayload) // Serialize POJO to JSON
                .when()
                .post(Routes.bookings_endpoint);
        // Log the response
        response.then().log().all();
        // Assert that the status code is 201
        Assert.assertEquals(response.getStatusCode(), 201, "Status code is not as expected!");
    }

    /**
     * Functional Tests
     * Property Retrieval
     */
    @Test
    public void getPropertyData() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Perform GET request
        Response response = given()
                .headers(headers)
                .when()
                .get(Routes.properties_endpoint + "/ff288cc0-c049-4bc4-a96d-01637466bed4")
                .then()
                .extract()
                .response();
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        System.out.println("GET Response: " + response.asString());
        //Assert specific field values in the response body
        String propertyId = response.jsonPath().getString("id");
        String propertyAlias = response.jsonPath().getString("alias");
        Assert.assertNotEquals(propertyId, "", "id should not be empty");
        Assert.assertNotNull(propertyId, "id should not be null");
        Assert.assertNotEquals(propertyAlias, "", "alias should not be empty");
        Assert.assertNotNull(propertyAlias, "alias should not be null");

        System.out.println("Response id: " + propertyId);
        System.out.println("Response Alias: " + propertyAlias);
    }

    /**
     * Functional Tests
     * Booking creation -> UNSUCCESSFUL due to overlapped days
     */
    @Test
    public void testPostBookings_Overlap() {
        // Get authentication headers
        Headers headers = BasicAuthentication.getBasicAuthHeaders();
        // Creating the request body
        BookingPayload bookingPayload = new BookingPayload();
        bookingPayload.setId(UUID.randomUUID().toString());
        bookingPayload.setStartDate("2023-01-02");
        bookingPayload.setEndDate("2023-01-02");
        bookingPayload.setStatus("SCHEDULED");
        bookingPayload.setPropertyId("ff288cc0-c049-4bc4-a96d-01637466bed4"); //-->sng_testprop15
        // Creating the guest object
        Guest guest = new Guest();
        guest.setFirstName("Sw");
        guest.setLastName("Ngr");
        guest.setDateOfBirth("2025-01-14");
        // Setting the guest object in the request
        bookingPayload.setGuest(guest);
        Response response = RestAssured
                .given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bookingPayload) // Serialize POJO to JSON
                .when()
                .post(Routes.bookings_endpoint);
        // Log the response
        response.then().log().all();
        // Assert that the status code is 201
        Assert.assertEquals(response.getStatusCode(), 422, "Status code is not as expected!");
    }

}
