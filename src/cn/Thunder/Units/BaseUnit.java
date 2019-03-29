package cn.Thunder.Units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import cn.Thunder.Others.Award;

public class BaseUnit extends FlyingObject implements Award{
	private static BufferedImage[] imgs;//����һ��Ψһ��ͼƬ�ز�
	
	// ��̬��������ڻ�ȡͼƬ��Դ
	static {
		imgs = new BufferedImage[5];
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/images/baseunit" + i + ".png";////��ȡͼƬ·��
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
			return null;//����±���ڵ������鳤��,��ͼ�ɲ�,����null.
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
