import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientTicTac extends SwingGUI {

	TicTacToeGame tictac = null;
	public TicTacPlayer player = null;
	public int clientV, aiV;
	
	public ClientTicTac(String title) {
		super(title);
		
		// Ajoute sur chaque Case un ActionListener qui envoie l'action du joueur au serveur
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				Cases[i][j].addActionListener( new ButtonHandler(i, j) );
		
		// Ajoute un Listener sur le texte du "Restart" pour recommencer la game 
		this.TitleText.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) { //Event pour reset
        		if (TitleText.getText() != "Tic-Tac-Toe" ) {
        			ResetGrid();
    				try {
    					tictac.restartGame();
					} catch (RemoteException e1) {
					     System.out.println("Erreur au state" + e);
					}
        			TitleText.setText("Tic-Tac-Toe");
        		}
        	}
        });
		
		// Cree le stub à partir du squelette TicToeToeGame sur le serveur
		try {
			Registry rg =LocateRegistry.getRegistry("localhost",6767);
		    tictac = (TicTacToeGame) rg.lookup("titactoegame");	
		    System.out.println("Client connecting.....");
		} 
		catch (Exception e) {
		     System.out.println("Erreur à l'accès de la game" + e);
		}
	}
	
	// Permet au joueur de choisir entre jouer 'X'ou 'O'
	public void selectType() {
		Object[] options = {"X", "O"};
		if (JOptionPane.showOptionDialog(this, 
                "Que voulez-vous jouer ? (X ou O)", 
                "Choix de Tic ou Tac", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, null) == JOptionPane.OK_OPTION) {
			clientV = 1; aiV = 2;
		}
		else {
			clientV = 2; aiV = 1;
		}
		try {
			player = tictac.newPlayer(clientV);
		}
		catch (Exception e) {
			System.out.println("Erreur à la création d'un Joueur : " + e);
		}
	}
	
	// Class Handler pour chaque Case de la grille
	private class ButtonHandler implements ActionListener {
		int X, Y;
		public ButtonHandler(int x, int y) {
			X = x; Y = y;
		}
		public void actionPerformed (ActionEvent event) {
			play(X,Y);
		}
	}
	
	// Appelle la fonction playTurn(int X, int Y) du stub TicTacToeGame
	public void play(int X, int Y) {
		try {
			tictac.playTurn(X, Y);
		}
		catch (Exception e) {
			System.out.println("Player can't play : " + e);
		}
	}
	
	// Boucle true pour vérifier l'état de la game
	public void checkGameIssue () {
		boolean issue;
		while (true) {
			try {
				GameState state = tictac.getGameState();
				if(state == GameState.CLIENT_TURN) { 		// Si c'est le tour du joueur
					int[]cP = player.getClientPlay();		// Recupère la Case sélectionnée
					Cases[cP[0]][cP[1]].tileChange(clientV);		// Change la case en X 
				}
				else if (state == GameState.AI_TURN) { 		// Si c'est le tour de l'IA
					int[] aiP = player.getAIPlay();			// Recupère la Case sélectionnée
					Cases[aiP[0]][aiP[1]].tileChange(aiV);	// Change la case en O
				} 
				else if (state == GameState.ENDED) {		// Si c'est la game est finie
					issue = player.getGameIssue ();			// Vérifie si le client a gagné
					int casesRes = tictac.getCasesRes();
					if(issue) {
						state = tictac.getGameState(); //Pour éviter un second appel au mauvais tick
						if (state == GameState.ENDED)
							this.TitleText.setText("You WIN click: Restart");
						System.out.println("Player win !");//play win anim
					}
					else if (casesRes > 0){
						state = tictac.getGameState(); //Pour éviter un second appel au mauvais tick
						if (state == GameState.ENDED)
							this.TitleText.setText("You LOSE click: Restart");
						System.out.println("Player lose !");//play lose anim
					}
					else {
						state = tictac.getGameState(); //Pour éviter un second appel au mauvais tick
						if (state == GameState.ENDED)
							this.TitleText.setText("Match nul click: Restart");						
					}
				}
			}
			catch (RemoteException i) {}
		}
	}
	

	public static void main(String args[]) {
		ClientTicTac client = new ClientTicTac("TicToeGame"); // Initialise la fenêtre de jeu
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.pack ();
		client.setLocationRelativeTo(null);
		client.setVisible(true);
		client.checkGameIssue ();
	}
}
	
