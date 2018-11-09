package com.example.andrey.testtask.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.andrey.testtask.R;
import com.example.andrey.testtask.adapters.RecordAdapter;
import com.example.andrey.testtask.model.Record;
import com.example.andrey.testtask.ui.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentRecordsList extends Fragment {

    private RecyclerView mRecordsRecyclerView;
    private RecordAdapter mRecordAdapter;
    private List<Record> mRecordList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_records, container, false);
        mRecordList = new ArrayList<>();
        getRecordsFirebase();
        initRecyler(view);
        MainActivity.sAddMenuItem.setVisible(true);
        return view;
    }

    private void initRecyler(View pView) {
        mRecordsRecyclerView = pView.findViewById(R.id.recycler_records);
        mRecordsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Collections.reverse(mRecordList);
        mRecordAdapter = new RecordAdapter(mRecordList);
        mRecordsRecyclerView.setAdapter(mRecordAdapter);
    }

    private void getRecordsFirebase() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("records");
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            Query query = databaseReference.orderByChild("date");
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {
                    if (pDataSnapshot.getValue(Record.class).getUserId().equals(currentFirebaseUser.getUid())) {
                        mRecordAdapter.addItem(pDataSnapshot.getValue(Record.class));
                        mRecordAdapter.notifyItemInserted(mRecordAdapter.getItemCount());
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot pDataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError pDatabaseError) {

                }
            });
        } else {
            Toast.makeText(getContext(), getString(R.string.need_login), Toast.LENGTH_SHORT).show();
        }
    }
}
