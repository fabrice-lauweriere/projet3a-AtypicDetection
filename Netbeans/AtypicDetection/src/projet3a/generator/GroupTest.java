/*
 * @author fabrice lauweriere
 * 
 */

package projet3a.generator;

public class GroupTest {
	//ATTRIBUTES
	private int size;
	private Individual[] individuals;
	
	//CONSTRUCTOR
	public GroupTest(int size, int minDepth, int maxDepth, int minRoot, int maxRoot){
		this.size = size;
		this.individuals = new Individual[size];
		for(int i=0; i<this.size; i++){
			this.individuals[i] = new Individual(i, minDepth, maxDepth, minRoot, maxRoot);
		}
	}
	
	//FUNCTIONS
	public int getSize(){
		return this.size;
	}
	public Individual getIndividual(int i){
		return this.individuals[i];
	}
	public String toString(){
		String out = "Size = "+this.size+"\n";
		for(int i=0; i<this.size; i++){
			out+=this.individuals[i].toString() + "\n";
		}
		return out;
	}


}
