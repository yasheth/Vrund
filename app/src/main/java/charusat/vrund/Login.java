package charusat.vrund;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button register, login;
    TextView rollNumber, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (Button) findViewById(R.id.bt_register);
        login = (Button) findViewById(R.id.bt_login);

        rollNumber = (TextView) findViewById(R.id.et_rollno);
        mobile = (TextView) findViewById(R.id.et_mobile);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i;
                if (rollNumber.getText().toString().equals("14IT120")) {
                    i = new Intent(Login.this, MainActivity_Organiser.class);
                }
                else{
                    i = new Intent(Login.this, MainActivity.class);
                }
                startActivity(i);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });
    }

    public void onBackPressed() {
        alertMessage();
    }

    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
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
