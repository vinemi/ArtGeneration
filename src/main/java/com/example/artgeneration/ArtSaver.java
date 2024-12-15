package com.example.artgeneration;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Класс для сохранения содержимого Canvas в файл изображения.
 */
public class ArtSaver {
    private static final Logger logger = LogManager.getLogger(ArtSaver.class);

    /**
     * Сохраняет содержимое Canvas в файл изображения.
     *
     * @param canvas холст для рисования.
     * @param fileName имя файла, в который будет сохранен рисунок.
     */
    public static void saveToFile(Canvas canvas, String fileName) {
        logger.info("Сохранение рисунка в файл: " + fileName);

        // Создаем WritableImage и захватываем содержимое Canvas
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(new SnapshotParameters(), writableImage);

        // Преобразуем WritableImage в массив байтов и сохраняем в файл
        try {
            File file = new File(fileName);
            saveWritableImageToFile(writableImage, file);
            logger.info("Рисунок успешно сохранен в файл: " + fileName);
        } catch (IOException e) {
            logger.error("Ошибка при сохранении файла: " + fileName, e);
        }
    }

    /**
     * Сохраняет WritableImage в файл формата PNG.
     *
     * @param writableImage изображение, захваченное с Canvas.
     * @param file файл, в который будет сохранено изображение.
     * @throws IOException если произошла ошибка при записи файла.
     */
    private static void saveWritableImageToFile(WritableImage writableImage, File file) throws IOException {
        // Получаем размер изображения
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();

        // Буфер для хранения данных пикселей
        ByteBuffer buffer = ByteBuffer.allocate(width * height * 4);

        // Читаем пиксели из WritableImage
        writableImage.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), buffer, width * 4);

        // Создаем массив байтов из буфера
        byte[] bytes = buffer.array();

        // Пишем данные в файл через стандартный Java API
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            // Используем ImageIO для создания PNG файла
            writePngImage(outputStream, bytes, width, height);
        }
    }

    /**
     * Записывает изображение в формате PNG.
     *
     * @param outputStream поток для записи.
     * @param bytes массив байтов изображения.
     * @param width ширина изображения.
     * @param height высота изображения.
     * @throws IOException если произошла ошибка записи.
     */
    private static void writePngImage(FileOutputStream outputStream, byte[] bytes, int width, int height) throws IOException {
        // Используем ImageIO для создания изображения PNG из массива байтов
        ImageIO.write(createBufferedImage(bytes, width, height), "png", outputStream);
    }

    /**
     * Преобразует массив байтов в BufferedImage.
     *
     * @param bytes массив байтов изображения.
     * @param width ширина изображения.
     * @param height высота изображения.
     * @return объект BufferedImage.
     */
    private static BufferedImage createBufferedImage(byte[] bytes, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] argbPixels = new int[width * height];

        // Преобразуем BGRA в ARGB
        for (int i = 0; i < bytes.length / 4; i++) {
            int b = bytes[i * 4] & 0xFF;
            int g = bytes[i * 4 + 1] & 0xFF;
            int r = bytes[i * 4 + 2] & 0xFF;
            int a = bytes[i * 4 + 3] & 0xFF;

            argbPixels[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }

        image.setRGB(0, 0, width, height, argbPixels, 0, width);
        return image;
    }
}
