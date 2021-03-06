/*
 * @author fabrice lauweriere
 * 
 */
package projet3a.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import projet3a.data.NavigateXML;

public class Generator {

    private int groupTestSize;
    private int historyMinDepth, historyMaxDepth;
    private int minRoot, maxRoot;
    private GroupTest groupTest;
    public static int[][] algoInput;

    public Generator() {
        System.out.println("========== BEGIN GENERATOR ==========");
        for (int i = 0; i < 5; i++) {
            readEntry(i);
        }
        System.out.println("========== END GENERATOR ==========");

    }

    public void readEntry(int entry) {//for reading parameters input from the user
        try {
            Scanner in = new Scanner(System.in);
            switch (entry) {
                case 0:
                    System.out.print("Size of the group Test ? ");
                    int nb = in.nextInt();
                    if (nb > 0) {
                        this.groupTestSize = nb;
                    } else {
                        System.out.println("ERROR : Size of the group test must be >0 ! ");
                        readEntry(entry);
                    }
                    break;
                case 1:
                    System.out.print("Minimum depth of the history ( >0 )? ");
                    int miD = in.nextInt();
                    if (miD > 0) {
                        this.historyMinDepth = miD;
                    } else {
                        System.out.println("ERROR : Minimum depth must be larger than 0 ! ");
                        readEntry(entry);
                    }

                    break;
                case 2:
                    System.out.print("Maximum depth of the history ? ");
                    int maD = in.nextInt();
                    if (maD > this.historyMinDepth) {
                        this.historyMaxDepth = maD;
                    } else {
                        System.out.println("ERROR : Maximum depth must be larger than minimum depth ! ");
                        readEntry(entry);
                    }
                    break;
                case 3:
                    System.out.print("Minimun number of history root ( >=1 ) ? ");
                    int miR = in.nextInt();
                    if (miR >= 1 && miR <= 50) {
                        this.minRoot = miR;
                    } else {
                        System.out.println("ERROR : Minimum number of history root must be >= 1 and <=50  ! ");
                        readEntry(entry);
                    }
                    break;
                case 4:
                    System.out.print("Maximun number of history root ( <=50 ) ? ");
                    int maR = in.nextInt();
                    if (maR >= this.minRoot && maR <= 50) {
                        this.maxRoot = maR;
                    } else {
                        System.out.println("ERROR : Maximum number of history root must be >= minimum and <= 50 ! ");
                        readEntry(entry);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR : Please enter an Integer ! ");
            readEntry(entry);
        }
    }

    public GroupTest getGroupTest() {
        return groupTest;
    }

    public int[][] getAlgoInput() {
        return algoInput;
    }

    public void setAlgoInput(int[][] algoInput) {
        this.algoInput = algoInput;
    }

    public void generate() {//launching the generation of the individuals and their browsing history
        try {
            this.groupTest = new GroupTest(this.groupTestSize, this.historyMinDepth, this.historyMaxDepth, this.minRoot, this.maxRoot);
        } catch (Exception e) {
            System.out.println("Error during the group test generation.");
            e.printStackTrace();
        }
    }

    public void sort() {//sorting each browsing history into categories
        int[][] out = new int[this.getGroupTest().getSize()][NavigateXML.getNbOfCategories()];
        try {
            for (int i = 0; i < this.getGroupTest().getSize(); i++) {
                HashMap<String, Integer> map = this.getGroupTest().getIndividual(i).getHistory().getHistory();
                Set keySet = map.keySet();
                Iterator iterator = keySet.iterator();
                while (iterator.hasNext()) {

                    String site = String.valueOf(iterator.next());
                    ArrayList<String> categories = NavigateXML.getCategories(site);
                    if (!categories.isEmpty()) {
                        for (String s : categories) {
                            out[i][NavigateXML.getCategoryIndex(s)] += map.get(site);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Sorting failed. WARNING : genrate() function must be called before sorting.");
            e.printStackTrace();
        }
        this.setAlgoInput(out);
    }

    

}
