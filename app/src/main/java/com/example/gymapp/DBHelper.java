package com.example.gymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "Gym.db";
    private static final String TABLE_NAME = "Athletes";
    private static final String TABLE_NAME2 = "Exercises";
    private static final String TABLE_NAME3 = "Workouts";


    private static final int VERSION = 1;

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRSTNAME = "First_name";
    private static final String COLUMN_LASTNAME = "Last_name";
    private static final String COLUMN_TELEPHONE = "Telephone";
    private static final String COLUMN_DESCRIPTION = "Description";

    private static final String COLUMN_ID2 = "_id";
    private static final String COLUMN_EXNAME = "Exercise_name";
    private static final String COLUMN_REPEATS = "Repeats";
    private static final String COLUMN_SETS = "Sets";
    private static final String COLUMN_WORKOUT = "Workout";


    private static final String COLUMN_ID3 = "_id";
    private static final String COLUMN_WORKNAME = "Workout_name";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME+
                " (" +COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_FIRSTNAME +" TEXT, "+
                COLUMN_LASTNAME +" TEXT, "+
                COLUMN_TELEPHONE +" BIGINT, "+
                COLUMN_DESCRIPTION +" TEXT);";

        String query2 = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME2+
                " (" +COLUMN_ID2 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_EXNAME +" TEXT, "+
                COLUMN_REPEATS +" INT, "+
                COLUMN_SETS +" INT, "+

                COLUMN_WORKOUT +" Workout);";

        String query3 = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME3+
                " (" +COLUMN_ID3 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_WORKNAME +" TEXT);";


        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " +TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS " +TABLE_NAME2;
        db.execSQL(query);
        db.execSQL(query2);
        onCreate(db);
    }

    public void addAthlete(Athlete athlete){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRSTNAME, athlete.getFirstName());
        cv.put(COLUMN_LASTNAME, athlete.getLastName());
        cv.put(COLUMN_DESCRIPTION, athlete.getDescription());
        cv.put(COLUMN_TELEPHONE, athlete.getPhone());

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Entry not added",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Athlete with name "+athlete.getFirstName()+" "+athlete.getLastName()+" added",Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllAthletes(){
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateAthlete(String row_id, Athlete athlete){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRSTNAME, athlete.getFirstName());
        cv.put(COLUMN_LASTNAME, athlete.getLastName());
        cv.put(COLUMN_DESCRIPTION, athlete.getDescription());
        cv.put(COLUMN_TELEPHONE, athlete.getPhone());

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Entry not updated",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Athlete with name "+athlete.getFirstName()+" "+athlete.getLastName()+" updated",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAthlete(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if(result == -1) {
            Toast.makeText(context, "Entry not deleted.",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Athlete deleted.",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllAthletes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }

    public void addExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXNAME, exercise.getExName());
        cv.put(COLUMN_REPEATS, exercise.getRepeats());
        cv.put(COLUMN_SETS, exercise.getSets());

        long result = db.insert(TABLE_NAME2, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Entry not added",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Exercise with name "+exercise.getExName()+" added",Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllExercises(){
        String query = "SELECT * FROM "+TABLE_NAME2;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateExercise(String row_id, Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXNAME, exercise.getExName());
        cv.put(COLUMN_REPEATS, exercise.getRepeats());
        cv.put(COLUMN_SETS, exercise.getSets());

        long result = db.update(TABLE_NAME2, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Entry not updated",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Exercise: "+exercise.getExName()+" updated",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteExercise(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, "_id=?", new String[]{row_id});

        if(result == -1) {
            Toast.makeText(context, "Entry not deleted.",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Athlete deleted.",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllExercises(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME2);
    }

    public void addWorkout(Workout workout){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORKNAME, workout.getWorkoutName());

        long result = db.insert(TABLE_NAME3, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Entry not added",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Workout with name "+workout.getWorkoutName()+" added",Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllWorkouts(){
        String query = "SELECT * FROM "+TABLE_NAME3;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateWorkout(String row_id, Workout workout){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORKNAME, workout.getWorkoutName());

        long result = db.update(TABLE_NAME3, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Entry not updated",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Workout: "+workout.getWorkoutName()+" updated",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteWorkout(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME3, "_id=?", new String[]{row_id});

        if(result == -1) {
            Toast.makeText(context, "Entry not deleted.",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Workout deleted.",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllWorkouts(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME3);
    }

    public Cursor searchWorkoutByName(String row_id){
        String query = "SELECT * FROM "+TABLE_NAME3 +" WHERE " +COLUMN_WORKNAME +"='" +row_id+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
//        while(cursor.moveToNext()){
//            Workout work = new Workout();
//            work.setId(Integer.parseInt(cursor.getString(0)));
//            work.setWorkoutName(cursor.getString(1));
//        }
        return cursor;
    }

    public Cursor searchExerciseById(String row_id){
        String query = "SELECT * FROM "+TABLE_NAME2 +" WHERE " +COLUMN_ID2 +"='" +row_id+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor searchExerciseByName(String row_id){
        String query = "SELECT * FROM "+TABLE_NAME2 +" WHERE " +COLUMN_EXNAME +"='" +row_id+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void fillWorkout(Workout workout, Exercise ex){
        Cursor cursor = searchWorkoutByName(workout.getWorkoutName());
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();

        Workout work = new Workout();
        while(cursor.moveToNext()){
            work.setId(Integer.parseInt(cursor.getString(0)));
            work.setWorkoutName(cursor.getString(1));
        }

        cv.put(COLUMN_WORKOUT, workout.getId());

        long result = db.update(TABLE_NAME2, cv, "_id=?", new String[]{String.valueOf(ex.getId())});
        if(result == -1) {
            Toast.makeText(context, "Entry not updated",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Exercise: "+ex.getExName()+" updated",Toast.LENGTH_SHORT).show();
        }
    }
}
