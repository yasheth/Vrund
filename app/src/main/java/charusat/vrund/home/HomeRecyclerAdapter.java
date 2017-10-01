package charusat.vrund.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import charusat.vrund.R;

/**
 * Created by iharsh on 28/9/17.
 * Adapter class for Home fragment layout
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
        holder.homeCardTextView.setText(currentItem.getTextLabel());
        Picasso.with(holder.homeCardImageView.getContext())
                .load(currentItem.getImageResourceId())
                .placeholder(R.drawable.placeholder_image_loading)
                .into(holder.homeCardImageView);

        if (position == (cardItemArrayList.size() - 1)) {
            holder.appTeamCardView.setVisibility(View.VISIBLE);
            holder.kalashLayout.setVisibility(View.VISIBLE);
        } else {
            holder.kalashLayout.setVisibility(View.GONE);
            holder.appTeamCardView.setVisibility(View.GONE);
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

        CardView appTeamCardView;
        LinearLayout kalashLayout;
        ImageView homeCardImageView;
        TextView homeCardTextView;

        ItemsHolder(View itemView) {
            super(itemView);
            appTeamCardView = (CardView) itemView.findViewById(R.id.home_app_team_card_view);
            kalashLayout = (LinearLayout) itemView.findViewById(R.id.kalash_layout);
            homeCardImageView = (ImageView) itemView.findViewById(R.id.home_image_card_image_view);
            homeCardTextView = (TextView) itemView.findViewById(R.id.home_text_card_text_view);
        }
    }
}
