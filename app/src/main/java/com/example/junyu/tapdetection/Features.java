package com.example.junyu.tapdetection;

import Jama.Matrix;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.LinkedList;

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

    private double[] getMean(LinkedList<double[]> sample) {
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

    private double[] getStdDev(LinkedList<double[]> sample) {
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

    private double[] getSkewness(LinkedList<double[]> sample) {
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

    private double[] getKurtosis(LinkedList<double[]> sample) {
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

    private double getL1Norm(Matrix sampleMatrix) {
        return sampleMatrix.norm1();
    }

    private double getInfNorm(Matrix sampleMatrix) {
        return sampleMatrix.normInf();
    }

    private double getFroNorm(Matrix sampleMatrix) {
        return sampleMatrix.normF();
    }

    private double[] getRMS(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        return new double[] { xStats.getQuadraticMean(), yStats.getQuadraticMean(), zStats.getQuadraticMean() };
    }

    private double[] getP2p(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        double[] minValues = new double[] { xStats.getMin(), yStats.getMin(), zStats.getMin() };
        double[] maxValues = new double[] { xStats.getMax(), yStats.getMax(), zStats.getMax() };

        return new double[] {
                Math.abs(minValues[0] - maxValues[0]),
                Math.abs(minValues[1] - maxValues[1]),
                Math.abs(minValues[2] - maxValues[2])
        };
    }

    private double[] getMin(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        return new double[] { xStats.getMin(), yStats.getMin(), zStats.getMin() };
    }

    private double[] getMax(LinkedList<double[]> sample) {
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics zStats = new DescriptiveStatistics();
        for (double[] sensorValues : sample) {
            xStats.addValue(sensorValues[0]);
            yStats.addValue(sensorValues[1]);
            zStats.addValue(sensorValues[2]);
        }
        return new double[] { xStats.getMax(), yStats.getMax(), zStats.getMax() };
    }

    private double[] getPearsonCoeff(LinkedList<double[]> linAccSample, LinkedList<double[]> gyroSample) {
        // Get each list of sensor values as an array
        double[] xLinAccValues = new double[linAccSample.size()],
                 yLinAccValues = new double[linAccSample.size()],
                 zLinAccValues = new double[linAccSample.size()],
                 xGyroValues = new double[linAccSample.size()],
                 yGyroValues = new double[linAccSample.size()],
                 zGyroValues = new double[linAccSample.size()];
        for (int i = 0; i < linAccSample.size(); i++) {
            xLinAccValues[i] = linAccSample.get(i)[0];
            yLinAccValues[i] = linAccSample.get(i)[1];
            zLinAccValues[i] = linAccSample.get(i)[2];
        }
        for (int i = 0; i < gyroSample.size(); i++) {
            xGyroValues[i] = gyroSample.get(i)[0];
            yGyroValues[i] = gyroSample.get(i)[1];
            zGyroValues[i] = gyroSample.get(i)[2];
        }
        // order: xlinaccvalues vs xgyro,ygyro,zgyro, then ylinaccvalues, so on...
        PearsonsCorrelation pCorr = new PearsonsCorrelation();
        double pCoeff1 = pCorr.correlation(xLinAccValues, xGyroValues);
        double pCoeff2 = pCorr.correlation(xLinAccValues, yGyroValues);
        double pCoeff3 = pCorr.correlation(xLinAccValues, zGyroValues);
        double pCoeff4 = pCorr.correlation(yLinAccValues, xGyroValues);
        double pCoeff5 = pCorr.correlation(yLinAccValues, yGyroValues);
        double pCoeff6 = pCorr.correlation(yLinAccValues, zGyroValues);
        double pCoeff7 = pCorr.correlation(zLinAccValues, xGyroValues);
        double pCoeff8 = pCorr.correlation(zLinAccValues, yGyroValues);
        double pCoeff9 = pCorr.correlation(zLinAccValues, zGyroValues);

        double[] coefficients = {
                pCoeff1, pCoeff2, pCoeff3, pCoeff4, pCoeff5, pCoeff6, pCoeff7, pCoeff8, pCoeff9
        };
        for (int i = 0; i < coefficients.length; i++) {
            if (Double.isNaN(coefficients[i])) coefficients[i] = 0;
        }
        return coefficients;
    }

    private double getAngle(LinkedList<double[]> linAccSample) {
        double max_magnitude = 0, xMax = 0, yMax = 0;
        for (double[] sample : linAccSample) {
            double magnitude = Math.sqrt(Math.pow(sample[0], 2) + Math.pow(sample[1], 2));
            if (magnitude > max_magnitude) {
                max_magnitude = magnitude;
                xMax = sample[0];
                yMax = sample[1];
            }
        }
        return Math.atan2(yMax, xMax);
    }

    public String getTapOccurrenceFeatures() {
//        Standard features, lin acc before gyro:
//        1. means
//        2. standard dev
//        3. skewness
//        4. kurtosis
//        5. l1 norm
//        6. infinite norm
//        7. frobenius norm
//
//        lastly:
//        8. pearson coefficients

        // 15 linear accelerometer features (means etc across 3 axes)
        double[] linAccMeans = getMean(linAccSample);
        double[] linAccStdDevs = getStdDev(linAccSample);
        double[] linAccSkewness = getSkewness(linAccSample);
        double[] linAccKurtosis = getKurtosis(linAccSample);

        double linAcc1Norm = getL1Norm(linAccMatrix);
        double linAccInfNorm = getInfNorm(linAccMatrix);
        double linAccFroNorm = getFroNorm(linAccMatrix);
        double[] linAccNorms = new double[]{linAcc1Norm, linAccInfNorm, linAccFroNorm};

        // 15 gyroscope features
        double[] gyroMeans = getMean(gyroSample);
        double[] gyroStdDevs = getStdDev(gyroSample);
        double[] gyroSkewness = getSkewness(gyroSample);
        double[] gyroKurtosis = getKurtosis(gyroSample);

        double gyro1Norm = getL1Norm(gyroMatrix);
        double gyroInfNorm = getInfNorm(gyroMatrix);
        double gyroFroNorm = getFroNorm(gyroMatrix);
        double[] gyroNorms = new double[] {gyro1Norm, gyroInfNorm, gyroFroNorm};

        // 9 pearson features
        double[] pearsonCoeffs = getPearsonCoeff(linAccSample, gyroSample);

        double[] featureArray = merge(
                linAccMeans, linAccStdDevs, linAccSkewness, linAccKurtosis, linAccNorms,
                gyroMeans, gyroStdDevs, gyroSkewness, gyroKurtosis, gyroNorms,
                pearsonCoeffs);

        StringBuilder featureString = new StringBuilder();
        for (int i = 0; i < featureArray.length; i++) {
            featureString.append(i+1);
            featureString.append(':');
            featureString.append(featureArray[i]);
            featureString.append(' ');
        }
        return featureString.toString();
    }

    public String getHoldingHandFeatures() {
//        First angles
//        1. Impact angle
//        Standard features, lin acc before gyro:
//        2. means
//        3. standard dev
//        4. skewness
//        5. kurtosis
//        6. l1 norm
//        7. infinite norm
//        8. frobenius norm
//
//        lastly:
//        9. pearson coefficients
        double[] angle = new double[] {getAngle(linAccSample)};

        // 15 linear accelerometer features (means etc across 3 axes)
        double[] linAccMeans = getMean(linAccSample);
        double[] linAccStdDevs = getStdDev(linAccSample);
        double[] linAccSkewness = getSkewness(linAccSample);
        double[] linAccKurtosis = getKurtosis(linAccSample);

        double linAcc1Norm = getL1Norm(linAccMatrix);
        double linAccInfNorm = getInfNorm(linAccMatrix);
        double linAccFroNorm = getFroNorm(linAccMatrix);
        double[] linAccNorms = new double[]{linAcc1Norm, linAccInfNorm, linAccFroNorm};

        // 15 gyroscope features
        double[] gyroMeans = getMean(gyroSample);
        double[] gyroStdDevs = getStdDev(gyroSample);
        double[] gyroSkewness = getSkewness(gyroSample);
        double[] gyroKurtosis = getKurtosis(gyroSample);

        double gyro1Norm = getL1Norm(gyroMatrix);
        double gyroInfNorm = getInfNorm(gyroMatrix);
        double gyroFroNorm = getFroNorm(gyroMatrix);
        double[] gyroNorms = new double[] {gyro1Norm, gyroInfNorm, gyroFroNorm};

        // 9 pearson features
        double[] pearsonCoeffs = getPearsonCoeff(linAccSample, gyroSample);

        double[] featureArray = merge(
                angle,
                linAccMeans, linAccStdDevs, linAccSkewness, linAccKurtosis, linAccNorms,
                gyroMeans, gyroStdDevs, gyroSkewness, gyroKurtosis, gyroNorms,
                pearsonCoeffs);

        StringBuilder featureString = new StringBuilder();
        for (int i = 0; i < featureArray.length; i++) {
            featureString.append(i+1);
            featureString.append(':');
            featureString.append(featureArray[i]);
            featureString.append(' ');
        }
        return featureString.toString();
    }

    public String getLHandLocFeatures() {
//        First angles
//        1. Impact angle
//        Standard features, lin acc before gyro:
//        2. means
//        3. standard dev
//        4. skewness
//        5. kurtosis
//        6. l1 norm
//        7. infinite norm
//        8. frobenius norm
//
//        lastly:
//        9. pearson coefficients
        double[] angle = new double[] {getAngle(linAccSample)};

        // 15 linear accelerometer features (means etc across 3 axes)
        double[] linAccMeans = getMean(linAccSample);
        double[] linAccStdDevs = getStdDev(linAccSample);
        double[] linAccSkewness = getSkewness(linAccSample);
        double[] linAccKurtosis = getKurtosis(linAccSample);
        double linAcc1Norm = getL1Norm(linAccMatrix);
        double linAccInfNorm = getInfNorm(linAccMatrix);
        double linAccFroNorm = getFroNorm(linAccMatrix);

        double[] linAccNorms = new double[]{linAcc1Norm, linAccInfNorm, linAccFroNorm};

        // 15 gyroscope features
        double[] gyroMeans = getMean(gyroSample);
        double[] gyroStdDevs = getStdDev(gyroSample);
        double[] gyroSkewness = getSkewness(gyroSample);
        double[] gyroKurtosis = getKurtosis(gyroSample);
        double gyro1Norm = getL1Norm(gyroMatrix);
        double gyroInfNorm = getInfNorm(gyroMatrix);
        double gyroFroNorm = getFroNorm(gyroMatrix);

        double[] gyroNorms = new double[] {gyro1Norm, gyroInfNorm, gyroFroNorm};

        // 9 pearson features
        double[] pearsonCoeffs = getPearsonCoeff(linAccSample, gyroSample);

        double[] featureArray = merge(
                angle,
                linAccMeans, linAccStdDevs, linAccSkewness, linAccKurtosis, linAccNorms,
                gyroMeans, gyroStdDevs, gyroSkewness, gyroKurtosis, gyroNorms,
                pearsonCoeffs);

        StringBuilder featureString = new StringBuilder();
        for (int i = 0; i < featureArray.length; i++) {
            featureString.append(i+1);
            featureString.append(':');
            featureString.append(featureArray[i]);
            featureString.append(' ');
        }
        return featureString.toString();
    }

    public String getRHandLocFeatures() {
//        First angles
//        1. Impact angle
//        Standard features, lin acc before gyro:
//        2. means
//        3. standard dev
//        4. skewness
//        5. kurtosis
//        6. l1 norm
//        7. infinite norm
//        8. frobenius norm
//
//        lastly:
//        9. pearson coefficients
        double[] angle = new double[] {getAngle(linAccSample)};

        // 15 linear accelerometer features (means etc across 3 axes)
        double[] linAccMeans = getMean(linAccSample);
        double[] linAccStdDevs = getStdDev(linAccSample);
        double[] linAccSkewness = getSkewness(linAccSample);
        double[] linAccKurtosis = getKurtosis(linAccSample);
        double linAcc1Norm = getL1Norm(linAccMatrix);
        double linAccInfNorm = getInfNorm(linAccMatrix);
        double linAccFroNorm = getFroNorm(linAccMatrix);

        double[] linAccNorms = new double[]{linAcc1Norm, linAccInfNorm, linAccFroNorm};

        // 15 gyroscope features
        double[] gyroMeans = getMean(gyroSample);
        double[] gyroStdDevs = getStdDev(gyroSample);
        double[] gyroSkewness = getSkewness(gyroSample);
        double[] gyroKurtosis = getKurtosis(gyroSample);
        double gyro1Norm = getL1Norm(gyroMatrix);
        double gyroInfNorm = getInfNorm(gyroMatrix);
        double gyroFroNorm = getFroNorm(gyroMatrix);

        double[] gyroNorms = new double[] {gyro1Norm, gyroInfNorm, gyroFroNorm};

        // 9 pearson features
        double[] pearsonCoeffs = getPearsonCoeff(linAccSample, gyroSample);

        double[] featureArray = merge(
                angle,
                linAccMeans, linAccStdDevs, linAccSkewness, linAccKurtosis, linAccNorms,
                gyroMeans, gyroStdDevs, gyroSkewness, gyroKurtosis, gyroNorms,
                pearsonCoeffs);

        StringBuilder featureString = new StringBuilder();
        for (int i = 0; i < featureArray.length; i++) {
            featureString.append(i+1);
            featureString.append(':');
            featureString.append(featureArray[i]);
            featureString.append(' ');
        }
        return featureString.toString();        }

    private double[] merge(double[]... arrays) {
        // get length of merged array
        int count = 0;
        for (double[] array : arrays) {
            count += array.length;
        }
        // Create new array and copy all array contents
        double[] mergedArray = new double[count];
        int start = 0;
        for (double[] array: arrays)
        {
            System.arraycopy(array, 0, mergedArray, start, array.length);
            start += array.length;
        }
        return mergedArray;
    }
}