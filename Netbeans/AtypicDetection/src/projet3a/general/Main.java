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
        generator.generate();
        generator.getGroupTest().getIndividual(0).getHistory().printHistory();
        generator.sort();
        
        algo = new AlgoGenetic();
        algo.printAlgoInput();
        algo.addAtypicBehavior();
        algo.printChanges();
        algo.printAlgoInput();
        
        
        
        
        
        
        //algo.marker.printMarker();
        //algo.printInput();
//        algo.runAlgo();
    }
    
}
