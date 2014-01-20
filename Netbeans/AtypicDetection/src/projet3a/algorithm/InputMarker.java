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

    public InputMarker(int nbOfCoef) {
        this.nbOfCoeficients = nbOfCoef;
        this.coeficients = new int[NavigateXML.getNbOfCategories()][this.nbOfCoeficients];
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
             e.printStackTrace();
        }
        return -1;
    }

    public void chooseMarker(int ref) {
        
    }
}
