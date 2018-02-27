package phg.com.automotiveoctoengine.controllers;

public class URLs {

    private static final String IP = "http://10.0.2.2";
    private static final int PORT = 7000;

    // localhost
    private static final String ROOT_URL =  IP + ":" + PORT + "/api/v0.1/";


    public static final String URL_LOGIN = ROOT_URL + "login";
    public static final String URL_HEALTH = ROOT_URL + "health";
    public static final String URL_USERS = ROOT_URL + "users";
    public static final String URL_CLASSIFIER = ROOT_URL + "classifier";
}
