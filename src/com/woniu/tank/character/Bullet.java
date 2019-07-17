package com.woniu.tank.character;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;
import javax.swing.JPanel;
import com.woniu.tank.constants.MapConstants;
import com.woniu.tank.constants.TankImageConstants;
import com.woniu.tank.ui.MyGamePanel;
import com.woniu.tank.character.Blast;
import com.woniu.tank.character.EnemyTank;
import com.woniu.tank.character.Player;

/**
 * 子弹
 * @author admin
 *
 */
public class Bullet extends Character implements Runnable {

	private boolean isOver = false;
	private int characterType;//角色类型
	
	public Bullet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bullet(int x, int y, int imageX, int imageY, Image img, int size,int characterType,JPanel jp) {
		super();
		setImg(img);
		setSize(size);
		setX(x);
		setY(y);
		setImageX(imageX);
		setImageY(imageY);
		this.characterType = characterType;
		setJp(jp);
		new Thread(this).start();
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
//		int bulletImgX = direction==0?3:direction==1?0:direction==2?1:2;//子弹源图片的x坐标
		while(!isOver) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			move();
		}
	}
	
	/**
	 * 子弹坐标的变动
	 */
	public void move() {		
			//判断是否将要遇到地图静态障碍物
			if (Arrays.asList(1,2,6,7).contains(MapConstants.MAP[getY()][getX()])) {
				//判断当前障碍物是否可以炸掉
				if (MapConstants.MAP[getY()][getX()] == 1) {//打中砖头
					MapConstants.MAP[getY()][getX()] = 0;   //清除
				}
				if (MapConstants.MAP[getY()][getX()] == 7) {//子弹打中boss
					MapConstants.MAP[getY()][getX()] = 0;   //清除
					MyGamePanel.gameOver();//游戏结束
				}
				isOver = true;
				MyGamePanel.bullets.remove(this);
				//爆破效果
				MyGamePanel.blasts.add(new Blast(getX(), getY(), TankImageConstants.boom, 192, getJp()));
			} else {//没有静态障碍物
				//判断是否将要遇到动态障碍物（坦克）
				if ((characterType == MapConstants.CHARACTER_PLAYER && !bulletToEnemy())
						|| (characterType == MapConstants.CHARACTER_ENEMYTANK && !bulletToPlayer())) {//没有打到敌方坦克进入移动方法
					int x = getImageX()==0?getX()+1:getImageX()==2?getX()-1:getX();
					int y = getImageX()==1?getY()+1:getImageX()==3?getY()-1:getY();
					setX(x);
					setY(y);
		}
	}	
}
	/**
	 * 玩家子弹打到敌方坦克
	 * @return
	 */
	public boolean bulletToEnemy() {
		for (int i = 0; i < MyGamePanel.enemyTanks.size(); i++) {
			EnemyTank e = MyGamePanel.enemyTanks.get(i);
			
			if (getX() == e.getX() && getY() == e.getY()) {
				//结束当前子弹的线程
				isOver = true;
				//在子弹集合中移除这枚子弹
				MyGamePanel.bullets.remove(this);
				//产生爆破效果
				MyGamePanel.blasts.add(new Blast(getX(), getY(), TankImageConstants.boom, 192, getJp()));
				//在数组中移除敌方坦克
				e.isOver = true;//标记被打中的地方坦克为毁灭状态，达到停止里面的线程的目的
				MyGamePanel.enemyTanks.remove(e);
				if(MyGamePanel.enemyTanks.size()==0) {
					MyGamePanel.gameOver();
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 敌方坦克子弹打到玩家
	 * @return
	 */
	public boolean bulletToPlayer() {
		Player player = Player.getSingletonPlayer(TankImageConstants.player, 28, 13, 21, 0, 0, getJp());
		//子弹碰到玩家
		if (getX() == player.getX() && getY() == player.getY()) {
			//结束当前子弹的线程
			isOver = true;
			//在子弹集合中移除这枚子弹
			MyGamePanel.bullets.remove(this);
			//产生爆破效果
			MyGamePanel.blasts.add(new Blast(getX(), getY(), TankImageConstants.boom, 192, getJp()));
			//移除玩家对象
			player = null;
			MyGamePanel.gameOver();//结束当前游戏
			return true;
		}
		return false;
	}
}
