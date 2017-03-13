import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

public class ClientTicTac extends SwingGUI {

	TicTacToeGame tictac = null;
	public TicTacPlayer player = null;
	
	public ClientTicTac(String title) {
		super(title);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				Cases[i][j].addActionListener( new ButtonHandler(i, j) );
		this.TitleText.addMouseListener(new MouseAdapter() {
            //override the method
        	public void mouseClicked(MouseEvent e) { //Event pour reset
        		if (TitleText.getText() != "Tic-Tac-Toe" ) {
        			ResetGrid();
        			TitleText.setText("Tic-Tac-Toe");
    				try {
    					tictac.restartGame();
					} catch (RemoteException e1) {
					     System.out.println("Erreur au state" + e);
					}
        		}
        	}
        });
		try {
			Registry rg =LocateRegistry.getRegistry("localhost",6767);
		    tictac = (TicTacToeGame) rg.lookup("titactoegame");	
		    System.out.println("Client connecting.....");
		    player = tictac.newPlayer(1);
		} 
		catch (Exception e) {
		     System.out.println("Erreur à l'accès de la game" + e);
		}
	}
	
	public void selectType(int xo) { //player select if playing "x" or "o" do it at start
		try {
			player = tictac.newPlayer(xo);
		}
		catch (Exception e) {
			System.out.println("Erreur à la création d'un Joueur : " + e);
		}
	}
	
	private class ButtonHandler implements ActionListener {
		int X, Y;
		public ButtonHandler(int x, int y) {
			X = x; Y = y;
		}
		public void actionPerformed (ActionEvent event) {
			play(X,Y);
		}
	}
	
	public void play(int X, int Y) { // call it when click in a tile 
		try {
			tictac.playTurn(X, Y);
		}
		catch (Exception e) {
			System.out.println("Player can't play : " + e);
		}
	}
	
	public void checkGameIssue () { // run it to check game issue
		boolean issue;
		while (true) {
			try {
				GameState state = tictac.getGameState();
				if(state == GameState.CLIENT_TURN) {
					int[]cP = player.getClientPlay();
					Cases[cP[0]][cP[1]].tileChange(1);
				}
				else if (state == GameState.AI_TURN) {
					int[] aiP = player.receiveTurn();
					Cases[aiP[0]][aiP[1]].tileChange(2);
				} 
				else if (state == GameState.ENDED) {
					issue = player.getGameIssue ();
					int casesRes = player.getCasesRes();
					if(issue) {
						this.TitleText.setText("You WIN");
						System.out.println("Player win !");//play win anim
					}
					else if (casesRes > 0){
						this.TitleText.setText("You LOSE");
						System.out.println("Player lose !");//play lose anim
					}
					else
						this.TitleText.setText("Match nul");
						
				}
			}
			catch (RemoteException i) {}
		}
	}

	public static void main(String args[]) {
		ClientTicTac client = new ClientTicTac("TicToeGame");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.pack ();
		client.setLocationRelativeTo(null);
		client.setVisible(true);
		client.checkGameIssue ();
	}
}
	
