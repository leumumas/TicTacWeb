import java.awt.*;

import javax.swing.JFrame;


public class MainGame {
	public static ServerGame Server;
	public static ClientTicTac Game;
	
	public static void main (String[] args)
	{
	    String[] arguments = new String[] {"Start"};
		Server = new ServerGame();
		Game = new ClientTicTac("TicToeGame");
		Game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game.pack ();
		Game.setLocationRelativeTo(null);
		Game.setVisible(true);
		Game.selectType();
		Game.checkGameIssue ();
	}
}
