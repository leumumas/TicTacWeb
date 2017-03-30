import java.rmi.*;

public interface TicTacToeGame extends Remote {
	public TicTacPlayer newPlayer(int typeXO) throws RemoteException, InterruptedException;
	public void playTurn(int tileX, int tileY) throws RemoteException, InterruptedException;
	public void changePlayerType(int typeXO, int newtypeXO) throws RemoteException;
	public void restartGame() throws RemoteException;
	public void gameIssue(boolean issue) throws RemoteException;
	public GameState getGameState() throws RemoteException;
	public int getCasesRes() throws RemoteException;
}
