package charusat.vrund17;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {

    SharedPreferences sharedpreferences;
    private TextView p_rollno;
    private TextView p_name;
    private TextView p_comp;
    private TextView p_id;
    private Switch sth_participate;
    private Button btn_logout;
    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    private String rollno, id;

    public Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedpreferences = getActivity().getSharedPreferences(SignUp.MyPREFERENCES, Context.MODE_PRIVATE);

        p_rollno = (TextView) view.findViewById(R.id.p_rollno);
        p_name = (TextView) view.findViewById(R.id.p_name);
        p_comp = (TextView) view.findViewById(R.id.p_comp);
        p_id = (TextView) view.findViewById(R.id.p_id);

        String name = sharedpreferences.getString(SignUp.Name, null);
        rollno = sharedpreferences.getString(SignUp.Rollno, null);
        boolean ioc = sharedpreferences.getBoolean(SignUp.Comp, false);
        id = sharedpreferences.getString(SignUp.ID, null);

        btn_logout = (Button) view.findViewById(R.id.btn_logout);

        sth_participate = (Switch) view.findViewById(R.id.sth_participate);

        sth_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    if (sth_participate.isChecked()) {
                        databaseRef.child(rollno).child("ioc").setValue(true);
                        p_comp.setText("Participating in Dressing and Performance competitions");
                        p_id.setVisibility(View.VISIBLE);
                        p_id.setText("Participation Id : " + id);

                    } else {
                        databaseRef.child(rollno).child("ioc").setValue(false);
                        p_comp.setText("Not Participating in Dressing and Performance competitions");
                        p_id.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("P", "Logout click");
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent i = new Intent(getActivity(), Login.class);
                                startActivity(i);
                                getActivity().finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE: // No button clicked do nothing
                                Toast.makeText(getActivity(), "Good Choice", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to Log Out?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();

            }
        });


        p_rollno.setText("Roll Number : " + rollno);
        p_name.setText("Name : " + name);
        if (ioc) {
            p_comp.setText("Participating in Dressing and Performance competitions");
            p_id.setText("Participation Id : " + id);
            sth_participate.setChecked(true);
        } else {
            p_comp.setText("Not Participating in Dressing and Performance competitions");
            p_id.setVisibility(View.INVISIBLE);
            sth_participate.setChecked(false);
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
