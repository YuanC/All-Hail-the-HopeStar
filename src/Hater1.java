
public class Hater1{
	
	private double xpos, ypos, dir,  speed, octLength;
	private int[] canvas = { 1200, 800 };
	public Hater1(int x, int y) {
		speed = 100;
		dir = 0;
		xpos = x;
		ypos = y;
		octLength = 0.0;
	}
	
	//updates the enemy's position
	public void updateHater(long updateLengthLong, int playerX, int playerY){
		
		double updateLength = updateLengthLong/1000000000.0;
		
		dirCal(playerX, playerY);
	
		xpos = Math.min(Math.max(xpos+speed*updateLength*Math.cos(dir), 60),canvas[0]-60);
		ypos = Math.min(Math.max(ypos+speed*updateLength*Math.sin(dir), 60),canvas[1]-60);
		
		octLength = (octLength + updateLength*30)%15;
		
		
	}
	
	//updates the direction fo the enenmy in relation to the player
	public void dirCal(int playerX, int playerY){
		if (xpos-playerX>0&&ypos-playerY>0){
			dir = Math.PI+(Math.abs(Math.atan((playerY - ypos)/(playerX - xpos))));
		}else if (xpos-playerX>0&&ypos-playerY<0){
			dir = Math.PI-(Math.abs(Math.atan((playerY - ypos)/(playerX - xpos))));
		}else if (xpos-playerX<0&&ypos-playerY>0){
			dir = Math.PI*2-(Math.abs(Math.atan((playerY - ypos)/(playerX - xpos))));
		}else{
			dir = Math.abs(Math.atan((playerY - ypos)/(playerX - xpos)));
		}
	}
	
	//returns some values to the gamepanel
	public int returnX(){
		return (int)xpos;
	}
	public int returnY(){
		return (int) ypos;
	}
	public double returnHitRad(){
		return 15.0;
	}
	public int[] vertexCalX1(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians(90) % 360) * 15) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians(180) % 360) * 15) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians(270) % 360) * 15) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians(0) % 360) * 15) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4-camX};
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalY1(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians(90) % 360) * 15) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians(180) % 360) * 15) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians(270) % 360) * 15) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians(0) % 360) * 15) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4-camY};
		return vertexList;

	}
	public int[] vertexCalX2(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians(45) % 360) * 15) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians(135) % 360) * 15) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians(225) % 360) * 15) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians(315) % 360) * 15) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4-camX};
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalY2(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians(45) % 360) * 15) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians(135) % 360) * 15) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians(225) % 360) * 15) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians(315) % 360) * 15) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4-camY};
		return vertexList;

	}
	public int[] vertexCalX3(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians(45) % 360) * octLength) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians(90) % 360) * octLength) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians(135) % 360) * octLength) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians(180) % 360) * octLength) + xpos);
		int x5 = (int) ((Math.cos(Math.toRadians(225) % 360) * octLength) + xpos);
		int x6 = (int) ((Math.cos(Math.toRadians(270) % 360) * octLength) + xpos);
		int x7 = (int) ((Math.cos(Math.toRadians(315) % 360) * octLength) + xpos);
		int x8 = (int) ((Math.cos(Math.toRadians(0) % 360) * octLength) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4-camX, x5-camX, x6-camX, x7-camX, x8-camX};
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalY3(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians(45) % 360) * octLength) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians(90) % 360) * octLength) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians(135) % 360) * octLength) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians(180) % 360) * octLength) + ypos);
		int y5 = (int) ((Math.sin(Math.toRadians(225) % 360) * octLength) + ypos);
		int y6 = (int) ((Math.sin(Math.toRadians(270) % 360) * octLength) + ypos);
		int y7 = (int) ((Math.sin(Math.toRadians(315) % 360) * octLength) + ypos);
		int y8 = (int) ((Math.sin(Math.toRadians(0) % 360) * octLength) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4-camY, y5-camY, y6-camY, y7-camY, y8-camY};
		return vertexList;

	}
}