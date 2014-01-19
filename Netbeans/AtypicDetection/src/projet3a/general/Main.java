/*
 * @author fabrice lauweriere
 * 
 */

package projet3a.general;

import projet3a.generator.Generator;

public class Main {
    private static Generator generator;
    
    public static void main(String[] args){
        generator = new Generator();
        generator.generate();
        System.out.println(generator.getGroupTest().getIndividual(0).getHistory().toString());
        generator.sort();
        generator.printAlgoInput();
    }
    
}
