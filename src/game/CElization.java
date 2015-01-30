package game;

import java.util.ArrayList;
import java.util.Arrays;

import soldier.Axer;
import soldier.CluberRider;
import soldier.Darter;
import soldier.NiggaRider;
import soldier.Soldier;
import units.Boat;
import units.Unit;
import units.Worker;
import buildings.Building;
import buildings.Farm;
import buildings.GoldMine;
import buildings.MainBuilding;
import buildings.Market;
import buildings.Military;
import buildings.Port;
import buildings.Stable;
import buildings.StockPile;
import buildings.StoneMine;
import buildings.University;
import buildings.WoodCamp;

public class CElization {

	int resourceCapacity;
	int goldInBank, stoneInBank, lumberInBank, foodInBank, knowledge;
	boolean isDiscovered[][];
	ArrayList<Building> buildings = new ArrayList<Building>();
	ArrayList<Unit> units = new ArrayList<Unit>();
	boolean[] researches = new boolean[25];
	int ceId;

	/*
	 * reserch info[]: {Gold,Stone,Lumber,knowledge,ETA}
	 */
	static public int[][] researchInfo = { { 5, 0, 0, 0, 1 },
			{ 10, 0, 0, 0, 2 }, { 10, 0, 0, 0, 2 }, { 10, 0, 0, 0, 2 },
			{ 35, 0, 20, 10, 8 }, { 70, 10, 30, 120, 20 },
			{ 70, 30, 10, 120, 16 }, { 70, 0, 50, 120, 16 },
			{ 80, 0, 100, 50, 16 }, { 20, 0, 10, 20, 4 },
			{ 65, 15, 15, 80, 10 }, { 90, 25, 25, 140, 24 },
			{ 80, 10, 0, 130, 12 }, { 100, 0, 0, 150, 18 },
			{ 80, 0, 0, 60, 10 }, { 50, 50, 50, 50, 12 },
			{ 100, 0, 0, 120, 22 }, { 100, 0, 0, 120, 16 }, { 10, 0, 0, 0, 2 },
			{ 20, 0, 0, 25, 6 }, { 20, 0, 0, 50, 10 }, { 50, 50, 50, 120, 24 },
			{ 30, 40, 30, 20, 4 }, { 60, 80, 60, 100, 12 },
			{ 150, 150, 150, 250, 28 } };
	/*
	 * shows what are the Prerequisites of each research
	 */
	static public int[][] researchPrerequisite = { {}, { 1 }, { 1 }, { 1 },
			{ 2 }, { 2 }, { 3 }, { 4 }, { 4, 5 },
			{},// 10th
			{ 10 }, { -11, -21 }, { 12 }, { 12 }, { 10 }, { 15, 11 }, { 18 },
			{ 15 }, {}, { 19 },// 20th
			{ 20 }, { 21 }, { 19 }, { -23, -8 }, { 22, 24 },

	};

	public int ceId() {
		return ceId;
	}

	/**
	 * the constructor
	 */
	public CElization(int rowNum, int colNum, int ceId) {
		this.ceId = ceId;
		// TODO
		goldInBank = stoneInBank = lumberInBank = foodInBank = 1000;
		resourceCapacity = 200;
		for (int i = 0; i < researches.length; i += 1)
			researches[i] = false;
		// makes a map that shows discovered blocks
		isDiscovered = new boolean[rowNum][colNum];
		for (int i = 0; i < rowNum; i += 1)
			for (int j = 0; j < colNum; j += 1)
				isDiscovered[i][j] = false;

		// finds an available place for Derp --> derpRow-1 , derpCol-1
		int derpRow = 0, derpCol = 0;
		if (ceId == 1) {
			for (int found = 0; derpRow < rowNum && found == 0; derpRow += 1) {
				derpCol = 0;
				for (; derpCol < colNum && found == 0; derpCol += 1)
					if (Board.isAvailable[derpRow][derpCol] == true)
						found += 1;
			}
			derpRow -= 1;
			derpCol -= 1;
		} else {
			derpRow = rowNum - 1;
			for (int found = 0; 0 < derpRow && found == 0; derpRow -= 1) {
				derpCol = colNum - 1;
				for (; 0 < derpCol && found == 0; derpCol -= 1) {
					if (Board.isAvailable[derpRow][derpCol] == true)
						found += 1;
				}

			}
			derpRow += 1;
			derpCol += 1;
		}
		// creates the first worker at found location
		units.add(new Worker(derpRow, derpCol, isDiscovered, buildings, ceId));

	}

	private boolean isResearchDone(String cmd, int buildingType) {
		String bType = "" + buildingType;
		if (cmd.equals("build")) {
			/*
			 * checks the research from boolean[] researches
			 */
			if (bType.equals("3") || bType.equals("4"))
				return researches[2];
			else if (bType.equals("5"))
				return researches[1];
			else if (bType.equals("6"))
				return researches[3];
			else if (bType.equals("9"))
				return researches[8];
			else if (bType.equals("8"))
				return researches[15];
			else
				return true;
		} else
			return true; // TODO

	}

	private boolean hasResourceToBuild(int buildingType) {
		String type = "" + buildingType;
		int[] needed;
		if (type.equals("1"))
			needed = MainBuilding.recourcesNeeded;
		else if (type.equals("2"))
			needed = University.recourcesNeeded;
		else if (type.equals("3"))
			needed = GoldMine.recourcesNeeded;
		else if (type.equals("4"))
			needed = StoneMine.recourcesNeeded;
		else if (type.equals("5"))
			needed = Farm.recourcesNeeded;
		else if (type.equals("6"))
			needed = WoodCamp.recourcesNeeded;
		else if (type.equals("7"))
			needed = StockPile.recourcesNeeded;
		else if (type.equals("8"))
			needed = Market.recourcesNeeded;
		else if (type.equals("9"))
			needed = Port.recourcesNeeded;
		else if (type.equals("10"))
			needed = Military.recourcesNeeded;
		else if (type.equals("11"))
			needed = Stable.recourcesNeeded;
		else
			return false; // not found

		return hadResourceFor(needed);
	}

	private boolean hasSpaceToBuild(int buildingType, int row, int col) {
		String type = "" + buildingType;
		int needed;
		if (type.equals("1"))
			needed = MainBuilding.size;
		else if (type.equals("2"))
			needed = University.size;
		else if (type.equals("3"))
			needed = GoldMine.size;
		else if (type.equals("4"))
			needed = StoneMine.size;
		else if (type.equals("5"))
			needed = Farm.size;
		else if (type.equals("6"))
			needed = WoodCamp.size;
		else if (type.equals("7"))
			needed = StockPile.size;
		else if (type.equals("8"))
			needed = Market.size;
		else if (type.equals("9"))
			needed = Port.size;
		else if (type.equals("10"))
			needed = Military.size;
		else if (type.equals("11"))
			needed = Stable.size;
		else
			return false; // not found

		for (int i = 0; i < needed; i += 1)
			for (int j = 0; j < needed; j += 1)
				if (Board.isAvailable.length <= row + i
						|| Board.isAvailable[0].length <= col + j
						|| !Board.isAvailable[row + i][col + j])
					return false;
		return true;
	}

	/*
	 * worker methods
	 */
	public String workerMove(String workerId, int row, int col) {
		Worker thisWorker = findWorker(workerId);
		if (thisWorker == null || !Board.isAvailable[row][col])
			return "invalid id";
		thisWorker.moveTo(row, col);
		return "command successful";
	}

	public Worker findWorker(String workerId) {
		for (int i = 0; i < units.size(); i += 1) {
			if (units.get(i).hasId(workerId))
				return (Worker) units.get(i);
		}
		return null;// not found
	}

	public String soldierMove(String soldierId, int row, int col) {
		Soldier thisSoldier = findSoldier(soldierId);
		if (thisSoldier == null || !Board.isAvailable[row][col])
			return "invalid id";
		thisSoldier.moveTo(row, col);
		return "command successful";
	}
	public Soldier findSoldier(String solId) {
		for (int i = 0; i < units.size(); i += 1) {
			if (units.get(i).hasId(solId))
				return (Soldier) units.get(i);
		}
		return null;// not found
	}

	public Boat findBoat(String boatId) {
		for (int i = 0; i < units.size(); i += 1)
			if (units.get(i).hasId(boatId))
				return (Boat) units.get(i);
		return null;// not found
	}

	public Building findBuilding(String buildingId) {
		for (int i = 0; i < buildings.size(); i += 1)
			if (buildings.get(i).hasId(buildingId))
				return (Building) buildings.get(i);
		return null;// not found
	}

	public String soldierAttack(String soldierId, String victimId){
		Soldier sol = findSoldier(soldierId);
		if(sol == null)
			return "invalid id";
		sol.attack(victimId);
		return "command successful";
	}
	public String workerWorkAt(String workerId, String buildingId) {
		Worker thisWorker = findWorker(workerId);
		if (thisWorker == null)
			return "invalid id";
		Building thisBuilding = findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.turnsLeftToBuild != 0)
			return "invalid id";
		if (thisBuilding.isFull())
			return "workplace full";

		thisWorker.workAt(buildingId);
		return "command successful";
	}

	public String workerBuild(String workerId, int buildingType, int row,
			int col) {
		if (buildingType < 1 || 11 < buildingType)
			return "invalid id";
		if (!hasSpaceToBuild(buildingType, row, col))
			return "invalid location";
		if (!isResearchDone("build", buildingType))
			return "insufficient researches";
		if (!hasResourceToBuild(buildingType))
			return "insufficient resources";
		Worker thisWorker = findWorker(workerId);
		thisWorker.construct(buildingType, row, col);
		return "command successful";
	}

	/*
	 * editing resources: + or -
	 */
	public void addGoldInBank(int adding) {
		goldInBank += adding;
		if (resourceCapacity < goldInBank)
			goldInBank = resourceCapacity;
	}

	public void addStoneInBank(int adding) {
		stoneInBank += adding;
		if (resourceCapacity < stoneInBank)
			stoneInBank = resourceCapacity;
	}

	public void addLumberInBank(int adding) {
		lumberInBank += adding;
		if (resourceCapacity < lumberInBank)
			lumberInBank = resourceCapacity;
	}

	public void addFoodInBank(int adding) {
		foodInBank += adding;
		if (resourceCapacity < foodInBank)
			foodInBank = resourceCapacity;
	}

	public void addKnowledge(int adding) {
		knowledge += adding;
	}

	public void addResearch(int research) {
		researches[research - 1] = true;
	}

	/*
	 * cheat for recourses
	 */
	public void setGoldInBank(int adding) {
		goldInBank = adding;
		if (resourceCapacity < goldInBank)
			goldInBank = resourceCapacity;
	}

	public void setStoneInBank(int adding) {
		stoneInBank = adding;
		if (resourceCapacity < stoneInBank)
			stoneInBank = resourceCapacity;
	}

	public void setLumberInBank(int adding) {
		lumberInBank = adding;
		if (resourceCapacity < lumberInBank)
			lumberInBank = resourceCapacity;
	}

	public void setFoodInBank(int adding) {
		foodInBank = adding;
		if (resourceCapacity < foodInBank)
			foodInBank = resourceCapacity;
	}

	public void setKnowledge(int adding) {
		knowledge = adding;
	}

	// *********capacity
	public void addResourceCapacity(int adding) {
		resourceCapacity += adding;
	}

	public String exchange(int amount, String from, String to) {
		if (from.equals("gold")) {
			if (goldInBank < amount)
				return "insufficient resources";
			goldInBank -= amount;
		} else if (from.equals("stone")) {
			if (stoneInBank < amount)
				return "insufficient resources";
			stoneInBank -= amount;
		} else if (from.equals("lumber")) {
			if (lumberInBank < amount)
				return "insufficient resources";
			lumberInBank -= amount;
		} else if (from.equals("food")) {
			if (foodInBank < amount)
				return "insufficient resources";
			foodInBank -= amount;
		}
		if (to.equals("gold")) {
			addGoldInBank(amount);
		} else if (to.equals("stone")) {
			addStoneInBank(amount);
		} else if (to.equals("lumber")) {
			addLumberInBank(amount);
		} else if (to.equals("food")) {
			addFoodInBank(amount);
		}
		return "command successful";
	}

	// ********** has
	public boolean hasGoldInBank(int amount) {
		if (amount <= goldInBank)
			return true;
		return false;
	}

	public boolean hasStoneInBank(int amount) {
		if (amount <= stoneInBank)
			return true;
		return false;
	}

	public boolean hasLumberInBank(int amount) {
		if (amount <= lumberInBank)
			return true;
		return false;
	}

	public boolean hasFoodInBank(int amount) {
		if (amount <= foodInBank)
			return true;
		return false;
	}

	// 888888888888888888765467890987654er56789765456789iuygfvgh

	public boolean hadResourceFor(int[] resourses) {
		if (goldInBank >= resourses[0] && stoneInBank >= resourses[1]
				&& lumberInBank >= resourses[2])
			return true;
		return false;
	}

	public void takeFromBank(int[] resourses) {
		goldInBank -= resourses[0];
		stoneInBank -= resourses[1];
		lumberInBank -= resourses[2];
	}

	/*
	 * Building methods
	 */
	// Work class calls:
	public void addWorkerInfront(String buildingId) {
		Building thisBuilding = findBuilding(buildingId);
		int location[] = Worker.outOfBuilding(thisBuilding);
		units.add(new Worker(location[0], location[1], isDiscovered, buildings,
				ceId));
	}

	public void addAxerInfront(String buildingId) {
		Building thisBuilding = findBuilding(buildingId);
		int location[] = Worker.outOfBuilding(thisBuilding);
		units.add(new Axer(location[0], location[1], isDiscovered, ceId));
	}

	public void addDarterInfront(String buildingId) {
		Building thisBuilding = findBuilding(buildingId);
		int location[] = Worker.outOfBuilding(thisBuilding);
		units.add(new Darter(location[0], location[1], isDiscovered, ceId));
	}

	public void addNiggaInfront(String buildingId) {
		Building thisBuilding = findBuilding(buildingId);
		int location[] = Worker.outOfBuilding(thisBuilding);
		units.add(new NiggaRider(location[0], location[1], isDiscovered, ceId));
	}

	public void addCluberInfront(String buildingId) {
		Building thisBuilding = findBuilding(buildingId);
		int location[] = Worker.outOfBuilding(thisBuilding);
		units.add(new CluberRider(location[0], location[1], isDiscovered, ceId));
	}

	// Boat class calls:
	public void addBoatInfront(String buildingId) {
		Port port = (Port) findBuilding(buildingId);
		int location[] = Port.findCoast(port);
		units.add(new Boat(location[0], location[1], isDiscovered, ceId));
	}

	/*
	 * Board class calls
	 */
	public String trainWorkerIn(String buildingId) {
		MainBuilding thisBuilding = (MainBuilding) findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.isBusy())
			return "building is busy";
		if (goldInBank < 10)
			return "insufficient resources";
		goldInBank -= 10;
		thisBuilding.train();
		return "command successful";
	}

	public String trainAxerIn(String buildingId) {
		Military thisBuilding = (Military) findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.isBusy())
			return "building is busy";
		if (goldInBank < 30 || lumberInBank<80)
			return "insufficient resources";
		goldInBank -= 30;
		lumberInBank -= 80;
		thisBuilding.trainAxer();
		return "command successful";
	}

	public String trainDarterIn(String buildingId) {
		Military thisBuilding = (Military) findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.isBusy())
			return "building is busy";
		if (goldInBank < 30 || lumberInBank<50)
			return "insufficient resources";
		goldInBank -= 30;
		lumberInBank -= 50;
		thisBuilding.trainDarter();
		return "command successful";
	}

	public String trainNiggaIn(String buildingId) {
		Stable thisBuilding = (Stable) findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.isBusy())
			return "building is busy";
		if (goldInBank < 50 || lumberInBank < 80)
			return "insufficient resources";
		goldInBank -= 50;
		lumberInBank -= 80;
		thisBuilding.trainNigga();
		return "command successful";
	}

	public String trainCluberIn(String buildingId) {
		Stable thisBuilding = (Stable) findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.isBusy())
			return "building is busy";
		if (goldInBank < 60 || lumberInBank < 100)
			return "insufficient resources";
		goldInBank -= 60;
		lumberInBank -= 100;
		thisBuilding.trainCluber();
		return "command successful";
	}

	// end of training :D

	public String trainBoatIn(String buildingId) {
		Port thisBuilding = (Port) findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.isBusy())
			return "building is busy";
		if (goldInBank < 20 || lumberInBank < 80)
			return "insufficient resources";
		goldInBank -= 20;
		lumberInBank -= 80;
		thisBuilding.train();
		return "command successful";
	}

	public String trainScholarIn(String buildingId) {
		University thisBuilding = (University) findBuilding(buildingId);
		if (thisBuilding == null)
			return "invalid id";
		if (thisBuilding.isFull())
			return "university full";
		if (thisBuilding.isBusy())
			return "building busy";
		if (goldInBank < 40)
			return "insufficient resources";
		goldInBank -= 40;
		thisBuilding.train();
		return "command successful";
	}

	public String doResearch(String scholarId, int research) {
		if (!hasPrerequisite(research))
			return "insufficient researches";
		if (!hasResources(research))
			return "insufficient resources";
		University thisUni = (University) findBuilding(scholarId.split("-")[1]);
		if (thisUni == null)
			return "invalid id";
		if (!thisUni.hasScholar(scholarId))
			return "invalid id";
		if (thisUni.isBusyForResearch())
			return "building busy";
		thisUni.research(research);
		return "command successful";
	}

	public String doResearchUni(String uniId, int research) {
		if (!hasPrerequisite(research))
			return "failed: insufficient researches";
		if (!hasResources(research))
			return "failed: insufficient resources";
		University thisUni = (University) findBuilding(uniId);
		if (thisUni == null)
			return "failed: invalid id";
		if (thisUni.isBusyForResearch())
			return "failed: building busy";
		if (thisUni.scholarsInside == 0)
			return "failed: no scholars inside!";
		thisUni.research(research);
		return "command successful";
	}

	private boolean hasPrerequisite(int research) {
		int[] prerequisites = researchPrerequisite[research - 1];
		for (int prereq : prerequisites) {
			if (0 < prereq) {
				if (!researches[prereq - 1])
					return false;
			} else if (researches[(-1 * prereq) - 1])
				return true;
		}
		return true;
	}

	private boolean hasResources(int researsh) {
		if (researchInfo[researsh][0] <= goldInBank
				&& researchInfo[researsh][1] <= stoneInBank
				&& researchInfo[researsh][2] <= lumberInBank
				&& researchInfo[researsh][3] <= knowledge)
			return true;
		return false;
	}

	public String sell(String buildingId) {
		// TODO
		return "command successful";
	}

	public void update() {
		for (Unit unit : units)
			unit.update();
		for (Building building : buildings)
			building.update();
		// TODO people dieing and more
		// this method is not beeing used yet
	}

	/*
	 * generates a list of buildings that a worker can make only for info method
	 * in judge
	 */
	public String buildingsAllowed() {
		String ret = "";
		for (int i = 0; i < 11; i += 1)
			if (isResearchDone("build", i + 1) && hasResourceToBuild(i + 1))
				ret += (" " + (i + 1));
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);
	}

	public String buildingsAllowedName() {
		String ret = "";
		for (int i = 0; i < 11; i += 1)
			if (isResearchDone("build", i + 1) && hasResourceToBuild(i + 1))
				ret += (" " + buildingName(i + 1));
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);
	}

	public String buildingName(int i) {
		if (i == 1)
			return "MainBuilding";
		else if (i == 2)
			return "University";
		else if (i == 3)
			return "GoldMine";
		else if (i == 4)
			return "StoneMine";
		else if (i == 5)
			return "Farm";
		else if (i == 6)
			return "WoodCamp";
		else if (i == 7)
			return "StockPile";
		else if (i == 8)
			return "Market";
		else if (i == 9)
			return "Port";
		else if (i == 10)
			return "Military";
		return "Stable";
	}

	public int buildingType(String name) {
		if (name.equals("MainBuilding"))
			return 1;
		else if (name.equals("University"))
			return 2;
		else if (name.equals("GoldMine"))
			return 3;
		else if (name.equals("StoneMine"))
			return 4;
		else if (name.equals("Farm"))
			return 5;
		else if (name.equals("WoodCamp"))
			return 6;
		else if (name.equals("StockPile"))
			return 7;
		else if (name.equals("Market"))
			return 8;
		else if (name.equals("Port"))
			return 9;
		else if (name.equals("Military"))
			return 10;
		else
			return 11;
	}

	public String availableResearches() {
		String ret = "";
		for (int i = 0; i < 25; i += 1)
			if (hasPrerequisite(i + 1) && hasResources(i + 1) && !researches[i])
				ret += (" " + (i + 1));
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);

	}

	/*
	 * info methods
	 */
	public String goldInfo() {
		return goldInBank + "/" + resourceCapacity;
	}

	public String stoneInfo() {
		return stoneInBank + "/" + resourceCapacity;
	}

	public String lumberInfo() {
		return lumberInBank + "/" + resourceCapacity;
	}

	public String foodInfo() {
		return foodInBank + "/" + resourceCapacity;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public String buildingsGenerator() {
		String ret = "";
		for (Building building : buildings)
			ret += " " + building.getId();
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);
	}

	public String workersGenerator() {
		String ret = "";
		for (Unit unit : units)
			if (unit instanceof Worker)
				ret += " " + unit.getId();
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);
	}

	public String scholarsGenerator() {
		String ret = "";
		for (Building building : buildings)
			if (building instanceof University) {
				University uni = (University) building;
				ret += " " + uni.scholarsIdGenerator();
			}
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);
	}

	public String boatsGenerator() {
		String ret = "";
		for (Unit unit : units)
			if (unit instanceof Boat)
				ret += " " + unit.getId();
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);
	}

	public String blockTypeInfo(int row, int col) {
		if (isDiscovered[row][col]) {
			if (Board.map[row][col].type().equals(BlockType.Plain))
				return "P";
			if (Board.map[row][col].type().equals(BlockType.Forest))
				return "F";
			if (Board.map[row][col].type().equals(BlockType.Mountain))
				return "M";
			if (Board.map[row][col].type().equals(BlockType.Water))
				return "W";
		}
		return "?";
	}

	public String seeGoldInfo(int row, int col) {
		if (researches[0] && isDiscovered[row][col])
			return "" + Board.map[row][col].goldNum();
		return "?";
	}

	public String seeStoneInfo(int row, int col) {
		if (researches[0] && isDiscovered[row][col])
			return "" + Board.map[row][col].stoneNum();
		return "?";
	}

	public String seeLumberInfo(int row, int col) {
		if (researches[0] && isDiscovered[row][col])
			return "" + Board.map[row][col].lumberNum();
		return "?";
	}

	public String seeFoodInfo(int row, int col) {
		if (researches[0] && isDiscovered[row][col])
			return "" + Board.map[row][col].foodNum();
		return "?";
	}

	/*
	 * for the cosole.window:
	 */
	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	public boolean[][] getIsDiscovered() {
		return isDiscovered;
	}

	/*
	 * next turn for updating and for judge nextTurn mehthod
	 */
	public String[] nextTurn() {
		ArrayList<String> ret = new ArrayList<String>();
		for (Unit unit : units) {
			unit.update();
			if (unit instanceof Worker) {
				Worker thisWorker = (Worker) unit;
				ret.addAll(thisWorker.updateWithReturn());
			} else if(unit instanceof Soldier){
				Soldier sol = (Soldier) unit;
				ret.addAll(sol.updateWithReturn());
				
			}
		}

		for (Building building : buildings) {
			building.update();
			if (building instanceof University) {
				University uni = (University) building;
				ret.addAll(uni.updateWithReturn());
			} else if (building instanceof MainBuilding) {
				MainBuilding main = (MainBuilding) building;
				ret.addAll(main.updateWithReturn());
			} else if (building instanceof Port) {
				Port port = (Port) building;
				ret.addAll(port.updateWithReturn());
			} else if(building instanceof Military){
				Military mili = (Military) building;
				ret.addAll(mili.updateWithReturn());
			}else if(building instanceof Stable){
				Stable stab = (Stable) building;
				ret.addAll(stab.updateWithReturn());
			}
		}
		ret.addAll(takeFoodWithReturn());

		ArrayList<String> retNull = new ArrayList<String>();
		retNull.add(null);
		ret.removeAll(retNull);

		Object[] ObjectList = ret.toArray();
		String[] StringArray = Arrays.copyOf(ObjectList, ObjectList.length,
				String[].class);
		return StringArray;
	}

	private ArrayList<String> takeFoodWithReturn() {
		ArrayList<String> ret = new ArrayList<String>();
		int kill = 0;
		if (units.size() != 0)
			for (Unit unit : units)
				if (unit instanceof Worker)
					if (!takeFood(2))
						kill += 1;
		if (buildings.size() != 0)
			for (Building building : buildings)
				if (building instanceof University) {
					University uni = (University) building;
					for (int i = 0; i < uni.getScholarIds().size(); i += 1)
						if (!takeFood(1))
							kill += 1;
				}
		ret.add(kill(kill));

		return ret;
	}

	private boolean takeFood(int amount) {

		if (amount <= foodInBank) {
			foodInBank -= amount;
			return true;
		}
		return false;
	}

	private String kill(int times) {

		while (0 < times--) {
			for (Building building : buildings)
				if (building instanceof University) {
					University uni = (University) building;
					if (0 < uni.getScholarIds().size()) {
						String id = uni.getScholarIds().get(0);
						uni.getScholarIds().remove(0);
						uni.scholarsInside -= 1;
						return "scholar died " + id;
					}

				}
			for (int i = 0; i < units.size(); i += 1)
				if (units.get(i) instanceof Worker) {
					Worker thisWorker = (Worker) units.get(i);
					String id = thisWorker.getId();
					thisWorker.kill();
					units.remove(i);
					return "worker died " + id;
				}
		}
		return null;
	}

	public void killUnit(String id) {
		for (int i = 0; i < units.size(); i += 1)
			if (units.get(i).getId().equals(id)) {
				Unit thisWorker = units.get(i);
				// String id = thisWorker.getId();
				thisWorker.kill();
				units.remove(i);
				// return "worker died " + id;
			}
	}

	public void destroyBuilding(String id) {
		for (int i = 0; i < buildings.size(); i += 1)
			if (buildings.get(i).getId().equals(id)) {
				Building thisBuilding = buildings.get(i);
				// String id = thisWorker.getId();
				thisBuilding.destroy();
				buildings.remove(i);
				// return "worker died " + id;
			}

	}

	/*
	 * cheats
	 */
	public String cheatMakeWorkerAt(int row, int col) {
		Worker thisGuy = new Worker(row, col, isDiscovered, buildings, ceId);
		units.add(thisGuy);
		return thisGuy.getId();

	}

	public void teleportWorker(String workerId, int row, int col) {
		Worker thisGuy = findWorker(workerId);
		thisGuy.teleport(row, col);
	}

	public String cheatMakeScholar() {
		University uni = null;
		for (Building building : buildings)
			if (building instanceof University)
				uni = (University) building;
		return uni.cheatTrainScholar();
	}

	public String cheatMakeBuildingAt(String buildingType, int row, int col) {
		Building workingAt;
		if (buildingType.equals("1"))
			workingAt = new MainBuilding(row, col, ceId);
		else if (buildingType.equals("2"))
			workingAt = new University(row, col, ceId);
		else if (buildingType.equals("3"))
			workingAt = new GoldMine(row, col, ceId);
		else if (buildingType.equals("4"))
			workingAt = new StoneMine(row, col, ceId);
		else if (buildingType.equals("5"))
			workingAt = new Farm(row, col, ceId);
		else if (buildingType.equals("6"))
			workingAt = new WoodCamp(row, col, ceId);
		else if (buildingType.equals("7"))
			workingAt = new StockPile(row, col, ceId);
		else if (buildingType.equals("8"))
			workingAt = new Market(row, col, ceId);
		else if (buildingType.equals("9"))
			workingAt = new Port(row, col, ceId);
		else
			workingAt = null;
		buildings.add(workingAt);
		workingAt.turnsLeftToBuild = 0;
		return workingAt.getId();

	}

	public void cheatExplore(int row, int col) {
		isDiscovered[row][col] = true;
	}

	public void cheatExploreAll() {
		for (int i = 0; i < isDiscovered.length; i += 1)
			for (int j = 0; j < isDiscovered[0].length; j += 1)
				isDiscovered[i][j] = true;
	}

}
