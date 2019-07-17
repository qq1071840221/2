package com.woniu.tank.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPanel;

import com.woniu.tank.character.Blast;
import com.woniu.tank.character.Bullet;
import com.woniu.tank.character.EnemyTank;
import com.woniu.tank.character.Player;
import com.woniu.tank.constants.MapConstants;
import com.woniu.tank.constants.TankImageConstants;
import com.woniu.tank.util.GameRules;
import com.woniu.tank.character.Music;


/**
 *  游戏界面布局类
 * @author admin
 *
 */
public class MyGamePanel extends JPanel implements Runnable {
	private int bossImageY = 0 ;//boss源图片的y坐标
	public static GameRules gameRules = new GameRules();//游戏规则类	
	public static boolean gameIsOver = false;//当前游戏状态
	
	//创建玩家对象
	public static Player player;	
	//创建敌方坦克集合对象
	public static Vector<EnemyTank> enemyTanks = new Vector<>();		
	//创建子弹集合对象
	public static Vector<Bullet> bullets = new Vector<Bullet>();
	//创建爆炸集合对象
	public static Vector<Blast> blasts = new Vector<Blast>();
	
	public MyGamePanel() {
		setBackground(Color.BLACK);	//设置背景为黑色	
		//启动当前线程
		new Thread(this).start();		
		//绘制玩家角色
		player = Player.getSingletonPlayer(TankImageConstants.player, 28, 13, 21, 0, 0, this);		
		for (int i = 0; i < 4; i++) {
			//创建敌方坦克对象
			EnemyTank enemyTank = new EnemyTank(TankImageConstants.enemyTank, 28, (int)(Math.random()*30)+1, 1, (int)(Math.random()*8), (int)(Math.random()*4), this);
			//启动敌方坦克线程
			new Thread(enemyTank).start();
			enemyTanks.add(enemyTank);
		}
		new Thread(new Music("img/start.wav")).start();
//		new Music("img/start.wav").play();
	}

	/**
	 * 面板的绘制方法，由系统来调用
	 * 绘制面板组件
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);	
		//绘制地图面板
		drawMap(g);
		//绘制玩家角色
		player.draw(g);
		//绘制子弹
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		//绘制爆破效果
		for (int i = 0; i < blasts.size(); i++) {
			blasts.get(i).draw(g);
		}
		//调用敌方坦克的绘制方法进行坦克绘制
		for (int i = 0; i < enemyTanks.size(); i++) {
			enemyTanks.get(i).draw(g);
		}
		if (gameIsOver) {
			g.drawImage(TankImageConstants.gameOver, 0, 0, getWidth(), getHeight(), this);
		}
		
	}

	/**
	 *  绘制地图
	 *  //32*23个单元格
		//每个矩形的像素：32*32
		//1024*736
	 * @param g
	 */
	public void drawMap(Graphics g) {
		/**
		 * 1.img:图片资源
		 * 2.图片目标矩形左上角地址的x坐标
		 * 3.图片目标矩形左上角地址的y坐标
		 * 4.图片目标矩形右下角地址的x坐标
		 * 5.图片目标矩形右下角地址的y坐标
		 * 6.图片资源的目标矩形左上角地址的x坐标
		 * 7.图片资源的目标矩形左上角地址的y坐标
		 * 8.图片资源的目标矩形右下角地址的x坐标
		 * 9.图片资源的目标矩形右下角地址的y坐标
		 * 10.
		 */
		//遍历地图的每个点
		for (int y = 0; y < MapConstants.MAP.length; y++) {//地图的行
			for (int x = 0; x < MapConstants.MAP[y].length; x++) {//地图的列
				int index = MapConstants.MAP[y][x];				
				switch (index) {
					case 0:
						//不绘制
						break;
					case 1:
						//砖头
						g.drawImage(TankImageConstants.brick, x<<5, y*32, (x+1)*32, (y+1)*32, 0, 0, 32, 32, this);
						break;
					case 2:	
						//铁
						g.drawImage(TankImageConstants.steels, x*32, y*32, (x+1)*32, (y+1)*32, 0, 0, 32, 32, this);
						break;
					case 3:
						//冰
						g.drawImage(TankImageConstants.ice, x*32, y*32, (x+1)*32, (y+1)*32, 0, 0, 64, 64, this);
						break;
					case 4:
						//草
						g.drawImage(TankImageConstants.grass, x*32, y*32, (x+1)*32, (y+1)*32, 0, 0, 87, 83, this);
						break;
					case 5:
						//水
						g.drawImage(TankImageConstants.water, x*32, y*32, (x+1)*32, (y+1)*32, 0, 0, 122, 122, this);
						break;
					case 6:
						//边框
						g.drawImage(TankImageConstants.border, x*32, y*32, (x+1)*32, (y+1)*32, 0, 0, 74, 78, this);
						break;
					case 7:
						//boss
						g.drawImage(TankImageConstants.boss, x*32, y*32, (x+1)*32, (y+1)*32, 0, bossImageY*34, 41, (bossImageY+1)*34, this);
						break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void run() {
		while(true) {
			bossImageY++;//boss源图片按顺序变动
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//boss源图片变到最后一张重置为第一张
			if (bossImageY == 11) {
				bossImageY = 0;
			}
			//调用系统重绘方法
			repaint();
		}
	}
	/**
	 * 游戏结束,处理相关数据
	 */
	public static void gameOver() {
		gameIsOver = true;
		//停止所有敌方坦克的移动
		for (int i = 0; i < enemyTanks.size(); i++) {
			enemyTanks.get(i).isOver = true;
		}
		//把玩家对象置为销毁状态
		Player.isOver = true;
		new Thread(new Music("img/fail.mp3")).start();
//		new Music("img/fail.mp3").play();
	}
}