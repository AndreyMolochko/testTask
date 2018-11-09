package com.example.andrey.testtask.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.andrey.testtask.R;
import com.example.andrey.testtask.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentRegistration extends Fragment {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mNameEditText;
    private EditText mSurnameEditText;
    private EditText mAgeEditText;
    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    private User mUser;

    private Button mRegistrationButton;
    private Button mAutorizationButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initViews(view);
        initListenrs();
        return view;
    }

    private void initListenrs() {
        mRegistrationButton.setOnClickListener(v -> {
            if (!isEmptyFields()) {
                doRegistration();
            } else {
                Toast.makeText(getContext(), getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            }
        });
        mAutorizationButton.setOnClickListener(v -> {
            getFragmentManager().beginTransaction().replace(R.id.root_layout_content_main, new FragmentAutorization()).commit();
        });
    }

    private void initViews(View pView) {
        mEmailEditText = pView.findViewById(R.id.email_edit_text_fragment_registration);
        mPasswordEditText = pView.findViewById(R.id.password_edit_text_fragment_registration);
        mNameEditText = pView.findViewById(R.id.name_edit_text_fragment_registration);
        mSurnameEditText = pView.findViewById(R.id.surname_edit_text_fragment_registration);
        mAgeEditText = pView.findViewById(R.id.age_edit_text_fragment_registration);
        mMaleRadioButton = pView.findViewById(R.id.male_radio_button_fragment_registration);
        mFemaleRadioButton = pView.findViewById(R.id.female_radio_button_fragment_registration);
        mRegistrationButton = pView.findViewById(R.id.sign_up_button_fragment_registration);
        mAutorizationButton = pView.findViewById(R.id.sign_in_button_fragment_registration);
    }

    private void doRegistration() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString()).
                addOnCompleteListener((Activity) getContext(), pTask -> {
                    if (pTask.isSuccessful()) {
                        initUser();
                        writeRecordFirebase();
                        Toast.makeText(getContext(), getString(R.string.successful), Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.root_layout_content_main, new FragmentRecordsList()).commit();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isEmptyFields() {
        if (mEmailEditText.getText().toString().equals("") || mPasswordEditText.getText().toString().equals("") ||
                mNameEditText.getText().toString().equals("") || mSurnameEditText.getText().toString().equals("") ||
                mAgeEditText.getText().toString().equals("") ||
                !(mMaleRadioButton.isChecked() || mFemaleRadioButton.isChecked())) {
            return true;
        } else {
            return false;
        }
    }

    private void initUser() {
        mUser = new User();
        mUser.setEmail(mEmailEditText.getText().toString());
        mUser.setName(mNameEditText.getText().toString());
        mUser.setSurname(mSurnameEditText.getText().toString());
        mUser.setAge(Integer.parseInt(mAgeEditText.getText().toString()));
        if (mMaleRadioButton.isChecked()) {
            mUser.setSex(getString(R.string.male));
        } else {
            mUser.setSex(getString(R.string.female));
        }
    }

    private void writeRecordFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.push().setValue(mUser);
    }
}
