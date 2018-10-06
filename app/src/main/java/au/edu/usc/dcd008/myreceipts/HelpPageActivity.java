package au.edu.usc.dcd008.myreceipts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class HelpPageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext, Uri helpPageUri){
        Intent intent = new Intent(packageContext, HelpPageActivity.class);
        intent.setData(helpPageUri);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return HelpPageFragment.newInstance(getIntent().getData());
    }
}
