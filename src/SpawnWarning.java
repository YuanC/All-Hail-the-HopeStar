public class SpawnWarning {
	private double dir, turnRate, timer, xpos, ypos, turnRateMultiplier;
	private int turretNum;
	
	public SpawnWarning(int x, int y) {
		dir = 0;
		turnRate = 100;
		xpos = x;
		ypos = y;
		if(Math.random()*2>1){
			turnRateMultiplier = 1;
		} else {
			turnRateMultiplier = -1;
		}
		
	}
	
	public SpawnWarning(int x, int y, int num) {
		dir = 0;
		turnRate = 100;
		xpos = x;
		ypos = y;
		turretNum = num;
		
		if(Math.random()*2>1){
			turnRateMultiplier = 1;
		} else {
			turnRateMultiplier = -1;
		}
		
	}
	
	//updates the rotating triangle
	public void update(long updateLengthLong) {
		double updateLength = updateLengthLong / 1000000000.0;

		timer += updateLength;
		dir += turnRate*updateLength*turnRateMultiplier;

		if (dir >= 360)
			dir = dir % 360;
		else if (dir < 0)
			dir = 360 - Math.abs(dir);
	}

	public boolean deleteCheck() {
		if (timer > 2) {
			return true;
		}
		return false;
	}

	//Calculates the vertexes
	public int[] vertexCalX(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians((dir)) % 360) * 13) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians((dir + 120)) % 360) * 13) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians((dir + 240)) % 360) * 13) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX};
		return vertexList;
	}
	public int[] vertexCalY(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians((dir)) % 360) * 13) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians((dir + 120)) % 360) * 13) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians((dir + 240)) % 360) * 13) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY };
		return vertexList;

	}
	
	public int returnX() {
		return (int) Math.round(xpos);
	}

	public int returnY() {
		return (int) Math.round(ypos);
	}
	public int returnTurretNum() {
		return turretNum;
	}
}
