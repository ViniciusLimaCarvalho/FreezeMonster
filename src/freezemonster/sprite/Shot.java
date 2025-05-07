package freezemonster.sprite;

import javax.swing.ImageIcon;

import spriteframework.sprite.BadSprite;

import java.awt.*;


public class Shot extends BadSprite {

    private int dx = 0;

    private int dy = -2;

    public Shot() {
    }

    public Shot(int x, int y, int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        initShot(x, y);
    }

    public void update(){
        setX(getX() + dx);
        setY(getY() + dy);
    }

    private void initShot(int x, int y) {
        String shotImg = "/images/ray.png";
        ImageIcon ii = new ImageIcon(this.getClass().getResource(shotImg));
        Image scaledImage = ii.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        setImage(scaledImage);

        int offset = 10;

        // Centraliza o tiro dependendo da direção
        if (dx < 0) { // Esquerda
            setX(x - offset);
            setY(y);
        } else if (dx > 0) { // Direita
            setX(x + offset);
            setY(y);
        } else if (dy < 0) { // Cima
            setX(x);
            setY(y - offset);
        } else if (dy > 0) { // Baixo
            setX(x);
            setY(y + offset);
        } else {
            // Se dx = 0 e dy = 0, dispara para cima por padrão
            dy = -2;
            setX(x);
            setY(y - offset);
        }
    }

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
}
