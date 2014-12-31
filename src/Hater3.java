public class Hater3 {

	private double xpos, ypos, dir, speed, shootTimer, hexDir, circLength;
	private int[] canvas = { 1200, 800 };
	private boolean fireCoolDown;

	public Hater3(int x, int y) {
		speed = 50;
		dir = 0;
		hexDir = 0;
		xpos = x;
		ypos = y;
		shootTimer = 0;
		fireCoolDown = true;
	}

	// updates the enemy's position
	public void updateHater(long updateLengthLong, int playerX, int playerY) {

		double updateLength = updateLengthLong / 1000000000.0;

		dirCal(playerX, playerY);

		if (!fireCoolDown) {
			GamePanel.newEnemyShot((int) xpos, (int) ypos, 45, 200);
			GamePanel.newEnemyShot((int) xpos, (int) ypos, 135, 200);
			GamePanel.newEnemyShot((int) xpos, (int) ypos, 225, 200);
			GamePanel.newEnemyShot((int) xpos, (int) ypos, 315, 200);
			fireCoolDown = true;
			shootTimer = 0;
		}
		if (fireCoolDown)
			shootTimer += updateLength;

		if (shootTimer > 2) {
			fireCoolDown = false;

		}
		
		hexDir+=(updateLength*400)%360;
		circLength=(circLength+updateLength*200)%40;
		
		xpos = Math.min(
				Math.max(xpos + speed * updateLength * Math.cos(dir), 60),
				canvas[0] - 60);
		ypos = Math.min(
				Math.max(ypos + speed * updateLength * Math.sin(dir), 60),
				canvas[1] - 60);

	}

	// updates the direction fo the enenmy in relation to the player
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
		int x1 = (int) ((Math.cos(Math.toRadians(hexDir+60) % 360) * 13) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians(hexDir+120) % 360) * 13) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians(hexDir+180) % 360) * 13) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians(hexDir+240) % 360) * 13) + xpos);
		int x5 = (int) ((Math.cos(Math.toRadians(hexDir+300) % 360) * 13) + xpos);
		int x6 = (int) ((Math.cos(Math.toRadians(hexDir+0) % 360) * 13) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4-camX, x5-camX, x6-camX};
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalY2(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians(hexDir+60) % 360) * 13) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians(hexDir+120) % 360) * 13) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians(hexDir+180) % 360) * 13) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians(hexDir+240) % 360) * 13) + ypos);
		int y5 = (int) ((Math.sin(Math.toRadians(hexDir+300) % 360) * 13) + ypos);
		int y6 = (int) ((Math.sin(Math.toRadians(hexDir+0) % 360) * 13) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4-camY, y5-camY, y6-camY};
		return vertexList;
	}
	// returns some values to the gamepanel
	public int returnX() {
		return (int) xpos;
	}

	public int returnY() {
		return (int) ypos;
	}

	public double returnHitRad() {
		return 20.0;
	}
	public int returnCircRad() {
		return (int)circLength;
	}
}
