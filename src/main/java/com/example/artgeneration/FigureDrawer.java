package com.example.artgeneration;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Класс для рисования фигур и сетки.
 */
public class FigureDrawer {
    private static final Logger logger = LogManager.getLogger(FigureDrawer.class);
    private final Random random = new Random();



    /**
     * Рисует случайную линию.
     *
     * @param gc объект GraphicsContext.
     * @param xMin минимальное значение X.
     * @param xMax максимальное значение X.
     * @param yMin минимальное значение Y.
     * @param yMax максимальное значение Y.
     * @param density плотность (кучность).
     */
    public void drawLine(GraphicsContext gc, double xMin, double xMax, double yMin, double yMax, double density) {
        gc.setStroke(randomColor());
        gc.setLineWidth(2);
        double x1 = randomCoord(xMin, xMax, density);
        double y1 = randomCoord(yMin, yMax, density);
        double x2 = randomCoord(xMin, xMax, density);
        double y2 = randomCoord(yMin, yMax, density);
        gc.strokeLine(x1, y1, x2, y2);
        logger.info(String.format("Линия нарисована: (%.2f, %.2f) -> (%.2f, %.2f)", x1, y1, x2, y2));
    }

    /**
     * Рисует случайную окружность.
     *
     * @param gc объект GraphicsContext.
     * @param xMin минимальное значение X.
     * @param xMax максимальное значение X.
     * @param yMin минимальное значение Y.
     * @param yMax максимальное значение Y.
     * @param density плотность (кучность).
     */
    public void drawCircle(GraphicsContext gc, double xMin, double xMax, double yMin, double yMax, double density) {
        gc.setFill(randomColor());
        double radius = 20 + random.nextDouble() * 50;
        double x = randomCoord(xMin, xMax, density);
        double y = randomCoord(yMin, yMax, density);
        gc.fillOval(x, y, radius, radius);
        logger.info(String.format("Окружность нарисована: центр=(%.2f, %.2f), радиус=%.2f", x, y, radius));
    }

    /**
     * Рисует случайный прямоугольник.
     *
     * @param gc объект GraphicsContext.
     * @param xMin минимальное значение X.
     * @param xMax максимальное значение X.
     * @param yMin минимальное значение Y.
     * @param yMax максимальное значение Y.
     * @param density плотность (кучность).
     */
    public void drawRectangle(GraphicsContext gc, double xMin, double xMax, double yMin, double yMax, double density) {
        gc.setFill(randomColor());
        double width = 30 + random.nextDouble() * 100;
        double height = 20 + random.nextDouble() * 80;
        double x = randomCoord(xMin, xMax, density);
        double y = randomCoord(yMin, yMax, density);
        gc.fillRect(x, y, width, height);
        logger.info(String.format("Прямоугольник нарисован: верхний левый угол=(%.2f, %.2f), ширина=%.2f, высота=%.2f", x, y, width, height));
    }

    /**
     * Рисует координатную сетку на холсте.
     *
     * @param gc объект GraphicsContext.
     * @param width ширина холста.
     * @param height высота холста.
     */
    public void drawGrid(GraphicsContext gc, double width, double height) {
        logger.info("Рисование координатной сетки.");
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        for (int x = 0; x < width; x += 20) {
            gc.strokeLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += 20) {
            gc.strokeLine(0, y, width, y);
        }
    }

    /**
     * Рисует случайный треугольник.
     *
     * @param gc объект GraphicsContext.
     * @param xMin минимальное значение X.
     * @param xMax максимальное значение X.
     * @param yMin минимальное значение Y.
     * @param yMax максимальное значение Y.
     * @param density плотность (кучность).
     */
    public void drawTriangle(GraphicsContext gc, double xMin, double xMax, double yMin, double yMax, double density) {
        gc.setFill(randomColor());
        double x1 = randomCoord(xMin, xMax, density);
        double y1 = randomCoord(yMin, yMax, density);
        double x2 = randomCoord(xMin, xMax, density);
        double y2 = randomCoord(yMin, yMax, density);
        double x3 = randomCoord(xMin, xMax, density);
        double y3 = randomCoord(yMin, yMax, density);
        gc.fillPolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
        logger.info(String.format("Треугольник нарисован: вершины=(%.2f, %.2f), (%.2f, %.2f), (%.2f, %.2f)", x1, y1, x2, y2, x3, y3));
    }

    /**
     * Рисует случайную параболу.
     *
     * @param gc объект GraphicsContext.
     * @param xMin минимальное значение X.
     * @param xMax максимальное значение X.
     * @param yMin минимальное значение Y.
     * @param yMax максимальное значение Y.
     */
    public void drawParabola(GraphicsContext gc, double xMin, double xMax, double yMin, double yMax, double density) {
        gc.setStroke(randomColor());
        gc.setLineWidth(2);

        double vertexX = randomCoord(xMin, xMax, density);
        double vertexY = randomCoord(yMin, yMax, density);

        double a = random.nextDouble() * 0.01 - 0.005; // Коэффициент "a" для параболы

        for (double x = vertexX - 50; x <= vertexX + 50; x += 1) {
            double y = vertexY + a * Math.pow(x - vertexX, 2);
            gc.strokeLine(x, y, x + 1, vertexY + a * Math.pow(x + 1 - vertexX, 2));
        }

        logger.info(String.format("Парабола нарисована: вершина=(%.2f, %.2f), коэффициент a=%.4f", vertexX, vertexY, a));
    }

    /**
     * Рисует случайную трапецию.
     *
     * @param gc объект GraphicsContext.
     * @param xMin минимальное значение X.
     * @param xMax максимальное значение X.
     * @param yMin минимальное значение Y.
     * @param yMax максимальное значение Y.
     * @param density плотность (кучность).
     */
    public void drawTrapezoid(GraphicsContext gc, double xMin, double xMax, double yMin, double yMax, double density) {
        gc.setFill(randomColor());

        double x1 = randomCoord(xMin, xMax, density);
        double y1 = randomCoord(yMin, yMax, density);
        double x2 = x1 + random.nextDouble() * 100 + 20; // Верхняя база
        double y2 = y1;

        double x3 = randomCoord(xMin, xMax, density);
        double y3 = randomCoord(y1, yMax, density); // Нижняя база
        double x4 = x3 + random.nextDouble() * 150 + 30;

        gc.fillPolygon(new double[]{x1, x2, x4, x3}, new double[]{y1, y2, y3, y3}, 4);
        logger.info(String.format("Трапеция нарисована: вершины=(%.2f, %.2f), (%.2f, %.2f), (%.2f, %.2f), (%.2f, %.2f)", x1, y1, x2, y2, x4, y3, x3, y3));
    }

    /**
     * Генерирует случайный цвет.
     *
     * @return Случайный объект Color.
     */
    private Color randomColor() {
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    /**
     * Генерирует случайное значение координаты с учетом плотности.
     *
     * @param min минимальное значение.
     * @param max максимальное значение.
     * @param density плотность (кучность).
     * @return Случайное значение координаты.
     */
    private double randomCoord(double min, double max, double density) {
        return min + (random.nextDouble() * (max - min) / density);
    }
}