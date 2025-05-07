package spaceinvaders.sprite;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;

import java.awt.*;

public class Bomb extends BadSprite {

    private boolean destroyed;

    public Bomb(int x, int y) {

        initBomb(x, y);
    }

    private void initBomb(int x, int y) {

        setDestroyed(true);

        this.x = x;
        this.y = y;

        String bombImg = "/images/gosma.png";
        ImageIcon ii = new ImageIcon(this.getClass().getResource(bombImg));
        Image scaledImage = ii.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {

        return destroyed;
    }
    
    
}
