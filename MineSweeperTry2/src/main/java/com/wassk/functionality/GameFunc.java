package com.wassk.functionality;

import java.util.Random;
import java.util.Scanner;

public class GameFunc {
    private int maxSide;
    private GameBoard[][] field;
    private int minedFields;
    private int exploredFields;
    private int maxMines;
    private boolean destroyed;
    private int numberOfMinedFieldsAround;
    private Random random = new Random();

    public GameFunc(int level) {
        if (level == 1) {
            maxSide = 9;
            maxMines = 10;
        } else if (level == 2) {
            maxSide = 16;
            maxMines = 40;
        } else if (level == 3) {
            maxSide = 24;
            maxMines = 99;
        }

//        else {
////            System.out.println("Wrong Level has been selected, please use 1, 2 or 3");
//            return;
//        }
//     this.maxSide = maxSide;
        // Creating the Board
        field = new GameBoard[maxSide][maxSide];
        for (int i = 0; i < maxSide; i++) {
            for (int j = 0; j < maxSide; j++) {
                field[i][j] = new GameBoard(false);
            }
        }
        addMines();
    }

    public void addMines() {
        while (minedFields <= maxMines) {
            int line = random.nextInt(maxSide);
            int col = random.nextInt(maxSide);
            if (field[line][col] != new GameBoard(true) && !field[line][col].isExplored()) {
                field[line][col] = new GameBoard(true);
                minedFields++;
            }
        }
    }

    public boolean isCompletelyExplored() {
        return maxSide * maxSide - minedFields - exploredFields == 0;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

//    public void displayUserInformation() {
//        System.out.println("x: line number");
//        System.out.println("y: column number\n");
//    }

    public boolean explore(int line, int col) {
        field[line][col].explored();
        numberOfMinedFieldsAround = 0;

        int[][] FieldsAround = getNearbyFields(line, col);
        // check how many fields around are mined
        for (int i = 0; i < FieldsAround.length; i++) {
            if (field[FieldsAround[i][0]][FieldsAround[i][1]].isMined()) {
                numberOfMinedFieldsAround++;
            }
        }
        if (field[line][col].isMined() && exploredFields == 0) {
            destroyed = false;
            minedFields--;
            addMines();
            field[line][col].setAppearance(String.valueOf(
                    numberOfMinedFieldsAround));
            return false;

        } else if (field[line][col].isMined()) {
            destroyed = true;
            return true;
        }
        exploredFields++;
        // Fields Around will be discovered if not mined.
        if (numberOfMinedFieldsAround == 0) {
            field[line][col].setAppearance("0");

            // discover neighbor fields
            for (int i = 0; i < FieldsAround.length; i++) {
                if (!field[FieldsAround[i][0]][FieldsAround[i][1]]
                        .isExplored()) {
                    explore(FieldsAround[i][0], FieldsAround[i][1]);
                }
            }
        } else {
            field[line][col].setAppearance(String.valueOf(
                    numberOfMinedFieldsAround));
            System.out.print("");
        }
        return false;
    }
//  mark a field as a mine.
//    public void mark(int line, int col) {
//        fields[line][col].setAppearance("*");
//    }

    private int[][] getNearbyFields(int line, int col) {
        int[][] allCoordinates = new int[][]{{line - 1, col - 1}, {line - 1, col},
                {line - 1, col + 1}, {line, col - 1}, {line, col + 1}, {line + 1, col - 1}, {line + 1, col}, {line + 1, col + 1}};
        int numberOfValidCoordinates = 0;
        int[] indices = new int[8];
        int indicesIndex = 0;
        for (int i = 0; i < 8; i++) {
            if ((allCoordinates[i][0] > -1 && allCoordinates[i][0] <
                    maxSide) && (allCoordinates[i][1] > -1 && allCoordinates[i][1] <
                    maxSide)) {
                numberOfValidCoordinates++;
                indices[indicesIndex] = i;
                indicesIndex++;
            }
        }

        // add the valid coordinates to a list and return it
        int[][] validCoordinates = new int[numberOfValidCoordinates][2];
        for (int i = 0; i < numberOfValidCoordinates; i++) {
            validCoordinates[i] = allCoordinates[indices[i]];
        }
        return validCoordinates;
    }

    // asks the user which field he wants to explore
    public void getUserInput(Scanner scanner) {
//        System.out.print("Select 'E' to Explore the Board " +
//                "\n Select 'M' to mark a mine");
//        String choice = scanner.next();
//        System.out.print("x: ");
        System.out.println("Enter your move (line, row ) ");
        int line = Integer.parseInt(scanner.next());
//        System.out.print("y: ");
        int col = Integer.parseInt(scanner.next());
//        System.out.println();
//        if(choice.equals("E")) {
        explore(line, col);
//        } else if (choice.equals("M")) {
//            mark(x, y);
//        }
    }


    // displays all fields of the board with the coordinates
    public void display() {
        System.out.println("Current Status of the Board: ");
        System.out.print("     ");
        for (int i = 0; i < maxSide; i++) {
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");

        if (isDestroyed()) {
            for (int i = 0; i < maxSide; i++) {
                if (i < 10) {
                    System.out.print(i + "    ");
                } else {
                    System.out.print(i + "   ");
                }
                for (int j = 0; j < maxSide; j++) {
                    System.out.print(field[i][j].lostGame() + "  ");
                }
                System.out.println("\n");
            }

        } else {
            // display all lines
            for (int i = 0; i < maxSide; i++) {
                if (i < 10) {
                    System.out.print(i + "    ");
                } else {
                    System.out.print(i + "   ");
                }
                for (int j = 0; j < maxSide; j++) {
                    System.out.print(field[i][j].getAppearance() + "  ");
                }
                System.out.println("\n");
            }
        }
    }
}