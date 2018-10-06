package au.edu.usc.dcd008.myreceipts;

import android.support.v4.app.Fragment;

public class ReceiptListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ReceiptListFragment();
    }
}
