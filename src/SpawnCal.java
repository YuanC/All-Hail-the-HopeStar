
public class SpawnCal {
	private int[][][] hater1Positions = { { { 150, 150 }, { 250, 150 }, { 150, 250 } },
			{ { 1050, 150 }, { 950, 150 }, { 1050, 250 } }, { { 150, 650 }, { 250, 650 }, { 150, 550 } },
			{ { 1050, 650 }, { 950, 650 }, { 1050, 550 } }, { { 500, 150 }, { 600, 150 }, { 700, 150 } },
			{  { 500, 650 }, { 600, 650 }, { 700, 650 } }};
	
	private int[][] turretPositions = { { 100, 400 },{ 300, 100 }, { 900, 100 },  { 1100, 400 },
			{ 900, 700 }, {300, 700 } };
	private int[][] hater3Positions = { { 250,250 },{ 250, 550 },{ 950,250 },{ 950, 550 }};
	public SpawnCal(){
		
	}
	
	public int[][] hater1Cal(){
		int i = (int)(Math.random()*6);
		return hater1Positions[i];
	}
	
	public int[] hater2Cal(int i){
		return turretPositions[i];
	}
	public int[] hater3Cal(){
		int i = (int)(Math.random()*4);
		return hater3Positions[i];
	}
}
