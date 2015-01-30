package soldier;

import game.Board;

import java.util.ArrayList;

import objects.Obj;
import units.Unit;

public abstract class Soldier extends Unit {
	protected int health;

	public int getHealth() {
		return health;
	}

	public int getPointOnInfantry() {
		return pointOnInfantry;
	}

	public int getPointOnHorseman() {
		return pointOnHorseman;
	}

	public int getPointOnDefence() {
		return pointOnDefence;
	}

	public int getPointOnAttack() {
		return pointOnAttack;
	}

	protected int pointOnInfantry;
	protected int pointOnHorseman;
	protected int pointOnDefence;
	protected int pointOnAttack;

	private ArrayList<String> toDoList = new ArrayList<String>();

	public ArrayList<String> getToDoList() {
		return toDoList;
	}

	protected Soldier(int row, int col, boolean[][] isDiscovered, int ceId,
			int health, int pointOnInfantry, int pointOnHorseman,
			int pointOnAttack, int pointOnDefence) {
		super(row, col, isDiscovered, ceId);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public void kill() {
		Board.map[row][col].freeBlock();
		freeNode();
		occupation = "dead";
		// TODO
	}

	public void clearToDoList() {
		occupation = "idle";
		// infoOccupation = "idle";
		// if (toDoList.size() != 0
		// && (toDoList.get(0).split(" ")[0].equals("constructing") || toDoList
		// .get(0).split(" ")[0].equals("workingAt"))) {
		// toDoList.clear();
		// int[] target = outOfBuilding(workingAt);
		// toDoList.add("stopWorking " + target[0] + " " + target[1]);
		// } else
		toDoList.clear();
	}

	public void moveTo(int row, int col) {
		// becomIdle(); TODO
		clearToDoList();
		occupation = "moving";
		// infoOccupation = "moving to " + row + " " + col;
		toDoList.add("move " + row + " " + col);
	}

	public String occupation() {
		return occupation;
	}

	public void attack(String victimId) {
		clearToDoList();
		toDoList.add("moveNkill " + victimId);
		occupation = "attacking";
		// Obj o = Board.findVictimObj(victimId);
		// if (o instanceof Building){
		// toDoList.add("moveNkill " + o.getRow() + " " + o.getCol());
		// toDoList.add("attack " + buildingType);
		// }else if (o instanceof Worker){
		//
		// } else if (o instanceof Soldier){
		//
		// }
		// infoOccupation = "moving to " + dRow + " " + dCol;

		// toDoList.add("move " + dRow + " " + dCol);
		// toDoList.add("construct " + buildingType);
		// TODO
	}

	private boolean isNearTarget(Obj victim) {
		if ((victim.getCol() == col && (row == victim.getRow() - 1 || row == victim
				.getRow() + 1))
				|| (victim.getRow() == row && (col == victim.getCol() - 1 || row == victim
						.getCol() + 1)))
			return true;
		return false;

	}

	private int targetRow(String victimId) {
		Obj o = Board.findVictimObj(victimId);
		return o.getRow();
	}

	private int targetCol(String victimId) {
		Obj o = Board.findVictimObj(victimId);
		return o.getCol();
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
				// infoOccupation = "idle";
				toDoList.clear();
			}
			if (("" + row).equals(toDo[1]) && ("" + col).equals(toDo[2])) {
				occupation = "idle";
				// infoOccupation = "idle";
				toDoList.remove(0);
			}
			Board.map[row][col].setObjOnBlock(id);
			Board.isAvailable[row][col] = false;
			exploreBlocks();

		} else if (toDo[0].equals("moveNkill")) {
			Obj o = Board.findVictimObj(toDo[1]);
			if (o == null) {
				toDoList.clear();
				occupation = "idle";
			} else if (isNearTarget(o)) {
				freeNode();
				occupation = "idle";
				toDoList.clear();
				row = targetRow(toDo[1]);
				col = targetCol(toDo[1]);
				if (judge.Judge.isBuilding(toDo[1])) {

					Board.herpCElization.destroyBuilding(toDo[1]);
					Board.map[row][col].setObjOnBlock(id);
					Board.isAvailable[row][col] = false;
					exploreBlocks();

				} else if (judge.Judge.isWorker(toDo[1])) {
					Board.herpCElization.killUnit(toDo[1]);
					Board.map[row][col].setObjOnBlock(id);
					Board.isAvailable[row][col] = false;
					exploreBlocks();

				} else if (judge.Judge.isSoldier(toDo[1])) {
					if (Board.isWinner(id, toDo[1], "attack")) {
						Board.herpCElization.killUnit(toDo[1]);
						Board.map[row][col].setObjOnBlock(id);
						Board.isAvailable[row][col] = false;
						exploreBlocks();
					} else {
						Board.derpCElization.killUnit(id);
					}

				}
			} else {
				String direction = Board.findPathToKill(row, col, o.getRow(),
						o.getCol());
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
					// infoOccupation = "idle";
					toDoList.clear();

				}
				Board.map[row][col].setObjOnBlock(id);
				Board.isAvailable[row][col] = false;
				exploreBlocks();

			}
			// TODO
		} else if (toDo[0].equals("stopWorking")) {
			// workingAt.removeWorker();
			teleport(Integer.parseInt(toDo[1]), Integer.parseInt(toDo[2]));
			toDoList.remove(0);
			// workingAt = null;
			occupation = "idle";
			// infoOccupation = "idle";
		}
		return ret;
	}// end of update()

}
