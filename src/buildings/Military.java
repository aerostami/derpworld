package buildings;

import game.Board;

import java.util.ArrayList;

import units.Worker;

public class Military extends Building{
	public final static int size=2;
	public final static String type="10";
	public final static int turnsLeftToBuild=15;
	public final static int[] recourcesNeeded = {20,50,200};
	private int trainLimit=1;
	private static int typeNum;
	
	public Military(int row, int col, int ceId) {
		super(row, col, size, type, turnsLeftToBuild, recourcesNeeded, ceId);
		typeNum+=1;
		id="building_10_"+typeNum;
		placeOnBlocks();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getAvgRecourceUnder() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public boolean isBusy(){
		if (isTraining<trainLimit)
			return false;
		return true;
	}

	
	final static int turnsToTrain = 8;
	int isTraining;
	
	
	int turnsLeftToTrainAxer;
	int turnsLeftToTrainDarter;
	
	public void trainAxer(){
		isTraining+=1;
		turnsLeftToTrainAxer=turnsToTrain;
	}
	public void trainDarter(){
		isTraining+=1;
		turnsLeftToTrainDarter=turnsToTrain;
	}
	
	public ArrayList<String> updateWithReturn() {
		ArrayList<String> ret = new ArrayList<String>();
		if (0<turnsLeftToTrainAxer){
			turnsLeftToTrainAxer-=1;
			if (turnsLeftToTrainAxer==0){
				if (ceId == 1)
					Board.derpCElization.addAxerInfront(id);
				else
					Board.herpCElization.addAxerInfront(id);
				
				ret.add("Axer trained sol_axer_"+(Worker.workersNum-1));
				isTraining-=1;
			}
		}
		if (0<turnsLeftToTrainDarter){
			turnsLeftToTrainDarter-=1;
			if (turnsLeftToTrainDarter==0){
				if (ceId == 1)
					Board.derpCElization.addDarterInfront(id);
				else
					Board.herpCElization.addDarterInfront(id);
				
				ret.add("Darter trained sol_darter_"+(Worker.workersNum-1));
				isTraining-=1;
			}
		}
		return ret;
	}
	
	public String nowInTraining(){
		return isTraining+"/"+trainLimit;
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
