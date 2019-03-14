package cn.Thunder.Units;

import java.awt.Color;
import java.awt.Graphics;

import cn.Thunder.Others.Award;

public class BigPlaneAward extends BigPlane implements Award{
	
	public void paint(Graphics g){
		super.paint(g);//���ظ����paint����
		g.setColor(Color.yellow);
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	@Override
	public int getAward() {
		return DOUBLE_FIRE;//��ȡ˫������
	}
	
	@Override
	public int getScore() {
		return 15;
	}
	
	
}
