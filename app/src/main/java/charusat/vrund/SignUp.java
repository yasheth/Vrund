package charusat.vrund;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    private final String TAG = "Sign Up Fragment";

    private EditText et_email;
    private EditText et_password;
    private EditText et_confirm_password;
    private EditText et_roll_num;
    private EditText et_name;
    private EditText et_phoneno;

    private RadioButton rb_student;
    private RadioButton rb_faculty;
    private RadioButton rb_male;
    private RadioButton rb_female;

    private CheckBox cb_ioc;

    private Button register;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    String rollno, email, pass, conf_pass, name, phone, gender, role;
    boolean ioc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_roll_num = (EditText) findViewById(R.id.et_rollno);
        et_confirm_password = (EditText) findViewById(R.id.et_confpass);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phoneno = (EditText) findViewById(R.id.et_phoneno);

        rb_faculty = (RadioButton) findViewById(R.id.rb_faculty);
        rb_student = (RadioButton) findViewById(R.id.rb_student);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);

        cb_ioc = (CheckBox) findViewById(R.id.checkBox_ioc);

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
        email = et_email.getText().toString().trim();
        pass = et_password.getText().toString().trim();
        conf_pass = et_confirm_password.getText().toString().trim();
        rollno = et_roll_num.getText().toString().trim();
        name = et_name.getText().toString().trim();
        phone = et_phoneno.getText().toString().trim();
        gender = null;
        role = null;
        ioc = false;



        if (TextUtils.isEmpty(email)) {
            flag++;
            et_email.setError("Filled is Mandatory");
        }
        if (TextUtils.isEmpty(name)) {
            flag++;
            et_name.setError("Filled is Mandatory");
        }
        if (TextUtils.isEmpty(phone)) {
            flag++;
            et_phoneno.setError("Filled is Mandatory");
        }
        if(rb_female.isChecked()){
            gender = "Female";
        }else{
            gender = "Male";
        }
        if(rb_student.isChecked()){
            role = "Student";
        }else{
            role = "Student";
        }
        if(cb_ioc.isChecked()){
            ioc = true;
        }
        if (TextUtils.isEmpty(pass)) {
            flag++;
            et_password.setError("Filled is Mandatory");
        }
        if (TextUtils.isEmpty(conf_pass)) {
            flag++;
            et_confirm_password.setError("Filled is Mandatory");
        }
        if (TextUtils.isEmpty(rollno)) {
            flag++;
            et_roll_num.setError("Filled is Mandatory");
        }
        if (flag > 0)
            return;

        if (pass.equals(conf_pass)) {

           databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(rollno)){
                        Toast.makeText(getApplicationContext(),"Already Registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        databaseRef.child(rollno).setValue(new User(email, pass, name, phone, role, gender, ioc)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            });



            return;
        } else {
            et_password.setError("Password and Confirm Password must be same");
            return;
        }
    }
}
