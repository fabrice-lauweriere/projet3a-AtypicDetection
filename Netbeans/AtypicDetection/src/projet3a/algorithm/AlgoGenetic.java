/**
 *
 * @author fabrice
 */
package projet3a.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
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
            System.out.print("Choose a number of Atypic Individuals (1<=nb<=" + (int) (Main.generator.getGroupTest().getSize() / 2) + ") : ");
            int nb = in.nextInt();
            if (nb > 0 && nb <= (int) (Main.generator.getGroupTest().getSize() / 2)) {
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
//        java.util.GregorianCalendar calendar = new GregorianCalendar();
//        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(java.util.Calendar.MINUTE);
        int seconds = (int) ((System.currentTimeMillis() / 1000) % 60);
        int minutes = (int) ((System.currentTimeMillis() / 1000) / 60);
        for (int i = 0; i < Math.pow(2, Main.generator.getGroupTest().getSize()); i++) {
            String bin = Integer.toBinaryString(i);
            int nb = 0;
            for (int j = 0; j < bin.length(); j++) {
                nb += Integer.parseInt(String.valueOf(bin.charAt(j)));
            }
            if (nb <= (int) (Main.generator.getGroupTest().getSize() / 2)) {
                System.out.println(100 * (i / Math.pow(2, Main.generator.getGroupTest().getSize())) + "%");
                int[] H = new int[Main.generator.getGroupTest().getSize()];
                for (int j = 0; j < bin.length(); j++) {
                    H[H.length - 1 - j] = Integer.parseInt(String.valueOf(bin.charAt(bin.length() - 1 - j)));
                }
                int[] prod = matrixProduct(transpose(this.occurences), H);
                int penalty = 0;
                for (int j = 0; j < NavigateXML.getNbOfCategories(); j++) {
                    if (prod[j] <= this.observation[j]) {
                        penalty++;
                    }
                }
                Double result = this.alpha - this.beta * Math.pow(penalty, this.power);
                for (int k = 0; k < Main.generator.getGroupTest().getSize(); k++) {
                    result += this.risk[k] * H[k];
                }
                if (result > 0) {
                    this.selection.put(i, result);
                }

            }
        }
        int seconds2 = (int) ((System.currentTimeMillis() / 1000) % 60) - seconds;
        int minutes2 = (int) ((System.currentTimeMillis() / 1000) / 60) - minutes;
        if (seconds2 < 0) {
            minutes2--;
            seconds2 = 60 + seconds2;
        }
        System.out.println("Algorithm computing time : " + (minutes2) + " minutes et " + (seconds2) + " seconds");
    }

    public void sortSelection() {

        ArrayList<Integer> out = new ArrayList<>();
        Collection<Double> list = this.selection.values();

        Double maximum = Collections.max(list);
        Set<Integer> set = this.selection.keySet();
        for (int i1 : set) {
            if (maximum - this.selection.get(i1) > 500) {
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
            int[] H = new int[Main.generator.getGroupTest().getSize()];
            for (int j = 0; j < Integer.toBinaryString(i).length(); j++) {
                H[H.length - 1 - j] = Integer.parseInt(String.valueOf(Integer.toBinaryString(i).charAt(Integer.toBinaryString(i).length() - 1 - j)));
            }
            out+="Atypic Individuals : ";
            for(int k=0; k<H.length; k++){
                if(H[k]==1){
                    out+=k+", ";
                }
            }
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

    public void printChanges(boolean b) {
        String out = "Individual changed :\n";
        for (int ind : this.changedIndividuals) {
            out += ind + "\n";
        }
        if (b) {
            out += "Categories Changed :\n";
            Set<Integer> set = this.changedCategories.keySet();
            for (int c : set) {
                out += "Cat ID : " + c + " -> " + this.changedCategories.get(c) + "\n";
            }
        }
        System.out.println(out + "\n");
    }



    public void printMatrix(int[] M) {
        String out = "MATRIX : ";
        for (int i : M) {
            out += i + " | ";
        }
        System.out.println(out);
    }
}
