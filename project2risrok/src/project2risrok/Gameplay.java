package project2risrok;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private int alien = (int) (Math.random() * 2) + 1;
	private int alien1 = (int) (Math.random() * 4) + 1;
	private Timer timer, bullet;
	private int delay = 17;
	private int playerX = 310;
	private int bulletX = 310;
	private int playerY = 550;
	private int bulletY = 550;
	private boolean play = false;
	private boolean shot = false;
	private MapGenerator map;

	public Gameplay() {
		map = new MapGenerator(alien, alien1);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	private void drawShot(Graphics obj) {
		obj.setColor(Color.green);
		if(!shot) {
		obj.fillRect(playerX +20, bulletY - 20, 10, 25);
		bulletX = playerX  + 20;
		}else {
		obj.fillRect(bulletX, bulletY - 20, 10, 25);
		}
	}
	public void paint(Graphics obj) {

		obj.setColor(Color.black);
		obj.fillRect(1, 1, 692, 592);

		obj.setColor(Color.magenta);
		obj.fillRect(playerX, playerY, 50, 25);

		drawShot(obj);
		map.draw((Graphics2D) obj);

		Toolkit.getDefaultToolkit().sync();
	}

	public void actionPerformed(ActionEvent e) {
		if(bulletY <= -25) {
			 bulletX = playerX +20;
			 bulletY = 550;
			 shot = false;
			 bullet.stop();
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paint(g);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		int x = bulletX;
		int y = bulletY;

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (bulletY == 0) {
				bulletY = 550;
			} else {
				if(!shot) {
				moveAbove();
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 4;
			} else {
				moveLeft();
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	public void moveAbove() {
		play = true;
		shot = true;
		bullet = new Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bulletY -=20;
			}
		});
		bullet.start();
	}
}
