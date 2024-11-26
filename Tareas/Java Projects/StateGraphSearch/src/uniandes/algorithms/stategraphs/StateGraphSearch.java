package uniandes.algorithms.stategraphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class StateGraphSearch {
    public static void main(String[] args){
        int mode = Integer.valueOf(args[0]);
        int verbose = Integer.valueOf(args[1]);
        String line;
        try ( 
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		){
            if(mode == 3  ||  mode == 2){
                //We receive the problem input as a standard I/O
                line = br.readLine();
                String[] caseData = line.split(" ");
                int totalPeople = Integer.parseInt(caseData[0]);
                int totalFights = Integer.parseInt(caseData[1]);
                int t = Integer.parseInt(caseData[2]);
                line = br.readLine();
                String [] people = line.split(" ");
                assert totalPeople == people.length;
                HashSet<Fight> fights = new HashSet<>();

                for(int i=0; i<totalFights; i++){
                    line = br.readLine();
                    String[] peopleFight = line.split(" ");
                    assert peopleFight.length == 2;
                    Fight fight = new Fight(peopleFight[0], peopleFight[1]);
                    fights.add(fight);
                }
                assert totalFights == fights.size();
                if(mode==3){
                    FightsDecisionProblem fightsDecisionProblem = new FightsDecisionProblem(people,fights);
                    boolean result = fightsDecisionProblem.solveDecisionProblem(t,verbose);
                    if(result) System.out.println("A solution with "+t+" fights or less was found");
                    else System.out.println("A solution with "+t+" fights or less was not found");
                }else{
                    FightsOptimizationProblem fightsOptimizationProblem = new FightsOptimizationProblem(people,fights);
                    fightsOptimizationProblem.findOptimalDistribution(verbose);
                    boolean[] optimalDistribution = fightsOptimizationProblem.getOptimal();
                    int minFights = fightsOptimizationProblem.getMinFights();
                    System.out.println("Minimum number of fights: "+minFights);
                    System.out.println(fightsOptimizationProblem.getSolution(optimalDistribution));
                }
            }

        }catch (IOException e) {
            System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
    }
}
