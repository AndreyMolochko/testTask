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
import android.widget.Toast;

import com.example.andrey.testtask.R;
import com.example.andrey.testtask.ui.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentAutorization extends Fragment {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mRegistrationButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        initListenrs();
        MainActivity.sAddMenuItem.setVisible(false);
        return view;
    }

    private void initListenrs() {
        mLoginButton.setOnClickListener(v -> {
            if (!isEmptyFields()) {
                doAutorization();
            } else {
                Toast.makeText(getContext(), getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            }
        });
        mRegistrationButton.setOnClickListener(v -> {
            getFragmentManager().beginTransaction().replace(R.id.root_layout_content_main, new FragmentRegistration()).commit();
        });
    }

    private void initViews(View pView) {
        mEmailEditText = pView.findViewById(R.id.email_edit_text_fragment_login);
        mPasswordEditText = pView.findViewById(R.id.password_edit_text_fragment_login);
        mLoginButton = pView.findViewById(R.id.sign_in_button_fragment_login);
        mRegistrationButton = pView.findViewById(R.id.sign_up_button_fragment_login);
    }

    private void doAutorization() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString()).addOnCompleteListener((Activity) getContext(), pTask -> {
            if (pTask.isSuccessful()) {
                getFragmentManager().beginTransaction().replace(R.id.root_layout_content_main, new FragmentRecordsList()).commit();
            } else {
                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmptyFields() {
        if (mEmailEditText.getText().toString().equals("") || mPasswordEditText.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
