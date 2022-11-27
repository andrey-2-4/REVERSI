package org.example;

/**
 * Класс, содержащи логику игры
 */
public class Board {
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
        System.out.println();
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
        System.out.println();
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
        calculateAndPrintResults(true);
        System.out.println();
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
        System.out.println();
    }

    private void displayBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < 8; ++i) {
            System.out.print(i);
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 0) {
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
        // проходим по элементам
        // проверяем, что он 0,
        // что рядом есть противоположное число,
        // (в направлении проивоположного числа) мы что-то замыкаем
        // TODO
        // если было хотя бы 1 изменение
        return true;
    }

    private void makeMoveByPlayer(int playerNumber) {
        if (updateBoardOfPossibleMoves(playerNumber)) {
            // TODO
        } else {
            // TODO
        }
    }

    private void makeMoveByComputer(boolean isEasyMode) {
        if (updateBoardOfPossibleMoves(2)) {
            if (isEasyMode) {
                // TODO
            } else {
                // TODO
            }
        } else {
            // TODO
        }
    }

    private void calculateAndPrintResults(boolean isGameAgainstComputer) {
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
        bestScorePlayer1 = Math.max(ones, bestScorePlayer1);
        System.out.println("Результат игры:");
        System.out.println("Игрок 1: " + ones);
        if (isGameAgainstComputer) {
            bestScoreComputer = Math.max(twos, bestScoreComputer);
            System.out.println("Компьютер: " + ones);
        } else {
            bestScorePlayer2 = Math.max(twos, bestScorePlayer2);
            System.out.println("Игрок 2: " + twos);
        }
    }
}
