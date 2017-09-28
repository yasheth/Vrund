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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflateView = inflater.inflate(R.layout.fragment_home, container, false);
        //TODO: Display Content
        setArrayListItems();
        RecyclerView homeRecyclerView = (RecyclerView) inflateView.findViewById(R.id.home_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(inflateView.getContext());
        homeRecyclerView.setLayoutManager(layoutManager);
        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(cardItemArrayList);
        homeRecyclerView.setAdapter(adapter);
        return inflateView;

    }

    private void setArrayListItems() {

        //DUMMY LINKS
        cardItemArrayList.add(new CardItem("https://i.pinimg.com/originals/c2/24/ac/c224ac793226ce5fca01277c51c6129a.jpg", "Item 1"));
        cardItemArrayList.add(new CardItem("https://cdn.dribbble.com/users/230193/screenshots/1793458/google-dribbble.jpg", "Item 2"));
        cardItemArrayList.add(new CardItem("https://cdn.dribbble.com/users/230193/screenshots/2914784/archery-dribbble_1x.jpg", "Item 3"));
        cardItemArrayList.add(new CardItem("http://www.gizmobolt.com/wp-content/uploads/2014/10/bkg_03_march-900x900.jpg", "Item 4"));
        cardItemArrayList.add(new CardItem("http://www.gizmobolt.com/wp-content/uploads/2014/10/bkg_06_june-900x900.jpg", "Item 5"));
        cardItemArrayList.add(new CardItem("http://www.gizmobolt.com/wp-content/uploads/2014/10/bkg_05_may-900x900.jpg", "Item 6"));
        cardItemArrayList.add(new CardItem("http://www.gizmobolt.com/wp-content/uploads/2014/10/bkg_10_october-900x900.jpg", "Item 7"));
        cardItemArrayList.add(new CardItem("https://wallpapershome.com/images/pages/ico_h/3434.jpg", "Item 8"));
        cardItemArrayList.add(new CardItem("https://articles-images.sftcdn.net/wp-content/uploads/sites/3/2016/04/google-calendar-goals-664x374-664x374.jpg", "Item 9"));
        cardItemArrayList.add(new CardItem("https://i.pinimg.com/736x/81/24/9b/81249b49ff7c1541ff73709fb6b43ff8--calendar-app-google-calendar.jpg", "Item 10"));
    }

}
