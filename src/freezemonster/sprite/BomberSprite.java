package freezemonster.sprite;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.BadnessBoxSprite;

public class BomberSprite extends BadnessBoxSprite {

    private Bomb bomb;

    private boolean dead = false;


    public boolean isDead() {
        return dead;
    }


    public void setDead(boolean dead) {
        this.dead = dead;
    }

    private int dx = 0;
    private int dy = 0;

    public String getAlienImg() {
        return alienImg;
    }

    public void setDeadAlienImg(){
        this.alienImg = alienImg.replace(".png", "bg.png");
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienImg));
        Image scaledImage = ii.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    public void setAlienImg(String alienImg) {
        this.alienImg = alienImg;
    }

    private String alienImg;

    @Override
    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    @Override
    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public BomberSprite(int x, int y) {

        initBomber(x, y);
    }

    private void initBomber(int x, int y) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        Random rand = new Random();
        int monsterNumber = rand.nextInt(7) + 1;

        alienImg = "/images/monster" + monsterNumber + ".png";
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienImg));
        Image scaledImage = ii.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        setImage(scaledImage);
    }



    public Bomb getBomb() {

        return bomb;
    }


	@Override
	public LinkedList<BadSprite> getBadnesses() {
		LinkedList<BadSprite> aBomb = new LinkedList<BadSprite>();
		aBomb.add(bomb);
		return aBomb;
	}
}
