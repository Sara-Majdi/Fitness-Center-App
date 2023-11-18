package com.example.testing;

import static com.example.testing.DatabaseHelper.Announcement_Table;
import static com.example.testing.DatabaseHelper.Booking_Table;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager (Context ctx) {context = ctx;}

    public DatabaseManager open () throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public boolean createNewAccount(String username, String password, String status, String registerDate, String role) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawQuery = "INSERT INTO " + DatabaseHelper.Account_Table + " (username, password, status, registerDate, role, hasProfile) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = db.compileStatement(rawQuery);
        statement.bindString(1, username);
        statement.bindString(2, password);
        statement.bindString(3, status);
        statement.bindString(4, registerDate);
        statement.bindString(5, role);
        statement.bindString(6, "NO");

        try {
            long rowId = statement.executeInsert();

            statement.close();
            db.close();

            return rowId != -1;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }

    public boolean insertFirstTable (){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int input = 1;

        String rawQuery = "INSERT INTO " + DatabaseHelper.First_Table +
                " (input) " +
                "VALUES (?)";

        SQLiteStatement statement = db.compileStatement(rawQuery);
        statement.bindLong(1, input);

        try {
            long rowId = statement.executeInsert();
            statement.close();
            db.close();
            return rowId != -1;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }

    public int getFirstTableInput() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String rawQuery = "SELECT * " +
                " FROM " + DatabaseHelper.First_Table +
                " WHERE firstID = 1;";

        Cursor cursor = db.rawQuery(rawQuery, null);

        int input = -1;

        if (cursor != null && cursor.moveToFirst()) {
            input = cursor.getInt(cursor.getColumnIndexOrThrow("input"));
            cursor.close();
        }

        db.close();
        return input;
    }

    public boolean createProfile (int account_DBID, String name, String gender, String email,
                                 String contact_number, int age, double weight, double height,
                                 double BMI, String achievements) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawQuery = "INSERT INTO " + DatabaseHelper.Profile_Table +
                " (account_DBPID, name, gender, email, contact_number, age, weight, height, BMI, achievements) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = db.compileStatement(rawQuery);
        statement.bindLong(1, account_DBID);
        statement.bindString(2, name);
        statement.bindString(3, gender);
        statement.bindString(4, email);
        statement.bindString(5, contact_number);
        statement.bindLong(6, age);
        statement.bindDouble(7, weight);
        statement.bindDouble(8, height);
        statement.bindDouble(9, BMI);
        statement.bindString(10, achievements);

        try {
            long rowId = statement.executeInsert();
            statement.close();
            db.close();
            return rowId != -1;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }

    public boolean updateAccountHasProfile(int account_DBID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String rawQuery = "UPDATE " + DatabaseHelper.Account_Table +
                " SET hasProfile = ? WHERE account_DBID = ?";

        SQLiteStatement statement = db.compileStatement(rawQuery);
        statement.bindString(1, "YES");
        statement.bindLong(2, account_DBID);

        try {
            long rowId = statement.executeInsert();
            statement.close();
            db.close();
            return rowId != -1;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }

    public boolean updateProfile(int account_DBID, String name, String gender, String email,
                                 String contact_number, int age, double weight, double height,
                                 double BMI, String achievements) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String updateQuery = "UPDATE " + DatabaseHelper.Profile_Table +
                " SET name = ?, gender = ?, email = ?, contact_number = ?, age = ?, weight = ?, height = ?, BMI = ?, achievements = ? " +
                " WHERE account_DBPID = ?";

        SQLiteStatement statement = db.compileStatement(updateQuery);
        statement.bindString(1, name);
        statement.bindString(2, gender);
        statement.bindString(3, email);
        statement.bindString(4, contact_number);
        statement.bindLong(5, age);
        statement.bindDouble(6, weight);
        statement.bindDouble(7, height);
        statement.bindDouble(8, BMI);
        statement.bindString(9, achievements);
        statement.bindLong(10, account_DBID);

        try {
            int rowCount = statement.executeUpdateDelete();
            statement.close();
            db.close();
            return rowCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }

    public int getProfileDBID(int accountDBID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String rawQuery = "SELECT profile_DBID " +
                " FROM " + DatabaseHelper.Profile_Table +
                " WHERE account_DBPID = ?;";

        String[] selectionArgs = {String.valueOf(accountDBID)};

        Cursor cursor = db.rawQuery(rawQuery, selectionArgs);

        int profileDBID = -1;

        if (cursor != null && cursor.moveToFirst()) {
            profileDBID = cursor.getInt(cursor.getColumnIndexOrThrow("profile_DBID"));
            cursor.close();
        }

        db.close();

        return profileDBID;
    }

    @SuppressLint("Range")
    public ArrayList<User> getAllTrainers() {
        ArrayList<User> trainers = new ArrayList<>();
        User trainer;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT p.name, p.achievements, p.image_path " +
                        "FROM Profiles p " +
                        "JOIN Accounts a ON p.account_DBPID = a.account_DBID " +
                        "WHERE a.role = 'TRAINER';";


        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String achievements = cursor.getString(cursor.getColumnIndex("achievements"));
                String imagePath = cursor.getString(cursor.getColumnIndex("image_path"));

                // Create a User object with the retrieved data
                trainer = new User();
                trainer.setName(name);
                trainer.setAchievements(achievements);
                trainer.setImagePath(imagePath);
                trainers.add(trainer);
            }

            cursor.close();
        }

        return trainers;
    }


    public long updateImagePath(int profileDBID, String imagePath) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String updateQuery = "UPDATE " + DatabaseHelper.Profile_Table +
                " SET image_path = ?" +
                " WHERE profile_DBID = ?;";

        SQLiteStatement statement = db.compileStatement(updateQuery);
        statement.bindString(1, imagePath);
        statement.bindLong(2, profileDBID);

        long result;

        try {
            result = statement.executeUpdateDelete();
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        } finally {
            statement.close();
            db.close();
        }
        return result;
    }

    @SuppressLint("Range")
    public boolean isHavingProfile(int accountDBID) {
        String hasProfile = "NO";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String rawQuery = "SELECT hasProfile FROM Accounts WHERE account_DBID = ?";
        Cursor cursor = db.rawQuery(rawQuery, new String[]{String.valueOf(accountDBID)});

        if (cursor != null && cursor.moveToFirst()) {
            hasProfile = String.valueOf(cursor.getInt(cursor.getColumnIndex("hasProfile")));
            cursor.close();
        }

        if (hasProfile.equals("YES")){
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public String getAchievements(int accountDBID){
        String achievement = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "achievements FROM Profiles " +
                "WHERE account_DBPID = ?;";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(accountDBID)});

        if (cursor.moveToFirst()){
            achievement = cursor.getString(cursor.getColumnIndex("achievements"));
        }
        cursor.close();
        db.close();
        return achievement;
    }

    public boolean setAchievements(int accountDBID, String achievements) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String updateQuery = "UPDATE " + DatabaseHelper.Profile_Table +
                " SET achievements = ? " +
                " WHERE account_DBPID = ?";

        SQLiteStatement statement = db.compileStatement(updateQuery);
        statement.bindString(1, achievements);
        statement.bindLong(2, accountDBID);

        try {
            int rowCount = statement.executeUpdateDelete();
            statement.close();
            db.close();
            return rowCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }


    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String rawQuery = "SELECT username FROM Accounts WHERE username=?";
        Cursor cursor = db.rawQuery(rawQuery, new String[] { username });

        boolean isTaken = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isTaken;
    }

    @SuppressLint("Range")
    public ArrayList<MyClass> getAvailableClasses(int accountDBID) {
        ArrayList<MyClass> availableClasses = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
        Date currentDateTime = new Date();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "Class.classDBID, " +
                "Class.classDescription, " +
                "Class.classStartTime, " +
                "Class.classEndTime, " +
                "Class.classDuration, " +
                "Class.classDate, " +
                "Class.classVenue, " +
                "Class.classAvailability, " +
                "Profiles.name AS trainerName " +
                "FROM " +
                "Class " +
                "JOIN " +
                "Accounts ON Class.account_DBCID = Accounts.account_DBID " +
                "JOIN " +
                "Profiles ON Profiles.account_DBPID = Accounts.account_DBID " +
                "LEFT JOIN " +
                "Booking ON Booking.classDBBID = Class.classDBID " +
                "WHERE " +
                "(Booking.account_DBBID IS NULL OR (Booking.account_DBBID != ? AND Booking.bookingDate < datetime('now')));";

        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(accountDBID)});

            while (cursor.moveToNext()) {
                MyClass myClass = new MyClass();
                User trainer = new User();

                myClass.setClassDBID(cursor.getInt(cursor.getColumnIndex("classDBID")));
                myClass.setClassDescription(cursor.getString(cursor.getColumnIndex("classDescription")));

                myClass.setClassStartTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classStartTime"))));
                myClass.setClassEndTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classEndTime"))));

                myClass.setClassDuration(cursor.getInt(cursor.getColumnIndex("classDuration")));

                myClass.setClassDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex("classDate"))));
                myClass.setClassVenue(cursor.getString(cursor.getColumnIndex("classVenue")));

                trainer.setName(cursor.getString(cursor.getColumnIndex("trainerName")));
                myClass.setTrainerDetails(trainer);

                availableClasses.add(myClass);
            }
            cursor.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.close();
        return availableClasses;
    }

    public boolean bookClass(int accountDBID, int classDBID) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String bookingDate = dateFormat.format(new Date());

        String query = "INSERT INTO " + DatabaseHelper.Booking_Table + " (account_DBBID, classDBBID, bookingDate) " +
                "VALUES (?, ?, ?);";

        try {
            db.execSQL(query, new String[]{String.valueOf(accountDBID), String.valueOf(classDBID), bookingDate});
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deleteAllFromBooking() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            // Execute the delete query
            db.delete("Booking", null, null);
            return true;
        } catch (Exception e) {
            // Handle any exceptions here, e.g., log or show an error message
            e.printStackTrace();
            return false;
        } finally {
            // Close the database connection
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    public ArrayList<String> getAllBookingTablesData() {
        ArrayList<String> bookingList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM Booking";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor and add it to the ArrayList
                @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex("bookingID"));
                @SuppressLint("Range") String data2 = cursor.getString(cursor.getColumnIndex("account_DBBID"));
                @SuppressLint("Range") String data3 = cursor.getString(cursor.getColumnIndex("classDBBID"));
                @SuppressLint("Range") String data4 = cursor.getString(cursor.getColumnIndex("bookingDate"));
                bookingList.add(data);
                bookingList.add(data2);
                bookingList.add(data3);
                bookingList.add(data4);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bookingList;
    }

    @SuppressLint("Range")
    public ArrayList<MyClass> getHistoryClasses(int accountDBID) {
        ArrayList<MyClass> historyClasses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String query = "SELECT b.*, c.*, p.name AS trainerName " +
                "FROM Booking b " +
                "JOIN Class c ON b.classDBBID = c.classDBID " +
                "JOIN Profiles p ON c.account_DBCID = p.account_DBPID " +
                "WHERE b.account_DBBID = ?;";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(accountDBID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    MyClass myClass = new MyClass();
                    myClass.setClassDBID(cursor.getInt(cursor.getColumnIndex("classDBID")));
                    myClass.setClassDescription(cursor.getString(cursor.getColumnIndex("classDescription")));
                    myClass.setClassStartTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classStartTime"))));
                    myClass.setClassEndTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classEndTime"))));
                    myClass.setClassDuration(cursor.getInt(cursor.getColumnIndex("classDuration")));
                    myClass.setClassDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex("classDate"))));
                    myClass.setClassVenue(cursor.getString(cursor.getColumnIndex("classVenue")));
                    myClass.getTrainerDetails().setName(cursor.getString(cursor.getColumnIndex("trainerName")));
                    historyClasses.add(myClass);
                } while (cursor.moveToNext());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            cursor.close();
        }
        return historyClasses;
    }

    @SuppressLint("Range")
    public ArrayList<MyClass> getTrainerClass(int accountDBID){
        ArrayList<MyClass> trainerClass = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String query = "SELECT c.*, p.name AS trainerName " +
                "FROM Class c " +
                "JOIN Profiles p ON c.account_DBCID = p.account_DBPID " +
                "WHERE account_DBPID = ?;";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(accountDBID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    MyClass myClass = new MyClass();
                    myClass.setClassDBID(cursor.getInt(cursor.getColumnIndex("classDBID")));
                    myClass.setClassDescription(cursor.getString(cursor.getColumnIndex("classDescription")));
                    myClass.setClassStartTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classStartTime"))));
                    myClass.setClassEndTime(timeFormat.parse(cursor.getString(cursor.getColumnIndex("classEndTime"))));
                    myClass.setClassDuration(cursor.getInt(cursor.getColumnIndex("classDuration")));
                    myClass.setClassDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex("classDate"))));
                    myClass.setClassVenue(cursor.getString(cursor.getColumnIndex("classVenue")));
                    myClass.getTrainerDetails().setName(cursor.getString(cursor.getColumnIndex("trainerName")));
                    trainerClass.add(myClass);
                } while (cursor.moveToNext());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            cursor.close();
        }
        return trainerClass;
    }

    public boolean createClass(int account_DBCID, String classDescription, String classStartTime,
                               String classEndTime, int classDuration, String classDate,
                               String classVenue, String classAvailability) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
        String creationDate = dateFormat.format(new Date());
        String creationTime = timeFormat.format(new Date());

        String query = "INSERT INTO " + DatabaseHelper.Class_Table + " (account_DBCID, classDescription, " +
                "classStartTime, classEndTime, classDuration, classDate, classVenue, classAvailability) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, account_DBCID);
        statement.bindString(2, classDescription);
        statement.bindString(3, classStartTime);
        statement.bindString(4, classEndTime);
        statement.bindLong(5, classDuration);
        statement.bindString(6, classDate);
        statement.bindString(7, classVenue);
        statement.bindString(8, classAvailability);

        try {
            long rowId = statement.executeInsert();
            statement.close();
            db.close();
            return rowId != -1;
        } catch (Exception e) {
            e.printStackTrace();
            statement.close();
            db.close();
            return false;
        }
    }

    public boolean createAnnouncement(String description, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());
        String creationDate = dateFormat.format(new Date());
        String creationTime = timeFormat.format(new Date());

        String query = "INSERT INTO " + DatabaseHelper.Announcement_Table + " (description, announcementTitle, creationDate, creationTime) " +
                "VALUES (?, ?, ?, ?);";

        try {
            db.execSQL(query, new String[]{String.valueOf(description), String.valueOf(title), creationDate, creationTime});
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Announcement> getAllAnnouncement() {
        ArrayList<Announcement> announcements = new ArrayList<>();
        Announcement announcement;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Announcement_Table;
        //String query = "SELECT * FROM " + Announcement_Table + " ORDER BY creationDate DESC, creationTime DESC";
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String announcementTitle = cursor.getString(cursor.getColumnIndex("announcementTitle"));
                String creationDate = cursor.getString(cursor.getColumnIndex("creationDate"));
                String creationTime = cursor.getString(cursor.getColumnIndex("creationTime"));
                int announcementDBID = cursor.getInt(cursor.getColumnIndex("announcementDBID"));

                try {
                    announcement = new Announcement(description, announcementTitle, announcementDBID, dateFormat.parse(creationDate), timeFormat.parse(creationTime));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                announcements.add(announcement);
            }

            cursor.close();
        }
        return announcements;
    }


    public ArrayList<String> fetchUsername() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ArrayList<String> usernames = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT username FROM " + dbHelper.Account_Table, null);

        while (cursor.moveToNext()){
            String username = cursor.getString(0);
            usernames.add(username);
        }
        return usernames;
    }

    public int update(long account_DBID, String username, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        int ret = database.update(DatabaseHelper.Account_Table, contentValues, "account_DBID = " + account_DBID, null);
        return ret;
    }



    public void deleteAccount(long account_DBID){
        database.delete(DatabaseHelper.Account_Table, "account_DBID = " + account_DBID, null);
    }

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

}
