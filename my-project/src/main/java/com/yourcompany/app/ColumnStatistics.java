package com.yourcompany.app;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ColumnStatistics {

    public static void main(String[] args) throws Exception {

        DataSource source = new DataSource("C:/Users/kshre/OneDrive/Desktop/iris.arff");
        Instances data = source.getDataSet();

        for (int i = 0; i < data.numAttributes(); i++) {
            System.out.println("\n***** Attribute " + (i + 1) + ": " + data.attribute(i).name() + " *****");
            if (data.attribute(i).isNumeric()) {
                System.out.println("Type: Numeric");
                System.out.println("Minimum: " + data.attributeStats(i).numericStats.min);
                System.out.println("Maximum: " + data.attributeStats(i).numericStats.max);
                System.out.println("Mean: " + data.attributeStats(i).numericStats.mean);
                System.out.println("Standard Deviation: " + data.attributeStats(i).numericStats.stdDev);
            } else {
                System.out.println("Type: Nominal");
                System.out.println("Distinct Values: " + data.attributeStats(i).nominalCounts.length);
            }
        }
    }
}
