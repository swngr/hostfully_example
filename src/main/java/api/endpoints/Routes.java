package api.endpoints;

public class Routes {

    public static String base_url = "https://qa-assessment.svc.hostfully.com";

    //properties
    public static String properties_get_url = base_url + "/properties";
    public static String properties_post_url = base_url + "/properties/{username}";

    //booking
    public static String booking_get_url = base_url + "/bookings";
    public static String booking_post_url = base_url + "/bookings/{username}";


}
