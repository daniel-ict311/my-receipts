package au.edu.usc.dcd008.myreceipts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReceiptListFragment extends Fragment {
    private RecyclerView mReceiptRecyclerView;
    private ReceiptAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    //holds a list_item_receipt views, binds it to different Crime objects
    private class ReceiptHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mShopNameView;
        private Receipt mReceipt;
        public ReceiptHolder(LayoutInflater inflater, ViewGroup parent){
            //you can find the view in ReceiptHolder.itemView
            super(inflater.inflate(R.layout.list_item_receipt, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.receipt_title);
            mDateTextView = itemView.findViewById(R.id.receipt_date);
            mShopNameView = itemView.findViewById(R.id.receipt_shop_name);
        }
        public void bind(Receipt receipt){
            mReceipt = receipt;
            mTitleTextView.setText(mReceipt.getTitle());
            mDateTextView.setText(mReceipt.getDate().toString());
            mShopNameView.setText(mReceipt.getShopName());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ReceiptActivity.newIntent(getActivity(), mReceipt.getId());
            startActivity(intent);
        }
    }

    //connects the RecyclerView to the CrimeHolder classes
    private class ReceiptAdapter extends RecyclerView.Adapter<ReceiptHolder>{
        private List<Receipt> mReceipts;

        public void setReceipts(List<Receipt> receipts){
            mReceipts = receipts;
        }

        public ReceiptAdapter(List<Receipt> receipts){
            mReceipts = receipts;
        }

        @NonNull
        @Override
        public ReceiptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ReceiptHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ReceiptHolder holder, int position) {
            Receipt receipt = mReceipts.get(position);
            holder.bind(receipt);
        }

        @Override
        public int getItemCount() {
            return mReceipts.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt_list, container, false);

        mReceiptRecyclerView = view.findViewById(R.id.receipt_recycler_view);
        mReceiptRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        ReceiptLab receiptLab = ReceiptLab.get(getActivity());
        List<Receipt> receipts = receiptLab.getReceipts();

        if (mAdapter == null){
            mAdapter = new ReceiptAdapter(receipts);
            mReceiptRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
            mAdapter.setReceipts(receipts);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_receipt_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.new_receipt:
                Receipt receipt = new Receipt();
                ReceiptLab.get(getActivity()).addReceipt(receipt);
                Intent intent = ReceiptActivity.newIntent(getActivity(), receipt.getId());
                startActivity(intent);
                return true;
           // case R.id.show_help:
                //todo
                //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //delete this method later maybe
    }
}


