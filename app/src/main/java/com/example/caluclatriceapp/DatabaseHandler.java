package com.example.caluclatriceapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "historyoperationsdb";
    private static final String TABLE_History = "history";
    private static final String ID = "id";
    private static final String OPERATION = "operation";
    private static final String RESULT = "result";
    public DatabaseHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_History + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + OPERATION + " TEXT,"
                + RESULT + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_History);
        onCreate(db);
    }

    void addOperation(String operation, String result){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OPERATION, operation);
        values.put(RESULT, result);
        long newRowId = db.insert(TABLE_History,null, values);
        db.close();
    }
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getOperations(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> operationsList = new ArrayList<>();
        String query = "SELECT operation, result FROM "+ TABLE_History;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> operation = new HashMap<>();
            operation.put("operation",cursor.getString(cursor.getColumnIndex(OPERATION)));
            operation.put("result",cursor.getString(cursor.getColumnIndex(RESULT)));
            operationsList.add(operation);
        }
        return  operationsList;
    }
}
