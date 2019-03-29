package cn.Thunder.Units;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import cn.Thunder.Others.Award;

public class Airplane extends FlyingObject implements Award{
	private static BufferedImage[] imgs;// ����һ��Ψһ��ͼƬ�ز�
	// ��̬��������ڻ�ȡͼƬ��Դ
	static {
		imgs = new BufferedImage[5];
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/images/airplane" + i + ".png";//��ȡͼƬ·��
				imgs[i] = ImageIO.read(Airplane.class.getClassLoader().getResourceAsStream(png));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Airplane() {
		super(49, 36);// ��ʼ��С�ɻ�
		this.image = imgs[0];
		life = 1;
		step = Math.random()*6+12;;
	}
	
	/**
	 * ������һ��ͼƬ
	 */
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
		return 0;
		// TODO Auto-generated method stub
	}
	@Override
	public int getScore() {
		return 5;
	}
}
