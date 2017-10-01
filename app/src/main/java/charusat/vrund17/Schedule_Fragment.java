package charusat.vrund17;


import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Schedule_Fragment extends Fragment {

    Button calender;

    public Schedule_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calender = (Button) view.findViewById(R.id.btn_calender);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2017, 9, 6, 8, 30);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2017, 9, 6, 23, 30);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Vrund")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Annual Garba Fest\nWear Traditional Dress\nGreetings from DSC AppTeam")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "Centre Lawn")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(), "Successsfullyy Added Event to your Calender", Toast.LENGTH_SHORT).show();
            }
        });


    }


}

