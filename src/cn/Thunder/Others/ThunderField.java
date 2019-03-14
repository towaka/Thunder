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
	 * ִ��������
	 */
	private static final long serialVersionUID = 1L;
	private FlyingObject[] flyingObjects;// ������(��:��С�ɻ�,�۷��)
	private Bullet[] bullets;// �ӵ�������Щ���ǳɶѳ��ֵ�ͼƬ����������Ҫ������ʽ
	private PlayerUnit hero;// Ӣ�۷ɻ�
	private RollingBackGround sky;// һƬ���

	private long nextTime;// ��һʱ�̵�ʱ��
	private long Period;
	private Timer timer;// ��ʱ��
	long Start_time;
	long End_time;

	private boolean flagPause = false;
	/** �Ʒֱ��� */
	private int score = 0;// ����
	private int life = 3;// ��Ҳ��ݵķɻ���life��
	private int fireType = 1;// �ӵ���ʼʱΪ��ǹ

	/** ��Ϸ��״̬ */
	public static final int READY = 0;// ��ʼ״̬
	public static final int RUNNING = 1;// ����״̬
	public static final int PAUSE = 2;// ��ͣ״̬
	public static final int GAME_OVER = 3;// ����״̬
	private int state = READY;// ��Ϸ״̬

	/** ���ؿ�ʼ,��ͣ,������������Ƭ */
	private static BufferedImage pause;// ��ͣ
	private static BufferedImage ready;// ��ʼ
	private static BufferedImage gameOver;// ����
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
		flyingObjects = new FlyingObject[] {};// ��ʼ������������
		bullets = new Bullet[] {};// ��ʼ���ӵ�����
		hero = new PlayerUnit();// ��ʼ��Ӣ�۷ɻ�
		sky = new RollingBackGround();// ��ʼ��һƬ���
		nextTime = System.currentTimeMillis() + 1000;// ��ʼ����һ���ϵͳʱ��
		new PaintThread().start();// ��Ҫ�ڹ������������ػ��̵߳ķ���
	}

	// nextOne��������ʱ(1/24��)����һ��
	public void nextOne() {
		// ��������һ��,��ȡһ�µ�ǰʱ��.
		long now = System.currentTimeMillis();// ��ȡ��ǰϵͳʱ��ĺ�����
		if (now >= nextTime) {
			nextTime = now + 500;// ���Ʒ�����ĳ���ʱ����,��ÿ��һ�����һ��������,1��=1000����
			FlyingObject obj = randomOne();// ����randomOne����,�������һ��������
			flyingObjects = Arrays.copyOf(flyingObjects, flyingObjects.length + 1);// �������ݣ���һ����Դͷ���飬�ڶ�������Ҫ���ݵĿռ䳤��
			flyingObjects[flyingObjects.length - 1] = obj;// ������ɵķ����ﱻ�ŵ�������ȥ
		}
	}

	private FlyingObject randomOne() { // ���﷽��������������FlyingObject����Ϊ���ؽ������FlyingObject������
		int n = (int) (Math.random() * 15);// Math.random���������һ������0��1֮�����������10��ķ�Χ��[0,10]������ǿ������ת��
		// ����������������Ĵ�СҲ���Ե�����ͬ������ĳ�������
		switch (n) {
		case 0:
			return new Airplane();// ���Ｘ��new�����ڶ���ֱ�������µĶ��󣬵��������ɾͱ�Ȼ�л��գ��ҿ�����ķ���
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
	public void paint(Graphics g) { // ����ͼƬ�ķ���
		sky.paint(g);// �������,������ͼƬ
		hero.paint(g);// ����Ӣ�۷ɻ�

		for (FlyingObject fly : flyingObjects) {
			fly.paint(g);// ����ÿ��������
		}

		for (Bullet bullet : bullets) {
			bullet.paint(g);// ����ÿ���ӵ�
		}

		// ��ӷ���,����,�ӵ����͵���ʾ
		g.setColor(Color.cyan);
		Font f = new Font("΢���ź�", Font.PLAIN, 20);
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
			if(End_time==0)//һ��Ҫ��if�жϣ���Ȼ��Ϸ������ῴ����ʱ���ּ����ۼ�
				End_time = System.currentTimeMillis();
			Period = ( End_time - Start_time )/1000; // Date���gettime���������ؽ��������λ�Ǻ���(1��=1000����)
			g.drawImage(gameOver, (Constant.GAME_WIDTH - gameOver.getWidth()) / 2,
					(Constant.GAME_HEIGHT - gameOver.getHeight()) / 2, null);
			g.drawString("���¿�ʼ��Ϸ�밴Enter��", 220, 500);
			g.drawString("��Ϸ��ʱ" + Period + "��", 220, 520);
		}
	}

	// ��World�����һ����ʱ����������ʱ���ķ���.ÿ��Լ33.3����һ������
	public void action() {
		timer = new Timer();// ��ʼ��һ����ʱ��
		timer.schedule(new TimerTask() { // ����ʹ���������ڲ���

			@Override
			public void run() {
				if (state == RUNNING) {
					nextOne();// ÿ(1/24��)����һ��������
					move();// ����������ƶ�
					duangDuang();// ��ײ������ͬʱ���н������
					removeObjects();// ���շ���
					heroLifeCircle();// ��ҷɻ�����������
				}
				repaint();// ���»���JPanel
			}
		}, 0, 1000 / 30);// ��0��ʼ

		keyBoardControll();
	}

	public void keyBoardControll() {
		// �������̵ļ���
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
					Start_time = System.currentTimeMillis();// ��ʼ��Ϸ��ͼ�ʱ
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

		this.addKeyListener(k); // ע���������ȻJPanel����û����Ӧ���̲���
		this.setFocusable(true);// ���ҲҪ����ҪʹJPanel��ò�������
	}

	/** �ɻ����������� */
	public void heroLifeCircle() {
		// ���������Ӣ�۵���ײ
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
				for (FlyingObject plane : flyingObjects) { // ��Ϊ�Ǹ����ԭ�򣬰���ariplane�ȷ����ﶼ��������
					if (plane.isActive() && plane.duang(hero)) { // Ҳ������java��̬�������ص�
						plane.goDead();
					}
				}
			} else {
				state = GAME_OVER;
			}
		}
	}

	/**
	 * ɾ����û���õ��ӵ��ͷɻ�
	 */
	public void removeObjects() {
		// ɾ�����������ӵ�
		Bullet[] bary = {};// ��ʼ��һ���ӵ�����
		for (Bullet b : bullets) { // ѭ���ж�
			if (b.canRemove()) { // �����״̬��REMOVE~~
				continue;// �ص�ѭ���ж������������ж�
			}
			bary = Arrays.copyOf(bary, bary.length + 1);// ��������
			bary[bary.length - 1] = b;// ��Խ����ӵ���ӵ�������ȥ
		}
		bullets = bary;// bullets�������õ�bary������,����ԭ�������ã���Ϊ֮��baryû���ٱ����ã��������Ŵ˷���ִ����Ϻ����٣�

		// ɾ���������ķɻ�
		FlyingObject[] Fary = {};// ��ʼ��һ������������
		for (FlyingObject obj : flyingObjects) {
			if (obj.canRemove()) {
				continue;// �ص�ѭ���ж������������ж�
			}
			Fary = Arrays.copyOf(Fary, Fary.length + 1);// ��������
			Fary[Fary.length - 1] = obj;// ��û��ɾ���ķ�������ӵ�������ȥ
		}
		flyingObjects = Fary;// ����������ͬ��
	}

	/**
	 * ��world���������ײ��ⷽ��
	 */
	public void duangDuang() {
		for (FlyingObject fob : flyingObjects) { // ���ϵؽ���ѭ���ж�,�������ֶ�̬���ŵ�
			if (fob.isActive()) { // ����ڷ��е�λ�������ڵ�����·����������
				if (shootByBullet(fob)) { // 1.���ӵ�����
					fob.hit(); // �򴥷�FlyingObject���������hit������life��1
					if (fob.isDead()) { // 2.������е�λ��״̬�Ѿ���DEAD
						// �Ʒ�,��ȡ����
						if (fob instanceof Award) { // �պ������ķ��е�λ�̳���ĳ���ӿھͽ����ж�
							// ���Ǽ���һ����Ķ����Ƿ�ʵ����ĳ���ӿڵ��﷨
							// �����������plane����ʵ����Enemy�ӿ�....��
							Award enemy = (Award) fob; // ��ס���ӿڱ�����newһ�����󣬵����ܹ���Ϊʵ���˽ӿڵ���Ķ��������
							int s = enemy.getScore(); // �������enemy.getScore()���ܳ���
							score += s;
						}
						if (fob instanceof Award) {
							Award award = (Award) fob;
							int awd = award.getAward();
							if (awd == Award.LIFE) { // �������ֵ��Ϊ0
								life++; // ����+1
							} else if (awd == Award.FIRE) { // ��������Ϊ1
								fireType = 1;
							} else if (awd == Award.DOUBLE_FIRE) { // ˫������Ϊ2
								fireType = 2;
							} else if (awd == Award.TRIPLE_FIRE) { // ��������Ϊ3
								fireType = 3;
							}
						}
						if (fob instanceof Punishment) { // �ͷ�����
							Punishment friend = (Punishment) fob;// ����з��������гͷ�����
							int psh = friend.getPunish();
							score -= psh;// �۷�
							if (fireType == 3) // ����ģʽһ�������
								fireType = 2;
							else if (fireType == 2)
								fireType = 1;
						}
					}
				}
			}

		}
		if (life > 3) { // ��������ֻ���������������ű߽翴�����ˣ�
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
		Bullet[] arr = hero.shoot(fireType);// �ӵ��������
		bullets = Arrays.copyOf(bullets, bullets.length + arr.length);// Ϊ�����仯������Ҫ����������
		System.arraycopy(arr, 0, bullets, bullets.length - arr.length, arr.length);
	}

	/**
	 * ����������ƶ�
	 */
	private void move() {
		// ÿ���������ƶ�һ��,���»���JPanel����
		for (FlyingObject fly : flyingObjects) {
			fly.move();
		}
		// ÿ�������ӵ����ƶ�
		for (Bullet bullet : bullets) {
			bullet.move();
		}

		sky.move();// ����ƶ�
		hero.move();// Ӣ�۷ɻ��ƶ�
	}

	public void Reset() { // �����ڶ��ｨ���µĶ���֮ǰ�����Ķ���ʧȥ���ú󽫻ᱻ����
		score = 0;
		life = 3;
		fireType = 1;
		hero = new PlayerUnit();
		bullets = new Bullet[0];
		flyingObjects = new FlyingObject[0];
		state = READY;
	}

	class PaintThread extends Thread {// ��Ա�ڲ���
		// ����һ���ػ����ڵ��߳��࣬���ڲ������ʽ��Ϊ�˷���ʹ���ⲿ�������
		// �������֮����ҵķɻ������ȸ���һ��
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40);
					// System.out.println("paint again!");//�����ã�����̨�Ƿ��ӡ��仰
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		ThunderField world = new ThunderField();// ����һ��world���
		JFrame frame = new JFrame();// ����һ������

		frame.setTitle("Thunder Field");
		frame.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);// ���ڴ�С
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رմ��ڼ��رճ���
		frame.setLocationRelativeTo(null);// �ô��ھ���
		frame.add(world);// ��world���ӵ�JFrame������ȥ
		frame.setVisible(true);// ���ڿɼ�
		frame.setIconImage(wing);
		frame.setResizable(false);

		world.action();// ������ʱ��
	}

}
