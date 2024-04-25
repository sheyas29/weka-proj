package com.yourcompany.app;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.Scanner;

public class ColumnStatisticsCompare {

    public static void main(String[] args) throws Exception {

        DataSource source = new DataSource("C:/Users/kshre/OneDrive/Desktop/iris.arff");
        Instances data = source.getDataSet();

        System.out.println("\n***** Column Indices and Names *****");
        for (int i = 0; i < data.numAttributes(); i++) {
            System.out.println("Index " + i + ": " + data.attribute(i).name());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the index of the first column to compare: ");
        int column1Index = scanner.nextInt();
        System.out.print("Enter the index of the second column to compare: ");
        int column2Index = scanner.nextInt();
        scanner.close();
        if (!data.attribute(column1Index).isNumeric() || !data.attribute(column2Index).isNumeric()) {
            System.out.println("Error: Both columns must be numeric for comparison.");
            return;
        }
        double[] stats1 = calculateColumnStats(data, column1Index);
        double[] stats2 = calculateColumnStats(data, column2Index);
        System.out.println("\n***** Column Comparison *****");
        System.out.println("Column 1: " + data.attribute(column1Index).name());
        System.out.println("Column 2: " + data.attribute(column2Index).name());
        System.out.println("Minimum:   Column 1 = " + stats1[0] + ", Column 2 = " + stats2[0]);
        System.out.println("Maximum:   Column 1 = " + stats1[1] + ", Column 2 = " + stats2[1]);
        System.out.println("Mean:      Column 1 = " + stats1[2] + ", Column 2 = " + stats2[2]);
        System.out.println("Std Dev:   Column 1 = " + stats1[3] + ", Column 2 = " + stats2[3]);

    }

    private static double[] calculateColumnStats(Instances data, int columnIndex) {
        double min = data.attributeStats(columnIndex).numericStats.min;
        double max = data.attributeStats(columnIndex).numericStats.max;
        double mean = data.attributeStats(columnIndex).numericStats.mean;
        double stdDev = data.attributeStats(columnIndex).numericStats.stdDev;
        return new double[] { min, max, mean, stdDev };
    }
}
