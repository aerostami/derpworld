package soldier;

import game.Board;

public class Axer extends Soldier{
	public static final int staticPointOnInfantry = 60;
	public static final int staticPointOnHorseman = 10;
	public static final int staticPointOnDefence = 60;
	public static final int staticPointOnAttack = 20;
	public static final int staticHealth = 200;
	
	public static int axersNum;
	
	public Axer(int row, int col, boolean[][] isDiscovered, int ceId) {
		super(row, col, isDiscovered, ceId, staticHealth, staticPointOnInfantry, staticPointOnHorseman,
				staticPointOnAttack, staticPointOnDefence);
		
		axersNum+=1;
		id="sol_axer_"+axersNum+"_"+ceId;
		Board.map[row][col].setObjOnBlock(id);
		// TODO Auto-generated constructor stub
	}

}
