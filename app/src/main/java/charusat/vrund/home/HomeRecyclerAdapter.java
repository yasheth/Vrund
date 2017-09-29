package charusat.vrund.home;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import charusat.vrund.R;

/**
 * Created by iharsh on 28/9/17.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ItemsHolder> {

    private ArrayList<CardItem> cardItemArrayList;

    public HomeRecyclerAdapter(ArrayList<CardItem> cardItemArrayList) {
        //Default constructor
        this.cardItemArrayList = cardItemArrayList;
    }

    @Override
    public HomeRecyclerAdapter.ItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view_layout, parent, false);
        return new ItemsHolder(inflateView);
    }


    @Override
    public void onBindViewHolder(HomeRecyclerAdapter.ItemsHolder holder, int position) {
        //TODO: Update items
        CardItem currentItem = cardItemArrayList.get(position);

        //Random background colour
        Random rnd = new Random();
        int redColour = rnd.nextInt(256);
        int greenColour = rnd.nextInt(256);
        int blueColour = rnd.nextInt(256);
        int color = Color.argb(255, redColour, greenColour, blueColour);

        holder.homeTextCardLayout.setBackgroundColor(color);

        if ((redColour * 0.299) + (greenColour * 0.587) + (blueColour * 0.114) > 186) {
            holder.homeCardTextView.setTextColor(Color.BLACK);
        } else {
            holder.homeCardTextView.setTextColor(Color.WHITE);
        }
        holder.homeCardTextView.setText(currentItem.getTextLabel());
        Picasso.with(holder.homeCardImageView.getContext())
                .load(currentItem.getImageResourceUrl())
                .placeholder(R.drawable.placeholder_image_loading)
                .into(holder.homeCardImageView);

        if (position == (cardItemArrayList.size() - 1)) {
            holder.kalashLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewDetachedFromWindow(ItemsHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.homeCardImageView.destroyDrawingCache();
        holder.homeCardTextView.destroyDrawingCache();
    }

    @Override
    public int getItemCount() {
        return cardItemArrayList.size();
    }

    class ItemsHolder extends RecyclerView.ViewHolder {

        RelativeLayout homeTextCardLayout;
        LinearLayout kalashLayout;
        ImageView homeCardImageView;
        TextView homeCardTextView;

        ItemsHolder(View itemView) {
            super(itemView);
            homeTextCardLayout = (RelativeLayout) itemView.findViewById(R.id.home_text_card_layout);
            kalashLayout = (LinearLayout) itemView.findViewById(R.id.kalash_layout);
            homeCardImageView = (ImageView) itemView.findViewById(R.id.home_image_card_image_view);
            homeCardTextView = (TextView) itemView.findViewById(R.id.home_text_card_text_view);
        }
    }
}
