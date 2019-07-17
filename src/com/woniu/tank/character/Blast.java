package com.woniu.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import com.woniu.tank.ui.MyGamePanel;
import com.woniu.tank.character.Music;

/**
 * 爆炸
 * @author admin
 *
 */
public class Blast extends Character implements Runnable {

	public Blast() {
	}

	public Blast(int x, int y, Image img, int size, JPanel jp) {
		super();
		setImg(img);
		setSize(size);
		setX(x);
		setY(y);
		setJp(jp);
		new Thread(this).start();
		new Thread(new Music("img/blast.wav")).start();
//		new Music("img/blast.wav").play();
	}
	
	/* (non-Javadoc)
	 * @see com.woniuxy.tank.character.Character#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(getImg(), getX()<<5, getY()<<5, (getX()+1)<<5, (getY()+1)<<5, getImageX()*getSize(), getImageY()*getSize(), (getImageX()+1)*getSize(), (getImageY()+1)*getSize(), getJp());
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			setImageX(i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//清除爆炸对象
		MyGamePanel.blasts.remove(this);
	}	
}
