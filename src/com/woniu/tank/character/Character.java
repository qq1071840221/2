package com.woniu.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import com.woniu.tank.constants.MapConstants;
import com.woniu.tank.constants.TankImageConstants;

/**
 * 角色类
 * @author admin
 *
 */
public class Character {
	//面板上的x坐标
	private int x;
	//面板上的y坐标
	private int y;
	//角色源图的x坐标
	private int imageX;
	//角色源图的y坐标
	private int imageY;
	//图片资源
	private Image img;
	//图片源资源中的单元矩形的像素
	private int size;
	//面板对象
	private JPanel jp;
	
	/**
	 * 绘制图片到面板上
	 */
	public void draw(Graphics g) {//角色创建唯一入口
		g.drawImage(img, x<<5, y<<5, (x+1)<<5, (y+1)<<5, imageX*size, imageY*size, (imageX+1)*size, (imageY+1)*size, jp);
		imageStep();//坦克动态效果
		underGrass(g);//角色钻草
	}
	
	/**
	 * 判断图片上的坐标奇偶数，通过调用系统重绘方法达到动态效果
	 */
	public void imageStep() {
		//判断当前源图片的x坐标是奇数还是偶数，如果是偶数，对它+1操作，否则-1操作
		if(getImageX()%2 == 0) {
			setImageX(getImageX()+1);
		} else {
			setImageX(getImageX()-1);
		}
	}
	
	/**
	 * 角色钻草
	 */
	public void underGrass(Graphics g) {
		if (MapConstants.MAP[y][x] == 4) {
			g.drawImage(TankImageConstants.grass, (getX()<<5)-5, (getY()<<5)-5, ((getX()+1)<<5)+5, ((getY()+1)<<5)+5, 0, 0, 87, 83, getJp());
		}
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the imageX
	 */
	public int getImageX() {
		return imageX;
	}

	/**
	 * @param imageX the imageX to set
	 */
	public void setImageX(int imageX) {
		this.imageX = imageX;
	}

	/**
	 * @return the imageY
	 */
	public int getImageY() {
		return imageY;
	}

	/**
	 * @param imageY the imageY to set
	 */
	public void setImageY(int imageY) {
		this.imageY = imageY;
	}

	/**
	 * @return the jp
	 */
	public JPanel getJp() {
		return jp;
	}

	/**
	 * @param jp the jp to set
	 */
	public void setJp(JPanel jp) {
		this.jp = jp;
	}

	/**
	 * @return the img
	 */
	public Image getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(Image img) {
		this.img = img;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}	
}