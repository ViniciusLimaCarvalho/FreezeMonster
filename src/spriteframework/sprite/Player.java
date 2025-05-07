package spriteframework.sprite;

import javax.swing.ImageIcon;

import spriteframework.Commons;

import java.awt.Image;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private int width;
    private int height;

    public Player() {
        loadImage();
		getImageDimensions();
		resetState();
    }

    protected void loadImage () {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/woody.png"));
        Image scaledImage = ii.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH);
        setImage(scaledImage);
        width = scaledImage.getWidth(null);
        height = scaledImage.getHeight(null);
    }
    
    public void act() {

        x += dx;

        y += dy;

        if (y < 0){
            y = 0;
        }

        if(y > Commons.GROUND -   2 * height ){
            y = Commons.GROUND -   2 * height ;
        }

        if (x < Commons.BORDER_LEFT)  {

            x = Commons.BORDER_LEFT;
        }

        if (x >= Commons.BOARD_WIDTH -  Commons.BORDER_RIGHT - width) {

            x = Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - width;
        }



    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {

            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {

            dy = 0;
        }
    }
    private void resetState() {

        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }
}
