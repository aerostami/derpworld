package console;

import game.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Console {
	static JFrame frame;
	static Window window;
	static Board board;
	public static String mode;
	public static char[][] map;
	public static int[][] goldMap;
	public static int[][] stoneMap;
	public static int[][] lumberMap;
	public static int[][] foodMap;
	
	public static void main(String[] args) {
		/*
		 * making the main frame and window:
		 */
		Console.intitialize();

	}// end of main
	
	/*
	 * starts a new game
	 */
	public static void start( ) {
		// TODO reset everything
		board = judge.Judge.strat(map, goldMap, stoneMap, lumberMap, foodMap, mode);
		window.celization = Board.derpCElization;
		frame.repaint();
		
	}

	private static void intitialize() {
		/*
		 * Main Frame:
		 */
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
//		frame.setBounds(0, 0, 1366, 540); //TODO remove this later :D
		// end of initializing the frame

		// *****************************
		/*
		 * The Menu:
		 */
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("File");

		JMenu newGame = new JMenu("New Game");
		JMenuItem sample1 = new JMenuItem("Sample 1");
		sample1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				char[][] zisMap = { { 'W', 'W', 'W', 'P', 'P', 'P', 'P', 'P' },
						{ 'W', 'W', 'W', 'P', 'P', 'M', 'M', 'P' },
						{ 'W', 'W', 'P', 'P', 'P', 'M', 'M', 'P' },
						{ 'W', 'W', 'P', 'P', 'M', 'M', 'P', 'P' },
						{ 'W', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
						{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
						{ 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
						{ 'P', 'P', 'P', 'P', 'P', 'F', 'F', 'F' } };

				int[][] zgoldMap = { { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 7, 8, 0 }, { 0, 0, 0, 0, 0, 9, 8, 0 },
						{ 0, 0, 0, 0, 8, 6, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0 } };

				int[][] zstoneMap = { { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 9, 9, 0 }, { 0, 0, 0, 0, 0, 6, 8, 0 },
						{ 0, 0, 0, 0, 9, 6, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0 } };

				int[][] zlumberMap = { { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 0, 6, 9, 9 } };

				int[][] zfoodMap = { { 7, 5, 6, 6, 5, 7, 2, 4 },
						{ 2, 9, 8, 4, 5, 0, 0, 6 }, { 6, 6, 4, 2, 1, 0, 0, 3 },
						{ 4, 8, 8, 4, 0, 0, 5, 1 }, { 1, 3, 2, 3, 5, 9, 8, 6 },
						{ 0, 7, 4, 5, 8, 4, 4, 7 }, { 3, 8, 9, 5, 7, 0, 2, 1 },
						{ 2, 3, 4, 5, 6, 0, 0, 0 } };
				map = zisMap;
				goldMap = zgoldMap;
				foodMap = zfoodMap;
				lumberMap = zlumberMap;
				stoneMap = zstoneMap;
				mode = "free";
				start();
				// TODO Auto-generated method stub

			}
		});

		JMenuItem jmi1 = new JMenuItem("Save");
		JMenuItem close = new JMenuItem("close");
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		newGame.add(sample1);
		jm.add(newGame);
		jm.add(jmi1);
		jm.add(close);
		jmb.add(jm);
		//////////////////////////////////////////////////////end of file
		
		JMenu action = new JMenu("Action");
		JMenu zoom = new JMenu("zoom");
		JMenuItem zoomIn = new JMenuItem("zoon in");
		JMenuItem zoomOut = new JMenuItem("zoon out");
		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.xZoom+=5;
				window.yZoom+=5;
				frame.repaint();
			}
		});
		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.xZoom-=5;
				window.yZoom-=5;
				frame.repaint();
			}
		});
		zoom.add(zoomIn);
		zoom.add(zoomOut);
		action.add(zoom);
		//end of zoom
		JMenu move = new JMenu("move");
		JMenuItem moveR = new JMenuItem("move right");
		JMenuItem moveL = new JMenuItem("move left");
		JMenuItem moveU = new JMenuItem("move up");
		JMenuItem moveD = new JMenuItem("move down");
		
		moveR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.x+=20;
				frame.repaint();
			}
		});
		
		moveL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.x-=20;
				frame.repaint();
			}
		});moveU.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.y-=20;
				frame.repaint();
			}
		});moveD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.y+=20;
				frame.repaint();
			}
		});
		move.add(moveR);
		move.add(moveL);
		move.add(moveU);
		move.add(moveD);
		action.add(move);

		jmb.add(action);
		//////////////////////////////////////end of action

		frame.setJMenuBar(jmb);
		// end of menu section

		// *******************
		/*
		 * Main Panel:
		 */
		window = new Window();
		frame.add(window);
		// end of adding the main panel

		frame.setVisible(true);
	}
	public static void generateSampleMap10x24(){
		map = new char[10][24];
		/*
		 * generating a sample map[][]
		 */
		for(int i = 0 ; i <24; i+=1){
			if(i<7|| 16<i)
				map[0][i]= 'P';
			else map[0][i]= 'F';
		}
		for(int i = 0 ; i <24; i+=1){
			if((i<7)|| (16<i)){
				map[1][i]= 'P';
				map[2][i]= 'P';
			}
			else if(i<10|| 13<i){
				map[1][i]= 'F';
				map[2][i]= 'F';
			}
			else {
				map[1][i]= 'M';
				map[2][i]= 'M';
			}
		}
		for(int i = 0 ; i <24; i+=1){
			if(i<11|| 12<i)
				map[3][i]= 'P';
			else map[3][i]= 'M';
		}
		for(int i = 4; i<7;i+=1)
			for(int j=0;j<24;j+=1)
				map[i][j]='P';
		for(int i = 0 ; i <24; i+=1){
			if(i<5|| 19<i)
				map[7][i]= 'W';
			else map[7][i]= 'P';
		}
		for(int i =0 ; i<24;i+=1){
			map[8][i]= 'W';
			map[9][i]= 'W';
		}
		// map[][] is initialized!
		goldMap = new int[10][24];
		stoneMap = new int[10][24];
		lumberMap = new int[10][24];
		foodMap =new int[10][24];
		for (int i = 0 ; i < 10 ; i+=1 )
			for(int j =0 ; j<24;j+=1){
				goldMap[i][j]=0;
				stoneMap[i][j]=0;
				lumberMap[i][j]=0;
				foodMap[i][j]=0;
				if (map[i][j]=='P'){
					foodMap[i][j]=7;
				}else if (map[i][j]=='F'){
					lumberMap[i][j]=9;
				}else if (map[i][j]=='M'){
					stoneMap[i][j]=9;
					goldMap[i][j]=9;
				}else{
					foodMap[i][j]=9;
				}
			}
		
	}
}
