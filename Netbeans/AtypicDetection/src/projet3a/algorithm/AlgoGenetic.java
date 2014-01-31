/**
 *
 * @author fabrice
 */
package projet3a.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import projet3a.data.NavigateXML;
import projet3a.general.Main;
import projet3a.generator.Generator;

public class AlgoGenetic {

    private int[][] occurences;
    private ArrayList<Integer> changedIndividuals;
    private HashMap<Integer, Integer> changedCategories;
    private int[] observation;
    private int[] risk;
    private HashMap<Integer, Double> selection;
    private int alpha, beta, power;
    //public InputMarker marker;
    //private int[][] input;

    public AlgoGenetic() {
        this.occurences = Generator.algoInput;
        this.changedCategories = new HashMap<>();
        this.changedIndividuals = new ArrayList<>();
        this.observation = new int[NavigateXML.getNbOfCategories()];
        this.risk = new int[Main.generator.getGroupTest().getSize()];
        for (int i = 0; i < Main.generator.getGroupTest().getSize(); i++) {
            risk[i] = 1;
        }
        this.selection = new HashMap<>();
        this.alpha = 1672810;
        this.beta = 10;
        this.power = 2;

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
                int nbChangedCategories = (int) (5 * Math.random() + 1);
                for (int j = 0; j < nbChangedCategories; j++) {
                    int cat = (int) (NavigateXML.getNbOfCategories() * Math.random());
                    int temp = 0;
                    if (this.changedCategories.containsKey(cat)) {
                        temp = this.changedCategories.get(cat);
                    }
                    this.changedCategories.put(cat, temp + 100 + (int) (100 * Math.random()));
                    this.occurences[id][cat] += this.changedCategories.get(cat) - temp;
                }
                i++;
            }
        }
    }

    public int readEntry() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Choose a number of Atypic Individuals (1<=nb<=6) : ");
            int nb = in.nextInt();
            if (nb > 0 && nb < 6) {
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

    public void createObservation() {
        for (int i = 0; i < NavigateXML.getNbOfCategories(); i++) {
            this.observation[i] = this.getCategorySum(i);
//            if (this.changedCategories.containsKey(i)) {
//                this.observation[i] += this.changedCategories.get(i);
//            }
        }
        printMatrix(this.observation);
    }

    public int[][] transpose(int[][] M) {
        int[][] out = new int[M[0].length][M.length];
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                out[j][i] = M[i][j];
            }
        }
        return out;
    }

    public int[] matrixProduct(int[][] A, int[] B) {
        int[] out = new int[A.length];
        if (A[0].length != B.length) {
            System.out.println("ERROR : Matrix have the wrong size.");
            return null;
        } else {
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[0].length; j++) {
                    out[i] += A[i][j] * B[j];
                }
            }
        }
        return out;
    }

    public int getCategorySum(int cat) {
        int out = 0;
        for (int i = 0; i < this.occurences.length; i++) {
            out += this.occurences[i][cat];
        }
        return out;
    }

    public void computeSelectionFunction() {
        for (int i = 0; i < Math.pow(2, Main.generator.getGroupTest().getSize()); i++) {
            String bin = Integer.toBinaryString(i);
            System.out.println(bin);
            int nb = 0;
            for (int j = 0; j < bin.length(); j++) {
                nb += Integer.parseInt(String.valueOf(bin.charAt(j)));
            }
            if (nb <= 5) {
                int[] H = new int[Main.generator.getGroupTest().getSize()];
                for (int j = 0; j < bin.length(); j++) {
                    H[H.length - 1 - j] = Integer.parseInt(String.valueOf(bin.charAt(bin.length() - 1 - j)));
                }
                int[] prod = matrixProduct(transpose(this.occurences), H);
                printMatrix(prod);
                int penalty = 0;
                for (int j = 0; j < NavigateXML.getNbOfCategories(); j++) {
                    if (prod[j] <= this.observation[j]) {
                        penalty++;
                    }
                }
                System.out.println(penalty);
                Double result = this.alpha - this.beta * Math.pow(penalty, this.power);
                for (int k = 0; k < Main.generator.getGroupTest().getSize(); k++) {
                    result += this.risk[k] * H[k];
                }
                System.out.println(result);
                if (result > 0) {
                    this.selection.put(i, result);
                }

            }
        }
    }

    public void sortSelection() {

        ArrayList<Integer> out = new ArrayList<>();
        Collection<Double> list = this.selection.values();
        
        Double maximum = Collections.max(list);
        Set<Integer> set = this.selection.keySet();
        for(int i1:set){
            if(maximum-this.selection.get(i1)>500){
                out.add(i1);
            }
        }
        for (int j : out) {
            this.selection.remove(j);
        }
        set = this.selection.keySet();
        out = new ArrayList<>();
        list = this.selection.values();
        Double minimum = Collections.min(list);
        for (int i : set) {
            if (this.selection.get(i) != minimum) {
                out.add(i);
            }
        }
        for (int j : out) {
            this.selection.remove(j);
        }
    }

    public void printResults() {
        String out = "RESULTS\n";
        Set<Integer> set = this.selection.keySet();
        for (int i : set) {
            out += Integer.toBinaryString(i) + " -> " + String.valueOf(this.selection.get(i)) + "\n";
        }
        System.out.println(out);
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
            System.out.println(out + "\n");
        } catch (Exception e) {
            System.out.println("WARNING : generate() and sort() methods must be called first.");
        }
    }

    public void printChanges() {
        String out = "Individual changed :\n";
        for (int ind : this.changedIndividuals) {
            out += ind + "\n";
        }
        out += "Categories Changed :\n";
        Set<Integer> set = this.changedCategories.keySet();
        for (int c : set) {
            out += "Cat ID : " + c + " -> " + this.changedCategories.get(c) + "\n";
        }
        System.out.println(out + "\n");
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

    public void printMatrix(int[] M) {
        String out = "MATRIX : ";
        for (int i : M) {
            out += i + " | ";
        }
        System.out.println(out);
    }
}
