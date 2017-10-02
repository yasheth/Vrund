package charusat.vrund17;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private final String TAG = "Login";
    Button register, login;
    TextView rollNumber, mobile;
    SharedPreferences sharedpreferences;
    Intent i;
    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        sharedpreferences = getSharedPreferences(SignUp.MyPREFERENCES, Context.MODE_PRIVATE);
        String  x = sharedpreferences.getString(SignUp.Rollno,null);
        if(sharedpreferences.getString(SignUp.Rollno,null) == null) {

            register = (Button) findViewById(R.id.bt_register);
            login = (Button) findViewById(R.id.bt_login);

            rollNumber = (TextView) findViewById(R.id.et_rollno);
            mobile = (TextView) findViewById(R.id.et_mobile);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flag = 0;
                    final String rollno = rollNumber.getText().toString().trim();
                    final String phone = mobile.getText().toString().trim();

                    if (TextUtils.isEmpty(rollno)) {
                        flag++;
                        rollNumber.setError("Field is Empty");
                    }
                    if (TextUtils.isEmpty(phone)) {
                        flag++;
                        mobile.setError("Field is Empty");
                    }
                    if(flag == 0 && isNetworkAvailable())
                    {
                        progressDialog.setMessage("Logging In. Please Wait...");
                        progressDialog.show();

                        DatabaseReference db_roll = databaseRef.child(rollno);

                        db_roll.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                Object o = dataSnapshot.child("phone").getValue();
                                if (o == null)
                                {
                                    Toast.makeText(Login.this, "Wrong Roll Number", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    progressDialog.cancel();
                                    return;
                                }
                                else if (o.equals(phone))
                                {
                                    Log.d(TAG, dataSnapshot.child("name").getValue().toString());

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(SignUp.Name, dataSnapshot.child("name").getValue().toString());
                                    editor.putString(SignUp.Rollno, rollno);
                                    editor.putString(SignUp.ID, dataSnapshot.child("p_id").getValue().toString());
                                    editor.putBoolean(SignUp.Comp, (Boolean) dataSnapshot.child("ioc").getValue());
                                    editor.putBoolean(SignUp.Organiser, (Boolean) dataSnapshot.child("organiser").getValue());
                                    editor.commit();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Wrong Phone Number", Toast.LENGTH_SHORT).show();
                                }
                                if (sharedpreferences.getBoolean(SignUp.Organiser, false))
                                {
                                    i = new Intent(Login.this, MainActivity_Organiser.class);
                                } else {
                                    i = new Intent(Login.this, MainActivity.class);
                                }
                                progressDialog.dismiss();
                                progressDialog.cancel();
                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        });


                    /*
                    if (rollNumber.getText().toString().equals("14IT120")) {

                        i = new Intent(Login.this, MainActivity_Organiser.class);
                    }
                    else{
                        i = new Intent(Login.this, MainActivity.class);
                    }
                    */
                    }
                    if (!isNetworkAvailable()) {
                        Toast.makeText(getApplicationContext(), "Check Internet Connectivity", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Login.this, SignUp.class);
                    startActivity(i);
                }
            });
        } else {
            if (sharedpreferences.getBoolean(SignUp.Organiser, false)) {
                i = new Intent(Login.this, MainActivity_Organiser.class);

            } else {
                i = new Intent(Login.this, MainActivity.class);
            }
            startActivity(i);
            finish();
        }
    }

    /*
    public void onBackPressed() {
        alertMessage();
    }

    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // No button clicked do nothing
                        Toast.makeText(Login.this, "Good Choice", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit? :(").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }
    */

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
