package com.example.junyu.tapdetection;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SVMScale
{
    private static final String LOG_TAG = "SVMScale";
    private double lower = -1.0;
    private double upper = 1.0;
//    private double y_lower;
//    private double y_upper;
//    private boolean y_scaling = false;
    private double[] feature_max;
    private double[] feature_min;
//    private double y_max = -Double.MAX_VALUE;
//    private double y_min = Double.MAX_VALUE;
    private int max_index;
    private Context context;

    public SVMScale(Context context, String restore_filename) throws IOException {
        this.context = context;
        BufferedReader fp_restore = null;

		/* assumption: min index of attributes is 1 */
		/* pass 1: find out max index of attributes */
        // read the given scaling file to find its max index (no of attributes)
        // consider doing this to the feature vector to save calculations
        max_index = 0;

        if(restore_filename != null)
        {
            int idx, c;

            try {
                AssetManager assetManager = context.getAssets();
                fp_restore = new BufferedReader(
                        new InputStreamReader(assetManager.open(restore_filename)));
            }
            catch (Exception e) {
                System.err.println("can't open file " + restore_filename);
                System.exit(1);
            }
            if((c = fp_restore.read()) == 'y')
            {
                fp_restore.readLine();
                fp_restore.readLine();
                fp_restore.readLine();
            }
            fp_restore.readLine();
            fp_restore.readLine();

            String restore_line = null;
            while((restore_line = fp_restore.readLine())!=null)
            {
                StringTokenizer st2 = new StringTokenizer(restore_line);
                idx = Integer.parseInt(st2.nextToken());
                max_index = Math.max(max_index, idx);
            }
            fp_restore = rewind(fp_restore, restore_filename);
        }

        try {
            feature_max = new double[(max_index+1)];
            feature_min = new double[(max_index+1)];
        } catch(OutOfMemoryError e) {
            System.err.println("can't allocate enough memory");
            System.exit(1);
        }

        for(int i=0;i<=max_index;i++)
        {
            feature_max[i] = -Double.MAX_VALUE;
            feature_min[i] = Double.MAX_VALUE;
        }

		/* pass 2.5: save/restore feature_min/feature_max */
        if(restore_filename != null)
        {
            // fp_restore rewinded in finding max_index
            int idx, c;
            double fmin, fmax;

            fp_restore.mark(2);				// for reset
            if((c = fp_restore.read()) == 'y')
            {
                fp_restore.readLine();		// pass the '\n' after 'y'
                StringTokenizer st = new StringTokenizer(fp_restore.readLine());
//                y_lower = Double.parseDouble(st.nextToken());
//                y_upper = Double.parseDouble(st.nextToken());
                st = new StringTokenizer(fp_restore.readLine());
//                y_min = Double.parseDouble(st.nextToken());
//                y_max = Double.parseDouble(st.nextToken());
//                y_scaling = true;
            }
            else
                fp_restore.reset();

            if(fp_restore.read() == 'x') {
                fp_restore.readLine();		// pass the '\n' after 'x'
                StringTokenizer st = new StringTokenizer(fp_restore.readLine());
                lower = Double.parseDouble(st.nextToken());
                upper = Double.parseDouble(st.nextToken());
                String restore_line = null;
                while((restore_line = fp_restore.readLine())!=null)
                {
                    StringTokenizer st2 = new StringTokenizer(restore_line);
                    idx = Integer.parseInt(st2.nextToken());
                    fmin = Double.parseDouble(st2.nextToken());
                    fmax = Double.parseDouble(st2.nextToken());
                    if (idx <= max_index)
                    {
                        feature_min[idx] = fmin;
                        feature_max[idx] = fmax;
                    }
                }
            }
            fp_restore.close();
        }
    }

    private BufferedReader rewind(BufferedReader fp, String filename) throws IOException
    {
        fp.close();
        AssetManager assetManager = context.getAssets();
        return new BufferedReader(
            new InputStreamReader(assetManager.open(filename)));
    }

    private double output(int index, double value)
    {
        // scales the feature and prints it out on the console
		/* skip single-valued attribute */
        if(feature_max[index] == feature_min[index])
            return feature_max[index];

        if(value == feature_min[index])
            value = lower;
        else if(value == feature_max[index])
            value = upper;
        else
            value = lower + (upper-lower) *
                    (value-feature_min[index])/
                    (feature_max[index]-feature_min[index]);

        return value;
    }

    public String scale(String line)
    {
        int i,index;

		/* pass 3: scale */
        int next_index = 1;
        double value;
        StringBuilder scaledValues = new StringBuilder();

        StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");
        while(st.hasMoreElements())
        {
            index = Integer.parseInt(st.nextToken());
            value = Double.parseDouble(st.nextToken());
            for (i = next_index; i<index; i++)
                output(i, 0);
            scaledValues.append(index);
            scaledValues.append(':');
            scaledValues.append(output(index, value));
            scaledValues.append(' ');
            next_index = index + 1;
        }
        return scaledValues.toString();
    }

//    public void printSomething() {
//        Log.d(LOG_TAG, "First feature max is " + String.valueOf(feature_max[1]));
//    }

//    public static void main(String argv[]) throws IOException
//    {
//        SVMScale s = new SVMScale("range_tap_occurrence");
//        String line = "1:0.3481082902600368 2:0.09583571727077166 3:0.19676780054966608 4:0.2396138739481201 5:0.10931191830626712 6:0.20471175988127868 7:0.14788357258192586 8:0.35628289179084077 9:0.06022068212491881 10:-0.9894058258750102 11:-0.8791323432561846 12:-0.8952152979598496 13:5.2216243539 14:0.935271710157 15:1.87649466037 16:0.06845739893615246 17:0.03467639965626101 18:0.27781819850206374 19:0.0675994308953834 20:0.0462197091133752 21:0.11715953861908827 22:-0.49617041269271883 23:0.9143773100469148 24:0.40358864648906534 25:-1.2866912595066817 26:0.23534618292603993 27:-1.5097277050746827 28:4.16727297753 29:0.525029994547 30:1.20875915872 31:0.482790883171 32:-0.0556525003089 33:-0.290540351823 34:0.474814998532 35:0.00920382239479 36:-0.239132801658 37:0.190535625343 38:-0.0423874226815 39:-0.10819417346 ";
//        String scaledFeatures = s.run(line);
//        System.out.println(scaledFeatures);
//    }
}
