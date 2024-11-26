package uniandes.algorithms.stategraphs;

import java.util.*;

public class FightsDecisionProblem {

    private HashSet<Fight> fights;
    private String[] people;
    private HashMap<String, Integer> peopleMap;
    private PriorityQueue<boolean[]> agenda;
    private int t;
    private boolean[] finalState;

    public boolean[] getFinalState(){
        return finalState;
    }

    public FightsDecisionProblem(String[] p, HashSet<Fight> f){
        this.fights = f;
        this.peopleMap = new HashMap<String, Integer>();

        this.people = p;
        for (int i = 0; i < p.length; i++) {
            this.peopleMap.put(p[i], i);
        }


        this.agenda = new PriorityQueue<>(new Comparator<boolean[]>() {
            @Override
            public int compare(boolean[] s1, boolean[] s2) {
                return Integer.compare(countFights(s1),countFights(s2));
            }
        });
    }

    public boolean solveDecisionProblem(int t,int verbose){
        this.t = t;
        this.agenda.clear();
        this.agenda.add( new boolean[this.people.length]);
        boolean[] currentState;
        while(this.agenda.size()>0){
            currentState = this.agenda.poll();

            if(isSolution(currentState)){
                if(verbose==1) System.out.println(getSolution(currentState));
                this.finalState = currentState;
                return true;
            }

            List<boolean[]> successors = getSuccessors(currentState);
            int curFights = countFights(currentState);

            for(boolean[] successor : successors){
                if (countFights(successor) <= curFights){
                    this.agenda.add(successor);
                }
            }
        }
        return false;
    }

    public boolean isSolution(boolean[] state){
        return countFights(state) <= this.t;
    }

    public List<boolean[]> getSuccessors(boolean[] state){
        List<boolean[]> successors = new LinkedList<boolean[]>();
        int maxtrue = 0;
        for(int i=0;i<state.length;i++){
            if(maxtrue<i && state[i]) maxtrue=i;
        }

        for(int i=maxtrue+1;i<state.length;i++){
            boolean[] newState = Arrays.copyOf(state, state.length);
            newState[i] = true;
            successors.add(newState);
        }
        return successors;
    }

    public int countFights(boolean[] state){
        int c = 0;
        for(Fight fight: this.fights){
            int i = this.peopleMap.get(fight.person1);
            int j = this.peopleMap.get(fight.person2);

            if(state[i]==state[j]) c+=1;
        }
        return c;
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
