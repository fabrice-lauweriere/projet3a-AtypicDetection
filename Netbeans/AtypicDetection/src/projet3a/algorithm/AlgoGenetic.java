/**
 *
 * @author fabrice
 */
package projet3a.algorithm;

import projet3a.data.NavigateXML;
import projet3a.general.Main;
import projet3a.generator.Generator;

public class AlgoGenetic {

    private int[][] multiplicativeFactors;
    public InputMarker marker;
    private int[][] input;

    public AlgoGenetic() {
        this.multiplicativeFactors = Generator.algoInput;
        this.marker = new InputMarker();
        this.input = new int[Main.generator.getGroupTest().getSize()][NavigateXML.getNbOfCategories()];
        this.computeMarking(this.marker.id);
    }

    public void computeMarking(int ref) {
        switch (ref) {
            case 1:
                for (int individual = 0; individual < Main.generator.getGroupTest().getSize(); individual++) {
                    for(int cat=0; cat<NavigateXML.getNbOfCategories(); cat ++){
                        this.input[individual][cat] = 
                                ((this.multiplicativeFactors[individual][cat])^
                                (this.marker.coeficients[cat][0]))*
                                (this.marker.coeficients[cat][1]);
                    }
                }
                break;
            default:
                System.out.println("ERROR : This marking system camputation scheme doesn't exist");
                break;
        }

    }
    
    public void printInput(){
        String out = "";
        for(int i=0; i<input.length; i++){
            String line = "Individual N"+(i+1);
            for(int j=0; j<input[0].length; j++){
                line += " "+input[i][j]+" |";
            }
            line += "\n";
            out += line;
        }
        System.out.println(out);
    }

    public void runAlgo() {

    }

}
