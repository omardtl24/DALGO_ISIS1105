package uniandes.algorithms.stategraphs;

import java.util.*;

public class FightsOptimizationProblem {

    private HashSet<Pair> fights;
    private String[] people;
    private int minFights;
    private boolean[] optimal;

    public boolean[] getOptimal(){
        return optimal;
    }

    public int getMinFights(){
        return minFights;
    }

    public FightsOptimizationProblem(String[] p, HashSet<Pair> f){
        this.fights = f;
        this.people = p;
    }

    public void findOptimalDistribution(int verbose){
        FightsDecisionProblem fightsDecisionProblem = new FightsDecisionProblem(this.people, this.fights);
        int tMax = this.fights.size()-1;
        int tMin = 0;
        int i=0;
        while(tMax - tMin > 1){
            boolean bMax = fightsDecisionProblem.solveDecisionProblem(tMax,0);
            boolean bMin = fightsDecisionProblem.solveDecisionProblem(tMin,0);
            int mid = (tMax + tMin) / 2;
            boolean bMid = fightsDecisionProblem.solveDecisionProblem(mid,0);
            

            if(verbose==1){
                System.out.println("tMin = "+tMin+"\tmid = "+mid+"\ttMax = "+tMax);
                System.out.println("bMin = "+bMin+"\tbMid = "+bMid+"\tbMax = "+bMax+"\n");
            }

            if(!(bMid && bMax)){
                tMin = mid;
            }else{
                tMax = mid;
            }
            i+=1;
            if(i==5) return;
        }
        if (fightsDecisionProblem.solveDecisionProblem(tMin, 0)) {
            this.minFights = tMin;
        } else {
            fightsDecisionProblem.solveDecisionProblem(tMax, 0);
            this.minFights = tMax;
        }
    
        this.optimal = fightsDecisionProblem.getFinalState();
    }

    public String getSolution(boolean[] state){
        HashSet<String> d1 = new HashSet<String>(), d2= new HashSet<String>();
        for(int i=0; i<state.length;i++){
            if(state[i]) d1.add(people[i]);
            else d2.add(people[i]);
        }

        return "Day 1: "+d1.toString()+"\nDay 2: "+d2.toString();
    }
}
