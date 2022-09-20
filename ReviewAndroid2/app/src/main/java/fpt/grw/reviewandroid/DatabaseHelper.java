package fpt.grw.reviewandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import entities.Exam;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Exams";
    private static final String TABLE_EXAM = "Exams";
    private static final String TABLE_DETAIL = "Details";

    private static final String DETAIL_ID = "detail_id";
    private static final String DETAIL_QUESTION = "detail_question";
    private static final String DETAIL_PICTURE_URL = "detail_picture_URL";

    public static final String EXAM_ID = "exam_id";
    public static final String EXAM_NAME = "exam_name";
    public static final String EXAM_DATE = "exam_date";
    public static final String EXAM_DESCRIPTION = "exam_description";

    private SQLiteDatabase database;
    private static final String DETAIL_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s INTEGER, " +
                    "   %s TEXT, " +
                    "   %s TEXT)",
            TABLE_DETAIL, DETAIL_ID, EXAM_ID, DETAIL_QUESTION, DETAIL_PICTURE_URL);

    private static final String EXAM_TABLE_CREATE = String.format(
      "CREATE TABLE %s (" +
      "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
      "   %s TEXT, " +
      "   %s TEXT, " +
      "   %s TEXT)",
      TABLE_EXAM, EXAM_ID, EXAM_NAME, EXAM_DATE, EXAM_DESCRIPTION);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
        database = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EXAM_TABLE_CREATE);
        db.execSQL(DETAIL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    public long insertDetail(int exam_id,String question,String picURL){
        ContentValues rowValues = new ContentValues();

        rowValues.put(EXAM_ID , exam_id);
        rowValues.put(DETAIL_QUESTION, question);
        rowValues.put(DETAIL_PICTURE_URL, picURL);
        return database.insertOrThrow(TABLE_DETAIL, null, rowValues);
    }

    public long insertExam(String name, String exam_date, String description) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(EXAM_NAME, name);
        rowValues.put(EXAM_DATE, exam_date);
        rowValues.put(EXAM_DESCRIPTION, description);

        return database.insertOrThrow(TABLE_EXAM, null, rowValues);
    }

    public List<Exam> getExams() {
        Cursor cursor = database.query(TABLE_EXAM, new String[] {EXAM_ID, EXAM_NAME, EXAM_DATE, EXAM_DESCRIPTION},
                null, null, null, null, EXAM_NAME);

        List<Exam> results = new ArrayList<Exam>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String exam_date = cursor.getString(2);
            String description = cursor.getString(3);

            Exam exam = new Exam(id,name,exam_date,description);
            results.add(exam);

            cursor.moveToNext();
        }

        return results;

    }
}
