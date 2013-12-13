/*
 * @author fabrice lauweriere
 * 
 */
package projet3a.generator;

public class Individual {

    //ATTRIBUTES
    private int id;
    private WebHistory history;
    private int nbOfRoot;

    //CONSTRUCTOR
    public Individual(int id, int minDepth, int maxDepth, int minRoot, int maxRoot) {
        this.id = id;
        this.nbOfRoot = minRoot + (int) ((maxRoot - minRoot) * Math.random());
        this.history = new WebHistory(minDepth, maxDepth, this.nbOfRoot);//get he number from the window
    }

    //FUNCTIONS
    public int getId() {
        return this.id;
    }

    public WebHistory getHistory() {
        return this.history;
    }

    public String toString() {
        return "Individual id = " + this.id + "\nHistory = " + this.history.toString();
    }

}
