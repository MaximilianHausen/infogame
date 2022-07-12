package net.totodev.infogame.player;

import net.totodev.infoengine.resources.image.ImageResource;

public class Animation {
    private final ImageResource[] images;
    private int currentIndex = 0;

    public Animation(ImageResource... images) {
        this.images = images;
    }

    public ImageResource nextImage() {
        currentIndex++;
        if (currentIndex >= images.length) currentIndex = 0;
        return images[currentIndex];
    }
}
