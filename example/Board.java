package org.example;

import java.util.Scanner;
import java.util.Vector;

/**
 * Класс, содержащий логику игры
 */
public class Board {
    private static final Scanner SCANNER = new Scanner(System.in);
    private int[][] board;
    private boolean[][] boardOfPossibleMoves;
    private int bestScorePlayer1 = -1;
    private int bestScorePlayer2 = -1;
    private int bestScoreComputer = -1;

    public void playEasyMode() {
        initializeBoard();
        while(true) {
            if (!isGameStillPossible()) {
                break;
            }
            makeMoveByPlayer(1);
            if (!isGameStillPossible()) {
                break;
            }
            makeMoveByComputer(true);
        }
        calculateAndPrintResults(true);
    }

    public void playHardMode() {
        initializeBoard();
        while(true) {
            if (!isGameStillPossible()) {
                break;
            }
            makeMoveByPlayer(1);
            if (!isGameStillPossible()) {
                break;
            }
            makeMoveByComputer(false);
        }
        calculateAndPrintResults(true);
    }

    public void playAgainstPlayerMode() {
        initializeBoard();
        while(true) {
            if (!isGameStillPossible()) {
                break;
            }
            makeMoveByPlayer(1);
            if (!isGameStillPossible()) {
                break;
            }
            makeMoveByPlayer(2);
        }
        calculateAndPrintResults(false);
    }

    public void displayBestScore() {
        System.out.println("Лучшие результаты игроков:");
        if (-1 == bestScorePlayer1) {
            System.out.println("Игрок 1 ещё не играл");
        } else {
            System.out.println("Игрок 1: " + bestScorePlayer1);
        }
        if (-1 == bestScorePlayer2) {
            System.out.println("Игрок 2 ещё не играл");
        } else {
            System.out.println("Игрок 2: " + bestScorePlayer2);
        }
        if (-1 == bestScoreComputer) {
            System.out.println("Компьютер ещё не играл");
        } else {
            System.out.println("Компьютер: " + bestScoreComputer);
        }
    }

    private void displayBoard(boolean displayPossibleMoves) {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < 8; ++i) {
            System.out.print(i);
            for (int j = 0; j < 8; ++j) {
                if (displayPossibleMoves && boardOfPossibleMoves[i][j]) {
                    System.out.print(" X");
                } else if (board[i][j] == 0) {
                    System.out.print(" .");
                } else {
                    System.out.print(" " + board[i][j]);
                }
            }
            System.out.println(" " + i);
        }
        System.out.println("  0 1 2 3 4 5 6 7\n");
    }

    private void initializeBoard() {
        System.out.println("Пожалуйста, вводите координаты в формате 'x y' (x сверху вниз; y слева направо)");
        board = new int[8][8];
        boardOfPossibleMoves = new boolean[8][8];
        board[3][4] = 1;
        board[4][3] = 1;
        board[3][3] = 2;
        board[4][4] = 2;
    }

    private boolean isGameStillPossible() {
        return updateBoardOfPossibleMoves(1) || updateBoardOfPossibleMoves(2);
    }

    /**
     * Обновляет массив возможных ходов
     * @param playerNumber Номер игрока
     * @return true - если возможен хотя бы 1 ход для игрока
     */
    private boolean updateBoardOfPossibleMoves(int playerNumber) {
        boardOfPossibleMoves = new boolean[8][8];
        boolean gameIsPossible = false;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 0 && getIndexesOfClampedCells(playerNumber, i, j).size() != 0) {
                    gameIsPossible = true;
                    boardOfPossibleMoves[i][j] = true;
                }
            }
        }
        return gameIsPossible;
    }

    private Vector<Integer> getIndexesOfClampedCells(int playerNumber, int x, int y) {
        Vector<Integer> indexesOfClampedCells = new Vector<>();
        Vector<Integer> tempIndexes;
        int i, j;
        boolean closed;
        // налево наверх
        i = x - 1;
        j = y - 1;
        closed = false;
        tempIndexes = new Vector<>();
        while(0 <= i && 0 <= j && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            --i;
            --j;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        // направо наверх
        i = x - 1;
        j = y + 1;
        closed = false;
        tempIndexes = new Vector<>();
        while(0 <= i && 7 >= j && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            --i;
            ++j;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        // направо вниз
        i = x + 1;
        j = y + 1;
        closed = false;
        tempIndexes = new Vector<>();
        while(7 >= i && 7 >= j && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            ++i;
            ++j;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        // налево вниз
        i = x + 1;
        j = y - 1;
        closed = false;
        tempIndexes = new Vector<>();
        while(7 >= i && 0 <= j && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            ++i;
            --j;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        // направо
        i = x;
        j = y + 1;
        closed = false;
        tempIndexes = new Vector<>();
        while(7 >= j && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            ++j;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        // вниз
        i = x + 1;
        j = y;
        closed = false;
        tempIndexes = new Vector<>();
        while(7 >= i && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            ++i;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        // налево
        i = x;
        j = y - 1;
        closed = false;
        tempIndexes = new Vector<>();
        while(0 <= j && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            --j;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        // наверх
        i = x - 1;
        j = y;
        closed = false;
        tempIndexes = new Vector<>();
        while(0 <= i && board[i][j] != 0) {
            if (board[i][j] == playerNumber) {
                closed = true;
                break;
            } else {
                tempIndexes.add(i);
                tempIndexes.add(j);
            }
            --i;
        }
        if (closed) {
            indexesOfClampedCells.addAll(tempIndexes);
        }
        return indexesOfClampedCells;
    }

    private void makeMoveByPlayer(int playerNumber) {
        if (updateBoardOfPossibleMoves(playerNumber)) {
            displayBoard(true);
            displayPossibleMoves();
            System.out.println("Ход Игрока " + playerNumber + " [введите координаты вашего хода в формате 'x y']");
            int x, y;
            while (true) {
                try {
                    String string = SCANNER.nextLine();
                    String[] strings = string.split(" ");
                    x = Integer.parseInt(strings[0]);
                    y = Integer.parseInt(strings[1]);
                } catch (Exception exception) {
                    System.out.println("Некорректный ввод!");
                    continue;
                }
                if (x < 8 && x >= 0 && y < 8 && y >= 0 && boardOfPossibleMoves[x][y]) {
                    break;
                } else {
                    System.out.println("Невозможный ход!");
                }
            }
            var indexesToChangeColor = getIndexesOfClampedCells(playerNumber, x, y);
            board[x][y] = playerNumber;
            for (int i = 0; i < indexesToChangeColor.size(); i = i + 2) {
                board[indexesToChangeColor.get(i)][indexesToChangeColor.get(i + 1)] = playerNumber;
            }
        } else {
            System.out.println("Игрок " + playerNumber + " не может ходить, ход передается");
        }
    }

    private void displayPossibleMoves() {
        System.out.println("Возможные ходы: ");
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (boardOfPossibleMoves[i][j]) {
                    System.out.println(i + " " + j);
                }
            }
        }
    }

    private void makeMoveByComputer(boolean isEasyMode) {
        if (updateBoardOfPossibleMoves(2)) {
            if (isEasyMode) {
                makeEasyMove();
            } else {
                makeHardMove();
            }
            System.out.println("Компьютер сходил");
        } else {
            System.out.println("Компьютер не может ходить, ход передается");
        }
    }

    private void makeEasyMove() {
        double maxValue = 0;
        double temp;
        Vector<Integer> indexesVector = new Vector<>();
        Vector<Integer> tempIndexesVector;
        int x = -2, y = -2;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (boardOfPossibleMoves[i][j]) {
                    tempIndexesVector = getIndexesOfClampedCells(2, i, j);
                    temp = valueOfAMove(tempIndexesVector, i, j);
                    maxValue = Math.max(maxValue, temp);
                    if (maxValue == temp) {
                        x = i;
                        y = j;
                        indexesVector = new Vector<>(tempIndexesVector);
                    }
                }
            }
        }
        board[x][y] = 2;
        for (int i = 0; i < indexesVector.size(); i = i + 2) {
            board[indexesVector.get(i)][indexesVector.get(i + 1)] = 2;
        }
    }

    private double valueOfAMove(Vector<Integer> indexes, int x, int y) {
        double ans = 0;
        if (x == 0 || x == 7) {
            ans += 0.4;
        }
        if (y == 0 || y == 7) {
            ans += 0.4;
        }
        for (int i = 0; i < indexes.size(); i += 2) {
            if (indexes.get(i) == 0 || indexes.get(i) == 7 || indexes.get(i + 1) == 0 || indexes.get(i + 1) == 7) {
                ans += 1;
            }
            ans += 1;
        }
        return ans;
    }

    private void makeHardMove() {
        int[][] tempBoard = new int[8][8];
        boolean[][] tempBoardOfPossibleMoves = new boolean[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                tempBoard[i][j] = board[i][j];
                tempBoardOfPossibleMoves[i][j] = boardOfPossibleMoves[i][j];
            }
        }
        double maxValue = -64000;
        double temp;
        Vector<Integer> indexesVector = new Vector<>();
        Vector<Integer> tempIndexesVector;
        int x = -2, y = -2;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (boardOfPossibleMoves[i][j]) {
                    // делаем 1 шаг
                    tempIndexesVector = getIndexesOfClampedCells(2, i, j);
                    temp = valueOfAMove(tempIndexesVector, i, j);
                    board[i][j] = 2;
                    for (int k = 0;  k < tempIndexesVector.size(); k = k + 2) {
                        board[tempIndexesVector.get(k)][tempIndexesVector.get(k + 1)] = 2;
                    }
                    // делаем 2 шаг
                    double maxxValue = 0;
                    double ttemp;
                    Vector<Integer> ttempIndexesVector;

                    for (int k = 0; k < 8; ++k) {
                        for (int m = 0; m < 8; ++m) {
                            if (boardOfPossibleMoves[k][m]) {
                                ttempIndexesVector = getIndexesOfClampedCells(2, k, m);
                                ttemp = valueOfAMove(ttempIndexesVector, k, m);
                                maxxValue = Math.max(maxxValue, ttemp);
                            }
                        }
                    }
                    temp -= maxxValue;

                    maxValue = Math.max(maxValue, temp);
                    if (maxValue == temp) {
                        x = i;
                        y = j;
                        indexesVector = new Vector<>(tempIndexesVector);
                    }
                    // возвращем все как было
                    for (int m = 0; m < 8; ++m) {
                        for (int k = 0; k < 8; ++k) {
                            board[m][k] = tempBoard[m][k];
                            boardOfPossibleMoves[m][k] = tempBoardOfPossibleMoves[m][k];
                        }
                    }
                }
            }
        }
        board[x][y] = 2;
        for (int i = 0; i < indexesVector.size(); i = i + 2) {
            board[indexesVector.get(i)][indexesVector.get(i + 1)] = 2;
        }
    }

    private void calculateAndPrintResults(boolean isGameAgainstComputer) {
        displayBoard(false);
        int ones = 0;
        int twos = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 1) {
                    ++ones;
                } else if (board[i][j] == 2) {
                    ++twos;
                }
            }
        }
        System.out.println("Результат игры:");
        bestScorePlayer1 = Math.max(ones, bestScorePlayer1);
        System.out.println("Игрок 1: " + ones);
        if (isGameAgainstComputer) {
            bestScoreComputer = Math.max(twos, bestScoreComputer);
            System.out.println("Компьютер: " + twos);
        } else {
            bestScorePlayer2 = Math.max(twos, bestScorePlayer2);
            System.out.println("Игрок 2: " + twos);
        }
    }
}
