/*
 * @author fabrice lauweriere
 * 
 */
package projet3a.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import projet3a.data.NavigateXML;

public class WebHistory {

    //ATTRIBUTES
    private int size;
    private HashMap<String, Integer> history = new HashMap<String, Integer>();

    //CONSTRUCTOR
    public WebHistory(int minDepth, int maxDepth, int nbOfRoot) {
        for (int n = 0; n < nbOfRoot; n++) {//for each root : recursive add of websites in the navaigation branch
            //System.out.println("\n=== CREATING BROWSING HISTORY FOR ROOT " + (n + 1) + "/" + nbOfRoot + " ===");
            Site root = NavigateXML.getSiteByRank((int) (50 * Math.random()) + 1);
            addToHistory(root.getName());
            //System.out.println("ROOT = "+root.getName());
            int depth = minDepth + (int) ((maxDepth - minDepth) * Math.random());
            addParent(root, 0, depth);
        }
        this.size = this.history.size();

    }

    //FUNCTIONS
    public int getSize() {
        return this.size;
    }

    public HashMap<String, Integer> getHistory() {
        return history;
    }

    public void printHistory() {//printing function for debug
        String out = "";
        Set set = history.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String s = String.valueOf(it.next());
            int value = history.get(s);
            out += s + " -> " + value + "\n";
        }
        System.out.println(out);
    }

    public void addToHistory(String site) {//to add a website to the individual's browsing history
        this.history.put(site, this.history.containsKey(site) ? this.history.get(site) + 1 : 1);
    }

    public void addParent(Site site, int level, int depth) {//adding all parents in the navigation (recursive)
        if (level == depth) {//if the requiered depth is reached
            //System.out.println("arret level = " + level + "/" + depth);
            return;
        } else if (site.getRank() == 0) {//if the last added parentis not in teh 500 sites -> second chance
            if (Math.random() > (level/depth)+0.2) {
                int alea = (int) (20 * Math.random() + 1);
                addToHistory(NavigateXML.getSiteByRank(alea).getName());
                addParent(NavigateXML.getSiteByRank(alea), level + 1, depth);
                //System.out.println(" REPECHE PAR " + NavigateXML.getSiteByRank(alea).getName());
            } else {
                //System.out.println("arret level = " + level + "/" + depth);
                return;
            }
        } else {//if the last added site is one of the 500, we choose an other parent to add
            int chosen = 0;
            double bestOffset = 0;
            for (int i = 0; i < 10; i++) {
                double d = Double.parseDouble(site.getProbabilities()[i]) / 100 + 0.4;
                double rand = Math.random();
                if (d > rand) {
                    if (d - rand > bestOffset) {
                        bestOffset = d - rand;
                        chosen = i;
                    }
                }
            }
            addToHistory(site.getParents()[chosen]);
            addParent(NavigateXML.getSiteByName(site.getParents()[chosen]), level + 1, depth);
        }
    }

}
