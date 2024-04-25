package com.yourcompany.app;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.Scanner;

public class WekaAnalysis {
    public static void main(String[] args) throws Exception {
        DataSource source = new DataSource("C:/Users/kshre/Downloads/CUR-INR (1).csv");
        Instances data = source.getDataSet();
        for (int i = 0; i < 5; i++) {
            Instance instance = data.instance(i);
            System.out.println(instance.toString());
        }
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1); // Assuming the last column is the class attribute
        }
        Scanner scanner = new Scanner(System.in);
        boolean continueAnalysis = true;
        while (continueAnalysis) {
            System.out.println("\n***** Data Analysis *****");
            System.out.println("1. Statistics for All Columns");
            System.out.println("2. Compare Two Columns");
            System.out.println("3. Exit"); // Add an exit option
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayAllColumnStats(data);
                    break;
                case 2:
                    compareTwoColumns(data, scanner);
                    break;
                case 3:
                    continueAnalysis = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        System.out.println("Exiting analysis..."); // Indicate exit
    }

    private static void displayAllColumnStats(Instances data) {
        for (int i = 0; i < data.numAttributes(); i++) {
            System.out.println("\n***** Attribute " + (i + 1) + ": " + data.attribute(i).name() + " *****");
            if (data.attribute(i).isNumeric()) {
                double[] stats = calculateColumnStats(data, i); // Calculate stats
                System.out.println("Type: Numeric");
                System.out.println("Minimum: " + stats[0]);
                System.out.println("Maximum: " + stats[1]);
                System.out.println("Mean: " + stats[2]);
                System.out.println("Standard Deviation: " + stats[3]);
            } else {
                System.out.println("Type: Nominal");
                System.out.println("Distinct Values: " + data.attributeStats(i).nominalCounts.length);
            }
        }
    }

    private static void compareTwoColumns(Instances data, Scanner scanner) {
        System.out.println("\n***** Column Indices and Names *****");
        for (int i = 0; i < data.numAttributes(); i++) {
            System.out.println("Index " + i + ": " + data.attribute(i).name());
        }
        System.out.print("\nEnter the index of the first column to compare: ");
        int column1Index = scanner.nextInt();
        System.out.print("Enter the index of the second column to compare: ");
        int column2Index = scanner.nextInt();
        if (!data.attribute(column1Index).isNumeric() || !data.attribute(column2Index).isNumeric()) {
            System.out.println("Error: Both columns must be numeric for comparison.");
            return;
        }
        double[] stats1 = calculateColumnStats(data, column1Index);
        double[] stats2 = calculateColumnStats(data, column2Index);
        System.out.println("\n***** Column Comparison *****");
        System.out.printf("Minimum:  %s = %.1f, %s = %.1f\n",
                data.attribute(column1Index).name(), stats1[0],
                data.attribute(column2Index).name(), stats2[0]);
        System.out.printf("Maximum:  %s = %.1f, %s = %.1f\n",
                data.attribute(column1Index).name(), stats1[1],
                data.attribute(column2Index).name(), stats2[1]);
        System.out.printf("Mean:     %s = %.3f, %s = %.3f\n",
                data.attribute(column1Index).name(), stats1[2],
                data.attribute(column2Index).name(), stats2[2]);
        System.out.printf("Std Dev:  %s = %.3f, %s = %.3f\n",
                data.attribute(column1Index).name(), stats1[3],
                data.attribute(column2Index).name(), stats2[3]);
    }

    private static double[] calculateColumnStats(Instances data, int columnIndex) {
        if (data.attribute(columnIndex).isNumeric()) { // Check for numeric column
            double min = data.attributeStats(columnIndex).numericStats.min;
            double max = data.attributeStats(columnIndex).numericStats.max;
            double mean = data.attributeStats(columnIndex).numericStats.mean;
            double stdDev = data.attributeStats(columnIndex).numericStats.stdDev;
            return new double[] { min, max, mean, stdDev };
        } else {
            return null; // Indicate non-numeric column if needed
        }
    }
}
