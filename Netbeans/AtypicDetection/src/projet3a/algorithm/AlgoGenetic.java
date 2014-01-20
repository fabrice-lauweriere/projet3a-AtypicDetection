/**
 *
 * @author fabrice
 */
package projet3a.algorithm;

import projet3a.generator.Generator;


public class AlgoGenetic {
    
    private int[][] input;
    private InputMarker marker;

    public AlgoGenetic() {
        this.input = Generator.algoInput;
        marker = new InputMarker(3);
        marker.chooseMarker(marker.readEntryMarker());
    }
    
    
}
