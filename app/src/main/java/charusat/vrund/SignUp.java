package charusat.vrund;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.TextView;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Rollno = "rollnoKey";
    public static final String ID = "idKey";
    public static final String Comp = "compKey";
    public static final String Organiser = "organiserKey";
    private final String TAG = "Sign Up Fragment";
    String rollno, email, p_id, name, phone, gender, role;
    boolean ioc, organiser = false;
    SharedPreferences sharedpreferences;
    private EditText et_email;
    private EditText et_roll_num;
    private EditText et_name;
    private EditText et_phoneno;
    private RadioButton rb_student;
    private RadioButton rb_faculty;
    private RadioButton rb_male;
    private RadioButton rb_female;
    private CheckBox cb_ioc;
    private Button register;
    private TextView tv_login;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        et_email = (EditText) findViewById(R.id.et_email);
        et_roll_num = (EditText) findViewById(R.id.et_rollno);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phoneno = (EditText) findViewById(R.id.et_phoneno);

        rb_faculty = (RadioButton) findViewById(R.id.rb_faculty);
        rb_student = (RadioButton) findViewById(R.id.rb_student);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);

        cb_ioc = (CheckBox) findViewById(R.id.checkBox_ioc);

        register = (Button) findViewById(R.id.bt_register);

        tv_login = (TextView) findViewById(R.id.tv_login);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){
                    registerUser();
                }else{
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }


            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }
    private void registerUser() {
        int flag = 0;
        email = et_email.getText().toString().trim();
        rollno = et_roll_num.getText().toString().toUpperCase().trim();
        name = et_name.getText().toString().toUpperCase().trim();
        phone = et_phoneno.getText().toString().trim();
        gender = null;
        role = null;
        ioc = false;



        if (TextUtils.isEmpty(email)) {
            flag++;
            et_email.setError("Field is Empty");
        }
        if (TextUtils.isEmpty(name)) {
            flag++;
            et_name.setError("Field is Empty");
        }
        if (TextUtils.isEmpty(phone)) {
            flag++;
            et_phoneno.setError("Field is Empty");
        }
        if(rb_female.isChecked()){
            gender = "Female";
            p_id = "F";
        }else{
            gender = "Male";
            p_id = "M";
        }
        if(rb_student.isChecked()){
            role = "Student";
            p_id = p_id + "S_";
        }else{
            role = "Faculty";
            p_id = p_id + "F_";
        }
        if(cb_ioc.isChecked()){
            ioc = true;
        }
        if (TextUtils.isEmpty(rollno)) {
            flag++;
            et_roll_num.setError("Field is Empty");
        }
        if (!isValidMobile(phone)) {
            flag++;
            et_phoneno.setError("Enter Valid phone number");
        }
        if (!isValidEmail(email)) {
            flag++;
            et_email.setError("Enter Valid email id");
        }
        if (flag == 0) {
            progressDialog.setMessage("Registering. Please Wait...");
            progressDialog.show();

            p_id = p_id + rollno;


            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(rollno)){
                        Toast.makeText(getApplicationContext(),"Already Registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        databaseRef.child(rollno).setValue(new User(email, name, phone, role, gender, ioc, p_id, organiser)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString(Name, name);
                                editor.putString(Rollno, rollno);
                                editor.putString(ID, p_id);
                                editor.putBoolean(Comp,ioc);
                                editor.putBoolean(Organiser, organiser);
                                editor.commit();

                                Intent i = new Intent(SignUp.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    System.out.println(databaseError.getMessage());
                }
            });

//            databaseRef.child(rollno).setValue(new User(email, name, phone, role, gender, ioc,p_id)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(SignUp.this, Login.class);
//                                startActivity(i);
//                                finish();
//                            }
//                        });


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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}