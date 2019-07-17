package com.woniu.tank.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.woniu.tank.character.Player;
import com.woniu.tank.constants.MapConstants;
import com.woniu.tank.constants.TankImageConstants;

/**
 *  游戏结构布局
 * @author admin
 *
 */
public class GameFrame extends JFrame {
	public GameFrame() {
		//加入游戏名字与图标
		setTitle("坦克大战");	
		setIconImage(TankImageConstants.player);
		//获取屏幕大小
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) dim.getHeight();
		int width = (int) dim.getWidth();
		//设置窗体的大小
		setBounds((width-MapConstants.WIDTH)/2, (height-MapConstants.HEIGHT)/2, MapConstants.WIDTH, MapConstants.HEIGHT);
		JPanel jp = new MyGamePanel();//创建面板对象
		this.setContentPane(jp);//给当前框架加入面板对象
		//给当前框架加入对键盘的检测事件
		this.addKeyListener(Player.getSingletonPlayer(TankImageConstants.player, 28, 13, 21, 0, 0, jp));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置当前窗口可关闭
		setVisible(true);//设置显示
	}
	public static void main(String[] args) {
		new GameFrame();
	}
}
