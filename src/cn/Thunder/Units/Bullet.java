
package cn.Thunder.Units;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends FlyingObject{
	private static BufferedImage img;//定义一个唯一的图片素材,静态属性
	//静态代码块用于获取图片资源
	static{
		String png = "cn/Thunder/Units/images/bullet_01.png";//获取图片路径
		try {
			img = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream(png));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//初始化子弹,因为子弹出现的坐标得跟着英雄飞机一起走,所以需要传入两个参数
	public Bullet(int x,int y) {
		this.x = x;
		this.y = y;
		width = img.getWidth();
		height = img.getHeight();
		this.image = img;
		step=18;
	}
	//重写子弹move方法,子弹是从下往上移动
	@Override
	public void move() {
		if(state == ACTIVE){
			y -= step;
			if(y<=20){
				state = REMOVE;
			}
		}
	}
	@Override
	protected BufferedImage nextImage() {
		return null;
	}
	public void hit(){
		state = REMOVE;//子弹撞击后销毁
	}
}
