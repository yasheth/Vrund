package charusat.vrund;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignUp extends AppCompatActivity {

    private EditText et_email, et_password, et_confirm_password, et_roll_num;
    private Button register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_roll_num = (EditText) findViewById(R.id.et_rollno);
        et_confirm_password = (EditText) findViewById(R.id.et_confpass);
        register = (Button) findViewById(R.id.bt_register);
        mAuth = FirebaseAuth.getInstance();

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
        String pass = et_password.getText().toString().trim();
        String conf_pass = et_confirm_password.getText().toString().trim();
        String rollno = et_roll_num.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            flag++;
            et_email.setError("Empty");
        }
        if (TextUtils.isEmpty(pass)) {
            flag++;
            et_password.setError("Empty");
        }
        if (TextUtils.isEmpty(conf_pass)) {
            flag++;
            et_confirm_password.setError("Empty");
        }
        if (TextUtils.isEmpty(rollno)) {
            flag++;
            et_roll_num.setError("Empty");
        }
        if (flag > 0)
            return;

        if (pass.equals(conf_pass)) {

            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseAuthException e;
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                et_password.setText("");
                                et_confirm_password.setText("");
                                et_roll_num.setText("");
                                et_email.setText("");

                            } else {
                                e = (FirebaseAuthException) task.getException();
                                Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                                System.out.println(e.getMessage());
                                Log.e("LoginActivity", "Failed Registration", e);
                            }

                        }
                    });
            return;
        } else {
            et_password.setError("password and confirm password doesnt match");
            return;
        }
    }
}
