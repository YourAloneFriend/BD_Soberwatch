package com.example.soberwatch.DB_stuff;

import java.util.regex.Pattern;

public class User {
    private static String Firstname;
    private static String id, email, password;

    public User(String Firstname, String email, String password)
    {
        this.Firstname = Firstname;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }


    public static String getFirstname() {
        return Firstname;
    }

    public static String getId() {
        return id;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }


    public static void setId(String id) {
        User.id = id;
    }

}
