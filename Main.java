package org.example;

import java.util.Scanner;

/**
 * Класс для ввода и вывода
 */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        String s = SCANNER.nextLine();
        System.out.println(s);

    }

    /**
     * Выводит меню, получает выбранный режим
     * @return Число, соответствующее режиму
     */
    private static int getMenuCommand() {
        int command = 0;
        System.out.println("Выберите режим (Введите соответствующее число [и нажмите Enter]):" +
                "'1' Игрок против компьютера (легкий режим)" +
                "'2' Игрок против компьютера (усложненный режим)" +
                "'3' Игрок против Игрока" +
                "'4' Лучшие результаты игроков (за текущую сессию)");
        boolean enteredGoodValue = false;
        while (!enteredGoodValue) {
            try {
                command = Integer.parseInt(SCANNER.nextLine());
            } catch (Exception exception) {
                System.out.println("Пожалуйста, повторите ввод");
                continue;
            }
            if (command != 1 && command != 2 && command != 3 && command != 4) {
                continue;
            }
            enteredGoodValue = true;
        }
        return command;
    }
}