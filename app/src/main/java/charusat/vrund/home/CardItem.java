package charusat.vrund.home;

/**
 * Created by iharsh on 28/9/17.
 */

public class CardItem {
    private String imageResourceUrl;
    private int textLabel;

    public CardItem(String imageResourceUrl, int textLabel) {
        this.imageResourceUrl = imageResourceUrl;
        this.textLabel = textLabel;
    }

    String getImageResourceUrl() {
        return imageResourceUrl;
    }

    int getTextLabel() {
        return textLabel;
    }
}
