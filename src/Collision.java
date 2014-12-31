import java.util.ArrayList;

public class Collision {

	public Collision(){
		
	}
	
	public int listHitCal(int xpos, int ypos, double hitRad, ArrayList<Projectile> playerShots){
		Projectile tempProj;
		for (int i =0;i<playerShots.size();i++){
			tempProj = playerShots.get(i);
			if (Math.pow((tempProj.returnX()-xpos), 2)+Math.pow((tempProj.returnY()-ypos), 2)<Math.pow(tempProj.returnHitRad()+hitRad,2)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean listHitCalHater1(int xpos, int ypos, double hitRad, ArrayList<Hater1> hater1List){
		Hater1 tempHater;
		for (int i =0;i<hater1List.size();i++){
			tempHater = hater1List.get(i);
			if (Math.pow((tempHater.returnX()-xpos), 2)+Math.pow((tempHater.returnY()-ypos), 2)<Math.pow(tempHater.returnHitRad()+hitRad,2)){
				return true;
			}
		}
		return false;
	}
	public boolean listHitCalHater2(int xpos, int ypos, double hitRad, ArrayList<Hater2> hater2List){
		Hater2 tempHater;
		for (int i =0;i<hater2List.size();i++){
			tempHater = hater2List.get(i);
			if (Math.pow((tempHater.returnX()-xpos), 2)+Math.pow((tempHater.returnY()-ypos), 2)<Math.pow(tempHater.returnHitRad()+hitRad,2)){
				return true;
			}
		}
		return false;
	}

	public boolean listHitCalHater3(int xpos, int ypos,
			double hitRad, ArrayList<Hater3> hater3List) {
		Hater3 tempHater;
		for (int i =0;i<hater3List.size();i++){
			tempHater = hater3List.get(i);
			if (Math.pow((tempHater.returnX()-xpos), 2)+Math.pow((tempHater.returnY()-ypos), 2)<Math.pow(tempHater.returnHitRad()+hitRad,2)){
				return true;
			}
		}
		return false;
	}
}
