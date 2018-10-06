package au.edu.usc.dcd008.myreceipts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class ReceiptActivity extends SingleFragmentActivity {
    public static final String EXTRA_RECEIPT_ID =
            "au.edu.usc.dcd008.myreceipts.receipt_id";
    public static final String EXTRA_RECEIPT_IS_NEW =
            "au.edu.usc.dcd008.myreceipts.receipt_is_new";

    public static Intent newIntent(Context packageContext, UUID receiptId, boolean isNewReceipt){
        Intent intent = new Intent(packageContext, ReceiptActivity.class);
        intent.putExtra(EXTRA_RECEIPT_ID, receiptId);
        intent.putExtra(EXTRA_RECEIPT_IS_NEW, isNewReceipt);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID receiptId = (UUID) getIntent().getSerializableExtra(EXTRA_RECEIPT_ID);
        boolean isNewReceipt = getIntent().getBooleanExtra(EXTRA_RECEIPT_IS_NEW, false);
        return ReceiptFragment.newInstance(receiptId, isNewReceipt);
    }
}
