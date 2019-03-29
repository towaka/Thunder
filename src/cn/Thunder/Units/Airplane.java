package cn.Thunder.Units;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import cn.Thunder.Others.Award;

public class Airplane extends FlyingObject implements Award{
	private static BufferedImage[] imgs;// 定义一个唯一的图片素材
	// 静态代码块用于获取图片资源
	static {
		imgs = new BufferedImage[5];
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/images/airplane" + i + ".png";//获取图片路径
				imgs[i] = ImageIO.read(Airplane.class.getClassLoader().getResourceAsStream(png));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Airplane() {
		super(49, 36);// 初始化小飞机
		this.image = imgs[0];
		life = 1;
		step = Math.random()*6+12;;
	}
	
	/**
	 * 播放下一张图片
	 */
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
		return 0;
		// TODO Auto-generated method stub
	}
	@Override
	public int getScore() {
		return 5;
	}
}
