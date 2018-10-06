package au.edu.usc.dcd008.myreceipts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import au.edu.usc.dcd008.myreceipts.database.ReceiptDbSchema.ReceiptTable;

public class ReceiptBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "receiptbase.db";

    public ReceiptBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + ReceiptTable.NAME + "(" +
                                "_id integer primary key autoincrement, " +
                                 ReceiptTable.cols.UUID + ", " +
                                ReceiptTable.cols.TITLE + ", " +
                                ReceiptTable.cols.DATE + ", " +
                                ReceiptTable.cols.SHOP_NAME + ", " +
                                ReceiptTable.cols.COMMENT + ", " +
                                ReceiptTable.cols.LONGITUDE + "," +
                                ReceiptTable.cols.LATITUDE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //do nothing as yet
    }
}
