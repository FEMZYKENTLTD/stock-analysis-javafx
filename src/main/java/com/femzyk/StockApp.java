package com.femzyk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StockApp extends Application {

    private TextField inputField;
    private Label resultLabel;

    @Override
    public void start(Stage stage) {

        inputField = new TextField();
        inputField.setPromptText("Enter prices (comma-separated)");

        TextField targetField = new TextField();
        targetField.setPromptText("Enter target price");

        Button avgBtn = new Button("Calculate Average");
        Button maxBtn = new Button("Find Maximum");
        Button countBtn = new Button("Count Occurrences");
        Button sumBtn = new Button("Cumulative Sum");

        resultLabel = new Label("Results will appear here");

        // Average
        avgBtn.setOnAction(e -> {
            try {
                float[] arr = parseArray(inputField.getText());
                resultLabel.setText("Average: " + calculateAveragePrice(arr));
            } catch (Exception ex) {
                resultLabel.setText("⚠ Enter valid stock prices");
            }
        });

        // Maximum
        maxBtn.setOnAction(e -> {
            try {
                float[] arr = parseArray(inputField.getText());
                resultLabel.setText("Maximum: " + findMaximumPrice(arr));
            } catch (Exception ex) {
                resultLabel.setText("⚠ Enter valid stock prices");
            }
        });

        // Count Occurrences (FIXED PROPERLY)
        countBtn.setOnAction(e -> {
            try {
                String input = inputField.getText();
                String targetText = targetField.getText();

                if (input == null || input.isEmpty() ||
                    targetText == null || targetText.isEmpty()) {
                    resultLabel.setText("⚠ Enter stock prices AND target value");
                    return;
                }

                float[] arr = parseArray(input);
                float target = Float.parseFloat(targetText.trim());

                resultLabel.setText("Occurrences: " + countOccurrences(arr, target));

            } catch (NumberFormatException ex) {
                resultLabel.setText("⚠ Enter valid numeric values only");
            } catch (Exception ex) {
                resultLabel.setText("⚠ Invalid input format");
            }
        });

        // Cumulative Sum
        sumBtn.setOnAction(e -> {
            try {
                ArrayList<Float> list = parseList(inputField.getText());
                resultLabel.setText("Cumulative: " + computeCumulativeSum(list));
            } catch (Exception ex) {
                resultLabel.setText("⚠ Enter valid stock prices");
            }
        });

        VBox layout = new VBox(10, inputField, targetField, avgBtn, maxBtn, countBtn, sumBtn, resultLabel);
        layout.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(layout, 400, 350));
        stage.setTitle("Stock Analysis App");
        stage.show();
    }

    // ===== Logic Methods =====

    public static float calculateAveragePrice(float[] prices) {
        float sum = 0;
        for (float p : prices) sum += p;
        return sum / prices.length;
    }

    public static float findMaximumPrice(float[] prices) {
        float max = prices[0];
        for (float p : prices) if (p > max) max = p;
        return max;
    }

    public static int countOccurrences(float[] prices, float target) {
        int count = 0;
        for (float p : prices) if (p == target) count++;
        return count;
    }

    public static ArrayList<Float> computeCumulativeSum(ArrayList<Float> prices) {
        ArrayList<Float> result = new ArrayList<>();
        float sum = 0;
        for (float p : prices) {
            sum += p;
            result.add(sum);
        }
        return result;
    }

    // ===== Helpers =====

    private float[] parseArray(String input) {
        String[] parts = input.split(",");
        float[] arr = new float[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arr[i] = Float.parseFloat(parts[i].trim());
        }
        return arr;
    }

    private ArrayList<Float> parseList(String input) {
        ArrayList<Float> list = new ArrayList<>();
        String[] parts = input.split(",");

        for (String s : parts) {
            list.add(Float.parseFloat(s.trim()));
        }
        return list;
    }

    public static void main(String[] args) {
        launch();
    }
}