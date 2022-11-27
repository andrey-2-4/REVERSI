package org.example;

/**
 * Класс, содержащий логику игры
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
        // проходим по элементам          for
        // проверяем, что он 0,

        // что рядом есть противоположное число, (вокруг)

        // (в направлении противоположного числа) мы что-то замыкаем
        // СКОРЕЕ ВСЕГО ПРИДЕТСЯ СДЕЛАТЬ ОТДЕЛЬНУЮ ФУНКЦИЮ ДЛЯ НАХОЖДЕНИЯ ЭЛЕМЕНТОВ, КОТОРЫЕ МЫ ЗАЖИМАЕМ
        // + К ЭТОМУ боард оф элементов, которые меняют цвет и В ФУНКЦИИ МЕНЯТЬ ЭТО ХЕРНЮ

        // мы в этом методе короче меняем boardOfPossibleMoves
        // TODO
        // если было хотя бы 1 изменение вернем true иначе false

        
        return true;
    }

    private void makeMoveByPlayer(int playerNumber) {
        if (updateBoardOfPossibleMoves(playerNumber)) {
            displayBoard(true);
            displayPossibleMoves();
            System.out.println("Ход Игрока " + playerNumber + " [введите координаты вашего хода в формате 'x y']");
            // TODO взять допустимый ход
            // Изменить в соответствии ему доску
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
            // выводить не надо, просто меняем параметры доски
            if (isEasyMode) {
                // TODO
            } else {
                // TODO
            }
            System.out.println("Компьютер сходил");
        } else {
            System.out.println("Компьютер не может ходить, ход передается");
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
