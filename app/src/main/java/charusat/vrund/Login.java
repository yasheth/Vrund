package charusat.vrund;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
}
