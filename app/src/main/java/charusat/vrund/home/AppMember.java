package charusat.vrund.home;

/**
 * Created by iharsh on 1/10/17.
 */

class AppMember {
    private int imageResourceId;
    private int appTeamMemberLabelResourceId;

    AppMember(int imageResourceId, int appTeamMemberLabelResourceId) {
        this.imageResourceId = imageResourceId;
        this.appTeamMemberLabelResourceId = appTeamMemberLabelResourceId;
    }

    int getImageResourceId() {
        return imageResourceId;
    }

    public int appTeamMemberLabelResourceId() {
        return appTeamMemberLabelResourceId;
    }
}
