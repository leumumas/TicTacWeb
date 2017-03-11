import java.rmi.server.*;
import java.rmi.*;
import java.util.Hashtable;

public class TicTacToeGameImpl extends UnicastRemoteObject implements TicTacToeGame {

	private static final long serialVersionUID = 1L;
	
	private char[][] grid;
	
	public TicTacPlayer client = null, ai = null;

	public TicTacToeGameImpl() throws RemoteException {}
	
	@Override
	public TicTacPlayer newPlayer(char typeXO) throws RemoteException {
		char aiType;
		if (typeXO == 'X') {
			aiType = 'O';
		}
		else if (typeXO == 'O') {
			aiType = 'O';
		}
		else {
			System.out.println("Impossible de commencer le jeu !");
			throw new RemoteException();
		}
		System.out.println("New Game...");
		client = new TicTacPlayer(typeXO);
		ai = new TicTacPlayer(aiType);
		
		grid = new char[3][3];
		
		return client;
	}
	
	@Override
	public void playTurn(int tileX, int tileY) throws RemoteException {
		if(client == null)
			throw new RemoteException() ;
			
		if (checkRow(tileX, tileY) == 3 || checkColumn(tileX, tileY) == 3 || checkDiagonal(tileX, tileY) == 3) {
			client.youWin();
		}
		else {
			/*ai play return int[2] tileX, tileY
			client.receiveTurn(aiTile[0], aiTile[1]);
			if (checkRow(aiTile[0], aiTile[1]) == 3 || checkColumn(aiTile[0], aiTile[1]) == 3 || checkDiagonal(aiTile[0], aiTile[1]) == 3) {
				client.youLose();
			}*/
		}
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
	public void changePlayerType(char typeXO, char newtypeXO) throws RemoteException {
 	      if (client == null) 
 	    	  throw new RemoteException() ;
 	      
 	    client = new TicTacPlayer(newtypeXO);
 	    ai  = new TicTacPlayer(typeXO);
	}
	
	@Override
	public void restartGame() throws RemoteException {

	}	

}
