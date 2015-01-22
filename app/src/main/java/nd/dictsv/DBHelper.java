package nd.dictsv;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ND on 12/7/2557.
 */
public class DBHelper extends SQLiteOpenHelper {

    /////////////////////////////////////////////////
    //กำหนดตัวแปรเก็บชื่อที่ใช้เกี่ยวกับในการสร้างฐานข้อมูล
    // Database Name
    public static final String DB_NAME = "WordDICT.db";
    // Database Version
    public static final int DB_VERSION = 1;
    ///Table IndexCat//////////////////////////////////////////////////////////////////////////
    public static final String TABLE_CATE = "Cate";
    //column

    //Column Name
    //primary key ต้องไว้คอลัมแรก
    public static final String COL0_CATE_ID = "_cate_id";
    public static final String COL1_CATENAME = "cate";
    public static final String TABLE_CREATE_CATE = "CREATE TABLE " + TABLE_CATE
            + "("
            + COL0_CATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL1_CATENAME + " TEXT "
            +");";
    //Table WordTB/////////////////////////////////////////////////////////////////////////////
    public static final String TABLE_WordTB = "WordTB";
    public static final String COL0_WORDS_ID = "_word_id";
    public static final String COL1_WORDS = "Words";
    public static final String COL2_TRANSLATER = "trans";
    public static final String COL3_TERMINOLOGY = "ter";
    public static final String COL5_WORD_CATE_ID = "cate_";
    public static final String TABLE_CREATE_WORDS = "CREATE TABLE  " + TABLE_WordTB
            + "("
            + COL0_WORDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL1_WORDS + " TEXT , "
            + COL2_TRANSLATER + " TEXT , "
            + COL3_TERMINOLOGY + " TEXT , "
            + COL5_WORD_CATE_ID + " INTEGER  "
            +");";
    /*//Table Favorite///////////////////////////////////////////////////////////////////////////
    public static final String TABLE_FAVORITE = "Favorite";
    //column name
    public static final String COL0_FAVORITE_ID = "id";
    public static final String COL1_FAVORITE_NAME = "name";
    // Table favorite
     /                    Table Name favorite
            +-----------+-----------+-----------+--------+
            |  FIELD          |     TYPE     |    KEY    |
            +-----------+-----------+-----------+--------+
            |     f_id       |     INT       |    PK     |
            |     f_name     |     TEXT      |           |
            ***
    public static final String TABLE_favorite_SQL = "CREATE TABLE " + TABLE_FAVORITE
            + "("
            + COL0_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL1_FAVORITE_NAME + " TEXT)";*/


    //////////////////////////////////////////////////////////////////////////////////



    // method ที่เป็น constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }




    //onCreate() นิยมแทรกคำสั่งสำหรับการ Create Database และ Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE_WORDS);  //สร้างตาราง WordTB
        for (int i = 0; i < 5; i++) {
            db.execSQL("insert into WordTB values (null, 'คำ1', 'Testt1', 'Testt1', 1)");
            db.execSQL("insert into WordTB values (null, 'คำ2', 'Testt2', 'Testt2', 1)");
            db.execSQL("insert into WordTB values (null, 'คำ3', 'Testt3', 'Testt3', 3)");
            db.execSQL("insert into WordTB values (null, 'คำ4', 'Testt4', 'Testt4', 2)");
        }
        // เพิ่มค่าในตารางผ่านแล้วลองเทส 1500 คำเปิดแอพมาเร็วมาก สร้างดาด้าเบสเร็ว

        db.execSQL(TABLE_CREATE_CATE); // สร้างตาราง IndexCat

            db.execSQL("insert into Cate values (null, 'คอม')");
            db.execSQL("insert into Cate values (null, 'วิทย์')");


        //db.execSQL(TABLE_favorite_SQL);
    }

    //onUpgrade() นิยมใช้สำหรับการเปลี่ยนแปลง Version หรือโครงสร้างของ Database และ table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ทำงานเมื่อพบว่าฐานข้อมูลเดิมเป็น version เก่า จะอัพเกรดฐานข้อมูล โดยจะให้ลบของเก่าทิ้งแล้วสร้างขึ้นมาใหม
        //แต่ สามารถใช้คำสั่ง ALTER แทรกคอลัมได้
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_CATE);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_favorite_SQL);
        Log.e("DBHelper", "Table Upgrade from" + oldVersion + "to" + newVersion);
        onCreate(db);
    }


}
