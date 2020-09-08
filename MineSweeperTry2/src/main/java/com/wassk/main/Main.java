package com.wassk.main;

import com.wassk.functionality.GameFunc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Choose Level Difficulty:" +
//                "\n 1 - Beginner (9x9 Board with 10 mines" +
//                "\n 2 - Intermediate (16x16 Board with 40 mines" +
//                "\n 3 - Advanced (24x24 Board with 99 mines");
        GameFunc board = new GameFunc(scanner.nextInt());
//        board.displayUserInformation();
        while (!board.isCompletelyExplored() && !board.isDestroyed()) {
          board.display();
            board.getUserInput(scanner);
        }

        // game over
        board.display();
        if(board.isCompletelyExplored()) {
            System.out.println("You're the Champion !");
        } else {
            System.out.println("BOOOM !");
        }
        scanner.close();
    }
}
