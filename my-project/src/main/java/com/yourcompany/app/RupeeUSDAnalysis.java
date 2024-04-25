package com.yourcompany.app;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.LinearRegression;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Calendar;

public class RupeeUSDAnalysis {

    public static void main(String[] args) throws Exception {
        new RupeeUSDAnalysis().analyzeAndForecast("C:/Users/kshre/Downloads/CUR-INR (1).csv");
    }

    public void analyzeAndForecast(String csvPath) throws Exception {
        // 1. Load the CSV
        DataSource source = new DataSource(csvPath);
        Instances data = source.getDataSet();

        // Assuming the first column is Date, the second is exchange rate
        if (data.classIndex() == -1) {
            data.setClassIndex(1);
        }

        // 2. Handle Date Conversion & Feature Engineering
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int dateAttributeIndex = 0;
        data.insertAttributeAt(new Attribute(data.attribute(dateAttributeIndex).name()), dateAttributeIndex);

        for (int i = 0; i < data.numInstances(); i++) {
            String dateString = data.instance(i).stringValue(dateAttributeIndex + 1);
            data.instance(i).setValue(dateAttributeIndex, dateString);
        }

        data.deleteAttributeAt(dateAttributeIndex + 1);
        addTimeBasedFeatures(data, dateAttributeIndex, dateFormat);

        // 3. Build Regression Model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(data); // Train the model
        System.out.println(model.toString());

        // 4. Generate Forecasts
        generateForecasts(data, model, dateFormat, 10); // Forecast for the next 10 periods
    }

    private void addTimeBasedFeatures(Instances data, int dateAttributeIndex, SimpleDateFormat dateFormat) {
        Date startDate = findStartDate(data, dateAttributeIndex, dateFormat);

        for (int i = 0; i < data.numInstances(); i++) {
            Date date = data.instance(i).toDate(dateAttributeIndex);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            double daysSinceStart = calculateDaysSinceStart(date, startDate);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int monthOfYear = calendar.get(Calendar.MONTH);

            data.instance(i).setValue(dateAttributeIndex + 1, daysSinceStart);
            data.instance(i).setValue(dateAttributeIndex + 2, dayOfWeek);
            data.instance(i).setValue(dateAttributeIndex + 3, monthOfYear);
        }
    }

    private Date findStartDate(Instances data, int dateAttributeIndex, SimpleDateFormat dateFormat) {
        Date startDate = null;
        for (int i = 0; i < data.numInstances(); i++) {
            try {
                Date currentDate = dateFormat.parse(data.instance(i).stringValue(dateAttributeIndex));
                if (startDate == null || currentDate.before(startDate)) {
                    startDate = currentDate;
                }
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
        }
        return startDate;
    }

    private double calculateDaysSinceStart(Date date, Date startDate) {
        long timeDifference = date.getTime() - startDate.getTime();
        long daysDifference = TimeUnit.MILLISECONDS.toDays(timeDifference);
        return (double) daysDifference; // Return days as a double
    }
    // ... (Implement calculateDaysSinceStart and findStartDate) ...

    private void generateForecasts(Instances data, LinearRegression model, SimpleDateFormat dateFormat,
            int numForecasts) throws Exception {
        // ... (Implement forecasting logic, using 'model' for predictions) ...
    }
}
