/**
 *
 * @author fabrice
 */

package projet3a.generator;


public class Site {
    private int rank = 0;
    private String name;
    private String[] probabilities;
    private String[] parents;

    public Site(int rank, String name, String[] probabilities, String[] parents) {
        this.rank = rank;
        this.name = name;
        this.probabilities = probabilities;
        this.parents = parents;
    }
    
    public Site(){
        this.rank = 0;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String[] getProbabilities() {
        return probabilities;
    }

    public String[] getParents() {
        return parents;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProbabilities(String[] probabilities) {
        this.probabilities = probabilities;
    }

    public void setParents(String[] parents) {
        this.parents = parents;
    }

    @Override
    public String toString() {
        String p="";
        for(int i=0; i<10; i++){
            p+=this.parents[i]+" => "+this.probabilities[i]+"\n";
        }
        return "rank=" + rank + 
                "\nname=" + name + 
                "\nparents=\n\n" + p;
    }
    
    
    
}
