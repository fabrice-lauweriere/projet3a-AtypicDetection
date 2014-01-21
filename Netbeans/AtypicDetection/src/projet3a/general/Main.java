/*
 * @author fabrice lauweriere
 * 
 */

package projet3a.general;

import projet3a.algorithm.AlgoGenetic;
import projet3a.generator.Generator;

public class Main {
    private static Generator generator;
    private static AlgoGenetic algo;
    
    public static void main(String[] args){
        generator = new Generator();
        generator.generate();
        generator.getGroupTest().getIndividual(0).getHistory().printHistory();
        generator.sort();
        generator.printAlgoInput();
        
        algo = new AlgoGenetic();
        algo.marker.printMarker();
//        algo.runAlgo();
    }
    
}
