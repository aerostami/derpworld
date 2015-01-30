package units;

import game.Board;

import java.util.ArrayList;

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

public class Worker extends Unit {
	public static int workersNum;
	ArrayList<Building> buildings;
	private int constExpNum = 1;
	public String mainOccupation = "idle";
	private int[] inventory = new int[4];// p[0]:gold,
											// p[1]:stone,p[2]:food,p[3]:lumber
	private int[] expPeriod = new int[5];// order in inventory+ p[5]:building
	
	public int[] getExpPeriod() {
		return expPeriod;
	}

	private int capacity = 30;
	private Building workingAt;
	private ArrayList<String> toDoList = new ArrayList<String>();

	String infoOccupation = "idle";// for judge

	/*
	 * constructor that creates a new worker and updates the map
	 */
	public Worker(int row, int col, boolean[][] isDiscovered,
			ArrayList<Building> buildings,int ceId) {
		super(row, col, isDiscovered,ceId);
		this.buildings = buildings;
		workersNum += 1;
		if (workersNum == 1)
			id = "derp";
		else
			id = "worker_" + (workersNum - 1);
		id+="_"+ceId;
		Board.map[row][col].setObjOnBlock(id);
		occupation = "idle";
		teleport(row, col);

	}

	public ArrayList<String> getToDoList() {
		return toDoList;
	}

	

	/*
	 * @see units.Unit#update() .
	 */
	public void update() {

	}

	public ArrayList<String> updateWithReturn() {
		ArrayList<String> ret = new ArrayList<String>();
		if (toDoList.size() == 0)
			return ret;
		String[] toDo = toDoList.get(0).split(" ");

		if (toDo[0].equals("move")) {
			String direction = Board.findPath(row, col, toDo[1], toDo[2]);
			freeNode();
			if (direction.equals("up"))
				row -= 1;
			else if (direction.equals("down"))
				row += 1;
			else if (direction.equals("left"))
				col -= 1;
			else if (direction.equals("right"))
				col += 1;
			else {
				occupation = "idle";
				infoOccupation = "idle";
				toDoList.clear();
			}
			if (("" + row).equals(toDo[1]) && ("" + col).equals(toDo[2])) {
				occupation = "idle";
				infoOccupation = "idle";
				toDoList.remove(0);
			}
			Board.map[row][col].setObjOnBlock(id);
			Board.isAvailable[row][col] = false;
			exploreBlocks();

		} else if (toDo[0].equals("construct")) {
			if (toDo[1].equals("1"))
				workingAt = new MainBuilding(row, col,ceId);
			else if (toDo[1].equals("2"))
				workingAt = new University(row, col,ceId);
			else if (toDo[1].equals("3"))
				workingAt = new GoldMine(row, col,ceId);
			else if (toDo[1].equals("4"))
				workingAt = new StoneMine(row, col,ceId);
			else if (toDo[1].equals("5"))
				workingAt = new Farm(row, col,ceId);
			else if (toDo[1].equals("6"))
				workingAt = new WoodCamp(row, col,ceId);
			else if (toDo[1].equals("7"))
				workingAt = new StockPile(row, col,ceId);
			else if (toDo[1].equals("8"))
				workingAt = new Market(row, col,ceId);
			else if (toDo[1].equals("9"))
				workingAt = new Port(row, col,ceId);
			else if (toDo[1].equals("10"))
				workingAt = new Military(row, col,ceId);
			else if (toDo[1].equals("11"))
				workingAt = new Stable(row, col,ceId);
			if (Board.derpCElization
					.hadResourceFor(workingAt.recourcesNeeded())) {
				Board.derpCElization.takeFromBank(workingAt.recourcesNeeded());
				buildings.add(workingAt);
				toDoList.set(0, "constructing");
				workingAt.addWorker();
				workingAt.turnsLeftToBuild -= 1;
				occupation = "constructing";
				infoOccupation = "constructing " + workingAt.getId();
				ret.add("construction started " + workingAt.getId());

			} else
				toDoList.remove(0);

		} else if (toDo[0].equals("constructing")) {
			workingAt.turnsLeftToBuild -= 1;
			/*
			 * BUG!!! the building can be used before being ready!!! Future me
			 * should solve this problem!
			 */
			if (workingAt.turnsLeftToBuild <= 0) {
				toDoList.remove(0);
				int[] target = outOfBuilding(workingAt);
				teleport(target[0], target[1]);
				workingAt.removeWorker();
				occupation = "idle";
				infoOccupation = "idle";
				ret.add("construction finished " + workingAt.getId());
				workingAt = null;
			}

		} else if (toDo[0].equals("workAt")) {
			/*
			 * become invisible in graphics mode
			 */
			toDoList.set(0, "workingAt " + toDo[1]);
			workingAt = getBuilding(toDo[1]);
			workingAt.addWorker();
			occupation = "working";
			infoOccupation = makeWorkingOccupation(workingAt);
			freeNode();
			// TODO bug ;)

		} else if (toDo[0].equals("workingAt")) {
			if (isStorageFull()) {
				toDoList.add(0, "move " + row + " " + col);// the last task
				toDoList.add(0, "deliver");
				int[] nearestStockPile = findNearestStockPile();
				int tRow = nearestStockPile[0], tCol = nearestStockPile[1];
				toDoList.add(0, "move " + tRow + " " + tCol);// the first task
			} else {

				/*
				 * i made a connection between building Types and inventory:
				 * inventory[i] ==> i=buildingType-3
				 */
				int buildingType = Integer.parseInt(toDo[1].split("_")[1]);
				double exp =1;
				if(3<buildingType)
				exp = (expPeriod[buildingType - 3] % 5) * 0.05;
				int gainedResource = (int) (constExpNum * (exp + 1) * workingAt
						.getAvgRecourceUnder());
				if(3<buildingType){
				inventory[buildingType - 3] += gainedResource;
				expPeriod[buildingType - 3] += 1;
				}
				/*
				 * makes sure the inventory is not more than capacity
				 */
				if (isStorageFull()) {
					toDoList.add(0, "move " + row + " " + col);// the last task
																// is to get
																// back
					toDoList.add(0, "deliver");// add recourses to bank
					int[] nearestStockPile = findNearestStockPile();
					int tRow = nearestStockPile[0], tCol = nearestStockPile[1];
					toDoList.add(0, "move " + tRow + " " + tCol);// the first
																	// task is
																	// to go to
																	// a pile
					while (isStorageFull())
						inventory[buildingType - 3] -= 1;
					inventory[buildingType - 3] += 1;
				}
			}
			/*
			 * end of working process, but the cycle repeats...
			 */

		} else if (toDo[0].equals("deliver")) {
			Board.derpCElization.addGoldInBank(inventory[0]);
			Board.derpCElization.addStoneInBank(inventory[1]);
			Board.derpCElization.addFoodInBank(inventory[2]);
			Board.derpCElization.addLumberInBank(inventory[3]);
			inventory[0] = inventory[1] = inventory[2] = inventory[3] = 0;
			toDoList.remove(0);

		} else if (toDo[0].equals("stopWorking")) {
			workingAt.removeWorker();
			teleport(Integer.parseInt(toDo[1]), Integer.parseInt(toDo[2]));
			toDoList.remove(0);
			workingAt = null;
			occupation = "idle";
			infoOccupation = "idle";
		}
		return ret;
	}// end of update()

	private String makeWorkingOccupation(Building bil) {
		String ret = "working at ";
		String bilType;
		if (bil instanceof GoldMine)
			bilType = "gold mine ";
		else if (bil instanceof StoneMine)
			bilType = "stone mine ";
		else if (bil instanceof WoodCamp)
			bilType = "wood camp ";
		else
			bilType = "farm ";
		return ret + bilType + bil.getId();
	}

	private int[] findNearestStockPile() {
		return outOfBuilding(buildings.get(0));
	}

	private boolean isStorageFull() {
		int sum = inventory[0] + inventory[1] + inventory[2] + inventory[3];
		return sum >= capacity;
	}

	/*
	 * returns row and col out of a building
	 */
	static public int[] outOfBuilding(Building workingInside) {
		int size = workingInside.getSize();
		int bRow = workingInside.getLocation()[0];
		int bCol = workingInside.getLocation()[1];
		int inRow = bRow, inCol = bCol;// the location of building block near
										// outside
		int trow = -1, tcol = -1;
		if (0 < bRow && Board.isAvailable[bRow - 1][bCol] == true) {
			trow = bRow - 1;
			tcol = bCol;
		} else if (0 < bCol && Board.isAvailable[bRow][bCol - 1] == true) {
			trow = bRow;
			tcol = bCol - 1;
		} else if (bRow + workingInside.getSize() < Board.isAvailable.length
				&& Board.isAvailable[bRow + size][bCol] == true) {
			trow = bRow + size;
			tcol = bCol;
			inRow += workingInside.getSize() - 1;
			inCol += workingInside.getSize() - 1;
		} else if (bCol + workingInside.getSize() < Board.isAvailable[0].length
				&& Board.isAvailable[bRow][bCol + size] == true) {
			trow = bRow;
			tcol = bCol + size;
			inRow += workingInside.getSize() - 1;
			inCol += workingInside.getSize() - 1;
		} else if (bRow + workingInside.getSize() - 1 < Board.isAvailable.length
				&& 0 < bCol - 1) {
			trow = bRow + size-1;
			tcol = bCol-1;
			inRow += workingInside.getSize() - 1;
			inCol += workingInside.getSize() - 1;
		}
		int[] ret = { trow, tcol, inRow, inCol };
		return ret;
	}

	// ---------------------------------------

	

	

	// ***

	/*
	 * it takes the upper left location of building and builds it
	 */
	public void construct(int buildingType, int dRow, int dCol) {
		clearToDoList();
		occupation = "constructing";
		infoOccupation = "moving to " + dRow + " " + dCol;
		toDoList.add("move " + dRow + " " + dCol);
		toDoList.add("construct " + buildingType);
		// TODO
	}

	public void moveTo(int row, int col) {
		// becomIdle(); TODO
		clearToDoList();
		occupation = "moving";
		infoOccupation = "moving to " + row + " " + col;
		toDoList.add("move " + row + " " + col);
	}

	public void clearToDoList() {
		infoOccupation = "idle";
		occupation = "idle";
		if (toDoList.size() != 0
				&& (toDoList.get(0).split(" ")[0].equals("constructing") || toDoList
						.get(0).split(" ")[0].equals("workingAt"))) {
			toDoList.clear();
			int[] target = outOfBuilding(workingAt);
			toDoList.add("stopWorking " + target[0] + " " + target[1]);
		} else
			toDoList.clear();
	}

	public void workAt(String buildingId) {
		clearToDoList();
		occupation = "working";
		Building targetBuilding = getBuilding(buildingId);
		int[] target = outOfBuilding(targetBuilding);
		int dRow = target[0];
		int dCol = target[1];
		int insideRow = target[2];// these are not needed now
		int insideCol = target[3];//
		infoOccupation = "moving to " + dRow + " " + dCol;
		toDoList.add("move " + dRow + " " + dCol);
		toDoList.add("workAt " + buildingId +" "+ insideRow + " " + insideCol);
	}

	private Building getBuilding(String buildingId) {
		Building found = null;
		for (int i = 0; i < buildings.size(); i += 1)
			if (buildings.get(i).hasId(buildingId))
				found = buildings.get(i);
		return found;
	}

	public String getRealOccupation() {
		return occupation;
	}

	/*
	 * for judge info method because my inventory has different values, i use
	 * the max value when asked inventory returns : {max, capacity, type}
	 */
	public String[] inventory() {
		String type;
		int maxIndex = -1;
		int max = -1;
		for (int i = 0; i < inventory.length; i += 1)
			if (max < inventory[i]) {
				max = inventory[i];
				maxIndex = i;
			}
		if (maxIndex == 0)
			type = "gold";
		else if (maxIndex == 1)
			type = "stone";
		else if (maxIndex == 2)
			type = "food";
		else
			type = "lumber";
		String[] ret = { "" + max, "" + capacity, type };
		return ret;
	}

	/*
	 * for judge info method
	 */
	public String getOccupation() {
		return infoOccupation;
	}
}
