package com.woniu.tank.constants;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 *  加载游戏面板所需要的图片资源
 * @author admin
 *
 */
public class TankImageConstants {

	public static Image backGround;//背景图片
	
	public static Image grass;//草
	public static Image brick;//砖头
	public static Image steels;//铁砖
	public static Image ice;//冰
	public static Image water;//水
	public static Image boss;//老板
	public static Image border;//边框
	public static Image player;//玩家
	public static Image enemyTank;//敌方坦克
	public static Image star;//星星
	public static Image bullet;//子弹
	public static Image boom;//爆炸
	public static Image gameOver;//游戏结束
	
	//静态代码块写入图片资源地址
	static {
		try {
			backGround = ImageIO.read(new File("img/bg.jpg"));
			grass = ImageIO.read(new File("img/grass.png"));
			brick = ImageIO.read(new File("img/brick.png"));
			steels = ImageIO.read(new File("img/steels.png"));
			ice = ImageIO.read(new File("img/ice.png"));
			water = ImageIO.read(new File("img/water1.jpg"));
			boss = ImageIO.read(new File("img/boss.gif"));
			border = ImageIO.read(new File("img/gray.png"));
			player = ImageIO.read(new File("img/player.gif"));
			enemyTank = ImageIO.read(new File("img/tanks.bmp"));
			star = ImageIO.read(new File("img/star.bmp"));
			bullet = ImageIO.read(new File("img/bullet.png"));
			boom = ImageIO.read(new File("img/boom.png"));
			gameOver = ImageIO.read(new File("img/gameover.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

