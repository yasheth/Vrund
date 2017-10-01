package charusat.vrund.home;

/**
 * Created by iharsh on 28/9/17.
 */

public class CardItem {
    private int imageResourceId;
    private int textLabel;

    public CardItem(int imageResourceId, int textLabel) {
        this.imageResourceId = imageResourceId;
        this.textLabel = textLabel;
    }


    int getImageResourceId() {
        return imageResourceId;
    }

    int getTextLabel() {
        return textLabel;
    }
}
