package com.example.artgeneration;

import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ArtSaverTest {
    @Test
    void testSaveToFile() {
        // Создаем Canvas
        Canvas canvas = new Canvas(100, 100);

        // Временный файл для тестирования
        File tempFile = new File("test_image.png");

        // Сохраняем содержимое Canvas
        ArtSaver.saveToFile(canvas, tempFile.getAbsolutePath());

        // Проверяем, что файл был создан
        assertTrue(tempFile.exists());

        // Удаляем временный файл
        tempFile.delete();
    }
}