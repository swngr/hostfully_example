import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class PropertiesTest {

    private BasicAuthApi api;

    @BeforeClass
    public void setup() {
        api = new BasicAuthApi();
    }


    @Test
    public void testGetWithBasicAuth_Success() {
        String endpoint = "/properties"; // Replace with your actual endpoint

        Response response = api.getWithBasicAuth(endpoint);

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Add additional assertions as necessary
        Assert.assertNotNull(response.getBody(), "Response body should not be null");
    }

    @Test
    public void testGetWithBasicAuth_Unauthorized() {
        // Override credentials for testing unauthorized scenario
        Response response = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("wrong_username", "wrong_password")
                .when()
                .get("/properties"); // Replace with your actual endpoint

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401");
    }

//
//    }
//
//    @AfterSuite
//    void tearDown() {
//        System.out.println("TearDown");
//    }
//


}
