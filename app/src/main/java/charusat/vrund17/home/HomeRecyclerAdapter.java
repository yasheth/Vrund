package charusat.vrund17.home;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import charusat.vrund17.R;

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
        holder.homeCardTextView.setText(currentItem.getTextLabel());
        Picasso.with(holder.homeCardImageView.getContext())
                .load(currentItem.getImageResourceId())
                .into(holder.homeCardImageView);

        if (position == (cardItemArrayList.size() - 1)) {
            AppMember[] appMembers = new AppMember[5];
            appMembers[0] = new AppMember(R.drawable.app_yash_sheth, R.string.app_member_yash_sheth);
            appMembers[1] = new AppMember(R.drawable.app_harsh_shah, R.string.app_member_harsh_shah);
            appMembers[2] = new AppMember(R.drawable.app_yash_sodha, R.string.app_member_yash_sodha);
            appMembers[3] = new AppMember(R.drawable.app_umang_patel, R.string.app_member_umang_patel);
            appMembers[4] = new AppMember(R.drawable.app_mitkumar_patel, R.string.app_member_mit_patel);

            holder.appTeamCardView.setVisibility(View.VISIBLE);
            holder.appTeamRecyclerView.setHasFixedSize(true);
            LinearLayoutManager manager = new LinearLayoutManager(holder.appTeamCardView.getContext());
            holder.appTeamRecyclerView.setLayoutManager(manager);
            AppTeamRecyclerAdapter adapter = new AppTeamRecyclerAdapter(appMembers);
            holder.appTeamRecyclerView.setAdapter(adapter);

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
        RecyclerView appTeamRecyclerView;
        LinearLayout kalashLayout;
        ImageView homeCardImageView;
        TextView homeCardTextView;

        ItemsHolder(View itemView) {
            super(itemView);
            appTeamCardView = (CardView) itemView.findViewById(R.id.home_app_team_card_view);
            appTeamRecyclerView = (RecyclerView) itemView.findViewById(R.id.home_app_team_recycler_view);
            kalashLayout = (LinearLayout) itemView.findViewById(R.id.kalash_layout);
            homeCardImageView = (ImageView) itemView.findViewById(R.id.home_image_card_image_view);
            homeCardTextView = (TextView) itemView.findViewById(R.id.home_text_card_text_view);
            appTeamRecyclerView.setNestedScrollingEnabled(false);
        }
    }
}
