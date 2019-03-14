package cn.Thunder.Others;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import cn.Thunder.Units.Airplane;
import cn.Thunder.Units.BaseUnit;
import cn.Thunder.Units.BigPlane;
import cn.Thunder.Units.BigPlaneAward;
import cn.Thunder.Units.Bullet;
import cn.Thunder.Units.FlyingObject;
import cn.Thunder.Units.Friendly;
import cn.Thunder.Units.PlayerUnit;

public class ThunderField extends JPanel {
	/**
	 * 执行主程序
	 */
	private static final long serialVersionUID = 1L;
	private FlyingObject[] flyingObjects;// 飞行物(如:大小飞机,蜜蜂等)
	private Bullet[] bullets;// 子弹数组这些都是成堆出现的图片对象，所以需要数组形式
	private PlayerUnit hero;// 英雄飞机
	private RollingBackGround sky;// 一片天空

	private long nextTime;// 下一时刻的时间
	private long Period;
	private Timer timer;// 定时器
	long Start_time;
	long End_time;

	private boolean flagPause = false;
	/** 计分变量 */
	private int score = 0;// 分数
	private int life = 3;// 玩家操纵的飞机的life数
	private int fireType = 1;// 子弹初始时为单枪

	/** 游戏的状态 */
	public static final int READY = 0;// 开始状态
	public static final int RUNNING = 1;// 运行状态
	public static final int PAUSE = 2;// 暂停状态
	public static final int GAME_OVER = 3;// 结束状态
	private int state = READY;// 游戏状态

	/** 加载开始,暂停,结束的三张照片 */
	private static BufferedImage pause;// 暂停
	private static BufferedImage ready;// 开始
	private static BufferedImage gameOver;// 结束
	private static BufferedImage wing;

	static {
		try {
			wing = ImageIO
					.read(ThunderField.class.getClassLoader().getResourceAsStream("cn/Thunder/Units/images/icon.png"));
			ready = ImageIO
					.read(ThunderField.class.getClassLoader().getResourceAsStream("cn/Thunder/Units/images/start.png"));
			pause = ImageIO
					.read(ThunderField.class.getClassLoader().getResourceAsStream("cn/Thunder/Units/images/pause.png"));
			gameOver = ImageIO
					.read(ThunderField.class.getClassLoader().getResourceAsStream("cn/Thunder/Units/images/gameover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ThunderField() {
		flyingObjects = new FlyingObject[] {};// 初始化飞行物数组
		bullets = new Bullet[] {};// 初始化子弹数组
		hero = new PlayerUnit();// 初始化英雄飞机
		sky = new RollingBackGround();// 初始化一片天空
		nextTime = System.currentTimeMillis() + 1000;// 初始化下一秒的系统时间
		new PaintThread().start();// 需要在构造器里启动重绘线程的方法
	}

	// nextOne方法被定时(1/24秒)调用一次
	public void nextOne() {
		// 方法调用一次,获取一下当前时间.
		long now = System.currentTimeMillis();// 获取当前系统时间的毫秒数
		if (now >= nextTime) {
			nextTime = now + 500;// 控制飞行物的出场时间间隔,即每隔一秒出场一个飞行物,1秒=1000毫秒
			FlyingObject obj = randomOne();// 调用randomOne方法,随机生成一个飞行物
			flyingObjects = Arrays.copyOf(flyingObjects, flyingObjects.length + 1);// 数组扩容，第一个是源头数组，第二个是需要扩容的空间长度
			flyingObjects[flyingObjects.length - 1] = obj;// 随机生成的飞行物被放到数组中去
		}
	}

	private FlyingObject randomOne() { // 这里方法的数据类型是FlyingObject，因为返回结果都是FlyingObject的子类
		int n = (int) (Math.random() * 15);// Math.random是随机生成一个介于0到1之间的数，乘以10后的范围是[0,10]，留意强制类型转换
		// 这个上面这个随机数的大小也可以调整不同飞行物的出场概率
		switch (n) {
		case 0:
			return new Airplane();// 这里几个new都是在堆里直接生成新的对象，但是有生成就必然有回收，且看后面的方法
		case 1:
			return new Airplane();
		case 2:
			return new BigPlane();
		case 3:
			return new Airplane();
		case 4:
			return new Friendly();
		case 5:
			return new BigPlaneAward();
		case 6:
			return new BigPlane();
		case 7:
			return new Friendly();
		case 8:
			return new BaseUnit();
		case 9:
			return new Friendly();
		case 10:
			return new BigPlaneAward();
		default:
			return new Airplane();
		}
	}

	@Override
	public void paint(Graphics g) { // 绘制图片的方法
		sky.paint(g);// 画出天空,即背景图片
		hero.paint(g);// 画出英雄飞机

		for (FlyingObject fly : flyingObjects) {
			fly.paint(g);// 绘制每个飞行物
		}

		for (Bullet bullet : bullets) {
			bullet.paint(g);// 绘制每个子弹
		}

		// 添加分数,生命,子弹类型的显示
		g.setColor(Color.cyan);
		Font f = new Font("微软雅黑", Font.PLAIN, 20);
		g.setFont(f);
		g.drawString("SCORE:" + score, 20, 30);
		g.drawString("LIFE:" + life, 20, 50);
		g.drawString("FIRE:" + fireType, 20, 70);

		switch (state) {
		case READY:
			g.drawImage(ready, 0, 0, null);
			break;
		case PAUSE:
			if (flagPause)
				g.drawImage(pause, 0, 207, null);
			if (!flagPause)
				state = RUNNING;
			break;
		case GAME_OVER:
			if(End_time==0)//一定要有if判断，不然游戏结束后会看到耗时数字继续累加
				End_time = System.currentTimeMillis();
			Period = ( End_time - Start_time )/1000; // Date里的gettime方法，返回结果计量单位是毫秒(1秒=1000毫秒)
			g.drawImage(gameOver, (Constant.GAME_WIDTH - gameOver.getWidth()) / 2,
					(Constant.GAME_HEIGHT - gameOver.getHeight()) / 2, null);
			g.drawString("重新开始游戏请按Enter键", 220, 500);
			g.drawString("游戏耗时" + Period + "秒", 220, 520);
		}
	}

	// 在World中添加一个定时器和启动定时器的方法.每大约33.3毫秒一个动作
	public void action() {
		timer = new Timer();// 初始化一个定时器
		timer.schedule(new TimerTask() { // 这里使用了匿名内部类

			@Override
			public void run() {
				if (state == RUNNING) {
					nextOne();// 每(1/24秒)生成一个飞行物
					move();// 各种物体的移动
					duangDuang();// 碰撞方法，同时进行奖励检测
					removeObjects();// 回收方法
					heroLifeCircle();// 玩家飞机的生命周期
				}
				repaint();// 重新绘制JPanel
			}
		}, 0, 1000 / 30);// 从0开始

		keyBoardControll();
	}

	public void keyBoardControll() {
		// 创建键盘的监听
		KeyAdapter k = new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				hero.Direction_true(e);
				if (state == RUNNING && e.getKeyCode() == KeyEvent.VK_SPACE)
					state = PAUSE;
				if (state == RUNNING && e.getKeyCode() == KeyEvent.VK_Z) {
					Heroshoot();
				}
				if (state == READY && e.getKeyCode() == KeyEvent.VK_ENTER) {
					Start_time = System.currentTimeMillis();// 开始游戏后就计时
					state = RUNNING;
				}
				if (state == GAME_OVER && e.getKeyCode() == KeyEvent.VK_ENTER) {
					state = READY;
					Reset();
				}
				if (state == PAUSE && e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!flagPause) {
						flagPause = true;
					} else {
						flagPause = false;
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				hero.Direction_false(e);
			}
		};

		this.addKeyListener(k); // 注册监听，不然JPanel根本没法响应键盘操作
		this.setFocusable(true);// 这个也要做，要使JPanel获得操作焦点
	}

	/** 飞机的生命周期 */
	public void heroLifeCircle() {
		// 检查飞行物和英雄的碰撞
		if (hero.isActive()) {
			for (FlyingObject plane : flyingObjects) {
				if (plane.isActive() && plane.duang(hero)) {
					plane.goDead();
					hero.goDead();
				}
			}
		}
		if (hero.canRemove()) {
			if (life > 0) {
				life--;
				hero = new PlayerUnit(hero.getX(), hero.getY());
				for (FlyingObject plane : flyingObjects) { // 因为是父类的原因，包括ariplane等飞行物都能算在内
					if (plane.isActive() && plane.duang(hero)) { // 也体现了java多态特征的特点
						plane.goDead();
					}
				}
			} else {
				state = GAME_OVER;
			}
		}
	}

	/**
	 * 删除掉没有用的子弹和飞机
	 */
	public void removeObjects() {
		// 删除掉废弃的子弹
		Bullet[] bary = {};// 初始化一个子弹数组
		for (Bullet b : bullets) { // 循环判断
			if (b.canRemove()) { // 如果其状态是REMOVE~~
				continue;// 回到循环判断条件，继续判断
			}
			bary = Arrays.copyOf(bary, bary.length + 1);// 数组扩容
			bary[bary.length - 1] = b;// 把越界的子弹添加到数组中去
		}
		bullets = bary;// bullets变量引用到bary数组上,抛弃原来的引用（因为之后bary没有再被引用，而是随着此方法执行完毕后被销毁）

		// 删除掉废弃的飞机
		FlyingObject[] Fary = {};// 初始化一个飞行物数组
		for (FlyingObject obj : flyingObjects) {
			if (obj.canRemove()) {
				continue;// 回到循环判断条件，继续判断
			}
			Fary = Arrays.copyOf(Fary, Fary.length + 1);// 数组扩容
			Fary[Fary.length - 1] = obj;// 把没有删掉的飞行物添加到数组中去
		}
		flyingObjects = Fary;// 方法和上面同样
	}

	/**
	 * 在world里面添加碰撞检测方法
	 */
	public void duangDuang() {
		for (FlyingObject fob : flyingObjects) { // 不断地进行循环判断,这里体现多态的优点
			if (fob.isActive()) { // 如果在飞行单位持续存在的情况下发生以下情况
				if (shootByBullet(fob)) { // 1.被子弹射中
					fob.hit(); // 则触发FlyingObject及其子类的hit方法，life减1
					if (fob.isDead()) { // 2.如果飞行单位的状态已经是DEAD
						// 计分,获取奖励
						if (fob instanceof Award) { // 刚好死亡的飞行单位继承了某个接口就进行判断
							// 这是检验一个类的对象是否实现了某个接口的语法
							// “如果抽象父类plane对象实现了Enemy接口....”
							Award enemy = (Award) fob; // 记住，接口本身不能new一个对象，但是能够作为实现了接口的类的对象的引用
							int s = enemy.getScore(); // 所以这句enemy.getScore()才能成立
							score += s;
						}
						if (fob instanceof Award) {
							Award award = (Award) fob;
							int awd = award.getAward();
							if (awd == Award.LIFE) { // 如果左右值都为0
								life++; // 命数+1
							} else if (awd == Award.FIRE) { // 单倍火力为1
								fireType = 1;
							} else if (awd == Award.DOUBLE_FIRE) { // 双倍火力为2
								fireType = 2;
							} else if (awd == Award.TRIPLE_FIRE) { // 三倍火力为3
								fireType = 3;
							}
						}
						if (fob instanceof Punishment) { // 惩罚机制
							Punishment friend = (Punishment) fob;// 如果敌方飞行物有惩罚机制
							int psh = friend.getPunish();
							score -= psh;// 扣分
							if (fireType == 3) // 火力模式一步步会减
								fireType = 2;
							else if (fireType == 2)
								fireType = 1;
						}
					}
				}
			}

		}
		if (life > 3) { // 命的上限只有三条（看花括号边界看死我了）
			life = 3;
		}
	}

	public boolean shootByBullet(FlyingObject fob) {
		for (Bullet bullet : bullets) {
			if (bullet.duang(fob)) {
				bullet.hit();
				return true;
			}
		}
		return false;
	}

	public void Heroshoot() {
		Bullet[] arr = hero.shoot(fireType);// 子弹射击类型
		bullets = Arrays.copyOf(bullets, bullets.length + arr.length);// 为火力变化做好需要的数组扩容
		System.arraycopy(arr, 0, bullets, bullets.length - arr.length, arr.length);
	}

	/**
	 * 各种物体的移动
	 */
	private void move() {
		// 每个飞行物移动一下,重新绘制JPanel方法
		for (FlyingObject fly : flyingObjects) {
			fly.move();
		}
		// 每个己方子弹的移动
		for (Bullet bullet : bullets) {
			bullet.move();
		}

		sky.move();// 天空移动
		hero.move();// 英雄飞机移动
	}

	public void Reset() { // 重新在堆里建立新的对象，之前建立的对象失去引用后将会被回收
		score = 0;
		life = 3;
		fireType = 1;
		hero = new PlayerUnit();
		bullets = new Bullet[0];
		flyingObjects = new FlyingObject[0];
		state = READY;
	}

	class PaintThread extends Thread {// 成员内部类
		// 定义一个重画窗口的线程类，用内部类的形式是为了方便使用外部类的属性
		// 加了这个之后，玩家的飞机流畅度更高一点
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40);
					// System.out.println("paint again!");//测试用，看后台是否打印这句话
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		ThunderField world = new ThunderField();// 创建一个world面板
		JFrame frame = new JFrame();// 创建一个窗口

		frame.setTitle("Thunder Field");
		frame.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);// 窗口大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口即关闭程序
		frame.setLocationRelativeTo(null);// 让窗口居中
		frame.add(world);// 把world面板加到JFrame窗口里去
		frame.setVisible(true);// 窗口可见
		frame.setIconImage(wing);
		frame.setResizable(false);

		world.action();// 启动定时器
	}

}
