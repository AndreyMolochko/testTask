package com.example.andrey.testtask.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrey.testtask.R;
import com.example.andrey.testtask.model.Record;
import com.example.andrey.testtask.util.CalendarWorker;


import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordHolder> {

    private List<Record> mRecordList;

    public RecordAdapter(List<Record> pRecordList) {
        mRecordList = pRecordList;
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {
        View view = LayoutInflater.from(pViewGroup.getContext()).inflate(R.layout.item_recycler_record, pViewGroup, false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder pRecordHolder, int pI) {
        Record record = mRecordList.get(pI);
        pRecordHolder.mContextTextView.setText(record.getContext());
        pRecordHolder.mDateTextView.setText(CalendarWorker.convertDate(Math.abs(record.getDate())));//use module, because we store data in firebase with minus
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    public void addItem(Record pRecord) {
        mRecordList.add(pRecord);
    }

    public class RecordHolder extends RecyclerView.ViewHolder {
        private TextView mContextTextView;
        private TextView mDateTextView;

        public RecordHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View pItemView) {
            mContextTextView = pItemView.findViewById(R.id.context_text_view_item_recycler);
            mDateTextView = pItemView.findViewById(R.id.date_text_view_item_recycler);
        }
    }

}
