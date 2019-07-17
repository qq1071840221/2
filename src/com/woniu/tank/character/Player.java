package com.woniu.tank.character;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.JPanel;
import com.woniu.tank.constants.MapConstants;
import com.woniu.tank.constants.TankImageConstants;
import com.woniu.tank.ui.MyGamePanel;

/**
 * 游戏玩家
 * @author admin
 *
 */
public class Player extends Character implements KeyListener {
	
	public static Player player;//需要时创建唯一玩家对象，防止外界直接创建
	public static int direction;//玩家源图片的y坐标
	public static boolean isOver = false;//当前玩家是否销毁
	
	private Player() {

	}

	private Player(Image img, int size, int x, int y, int imageX, int imageY, JPanel jp) {
		super();
		setImg(img);
		setSize(size);
		setX(x);
		setY(y);
		setImageX(imageX);
		setImageY(imageY);
		setJp(jp);
	}
	
	/**
	 * 获取当前类中唯一的对象，没有的话将会创建
	 */
	public static Player getSingletonPlayer(Image img, int size, int playerX, int playerY, int playerImageX, int playerImageY, JPanel jp) {
		if (player == null) {
			player = new Player(img, size, playerX, playerY, 0, 0, jp);
		}
		return player;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {//监控键盘进行对应操作
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				move(0);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				move(2);
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				move(3);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				move(1);
				break;
			case KeyEvent.VK_SPACE://空格键发射子弹
				fire();
				break;
			default:
				break;
		}
		
		getJp().repaint();//重绘地图
	}
	
	/**
	 * 发射子弹
	 */
	public void fire() {
		//获取子弹将要发射的开始坐标地址
		int x = direction==3?getX()-1:direction==1?getX()+1:getX();
		int y = direction==0?getY()-1:direction==2?getY()+1:getY();		
		int bulletImgX = direction==0?3:direction==1?0:direction==2?1:2;//子弹源图片的x坐标
		//创建子弹
		MyGamePanel.bullets.add(new Bullet(x, y, bulletImgX, 0, TankImageConstants.bullet, 32, MapConstants.CHARACTER_PLAYER, getJp()));
	}
	
	/**
	 * 玩家移动
	 * @param direction 玩家图片的y坐标 (0上2下3左1右)
	 */
	public void move(int willGoDirection) {
		//判断源图片y坐标数值。等于3地图x坐标减1向左移动，否则等于1地图x坐标加1向右移动，否则地图x坐标不变原方向移动
		int x = direction==3?getX()-1:direction==1?getX()+1:getX();
		//判断源图片y坐标数值。等于0地图y坐标减1向上移动，否则等于2地图y坐标加1向下移动，否则地图y坐标不变原方向移动
		int y = direction==0?getY()-1:direction==2?getY()+1:getY();
		if (getImageY()!=willGoDirection) {//坦克方向不是将要去的方向
			setImageY(willGoDirection);
			direction = willGoDirection;
		} else {
			//判断即将要移动的位置是否是障碍物
			if (Arrays.asList(0,3,4).contains(MapConstants.MAP[y][x]) && MyGamePanel.gameRules.playerEncounterEnemy(x, y)) {
				setX(x);
				setY(y);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
