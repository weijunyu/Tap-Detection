package com.example.junyu.tapdetection;

import Jama.Matrix;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Features {
//    private DescriptiveStatistics xLinAccStats, yLinAccStats, zLinAccStats,
//            xGyroStats, yGyroStats, zGyrostats;
    private LinkedList<double[]> linAccSample, gyroSample;
    private Matrix linAccMatrix, gyroMatrix;

    public Features(LinkedList<double[]> linAccSample, LinkedList<double[]> gyroSample) {
        // Use sample values for apache DescriptiveStatistics
        this.linAccSample = linAccSample;
        this.gyroSample = gyroSample;

        // Use sample values for the Jama Matrix
        double[][] linAccArray = new double[linAccSample.size()][];
        for (int i = 0; i < linAccSample.size(); i++) {
            linAccArray[i] = linAccSample.get(i);
        }
        linAccMatrix = new Matrix(linAccArray);

        double[][] gyroArray = new double[gyroSample.size()][];
        for (int i = 0; i < gyroSample.size(); i++) {
            gyroArray[i] = gyroSample.get(i);
        }
        gyroMatrix = new Matrix(gyroArray);
    }

    public double[] getMean(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        return new double[] { xStats.getMean(), yStats.getMean(), zStats.getMean() };
    }

    public double[] getStdDev(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        return new double[] {
                xStats.getStandardDeviation(),
                yStats.getStandardDeviation(),
                zStats.getStandardDeviation()
        };
    }

    public double[] getSkewness(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        return new double[] {
                xStats.getSkewness(),
                yStats.getSkewness(),
                zStats.getSkewness()
        };
    }

    public double[] getKurtosis(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        return new double[] {
                xStats.getKurtosis(),
                yStats.getKurtosis(),
                zStats.getKurtosis()
        };
    }

    public double getL1Norm(Matrix sampleMatrix) {
        return sampleMatrix.norm1();
    }

    public double getInfNorm(Matrix sampleMatrix) {
        return sampleMatrix.normInf();
    }

    public double getFroNorm(Matrix sampleMatrix) {
        return sampleMatrix.normF();
    }

    public double[] getPearsonCoeff(LinkedList<double[]> linAccSample, LinkedList<double[]> gyroSample) {
        // First make a 15 X 6 matrix of lin acc + gyro sensor values
        double[][] sampleArray = new double[linAccSample.size()][];
        for (int i = 0; i < linAccSample.size(); i++) {
            sampleArray[i] = ArrayUtils.addAll(linAccSample.get(i), gyroSample.get(i));
        }
        // Now construct the pearson correlation matrix
        RealMatrix corrMatrix = new PearsonsCorrelation().computeCorrelationMatrix(sampleArray);
//        PearsonsCorrelation pCorr = new PearsonsCorrelation();
//        RealMatrix corrMatrix = pCorr.computeCorrelationMatrix(sampleArray);
        double[][] corrMatrixArray = corrMatrix.getData();
        List<Double> pCoeffs = new ArrayList<>();
        for (int i = 0; i < corrMatrixArray.length; i++) {
            for (int j = 0; j < corrMatrixArray[0].length; j++) {
                if (j > i) {
                    if (Double.isNaN(corrMatrixArray[i][j])) {
                        corrMatrixArray[i][j] = 0;
                    }
                    pCoeffs.add(corrMatrixArray[i][j]);
                }
            }
        }
        return ArrayUtils.toPrimitive(pCoeffs.toArray(new Double[pCoeffs.size()]));
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
