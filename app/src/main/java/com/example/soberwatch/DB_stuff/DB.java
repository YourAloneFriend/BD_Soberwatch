package com.example.soberwatch.DB_stuff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


public class DB extends SQLiteOpenHelper {


    public DB(@Nullable Context context) {
        super(context, Consts.DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL(String.format("create Table %s(id INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
                Consts.BASE_USER_DATA_TABLE, Consts.FIRSTNAME, Consts.LASTNAME, Consts.PHONENUM));

        //MyDB.execSQL(String.format("create Table %s(id INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, FOREIGN KEY(%s) REFERENCES %s(id))",
        //        Consts.FAMILY_DOCTOR_TABLE, Consts.ID_BASE_USER_DATA, Consts.ID_BASE_USER_DATA, Consts.BASE_USER_DATA_TABLE));

        MyDB.execSQL(String.format("create Table %s(id INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s INT, %s TEXT unique NOT NULL, %s TEXT NOT NULL)," +
                        "FOREIGN KEY(%s) REFERENCES %s(id), FOREIGN KEY(%s) REFERENCES %s(id))",
                Consts.USER_INFO_TABLE, Consts.ID_BASE_USER_DATA, Consts.ID_FAMILY_DOCTOR, Consts.EMAIL, Consts.PASSWORD,
                Consts.ID_BASE_USER_DATA, Consts.BASE_USER_DATA_TABLE, Consts.ID_FAMILY_DOCTOR, Consts.BASE_USER_DATA_TABLE));

        MyDB.execSQL(String.format("create Table %s(id INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s TEXT NOT NULL, %s TEXT unique NOT NULL)," +
                        "FOREIGN KEY(%s) REFERENCES %s(id)",
                Consts.USER_CAR_TABLE, Consts.OWNER_ID, Consts.WHOLE_NAME, Consts.NUMBER, Consts.OWNER_ID, Consts.USER_INFO_TABLE));

        MyDB.execSQL(String.format("create Table %s(id INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s TEXT unique NOT NULL, %s TEXT NOT NULL)" +
                        "FOREIGN KEY(%s) REFERENCES %s(id)",
                Consts.USER_BANK_CARD_TABLE, Consts.USER_ID, Consts.CARD_NUM, Consts.CSV, Consts.USER_ID, Consts.USER_INFO_TABLE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL(String.format("drop Table if exists %s", Consts.BASE_USER_DATA_TABLE));
        MyDB.execSQL(String.format("drop Table if exists %s", Consts.USER_INFO_TABLE));
        MyDB.execSQL(String.format("drop Table if exists %s", Consts.USER_CAR_TABLE));
        MyDB.execSQL(String.format("drop Table if exists %s", Consts.USER_BANK_CARD_TABLE));
    }

    public Boolean signUpUser(User user)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Consts.FIRSTNAME, user.getFirstname());
        long result = MyDB.insert(Consts.BASE_USER_DATA_TABLE, null, contentValues);

        contentValues.clear();
        contentValues.put(Consts.ID_BASE_USER_DATA, result);
        contentValues.put(Consts.EMAIL, user.getEmail());
        contentValues.put(Consts.PASSWORD, user.getPassword());
        result = MyDB.insert(Consts.USER_INFO_TABLE, null, contentValues);

        return result != -1;
    }

    public String logInUser(User user)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT id FROM " +  Consts.USER_INFO_TABLE + " WHERE " + Consts.EMAIL + "=? AND " + Consts.PASSWORD + "=?",  new String[]{user.getEmail(), user.getPassword()});
        if (cursor.moveToFirst())
            return String.valueOf(cursor.getInt(0));
        cursor.close();

        return null;
    }


    // CarDB
    public List<Car> GetCarData(String id)
    {
        List<Car> result = new ArrayList<>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT " + Consts.WHOLE_NAME + "," + Consts.NUMBER + " FROM " +  Consts.USER_CAR_TABLE + " WHERE " + Consts.OWNER_ID + "=?",  new String[]{id});
        if (cursor.moveToFirst())
        {
            do
            {
                 result.add(new Car(String.valueOf(id), cursor.getString(1), cursor.getString(2)));
            } while(cursor.moveToNext());
        }

        MyDB.close();
        cursor.close();
        return result;
    }

    public boolean AddCarData(Car car)
    {
        //#INSERT INTO BD_Soberwatch.User_car(owner_id, Whole_name, Number) values (9, "Lada kalina", "AO 1477 KO");

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Consts.OWNER_ID, car.getOwner_id());
        contentValues.put(Consts.WHOLE_NAME, car.getWhole_name());
        contentValues.put(Consts.NUMBER, car.getNumber());

        long result = MyDB.insert(Consts.USER_CAR_TABLE, null, contentValues);
        return result != -1;

    }

    public void UpdateCarData(Car car)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("UPDATE " + Consts.USER_CAR_TABLE + " SET " + Consts.WHOLE_NAME + " = " + car.getWhole_name() + " WHERE " + Consts.NUMBER + "=" + car.getNumber() + ";");
        MyDB.close();
    }

    public String GetCarId(Car car)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT id FROM " +  Consts.USER_CAR_TABLE + " WHERE " + Consts.NUMBER + "=?;",  new String[]{car.getNumber()});
        if (cursor.moveToFirst())
        {
            return String.valueOf(cursor.getInt(0));
        }

        MyDB.close();
        cursor.close();
        return null;
    }

    // CardBD

    public List<Card> GetCardData(String id)
    {
        List<Card> result = new ArrayList<>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT " + Consts.CARD_NUM + "," + Consts.CSV + " FROM " +  Consts.USER_BANK_CARD_TABLE + " WHERE " + Consts.USER_ID + "=?",  new String[]{id});
        if (cursor.moveToFirst())
        {
            do
            {
                result.add(new Card(String.valueOf(id), cursor.getString(1), cursor.getString(2)));
            } while(cursor.moveToNext());
        }

        MyDB.close();
        cursor.close();
        return result;
    }

    public boolean AddCardData(Card card)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Consts.USER_ID, card.getUser_id());
        contentValues.put(Consts.CARD_NUM, card.getCard_num());
        contentValues.put(Consts.CSV, card.getCsv());

        long result = MyDB.insert(Consts.USER_BANK_CARD_TABLE, null, contentValues);
        return result != -1;
    }

    public void UpdateCardData(Card card)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("UPDATE " + Consts.USER_BANK_CARD_TABLE + " SET " + Consts.CARD_NUM + " = " + card.getCard_num() +
                " WHERE " + Consts.USER_ID + " = " + card.getUser_id() + " AND " + Consts.CSV + " = " + card.getCsv() + ";");
        MyDB.close();
    }

    public String GetCardId(Card card)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT id FROM " +  Consts.USER_CAR_TABLE + " WHERE " +
                Consts.USER_ID + "=? AND " + Consts.CSV + "=?;" ,  new String[]{card.getUser_id(), card.getCsv()});
        if (cursor.moveToFirst())
        {
            return String.valueOf(cursor.getInt(0));
        }

        MyDB.close();
        cursor.close();
        return null;
    }

    // DocBD

    public FamilyDoctor GetDocData(String doc_id)
    {
        FamilyDoctor doc = null;
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT " + Consts.FIRSTNAME + "," + Consts.LASTNAME + "," +  Consts.PHONENUM + " FROM "
                + Consts.BASE_USER_DATA_TABLE + " WHERE id=?;",  new String[]{doc_id});
        if (cursor.moveToFirst())
        {
            doc = new FamilyDoctor(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        MyDB.close();
        cursor.close();
        return doc;
    }

    public void SetDocToUser(String id, FamilyDoctor doc)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Consts.FIRSTNAME, doc.getFirstname());
        contentValues.put(Consts.LASTNAME, doc.getLastname());
        contentValues.put(Consts.PHONENUM, doc.getPhonenum());
        long result = MyDB.insert(Consts.BASE_USER_DATA_TABLE, null, contentValues);

        MyDB.execSQL("UPDATE " + Consts.USER_INFO_TABLE + " SET " + Consts.ID_FAMILY_DOCTOR + " = " + result +
                " WHERE id = " + id + ";");
        MyDB.close();
    }

    public String GetDocId(String id)
    {
        String doc_id = null;
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT " + Consts.ID_FAMILY_DOCTOR + " FROM "
                + Consts.USER_INFO_TABLE + " WHERE id=?;",  new String[]{id});
        if (cursor.moveToFirst())
        {
            doc_id = cursor.getString(0);
        }

        MyDB.close();
        cursor.close();
        return doc_id;
    }

    //Delete record

    public boolean RemoveData(String id, String table)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("DELETE FROM " + table + " WHERE id=?;", new String[]{id} );

        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }
}