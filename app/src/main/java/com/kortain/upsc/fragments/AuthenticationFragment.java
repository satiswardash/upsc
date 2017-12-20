package com.kortain.upsc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kortain.upsc.AuthenticationActivity;
import com.kortain.upsc.R;
import com.kortain.upsc.utils.StringUtility;

/**
 * Created by satiswardash on 20/12/17.
 */

public class AuthenticationFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static EditText signInEmailId;
    private static EditText signInPassword;
    private static EditText signUpEmailId;
    private static EditText signUpPassword;
    private static EditText signupPhone;
    private static EditText signupName;
    private static Button signInButton;
    private static Button signUpButton;
    private static Button googleButton;
    private static Button facebookButton;
    private View rootView;

    public AuthenticationFragment() {
        rootView = null;

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AuthenticationFragment newInstance(int sectionNumber) {
        AuthenticationFragment fragment = new AuthenticationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int arg = getArguments().getInt(ARG_SECTION_NUMBER);
        if (arg == 1) {
            rootView = inflater.inflate(R.layout.layout_signin, container, false);
            initSignInLayout();
        } else if (arg == 2) {
            rootView = inflater.inflate(R.layout.layout_register, container, false);
            initSignUpLayout();
        }
        return rootView;
    }

    private void initSignInLayout() {
        signInEmailId = rootView.findViewById(R.id.al_signin_email_editText);
        signInPassword = rootView.findViewById(R.id.al_signin_password_editText);
        signInButton = rootView.findViewById(R.id.al_signin_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtility.validate(signInEmailId.getText().toString())) {
                    ((AuthenticationActivity) getActivity())
                            .signIn(signInEmailId.getText().toString(), signInPassword.getText().toString());
                } else {
                    Toast.makeText(getActivity(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initSignUpLayout() {
        signupName = rootView.findViewById(R.id.al_signup_name_editText);
        signupPhone = rootView.findViewById(R.id.al_signup_phone_editText);
        signUpEmailId = rootView.findViewById(R.id.al_signup_email_editText);
        signUpPassword = rootView.findViewById(R.id.al_signup_password_editText);
        signUpButton = rootView.findViewById(R.id.al_signup_button);
        googleButton = rootView.findViewById(R.id.al_signin_google_button);
        facebookButton = rootView.findViewById(R.id.al_signin_facebook_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtility.validate(signUpEmailId.getText().toString())) {
                    ((AuthenticationActivity) getActivity())
                            .signUp(signUpEmailId.getText().toString(), signUpPassword.getText().toString());
                } else {
                    Toast.makeText(getActivity(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
                }
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}