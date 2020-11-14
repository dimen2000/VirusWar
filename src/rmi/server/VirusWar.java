package src.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirusWar extends Remote {
    int addNewPlayer() throws RemoteException;
    char [][] getField() throws RemoteException;
    boolean IsReady() throws RemoteException;
    void setCell(int x, int y, int id) throws RemoteException;
    boolean isMyTurn(int myid) throws RemoteException;
    boolean isGameEnded() throws RemoteException;
    boolean canSetCell(int x, int y, int id) throws RemoteException;
    boolean canSetXCell(int x, int y) throws RemoteException;
    boolean canSetOCell(int x, int y) throws RemoteException;
    void endTurn() throws RemoteException;
}