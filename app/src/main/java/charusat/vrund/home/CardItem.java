package charusat.vrund.home;

/**
 * Created by iharsh on 28/9/17.
 */

public class CardItem {
    private String imageResourceUrl;
    private int imageResourceId;
    private int textLabel;

    public CardItem(String imageResourceUrl, int imageResourceId, int textLabel) {
        this.imageResourceUrl = imageResourceUrl;
        this.imageResourceId = imageResourceId;
        this.textLabel = textLabel;
    }

    String getImageResourceUrl() {
        return imageResourceUrl;
    }

    int getImageResourceId() {
        return imageResourceId;
    }

    int getTextLabel() {
        return textLabel;
    }
}
