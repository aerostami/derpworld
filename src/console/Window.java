package console;

import game.BlockType;
import game.Board;
import game.CElization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import judge.Judge;
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
import conect.Connect;

public class Window extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window() {
		addMouseListener();
		addKeyListener();
		loadImages();

	}

	String menuFocus = "idle";

	boolean isFocused = false;
	boolean isFocusEnemy = false;
	int colFocused, rowFocused;
	String focusedMsg = "";
	String focusedId;
	String focusMenuState = "idle";
	String focusResult = "";
	String lanMode = "none";

	int gFont;
	String[] focusMenuItems;
	String[] nextTurnResult;
	boolean showNextTurn = false;

	BufferedImage openBook = null;
	BufferedImage NewGame = null;
	BufferedImage modePage = null;
	BufferedImage mapPage = null;
	BufferedImage joinPage = null;
	BufferedImage waitPage = null;
	BufferedImage redC = null;
	BufferedImage greenC = null;

	BufferedImage bookBar = null;
	BufferedImage playPressed = null;
	BufferedImage play = null;

	BufferedImage rock = null;
	BufferedImage grass = null;
	BufferedImage plain = null;
	BufferedImage water = null;
	BufferedImage mark = null;
	BufferedImage cloud = null;

	BufferedImage derp = null;
	BufferedImage worker = null;
	BufferedImage axer = null;
	BufferedImage darter = null;
	BufferedImage nigga = null;
	BufferedImage cluber = null;

	BufferedImage boat = null;

	BufferedImage cabin = null;
	BufferedImage cabinConst = null;
	BufferedImage main = null;
	BufferedImage mainConst = null;
	BufferedImage camp = null;
	BufferedImage campConst = null;
	BufferedImage pile = null;
	BufferedImage pileConst = null;

	public CElization celization;

	int x = 30, y = 30, xZoom = 55, yZoom = 55;

	@Override
	public void paint(Graphics grp) {
		Graphics2D g = (Graphics2D) grp;
		if (Console.board != null) {
			Console.frame.setBackground(new Color(27, 26, 31));
			for (int i = 0; i < Board.map.length; i += 1)
				for (int j = 0; j < Board.map[0].length; j += 1) {

					if (Board.map[i][j].type() == BlockType.Forest) {
						g.drawImage(getGrass(), j * xZoom + x, i * yZoom + y,
								xZoom, yZoom, null);
					} else if (Board.map[i][j].type() == BlockType.Mountain) {
						g.drawImage(getRock(), j * xZoom + x, i * yZoom + y,
								xZoom, yZoom, null);
					} else if (Board.map[i][j].type() == BlockType.Water) {
						g.drawImage(getWater(), j * xZoom + x, i * yZoom + y,
								xZoom, yZoom, null);
					} else {
						g.drawImage(getPlain(), j * xZoom + x, i * yZoom + y,
								xZoom, yZoom, null);
					}
				}
			// end of painting the map
			drawBuildings(g, celization);
			if (!Console.mode.equals("free")) {
				drawBuildings(g, Board.herpCElization);
			}
			// end of painting buildings

			/*
			 * Drawing Units:
			 */
			drawUnits(g, celization);
			if (!Console.mode.equals("free")) {
				drawUnits(g, Board.herpCElization);
			}

			// end of painting units
			/*
			 * drawing the clouds
			 */
			for (int i = 0; i < Board.map.length; i += 1)
				for (int j = 0; j < Board.map[0].length; j += 1)
					if (!Board.derpCElization.getIsDiscovered()[i][j])
						g.drawImage(cloud, j * xZoom + x-(xZoom/6), i * yZoom + y-(yZoom/6), xZoom+2*(xZoom/6),
								yZoom+2*(yZoom/6), null);

			/*
			 * Drawing the main Bar
			 */
			g.drawImage(bookBar, 0, 0, getWidth(), getHeight(), null);
			g.drawImage(play, 0, 0, getWidth(), getHeight(), null);

			/*
			 * drawing the stats info
			 */
			g.setColor(new Color(217, 184, 139));
			int w = (int) (getWidth() * .69);
			int h = (int) (getHeight() * .8);
			g.drawString("Gold " + celization.goldInfo(), w, h);
			h += g.getFontMetrics().getHeight();
			g.drawString("Stone " + celization.stoneInfo(), w, h);
			h += g.getFontMetrics().getHeight();
			g.drawString("Lumber " + celization.lumberInfo(), w, h);
			h += g.getFontMetrics().getHeight();
			g.drawString("Food " + celization.foodInfo(), w, h);
			h += g.getFontMetrics().getHeight();
			g.drawString("Knowledge " + celization.getKnowledge(), w, h);
			h += g.getFontMetrics().getHeight();
			g.setColor(Color.BLACK);
			// stats on bar over

			// end of drawing the main Bar

			/*
			 * drawing focused info on bar
			 */
			g.setColor(Color.GRAY);
			if (isFocused) {
				if (Board.map[rowFocused][colFocused].objOnBlock().equals(
						"empty")
						&& (Judge.isWorker(focusedId) || Judge
								.isSoldier(focusedId))) {
					/*
					 * removes the moved focus
					 */

					isFocused = false;

				} else {
					g.drawString(focusedMsg, (int) (getWidth() * .83),
							(int) (getHeight() * .03));
					drawFocusedInfo(g);
				}
			}// end of focused

			// if(celization.getUnits().size()==0){
			// JOptionPane.showMessageDialog(null,
			// "All of your units are dead! :D");
			// System.exit(0);
			// }
			if (showNextTurn) {
				/*
				 * printing the next turn logs...
				 */
				w = (int) (getWidth() * .83);
				h = (int) (getHeight() * .03);
				for (int i = 0; i < nextTurnResult.length; i += 1) {
					h += g.getFontMetrics().getHeight();
					g.drawString(nextTurnResult[i], w, h);
				}
			}
			g.setColor(Color.black);
			

		}// if (board!=null) else{
		/*
		 * on menu:
		 */
		else {
			g.drawImage(openBook, 0, 0, getWidth(), getHeight(), null);
			if (menuFocus.equals("idle")) {
				g.drawImage(NewGame, 0, 0, getWidth(), getHeight(), null);
			} else if (menuFocus.equals("newGame")) {
				g.drawImage(modePage, 0, 0, getWidth(), getHeight(), null);
			} else if (menuFocus.equals("map")) {
				g.drawImage(mapPage, 0, 0, getWidth(), getHeight(), null);
			} else if (menuFocus.equals("join")) {
				g.drawImage(joinPage, 0, 0, getWidth(), getHeight(), null);
			} else if (menuFocus.equals("wait")) {
				g.drawImage(waitPage, 0, 0, getWidth(), getHeight(), null);
			}

		}

	}

	private void drawUnits(Graphics2D g, CElization ce) {
		
		for (Unit unit : ce.getUnits()) {
			if (ce.ceId()==1)
				g.drawImage(greenC, x + unit.getCol() * xZoom, y + unit.getRow()
						* yZoom, xZoom, yZoom, null);
			else g.drawImage(redC, x + unit.getCol() * xZoom, y + unit.getRow()
					* yZoom, xZoom, yZoom, null);
			
			if (unit instanceof Boat) {
				g.drawImage(boat, y + unit.getCol() * yZoom, x + unit.getRow()
						* xZoom, xZoom, yZoom, null);
			} else if (unit instanceof Worker) {
				if (unit.getId().split("_")[0].equals("derp"))
					g.drawImage(derp, x + unit.getCol() * xZoom,
							y + unit.getRow() * yZoom, 4 * xZoom / 5,
							4 * yZoom / 5, null);
				else
					g.drawImage(worker, y + unit.getCol() * yZoom,
							x + unit.getRow() * xZoom, 4 * xZoom / 5,
							4 * yZoom / 5, null);

			} else if (unit instanceof Axer)
				g.drawImage(axer, x + unit.getCol() * xZoom, y + unit.getRow()
						* yZoom, 4 * xZoom / 5, 4 * yZoom / 5, null);
			else if (unit instanceof Darter)
				g.drawImage(darter, x + unit.getCol() * xZoom,
						y + unit.getRow() * yZoom, 4 * xZoom / 5,
						4 * yZoom / 5, null);
			else if (unit instanceof NiggaRider)
				g.drawImage(nigga, x + unit.getCol() * xZoom, y + unit.getRow()
						* yZoom, xZoom, yZoom, null);
			else if (unit instanceof CluberRider)
				g.drawImage(cluber, x + unit.getCol() * xZoom,
						y + unit.getRow() * yZoom, xZoom, yZoom, null);
		}
	}

	private void drawBuildings(Graphics2D g, CElization ce) {
		BufferedImage a;
		if (ce.ceId()==2)
			a=redC;
		else a = greenC;
		
		for (Building building : ce.getBuildings()) {
			int X = building.getCol();
			int Y = building.getRow();
			if (building instanceof University) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						3 * xZoom, 3 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(mainConst, X * xZoom + x, Y * yZoom + y,
							3 * xZoom, 3 * yZoom, null);
				} else {
					g.drawImage(main, X * xZoom + x, Y * yZoom + y, 3 * xZoom,
							3 * yZoom, null);
				}
			} else if (building instanceof StoneMine) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						2 * xZoom, 2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(cabinConst, X * xZoom + x, Y * yZoom + y,
							2 * xZoom, 2 * yZoom, null);
				} else {
					g.drawImage(cabin, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
							2 * yZoom, null);
				}
			} else if (building instanceof Farm) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
						2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(pileConst, X * xZoom + x, Y * yZoom + y,
							2 * xZoom, 2 * yZoom, null);
				} else {
					g.drawImage(pile, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
							2 * yZoom, null);
				}
			} else if (building instanceof GoldMine) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						2 * xZoom, 2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(cabinConst, X * xZoom + x, Y * yZoom + y,
							2 * xZoom, 2 * yZoom, null);
				} else {
					g.drawImage(cabin, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
							2 * yZoom, null);
				}
			} else if (building instanceof Market) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						3 * xZoom, 3 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(mainConst, X * xZoom + x, Y * yZoom + y,
							3 * xZoom, 3 * yZoom, null);
				} else {
					g.drawImage(main, X * xZoom + x, Y * yZoom + y, 3 * xZoom,
							3 * yZoom, null);
				}
			} else if (building instanceof Port) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						2 * xZoom, 2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(cabinConst, X * xZoom + x, Y * yZoom + y,
							2 * xZoom, 2 * yZoom, null);
				} else {
					g.drawImage(cabin, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
							2 * yZoom, null);
				}
			} else if (building instanceof StockPile) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						2 * xZoom, 2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(pileConst, X * xZoom + x, Y * yZoom + y,
							2 * xZoom, 2 * yZoom, null);
				} else {
					g.drawImage(pile, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
							2 * yZoom, null);
				}
			} else if (building instanceof MainBuilding) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						3 * xZoom, 3 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(mainConst, X * xZoom + x, Y * yZoom + y,
							3 * xZoom, 3 * yZoom, null);
				} else {
					g.drawImage(main, X * xZoom + x, Y * yZoom + y, 3 * xZoom,
							3 * yZoom, null);
				}
			} else if (building instanceof Military) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						2 * xZoom, 2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(mainConst, X * xZoom + x, Y * yZoom + y,
							2 * xZoom, 2 * yZoom, null);
				} else {
					g.drawImage(main, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
							2 * yZoom, null);
				}
			} else if (building instanceof Stable) {
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						2 * xZoom, 2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(mainConst, X * xZoom + x, Y * yZoom + y,
							2 * xZoom, 2 * yZoom, null);
				} else {
					g.drawImage(main, X * xZoom + x, Y * yZoom + y, 2 * xZoom,
							2 * yZoom, null);
				}
			} else {// WoodCamp
				g.drawImage(a, X * xZoom + x, Y * yZoom + y,
						2 * xZoom, 2 * yZoom, null);
				if (0 < building.turnsLeftToBuild) {
					g.drawImage(campConst, X * xZoom + x, Y * yZoom + y, xZoom,
							yZoom, null);
				} else {
					g.drawImage(camp, X * xZoom + x, Y * yZoom + y, xZoom,
							yZoom, null);
				}
			}
		}// end of draw buildings
	}

	private void drawFocusedInfo(Graphics2D g) {
		if (focusedId.equals("empty"))
			return;
		int w = (int) (getWidth() * .223);
		int h = (int) (getHeight() * .79);
		gFont = g.getFontMetrics().getHeight();
		g.setColor(new Color(217, 184, 139));
		if (isFocusEnemy) {
			g.drawString("Enemy object", w, h);
			h += g.getFontMetrics().getHeight();
			return;
		}
		if (Judge.isWorker(focusedId)) {
			Worker wo = Board.derpCElization.findWorker(focusedId);
			ArrayList<String> toDoList = wo.getToDoList();
			if (toDoList.size() != 0) {
				String[] toDo = toDoList.get(0).split(" ");
				if (toDo[0].equals("move")) {
					int zisY = Integer.parseInt(toDo[1]);
					int zisX = Integer.parseInt(toDo[2]);
					g.drawImage(mark, x + zisX * xZoom, y + zisY * yZoom,
							xZoom, yZoom, null);
				}
			}

			if (focusMenuState.equals("idle")) {
				h = (int) (getHeight() * .767);
				g.drawString("                 Worker", w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("Name: " + focusedId, w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString(
						"Inventory: " + wo.inventory()[0] + "/"
								+ wo.inventory()[1] + " " + wo.inventory()[2],
						w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("Occupation: " + wo.getOccupation(), w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("> Build", w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("building exp: " + wo.getExpPeriod()[4], w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("gold mining exp: " + wo.getExpPeriod()[0], w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("stone mining exp" + wo.getExpPeriod()[1], w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("farming exp: " + wo.getExpPeriod()[2], w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("lumbering exp: " + wo.getExpPeriod()[3], w, h);
				// end of idle info
			} else if (focusMenuState.equals("build")) {
				focusMenuItems = celization.buildingsAllowedName().split(" ");
				if (focusMenuItems.length == 0 || focusMenuItems[0].equals(""))
					g.drawString("No building available", w, h);
				else
					g.drawString("Available to build:", w, h);
				h += g.getFontMetrics().getHeight();
				for (int i = 0; i < focusMenuItems.length; i += 1) {
					g.drawString(">" + focusMenuItems[i], w, h);
					h += g.getFontMetrics().getHeight();
				}
			} else if (focusMenuState.charAt(0) == '0') {
				int i = Integer.parseInt(focusMenuState);
				h += g.getFontMetrics().getHeight() + 5;
				g.drawString("  select a place", w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("  to construct", w, h);
				h += g.getFontMetrics().getHeight();
				g.drawString("  the" + focusMenuItems[i], w, h);
			}

		} else if (Judge.isBuilding(focusedId)) {
			Building b = celization.findBuilding(focusedId);
			int type = Integer.parseInt(b.getType());
			String name = celization.buildingName(type);
			if (b instanceof MainBuilding) {
				if (focusMenuState.equals("idle")) {

					g.drawString("  " + name, w, h);
					h += g.getFontMetrics().getHeight() + 5;
					g.drawString(">Train Worker", w, h);
					h += g.getFontMetrics().getHeight();
				} else if (focusMenuState.equals(">Train")) {
					h += g.getFontMetrics().getHeight() + 5;
					g.drawString("-Train a worker", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(focusResult, w, h);
				}
			} else if (b instanceof University) {
				// ///////////////////////////////////
				if (focusMenuState.equals("idle")) {

					g.drawString("  " + name, w, h);
					h += g.getFontMetrics().getHeight() + 5;
					University uni = (University) b;
					g.drawString(">Train Scholar", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(">Reserch", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString("Scholars inside: " + uni.scholarsInside + "/"
							+ uni.scholarLimit(), w, h);

				} else if (focusMenuState.equals(">Train")) {
					h += g.getFontMetrics().getHeight() + 5;
					g.drawString("-Train a scholar", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(focusResult, w, h);

				} else if (focusMenuState.equals(">Research")) {
					focusMenuItems = celization.availableResearches()
							.split(" ");
					g.drawString("Researches available:", w, h);
					for (int i = 0; i < focusMenuItems.length; i += 1) {
						h += g.getFontMetrics().getHeight();
						g.drawString(">" + focusMenuItems[i], w, h);
					}
				} else if (focusMenuState.charAt(0) == '0') {
					int i = Integer.parseInt(focusMenuState);
					h += g.getFontMetrics().getHeight() + 5;
					g.drawString("Research on project " + i, w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(focusResult, w, h);
				}
			} else if (b instanceof Military) {
				if (focusMenuState.equals("idle")) {
					g.drawString("  " + name, w, h);
					h += g.getFontMetrics().getHeight() + 5;
					Military mili = (Military) b;
					g.drawString(">Train Axer", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(">Train Darter", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString("Now in Training: " + mili.nowInTraining(), w,
							h);

				} else if (focusMenuState.equals(">axer")) {
					g.drawString("Train an axer", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(focusResult, w, h);
				} else if (focusMenuState.equals(">darter")) {
					g.drawString("Train a darter", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(focusResult, w, h);

				}
			} else if (b instanceof Stable) {
				if (focusMenuState.equals("idle")) {
					g.drawString("  " + name, w, h);
					h += g.getFontMetrics().getHeight() + 5;
					Stable stable = (Stable) b;
					g.drawString(">Train Nigga", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(">Train Clber", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString("Now in Training: " + stable.nowInTraining(),
							w, h);

				} else if (focusMenuState.equals(">nigga")) {
					g.drawString("Train an nigga", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(focusResult, w, h);
				} else if (focusMenuState.equals(">cluber")) {
					g.drawString("Train a cluber", w, h);
					h += g.getFontMetrics().getHeight();
					g.drawString(focusResult, w, h);

				}
			}
			// end of b instanceof uni
			// other buildings //TODO

			// TODO ^ ^
		} else if (Judge.isBoat(focusedId)) {

		} else if (Judge.isSoldier(focusedId)) {
			Soldier sol = celization.findSoldier(focusedId);
			g.drawString("Soldier", w, h);
			h += g.getFontMetrics().getHeight();
			g.drawString(focusedId, w, h);
			h += g.getFontMetrics().getHeight();
			g.drawString("occupation: " + sol.occupation(), w, h);

			ArrayList<String> toDoList = sol.getToDoList();
			if (toDoList.size() != 0) {
				String[] toDo = toDoList.get(0).split(" ");
				if (toDo[0].equals("move")) {
					int zisY = Integer.parseInt(toDo[1]);
					int zisX = Integer.parseInt(toDo[2]);
					g.drawImage(mark, x + zisX * xZoom, y + zisY * yZoom,
							xZoom, yZoom, null);
				}
			}
		}
		g.setColor(Color.BLACK);
	}

	private void loadImages() {
		try {
			openBook = ImageIO.read(new File("data/menu/openBook.jpg"));
			NewGame = ImageIO.read(new File("data/menu/NewGame.png"));
			modePage = ImageIO.read(new File("data/menu/mode.png"));
			mapPage = ImageIO.read(new File("data/menu/map.png"));
			joinPage = ImageIO.read(new File("data/menu/join.png"));
			waitPage = ImageIO.read(new File("data/menu/waiting.png"));

			bookBar = ImageIO.read(new File("data/menu/bookBar.png"));
			playPressed = ImageIO.read(new File("data/menu/playPressed.png"));

			rock = ImageIO.read(new File("data/rock.jpg"));
			grass = ImageIO.read(new File("data/grass.jpg"));
			plain = ImageIO.read(new File("data/plain.jpg"));
			water = ImageIO.read(new File("data/water.jpg"));
			mark = ImageIO.read(new File("data/marked.png"));
			cloud = ImageIO.read(new File("cloud.png"));
			redC = ImageIO.read(new File("data/redC.png"));
			greenC = ImageIO.read(new File("data/greenC.png"));

			derp = ImageIO.read(new File("data/units/derp.png"));
			worker = ImageIO.read(new File("data/units/worker.png"));
			boat = ImageIO.read(new File("data/units/boat.png"));
			axer = ImageIO.read(new File("data/units/axer.png"));
			darter = ImageIO.read(new File("data/units/darter.png"));
			nigga = ImageIO.read(new File("data/units/nigga.png"));
			cluber = ImageIO.read(new File("data/units/cluber.png"));

			cabin = ImageIO.read(new File("data/buildings/cabin.png"));
			cabinConst = ImageIO
					.read(new File("data/buildings/cabinConst.png"));
			main = ImageIO.read(new File("data/buildings/main.png"));
			mainConst = ImageIO.read(new File("data/buildings/mainConst.png"));
			camp = ImageIO.read(new File("data/buildings/camp.png"));
			campConst = ImageIO.read(new File("data/buildings/campConst.png"));
			pile = ImageIO.read(new File("data/buildings/pile.png"));
			pileConst = ImageIO.read(new File("data/buildings/pileConst.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end of loading the images

	private BufferedImage getWater() {
		return water;
	}

	private BufferedImage getPlain() {
		return plain;
	}

	private BufferedImage getRock() {
		return rock;
	}

	private BufferedImage getGrass() {
		return grass;
	}

	private void addMouseListener() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				play = null;
				Console.frame.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (Console.board != null) {
					/*
					 * in the game
					 */
					// System.out.println(e.getX() + " " + e.getY());
					if (e.getY() < getHeight() * 0.72) {
						/*
						 * click upper than bar
						 */
						if (x < e.getX() && y < e.getY()
								&& e.getX() < x + Board.map[0].length * xZoom
								&& e.getY() < y + Board.map.length * yZoom) {
							/*
							 * click on map
							 */
							if (!isFocused) {

								/*
								 * clicked on map when not focused:
								 */
								focusOnPoint(e);
								// end of not focused click
							} else {
								/*
								 * click when focused on the map
								 */
								int newCol = (e.getX() - x) / xZoom;
								int newRow = (e.getY() - y) / yZoom;
								String newId = Board.map[newRow][newCol]
										.objOnBlock();
								if (Judge.isWorker(focusedId)) {
									if (isFocusEnemy) {
										focusOnPoint(e);
									} else if (Judge.isWorker(newId)) {
										focusOnPoint(e);
									} else if (Judge.isBuilding(newId)) {
										// TODO
										Building b = celization
												.findBuilding(newId);
										if (b instanceof Farm
												|| b instanceof GoldMine
												|| b instanceof StoneMine
												|| b instanceof WoodCamp) {
											focusResult = celization
													.workerWorkAt(focusedId,
															newId);
											Connect.addToPackage(focusedId
													+ "+work " + newId);
										} else {
											focusOnPoint(e);
										}
										// end of clicking on an other object
									} else if (newId.equals("empty")) {
										if (focusMenuState.charAt(0) == '0') {
											int i = Integer
													.parseInt(focusMenuState);
											int builType = celization
													.buildingType(focusMenuItems[i]);
											focusResult = celization
													.workerBuild(focusedId,
															builType, newRow,
															newCol);
											Connect.addToPackage(focusedId
													+ "+build " + builType
													+ " " + newRow + " "
													+ newCol);
											if (!focusResult
													.equals("invalid location")) {
												focusMenuState = "idle";
											}
										} else {
											String result = celization
													.workerMove(focusedId,
															newRow, newCol);
											// Connect.addToPackage(focusedId+"+move "+newRow+" "+newCol);
											Connect.addToPackage((focusedId
													+ "+move " + newRow + " " + newCol));
											if (result.equals("invalid id")) {
												Worker wo = Board.derpCElization
														.findWorker(focusedId);
												wo.clearToDoList();

											}

											// if click on unavailable block,
											// then
											// clearToDoList();
										}
										// end of click when focused on worker
									}// end of click cases worker and for newId
										// end of click when focused on worker
								} else if (Judge.isSoldier(focusedId)) {
									if (newId.equals("empty")) {
										String result = celization.soldierMove(
												focusedId, newRow, newCol);
										Connect.addToPackage(focusedId
												+ "+move " + newRow + " "
												+ newCol);
										if (result.equals("invalid id")) {
											Soldier sol = Board.derpCElization
													.findSoldier(focusedId);
											sol.clearToDoList();
										}
									}
									if (Board.isEnemy(newId, celization.ceId())) {
										focusResult = celization.soldierAttack(
												focusedId, newId);
										Connect.addToPackage(focusedId
												+ "+attack " + newId);
									}
									// TODO first!!!!
								} else {
									/*
									 * when was focused on other than worker
									 */
									focusOnPoint(e);
								}
							}// end of click when focused on
						} else {
							/*
							 * out of map
							 */
							isFocused = false;

						}

					} else {
						/*
						 * clicked on bar:
						 */
						int h = getHeight();
						int w = getWidth();
						if (w * 0.113 < e.getX() && e.getX() < w * 0.14
								&& h * 0.836 < e.getY() && e.getY() < h * 0.873) {
							/*
							 * click on next turn
							 */
							nextTurnResult = Judge.nextTurn();
							showNextTurn = true;
							play = playPressed;

						} else if (isFocused) {
							if (Judge.isWorker(focusedId)) {
								if (focusMenuState.equals("idle")) {
									if (getWidth() * 0.222 < e.getX()
											&& e.getX() < getWidth() * 0.25
											&& getHeight() * 0.841 < e.getY()
											&& e.getY() < getHeight() * 0.856) {
										focusMenuState = "build";
									}
									// end of worker idle
								} else if (focusMenuState.equals("build")) {
									if (w * 0.224 < e.getX()
											&& e.getX() < w * 0.28
											&& h * 0.8 < e.getY()
											&& e.getY() < h * 0.98) {
										int zisH = (int) (getHeight() * .79);
										int i = (e.getY() - zisH) / gFont;
										if (i < focusMenuItems.length)
											focusMenuState = "0" + (i);
									}
								}
							} else if (Judge.isBuilding(focusedId)) {
								Building b = celization.findBuilding(focusedId);
								// int type = Integer.parseInt(b.getType());
								if (b instanceof MainBuilding) {
									if (focusMenuState.equals("idle")) {
										/*
										 * click on >Train
										 */
										if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 < e.getY()
												&& e.getY() < h * 0.817) {
											focusMenuState = ">Train";
											focusResult = celization
													.trainWorkerIn(focusedId);

										}

									}
								} else if (b instanceof University) {
									if (focusMenuState.equals("idle")) {
										/*
										 * click on >Train
										 */
										if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 < e.getY()
												&& e.getY() < h * 0.817) {
											focusMenuState = ">Train";
											focusResult = celization
													.trainScholarIn(focusedId);

										} else if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 + gFont < e.getY()
												&& e.getY() < h * 0.817 + gFont) {
											focusMenuState = ">Research";
										}
									} else if (focusMenuState
											.equals(">Research")) {
										if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 < e.getY()
												&& e.getY() < h * 0.98) {
											int zisH = (int) (getHeight() * .79);
											int i = (e.getY() - zisH) / gFont;
											if (i < focusMenuItems.length) {
												int research = Integer
														.parseInt(focusMenuItems[i]);
												focusMenuState = "0" + research;
												focusResult = celization
														.doResearchUni(
																focusedId,
																research);
											}
										}
									}
								} else if (b instanceof Military) {
									if (focusMenuState.equals("idle")) {
										/*
										 * click on >Train
										 */
										if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 < e.getY()
												&& e.getY() < h * 0.817) {
											focusMenuState = ">axer";
											focusResult = celization
													.trainAxerIn(focusedId);

										} else if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 + gFont < e.getY()
												&& e.getY() < h * 0.817 + gFont) {
											focusMenuState = ">darter";
											focusResult = celization
													.trainDarterIn(focusedId);
										}
									}
								} else if (b instanceof Stable) {
									if (focusMenuState.equals("idle")) {
										/*
										 * click on >Train
										 */
										if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 < e.getY()
												&& e.getY() < h * 0.817) {
											focusMenuState = ">nigga";
											focusResult = celization
													.trainNiggaIn(focusedId);

										} else if (w * 0.224 < e.getX()
												&& e.getX() < w * 0.28
												&& h * 0.8 + gFont < e.getY()
												&& e.getY() < h * 0.817 + gFont) {
											focusMenuState = ">cluber";
											focusResult = celization
													.trainCluberIn(focusedId);
										}
									}
								}
								// TODO

								// TODO

							}// end of building info on bar
						}// end of focused bar drawing
					}// end of bar position

				} else {
					/*
					 * clicked on menu:
					 */
					// System.out.println(e.getX()/1365.0 + " " +
					// e.getY()/744.0);
					if (menuFocus.equals("idle")) {
						if (getWidth() * 0.293 < e.getX()
								&& e.getX() < getWidth() * 0.41
								&& getHeight() * 0.407 < e.getY()
								&& e.getY() < getHeight() * 0.443) {
							menuFocus = "newGame";
						}
					} else if (menuFocus.equals("newGame")) {
						lanMode = "none";
						if (getWidth() * 0.295 < e.getX()
								&& e.getX() < getWidth() * 0.404
								&& getHeight() * 0.356 < e.getY()
								&& e.getY() < getHeight() * 0.407) {
							/*
							 * free play
							 */
							Console.mode = "free";
							menuFocus = "map";
						} else if (getWidth() * 0.331 < e.getX()
								&& e.getX() < getWidth() * 0.364
								&& getHeight() * 0.467 < e.getY()
								&& e.getY() < getHeight() * 0.494) {
							/*
							 * AI
							 */
							Console.mode = "AI";
							menuFocus = "map";
						} else if (getWidth() * 0.316 < e.getX()
								&& e.getX() < getWidth() * 0.375
								&& getHeight() * 0.560 < e.getY()
								&& e.getY() < getHeight() * 0.595) {
							/*
							 * LAN
							 */
							Console.mode = "lan";
							menuFocus = "join";
						}
					} else if (menuFocus.equals("map")) {
						if (getWidth() * 0.297 < e.getX()
								&& e.getX() < getWidth() * 0.392
								&& getHeight() * 0.311 < e.getY()
								&& e.getY() < getHeight() * 0.3407) {
							/*
							 * sample map
							 */
							Console.generateSampleMap10x24();
							if (lanMode.equals("server")) {
								menuFocus = "wait";
								Console.frame.repaint();
								Connect.mode = "server";
								Connect con = new Connect();
								con.start();

							} else {
								menuFocus = "hidden";
								Console.start();
							}
						} else if (getWidth() * 0.3 < e.getX()
								&& e.getX() < getWidth() * 0.391
								&& getHeight() * 0.383 < e.getY()
								&& e.getY() < getHeight() * 0.411) {
							/*
							 * insert a map
							 */

							// take the map
							// TODO
							// if(!lan)
							// Console.start();
							menuFocus = "hidden";
						}

					} else if (menuFocus.equals("join")) {
						if (getWidth() * 0.285 < e.getX()
								&& e.getX() < getWidth() * 0.427
								&& getHeight() * 0.369 < e.getY()
								&& e.getY() < getHeight() * 0.396) {
							/*
							 * create a game
							 */
							lanMode = "server";
							menuFocus = "map";
						} else if (getWidth() * 0.290 < e.getX()
								&& e.getX() < getWidth() * 0.419
								&& getHeight() * 0.479 < e.getY()
								&& e.getY() < getHeight() * 0.513) {
							/*
							 * join a server
							 */
							lanMode = "client";
							Connect.ip = JOptionPane
									.showInputDialog("insert server ip:");
							menuFocus = "wait";
							Console.frame.repaint();
							Connect.mode = "client";
							Connect con = new Connect();
							con.start();
							// getConnection TODO
						}
					}
				}

				Console.frame.repaint();
			}// end of pressed

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
	}// end of addMouseListener()

	private void focusOnPoint(MouseEvent e) {
		colFocused = (e.getX() - x) / xZoom;
		rowFocused = (e.getY() - y) / yZoom;
		focusedMsg = "Row = " + (rowFocused + 1) + "  Col = "
				+ (colFocused + 1);
		isFocused = true;
		focusedId = Board.map[rowFocused][colFocused].objOnBlock();
		isFocusEnemy = false;
		if (Board.isEnemy(focusedId, celization.ceId())) {
			isFocusEnemy = true;
		}
		focusMenuState = "idle";
	}

	private void addKeyListener() {
		Console.frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			// @Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == 'a') {
					// TODO
				} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}

			}
		});
	}// end of addKeyListiner()

}
