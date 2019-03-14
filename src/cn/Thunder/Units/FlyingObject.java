package cn.Thunder.Units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.Thunder.Others.Constant;

public abstract class FlyingObject {
	protected double x;// 物体的x坐标
	protected double y;// 物体的y坐标
	protected double width;// 物体的宽
	protected double height;// 物体的高
	protected BufferedImage image;// 当前正在显示的图片
	protected int index = 0;// 图片数组下标序号,子类中使用
	protected double step;// 飞行物每次移动的距离
	protected int life;// 命
	protected int state;// 飞行物的状态
	public static final int ACTIVE = 0;// 活动状态
	public static final int DEAD = 1;// 死亡状态
	public static final int REMOVE = 2;// 回收状态

	// 默认构造器
	public FlyingObject() {
		life = 1; // 各种随机出现的飞行物的初始命数，可以在其子类更改
		state = ACTIVE;
	}

	// 有参构造器
	public FlyingObject(double width, double height) {
		this();// 调用无参数的构造器FlyingObject(),语法规定调用其他的构造器，必须写在第一行.
		this.x = (int) (Math.random() * (Constant.GAME_WIDTH - width));// 在窗口X轴长-图片宽度的范围下随机生成目标
		this.y = -height; // 在窗口外加载非玩家操纵飞行物，其被加载后在窗口外的位置Y轴坐标由图片高度决定,可以在其子类更改
		this.width = width;
		this.height = height;
		step = Math.random() * 3 + 6.8;// step被初始化为介于6.8-9.8之间的数,可以在其子类更改
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	// 重写toString方法
	public String toString() {
		return x + "," + y + "," + width + "," + height + "," + image;
	}

	// 重写paint,方便子类对象的使用
	public void paint(Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);// 绘制图片
	}

	// 飞行物移动的move方法
	public void move() { // 在其子类中可重写此方法
		if (state == ACTIVE) {
			y += step;
			return; // 如果飞行物依然，退出此方法
		}
		if (state == DEAD) {
			// 从子类对象中获取下一张照片
			BufferedImage img = nextImage();
			if (img == null) { // 如果没有图片可读的话
				state = REMOVE;// 则回收
			} else {
				image = img;// 否则把子类的图片传给image
			}
			// 越界则销毁
			if (y >= 825) {
				state = REMOVE;
			}
		}
	}

	/**
	 * 子类中必须有的方法,返回下一个要播放的照片引用, 如果返回null表示没有可播放的照片了.
	 */
	protected abstract BufferedImage nextImage();

	/**
	 * 飞行物碰撞后的效果
	 */
	public void hit() {
		if (life > 0) {
			life--;
		}
		if (life == 0) {
			state = DEAD;
		}
	}

	/**
	 * 碰撞检测的方法 检测物体的位置是否在碰撞的范围内.
	 * 
	 */
	public boolean duang(FlyingObject obj) { // 经典算法，这还只是二维的
		// this(x,y,w,h)
		// obj(x,y,w,h)
		double x1 = this.x - obj.width;
		double x2 = this.x + this.width;
		double y1 = this.y - obj.height;
		double y2 = this.y + this.height;
		return x1 < obj.x && obj.x < x2 && y1 < obj.y && obj.y < y2;
	}

	/**
	 * 这四个方法规定的飞行物的各个状态，方便响应 
	 */
	
	/** 检查飞行物是否被击坠 */
	public boolean isDead() {
		return state == DEAD;
	}

	/** 检查飞行物是否活动的 */
	public boolean isActive() {
		return state == ACTIVE;
	}

	/** 检查飞行是否可以被删除 */
	public boolean canRemove() {
		return state == REMOVE;
	}

	/** 飞行物添加"死亡"方法 */
	public void goDead() {
		if (isActive()) {
			state = DEAD;
		}
	}
}
