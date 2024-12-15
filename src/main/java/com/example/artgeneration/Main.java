package com.example.artgeneration;

import javafx.application.Application;

/**
 * Главный класс запуска программы.
 * Запускает JavaFX приложение через Application.launch().
 */
public class Main {
    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Application.launch(DrawingApp.class, args);
    }
}
