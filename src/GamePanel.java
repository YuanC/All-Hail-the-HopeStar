import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.*;

class GamePanel extends JPanel implements KeyListener {

	// Stuff for game timer calculator, slowing time, canvas size, etcc.....
	private long lastTime = System.nanoTime(), presentTime, lastFpsTime = 0,
			updateLength, optimalFPS = 1000000000 / 60;
	private int fps = 0, camX, camY, temp2, gameState, lastGameScore;
	private boolean gameRunning, slowDown, slowCoolDown, death;
	private int[] canvas = { 1200, 800 };
	private double slowMeter, gameTimer, updateLengthMultiplier,
			nextSpawnTime1, nextSpawnTime2, nextSpawnTime3, nextShield,
			deathTimer, tempTimer, lastGameLength;

	// creates player object and collision object
	private static Player player = new Player();
	private Collision collisionCal = new Collision();
	private SpawnCal spawnCal = new SpawnCal();

	// arraylist to hold enemies, projectiles
	private static ArrayList<Hater1> hater1List = new ArrayList<Hater1>();
	private static ArrayList<Hater2> hater2List = new ArrayList<Hater2>();
	private static ArrayList<Hater3> hater3List = new ArrayList<Hater3>();
	private static ArrayList<Projectile> playerShots = new ArrayList<Projectile>();
	private static ArrayList<Projectile> enemyShots = new ArrayList<Projectile>();
	private static ArrayList<Integer> removeList = new ArrayList<Integer>();
	private static ArrayList<SpawnWarning> spawnList1 = new ArrayList<SpawnWarning>();
	private static ArrayList<SpawnWarning> spawnList2 = new ArrayList<SpawnWarning>();
	private static ArrayList<SpawnWarning> spawnList3 = new ArrayList<SpawnWarning>();
	private static ArrayList<EnemyExplosion> explosionList = new ArrayList<EnemyExplosion>();

	// [up, down, left, right, shoot]
	private boolean[] playerActions = { false, false, false, false, false },
			turretPositionStatus = { false, false, false, false, false, false };

	// construct a MyFrame object
	public GamePanel() {

		setFocusable(true);
		addKeyListener(this);
		temp2 = 0;
		gameRunning = true;
		updateLengthMultiplier = 1;
		slowMeter = 3.0;
		slowDown = false;
		slowCoolDown = false;
		camX = player.returnX();
		camY = player.returnY();
		gameTimer = 0.0;
		nextSpawnTime1 = 3.0;
		nextSpawnTime2 = 10.0;
		nextSpawnTime3 = 15.0;
		nextShield = 30.0;
		deathTimer = 0.0;
		death = false;
		gameState = 1;
		tempTimer = 0;
		lastGameLength = 10.09;
		lastGameScore = 5000;
	}

	// paint() is called automatically by the system
	public void paint(Graphics g) {

		super.paint(g);

		if (gameState == 1) {

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 907, 809);
			g.setFont(new Font("Arial", Font.BOLD, 60));
			g.setColor(Color.WHITE);
			g.drawString("ALL HAIL THE HOPESTAR", 70, 100);
			g.setFont(new Font("Arial", Font.PLAIN, 20));

			g.drawString("PRESS X TO SHOOT", 350, 150);
			g.drawString("ARROW KEYS TO MOVE", 330, 180);
			g.drawString("SHIFT TO SLOW TIME", 345, 210);

			g.drawString("DODGE THE BULLETS", 345, 300);
			g.drawString("ELIMINATE THE ENEMY", 335, 330);
			g.drawString("SURVIVE UNTIL DEAD", 345, 360);

			g.drawString("SHIELDS REGEN EVERY 30 SECONDS", 270, 450);
			g.drawString("GOOD LUCK", 390, 480);

			tempTimer = (tempTimer + updateLength / 1000000000.0) % 1;
			if (tempTimer > 0.5) {
				g.setFont(new Font("Arial", Font.PLAIN, 30));
				g.drawString("Press X to Start", 350, 600);
			}

		} else if (gameState == 3) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 907, 809);

			g.setFont(new Font("Arial", Font.BOLD, 60));
			g.setColor(Color.BLACK);
			g.drawString("A VALIANT EFFORT", 150, 100);

			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("YOU LASTED " + lastGameLength + " SECS", 320, 150);
			g.drawString("YOU SCORED " + lastGameScore + " PTS", 330, 180);
			tempTimer = (tempTimer + updateLength / 1000000000.0) % 1;
			if (tempTimer > 0.5) {
				g.setFont(new Font("Arial", Font.PLAIN, 30));
				g.drawString("Press X to try again", 330, 600);
			}
		}

		else if (gameState == 2) {
			// calculates the slowTime mechanic and updates objects
			updateLengthMultiplier = slowDownCal(slowDown, updateLength);
			updateStuff((long) (updateLength * updateLengthMultiplier));

			// fills with a black background
			g.setColor(Color.BLACK);
			g.fillRect(0 - camX, 0 - camY, canvas[0], canvas[1]);

			// draws the boundaries
			g.setColor(Color.gray);
			g.drawRect(40 - camX, 40 - camY, canvas[0] - 80, canvas[1] - 80);
			for (int i = 0; i < spawnList1.size(); i++) {
				g.drawPolygon(spawnList1.get(i).vertexCalX(camX), spawnList1
						.get(i).vertexCalY(camY), 3);
			}
			for (int i = 0; i < spawnList2.size(); i++) {
				g.drawPolygon(spawnList2.get(i).vertexCalX(camX), spawnList2
						.get(i).vertexCalY(camY), 3);
			}
			for (int i = 0; i < spawnList3.size(); i++) {
				g.drawPolygon(spawnList3.get(i).vertexCalX(camX), spawnList3
						.get(i).vertexCalY(camY), 3);
			}

			g.setColor(Color.darkGray);
			if (player.returnHealth() == 2) {
				g.fillPolygon(player.vertexCalX(camX), player.vertexCalY(camY),
						6);
			}

			// everything after this is white
			g.setColor(Color.WHITE);

			// displays timer and score
			g.drawString(Math.round(gameTimer * 10) / 10.0 + "\tsecs", 820, 638);
			g.drawString(player.returnScore() + " pts", 820, 650);

			// draws the player and the player name

			// draws the player and animations
			if (!death) {
				g.drawPolygon(player.vertexCalX(camX), player.vertexCalY(camY),
						6);
				g.drawString("HopeStar", player.returnX() - "HopeStar".length()
						* 3 - 3 - camX, player.returnY() - 20 - camY);
			}

			if (player.shootingStatus() && !death)
				g.drawPolygon(player.vertexCalXsmall(camX),
						player.vertexCalYsmall(camY), 6);

			// draws the slowmeter
			g.drawLine(872 - (int) (slowMeter * 100), 665, 872, 665);
			g.drawLine(572, 660, 572, 670);
			g.drawLine(872, 660, 872, 670);

			// Draws projectiles
			for (int i = 0; i < playerShots.size(); i++) {
				g.drawOval(playerShots.get(i).returnX() - 3 - camX, playerShots
						.get(i).returnY() - 3 - camY, (int) playerShots.get(i)
						.returnHitRad(), (int) playerShots.get(i)
						.returnHitRad());
			}

			// draws all the enemy projectiles
			for (int i = 0; i < enemyShots.size(); i++) {
				g.drawOval(enemyShots.get(i).returnX() - 3 - camX, enemyShots
						.get(i).returnY() - 3 - camY, 5, 5);
			}

			// draws all the explosions
			for (int i = 0; i < explosionList.size(); i++) {
				g.drawOval(explosionList.get(i).returnX()
						- explosionList.get(i).returnRad() / 2 - camX,
						explosionList.get(i).returnY()
								- explosionList.get(i).returnRad() / 2 - camY,
						explosionList.get(i).returnRad(), explosionList.get(i)
								.returnRad());
				g.drawString(
						"" + explosionList.get(i).returnScore(),
						explosionList.get(i).returnX()
								- ("" + explosionList.get(i).returnScore())
										.length() * 3 - 3 - camX, explosionList
								.get(i).returnY() + 5 - camY);
			}

			// draws the enemies
			for (int i = 0; i < hater1List.size(); i++) {
				g.drawPolygon(hater1List.get(i).vertexCalX1(camX), hater1List
						.get(i).vertexCalY1(camY), 4);
				g.drawPolygon(hater1List.get(i).vertexCalX2(camX), hater1List
						.get(i).vertexCalY2(camY), 4);
				g.drawPolygon(hater1List.get(i).vertexCalX3(camX), hater1List
						.get(i).vertexCalY3(camY), 8);
			}
			for (int i = 0; i < hater2List.size(); i++) {
				g.drawPolygon(hater2List.get(i).vertexCalX2(camX), hater2List
						.get(i).vertexCalY2(camY), 6);
				g.fillPolygon(hater2List.get(i).vertexCalX3(camX), hater2List
						.get(i).vertexCalY3(camY), 6);
			}
			for (int i = 0; i < hater3List.size(); i++) {
				g.drawOval(hater3List.get(i).returnX() - camX - 20, hater3List
						.get(i).returnY() - camY - 20, 40, 40);
				g.drawOval(
						hater3List.get(i).returnX() - camX
								- hater3List.get(i).returnCircRad() / 2,
						hater3List.get(i).returnY() - camY
								- hater3List.get(i).returnCircRad() / 2,
						hater3List.get(i).returnCircRad(), hater3List.get(i)
								.returnCircRad());
				g.fillPolygon(hater3List.get(i).vertexCalX2(camX), hater3List
						.get(i).vertexCalY2(camY), 6);
			}
		}
	}

	// UPDATES ALL THE OBJECTS
	public void updateStuff(long updateLength) {

		// calculates if the player has died
		deathCal();

		// delays the game a bit after the player dies
		if (death) {
			deathTimer += updateLength / 1000000000.0;
		}
		if (deathTimer > 3) {
			lastGameLength = Math.round(gameTimer * 10) / 10.0;
			lastGameScore = player.returnScore();
			gameReset();
			gameState = 3;
		}

		// updates the spawning system
		spawnUpdate(updateLength);

		// updates the timer of the game
		timerUpdate(updateLength);

		// updates all of the units
		unitsUpdate(updateLength);

		// Updates the camera
		camX = Math.min(Math.max(player.returnX() - 450, 0), canvas[0] - 900);
		camY = Math.min(Math.max(player.returnY() - 340, 0), canvas[1] - 680);

	}

	// resets all the stuff for a new game
	private void gameReset() {

		temp2 = 0;
		gameRunning = true;
		updateLengthMultiplier = 1;
		slowMeter = 3.0;
		slowDown = false;
		slowCoolDown = false;
		camX = player.returnX();
		camY = player.returnY();
		gameTimer = 0.0;
		nextSpawnTime1 = 3.0;
		nextSpawnTime2 = 10.0;
		nextSpawnTime3 = 15.0;
		nextShield = 30.0;
		deathTimer = 0.0;
		death = false;
		gameState = 1;
		hater1List.clear();
		hater2List.clear();
		hater3List.clear();
		playerShots.clear();
		enemyShots.clear();
		removeList.clear();
		spawnList1.clear();
		spawnList2.clear();
		spawnList3 = new ArrayList<SpawnWarning>();
		explosionList.clear();
		player.reset();
	}

	// Checks to see if the player died or not
	public void deathCal() {
		if (player.returnHealth() < 1) {
			death = true;
		}
	}

	// UPDATES ALL THE UNITS
	public void unitsUpdate(long updateLength) {
		int projectileListSizeP = playerShots.size();
		int projectileListSizeE = enemyShots.size();
		int hater1ListSize = hater1List.size();
		int hater2ListSize = hater2List.size();
		int hater3ListSize = hater3List.size();

		Hater1 tempHater1;
		Hater2 tempHater2;
		Hater3 tempHater3;

		// Updates player shot positions
		removeList.clear();
		for (int i = 0; i < projectileListSizeP; i++) {
			playerShots.get(i).update(updateLength);
			if (playerShots.get(i).deleteCal(canvas[0], canvas[1])) {
				removeList.add(i);
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			playerShots.remove(removeList.get(i) - i);
		}

		if (player.returnHitStatus()) {
			newPlayerExplosion(600);
		}

		// Updates enemy projectile positions
		removeList.clear();
		for (int i = 0; i < projectileListSizeE; i++) {
			enemyShots.get(i).update(updateLength);

			if (enemyShots.get(i).deleteCal(canvas[0], canvas[1])) {
				removeList.add(i);
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			enemyShots.remove(removeList.get(i) - i);
		}

		// updates the enemy 1 positions and calculates hit detection
		removeList.clear();
		for (int i = 0; i < hater1ListSize; i++) {
			tempHater1 = hater1List.get(i);
			hater1List.get(i).updateHater(updateLength, player.returnX(),
					player.returnY());

			if (collisionCal.listHitCal(tempHater1.returnX(),
					tempHater1.returnY(), tempHater1.returnHitRad(),
					playerShots) >= 0) {
				removeList.add(i);
				playerShots.remove(collisionCal.listHitCal(
						tempHater1.returnX(), tempHater1.returnY(),
						tempHater1.returnHitRad(), playerShots));
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			explosionList.add(new EnemyExplosion(hater1List.get(
					removeList.get(i) - i).returnX(), hater1List.get(
					removeList.get(i) - i).returnY(), 1));
			player.addScore(100);
			hater1List.remove(removeList.get(i) - i);
		}

		// updates enemy2's positions and calculates hit detection
		removeList.clear();
		for (int i = 0; i < hater2ListSize; i++) {
			tempHater2 = hater2List.get(i);

			hater2List.get(i).updateHater(updateLength, player.returnX(),
					player.returnY());
			if (collisionCal.listHitCal(tempHater2.returnX(),
					tempHater2.returnY(), tempHater2.returnHitRad(),
					playerShots) >= 0) {
				removeList.add(i);
				playerShots.remove(collisionCal.listHitCal(
						tempHater2.returnX(), tempHater2.returnY(),
						tempHater2.returnHitRad(), playerShots));
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			turretPositionStatus[hater2List.get(removeList.get(i) - i)
					.returnTurretNum()] = false;
			explosionList.add(new EnemyExplosion(hater2List.get(
					removeList.get(i) - i).returnX(), hater2List.get(
					removeList.get(i) - i).returnY(), 2));
			player.addScore(200);
			hater2List.remove(removeList.get(i) - i);
		}

		// updates the enemy 3 positions and calculates hit detection
		removeList.clear();
		for (int i = 0; i < hater3ListSize; i++) {
			tempHater3 = hater3List.get(i);
			hater3List.get(i).updateHater(updateLength, player.returnX(),
					player.returnY());

			if (collisionCal.listHitCal(tempHater3.returnX(),
					tempHater3.returnY(), tempHater3.returnHitRad(),
					playerShots) >= 0) {
				removeList.add(i);
				playerShots.remove(collisionCal.listHitCal(
						tempHater3.returnX(), tempHater3.returnY(),
						tempHater3.returnHitRad(), playerShots));
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			explosionList.add(new EnemyExplosion(hater3List.get(
					removeList.get(i) - i).returnX(), hater3List.get(
					removeList.get(i) - i).returnY(), 3));
			player.addScore(300);
			hater3List.remove(removeList.get(i) - i);
		}

		// updates and deletes explosions
		removeList.clear();
		for (int i = 0; i < explosionList.size(); i++) {
			explosionList.get(i).Update(updateLength);
			if (explosionList.get(i).deleteCheck())
				removeList.add(i);
		}

		for (int i = 0; i < removeList.size(); i++)
			explosionList.remove(removeList.get(i) - i);

		// Updates the player position and calculates hit collision
		player.update(updateLength, playerActions);

		if (player.returnRecoverCooldown() > 2.0) {
			if (collisionCal.listHitCal(player.returnX(), player.returnY(),
					player.returnHitRad(), enemyShots) >= 0) {
				// System.exit(0);
				player.loseHealth();
				player.setHitStatus(true);
			} else if (collisionCal.listHitCalHater1(player.returnX(),
					player.returnY(), player.returnHitRad(), hater1List)) {
				// System.exit(0);
				player.loseHealth();
				player.setHitStatus(true);
			} else if (collisionCal.listHitCalHater2(player.returnX(),
					player.returnY(), player.returnHitRad(), hater2List)) {
				// System.exit(0);
				player.loseHealth();
				player.setHitStatus(true);
			} else if (collisionCal.listHitCalHater3(player.returnX(),
					player.returnY(), player.returnHitRad(), hater3List)) {
				// System.exit(0);
				player.loseHealth();
				player.setHitStatus(true);
			} else {
				player.setHitStatus(false);
			}
		} else {
			player.setHitStatus(false);
		}
	}

	// Adds a new shot to projectiles list
	public static void newPlayerShot(double projectileSpeed) {
		double dir = player.returnDir();
		int xpos = player.returnX();
		int ypos = player.returnY();

		playerShots.add(new Projectile(xpos, ypos, (dir + 3) % 360,
				projectileSpeed));
		playerShots.add(new Projectile(xpos, ypos, (dir + 1) % 360,
				projectileSpeed));
		playerShots.add(new Projectile(xpos, ypos, (dir - 1) % 360,
				projectileSpeed));
		playerShots.add(new Projectile(xpos, ypos, (dir - 3) % 360,
				projectileSpeed));
	}

	public static void newPlayerExplosion(double projectileSpeed) {
		double dir = player.returnDir();
		int xpos = player.returnX();
		int ypos = player.returnY();

		xpos += (int) (Math.cos(Math.toRadians(dir)) * 15);
		ypos += (int) (Math.sin(Math.toRadians(dir)) * 15);

		for (int i = 0; i < 120; i++) {
			playerShots.add(new Projectile(xpos, ypos, (dir + 3 * i) % 360,
					projectileSpeed));
		}
	}

	public static void newEnemyShot(int xpos, int ypos, double dir, double speed) {
		enemyShots.add(new Projectile(xpos, ypos, (dir), speed));
	}

	// Lets the player press shift to slow down
	public double slowDownCal(boolean slowDown, long updateLength) {

		if (slowMeter < 0 && slowCoolDown == false) {
			slowCoolDown = true;
			return 1;
		} else if (slowCoolDown == true || (!slowDown && slowMeter < 3)) {
			slowMeter += (double) (updateLength / 1000000000.0) * 0.5;
			slowCoolDown = true;
			if (slowMeter > 3) {
				slowMeter = 3;
				slowCoolDown = false;
			}
			return 1;
		} else if (slowDown && slowMeter > 0) {
			slowMeter -= (double) (updateLength / 1000000000.0) * 0.6 * 2;
			return 0.3;
		}
		return 1;
	}

	private void timerUpdate(long updateLengthLong) {
		double updateLength = updateLengthLong / 1000000000.0;
		gameTimer += updateLength;
	}

	public void spawnUpdate(long updateLength) {

		// spawns the enemy type 1 after the indicator is done
		removeList.clear();
		for (int i = 0; i < spawnList1.size(); i++) {
			spawnList1.get(i).update(updateLength);
			if (spawnList1.get(i).deleteCheck()) {
				removeList.add(i);
				hater1List.add(new Hater1(spawnList1.get(i).returnX(),
						spawnList1.get(i).returnY()));
			}
		}
		// removes the spawnindicator
		for (int i = 0; i < removeList.size(); i++)
			spawnList1.remove(removeList.get(i) - i);

		// spawns the enemy type 2 after the indicator is done
		removeList.clear();
		for (int i = 0; i < spawnList2.size(); i++) {
			spawnList2.get(i).update(updateLength);
			if (spawnList2.get(i).deleteCheck()) {
				removeList.add(i);
				hater2List.add(new Hater2(spawnList2.get(i).returnX(),
						spawnList2.get(i).returnY(), spawnList2.get(i)
								.returnTurretNum()));
			}
		}
		// removes the spawnIndicator
		for (int i = 0; i < removeList.size(); i++)
			spawnList2.remove(removeList.get(i) - i);

		// spawns the enemy type 3 after the indicator is done
		removeList.clear();
		for (int i = 0; i < spawnList3.size(); i++) {
			spawnList3.get(i).update(updateLength);
			if (spawnList3.get(i).deleteCheck()) {
				removeList.add(i);
				hater3List.add(new Hater3(spawnList3.get(i).returnX(),
						spawnList3.get(i).returnY()));
			}
		}
		// removes the spawn indicator
		for (int i = 0; i < removeList.size(); i++)
			spawnList3.remove(removeList.get(i) - i);

		// Adds a spawn warning for enemy1
		if (gameTimer > nextSpawnTime1) {

			int[][] tempList = spawnCal.hater1Cal();

			for (int i = 0; i < tempList.length; i++) {
				spawnList1
						.add(new SpawnWarning(tempList[i][0], tempList[i][1]));
			}

			nextSpawnTime1 += 2.1;
		}

		// adds spawnwarning for enemy2
		if (gameTimer > nextSpawnTime2) {

			if ((int) (hater2List.size() + spawnList2.size()) < 6) {// something
																	// wrong
				int tempInt;

				while (true) {
					tempInt = (int) (Math.random() * 6);
					if (!(turretPositionStatus[tempInt]))
						break;
				}

				int[] tempList2 = spawnCal.hater2Cal(tempInt);

				turretPositionStatus[tempInt] = true;
				spawnList2.add(new SpawnWarning(tempList2[0], tempList2[1],
						tempInt));

			}
			if (hater2List.size() + spawnList2.size() < 6) {// something wrong

				int tempInt;

				while (true) {
					tempInt = (int) (Math.random() * 6);
					if (!(turretPositionStatus[tempInt]))
						break;
				}

				int[] tempList2 = spawnCal.hater2Cal(tempInt);

				turretPositionStatus[tempInt] = true;
				spawnList2.add(new SpawnWarning(tempList2[0], tempList2[1],
						tempInt));

			}
			nextSpawnTime2 += 4.6;
		}
		// Adds a spawn warning for enemy3
		if (gameTimer > nextSpawnTime3) {

			int[] tempList = spawnCal.hater3Cal();
			spawnList3.add(new SpawnWarning(tempList[0], tempList[1]));
			nextSpawnTime3 += 10;
		}

		if (gameTimer > nextShield) {
			player.setShield();
			nextShield += 30;
			player.gainHealth();
		}

	}

	// The gameloop which currently executes at 60fps.
	// Finds the time between loops and uses it to update objects
	public void run() {
		while (gameRunning) {
			repaint();
			presentTime = System.nanoTime();
			updateLength = presentTime - lastTime;
			lastTime = presentTime;

			if (lastFpsTime >= 1000000000) {
				lastFpsTime = 0;
				temp2 = fps;
				fps = 0;
			}

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			try {
				Thread.sleep((optimalFPS - (lastTime - System.nanoTime())) / 1000000);
			} catch (Exception e) {

			}
		}
	}

	// When a key is pressed
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			playerActions[0] = true;
			break;

		case KeyEvent.VK_DOWN:
			playerActions[1] = true;
			break;

		case KeyEvent.VK_LEFT:
			playerActions[2] = true;
			break;

		case KeyEvent.VK_RIGHT:
			playerActions[3] = true;
			break;

		case KeyEvent.VK_X:
			playerActions[4] = true;
			break;
		case KeyEvent.VK_SHIFT:
			slowDown = true;
			break;
		}

	}

	// When a key is released
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			playerActions[0] = false;
			break;
		case KeyEvent.VK_DOWN:
			playerActions[1] = false;
			break;
		case KeyEvent.VK_LEFT:
			playerActions[2] = false;
			break;
		case KeyEvent.VK_RIGHT:
			playerActions[3] = false;
			break;
		case KeyEvent.VK_X:
			playerActions[4] = false;
			gameState = 2;
			break;
		case KeyEvent.VK_SHIFT:
			slowDown = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
} // end class