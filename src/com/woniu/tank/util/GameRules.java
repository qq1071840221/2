package com.woniu.tank.util;

import com.woniu.tank.character.EnemyTank;
import com.woniu.tank.character.Player;
import com.woniu.tank.ui.MyGamePanel;

/**
 * 判断游戏规则类
 * @author admin
 *
 */
public class GameRules {
	
	private int playerX;//玩家x坐标
	private int playerY;//玩家y坐标
	
	/**
	 * 玩家遇到敌方坦克
	 * @param playerX 玩家将要走的x坐标
	 * @param playerY 玩家将要走的y坐标
	 * @return 是否可以继续移动（true代表可以移动，false代表不能移动）
	 */
	public boolean playerEncounterEnemy(int playerX, int playerY) {
		
		for (int i = 0; i < MyGamePanel.enemyTanks.size(); i++) {
			EnemyTank enemyTank = MyGamePanel.enemyTanks.get(i);
			if (enemyTank.getX()==playerX&&enemyTank.getY()==playerY) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 敌方坦克遇到玩家
	 * @param enemyTankX 敌方坦克将要走的x坐标
	 * @param enemyTankY 敌方坦克将要走的y坐标
	 * @return 是否可以继续移动（true代表可以移动，false代表不能移动）
	 */
	public boolean enemyEnCounterPlayer(int enemyTankX, int enemyTankY) {
		Player player = MyGamePanel.player;
		//当敌方坦克将要走的坐标和玩家此时待的位置的坐标进行比对
		if (enemyTankX == player.getX() && enemyTankY == player.getY()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断指定的坐标位置此时是否有敌方坦克
	 * @param x 将要走的x坐标
	 * @param y 将要走的y坐标
	 * @return 是否存在坦克（true代表将要走的位置存在坦克，false代表将要走的位置没有坦克）
	 */
	public boolean isHaveTank(int x, int y) {
		//判断是否有其他敌方坦克阻挡
		for (int i = 0; i < MyGamePanel.enemyTanks.size(); i++) {//遍历每个敌方坦克队列
			EnemyTank e = MyGamePanel.enemyTanks.get(i);//敌方坦克队列中某一个坦克
			int eX = e.getX();//获取敌方坦克队列中坦克的x坐标
			int eY = e.getY();//获取敌方坦克队列中坦克的y坐标
			
			if (eX == x && eY == y) {//比对坦克队列中的坦克坐标和当前坦克对象将要去的坐标一致，将需要转向
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the playerX
	 */
	public int getPlayerX() {
		return playerX;
	}

	/**
	 * @param playerX the playerX to set
	 */
	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	/**
	 * @return the playerY
	 */
	public int getPlayerY() {
		return playerY;
	}

	/**
	 * @param playerY the playerY to set
	 */
	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}
	
	
}
