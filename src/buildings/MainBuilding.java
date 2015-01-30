package buildings;

import java.util.ArrayList;

import units.Worker;

import game.Board;

public class MainBuilding extends Building {
	final static String type = "1";
	public final static int[] recourcesNeeded = { 0, 10, 20 };
	static final private int turnsToBuild = 2;
	public final static int size = 3;
	private static int typeNum;
	private int trainLimit = 1;
	private int isTraining = 0;
	private final int turnsToTrain = 2;
	private int turnsLeftToTrain;

	public MainBuilding(int row, int col, int ceId) {
		super(row, col, size, type, turnsToBuild, recourcesNeeded, ceId);
		typeNum += 1;
		id = "building_1_" + typeNum;
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
			if (turnsLeftToTrain == 0) {
				if (ceId == 1)
					Board.derpCElization.addWorkerInfront(id);
				else
					Board.herpCElization.addWorkerInfront(id);
				ret.add("worker trained worker_" + (Worker.workersNum - 1));
				isTraining -= 1;
			}
		}
		return ret;
	}

	public void update() {

	}

	public int getAvgRecourceUnder() {
		return 0;
	}

}
