package cn.Thunder.Units;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import cn.Thunder.Others.Award;

public class BigPlane extends FlyingObject implements Award{
	private static BufferedImage[] imgs;// ����һ��Ψһ��ͼƬ�ز�
	// ��̬��������ڻ�ȡͼƬ��Դ
	static {
		imgs = new BufferedImage[5];
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/images/bigplane" + i + ".png";//��ȡͼƬ·��
				imgs[i] = ImageIO.read(BigPlane.class.getClassLoader().getResourceAsStream(png));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public BigPlane() {
		super(70,131);//��ʼ����ɻ�
		this.image = imgs[0];
		life = 6;
	}

	/**
	 * ������һ��ͼƬ
	 */
	@Override
	protected BufferedImage nextImage() {//��д���෽��
		index++;
		if(index >= imgs.length){
			return null;//����±���ڵ������鳤��,��ͼ�ɲ�,����null.
		}
		return imgs[index];
	}

	@Override
	public int getAward() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 10;
	}
}
