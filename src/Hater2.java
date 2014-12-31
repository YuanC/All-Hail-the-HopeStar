public class Hater2 {

	private double xpos, ypos, dir, shootTimer, hexDir2, hexDir3;
	private boolean fireCoolDown;
	private int position;

	public Hater2(int x, int y, int posNum) {
		dir = 0;
		xpos = x;
		ypos = y;
		fireCoolDown = true;
		shootTimer = 2;
		position = posNum;
		hexDir2 = 30;
		hexDir3 = 60;
	}

	//updates the enemy shooting process
	public void updateHater(long updateLengthLong, int playerX, int playerY) {
		double updateLength = updateLengthLong / 1000000000.0;

		dirCal(playerX, playerY);

		if (!fireCoolDown) {
			GamePanel.newEnemyShot((int) xpos, (int) ypos, Math.toDegrees(dir), 200);
			GamePanel.newEnemyShot((int) xpos, (int) ypos, Math.toDegrees(dir)-4, 200);
			GamePanel.newEnemyShot((int) xpos, (int) ypos, Math.toDegrees(dir)+4, 200);
			fireCoolDown = true;
			shootTimer = 0;
		}
		if (fireCoolDown)
			shootTimer += updateLength;
		
		if (shootTimer > 3) {
			fireCoolDown = false;

		}
		
		hexDir2 = (hexDir2+updateLength*300)%360;
		hexDir3 = (hexDir3-updateLength*300)%360;
	}

	//calculates the direction of the enemy
	public void dirCal(int playerX, int playerY) {
		if (xpos - playerX > 0 && ypos - playerY > 0) {
			dir = Math.PI
					+ (Math.abs(Math.atan((playerY - ypos) / (playerX - xpos))));
		} else if (xpos - playerX > 0 && ypos - playerY < 0) {
			dir = Math.PI
					- (Math.abs(Math.atan((playerY - ypos) / (playerX - xpos))));
		} else if (xpos - playerX < 0 && ypos - playerY > 0) {
			dir = Math.PI
					* 2
					- (Math.abs(Math.atan((playerY - ypos) / (playerX - xpos))));
		} else {
			dir = Math.abs(Math.atan((playerY - ypos) / (playerX - xpos)));
		}
	}
	public int[] vertexCalX2(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians(hexDir2+60) % 360) * 12) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians(hexDir2+120) % 360) * 12) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians(hexDir2+180) % 360) * 12) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians(hexDir2+240) % 360) * 12) + xpos);
		int x5 = (int) ((Math.cos(Math.toRadians(hexDir2+300) % 360) * 12) + xpos);
		int x6 = (int) ((Math.cos(Math.toRadians(hexDir2+0) % 360) * 12) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4-camX, x5-camX, x6-camX};
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalY2(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians(hexDir2+60) % 360) * 12) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians(hexDir2+120) % 360) * 12) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians(hexDir2+180) % 360) * 12) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians(hexDir2+240) % 360) * 12) + ypos);
		int y5 = (int) ((Math.sin(Math.toRadians(hexDir2+300) % 360) * 12) + ypos);
		int y6 = (int) ((Math.sin(Math.toRadians(hexDir2+0) % 360) * 12) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4-camY, y5-camY, y6-camY};
		return vertexList;
	}
	public int[] vertexCalX3(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians(hexDir3+60) % 360) * 9) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians(hexDir3+120) % 360) * 9) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians(hexDir3+180) % 360) * 9) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians(hexDir3+240) % 360) * 9) + xpos);
		int x5 = (int) ((Math.cos(Math.toRadians(hexDir3+300) % 360) * 9) + xpos);
		int x6 = (int) ((Math.cos(Math.toRadians(hexDir3+0) % 360) * 9) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4-camX, x5-camX, x6-camX};
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalY3(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians(hexDir3+60) % 360) * 9) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians(hexDir3+120) % 360) * 9) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians(hexDir3+180) % 360) * 9) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians(hexDir3+240) % 360) * 9) + ypos);
		int y5 = (int) ((Math.sin(Math.toRadians(hexDir3+300) % 360) * 9) + ypos);
		int y6 = (int) ((Math.sin(Math.toRadians(hexDir3+0) % 360) * 9) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4-camY, y5-camY, y6-camY};
		return vertexList;
	}
	//returns some values to the gamepanel
	public int returnX() {
		return (int) xpos;
	}

	public int returnY() {
		return (int) ypos;
	}
	public double returnHitRad(){
		return 15.0;
	}

	public int returnTurretNum() {
		return position;
	}
}