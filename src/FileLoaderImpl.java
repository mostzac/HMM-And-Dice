import java.io.*;
enum Dice{
    D1,
    D2,
    D3,
}
public class FileLoaderImpl implements FileLoader {

    private BufferedReader reader;
    private double P;//prob of not switch
    private int[] states = new int[]{Dice.D1.ordinal(), Dice.D2.ordinal(), Dice.D3.ordinal()};
    private int[] observations;
    private double[][] transition_Prob;
    private double[][] emission_Prob;

    @Override
    public void getFile(String fileName) throws IOException {
        try {
            this.reader = new BufferedReader(new FileReader(new File(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // set transitionProb
        P = Double.parseDouble(reader.readLine());
        transition_Prob = new double[][]{
                {P, (1 - P) / 2, (1 - P) / 2},
                {(1 - P) / 2, P, (1 - P) / 2},
                {(1 - P) / 2, (1 - P) / 2, P},
        };
        // set emission_Prob
        emission_Prob = new double[3][3];
        for (int i = 0; i < 3; i++) {
            String[] emit_prob = reader.readLine().split(",");
            for(int j =0;j<3;j++){
                emission_Prob[i][j] = Double.parseDouble(emit_prob[j]);
            }
        }
        // set observation sequence
        String[] ob = reader.readLine().split(", ");
        observations = new int[100];
        for(int i = 0;i<100;i++){
            observations[i] = Integer.parseInt(ob[i]);
        }
    }

    public int[] getStates(){return this.states;}
    public int[] getObservations(){return this.observations;}
    public double[][] getTransition_Prob(){return this.transition_Prob;}
    public double[][] getEmission_Prob(){return this.emission_Prob;}

}
