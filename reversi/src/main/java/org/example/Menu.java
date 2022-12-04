package org.example;

import java.util.Scanner;

/**
 * Класс для вывода меню и выбора режима
 */
public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int PVE_EASY_MODE = 1;
    private static final int PVE_HARD_MODE = 2;
    private static final int PVP_MODE = 3;
    private static final int BEST_RESULT_MODE = 4;
    private static final int QUIT_GAME_MODE = 0;

    /**
     * Запускает игру в выбранном режиме
     */
    public static void startGame() {
        boolean stillWantToPlay = true;
        Board board = new Board();
        int menuCommand;
        while (stillWantToPlay) {
            menuCommand = getMenuCommand();
            switch (menuCommand) {
                case PVE_EASY_MODE -> board.playEasyMode();
                case PVE_HARD_MODE -> board.playHardMode();
                case PVP_MODE -> board.playAgainstPlayerMode();
                case BEST_RESULT_MODE -> board.displayBestScore();
                case QUIT_GAME_MODE -> stillWantToPlay = false;
            }
            System.out.println();
        }
    }

    /**
     * Выводит меню, получает выбранный режим
     * @return Число, соответствующее режиму
     */
    private static int getMenuCommand() {
        int command = 0;
        System.out.println("Выберите режим (Введите соответствующее число [и нажмите Enter]):\n'" +
                PVE_EASY_MODE + "' Игрок против компьютера (легкий режим)\n'" +
                PVE_HARD_MODE +  "' Игрок против компьютера (усложненный режим)\n'" +
                PVP_MODE + "' Игрок против Игрока\n'" +
                BEST_RESULT_MODE + "' Лучшие результаты игроков (за текущую сессию)\n'" +
                QUIT_GAME_MODE + "' Завершение программы\n");
        boolean enteredGoodValue = false;
        while (!enteredGoodValue) {
            try {
                command = Integer.parseInt(SCANNER.nextLine());
            } catch (Exception exception) {
                System.out.println("Пожалуйста, повторите ввод");
                continue;
            }
            if (command != PVE_EASY_MODE && command != PVE_HARD_MODE &&
                    command != PVP_MODE && command != BEST_RESULT_MODE &&
                    command != QUIT_GAME_MODE) {
                System.out.println("Пожалуйста, повторите ввод");
                continue;
            }
            enteredGoodValue = true;
        }
        System.out.println();
        return command;
    }
}
