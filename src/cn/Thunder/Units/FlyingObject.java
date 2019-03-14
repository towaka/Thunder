package cn.Thunder.Units;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import cn.Thunder.Others.Constant;

public abstract class FlyingObject {
	protected double x;// �����x����
	protected double y;// �����y����
	protected double width;// ����Ŀ�
	protected double height;// ����ĸ�
	protected BufferedImage image;// ��ǰ������ʾ��ͼƬ
	protected int index = 0;// ͼƬ�����±����,������ʹ��
	protected double step;// ������ÿ���ƶ��ľ���
	protected int life;// ��
	protected int state;// �������״̬
	public static final int ACTIVE = 0;// �״̬
	public static final int DEAD = 1;// ����״̬
	public static final int REMOVE = 2;// ����״̬

	// Ĭ�Ϲ�����
	public FlyingObject() {
		life = 1; // ����������ֵķ�����ĳ�ʼ���������������������
		state = ACTIVE;
	}

	// �вι�����
	public FlyingObject(double width, double height) {
		this();// �����޲����Ĺ�����FlyingObject(),�﷨�涨���������Ĺ�����������д�ڵ�һ��.
		this.x = (int) (Math.random() * (Constant.GAME_WIDTH - width));// �ڴ���X�᳤-ͼƬ��ȵķ�Χ���������Ŀ��
		this.y = -height; // �ڴ�������ط���Ҳ��ݷ�����䱻���غ��ڴ������λ��Y��������ͼƬ�߶Ⱦ���,���������������
		this.width = width;
		this.height = height;
		step = Math.random() * 3 + 6.8;// step����ʼ��Ϊ����6.8-9.8֮�����,���������������
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	// ��дtoString����
	public String toString() {
		return x + "," + y + "," + width + "," + height + "," + image;
	}

	// ��дpaint,������������ʹ��
	public void paint(Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);// ����ͼƬ
	}

	// �������ƶ���move����
	public void move() { // ���������п���д�˷���
		if (state == ACTIVE) {
			y += step;
			return; // �����������Ȼ���˳��˷���
		}
		if (state == DEAD) {
			// ����������л�ȡ��һ����Ƭ
			BufferedImage img = nextImage();
			if (img == null) { // ���û��ͼƬ�ɶ��Ļ�
				state = REMOVE;// �����
			} else {
				image = img;// ����������ͼƬ����image
			}
			// Խ��������
			if (y >= 825) {
				state = REMOVE;
			}
		}
	}

	/**
	 * �����б����еķ���,������һ��Ҫ���ŵ���Ƭ����, �������null��ʾû�пɲ��ŵ���Ƭ��.
	 */
	protected abstract BufferedImage nextImage();

	/**
	 * ��������ײ���Ч��
	 */
	public void hit() {
		if (life > 0) {
			life--;
		}
		if (life == 0) {
			state = DEAD;
		}
	}

	/**
	 * ��ײ���ķ��� ��������λ���Ƿ�����ײ�ķ�Χ��.
	 * 
	 */
	public boolean duang(FlyingObject obj) { // �����㷨���⻹ֻ�Ƕ�ά��
		// this(x,y,w,h)
		// obj(x,y,w,h)
		double x1 = this.x - obj.width;
		double x2 = this.x + this.width;
		double y1 = this.y - obj.height;
		double y2 = this.y + this.height;
		return x1 < obj.x && obj.x < x2 && y1 < obj.y && obj.y < y2;
	}

	/**
	 * ���ĸ������涨�ķ�����ĸ���״̬��������Ӧ 
	 */
	
	/** ���������Ƿ񱻻�׹ */
	public boolean isDead() {
		return state == DEAD;
	}

	/** ���������Ƿ��� */
	public boolean isActive() {
		return state == ACTIVE;
	}

	/** �������Ƿ���Ա�ɾ�� */
	public boolean canRemove() {
		return state == REMOVE;
	}

	/** ���������"����"���� */
	public void goDead() {
		if (isActive()) {
			state = DEAD;
		}
	}
}
