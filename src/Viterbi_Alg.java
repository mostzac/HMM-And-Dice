import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class Viterbi_Alg {
    /**
     * @param obs     Observations
     * @param states  States
     * @param start_p Start_Possibility
     * @param trans_p Transition_Possibility
     * @param emis_p  Emission_possibility
     * @param resultFile Result writes file
     **/
    public static void viterbi_DP(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emis_p, File resultFile,BufferedWriter out) {
        double[][] Viterbi = new double[states.length][obs.length]; // record the highest probability to state i with observation j
        int[][] path = new int[states.length][obs.length]; // record the path till state j-1

        for (int state : states) { // initialize
            Viterbi[state][0] = start_p[state] * emis_p[state][obs[0] - 1];
            path[state][0] = state;
        }

        for (int j = 1; j < obs.length; j++) {  //observation sequence
            int[][] temp = new int[states.length][obs.length]; // temp array to record max path

            for (int i : states) {  //states sequence
                double prob = -1;
                int curstate;
                for (int y0 : states) {
                    double nprob = Viterbi[y0][j - 1] * trans_p[y0][i] * emis_p[i][obs[j] - 1];
                    if (nprob > prob) { //fine the previous max node
                        prob = nprob;
                        curstate = y0;
                        // record the max prob
                        Viterbi[i][j] = prob;
                        // record path
                        System.arraycopy(path[curstate], 0, temp[i], 0, j);//copy the previous path
                        temp[i][j] = i;//add curstate j into to path
                    }
                }
            }

            path = temp;
        }

        double prob = -1;
        int state = 0;
        for (int i : states) {
            if (Viterbi[i][obs.length - 1] > prob) {
                prob = Viterbi[i][obs.length - 1];
                state = i;
            }
        }
        StringBuffer route = new StringBuffer();
        for (int i : path[state]) {
            route.append(Dice.values()[i]+" ");
        }

        try {
            out.write("Probability of path: " + prob+"\r\n");
            int i = 0;
            while(i<300){
                out.write(route.substring(i,i+30)+"\r\n");
                i += 30;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

