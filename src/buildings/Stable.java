package buildings;

import game.Board;

import java.util.ArrayList;

import units.Worker;

public class Stable extends Building{
	public final static int size=2;
	public final static String type="11";
	public final static int turnsLeftToBuild=20;
	public final static int[] recourcesNeeded = {50,50,250};
	private int trainLimit=1;
	private static int typeNum;
	
	public Stable(int row, int col, int ceId) {
		super(row, col, size, type, turnsLeftToBuild, recourcesNeeded, ceId);
		typeNum+=1;
		id="building_8_"+typeNum;
		placeOnBlocks();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getAvgRecourceUnder() {
		// TODO Auto-generated method stub
		return 0;
	}
	public String nowInTraining(){
		return isTraining+"/"+trainLimit;
	}

	
	public boolean isBusy(){
		if (isTraining<trainLimit)
			return false;
		return true;
	}
	
	final static int turnsToTrainNigga = 10;
	final static int turnsToTrainCluber = 14;
	int isTraining;
	
	
	int turnsLeftToTrainNigga;
	int turnsLeftToTrainCluber;
	public void trainNigga(){
		isTraining+=1;
		turnsLeftToTrainNigga=turnsToTrainNigga;
	}
	public void trainCluber(){
		isTraining+=1;
		turnsLeftToTrainCluber=turnsToTrainCluber;
	}
	
	public ArrayList<String> updateWithReturn() {
		ArrayList<String> ret = new ArrayList<String>();
		if (0<turnsLeftToTrainNigga){
			turnsLeftToTrainNigga-=1;
			if (turnsLeftToTrainNigga==0){
				if (ceId == 1)
					Board.derpCElization.addNiggaInfront(id);
				else
					Board.herpCElization.addNiggaInfront(id);
				
				ret.add("NiggaRider trained sol_nigga_"+(Worker.workersNum-1));
				isTraining-=1;
			}
		}
		if (0<turnsLeftToTrainCluber){
			turnsLeftToTrainCluber-=1;
			if (turnsLeftToTrainCluber==0){
				if (ceId == 1)
					Board.derpCElization.addCluberInfront(id);
				else
					Board.herpCElization.addCluberInfront(id);
				
				ret.add("Darter trained sol_cluber_"+(Worker.workersNum-1));
				isTraining-=1;
			}
		}
		return ret;
	}
	
	
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
