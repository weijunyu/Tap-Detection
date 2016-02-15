package com.example.junyu.tapdetection;

import Jama.Matrix;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.LinkedList;

public class Features {
    private DescriptiveStatistics xDescStats, yDescStats, zDescStats;
    private Matrix sampleMatrix;

    public Features(LinkedList<double[]> sample) {
        // Use sample values for apache DescriptiveStatistics
        xDescStats = new DescriptiveStatistics();
        yDescStats = new DescriptiveStatistics();
        zDescStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xDescStats.addValue(sensorValues[0]);
            yDescStats.addValue(sensorValues[1]);
            zDescStats.addValue(sensorValues[2]);
        }
        // Use sample values for the Jama Matrix
        double[][] sampleArray = new double[sample.size()][];
        for (int i = 0; i < sample.size(); i++) {
            sampleArray[i] = sample.get(i);
        }
        sampleMatrix = new Matrix(sampleArray);
    }

    public double[] getMean() {
        return new double[] { xDescStats.getMean(), yDescStats.getMean(), zDescStats.getMean() };
    }

    public double[] getStdDev() {
        return new double[] {
                xDescStats.getStandardDeviation(),
                yDescStats.getStandardDeviation(),
                zDescStats.getStandardDeviation()
        };
    }

    public double[] getSkew() {
        return new double[] {
                xDescStats.getSkewness(),
                yDescStats.getSkewness(),
                zDescStats.getSkewness()
        };
    }

    public double[] getKurtosis() {
        return new double[] {
                xDescStats.getKurtosis(),
                yDescStats.getKurtosis(),
                zDescStats.getKurtosis()
        };
    }

    public double getL1Norm() {
        return sampleMatrix.norm1();
    }

    public double getInfNorm() {
        return sampleMatrix.normInf();
    }

    public double getFroNorm() {
        return sampleMatrix.normF();
    }

    public double[] getPearsonCoeff(double[][] linAccSample, double[][] gyroSample) {
        double[] xLinAcc = new double[linAccSample.length];
        double[] yLinAcc = new double[linAccSample.length];
        double[] zLinAcc = new double[linAccSample.length];
        double[] xGyro = new double[gyroSample.length];
        double[] yGyro = new double[gyroSample.length];
        double[] zGyro = new double[gyroSample.length];

        return new double[] {
                new PearsonsCorrelation().correlation(xLinAcc, xGyro),
                new PearsonsCorrelation().correlation(yLinAcc, yGyro),
                new PearsonsCorrelation().correlation(zLinAcc, zGyro),
        };
    }

    public double getHighestLine(LinkedList<double[]> linAccSample) {
        double max_magnitude = 0;
        for (double[] sample : linAccSample) {
            double magnitude = Math.sqrt(Math.pow(sample[0], 2) + Math.pow(sample[1], 2));
            if (magnitude > max_magnitude) {max_magnitude = magnitude;}
        }
        return max_magnitude;
    }

//    public double getAngle(double linAccSample[][]) {
//        Double[] xValues = new Double[linAccSample.length];
//        Double[] yValues = new Double[linAccSample.length];
//        for (int i = 0; i < xValues.length; i++) {
//            xValues[i] = linAccSample[i][0];
//        }
//        for (int i = 0; i < yValues.length; i++) {
//            yValues[i] = linAccSample[i][1];
//        }
//        List<Double> xArrayList = new ArrayList<>(Arrays.asList(xValues));
//        List<Double> yArrayList = new ArrayList<>(Arrays.asList(yValues));
//        List<Double> magnitudes = new ArrayList<>();
//        for (int i = 0; i < xArrayList.size(); i++) {
//            magnitudes.add(
//                    Math.sqrt(Math.pow(xArrayList.get(i), 2) + Math.pow(yArrayList.get(i), 2))
//            );
//        }
//
//    }

    public double[] generateFeatures(double[][] sample) {


        double[] feature_vector = {};
        return feature_vector;
    }
}
