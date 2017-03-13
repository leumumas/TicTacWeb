import java.rmi.*;

public interface TicTacPlayer extends Remote {
	public void setTypeXO(int xo) throws RemoteException;
	public boolean getGameIssue() throws RemoteException;
	public int[] receiveTurn() throws RemoteException;
}
