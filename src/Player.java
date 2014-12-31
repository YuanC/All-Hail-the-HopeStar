public class Player {

	private double xpos, ypos, dir, radDir, speed, speedX, speedY, faceDir;
	private final double maxSpeed = 300, drag = 550, acceleration = 1200,
			turnRate = 250;
	private double updateLength, shootTimer, rMultiplier, recoverCooldown, shieldRad, deathTimer = 0;
	private boolean slowDown, fireCoolDown, playerSlow, playerHit,
			shootingStatus, shieldReg;
	private int[] canvas = { 1200, 800 };
	private int health, score;

	// sets the inital values for the player
	public Player() {
		xpos = 400.0;
		ypos = 400.0;
		dir = 90.0;
		faceDir = -1;
		speed = 0;
		slowDown = false;
		shootTimer = 0;
		fireCoolDown = false;
		playerSlow = false;
		playerHit = false;
		health = 2;
		rMultiplier = 0;
		shootingStatus = false;
		recoverCooldown = 2.1;
		score = 0;
		shieldRad = 100.0;
		shieldReg = false;
		deathTimer = 0.0;
	}
	
	//resets values for a new game
	public void reset(){
		xpos = 400.0;
		ypos = 400.0;
		dir = 90.0;
		faceDir = -1;
		speed = 0;
		slowDown = false;
		shootTimer = 0;
		fireCoolDown = false;
		playerSlow = false;
		playerHit = false;
		health = 2;
		rMultiplier = 0;
		shootingStatus = false;
		recoverCooldown = 2.1;
		score = 0;
		shieldRad = 100.0;
		shieldReg = false;
		deathTimer = 0.0;
	}
	
	// updates player values
	public void update(long updateLengthLong, boolean[] playerActions) {

		updateLength = updateLengthLong / 1000000000.0;
		faceDir = dirCal(playerActions);
		slowDown = false;

		if (faceDir != -1.0) {
			dirChangeCal(updateLength);
			radDir = Math.toRadians(dir);
			accelCal(updateLength);
		}

		// calculates the player values
		dragCal(updateLength);
		speedUpdate(updateLength, playerActions);
		radDir = Math.toRadians(dir);

		// Player firing code. Includes cooldown and stuff
		if (playerActions[4])
			playerSlow = true;
		else
			playerSlow = false;

		if (playerActions[4]) {
			shootingStatus = true;
			if (!fireCoolDown) {
				GamePanel.newPlayerShot(600);
				fireCoolDown = true;
				shootTimer = 0;
			}
		}else
			shootingStatus = false;

		if (fireCoolDown)
			shootTimer += updateLength;
			rMultiplier = shootTimer/0.08;
		if (shootTimer > 0.1) {
			fireCoolDown = false;
		}
		
		if (recoverCooldown<2){
			recoverCooldown += updateLength;
		}else if (playerHit){
			recoverCooldown = 0;
		}
		
		if (shieldReg){
			shieldRad=100;	
			shieldReg = false;
		}
		if (shieldRad>3){
			shieldRad-=updateLength*100;
		}else{
			shieldReg = false;
			shieldRad = 0;
		}
		
		xpos = Math.min(Math.max(xpos + speedX, 50), canvas[0] - 50);
		ypos = Math.min(Math.max(ypos + speedY, 50), canvas[1] - 50);

	}

	// Adds acceleration if the player wants to move
	private void accelCal(double updateLength) {

		if (!slowDown) {
			if (speed >= maxSpeed)
				speed = maxSpeed;
			else
				speed += acceleration * updateLength;
		}

	}

	// Changes the angle the character is facing based on input
	private void dirChangeCal(double updateLength) {// Changes angle based on
													// input
		if (speed < 0.1) {
			dir = faceDir;

		} else {

			if (Math.abs((faceDir) - (dir)) < 2) {
				dir = faceDir;
			} else if (Math.abs(180 - Math.abs(faceDir - dir)) < 5) {
				slowDown = true;
			} else if (faceDir == 270 && (dir < 90 || dir > 270)) {
				dir -= (turnRate * updateLength);
			} else if (faceDir == 0 && (dir > 180)) {
				dir += (turnRate * updateLength);
			} else if (faceDir == 315 && dir < 135) {
				dir -= (turnRate * updateLength);
			} else if (faceDir == 45 && (dir > 225 || dir < 45)) {
				dir += (turnRate * updateLength);
			} else if (faceDir == 90 && dir > 270) {
				dir += (turnRate * updateLength);
			} else if (faceDir < dir) {
				dir -= (turnRate * updateLength);
			} else {
				dir += (turnRate * updateLength);
			}

			if (dir >= 360)
				dir = dir % 360;
			else if (dir < 0)
				dir = 360 - Math.abs(dir);

		}
	}

	// Calculates drag
	private void dragCal(double updateLength) {
		double dragMultiplier = 1;
		if (slowDown)
			dragMultiplier = 3;

		if (Math.abs(speed) - (drag * updateLength * dragMultiplier) < 0)
			speed = 0;
		else
			speed -= drag * updateLength * dragMultiplier;

	}

	// updates the x and y coordinates by adding the speed
	private void speedUpdate(double updateLength, boolean[] playerActions) {
		double speedMultiplier = 1;

		// slows the player if shooting
		if (playerSlow)
			speedMultiplier = 0.65;

		speedY = speed * speedMultiplier * Math.sin(radDir) * updateLength;
		speedX = speed * speedMultiplier * Math.cos(radDir) * updateLength;

	}

	// Calculates the x - values of the player icon
	public int[] vertexCalX(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians((dir)) % 360) * 13) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians((dir + 80)) % 360) * 10) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians((dir + 144)) % 360) * 10) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians((dir + 180)) % 360) * 3) + xpos);
		int x5 = (int) ((Math.cos(Math.toRadians((dir + 216)) % 360) * 10) + xpos);
		int x6 = (int) ((Math.cos(Math.toRadians((dir + 280)) % 360) * 10) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4 - camX,
				x5 - camX, x6 - camX };
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalY(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians((dir)) % 360) * 13) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians((dir + 80)) % 360) * 10) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians((dir + 144)) % 360) * 10) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians((dir + 180)) % 360) * 3) + ypos);
		int y5 = (int) ((Math.sin(Math.toRadians((dir + 216)) % 360) * 10) + ypos);
		int y6 = (int) ((Math.sin(Math.toRadians((dir + 280)) % 360) * 10) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4 - camY,
				y5 - camY, y6 - camY };
		return vertexList;

	}

	public int[] vertexCalXsmall(int camX) {
		int x1 = (int) ((Math.cos(Math.toRadians((dir)) % 360) * 13 * rMultiplier) + xpos);
		int x2 = (int) ((Math.cos(Math.toRadians((dir + 80)) % 360) * 10 * rMultiplier) + xpos);
		int x3 = (int) ((Math.cos(Math.toRadians((dir + 144)) % 360) * 10 * rMultiplier) + xpos);
		int x4 = (int) ((Math.cos(Math.toRadians((dir + 180)) % 360) * 3 * rMultiplier) + xpos);
		int x5 = (int) ((Math.cos(Math.toRadians((dir + 216)) % 360) * 10 * rMultiplier) + xpos);
		int x6 = (int) ((Math.cos(Math.toRadians((dir + 280)) % 360) * 10 * rMultiplier) + xpos);
		int[] vertexList = { x1 - camX, x2 - camX, x3 - camX, x4 - camX,
				x5 - camX, x6 - camX };
		return vertexList;

	}

	// Calculates the y - values of the player icon
	public int[] vertexCalYsmall(int camY) {
		int y1 = (int) ((Math.sin(Math.toRadians((dir)) % 360) * 13 * rMultiplier) + ypos);
		int y2 = (int) ((Math.sin(Math.toRadians((dir + 80)) % 360) * 10 * rMultiplier) + ypos);
		int y3 = (int) ((Math.sin(Math.toRadians((dir + 144)) % 360) * 10 * rMultiplier) + ypos);
		int y4 = (int) ((Math.sin(Math.toRadians((dir + 180)) % 360) * 3 * rMultiplier) + ypos);
		int y5 = (int) ((Math.sin(Math.toRadians((dir + 216)) % 360) * 10 * rMultiplier) + ypos);
		int y6 = (int) ((Math.sin(Math.toRadians((dir + 280)) % 360) * 10 * rMultiplier) + ypos);
		int[] vertexList = { y1 - camY, y2 - camY, y3 - camY, y4 - camY,
				y5 - camY, y6 - camY };
		return vertexList;

	}
	
	// Calculates the direction the player wants to go
	private double dirCal(boolean[] playerActions) {
		String vertical;
		String horizontal;

		if (playerActions[0] == playerActions[1])
			vertical = "_";
		else if (playerActions[0])
			vertical = "N";
		else
			vertical = "S";

		if (playerActions[2] == playerActions[3])
			horizontal = "_";
		else if (playerActions[2])
			horizontal = "W";
		else
			horizontal = "E";

		String dir = vertical + horizontal;
		if (dir.equals("__"))
			return -1.0;
		else if (dir.equals("_E"))
			return 0.0;
		else if (dir.equals("NE"))
			return 315.0;
		else if (dir.equals("N_"))
			return 270.0;
		else if (dir.equals("NW"))
			return 225.0;
		else if (dir.equals("_W"))
			return 180.0;
		else if (dir.equals("SW"))
			return 135.0;
		else if (dir.equals("S_"))
			return 90.0;
		else if (dir.equals("SE"))
			return 45.0;

		return 0.0;

	}
	
	// A bunch of functions to return values to the gamePanel class
	public void addScore(int addedScore){
		score+=addedScore;
	}
	public void setShield(){
		shieldReg = true;
	}
	
	public int returnX() {
		return (int) Math.round(xpos);
	}

	public int returnY() {
		return (int) Math.round(ypos);
	}

	public double returnDir() {
		return dir;
	}

	public double returnHitRad() {
		return 10.0;
	}

	public void setHitStatus(boolean playerHitStatus) {
		playerHit = playerHitStatus;

	}

	public boolean returnHitStatus() {
		return playerHit;
	}

	public boolean shootingStatus() {
		return shootingStatus;
	}
	
	public int returnHealth(){
		return health;
	}

	public double returnRecoverCooldown() {
		return recoverCooldown;
	}
	public void loseHealth(){
		health-=1;
	}
	public void gainHealth(){
		health=2;
	}
	public int returnScore(){
		return score;
	}
	public double returnShield(){
		return shieldRad;
	}
}
