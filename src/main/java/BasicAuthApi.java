
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;

public class BasicAuthApi {

    private static final String BASE_URL = "https://qa-assessment.svc.hostfully.com";
    private static final String USERNAME = "candidate@hostfully.com";
    private static final String PASSWORD = "NaX5k1wFadtkFf";


    public BasicAuthApi() {
        RestAssured.baseURI = BASE_URL;
    }

    // Method to configure Basic Authentication
    public void configureBasicAuth() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(USERNAME);
        authScheme.setPassword(PASSWORD);
        RestAssured.authentication = authScheme;
    }

}
