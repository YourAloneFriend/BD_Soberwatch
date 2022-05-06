package com.example.soberwatch.DB_stuff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import com.example.soberwatch.DB_stuff.Consts;


public class DB extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }

    public void signUpUser(User user)
    {
        String last_id = "";
        String insert_base_user_data = "insert into " + Consts.BASE_USER_DATA_TABLE + "(" + Consts.FIRSTNAME + ") values (?);";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert_base_user_data, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, user.getFirstname());
            prSt.executeUpdate();
            ResultSet rs = prSt.getGeneratedKeys();
            if(rs.next())
                last_id = rs.getString(1);
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        String insert_user_info = "insert into " + Consts.USER_INFO_TABLE + "(" + Consts.EMAIL + "," + Consts.PASSWORD + "," + Consts.ID_BASE_USER_DATA +  ") values (?,?,?);";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert_user_info);
            prSt.setString(1, user.getEmail());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, last_id);
            prSt.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public ResultSet logInUser(User user)
    {
        ResultSet resSet = null;

        String select_user_info = "select * from " +
                Consts.USER_INFO_TABLE + " LEFT JOIN " + Consts.BASE_USER_DATA_TABLE +
                " ON " + Consts.USER_INFO_TABLE + "." + Consts.ID_USER_INFO + " = " + Consts.BASE_USER_DATA_TABLE + "." + Consts.ID_BASE_USER_DATA +
                " WHERE " + Consts.EMAIL + "=? AND " + Consts.PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, user.getEmail());
            prSt.setString(2, user.getPassword());
            resSet = prSt.executeQuery();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return resSet;
    }

    public ResultSet GetCarData(String id)
    {
        ResultSet resSet = null;

        String select_user_info = "select * from " +
                Consts.USER_INFO_TABLE + " LEFT JOIN " + Consts.USER_CAR_TABLE +
                " ON " + Consts.USER_INFO_TABLE + "." + Consts.ID_USER_INFO + " = " + Consts.USER_CAR_TABLE + "." + Consts.OWNER_ID +
                " WHERE " + Consts.OWNER_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, id);
            resSet = prSt.executeQuery();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return resSet;
    }

    public void AddCarData(String id, String whole_name, String number)
    {
        //#INSERT INTO BD_Soberwatch.User_car(owner_id, Whole_name, Number) values (9, "Lada kalina", "AO 1477 KO");

        String select_user_info = "insert into " + Consts.USER_CAR_TABLE +
                "(" + Consts.OWNER_ID + "," + Consts.WHOLE_NAME + "," + Consts.NUMBER + ") values (?,?,?);";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, id);
            prSt.setString(2, whole_name);
            prSt.setString(3, number);
           prSt.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void UpdateCarData(String id, String whole_name)
    {
        String select_user_info = "update " + Consts.USER_CAR_TABLE +
                " set " + Consts.WHOLE_NAME + "=? where id=?;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, whole_name);
            prSt.setString(2, id);
            prSt.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public String GetCarId(String id, String number)
    {
        String car_id = "";
        String select_user_info = "select id from " + Consts.USER_CAR_TABLE +
                " where " + Consts.OWNER_ID + "=?, " + Consts.NUMBER + "=?;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, id);
            prSt.setString(2, number);
            ResultSet resSet = prSt.executeQuery();
            car_id = resSet.getString("id");
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return car_id;
    }














    public ResultSet GetCardData(String id)
    {
        ResultSet resSet = null;

        String select_user_info = "select " + Consts.CARD_NUM + "," + Consts.CSV + " from " +
                Consts.USER_INFO_TABLE + " inner join " + Consts.USER_BANK_CARD_TABLE +
                " on " + Consts.USER_INFO_TABLE + "." + Consts.ID_USER_INFO + " = " + Consts.USER_BANK_CARD_TABLE + "." + Consts.USER_ID +
                " where " + Consts.USER_ID + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, id);
            resSet = prSt.executeQuery();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return resSet;
    }

    public void AddCardData(String id, String card_num, String csv)
    {
        //#INSERT INTO BD_Soberwatch.User_car(owner_id, Whole_name, Number) values (9, "Lada kalina", "AO 1477 KO");

        String select_user_info = "insert into " + Consts.USER_BANK_CARD_TABLE +
                "(" + Consts.USER_ID + "," + Consts.CARD_NUM + "," + Consts.CSV + ") values (?,?,?);";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, id);
            prSt.setString(2, card_num);
            prSt.setString(3, csv);
            prSt.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void UpdateCardData(String id, String card_num)
    {
        String select_user_info = "update " + Consts.USER_BANK_CARD_TABLE +
                " set " + Consts.CARD_NUM + "=? where id=?;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, card_num);
            prSt.setString(2, id);
            prSt.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public String GetCardId(String id, String number)
    {
        String card_id = "";
        String select_user_info = "select id from " + Consts.USER_BANK_CARD_TABLE +
                " where " + Consts.CARD_NUM + "=?, " + Consts.CSV + "=?;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, id);
            prSt.setString(2, number);
            ResultSet resSet = prSt.executeQuery();
            card_id = resSet.getString("id");
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return card_id;
    }


























    public ResultSet GetDocData(String id)
    {
/*        SELECT BD_Soberwatch.Base_user_data.id FROM BD_Soberwatch.User_info
        INNER JOIN BD_Soberwatch.Family_doctor ON BD_Soberwatch.User_info.id_family_doctor = BD_Soberwatch.Family_doctor.id
        INNER JOIN BD_Soberwatch.Base_user_data ON BD_Soberwatch.Family_doctor.id_base_user_data =
            BD_Soberwatch.Base_user_data.id WHERE BD_Soberwatch.User_info.id = 9;*/

        ResultSet resSet = null;

        String select_doc_data = "select " + Consts.FIRSTNAME + "," + Consts.LASTNAME + "," + Consts.PHONENUM
                + " from " + Consts.USER_INFO_TABLE + " inner join "
                + Consts.FAMILY_DOCTOR_TABLE + " on " + Consts.USER_INFO_TABLE + "." + Consts.ID_FAMILY_DOCTOR + " = "
                + Consts.FAMILY_DOCTOR_TABLE + ".id inner join " + Consts.BASE_USER_DATA_TABLE + " on "
                + Consts.USER_INFO_TABLE + "." + Consts.ID_BASE_USER_DATA + " = " + Consts.BASE_USER_DATA_TABLE + ".id where "
                + Consts.BASE_USER_DATA_TABLE + ".id = ?;";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_doc_data);
            prSt.setString(1, id);
            resSet = prSt.executeQuery();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return resSet;
    }

    public void SetDocToUser(String id, String id_family_doc)
    {
        String select_user_info = "update " + Consts.USER_INFO_TABLE +
                " set " + Consts.ID_FAMILY_DOCTOR + "=? where id=?;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, id_family_doc);
            prSt.setString(2, id);
            prSt.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public String GetDocId(String number)
    {
        String doc_id = "";
        String select_user_info = "select " + Consts.FAMILY_DOCTOR_TABLE + ".id from " + Consts.FAMILY_DOCTOR_TABLE
                + " inner join " + Consts.BASE_USER_DATA_TABLE + " on " + Consts.ID_BASE_USER_DATA + " = "
                + Consts.BASE_USER_DATA_TABLE + ".id where " + Consts.PHONENUM + "=?;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select_user_info);
            prSt.setString(1, number);
            ResultSet resSet = prSt.executeQuery();
            doc_id = resSet.getString("id");
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return doc_id;
    }






    public void RemoveData(String id, String table)
    {

        String delete_info = "delete from " + table + " where id=?;";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete_info);
            prSt.setString(1, id);
            prSt.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

}