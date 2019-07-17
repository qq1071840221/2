package com.woniu.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.JPanel;

import com.woniu.tank.constants.MapConstants;
import com.woniu.tank.constants.TankImageConstants;
import com.woniu.tank.ui.MyGamePanel;

/**
 * 敌方坦克类
 * @author admin
 *
 */
public class EnemyTank extends Character implements Runnable {
	
	private volatile boolean isDrawStar = true;//绘制星星
	private int starImageX;//星星源图的x坐标
	
	public boolean isOver = false;//坦克是否毁灭
	
	public EnemyTank() {
		super();
	}
	
	//有参构造
	public EnemyTank(Image img, int size, int x, int y, int imageX, int imageY, JPanel jp) {
		super();
		setImg(img);
		setSize(size);
		setX(x);
		setY(y);
		setImageX(imageX);
		setImageY(imageY);
		setJp(jp);
		
		//子弹发射的线程
		new Thread() {

			@Override
			public void run() {
				while (!isOver) {
					if (!isDrawStar) {//如果星星绘制结束，才开始发射子弹
						try {
							Thread.sleep(800);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						fire();//开火
					}
				}
			}
			
		}.start();
	}
	
	/**
	 * 绘制坦克
	 */
	@Override
	public void draw(Graphics g) {
		if (isDrawStar) {
			drawStar(g);//创建星星
		} else {
			super.draw(g);//调父类创建角色
		}
	}
	
	/**
	 * 绘制星星
	 * @param g
	 */
	public void drawStar(Graphics g) {
		g.drawImage(TankImageConstants.star, getX()<<5, getY()<<5, (getX()+1)<<5, (getY()+1)<<5, starImageX<<5, 0, (starImageX+1)<<5, 1<<5, getJp());
	}

	@Override
	public void run() {
		while(!isOver) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//判断是否有星星，如果没有创建动态星星
			if (isDrawStar) {
				starImageX++;
				if (starImageX == 4) {
					starImageX = 0;
					isDrawStar = false;
				}
			} else {
				move();//移动坦克
			}
		}
	}
	
	/**
	 * 开火
	 */
	public void fire() {
		//获取子弹将要发射的开始坐标地址
		int x = getImageY()==1?getX()+1:getImageY()==3?getX()-1:getX();
		int y = getImageY()==0?getY()-1:getImageY()==2?getY()+1:getY();
		
		int bulletImgX = getImageY()==1?0:getImageY()==2?1:getImageY()==3?2:3;//子弹源图片的x坐标
		//创建子弹
		MyGamePanel.bullets.add(new Bullet(x, y, bulletImgX, 0, TankImageConstants.bullet, 32, MapConstants.CHARACTER_ENEMYTANK, getJp()));
	}
	
	/**
	 * 坦克移动
	 */
	public void move() {
		switch (getImageY()) {//根据源图片上的坐标位置判断方向
			case 0://上
				if (isCanMove(getX(), getY()-1)) {
					setY(getY()-1);//面板y坐标减1
				}
				break;
			case 1://右
				if (isCanMove(getX()+1, getY())) {
					setX(getX()+1);//面板x坐标加1
				}
				break;
			case 2://下
				if (isCanMove(getX(), getY()+1)) {
					setY(getY()+1);//面板y坐标加1
				}
				break;
			case 3://左
				if (isCanMove(getX()-1, getY())) {
					setX(getX()-1);//面板x坐标减1
				}
				break;
	
			default:
				break;
		}
	}
	
	/**
	 * 判断是否可以运行此坐标
	 * @param x 面板的x轴
	 * @param y 面板的y轴
	 */
	public boolean isCanMove(int x, int y) {
		if (Arrays.asList(0,3,4).contains(MapConstants.MAP[y][x]) //将运行到的坐标在地图上为0,3,4
				&& MyGamePanel.gameRules.enemyEnCounterPlayer(x, y) //敌方坦克将运行到的坐标没有玩家
				&& !MyGamePanel.gameRules.isHaveTank(x, y)) {//将运行到的坐标没有敌方坦克
			return true;
		} else {//此坐标是障碍物，不能移动到此坐标
			//将进行随机转换方向
			while (true) {
				int randomDirection = (int) (Math.random()*4);//随机产生0-3之间的整数，对应不同图片，控制方向
				if (randomDirection!=getImageY()) {//转换方向与当前方向不一致，运行此方向
					setImageY(randomDirection);
					break;
				}
			}
		}
		return false;
	}
}