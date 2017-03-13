import java.rmi.*;

public interface TicTacPlayer extends Remote {
	public void setTypeXO(int xo) throws RemoteException;
	public boolean getGameIssue() throws RemoteException;
	public int getCasesRes() throws RemoteException;
	public int[] getAIPlay() throws RemoteException;
	public int[] getClientPlay() throws RemoteException;
}
