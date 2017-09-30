package charusat.vrund;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    ProgressBar progressBar;
    private EditText et_email, et_mobile, et_name, et_roll_num;
    private Button register;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_roll_num = (EditText) findViewById(R.id.et_rollno);
        et_name = (EditText) findViewById(R.id.et_name);
        register = (Button) findViewById(R.id.bt_register);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
    }
    private void registerUser() {
        int flag = 0;
        String email = et_email.getText().toString().trim();
        String mobile = et_mobile.getText().toString().trim();
        String rollno = et_roll_num.getText().toString().trim();
        String name = et_name.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            flag++;
            et_email.setError("Empty");
        }
        if (TextUtils.isEmpty(mobile)) {
            flag++;
            et_mobile.setError("Empty");
        }
        if (TextUtils.isEmpty(name)) {
            flag++;
            et_name.setError("Empty");
        }
        if (TextUtils.isEmpty(rollno)) {
            flag++;
            et_roll_num.setError("Empty");
        }
        if (!isValidEmail(email)) {
            flag++;
            et_email.setError("Invalid Email");
        }
        if (!isValidMobile(mobile)) {
            flag++;
            et_mobile.setError("Invalid Mobile Number.");
        }
        if (flag == 0) {
            progressDialog.setMessage("Registering. Please Wait...");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, mobile)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseAuthException e;
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                et_name.setText("");
                                et_mobile.setText("");
                                et_roll_num.setText("");
                                et_email.setText("");
                                Intent i = new Intent(SignUp.this, Login.class);
                                startActivity(i);
                                finish();

                            } else {
                                e = (FirebaseAuthException) task.getException();
                                Toast.makeText(getApplicationContext(), "FAILED :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                System.out.println(e.getMessage());
                                Log.e("LoginActivity", "Failed Registration", e);
                            }

                        }
                    });
            return;
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobile(String pass) {
        return pass != null && pass.length() == 10;
    }
}
