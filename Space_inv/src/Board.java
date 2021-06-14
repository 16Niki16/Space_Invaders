
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Board extends JPanel implements Runnable, MouseListener {
	boolean ingame = true;
	private Dimension d;
	int BOARD_WIDTH = 500;
	int BOARD_HEIGHT = 500;
	int x = 0;
	BufferedImage img;
	private Thread animator;
	Player p;
	Shot q;
	Aliens[] a = new Aliens[10];
	private int score = 0;
	private Timer bullet;
	private int bulletY;
	private int bulletX;
	private int playerX;
	private boolean play = false;
	private boolean shot = false;

	public Board() {
		addKeyListener(new TAdapter());
		addMouseListener(this);
		setFocusable(true);
		bulletY =  BOARD_HEIGHT - 65;
		playerX = BOARD_WIDTH / 2;
		bulletX = playerX + 5;
		
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
		p = new Player(BOARD_WIDTH / 2, BOARD_HEIGHT - 60 , 5);
		q = new Shot(bulletX,bulletY, 1);

		int ax = 10;
		int ay = 10;

		for (int i = 0; i < a.length; i++) {
			a[i] = new Aliens(ax, ay, 10);
			ax += 40;
			if (i == 4) {
				ax = 10;
				ay += 40;
			}
		}

		setBackground(Color.black);
		/*
		 * try { img = ImageIO.read(this.getClass().getResource("mount.jpg")); } catch
		 * (IOException e) { System.out.println("Image could not be read"); //
		 * System.exit(1); }
		 */
		if (animator == null || !ingame) {
			animator = new Thread(this);
			animator.start();
		}

		setDoubleBuffered(true);
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
	
	public void paint(Graphics g) {
		super.paint(g);

		//JFrame
		g.setColor(Color.white);
		g.fillRect(0, 0, d.width, d.height);
		
		//player
		Random randCol = new Random();
	        int m = randCol.nextInt(256);
	        int b = randCol.nextInt(256);
	        int c = randCol.nextInt(256);
	    Color cvqt = new Color(m,b,c);
		g.setColor(cvqt);
		g.fillRect(p.x, p.y, 20, 20);
		
		//shot
		//g.setColor(Color.green);
		//g.fillRect(q.x, q.y, 10, 25);
		drawShot(g);

		if (p.moveRight == true)
			p.x += p.speed;

		if (p.moveLeft == true)
			p.x -= p.speed;
		if (q.moveRight == true)
			q.x += q.speed;

		if (q.moveLeft == true)
			q.x -= q.speed;
		 moveAliens();
		for (int i = 0; i < a.length; i++) {
			g.setColor(Color.blue);
			g.fillRect(a[i].x, a[i].y, 30, 30);

		}

		g.setColor(cvqt);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" +score, 10, 20);
		
		if (ingame) {

			// g.drawImage(img,0,0,200,200 ,null);

		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void moveAliens() {
		for (int i = 0; i < a.length; i++) {
			if (a[i].moveLeft == true) {
				a[i].x -= 2; //a[i].speed;
			}
			if (a[i].moveRight == true) {
				a[i].x += 2; //a[i].speed;
			}
			
			// a[i].y += a[i].speed;

		}
		for (int i = 0; i < a.length; i++) {
		if (a[i].x > BOARD_WIDTH) {
			for (int j = 0; j < a.length; j++) {
				a[j].moveLeft = true;
				a[j].moveRight = false;
				a[j].y += 5;
			}
		}
		if (a[i].x < 0) {
			for (int j = 0; j < a.length; j++) {
				a[j].moveRight = true;
				a[j].moveLeft = false;
				a[j].y += 5;
				}
			}
		}
	}
	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			p.moveRight = false;
			p.moveLeft = false;
			q.moveRight = false;
			q.moveLeft = false;
		}

		public void keyPressed(KeyEvent e) {
//System.out.println( e.getKeyCode());
			// message = "Key Pressed: " + e.getKeyCode();
			int key = e.getKeyCode();
			if (key == 39) {
				p.moveRight = true;
				q.moveRight = true;
			}
			if (key == 37) {
				p.moveLeft = true;
				q.moveLeft = true;
			}
			if (key == KeyEvent.VK_SPACE) {
				if (bulletY == 0) {
					bulletY = 550;
				} else {
					if(!shot) {
					moveAbove();
					}
				}
			}
			}
	
		}

	

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();
		int animationDelay = 50;
		long time = System.currentTimeMillis();
		while (true) {// infinite loop
			// spriteManager.update();
			if(bulletY <= -25) {
				 bulletX = playerX +20;
				 bulletY = 550;
				 shot = false;
				 bullet.stop();
			}
			repaint();
			try {
				time += animationDelay;
				Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				System.out.println(e);
			} // end catch
		} // end while loop

	}// end of run

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
}// end of class
