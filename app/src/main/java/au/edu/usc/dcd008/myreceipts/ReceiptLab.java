package au.edu.usc.dcd008.myreceipts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import au.edu.usc.dcd008.myreceipts.database.ReceiptCursorWrapper;
import au.edu.usc.dcd008.myreceipts.database.ReceiptDbSchema.ReceiptTable;
import au.edu.usc.dcd008.myreceipts.database.ReceiptBaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//singleton
public class ReceiptLab {
    private static ReceiptLab sReceiptLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ReceiptLab get(Context context){
        if (sReceiptLab == null){
            sReceiptLab = new ReceiptLab(context);
        }
        return sReceiptLab;
    }

    private ReceiptLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new ReceiptBaseHelper(mContext)
                .getWritableDatabase();
    }

    public File getPhotoFile(Receipt receipt){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, receipt.getPhotoFilename());
    }

    private static ContentValues getContentValues(Receipt receipt){
        ContentValues values = new ContentValues();
        values.put(ReceiptTable.cols.UUID, receipt.getId().toString());
        values.put(ReceiptTable.cols.TITLE, receipt.getTitle());
        values.put(ReceiptTable.cols.DATE, receipt.getDate().getTime());
        values.put(ReceiptTable.cols.SHOP_NAME, receipt.getShopName());
        values.put(ReceiptTable.cols.COMMENT, receipt.getComment());
        //todo: values.put(ReceiptTable.cols.LOCATION, receipt.getLocation());


        return values;
    }

    public void updateReceipt(Receipt receipt){
        String uuidString = receipt.getId().toString();
        ContentValues values = getContentValues(receipt);
        mDatabase.update(ReceiptTable.NAME, values,
                ReceiptTable.cols.UUID + " =  ?",
                new String[] { uuidString});
    }

    private ReceiptCursorWrapper queryReceipts(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ReceiptTable.NAME,
                null, // selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ReceiptCursorWrapper(cursor);
    }


    public List<Receipt> getReceipts(){
        List<Receipt> receipts = new ArrayList<>();
        ReceiptCursorWrapper cursor = queryReceipts(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                receipts.add(cursor.getReceipt());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return receipts;
    }

    public Receipt getReceipt(UUID id){
        ReceiptCursorWrapper cursor = queryReceipts(
                ReceiptTable.cols.UUID + " = ?",
                new String[] { id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getReceipt();
        } finally {
            cursor.close();
        }
    }

    public void addReceipt(Receipt c){
        ContentValues values = getContentValues(c);

        mDatabase.insert(ReceiptTable.NAME, null, values);
    }

    public void deleteReceipt(Receipt c) {
        mDatabase.delete(ReceiptTable.NAME, ReceiptTable.cols.UUID + " = ?",
                new String[] { c.getId().toString()});
    }
}
