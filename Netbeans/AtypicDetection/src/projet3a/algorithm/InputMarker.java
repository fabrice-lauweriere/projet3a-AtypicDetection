/**
 *
 * @author fabrice
 */
package projet3a.algorithm;

import java.util.Scanner;
import projet3a.data.NavigateXML;

public class InputMarker {

    private int nbOfCoeficients;
    private int[][] coeficients;

    public InputMarker() {
        this.chooseMarker(this.readEntryMarker());
    }

    public int readEntryMarker() {
        try {
            int out = 0;
            Scanner in = new Scanner(System.in);
            System.out.print("Choose a marking system : ");
            int nb = in.nextInt();
            if (nb > 0 && nb <2) {
                return nb;
            } else {
                System.out.println("ERROR : This marking system reference does not exist ");
                return readEntryMarker();
            }
        } catch (Exception e) {
             System.out.println("ERROR reading the marker choosing input");
             return readEntryMarker();
        }
    }

    public void chooseMarker(int ref) {
        this.coeficients = NavigateXML.getMarkingSystem(ref);
        this.nbOfCoeficients = this.coeficients[0].length;
    }
    
    public void printMarker(){
        String out = "";
        String line = "";
        System.out.println("number of coef = "+this.nbOfCoeficients);
        
        for(int i=0; i<NavigateXML.getNbOfCategories(); i++){
            line += "Category "+i+" : ";
            for(int j=0; j<this.nbOfCoeficients; j++){
                line += coeficients[i][j] + " | ";
            }
            line +="\n";
            out += line;
            line = "";
        }
        
        System.out.println(out);
    }
}
