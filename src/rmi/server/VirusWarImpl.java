package src.rmi.server;

import java.rmi.*;

public class VirusWarImpl implements VirusWar {
    private char playerCount = 0;
    private char [][] field;
    private boolean [][] checkedfield;
    int fieldsize_x = 12;
    int fieldsize_y = 12;
    int currentTurn;

    public VirusWarImpl() {
        currentTurn = 0;
        field = new char[fieldsize_x][fieldsize_y];
        checkedfield = new boolean[fieldsize_x][fieldsize_y];
        for(int x = 0; x < fieldsize_x; ++x)
            for(int y = 0; y < fieldsize_y; ++y) {
                field[x][y] = '_';
                checkedfield[x][y] = false;
            }
    }

    public char[][] getField() throws RemoteException {
        return field;
    }

    public int addNewPlayer() throws RemoteException {
        if(playerCount < 2) {
            playerCount++;
            System.out.println("New player connected");
            return playerCount - 1;
        } else { 
            System.out.println("Enought players in lobby");
            return -1;
        }
    }

    public boolean IsReady() throws RemoteException {
        if(playerCount == 2) {
            return true;
        }
        return false;
    }

    public void setCell(int x, int y, int id) throws RemoteException {
        switch (id) {
            case(0):
                if(field[x + 1][y + 1] == '_')
                    field[x + 1][y + 1] = 'x';
                else 
                    field[x + 1][y + 1] = '@';
                break;
            case (1):
                if(field[x + 1][y + 1] == '_')
                    field[x + 1][y + 1] = 'o';
                else 
                    field[x + 1][y + 1] = '*';
                break;
        }
    }

    public boolean isMyTurn(int myid) throws RemoteException {
        if(myid == currentTurn)
            return true;
        return false;
    }

    public void endTurn() throws RemoteException {
        currentTurn  = (currentTurn + 1) % 2;
    }

    public boolean isGameEnded() throws RemoteException {
        for(int i = 0; i < fieldsize_y - 2; ++i)
            for(int j = 0; j < fieldsize_x - 2; ++j) {
                if(canSetCell(j, i, currentTurn))
                    return false;
            }
        return true;
    }

    public boolean canSetCell(int x, int y, int id) throws RemoteException {
        switch (id) {
            case(0):
                return  canSetXCell(x, y);
            case (1):
                return  canSetOCell(x, y);
        }
        return false;
    }

    private void freeCheckedField() {
        for(int i = 0; i < fieldsize_x; ++i)
            for(int j = 0; j < fieldsize_y; ++j)
                checkedfield[i][j] = false;
    }

    private boolean canSetXCellRecursive(int x, int y) {
        checkedfield[x][y] = true;
        for(int i = -1; i < 2; ++i)
                for(int j = -1; j < 2; j++) {
                    if(field[x + i][y + j] == 'x')
                        return true;
                    if(field[x + i][y + j] == '@' && !checkedfield[x + i][x + i])
                        if(canSetOCellRecursive(x + i, y + j))
                            return true;
                }
        return false;
    }

    public boolean canSetXCell(int x, int y) throws RemoteException {
        int dx = x + 1;
        int dy = y + 1;
        if((x == 0) && (y == 0) && (field[dx][dy] == '_'))
            return true;
        if((field[dx][dy] == '_' || field[dx][dy] == 'o') 
        && (dx > -1) && (dx < 10) && (dy > -1) && (dy < 10)) {
            for(int i = -1; i < 2; ++i)
                for(int j = -1; j < 2; j++) {
                    if(field[dx + i][dy + j] == 'x') {
                        return true;
                    }
                    if(field[dx + i][dy + j] == '@') 
                        if(canSetXCellRecursive(dx + i, dy + j)) {
                            freeCheckedField();
                            return true;
                        }
                }
        }
        freeCheckedField();
        return false;
    }

    private boolean canSetOCellRecursive(int x, int y) {
        checkedfield[x][y] = true;
        for(int i = -1; i < 2; ++i)
                for(int j = -1; j < 2; j++) {
                    if(field[x + i][y + j] == 'o')
                        return true;
                    if(field[x + i][y + j] == '*' && !checkedfield[x + i][x + i])
                        if(canSetXCellRecursive(x + i, y + j))
                            return true;
                }
        return false;
    }

    public boolean canSetOCell(int x, int y) throws RemoteException {
        int dx = x + 1;
        int dy = y + 1;
        if((x == fieldsize_x - 3) && (y == fieldsize_y - 3) && (field[dx][dy] == '_'))
            return true;
        if((field[dx][dy] == '_' || field[dx][dy] == 'x') 
        && (dx > -1) && (dx < 10) && (dy > -1) && (dy < 10)) {
            for(int i = -1; i < 2; ++i)
                for(int j = -1; j < 2; j++) {
                    if(field[dx + i][dy + j] == 'o') {
                        return true;
                    }
                    if(field[dx + i][dy + j] == '*') 
                        if(canSetOCellRecursive(dx + i, dy + j)) {
                            freeCheckedField();
                            return true;
                        }
                }
        }
        freeCheckedField();
        return false;
    }
}