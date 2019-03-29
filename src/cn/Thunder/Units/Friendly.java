package cn.Thunder.Units;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import cn.Thunder.Others.Punishment;

public class Friendly extends FlyingObject implements Punishment{
	
	private int direction;// ����direction,���������Ѿ����ĳ����ƶ�����
	private static BufferedImage[] imgs;// ����һ��Ψһ��ͼƬ�ز�
	private int speed = (int)(Math.random()*3+6.8); //����ٶ�
	private int animation = 0;//ͼƬ��̬Ч������������
	private int explode = 12; //��ըͼ�ĵ�һ֡ͼ����ǰһ��ͼ���±�
	
	// ��̬��������ڻ�ȡͼƬ��Դ
	static {
		imgs = new BufferedImage[17];
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/images/friendly_" + i + ".png";// ��ȡͼƬ·��
				imgs[i] = ImageIO.read(Friendly.class.getClassLoader().getResourceAsStream(png));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ʼ���۷�
	public Friendly() {
		super(80, 91);
		this.image = imgs[0];
		direction = Math.random() > 0.5 ? -speed : speed;// ��ʼ���Ѿ������ƶ�����,��ռ50%�ĸ���.
		this.y = 0;//���ڶ�������λ������
	}

	@Override
	public void move() {
		super.move();  // y += step;����y���꽻������������
		if (state == ACTIVE) { //x���������ദ��
			x += direction;
			// ����Ѿ����ƶ����ұ߽߱�,�򷴷����ƶ�
			if (x >= 640 - width) {
				direction = -speed;
			}
			// ����Ѿ����ƶ�����߽߱�,�򷴷����ƶ�
			if (x < 0) {
				direction = speed;
			}
			// �Ѿ����Ķ���Ч��
			this.image = imgs[animation];
			animation++;
			if(animation>12)
				animation=0;
		}
		
		if(state == DEAD){
			//��nextImage()�л�ȡ��һ����Ƭ
			BufferedImage img = nextImage();
			if(img == null){ //���û��ͼƬ�ɶ��Ļ�
				state = REMOVE;//�����
			}else{
				image = img;//����������ͼƬ����image
			}
			//Խ��������
			if(y>=825){
				state = REMOVE;
			}
		}
	}
	/**
	 * ������һ��ͼƬ
	 */
	@Override
	protected BufferedImage nextImage() {
		explode++;
		if (explode >= imgs.length) {
			return null;// ����±���ڵ������鳤��,��ͼ�ɲ�,����null.
		}
		return imgs[explode];
	}

	@Override
	public int getPunish() {
		return 15;
	}
}
