package charusat.vrund;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import charusat.vrund.home.CardItem;
import charusat.vrund.home.HomeRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    private ArrayList<CardItem> cardItemArrayList = new ArrayList<>();

    public Home_Fragment() {
        // Required empty public constructor
        setArrayListItems();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflateView = inflater.inflate(R.layout.fragment_home, container, false);
        //TODO: Display Content
        RecyclerView homeRecyclerView = (RecyclerView) inflateView.findViewById(R.id.home_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(inflateView.getContext());
        homeRecyclerView.setLayoutManager(layoutManager);
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(cardItemArrayList);
        homeRecyclerView.setAdapter(adapter);
        return inflateView;

    }

    private void setArrayListItems() {

        cardItemArrayList.add(new CardItem(R.drawable.home_image_1, R.string.home_title_string));
        cardItemArrayList.add(new CardItem(R.drawable.home_image_2, R.string.home_about_one_string));
        cardItemArrayList.add(new CardItem(R.drawable.home_image_3, R.string.home_about_two_string));
        cardItemArrayList.add(new CardItem(R.drawable.home_image_4, R.string.home_about_three_string));
        cardItemArrayList.add(new CardItem(R.drawable.home_image_5, R.string.home_about_four_string));
        cardItemArrayList.add(new CardItem(R.drawable.home_image_6, R.string.home_about_five_string));
        cardItemArrayList.add(new CardItem(R.drawable.home_image_7, R.string.home_about_six_string));
        cardItemArrayList.add(new CardItem(R.drawable.home_image_8, R.string.home_about_seven_string));
    }

}
