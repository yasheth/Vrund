package charusat.vrund17.home;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import charusat.vrund17.R;

/**
 * Created by iharsh on 30/9/17.
 */

class AppTeamRecyclerAdapter extends RecyclerView.Adapter<AppTeamRecyclerAdapter.MemberHolder> {

    private AppMember[] appMembers;

    AppTeamRecyclerAdapter(AppMember[] appMembers) {
        this.appMembers = appMembers;
    }

    @Override
    public AppTeamRecyclerAdapter.MemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_team_member, parent, false);
        return new MemberHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(AppTeamRecyclerAdapter.MemberHolder holder, int position) {
        AppMember currentMember = appMembers[position];
        Picasso
                .with(holder.appMemberImageView.getContext())
                .load(currentMember.getImageResourceId())
                .into(holder.appMemberImageView);
        holder.appMemberTextView.setText(currentMember.appTeamMemberLabelResourceId());
    }

    @Override
    public int getItemCount() {
        return appMembers.length;
    }

    class MemberHolder extends RecyclerView.ViewHolder {

        ImageView appMemberImageView;
        TextView appMemberTextView;

        MemberHolder(View itemView) {
            super(itemView);
            appMemberImageView = (ImageView) itemView.findViewById(R.id.app_member_image_view);
            appMemberTextView = (TextView) itemView.findViewById(R.id.app_member_text_view);
        }
    }
}
