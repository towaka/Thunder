package cn.Thunder.Units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import cn.Thunder.Others.Award;

public class BaseUnit extends FlyingObject implements Award{
	private static BufferedImage[] imgs;//定义一个唯一的图片素材
	
	// 静态代码块用于获取图片资源
	static {
		imgs = new BufferedImage[5];
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/images/baseunit" + i + ".png";////获取图片路径
				imgs[i] = ImageIO.read(BigPlane.class.getClassLoader().getResourceAsStream(png));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BaseUnit() {
		super(100,153);
		this.image = imgs[0];
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		life = 15;
		
	}
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	protected BufferedImage nextImage() {
		index++;
		if(index >= imgs.length){
			return null;//如果下标大于等于数组长度,无图可播,返回null.
		}
		return imgs[index];
	}


	@Override
	public int getAward() {
		// TODO Auto-generated method stub
		return TRIPLE_FIRE;
	}


	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 20;
	}

}
