package uniandes.algorithms.stategraphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class StateGraphSearch {
    public static void main(String[] args){
        int mode = Integer.valueOf(args[0]);
        String line;
        try ( 
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		){
            if(mode == 3){
                //We receive the problem input as a standard I/O
                line = br.readLine();
                String[] caseData = line.split(" ");
                int totalPeople = Integer.parseInt(caseData[0]);
                int totalFights = Integer.parseInt(caseData[1]);
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
                System.out.println("Total fights: "+totalFights);
                System.out.println("Number of people: "+ totalPeople);
                
            }

        }catch (IOException e) {
            System.err.println("Error al leer entrada estándar: " + e.getMessage());
        }
    }
}
