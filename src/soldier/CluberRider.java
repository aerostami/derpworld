package soldier;

import game.Board;

public class CluberRider extends Soldier{

	public static final int staticPointOnInfantry = 50;
	public static final int staticPointOnHorseman = 10;
	public static final int staticPointOnDefence = 10;
	public static final int staticPointOnAttack = 90;
	public static final int staticHealth = 300;
	
	public static int clubersNum;
	
	
	public CluberRider(int row, int col, boolean[][] isDiscovered, int ceId) {
		super(row, col, isDiscovered, ceId, staticHealth, staticPointOnInfantry, staticPointOnHorseman,
				staticPointOnAttack, staticPointOnDefence);
		
		clubersNum+=1;
		id="sol_clubber_"+clubersNum+"_"+ceId;
		Board.map[row][col].setObjOnBlock(id);
		
		// TODO Auto-generated constructor stub
	}

}
