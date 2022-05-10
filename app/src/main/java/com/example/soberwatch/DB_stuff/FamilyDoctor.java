package com.example.soberwatch.DB_stuff;

import java.util.regex.Pattern;

public class FamilyDoctor {

    private static String Firstname, Lastname, phonenum;

    public FamilyDoctor( String Firstname, String  Lastname, String phonenum) {
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.phonenum = phonenum;
    }

    public static String getFirstname() {
        return Firstname;
    }

    public static void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public static String getLastname() {
        return Lastname;
    }

    public static void setLastname(String lastname) {
        Lastname = lastname;
    }

    public static String getPhonenum() {
        return phonenum;
    }

    public static void setPhonenum(String phonenum) {
        FamilyDoctor.phonenum = phonenum;
    }

    public static boolean is_valid_phonenum(String phonenum)
    {
        if(Pattern.matches("\\+380\\d{9}", phonenum))
            return true;
        return false;
    }

    public static boolean is_valid_firstname(String firstname)
    {
        if(Pattern.matches("[A-Z][a-z]{2,}]", firstname))
            return true;
        return false;
    }

    public static boolean is_valid_lastname(String lastname)
    {
        return is_valid_firstname(lastname);
    }
}
