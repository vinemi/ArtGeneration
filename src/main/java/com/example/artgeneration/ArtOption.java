package com.example.artgeneration;



/**
 * Класс для хранения параметров рисунка.
 */
public class ArtOption {

    private int lineCount;
    private int circleCount;
    private int rectCount;
    private int triangleCount;
    private int parabolaCount;
    private int trapezoidCount;

    private double xMin, xMax, yMin, yMax;
    private double density;
    private boolean showGrid;

    /**
     * Конструктор с установкой параметров по умолчанию.
     */
    public ArtOption() {
        this.lineCount = 10;
        this.circleCount = 10;
        this.rectCount = 10;
        this.triangleCount = 10;
        this.parabolaCount = 10;
        this.trapezoidCount = 10;
        this.xMin = 0;
        this.xMax = 800;
        this.yMin = 0;
        this.yMax = 600;
        this.density = 5;
        this.showGrid = false;
    }

    // Геттеры и сеттеры для количества фигур

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getCircleCount() {
        return circleCount;
    }

    public void setCircleCount(int circleCount) {
        this.circleCount = circleCount;
    }

    public int getRectCount() {
        return rectCount;
    }

    public void setRectCount(int rectCount) {
        this.rectCount = rectCount;
    }

    public int getTriangleCount() {
        return triangleCount;
    }

    public void setTriangleCount(int triangleCount) {
        this.triangleCount = triangleCount;
    }

    public int getParabolaCount() {
        return parabolaCount;
    }

    public void setParabolaCount(int parabolaCount) {
        this.parabolaCount = parabolaCount;
    }

    public int getTrapezoidCount() {
        return trapezoidCount;
    }

    public void setTrapezoidCount(int trapezoidCount) {
        this.trapezoidCount = trapezoidCount;
    }

    // Геттеры и сеттеры для координат и плотности

    public double getXMin() {
        return xMin;
    }

    public void setXMin(double xMin) {
        this.xMin = xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public void setXMax(double xMax) {
        this.xMax = xMax;
    }

    public double getYMin() {
        return yMin;
    }

    public void setYMin(double yMin) {
        this.yMin = yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public void setYMax(double yMax) {
        this.yMax = yMax;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }
}
