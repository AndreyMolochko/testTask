package com.example.andrey.testtask.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.testtask.R;
import com.example.andrey.testtask.model.User;
import com.example.andrey.testtask.ui.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class FragmentUserProfil extends Fragment {

    private TextView mEmailTextView;
    private TextView mNameTextView;
    private TextView mSurnameTextView;
    private TextView mSexTextView;
    private TextView mAgeTextView;

    private User mCurrentuser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profil, container, false);
        initViews(view);
        getUserFirebase();
        MainActivity.sAddMenuItem.setVisible(false);
        return view;
    }

    private void initViews(View pView) {
        mEmailTextView = pView.findViewById(R.id.email_value_text_view_fragment_user_profil);
        mNameTextView = pView.findViewById(R.id.name_value_text_view_fragment_user_profil);
        mSurnameTextView = pView.findViewById(R.id.surname_value_text_view_fragment_user_profil);
        mSexTextView = pView.findViewById(R.id.sex_value_text_view_fragment_user_profil);
        mAgeTextView = pView.findViewById(R.id.age_value_text_view_fragment_user_profil);
    }

    private void setValuesViews() {
        mEmailTextView.setText(mCurrentuser.getEmail());
        mNameTextView.setText(mCurrentuser.getName());
        mSurnameTextView.setText(mCurrentuser.getSurname());
        mSexTextView.setText(mCurrentuser.getSex());
        mAgeTextView.setText(String.valueOf(mCurrentuser.getAge()));
    }

    private void getUserFirebase() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            Query query = databaseReference.orderByChild("email").equalTo(currentFirebaseUser.getEmail());
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {
                    mCurrentuser = pDataSnapshot.getValue(User.class);
                    setValuesViews();
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
