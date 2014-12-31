
public class Projectile {
	private double xpos, ypos, dir, speed;
	
	public Projectile(int x, int y, double d, double s){
		xpos=x;
		ypos=y;
		dir=d;
		speed = s;
	}
	
	//updates the projectile
	public void update(long updateLengthLong) {
		double updateLength = updateLengthLong/1000000000.0;
		
		ypos+=speed*Math.sin(Math.toRadians(dir))*updateLength;
		xpos+=speed*Math.cos(Math.toRadians(dir))*updateLength;
	}

	//calculates whether the projecitle is out of bounds to delete
	public boolean deleteCal(int i, int j) {
		if (xpos < 40 || xpos > i-40 || ypos < 40 || ypos > j-40)
			return true;
		else
			return false;

	}
	
	//returns values to the gamePanel
	public int returnX() {
		return (int) Math.round(xpos);
	}

	public int returnY() {
		return (int) Math.round(ypos);
	}

	
	public double returnHitRad(){
		return 5.0;
	}
}
