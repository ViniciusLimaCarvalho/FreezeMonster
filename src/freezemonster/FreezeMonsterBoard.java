package freezemonster;


import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;

import spriteframework.AbstractBoard;
import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;

import freezemonster.sprite.*;

public class FreezeMonsterBoard extends AbstractBoard{
    //define sprites
    //private List<BadSprite> aliens;
    private Shot shot;    
    
    // define global control vars   
    private int direction = -1;
    private int deaths = 0;


    private String explImg = "/images/explosion.png";




    protected void createBadSprites() {  // cria 10 monstros em posições aleatórias
        Random rand = new Random();
        int totalAliens = 7;

        for (int i = 0; i < totalAliens; i++) {
            int x = rand.nextInt(Commons.BOARD_WIDTH - Commons.ALIEN_WIDTH);
            int y = rand.nextInt(Commons.GROUND / 2); // evita gerar muito perto do chão

            BomberSprite alien = new BomberSprite(x, y);
            badSprites.add(alien);
        }
    }
    
    protected void createOtherSprites() {
        shot = new Shot();
    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    // Override
    protected void drawOtherSprites(Graphics g) {
            drawShot(g);
    }
    
    protected void processOtherSprites(Player player, KeyEvent e) {
		int x = player.getX();
		int y = player.getY();

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {

			if (inGame) {

				if (!shot.isVisible()) {

					shot = new Shot(x, y, player.getDx(), player.getDy());
				}
			}
		}
	}

//    private void gameOver(Graphics g) {
//
//        g.setColor(Color.black);
//        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
//
//        g.setColor(new Color(0, 32, 48));
//        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
//        g.setColor(Color.white);
//        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
//
//        Font small = new Font("Helvetica", Font.BOLD, 14);
//        FontMetrics fontMetrics = this.getFontMetrics(small);
//
//        g.setColor(Color.white);
//        g.setFont(small);
//        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
//                Commons.BOARD_WIDTH / 2);
//    }

    protected void update() {
        if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // Player
        for (Player player : players)
            player.act();

        // Shot
        if (shot.isVisible()) {
            int shotX = shot.getX() + shot.getDx();
            int shotY = shot.getY() + shot.getDy();

            for (BadSprite alien : badSprites) {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Commons.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

                        BomberSprite bomber = (BomberSprite) alien;
                        bomber.setDeadAlienImg();
                        bomber.setDx(0);
                        bomber.setDy(0);
                        bomber.setDead(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            if (shotY < 0 || shotY > Commons.BOARD_HEIGHT || shotX < 0 || shotX > Commons.BOARD_WIDTH) {
                shot.die();
            } else {
                shot.setX(shotX);
                shot.setY(shotY);
            }
        }

        // Aliens
        Iterator<BadSprite> it = badSprites.iterator();
        while (it.hasNext()) {
            BadSprite alien = it.next();
            BomberSprite bomber = (BomberSprite) alien;

            if (alien.isVisible() && !bomber.isDead()) {
                int x = alien.getX();
                int y = alien.getY();
                int dx = bomber.getDx();
                int dy = bomber.getDy();

                Random rand = new Random();

                // Movimento horizontal suave
                if (rand.nextInt(100) < 40) {  // 40% chance de alterar a direção horizontal
                    int newDx = rand.nextInt(5) - 2;  // Velocidades entre -2 e 2
                    if (newDx != 0) {
                        bomber.setDx(newDx);
                    }
                }

                // Movimento vertical suave
                if (rand.nextInt(100) < 40) {  // 40% chance de alterar a direção vertical
                    int newDy = rand.nextInt(5) - 2;  // Velocidades entre -2 e 2
                    if (newDy != 0) {
                        bomber.setDy(newDy);
                    }
                }

                // Impede que o alien ultrapasse os limites verticais
                if (y + dy < 0) {
                    dy = 1; // Impede que o alien suba além do topo da tela
                }
                if (y + dy + Commons.ALIEN_HEIGHT > Commons.GROUND) {
                    dy = -1; // Impede que o alien desça além do fundo da tela
                }

                // Atualiza a posição dos aliens
                alien.setX(x + dx);
                alien.setY(y + dy);

                // Verifica se o alien morreu
                if (bomber.isDead()) {
                    // Quando o alien está morto, ele não se move mais
                    int newX = x; // Mantém a posição original
                    int newY = y;
                    alien.setX(newX);
                    alien.setY(newY);
                }
            }
        }

        // Update other sprites (bombs)
        updateOtherSprites();
    }







    protected void updateOtherSprites() {
		Random generator = new Random();

        for (BadSprite alien : badSprites) {

            int shot = generator.nextInt(15);
            Bomb bomb = ((BomberSprite)alien).getBomb();

            if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = players.get(0).getX();
            int playerY = players.get(0).getY();

            if (players.get(0).isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Commons.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

                    ImageIcon ii = new ImageIcon(explImg);
                    players.get(0).setImage(ii.getImage());
                    players.get(0).setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
	}    
}

