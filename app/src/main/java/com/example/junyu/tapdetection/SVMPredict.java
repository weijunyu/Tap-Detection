package com.example.junyu.tapdetection;

import java.util.StringTokenizer;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

public class SVMPredict {
    /**
     * Returns an integer corresponding to the prediction of the highest probability.
     * 0 is for no tap
     *
     * 11 is for left hand location 1
     * 12 is for left hand location 2
     * 13, 14 and 15 follow
     *
     * 21 is for right hand location 1
     * 22 is for right hand location 2
     * 23, 24 and 25 follow as well.
     */
    private static double atof(String s)
    {
        return Double.valueOf(s);
    }

    private static int atoi(String s)
    {
        return Integer.parseInt(s);
    }

    public double[] predict(String line, svm_model model) {
        int nr_class=svm.svm_get_nr_class(model); // get the number of class labels from the model

        double[] prob_estimates = null;

        prob_estimates = new double[nr_class]; // prob_estimates for each class label

        StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

        // get number of features, eg 30
        int m = st.countTokens()/2;
        svm_node[] x = new svm_node[m];
        for(int j=0;j<m;j++)
        {
            // x is an array of svm_nodes. this populates x with the feature number and value
            x[j] = new svm_node();
            x[j].index = atoi(st.nextToken());
            x[j].value = atof(st.nextToken());
        }

        // we're predicting probabilities with C_SVC
        // v is the predicted class label for that data sample
        // prob_estimates holds probabilities that the vector is in each class
        double v = svm.svm_predict_probability(model,x,prob_estimates);;

        return prob_estimates;
    }
}
