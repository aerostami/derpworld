package game;

import java.util.LinkedList;

import judge.HerpJudge;
import objects.Obj;
import soldier.Axer;
import soldier.Darter;
import soldier.Soldier;
import buildings.Market;
import conect.Connect;

public class Board {

	public static Block[][] map;
	public static boolean isAvailable[][];
	public static int time;
	public static CElization derpCElization;
	public static CElization herpCElization;
	/*
	 * the mode can be free, AI, and LAN
	 */
	public String mode;

	public Board(Block[][] map, boolean[][] available, String mode) {
		this.mode = mode;
		Board.map = map;
		Board.isAvailable = available;
		time = 0;
		if (mode.equals("AI")) {
			derpCElization = new CElization(map.length, map[0].length, 1);
			herpCElization = new CElization(map.length, map[0].length, 2);
		} else if (mode.equals("server")) {
			derpCElization = new CElization(map.length, map[0].length, 1);
			herpCElization = new CElization(map.length, map[0].length, 2);
			// TODO
		} else if (mode.equals("client")) {
			herpCElization = new CElization(map.length, map[0].length, 1);
			derpCElization = new CElization(map.length, map[0].length, 2);
			// TODO
		} else {
			// free mode
			derpCElization = new CElization(map.length, map[0].length, 1);
		}
	}

	/*
	 * do i win against my apponent?
	 */
	public static boolean isWinner(String myId, String hisId, String myMode) {
		int myA, hisA, myB, hisB;
		Soldier me = (Soldier) findVictimObj(myId);
		Soldier him = (Soldier) findVictimObj(hisId);

		if (myMode.equals("defence")) {
			myB = me.getPointOnDefence();
			hisB = him.getPointOnAttack();
		} else {
			myB = me.getPointOnAttack();
			hisB = me.getPointOnDefence();
		}
		if (me instanceof Axer || me instanceof Darter)
			hisA = him.getPointOnInfantry();
		else
			hisA = him.getPointOnHorseman();
		if (him instanceof Axer || him instanceof Darter)
			myA = me.getPointOnInfantry();
		else
			myA = me.getPointOnHorseman();
		return (hisA + hisB) < (myA + myB);

	}

	public void killEnemy(String id) {
		// TODO
	}

	public static Obj findVictimObj(String id) {
		String[] temp = id.split("_");
		int objCeId;
		try {
			objCeId = Integer.parseInt(temp[temp.length - 1]);
		} catch (Exception e) {
			return null;
		}
		CElization ce = null;

		if (objCeId == derpCElization.ceId)
			ce = derpCElization;
		else if (objCeId == herpCElization.ceId)
			ce = herpCElization;
		Obj o = null;
		if (judge.Judge.isBuilding(id))
			o = ce.findBuilding(id);
		else if (judge.Judge.isWorker(id))
			o = ce.findWorker(id);
		else if (judge.Judge.isSoldier(id))
			o = ce.findSoldier(id);

		return o;
	}

	// WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	public String workerMove(String workerId, int row, int col) {
		return derpCElization.workerMove(workerId, row - 1, col - 1);

	}

	public String soldierMoveH(String workerId, int row, int col) {
		return herpCElization.workerMove(workerId, row, col);

	}

	public String workerMoveHerp(String workerId, int row, int col) {
		return herpCElization.workerMove(workerId, row, col);

	}

	public String workerWorkAt(String workerId, String buildingId) {
		return derpCElization.workerWorkAt(workerId, buildingId);
	}

	public String workerWorkAtH(String workerId, String buildingId) {
		return herpCElization.workerWorkAt(workerId, buildingId);
	}

	public String workerBuild(String workerId, int buildingType, int row,
			int col) {
		return derpCElization.workerBuild(workerId, buildingType, row, col);
	}

	public String workerBuildH(String workerId, int buildingType, int row,
			int col) {
		return herpCElization.workerBuild(workerId, buildingType, row, col);
	}

	// WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	// -------------------
	// BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
	public String trainWorkerIn(String buildingId) {
		return derpCElization.trainWorkerIn(buildingId);
	}

	public String trainWorkerInH(String buildingId) {
		return herpCElization.trainWorkerIn(buildingId);
	}

	public String trainAxerIn(String buildingId) {
		return derpCElization.trainAxerIn(buildingId);
	}

	public String trainDarterIn(String buildingId) {
		return derpCElization.trainDarterIn(buildingId);
	}

	public String trainNiggaIn(String buildingId) {
		return derpCElization.trainNiggaIn(buildingId);
	}

	public String trainCluberIn(String buildingId) {
		return derpCElization.trainCluberIn(buildingId);
	}

	public String trainScholarIn(String buildingId) {
		return derpCElization.trainScholarIn(buildingId);
	}

	public String trainBoatIn(String buildingId) {
		return derpCElization.trainBoatIn(buildingId);
	}

	public String doResearch(String scholar, int research) {
		if (research < 1 || 25 < research)
			return "invalid research";
		return derpCElization.doResearch(scholar, research);
	}

	public String exchange(int amount, String from, String to, String marketId) {
		if (derpCElization.findBuilding(marketId) instanceof Market)
			return derpCElization.exchange(amount, from, to);
		return "invalid id";
	}

	// *
	/*
	 * for herp:
	 */
	public String trainAxerInH(String buildingId) {
		return herpCElization.trainAxerIn(buildingId);
	}

	public String trainDarterInH(String buildingId) {
		return herpCElization.trainDarterIn(buildingId);
	}

	public String trainNiggaInH(String buildingId) {
		return herpCElization.trainNiggaIn(buildingId);
	}

	public String trainCluberInH(String buildingId) {
		return herpCElization.trainCluberIn(buildingId);
	}

	public String trainScholarInH(String buildingId) {
		return herpCElization.trainScholarIn(buildingId);
	}

	public String trainBoatInH(String buildingId) {
		return herpCElization.trainBoatIn(buildingId);
	}

	public String doResearchH(String scholar, int research) {
		if (research < 1 || 25 < research)
			return "invalid research";
		return herpCElization.doResearch(scholar, research);
	}

	public String exchangeH(int amount, String from, String to, String marketId) {
		if (herpCElization.findBuilding(marketId) instanceof Market)
			return herpCElization.exchange(amount, from, to);
		return "invalid id";
	}

	public String sell(String buildingId) {
		if (derpCElization.findBuilding(buildingId) == null)
			return "invalid id";
		return derpCElization.sell(buildingId);
	}

	public String sellH(String buildingId) {
		if (herpCElization.findBuilding(buildingId) == null)
			return "invalid id";
		return herpCElization.sell(buildingId);
	}

	public void soldierAttackH(String solId, String vId) {
		herpCElization.soldierAttack(solId, vId);
	}

	static int ai = 0;

	// BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
	public int time() {
		return time;
	}

	/**
	 * goes to the next turn
	 */
	public String[] nextTurn() {
		String cmds = null;
		if (mode.equals("server")) {
			// Connect.isServerDone = true;
			cmds = Connect.handshakeAsServer();
		} else if (mode.equals("client")) {
			// Connect.isClientDone = true;
			cmds = Connect.handshakeAsClient();
		} else if (mode.equals("AI")) {
			if (ai == 0) {
				cmds = "worker_1_2+build 1 1 20;";
			} else if (ai == 10) {
				cmds = "worker_1_2+build 2 5 16;"
						+ herpCElization.getBuildings().get(0).getId()
						+ "+train;";
			} else if (ai == 15) {
				cmds = herpCElization.getUnits().get(1).getId()
						+ "+build 5 0 17";
			} else if (ai == 24) {
				cmds = herpCElization.getUnits().get(1).getId() + "+work "
						+ herpCElization.getBuildings().get(1).getId() + ";";
			}
			ai += 1;
		}

		if (cmds != null) {
			String[] cmd = cmds.split(";");
			for (int i = 0; i < cmd.length; i += 1) {
				System.out.println(cmd[i]);
				if (cmd[i].equals(""))
					continue;
				String id = cmd[i].split("\\+")[0];
				String command = cmd[i].split("\\+")[1];
				System.out.println(id);
				System.out.println(command);
				System.out.println(HerpJudge.action(id, command));
			}
		}
		if (herpCElization != null)
			herpCElization.nextTurn();
		return derpCElization.nextTurn();

	}

	// @#$%^65A&*()_54d)(*4&^%$#f$0%^&*()_)(*&^%$%^&*()_)(YFCG()*HGFD*%(^&*^F^(&*^G*%&#$%&()+)(*654&$#&*()_+)(*1&^%$
	/*
	 * CHEATS!!!!! :D
	 */

	public void cheatSetResource(String resource, int amount) {
		if (resource.equals("gold"))
			derpCElization.setGoldInBank(amount);

		else if (resource.equals("stone"))
			derpCElization.setStoneInBank(amount);

		else if (resource.equals("lumber"))
			derpCElization.setLumberInBank(amount);

		else if (resource.equals("food"))
			derpCElization.setFoodInBank(amount);

		else if (resource.equals("knowledge"))
			derpCElization.setKnowledge(amount);

	}

	public String cheatMakeWorkerAt(int row, int col) {
		try {
			return derpCElization.cheatMakeWorkerAt(row, col);
		} catch (Exception ex) {
			return null;
		}
	}

	public void cheatMoveWorkerAt(String workerId, int row, int col) {
		try {
			derpCElization.teleportWorker(workerId, row, col);
		} catch (Exception ex) {
			// nothing
		}
	}

	public String cheatMakeBuildingAt(String buildingType, int row, int col) {
		try {
			return derpCElization.cheatMakeBuildingAt(buildingType, row, col);

		} catch (Exception ex) {
			return null;
		}
	}

	public static boolean isEnemy(String hisId, int urCeId) {
		String[] temp = hisId.split("_");
		int objCeId;
		try {
			objCeId = Integer.parseInt(temp[temp.length - 1]);
		} catch (Exception e) {
			return false;
		}
		if (urCeId != objCeId)
			return true;
		return false;
	}

	public String cheatMakeScholar() {
		try {
			return derpCElization.cheatMakeScholar();
		} catch (Exception ex) {
			return null;
		}
	}

	public void cheatResearch(int research) {
		derpCElization.researches[research - 1] = true;
	}

	public void cheatExploreAll() {
		derpCElization.cheatExploreAll();
	}

	public void cheatExplore(int row, int col) {
		derpCElization.cheatExplore(row, col);
	}

	/*
	 * path finder
	 */
	public static String findPath(int sRow, int sCol, String dRow, String dCol) {
		return findPathPrivate(isAvailable, sRow, sCol, Integer.parseInt(dRow),
				Integer.parseInt(dCol));
	}

	public static String findPathToKill(int sRow, int sCol, int dRow, int dCol) {
		boolean[][] newMap = isAvailable;
		newMap[dRow][dCol] = true;
		return findPathPrivate(isAvailable, sRow, sCol, (dRow), (dCol));
	}

	private static String findPathPrivate(boolean[][] map, int sRow, int sCol,
			int dRow, int dCol) {

		class Point {
			public int row;
			public int col;

			public Point(int row, int col) {
				this.row = row;
				this.col = col;
			}
		}

		int rowCount = map.length;
		int colCount = map[0].length;

		String[][] directions = new String[rowCount][colCount];
		Point[][] parents = new Point[rowCount][colCount];
		boolean[][] visited = new boolean[rowCount][colCount];
		boolean found = false;

		LinkedList<Point> queue = new LinkedList<Point>();
		queue.add(new Point(sRow, sCol));

		while (!found && queue.size() > 0) {
			Point point = queue.poll();
			if (point.row == dRow && point.col == dCol)
				found = true;

			String[] dirList = { "up", "right", "down", "left" };
			int[] deltaRow = { -1, 0, 1, 0 };
			int[] deltaCol = { 0, 1, 0, -1 };

			for (int i = 0; i < 4; i++) {
				int cRow = point.row + deltaRow[i];
				int cCol = point.col + deltaCol[i];
				if (cRow < 0 || cRow >= rowCount || cCol < 0
						|| cCol >= colCount)
					continue;
				if (visited[cRow][cCol] || !map[cRow][cCol])
					continue;
				queue.add(new Point(cRow, cCol));
				directions[cRow][cCol] = dirList[i];
				parents[cRow][cCol] = point;
				visited[cRow][cCol] = true;
				if (cRow == dRow && cCol == dCol)
					found = true;
			}
		}

		if (!found)
			return "none";

		Point point = new Point(dRow, dCol);
		Point parent = parents[dRow][dCol];
		while (parent != null && !(parent.row == sRow && parent.col == sCol)) {
			point = parent;
			parent = parents[point.row][point.col];
		}

		String dir = directions[point.row][point.col];
		if (dir == null)
			return "none";
		else
			return dir;
	}
}
