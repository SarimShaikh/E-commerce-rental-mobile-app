package com.ecommerce.app.customURL;

public class URLS {

    private static final String BASE_URL_1 = "http://192.168.100.8:8081/api/v1/";
    private static final String BASE_URL_2 = "http://192.168.100.8:8082/api/v1/";
    private static final String BASE_URL_3 = "http://192.168.100.8:8083/api/v1/";
    //urls for authentication modules
    public static final String URL_REGISTER = BASE_URL_1 + "auth/mob-signup";
    public static final String URL_LOGIN = BASE_URL_1 + "auth/signin";

    // urls for stores/inventory modules
    public static final String URL_GET_STORES = BASE_URL_2 + "inventory/get-stores";
    public static final String URL_GET_STORE_ITEMS = BASE_URL_2 + "inventory/get-store-items";
    public static final String URL_GET_CATEGORIES = BASE_URL_2 + "inventory/get-mob-categories";

    public static final String URL_CHECK_AVAILABLE_QUANTITY = BASE_URL_3 + "sales/check-quantity";
    public static final String URL_PLACE_ORDER = BASE_URL_3 + "sales/place-order";
    public static final String URL_GET_RENTAL_ITEMS = BASE_URL_3 + "sales/user-rental-items/";
}
