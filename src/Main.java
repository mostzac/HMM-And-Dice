import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        File resultFile = new File("Result.txt");
        if(!resultFile.exists()){
            try {
                resultFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int fileindext = 0;
        BufferedWriter out = new BufferedWriter(new FileWriter(resultFile));
        while (fileindext < 10) {
            FileLoaderImpl file = new FileLoaderImpl();
            try {
                file.getFile(".\\TestFile\\InputFile" + fileindext + ".txt");
                out.write("\n InputFile" + fileindext + ".txt Loaded:\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int[] states = file.getStates();
            int[] observations = file.getObservations();
            double[] start_prob = new double[]{(double) 1 / (double) 3, (double) 1 / (double) 3, (double) 1 / (double) 3};
            double[][] trasition_prob = file.getTransition_Prob();
            double[][] emisssion_prob = file.getEmission_Prob();
            Viterbi_Alg.viterbi_DP(observations, states, start_prob, trasition_prob, emisssion_prob,resultFile,out);
            fileindext++;
        }
        out.flush();
        out.close();
        System.out.println("Results write in Result.txt");
    }
}
