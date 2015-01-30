package soldier;

import game.Board;

public class Darter extends Soldier{
	public static final int staticPointOnInfantry = 30;
	public static final int staticPointOnHorseman = 80;
	public static final int staticPointOnDefence = 60;
	public static final int staticPointOnAttack = 20;
	public static final int staticHealth = 200;
	
	public static int dartersNum;
	
	public Darter(int row, int col, boolean[][] isDiscovered, int ceId) {
		super(row, col, isDiscovered, ceId, staticHealth, staticPointOnInfantry, staticPointOnHorseman,
				staticPointOnAttack, staticPointOnDefence);
		
		dartersNum+=1;
		id="sol_darter_"+dartersNum+"_"+ceId;
		Board.map[row][col].setObjOnBlock(id);
		
		// TODO Auto-generated constructor stub
	}

}
