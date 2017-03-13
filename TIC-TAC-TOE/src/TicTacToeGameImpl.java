import java.rmi.server.*;
import java.util.concurrent.TimeUnit;
import java.rmi.*;

public class TicTacToeGameImpl extends UnicastRemoteObject implements TicTacToeGame {

	private static final long serialVersionUID = 1L;
	
	private GameState gameState;
	
	private int[][] grid = new int[3][3];
	
	public TicTacPlayerImpl client = null;
	public AIPlayerMinimax ai = null;
	private int casesRes;

	// Initialise la grid du serveur
	public TicTacToeGameImpl() throws RemoteException {
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 3; col++)
				grid[row][col] = 0;
	}
	
	// Crée un nouveau joueur et un nouvel IA donc une nouvelle game
	@Override
	public TicTacPlayer newPlayer(int typeXO) throws RemoteException {
		int aiType;
		if (typeXO == 1) {
			aiType = 2;
		}
		else if (typeXO == 2) {
			aiType = 1;
		}
		else {
			System.out.println("Impossible de commencer le jeu !");
			throw new RemoteException();
		}
		System.out.println("New Game...");
		client = new TicTacPlayerImpl(typeXO);
		ai = new AIPlayerMinimax(grid, aiType);
		
		gameState = GameState.PLAYING; 
		
		return client;
	}
	
	// Executé à chaque fois que le client joue
	@Override
	public void playTurn(int tileX, int tileY) throws RemoteException, InterruptedException {
		if(client == null)
			throw new RemoteException() ;
	
		if(grid[tileX][tileY] == 0 && gameState != GameState.ENDED) {
			int clientTypeXO = client.getTypeXO();
			grid[tileX][tileY] = clientTypeXO;
			gameState = GameState.CLIENT_TURN; 
			client.setClientPlay(new int[] {tileX, tileY}); // Renvoie le move du client pour mettre à jour l'affichage
			TimeUnit.MILLISECONDS.sleep(50);				// Attend quelques ms que l'IA joue
			casesRes = 9;
			for(int row = 0; row < 3; row++)
				for(int col = 0; col < 3; col++) {
					if (grid[row][col] !=0)
						casesRes--;
				}
			if (casesRes <= 0) { 						//Vérifie, si personne n'as gagné, un match nul
				gameIssue(false);
														// Vérifie si le joueur a gagné, si oui envoie à l'IA qu'il a perdu
			} else if (checkRow(tileX, tileY) == 3 || checkColumn(tileX, tileY) == 3 || checkDiagonal(tileX, tileY) == 3) {
				gameIssue(true);
			}
			else {
				ai.UpdateGrid(grid); 					// Met à jour la grid de l'IA pour lui permettre de faire le bon choix
				int aiTypeXO = ai.getTypeXO();
				int[] aiPlay = ai.Move();
				grid[aiPlay[0]][aiPlay[1]] = aiTypeXO;
				gameState = GameState.AI_TURN; 
				client.setAiPlay(aiPlay); 				// Renvoie le move de l'IA pour mettre à jour l'affichage
				TimeUnit.MILLISECONDS.sleep(50);
														// Vérifie si l'IA a gagné, si oui envoie au joueur qu'il a perdu
				if (checkRow(aiPlay[0], aiPlay[1]) == 3 || checkColumn(aiPlay[0], aiPlay[1]) == 3 || checkDiagonal(aiPlay[0], aiPlay[1]) == 3) {
					gameIssue(false);
				}
			}
		}
	}

	// Vérifie la rangée de la Case sélectionnée 
	public int checkRow(int tileX, int tileY) {
		int i, buf = 0;
		for (i = 0; i < grid[tileX].length; i++) {
			if (grid[tileX][i] == grid[tileX][tileY])
				buf++;
		}
		return buf;		
	}
	
	// Vérifie la colonne de la Case sélectionnée 
	public int checkColumn(int tileX, int tileY) {
		int i, buf = 0;
		for (i = 0; i < grid[tileY].length; i++) {
			if (grid[i][tileY] == grid[tileX][tileY])
				buf++;
		}
		return buf;		
	}

	// Vérifie les diagonales de la Case sélectionnée 
	public int checkDiagonal(int tileX, int tileY) {
		int i, j = 0, buf = 0;
		for (i = 0; i < grid[tileY].length; i++) {
			if (grid[i][j] == grid[tileX][tileY])
				buf++;
			j++;
		}
		
		if(buf < 3) {
			buf = 0;
			for(i = 0, j = 2; i < 3; i++, j--)
				if(grid[i][j] == grid[tileX][tileY])
					buf++;
		}
			
		return buf;		
	}
	
	// Change l'issue de la game pour le joueur et l'IA, change l'état de la game
	@Override
	public void gameIssue(boolean issue) throws RemoteException {
		gameState = GameState.ENDED; 
		client.setPlayerIssue(issue);
		ai.setPlayerIssue(!issue);
	}
	
	// Permet au joueur de changer entre 'X' et 'O'
	@Override
	public void changePlayerType(int typeXO, int newtypeXO) throws RemoteException {
 	      if (client == null) 
 	    	  throw new RemoteException() ;
 	      
 	    client.setTypeXO(newtypeXO);
 	    ai.setTypeXO(typeXO);
	}
	
	// Réinitialise la grille de jeu et change l'état de la game
	@Override
	public void restartGame() throws RemoteException {
		gameState = GameState.PLAYING; 
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 3; col++)
				grid[row][col] = 0;		
	}	
	
	// Retourne l'état de la game
	@Override
	public GameState getGameState() {
		return gameState;
	}

	// Retourne le nombre de Cases restantes
	@Override
	public int getCasesRes() throws RemoteException {
		return casesRes;
	}
}