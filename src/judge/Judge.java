/*
 * In the name of God
 */

package judge;

import game.Block;
import game.BlockType;
import game.Board;

import java.util.HashMap;
import java.util.Map;

import units.Boat;
import units.Worker;
import buildings.Building;
import buildings.University;

public class Judge {

	// *************************
	public static Board gameBoard;
	private static int rowNum, colNum;

	public static Board strat(char[][] map, int[][] goldMap, int[][] stoneMap,
			int[][] lumberMap, int[][] foodMap, String mode) {
		rowNum = map.length;
		colNum = map[0].length;
		Block[][] fullMap = new Block[rowNum][colNum];
		boolean available[][] = new boolean[rowNum][colNum];

		/*
		 * creates the fullMap, and available map from Plains
		 */
		BlockType type;
		for (int i = 0; i < rowNum; i += 1)
			for (int j = 0; j < colNum; j += 1) {
				if (map[i][j] == 'P') {
					type = BlockType.Plain;
					available[i][j] = true;
				} else {
					available[i][j] = false;
					if (map[i][j] == 'M')
						type = BlockType.Mountain;
					else if (map[i][j] == 'F')
						type = BlockType.Forest;
					else
						type = BlockType.Water;
				}// type and available are initialized.

				fullMap[i][j] = new Block(type, goldMap[i][j], stoneMap[i][j],
						lumberMap[i][j], foodMap[i][j]);
			}// now the fullMap & available are read.

		gameBoard = new Board(fullMap, available, mode);
		return gameBoard;
	}

	// -------------------------------
	// ################################
	// -------------------------------

	public static Map<String, String> info(String id) {
		Map<String, String> map = new HashMap<String, String>();

		if (id == null) {
			map.put("gold", Board.derpCElization.goldInfo());
			map.put("stone", Board.derpCElization.stoneInfo());
			map.put("lumber", Board.derpCElization.lumberInfo());
			map.put("food", Board.derpCElization.foodInfo());
			map.put("knowledge", "" + Board.derpCElization.getKnowledge());
			map.put("buildings", Board.derpCElization.buildingsGenerator());
			map.put("workers", Board.derpCElization.workersGenerator());//
			map.put("scholars", Board.derpCElization.scholarsGenerator());
			map.put("boats", Board.derpCElization.boatsGenerator());//
			return map;

		} else if (isWorker(id)) {
			map.put("type", "worker");
			Worker w = Board.derpCElization.findWorker(id);
			map.put("location", (w.getRow() + 1) + " " + (w.getCol() + 1));
			map.put("inventory", w.inventory()[0] + "/" + w.inventory()[1]
					+ " " + w.inventory()[2]);
			map.put("occupation", w.getOccupation());
			map.put("buildings", Board.derpCElization.buildingsAllowed());
			return map;

		} else if (isBuilding(id)) {
			map.put("type", "building");
			Building b = Board.derpCElization.findBuilding(id);
			map.put("building type", b.getType());
			int row = b.getRow() + 1;
			int col = b.getCol() + 1;
			int size = b.getSize();
			map.put("blocks", generateBlocks(row, col, size));
			/*
			 * if id was for a university
			 */
			if (id.split("_")[1].equals("2")) {
				University uni = (University) b;
				map.put("scholars",
						uni.scholarsInside + "/" + uni.scholarLimit());
				map.put("researching", uni.researchInProccess());
				map.put("finished", uni.finished());
				map.put("available", Board.derpCElization.availableResearches());

			}
			return map;
		} else if (isBoat(id)) {
			map.put("type", "boat");
			Boat boat = Board.derpCElization.findBoat(id);
			map.put("location", (boat.getRow() + 1) + " " + (boat.getCol() + 1));
			return map;

		} else if (isScholar(id)) {
			map.put("type", "scholar");
			return map;
		}

		String[] splittedId = id.split(" ");
		if (splittedId[0].equals("block")) {
			int row;
			int col;
			try {
				row = Integer.parseInt(splittedId[1]) - 1;
				col = Integer.parseInt(splittedId[2]) - 1;
			} catch (Exception ex) {
				return null;
			}
			map.put("type", Board.derpCElization.blockTypeInfo(row, col));
			map.put("gold", Board.derpCElization.seeGoldInfo(row, col));
			map.put("stone", Board.derpCElization.seeStoneInfo(row, col));
			map.put("lumber", Board.derpCElization.seeLumberInfo(row, col));
			map.put("food", Board.derpCElization.seeFoodInfo(row, col));
			return map;
		}

		return null;
	}

	// -------------------------------
	// ################################
	// -------------------------------
	public static String action(String id, String command) {
		String[] splitedCommand = command.split(" ");
		if (isWorker(id)) {
			// workerMove
			if (splitedCommand[0].equals("move")) {
				// first check if the location is valid then calls the method
				int Row;
				int Col;
				try {
					Row = Integer.parseInt(splitedCommand[1]);
					Col = Integer.parseInt(splitedCommand[2]);
					if (Row < 1 || Col < 1 || rowNum < Row || colNum < Col)
						return "invalid location";
				} catch (Exception ex) {
					return "invalid location";
				}
				return gameBoard.workerMove(id, Row, Col);

			} // mind checked ;)
				// wwwwwwwwwwwwwwwwwwww
				// workerWorkAt
			else if (splitedCommand[0].equals("work")) {
				return gameBoard.workerWorkAt(id, splitedCommand[1]);
			}
			// wwwwwwwwwwwwwwwwwwww
			// workerBuild
			else if (splitedCommand[0].equals("build")) {

				int Row, Col, Type;
				try {
					Type = Integer.parseInt(splitedCommand[1]);
				} catch (Exception ex) {
					return "invalid building";
				}
				try {
					Row = Integer.parseInt(splitedCommand[2]);
					Col = Integer.parseInt(splitedCommand[3]);
					if (Row < 1 || Col < 1 || rowNum < Row || colNum < Col)
						return "invalid location";
				} catch (Exception ex) {
					return "invalid location";
				}

				return gameBoard.workerBuild(id, Type, Row - 1, Col - 1);
			} else
				return "invalid command";

		} else if (isBuilding(id)) {
			/*
			 * mainBuilding
			 */
			if (command.equals("sell"))
				return gameBoard.sell(id);

			if (id.split("_")[1].equals("1")) {
				if (command.equals("train"))
					return gameBoard.trainWorkerIn(id);
				else
					return "invalid command";
			}
			/*
			 * port
			 */
			else if (id.split("_")[1].equals("9")) {
				if (command.equals("train"))
					return gameBoard.trainBoatIn(id);
				else
					return "invalid command";
			}
			/*
			 * uni
			 */
			else if (id.split("_")[1].equals("2")) {
				if (command.equals("train"))
					return gameBoard.trainScholarIn(id);
				else
					return "invalid command";
			}
			/*
			 * market
			 */
			else if (id.split("_")[1].equals("8")) {
				int amount = 0;
				String from = null, to = null;
				try {
					amount = Integer.parseInt(splitedCommand[0]);
					from = splitedCommand[1];
					to = splitedCommand[2];
				} catch (Exception ex) {
					return "invalid command";
				}
				return gameBoard.exchange(amount, from, to, id);
			} else if (id.split("_")[1].equals("10")) {
				if (command.equals("axer"))
					return gameBoard.trainAxerIn(id);
				// TODO
				else if (command.equals("darter"))
					return gameBoard.trainDarterIn(id);
				else return "invalid command";
			} else if (id.split("_")[1].equals("11")) {
				if (command.equals("nigga"))
					return gameBoard.trainNiggaIn(id);
				else if (command.equals("cluber"))
					return gameBoard.trainCluberIn(id);
				// TODO
				else
					return "invalid command";
			}

			// end of building commands
		} else if (isScholar(id)) {
			if (splitedCommand[0].equals("research")) {
				int research;
				try {
					research = Integer.parseInt(splitedCommand[1]);
				} catch (Exception ex) {
					return "invalid research";
				}
				return gameBoard.doResearch(id, research);
			} else
				return "invalid command";

		} else if (isBoat(id)) {
			return "invalid id";

		} else if (id.equals("judge")) {
			/*
			 * cheats :D
			 */
			if (splitedCommand[0].equals("set")) {
				String resource = splitedCommand[1];
				int amount = Integer.parseInt(splitedCommand[2]);
				gameBoard.cheatSetResource(resource, amount);

			} else if (splitedCommand[0].equals("worker")) {
				int row, col;
				row = Integer.parseInt(splitedCommand[1]);
				col = Integer.parseInt(splitedCommand[2]);
				return gameBoard.cheatMakeWorkerAt(row - 1, col - 1);

			} else if (splitedCommand[0].equals("move")) {
				String workerId = splitedCommand[1];
				int row, col;
				row = Integer.parseInt(splitedCommand[2]);
				col = Integer.parseInt(splitedCommand[3]);
				gameBoard.cheatMoveWorkerAt(workerId, row - 1, col - 1);

			} else if (splitedCommand[0].equals("building")) {
				String buildingType = splitedCommand[1];
				int row, col;
				row = Integer.parseInt(splitedCommand[2]);
				col = Integer.parseInt(splitedCommand[3]);
				return gameBoard.cheatMakeBuildingAt(buildingType, row - 1,
						col - 1);

			} else if (splitedCommand[0].equals("scholar")) {
				return gameBoard.cheatMakeScholar();

			} else if (splitedCommand[0].equals("research")) {
				int research = Integer.parseInt(splitedCommand[1]);
				gameBoard.cheatResearch(research);

			} else if (splitedCommand[0].equals("explore")) {
				if (splitedCommand[1].equals("all"))
					gameBoard.cheatExploreAll();
				else {
					int row, col;
					row = Integer.parseInt(splitedCommand[1]);
					col = Integer.parseInt(splitedCommand[2]);
					gameBoard.cheatExplore(row - 1, col - 1);
				}
			}

		} else
			return "invalid id";

		return "invalid command";
	}

	public static boolean isWorker(String ID) {
		if (ID.split("_")[0].equals("derp")
				|| ID.split("_")[0].equals("worker"))
			return true;
		return false;
	}

	public static boolean isSoldier(String ID) {
		if (ID.split("_")[0].equals("sol"))
			return true;
		return false;
	}

	public static boolean isAxer(String ID) {
		if (ID.split("_")[1].equals("axer"))
			return true;
		return false;
	}

	public static boolean isDarter(String ID) {
		if (ID.split("_")[1].equals("darter"))
			return true;
		return false;
	}

	public static boolean isNigga(String ID) {
		if (ID.split("_")[1].equals("nigga"))
			return true;
		return false;
	}

	public static boolean isCluber(String ID) {
		if (ID.split("_")[1].equals("cluber"))
			return true;
		return false;
	}

	public static boolean isBuilding(String ID) {
		if (ID.split("_")[0].equals("building"))
			return true;
		return false;
	}

	public static boolean isScholar(String ID) {
		if (ID.split("_")[0].equals("scholar"))
			return true;
		return false;
	}

	public static boolean isBoat(String ID) {
		if (ID.split("_")[0].equals("boat"))
			return true;
		return false;
	}

	private static String generateBlocks(int row, int col, int size) {
		String ret = "";
		for (int i = 0; i < size; i += 1)
			for (int j = 0; j < size; j += 1)
				ret += "," + row + i + " " + col + j;
		return ret.substring(1);
	}

	// -------------------------------
	// ################################
	// -------------------------------

	public static String[] nextTurn() {
		return gameBoard.nextTurn();
	}

	// **************************
}
