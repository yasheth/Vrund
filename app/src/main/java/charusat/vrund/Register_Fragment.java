package charusat.vrund;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register_Fragment extends Fragment implements View.OnClickListener {

    View v;
    private EditText et_email, et_password, et_confirm_password, et_roll_num;
    private Button register;
    private FirebaseAuth mAuth;
    public Register_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_roll_num = (EditText) view.findViewById(R.id.et_rollno);
        et_confirm_password = (EditText) view.findViewById(R.id.et_confpass);
        register = (Button) view.findViewById(R.id.bt_register);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == register) {
            registerUser();
        }
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
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseAuthException e;
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity().getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                et_password.setText("");
                                et_confirm_password.setText("");
                                et_roll_num.setText("");
                                et_email.setText("");
                                MainActivity ma = (MainActivity) getActivity();
                                ma.tabLayout.setScrollPosition(2, 0, true);
                                ma.viewPager.setCurrentItem(2);
                            } else {
                                e = (FirebaseAuthException) task.getException();
                                Toast.makeText(getActivity().getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
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
