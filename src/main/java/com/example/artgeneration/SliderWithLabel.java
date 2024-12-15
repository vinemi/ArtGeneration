package com.example.artgeneration;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * Объект, объединяющий слайдер и его метку для удобного доступа.
 */
public class SliderWithLabel {
    private final Slider slider;
    private final Label label;
    private final VBox vBox;

    /**
     * Конструктор SliderWithLabel.
     *
     * @param slider Слайдер.
     * @param label Метка для слайдера.
     */
    public SliderWithLabel(Slider slider, Label label) {
        this.slider = slider;
        this.label = label;
        this.vBox = new VBox(5, label, slider);
    }

    /**
     * Возвращает значение слайдера.
     *
     * @return double.
     */
    public double getValue(){
        return slider.getValue();
    }
    /**
     * Возвращает слайдер.
     *
     * @return Slider.
     */
    public Slider getSlider() {
        return slider;
    }

    /**
     * Возвращает VBox с меткой и слайдером.
     *
     * @return VBox.
     */
    public VBox getVBox() {
        return vBox;
    }
}