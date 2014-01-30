/**
 *
 * @author fabrice
 */
package projet3a.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import projet3a.data.NavigateXML;
import projet3a.general.Main;
import projet3a.generator.Generator;

public class AlgoGenetic {

    private int[][] occurences;
    private ArrayList<Integer> changedIndividuals;
    private HashMap<Integer, Integer> changedCategories;
    //public InputMarker marker;
    //private int[][] input;

    public AlgoGenetic() {
        this.occurences = Generator.algoInput;
        this.changedCategories = new HashMap<>();
        this.changedIndividuals = new ArrayList<>();
        //this.marker = new InputMarker();
        //this.input = new int[Main.generator.getGroupTest().getSize()][NavigateXML.getNbOfCategories()];
        //this.computeMarking(this.marker.id);
    }

    public void addAtypicBehavior() {
        int nbAtypicIndividual = this.readEntry();
        int i = 0;
        while (i < nbAtypicIndividual) {
            int id = (int) (Main.generator.getGroupTest().getSize() * Math.random());
            if (!this.changedIndividuals.contains(id)) {
                this.changedIndividuals.add(id);
                int nbChangedCategories = (int) (3 * Math.random() + 1);
                for (int j = 0; j < nbChangedCategories; j++) {
                    int cat = (int) (NavigateXML.getNbOfCategories() * Math.random());
                    int temp = 0;
                    if (this.changedCategories.containsKey(cat)) {
                        temp = this.changedCategories.get(cat);
                    }
                    this.changedCategories.put(cat, temp + 50 + (int) (100 * Math.random()));
                    this.occurences[id][cat] = this.changedCategories.get(cat)-temp;
                }
                i++;
            }
        }
    }
    
    public int readEntry() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Choose a number of Atypic Individuals (1<=nb<=10 : ");
            int nb = in.nextInt();
            if (nb > 0 && nb <11) {
                return nb;
            } else {
                System.out.println("ERROR : You must choose a number between 1 and 10 ");
                return readEntry();
            }
        } catch (Exception e) {
             System.out.println("ERROR reading the number of atypic individual input");
             return readEntry();
        }
    }
    
    public void printAlgoInput() {
        try {
            String out = "";
            for (int i = 0; i < Main.generator.getGroupTest().getSize(); i++) {
                out += "Individual " + (i + 1) + " :";
                for (int c = 0; c < NavigateXML.getNbOfCategories(); c++) {
                    out += " " + this.occurences[i][c];
                }
                out += "\n";
            }
            System.out.println(out+"\n");
        } catch (Exception e) {
            System.out.println("WARNING : generate() and sort() methods must be called first.");
        }
    }
    
    public void printChanges(){
        String out = "Individual changed :\n";
        for(int ind:this.changedIndividuals){
            out+=ind+"\n";
        }
        out+="Categories Changed :\n";
        Set<Integer> set = this.changedCategories.keySet();
        for(int c:set){
            out+="Cat ID : "+c+" -> "+this.changedCategories.get(c)+"\n";
        }
        System.out.println(out+"\n");
    }

    /**
     * public void computeMarking(int ref) { switch (ref) { case 1: for (int
     * individual = 0; individual < Main.generator.getGroupTest().getSize();
     * individual++) { for(int cat=0; cat<NavigateXML.getNbOfCategories(); cat
     * ++){ this.input[individual][cat] =
     * ((this.multiplicativeFactors[individual][cat])^
     * (this.marker.coeficients[cat][0]))* (this.marker.coeficients[cat][1]); }
     * } break; default: System.out.println("ERROR : This marking system
     * camputation scheme doesn't exist"); break; }
     *
     * }
     *
     * public void printInput(){ String out = ""; for(int i=0; i<input.length;
     * i++){ String line = "Individual N"+(i+1); for(int j=0; j<input[0].length;
     * j++){ line += " "+input[i][j]+" |"; } line += "\n"; out += line; }
     * System.out.println(out); }
     */
    public void runAlgo() {

    }

}
