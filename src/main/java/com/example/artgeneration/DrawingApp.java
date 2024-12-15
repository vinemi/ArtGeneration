package com.example.artgeneration;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Основной класс приложения JavaFX.
 * Отвечает за пользовательский интерфейс и взаимодействие с логикой.
 */
public class DrawingApp extends Application {
    private static final Logger logger = LogManager.getLogger(DrawingApp.class);

    private final ArtOption artOption = new ArtOption();
    private final FigureDrawer figureDrawer = new FigureDrawer();

    private Canvas canvas;

    // Слайдеры для настроек
    private SliderWithLabel lineSlider;
    private SliderWithLabel circleSlider;
    private SliderWithLabel rectSlider;
    private SliderWithLabel xMaxSlider;
    private SliderWithLabel yMaxSlider;
    private SliderWithLabel densitySlider;
    private SliderWithLabel triangleSlider;
    private SliderWithLabel parabolaSlider;
    private SliderWithLabel trapezoidSlider;
    private double lastMouseX;
    private double lastMouseY;

    @Override
    public void start(Stage stage) {
        logger.info("Запуск JavaFX приложения.");

        // Создаем холст для рисования с рамкой и поддержкой перетаскивания
        StackPane canvasWithBorder = createCanvasWithBorder();

        // Панель управления
        VBox controlPanel = new VBox(10);
        controlPanel.getChildren().addAll(
                createSlidersPanel(),
                createGridCheckBox(),
                createGenerateButton(),
                createSaveButton()
        );

        // Основная компоновка
        BorderPane root = new BorderPane();
        root.setCenter(canvasWithBorder); // Устанавливаем холст с рамкой в центр
        root.setRight(controlPanel);     // Панель управления справа

        // Создание сцены и отображение окна
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Random Drawing App");
        stage.show();

        logger.info("Приложение запущено.");
    }


    /**
     * Создает панель с слайдерами для управления параметрами рисунка.
     *
     * @return VBox с слайдерами.
     */
    private VBox createSlidersPanel() {
        VBox slidersPanel = new VBox(10);

        // Слайдеры для количества фигур
        lineSlider = createSliderWithLabel(0, 50, 10, "Количество линий:",true);
        circleSlider = createSliderWithLabel(0, 50, 10, "Количество окружностей:",true);
        rectSlider = createSliderWithLabel(0, 50, 10, "Количество прямоугольников:",true);
        triangleSlider = createSliderWithLabel(0, 50, 10, "Количество треугольников:",true);
        parabolaSlider = createSliderWithLabel(0, 50, 10, "Количество парабол:",true);
        trapezoidSlider = createSliderWithLabel(0, 50, 10, "Количество трапеций:",true);

        // Слайдеры для области рисования и плотности
        xMaxSlider = createSliderWithLabel(100, 1000, 800, "Максимальный X:",true);
        yMaxSlider = createSliderWithLabel(100, 1000, 600, "Максимальный Y:",true);
        densitySlider = createSliderWithLabel(0.1, 5, 1, "Плотность фигур:",false);


        slidersPanel.getChildren().addAll(lineSlider.getVBox(), circleSlider.getVBox(), rectSlider.getVBox(), triangleSlider.getVBox(), parabolaSlider.getVBox(), trapezoidSlider.getVBox(), xMaxSlider.getVBox(), yMaxSlider.getVBox(), densitySlider.getVBox());
        logger.info("Добавлены слайдеры для управления параметрами.");
        return slidersPanel;
    }

    /**
     * Создает объект SliderWithLabel, включающий слайдер и его метку.
     *
     * @param min Минимальное значение.
     * @param max Максимальное значение.
     * @param initialValue Начальное значение.
     * @param labelText Текст для метки.
     * @return Объект SliderWithLabel.
     */
    private SliderWithLabel createSliderWithLabel(double min, double max, double initialValue, String labelText, boolean isInt) {
        Slider slider = new Slider(min, max, initialValue);
        Label label = new Label(labelText + " " + (int) initialValue);

        // Обновляем метку при изменении значения слайдера
        if(isInt)
            slider.valueProperty().addListener((obs, oldVal, newVal) -> label.setText(labelText + " " + newVal.intValue()));
        else
            slider.valueProperty().addListener((obs, oldVal, newVal) -> label.setText(labelText + " " + newVal));


        return new SliderWithLabel(slider, label);
    }

    /**
     * Создает кнопку для генерации рисунка.
     *
     * @return Кнопка "Сгенерировать рисунок".
     */
    private Button createGenerateButton() {
        Button generateButton = new Button("Сгенерировать рисунок");
        generateButton.setOnAction(e -> onGenerateDrawing());
        return generateButton;
    }

    /**
     * Обработчик нажатия кнопки "Сгенерировать рисунок".
     */
    private void onGenerateDrawing() {
        logger.info("Кнопка генерации рисунка нажата.");

        // Считываем значения слайдеров
        int lineCount = (int) lineSlider.getValue();
        int circleCount = (int) circleSlider.getValue();
        int rectCount = (int) rectSlider.getValue();
        int parabolaCount = (int) parabolaSlider.getValue();
        int triangleCount = (int) triangleSlider.getValue();
        int trapezoidCount = (int) trapezoidSlider.getValue();
        double xMax = xMaxSlider.getValue();
        double yMax = yMaxSlider.getValue();
        double density = densitySlider.getValue();

        // Обновляем параметры ArtOption
        artOption.setLineCount(lineCount);
        artOption.setCircleCount(circleCount);
        artOption.setRectCount(rectCount);
        artOption.setParabolaCount(parabolaCount);
        artOption.setTriangleCount(triangleCount);
        artOption.setTrapezoidCount(trapezoidCount);
        artOption.setXMax(xMax);
        artOption.setYMax(yMax);
        artOption.setDensity(density);

        // Рисуем на холсте
        draw();
    }

    /**
     * Генерация рисунка на основе текущих настроек.
     */
    private void draw() {
        logger.info("Начало генерации рисунка.");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0,0,800,600);
        // Рисование сетки, если включено
        if (artOption.isShowGrid()) {
            figureDrawer.drawGrid(gc, canvas.getWidth(), canvas.getHeight());
        }

        // Рисование фигур
        for (int i = 0; i < artOption.getLineCount(); i++) {
            figureDrawer.drawLine(gc, 0, artOption.getXMax(), 0, artOption.getYMax(), artOption.getDensity());
        }
        for (int i = 0; i < artOption.getCircleCount(); i++) {
            figureDrawer.drawCircle(gc, 0, artOption.getXMax(), 0, artOption.getYMax(), artOption.getDensity());
        }
        for (int i = 0; i < artOption.getRectCount(); i++) {
            figureDrawer.drawRectangle(gc, 0, artOption.getXMax(), 0, artOption.getYMax(), artOption.getDensity());
        }
        for (int i = 0; i < artOption.getParabolaCount(); i++) {
            figureDrawer.drawParabola(gc, 0, artOption.getXMax(), 0, artOption.getYMax(), artOption.getDensity());
        }
        for (int i = 0; i < artOption.getTrapezoidCount(); i++) {
            figureDrawer.drawTrapezoid(gc, 0, artOption.getXMax(), 0, artOption.getYMax(), artOption.getDensity());
        }
        for (int i = 0; i < artOption.getTriangleCount(); i++) {
            figureDrawer.drawTriangle(gc, 0, artOption.getXMax(), 0, artOption.getYMax(), artOption.getDensity());
        }

        logger.info("Генерация рисунка завершена.");
    }

    /**
     * Создает контейнер с Canvas и добавляет к нему рамку.
     *
     * @return StackPane с Canvas и рамкой.
     */
    private StackPane createCanvasWithBorder() {
        // Создаем холст для рисования
        canvas = new Canvas(800, 600);
        StackPane canvasContainer = new StackPane(canvas);

        canvas.getGraphicsContext2D().strokeRect(0,0,800,600);
        return canvasContainer;
    }
    /**
     * Создает кнопку для сохранения изображения с выбором пути и имени файла.
     *
     * @return Кнопка "Сохранить изображение".
     */
    private Button createSaveButton() {
        Button saveButton = new Button("Сохранить изображение");
        saveButton.setOnAction(e -> onSaveImage());
        return saveButton;
    }
    /**
     * Обработчик нажатия кнопки сохранения изображения.
     * Вызывает метод сохранения текущего содержимого Canvas в указанный файл.
     */
    private void onSaveImage() {
        logger.info("Кнопка сохранения изображения нажата.");

        // Создаем диалог выбора файла
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить изображение");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения PNG", "*.png"));

        // Предлагаем пользователю выбрать путь и имя файла
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            // Сохраняем изображение в выбранный файл
            ArtSaver.saveToFile(canvas, file.getAbsolutePath());
            logger.info("Рисунок сохранен в файл: " + file.getAbsolutePath());
        } else {
            logger.info("Сохранение изображения отменено пользователем.");
        }
    }
    /**
     * Создает CheckBox для управления отображением координатной сетки.
     *
     * @return CheckBox для включения/выключения сетки.
     */
    private CheckBox createGridCheckBox() {
        CheckBox gridCheckBox = new CheckBox("Показывать сетку");
        gridCheckBox.setSelected(false); // По умолчанию сетка выключена

        // Слушатель для изменения отображения сетки
        gridCheckBox.setOnAction(event -> {
            boolean showGrid = gridCheckBox.isSelected();
            artOption.setShowGrid(showGrid);
            logger.info("Сетка " + (showGrid ? "включена" : "выключена"));
        });

        return gridCheckBox;
    }
}
