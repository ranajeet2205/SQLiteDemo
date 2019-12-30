package com.ranajeet2205.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText userNameEdit, emailEdit, passwordEdit, confirmEdit, mobileEdit;
    private Button signUpBtn;
    private String userName, email, password, confirmPassword, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEdit = findViewById(R.id.username_edit);
        emailEdit = findViewById(R.id.email_edit);
        passwordEdit = findViewById(R.id.pass_edit);
        confirmEdit = findViewById(R.id.confirm_pass_edit);
        mobileEdit = findViewById(R.id.mobile_edit);
        signUpBtn = findViewById(R.id.sign_up_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEdit.getText().toString().trim();
                email = emailEdit.getText().toString().trim();
                password = passwordEdit.getText().toString().trim();
                confirmPassword = confirmEdit.getText().toString().trim();
                mobile = mobileEdit.getText().toString().trim();

                String validationReturn = validateInputData();

                if (validationReturn.equalsIgnoreCase("success")) {
                    
                } else {
                    Toast.makeText(SignUpActivity.this, "" + validationReturn, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /***
     * This method checks for the all the data and return the string of success and failure according
     * @return
     */
    private String validateInputData() {

        if (!validateEmail()) {
            return "Please Input Valid Email";
        } else if (!validateMobile()) {
            return "Please Input Valid Mobile Number";
        } else if (!isPasswordValid()) {
            return "Password must be 8 character with 1 upper ,lower and special character";
        } else if (!comparePassword()) {
            return "password and confirm password must be same";
        } else {
            return "success";
        }

    }

    /***
     * validate the email
     * @return
     */
    private boolean validateEmail() {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /***
     * Validate the mobile number
     * @return
     */
    private boolean validateMobile() {
        return (!TextUtils.isEmpty(email) && Patterns.PHONE.matcher(mobile).matches());
    }


    /***
     * checks given password is according to the regular expression rule
     *
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     *
     * @return
     */
    public boolean isPasswordValid() {


        String regExpn =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        CharSequence inputStr = password;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    /***
     * checks password and confirm password are same
     * @return
     */
    private boolean comparePassword() {
        return password.equals(confirmPassword);
    }
}
