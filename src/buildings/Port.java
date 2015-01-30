package buildings;

import java.util.ArrayList;

import units.Boat;

import game.BlockType;
import game.Board;

public class Port extends Building {
	public final static int size = 2;
	final static String type = "9";
	public final static int[] recourcesNeeded = { 20, 160, 140 };
	final static int turnsToBuild = 24;
	private int trainLimit = 1;
	private int isTraining = 0;
	private int turnsToTrain = 8;
	private int turnsLeftToTrain;

	private static int typeNum;

	public Port(int row, int col,int ceId) {
		super(row, col, size, type, turnsToBuild, recourcesNeeded,ceId);
		typeNum += 1;
		id = "building_9_" + typeNum;
		placeOnBlocks();
	}

	public boolean isBusy() {
		if (isTraining < trainLimit)
			return false;
		return true;
	}

	public void train() {
		isTraining += 1;
		turnsLeftToTrain = turnsToTrain;
	}

	
	
	
	
	
	
	public ArrayList<String> updateWithReturn() {
		ArrayList<String> ret = new ArrayList<String>();
		
		
		if (0 < turnsLeftToTrain) {
			turnsLeftToTrain -= 1;
			if (turnsLeftToTrain == 0){
				if (ceId == 1)
					Board.derpCElization.addBoatInfront(id);
				else
					Board.herpCElization.addBoatInfront(id);
				
				ret.add("boat trained boat_"+(Boat.boatsNum+1));
			}
		}

		
		
		return ret;
	}
	
	
	
	
	
	
	
	public void update() {
		
	}

	public int getAvgRecourceUnder() {
		return 0;
	}

	static public int[] findCoast(Port port) {
		int pRow = port.getLocation()[0];
		int pCol = port.getLocation()[1];
		int trow = -1, tcol = -1;
		int size = port.getSize();
		if (0 < pRow
				&& Board.map[pRow - 1][pCol].type().equals(BlockType.Water)) {
			trow = pRow - 1;
			tcol = pCol;
		} else if (0 < pCol
				&& Board.map[pRow][pCol - 1].type().equals(BlockType.Water)) {
			trow = pRow;
			tcol = pCol - 1;
		} else if (0 < pCol
				&& Board.map[pRow][pCol + size].type().equals(BlockType.Water)) {
			trow = pRow;
			tcol = pCol + size;
		} else if (0 < pCol
				&& Board.map[pRow + size][pCol].type().equals(BlockType.Water)) {
			trow = pRow + size;
			tcol = pCol;
		}
		int[] ret={trow,tcol};
		return ret;
	}

}
