import java.rmi.server.*;
import java.rmi.*;

public class TicTacToeGameImpl extends UnicastRemoteObject implements TicTacToeGame {

	private static final long serialVersionUID = 1L;
	
	private GameState gameState;
	
	private int[][] grid;
	
	public TicTacPlayerImpl client = null, ai = null;

	public TicTacToeGameImpl() throws RemoteException {}
	
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
		ai = new TicTacPlayerImpl(aiType);
		
		grid = new int[3][3];
		gameState = GameState.PLAYING; 
		
		return client;
	}
	
	@Override
	public void playTurn(int tileX, int tileY) throws RemoteException {
		if(client == null)
			throw new RemoteException() ;
		
		int clientTypeXO = client.getTypeXO();
		grid[tileX][tileY] = clientTypeXO;
		gameState = GameState.CLIENT_TURN; 
		if (checkRow(tileX, tileY) == 3 || checkColumn(tileX, tileY) == 3 || checkDiagonal(tileX, tileY) == 3) {
			gameIssue(true);
		}
		else {
			int aiTypeXO = ai.getTypeXO();
			int[] aiPlay =  getRandomInt(tileX, tileY);
			grid[aiPlay[0]][aiPlay[1]] = aiTypeXO;
			gameState = GameState.AI_TURN; 
			client.setAiPlay(aiPlay);
			if (checkRow(aiPlay[0], aiPlay[1]) == 3 || checkColumn(aiPlay[0], aiPlay[1]) == 3 || checkDiagonal(aiPlay[0], aiPlay[1]) == 3) {
				gameIssue(false);
			}
		}
		System.out.println("Working amen !");
	}
	
	private int[] getRandomInt(int eX, int eY) {
		int[] r = new int[2];
		r[0] = (int)(Math.random() * 3); 
		while (r[0] != eX) {
			r[0] = (int)(Math.random() * 3);
		}
		
		r[1] = (int)(Math.random() * 3); 
		while (r[1] != eY) {
			r[1] = (int)(Math.random() * 3);
		}
		
		return r;
	}

	public int checkRow(int tileX, int tileY) {
		int i, buf = 0;
		for (i = 0; i < grid[tileX].length; i++) {
			if (grid[tileX][i] == grid[tileX][tileY])
				buf++;
		}
		return buf;		
	}
	
	public int checkColumn(int tileX, int tileY) {
		int i, buf = 0;
		for (i = 0; i < grid[tileY].length; i++) {
			if (grid[i][tileY] == grid[tileX][tileY])
				buf++;
		}
		return buf;		
	}

	public int checkDiagonal(int tileX, int tileY) {
		int i, j = 0, buf = 0;
		for (i = 0; i < grid[tileY].length; i++) {
			if (grid[i][j] == grid[tileX][tileY])
				buf++;
			j++;
		}
		return buf;		
	}
	
	@Override
	public void gameIssue(boolean issue) throws RemoteException {
		gameState = GameState.ENDED; 
		client.setPlayerIssue(issue);
		ai.setPlayerIssue(!issue);
	}
	
	@Override
	public void changePlayerType(int typeXO, int newtypeXO) throws RemoteException {
 	      if (client == null) 
 	    	  throw new RemoteException() ;
 	      
 	    client.setTypeXO(newtypeXO);
 	    ai.setTypeXO(typeXO);
	}
	
	@Override
	public void restartGame() throws RemoteException {

	}	
	
	@Override
	public GameState getGameState() {
		return gameState;
	}

}
