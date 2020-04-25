package com.example.taskforu;

import android.provider.BaseColumns;

public class Database {
    public static final String  DATABASE_NAME = "com.example.taskforu.db";
    public static final int DB_VERSION = 3;
    public class DbColumns implements BaseColumns {

        public static final String TABLE_NAME = "tasks";
        public static final String ID = "ID";
        public static final String TITLE = "task_title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String CATEGORY = "category";



    }


}
