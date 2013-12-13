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
        for (int n = 0; n < nbOfRoot; n++) {
            Site root = NavigateXML.getSiteByRank((int) (50 * Math.random()) + 1);
            addToHistory(root.getName());
            int depth = minDepth + (int) ((maxDepth - minDepth) * Math.random());
            addParents(root, 0, depth);
            System.out.print(" - "+(n+1)*100/nbOfRoot+" %");
        }
        System.out.print("\n");
        this.size = this.history.size();

    }

    //FUNCTIONS
    public int getSize() {
        return this.size;
    }

    public String toString() {
        String out = "";
        Set set = history.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String s = String.valueOf(it.next());
            int value = history.get(s);
            out += s + " -> " + value + "\n";
        }
        return out;
    }

    public void addToHistory(String site) {
        this.history.put(site, this.history.containsKey(site) ? this.history.get(site) + 1 : 1);
    }

    public void addParents(Site site, int level, int depth) {
        if (level == depth || site.getRank() == 0) {
            return;
        } else {
            for (int i = 0; i < 10; i++) {
                if (Double.parseDouble(site.getProbabilities()[i]) / 100 + 0.2 > Math.random()) {
                    addToHistory(site.getParents()[i]);
                    addParents(NavigateXML.getSiteByName(site.getParents()[i]), level + 1, depth);
                }
            }
        }
    }

}
