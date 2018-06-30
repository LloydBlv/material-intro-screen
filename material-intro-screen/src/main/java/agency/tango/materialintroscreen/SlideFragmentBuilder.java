package agency.tango.materialintroscreen;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SlideFragmentBuilder {
    int backgroundColorResId;
    int backgroundColor;
    int buttonsColorResId;
    int buttonsColor;
    String title;
    String description;
    String[] neededPermissions;
    String[] possiblePermissions;
    int imageResId;
    String  imageUrl;

    public SlideFragmentBuilder backgroundColorResId(@ColorRes int backgroundColor) {
        this.backgroundColorResId = backgroundColor;
        return this;
    }

    public SlideFragmentBuilder backgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public SlideFragmentBuilder buttonsColorResId(@ColorRes int buttonsColor) {
        this.buttonsColorResId = buttonsColor;
        return this;
    }

    public SlideFragmentBuilder buttonsColor(int buttonsColor) {
        this.buttonsColor = buttonsColor;
        return this;
    }

    public SlideFragmentBuilder title(String title) {
        this.title = title;
        return this;
    }

    public SlideFragmentBuilder description(String description) {
        this.description = description;
        return this;
    }

    public SlideFragmentBuilder neededPermissions(String[] neededPermissions) {
        this.neededPermissions = neededPermissions;
        return this;
    }

    public SlideFragmentBuilder possiblePermissions(String[] possiblePermissions) {
        this.possiblePermissions = possiblePermissions;
        return this;
    }

    public SlideFragmentBuilder image(@DrawableRes int image) {
        this.imageResId = image;
        return this;
    }
    public SlideFragmentBuilder image(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public SlideFragment build() {
        return SlideFragment.createInstance(this);
    }
}
