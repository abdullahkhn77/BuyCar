package com.example.buycar;
import android.provider.BaseColumns;


public class CarContract {
    private CarContract() {}

    public static class CarEntry implements BaseColumns {
        public static final String TABLE_NAME = "cars";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";

        public static final String COLUMN_PHOTO = "photo"; // New column for photo
    }
}
