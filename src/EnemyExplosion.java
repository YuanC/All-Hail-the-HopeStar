
public class EnemyExplosion {

	private double xpos, ypos, rad, updateLength;
	private int type;
	private boolean dispose;
	
	public EnemyExplosion(int x, int y, int unitType){
		xpos = x;
		ypos = y;
		rad = 0;
		dispose = false;
		type = unitType*100;
	}
	
	public void Update(long updateLengthLong){
		updateLength = updateLengthLong/1000000000.0;
		rad += updateLength*200;
		if (rad>60)
			dispose = true;
	}
	
	public int returnX(){
		return (int)xpos;
	}
	public int returnY(){
		return (int)ypos;
	}
	public int returnRad(){
		return (int)rad;
	}
	public boolean deleteCheck(){
		return dispose;
	}
	public int returnScore(){
		return type;
	}
}
