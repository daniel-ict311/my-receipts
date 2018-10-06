package au.edu.usc.dcd008.myreceipts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReceiptFragment extends Fragment {
    private Receipt mReceipt;
    private File mPhotoFile;

    private EditText mTitleField;
    private EditText mShopNameField;
    private EditText mCommentField;
    private Button mDateButton;

    private Button mReportButton;
    private Button mDeleteButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    private static final String ARG_RECEIPT_ID = "receipt_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    public static ReceiptFragment newInstance(UUID receiptID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECEIPT_ID, receiptID);

        ReceiptFragment fragment = new ReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID receiptId = (UUID) getArguments().getSerializable(ARG_RECEIPT_ID);
        mReceipt = ReceiptLab.get(getActivity()).getReceipt(receiptId);
        mPhotoFile = ReceiptLab.get(getActivity()).getPhotoFile(mReceipt);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_receipt, container, false);
        PackageManager packageManager = getActivity().getPackageManager();

        mDeleteButton = v.findViewById(R.id.delete_receipt);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceiptLab.get(getActivity()).deleteReceipt(mReceipt);
                getActivity().finish();
            }
        });

        mTitleField = v.findViewById(R.id.receipt_title);
        mTitleField.setText(mReceipt.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //pass
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mReceipt.setTitle(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                //pass
            }
        });

        mShopNameField = v.findViewById(R.id.receipt_shop_name);
        mShopNameField.setText(mReceipt.getShopName());
        mShopNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //pass
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mReceipt.setShopName(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                //pass
            }
        });

        mCommentField = v.findViewById(R.id.receipt_comment);
        mCommentField.setText(mReceipt.getComment());
        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //pass
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mReceipt.setComment(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                //pass
            }
        });


        mDateButton = v.findViewById(R.id.receipt_date);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mReceipt.getDate());
                dialog.setTargetFragment(ReceiptFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        updateDateButton();

        mReportButton = v.findViewById(R.id.receipt_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getReceiptReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.receipt_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });




        mPhotoButton = v.findViewById(R.id.receipt_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null
                && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "au.edu.usc.dcd008.myreceipts.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> camerActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : camerActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });



        mPhotoView = v.findViewById(R.id.receipt_photo);

        updatePhotoView();
        return v;
    }

    private void updateDateButton() {
        mDateButton.setText(mReceipt.getDate().toString());
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mReceipt.setDate(date);
            updateDateButton();

        } else if (requestCode == REQUEST_PHOTO){
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.criminalintent.fileprovider",
                    mPhotoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        ReceiptLab.get(getActivity()).updateReceipt(mReceipt);
    }

    private String getReceiptReport(){
       return "todo: make report"; //todo: make report

    }

    private void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()){
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
