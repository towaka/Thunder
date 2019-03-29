package cn.Thunder.Others;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.Thunder.Units.FlyingObject;

public class RollingBackGround extends FlyingObject{
	private double y1;//����ڶ���y����,���ڿ��Ʊ������ƶ�
	private static BufferedImage img;//����һ��Ψһ��ͼƬ�ز�
	//��̬��������ڻ�ȡͼƬ��Դ
	static{
		String png = "cn/Thunder/images/background.png";//��ȡͼƬ·��
		try {
			img = ImageIO.read(RollingBackGround.class.getClassLoader().getResourceAsStream(png));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ʼ�����
	public RollingBackGround() {
		this.x = 0;
		this.y = 0;
		this.width=640;
		this.height=1820;
		this.image = img;
		this.step = 1;//��ʼ��ͼƬ�ƶ����ٶ�
		y1 = -height;//��ʼ���ڶ���ͼƬ��λ��
	}
	
	@Override
	public void move() {
		y++;
		y1++;
		if(y>=height){
			y = -height;//�����һ��ͼ�ƶ����±߽�λ��,�򷵻ض���
		}
		if(y1>=height){
			y1 = -height;//����ڶ���ͼ�ƶ����±߽�λ��,�򷵻ض���
		}
	}
	//��д��paint,���ڻ������ű���ͼƬ��λ��.
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, (int)x, (int)y, null);
		g.drawImage(image, (int)x, (int)y1, null);
	}
	@Override
	protected BufferedImage nextImage() {
		return null;
	}
}
