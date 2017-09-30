package charusat.vrund;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button register, login;
    TextView rollNumber, mobile;

    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    private final String TAG = "Login";

    SharedPreferences sharedpreferences;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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


                    final String rollno = rollNumber.getText().toString().trim();
                    final String phone = mobile.getText().toString().trim();

                    databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(rollno)) {
                                Log.d(TAG, phone + "   " + dataSnapshot.child(rollno).child("phone").getValue());
                                if (dataSnapshot.child(rollno).child("phone").getValue().equals(phone)) {

                                    Log.d(TAG, dataSnapshot.child(rollno).child("name").getValue().toString());

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(SignUp.Name, dataSnapshot.child(rollno).child("name").getValue().toString());
                                    editor.putString(SignUp.Rollno, rollno);
                                    editor.putString(SignUp.ID, dataSnapshot.child(rollno).child("p_id").getValue().toString());
                                    editor.putBoolean(SignUp.Comp, (Boolean) dataSnapshot.child(rollno).child("ioc").getValue());
                                    editor.putBoolean(SignUp.Organiser, (Boolean) dataSnapshot.child(rollno).child("organiser").getValue());
                                    editor.commit();

                                    if (sharedpreferences.getBoolean(SignUp.Organiser, false)) {
                                        i = new Intent(Login.this, MainActivity_Organiser.class);

                                    } else {
                                        i = new Intent(Login.this, MainActivity.class);
                                    }
                                    startActivity(i);
                                    finish();

                                } else {

                                    Log.d(TAG, "Wrong Phone Number");
                                }


                            } else {
                                Log.d(TAG, "Wrong Roll Number");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

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
}
