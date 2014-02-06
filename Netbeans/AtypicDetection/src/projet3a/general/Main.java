/*
 * @author fabrice lauweriere
 * 
 */

package projet3a.general;

import projet3a.algorithm.AlgoGenetic;
import projet3a.generator.Generator;

public class Main {
    public static Generator generator;
    private static AlgoGenetic algo;
    
    public static void main(String[] args){
        generator = new Generator();
        //generating individuals and their browsing history
        generator.generate();
        //sorting histories into categories
        generator.sort();
        
        algo = new AlgoGenetic();
        //creation of the reference matrix
        algo.createObservation();
        //insertion of atypic individuals
        algo.addAtypicBehavior();
        algo.printChanges(false);
        //searching for atypic individuals
        algo.computeSelectionFunction();
        //printing all detection combinaison
        algo.printResults();
        //finding the best combinaison
        algo.sortSelection();
        //printing best combinaison
        algo.printResults();
        

    }
    
}
