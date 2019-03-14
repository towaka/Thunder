
package cn.Thunder.Units;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends FlyingObject{
	private static BufferedImage img;//����һ��Ψһ��ͼƬ�ز�,��̬����
	//��̬��������ڻ�ȡͼƬ��Դ
	static{
		String png = "cn/Thunder/Units/images/bullet_01.png";//��ȡͼƬ·��
		try {
			img = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream(png));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ʼ���ӵ�,��Ϊ�ӵ����ֵ�����ø���Ӣ�۷ɻ�һ����,������Ҫ������������
	public Bullet(int x,int y) {
		this.x = x;
		this.y = y;
		width = img.getWidth();
		height = img.getHeight();
		this.image = img;
		step=18;
	}
	//��д�ӵ�move����,�ӵ��Ǵ��������ƶ�
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
		state = REMOVE;//�ӵ�ײ��������
	}
}
