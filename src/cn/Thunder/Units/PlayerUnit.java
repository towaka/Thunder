package cn.Thunder.Units;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import cn.Thunder.Units.Bullet;

import cn.Thunder.Others.Constant;

public class PlayerUnit extends FlyingObject {
	private boolean up,down,left,right;
	private int step = 10;
	private static BufferedImage[] imgs;// ����һ��Ψһ��ͼƬ�ز�
	// ��̬��������ڻ�ȡͼƬ��Դ
	static {
		//��Ϊһ��ʼֻ��һ��ͼƬ�������������д1���Ժ�������֡��̬ͼЧ�����������������С
		imgs = new BufferedImage[1]; 
		//nextImage()
		try {
			for (int i = 0; i < imgs.length; i++) {
				String png = "cn/Thunder/Units/images/hero" + i + ".png";//��ȡͼƬ·��
				imgs[i] = ImageIO.read(PlayerUnit.class.getClassLoader().getResourceAsStream(png));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ʼ��Ӣ�۷ɻ�
	public PlayerUnit() {
		this.image = imgs[0];
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		this.x = (Constant.GAME_WIDTH-width)/2;
		this.y = 500;
	}
	
	public PlayerUnit(double x,double y) {
		this();
		this.x = x;
		this.y = y;
	}


	@Override
	public void paint(Graphics g) {
		g.drawImage(image, (int)getX(), (int)getY(), null);//����ͼƬ
		move();
	}

	@Override
	public void move() { //��д���෽��
		if(isDead()){
			//�������ȡ��һ����Ƭ
			BufferedImage img = nextImage();
			if(img==null){
				state = REMOVE;
			}else{
				image = img;
			}
		}
		//---------------------------------------
		if(left) {
			x -= step;
		}
		if(right) {
			x += step;
		}
		if(up) {	
			y -= step;
		}
		if(down) {
			y += step;
		}
		//---------------------------------------
		if(y>Constant.GAME_HEIGHT-height-31) {
			y = Constant.GAME_HEIGHT-height-31;
		}
		if(y<0) {
			y = 0;
		}
		if(x>Constant.GAME_WIDTH-width) {
			x=Constant.GAME_WIDTH-width-5;
		}
		if(x<0) {
			x = 0;
		}	
	}
	
	public void Direction_true(KeyEvent e) { //�ĸ�����ֵ�ı仯��������������ʼ
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		default:
			break;
		}
	}
	
	public void Direction_false(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		default:
			break;
		}
	}
	
	/**
	 * ��hero������������
	 * ��������Ϊ�������ͣ�����������fireType����������
	 * ��Ϊ��Ҫ�ڴ����ڶ�λ����ӵ�ͼƬ���ҷ�����շɳ����ڵ��ӵ�ռ�ݵ��ڴ�ռ䣬
	 * ���Է�����������ΪBullet������
	 */
	public Bullet[] shoot(int firetype){ 
		int x = (int)(this.x+width/2-4);//�ӵ��ĳ�����xλ�ã�ͨ��΢���������ӷɻ�ͼƬ��Э�����������λ�÷ɳ�
		int y = (int)(this.y);//�ӵ��ĳ�����yλ�ã����ű�ʾ�Ե�ǰͼƬy�������ϵ���
		if(firetype == 1){//��ǹ����
			return new Bullet[]{
					new Bullet(x,y)//����һ���ӵ�
			};
		}
		if(firetype == 2){ //˫������
			return new Bullet[]{
					new Bullet((int)this.x+18, y),
					new Bullet((int)this.x+34,y)
			};
		}
		if(firetype == 3){ //��������
			return new Bullet[]{
					new Bullet((int)this.x+8, y),
					new Bullet((int)this.x+27,y),
					new Bullet((int)this.x+44,y)
			};
		}
		
		return new Bullet[0]; //������������ϵĻ������ͣ����ؿ����顣
	}
	
	
	

	/**
	 * ������һ��ͼƬ
	 */
	@Override
	protected BufferedImage nextImage() { //������չ��������Ч��
//		index++;
//		if(index >= imgs.length){
//			return null;//����±���ڵ������鳤��,��ͼ�ɲ�,����null.
//		}
//		return imgs[index];
		return null;
	}
	
}
