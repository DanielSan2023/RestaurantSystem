package com.engeto;

public class Settings {

    private static final String fileItemDelimiterValue = "\t";
    private static final String fileForDishes = "listDishes.txt";
    private static final String fileForOrders = "orders.txt";
    private static final String DEFAULT_IMAGE_VALUE = "blank";

    public static String fileItemDelimiter() {
        return fileItemDelimiterValue;
    }

    public static String fileDishesForLoad() {
        return fileForDishes;
    }

    public static String fileDishesForSave() {
        return fileForDishes;
    }

    public static String fileOrdersForLoad() {
        return fileForOrders;
    }

    public static String fileOrdersForSave() {
        return fileForOrders;
    }

    public static String defaultImageValue(){return DEFAULT_IMAGE_VALUE;}

}
