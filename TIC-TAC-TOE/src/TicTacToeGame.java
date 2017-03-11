import java.rmi.*;

public interface TicTacToeGame extends Remote {
	public TicTacPlayer newPlayer(char typeXO) throws RemoteException;
	public void playTurn (int tileX, int tileY) throws RemoteException;
	public void changePlayerType(char typeXO, char newtypeXO) throws RemoteException;
	public void restartGame() throws RemoteException;
}
