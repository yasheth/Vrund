package charusat.vrund;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {

    private TextView p_rollno;
    private TextView p_name;
    private TextView p_comp;
    private TextView p_id;

    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

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

        p_rollno = (TextView) view.findViewById(R.id.p_rollno);
        p_name = (TextView) view.findViewById(R.id.p_name);
        p_comp = (TextView) view.findViewById(R.id.p_comp);
        p_id = (TextView) view.findViewById(R.id.p_id);

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(SignUp.MyPREFERENCES, Context.MODE_PRIVATE);

        String name = sharedpreferences.getString(SignUp.Name, null);
        String rollno = sharedpreferences.getString(SignUp.Rollno,null);
        boolean ioc = sharedpreferences.getBoolean(SignUp.Comp,false);
        String id = sharedpreferences.getString(SignUp.ID,null);

        p_rollno.setText("Roll Number : " + rollno);
        p_name.setText("Name : " + name);
        if(ioc){
            p_comp.setText("Participating in Dressing and Performance competitions");
            p_id.setText("Participation Id : " + id);
        } else {
            p_comp.setText("Not Participating in Dressing and Performance competitions");
            p_id.setVisibility(View.INVISIBLE);
        }
    }
}
