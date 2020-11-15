package src.rmi.client;

import java.rmi.*;
import src.rmi.server.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VirusWarClient {
    public static void printField(char[][] field) {
        char[] horizonatal = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        char[] vertical = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 1; i < 11; ++i) {
            System.out.print(vertical[i - 1] + " ");
            for(int j = 1; j < 11; ++j) {
                System.out.print(field[j][i] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("  ");
        for (int i = 0; i < 10; ++i) {
            System.out.print(horizonatal[i] + " ");
        }
        System.out.print("\n");
    }

    public static int parseVertical(int num) {
        System.out.println(num);
        if(num < 48 || num > 57)
            return -1;
        else 
            return num - 48;
    }

    public static int parseHorizontal(int ch) {
        System.out.println(ch);
        if(ch < 97 || ch > 106)
            return -1;
        else 
            return ch - 97;
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(6666); 
        VirusWar stub = (VirusWar) registry.lookup("VirusWar"); 
        int id = stub.addNewPlayer();
        if (id == -1) {
            System.out.println("Can't connect, enought players in lobby");
        } else {
            boolean isGameEnded = false;
            do {
                while (!stub.IsReady()) {
                    System.out.println("Waitng for another player ...");
                    Thread.sleep(2000);
                }
                if(!stub.isMyTurn(id))
                    System.out.println("Your opponent's turn, please wait");
                while(!stub.isMyTurn(id)) {
                    Thread.sleep(500);
                }
                if(stub.GetGameEnded()) {
                    System.out.println("Congrats, you won!");
                    break;
                }
                System.out.println("It's your turn!");
                System.out.println("you should choose 3 cells");
                System.out.println("Enter cell coordinats");
                printField(stub.getField());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                int horizon = 0;
                int vertical = 0;

                for (int i = 0; i < 3; ++i) {
                    if(stub.isGameEnded()) {
                        isGameEnded = true;
                        System.out.println("You losed");
                        break;
                    }
                    boolean success = false;
                    System.out.println("format: letternumber");
                    while(true) {
                        String buff = bufferedReader.readLine();
                        if(buff.length() == 2) {
                            horizon = parseHorizontal(buff.charAt(0));
                            vertical = parseVertical(buff.charAt(1));
                            success = (horizon != -1) && (vertical != -1);
                            if(success){
                                if(!stub.canSetCell(horizon, vertical, id)){
                                    System.out.println("Can't set symbol there");
                                } else
                                    break;
                            }
                        }
                        System.out.println("Wrong input\nTry again");
                    }
                    stub.setCell(horizon, vertical, id);
                    printField(stub.getField());
                }
                stub.endTurn();
            } while(!isGameEnded);
        }
    }
}
