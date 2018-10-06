package au.edu.usc.dcd008.myreceipts.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import au.edu.usc.dcd008.myreceipts.Receipt;
import au.edu.usc.dcd008.myreceipts.database.ReceiptDbSchema.ReceiptTable;

public class ReceiptCursorWrapper extends CursorWrapper {

    public ReceiptCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Receipt getReceipt(){
        String uuidString = getString(getColumnIndex(ReceiptTable.cols.UUID));
        String title = getString(getColumnIndex(ReceiptTable.cols.TITLE));
        long date = getLong(getColumnIndex(ReceiptTable.cols.DATE));
        String shopName = getString(getColumnIndex(ReceiptTable.cols.SHOP_NAME));
        String comment = getString(getColumnIndex(ReceiptTable.cols.COMMENT));
        Receipt receipt = new Receipt(UUID.fromString(uuidString));
        receipt.setTitle(title);
        receipt.setDate(new Date(date));
        receipt.setShopName(shopName);
        receipt.setComment(comment);
        //todo: location

        return receipt;
    }
}
