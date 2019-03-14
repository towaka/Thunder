package cn.Thunder.Units;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import cn.Thunder.Units.Bullet;

import cn.Thunder.Others.Constant;

public class PlayerUnit extends FlyingObject {
	private boolean up,down,left,right;
	private int step = 10;
	private static BufferedImage[] imgs;// 定义一个唯一的图片素材
	// 静态代码块用于获取图片资源
	static {
		//因为一开始只有一张图片，所以这里可以写1，以后做好逐帧动态图效果可以再扩大数组大小
		imgs = new BufferedImage[1]; 
		//nextImage()
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/Units/images/hero" + i + ".png";//获取图片路径
				imgs[i] = ImageIO.read(PlayerUnit.class.getClassLoader().getResourceAsStream(png));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//初始化英雄飞机
	public PlayerUnit() {
		this.image = imgs[0];
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		this.x = (Constant.GAME_WIDTH-width)/2;
		this.y = 500;
	}
	
	public PlayerUnit(double x,double y) {
		this();
		this.x = x;
		this.y = y;
	}


	@Override
	public void paint(Graphics g) {
		g.drawImage(image, (int)getX(), (int)getY(), null);//绘制图片
		move();
	}

	@Override
	public void move() { //重写父类方法
		if(isDead()){
			//从子类获取下一张照片
			BufferedImage img = nextImage();
			if(img==null){
				state = REMOVE;
			}else{
				image = img;
			}
		}
		//---------------------------------------
		if(left) {
			x -= step;
		}
		if(right) {
			x += step;
		}
		if(up) {	
			y -= step;
		}
		if(down) {
			y += step;
		}
		//---------------------------------------
		if(y>Constant.GAME_HEIGHT-height-31) {
			y = Constant.GAME_HEIGHT-height-31;
		}
		if(y<0) {
			y = 0;
		}
		if(x>Constant.GAME_WIDTH-width) {
			x=Constant.GAME_WIDTH-width-5;
		}
		if(x<0) {
			x = 0;
		}	
	}
	
	public void Direction_true(KeyEvent e) { //四个布尔值的变化从这两个方法开始
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		default:
			break;
		}
	}
	
	public void Direction_false(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		default:
			break;
		}
	}
	
	/**
	 * 在hero上添加射击方法
	 * 方法参数为火力类型，由主函数的fireType变量决定。
	 * 因为需要在窗口内多次绘制子弹图片，且方便回收飞出窗口的子弹占据的内存空间，
	 * 所以返回数据类型为Bullet类数组
	 */
	public Bullet[] shoot(int firetype){ 
		int x = (int)(this.x+width/2-4);//子弹的出场的x位置，通过微调调整至从飞机图片中协调出的满意的位置飞出
		int y = (int)(this.y);//子弹的出场的y位置，减号表示以当前图片y坐标往上调整
		if(firetype == 1){//单枪发射
			return new Bullet[]{
					new Bullet(x,y)//创建一颗子弹
			};
		}
		if(firetype == 2){ //双倍火力
			return new Bullet[]{
					new Bullet((int)this.x+18, y),
					new Bullet((int)this.x+34,y)
			};
		}
		if(firetype == 3){ //三倍火力
			return new Bullet[]{
					new Bullet((int)this.x+8, y),
					new Bullet((int)this.x+27,y),
					new Bullet((int)this.x+44,y)
			};
		}
		
		return new Bullet[0]; //如果都不是以上的火力类型，返回空数组。
	}
	
	
	

	/**
	 * 播放下一张图片
	 */
	@Override
	protected BufferedImage nextImage() { //方便扩展，做动画效果
//		index++;
//		if(index >= imgs.length){
//			return null;//如果下标大于等于数组长度,无图可播,返回null.
//		}
//		return imgs[index];
		return null;
	}
	
}
