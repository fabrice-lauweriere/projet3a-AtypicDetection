/**
 *
 * @author fabrice
 */
package projet3a.data;

import java.io.File;
import java.util.Iterator;
import projet3a.generator.Site;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class NavigateXML {

    public static Site getSiteByRank(int rank) {
        Site out = new Site();
        try {
            SAXBuilder sxb = new SAXBuilder();
            Document document = sxb.build(new File("src/projet3a/data/data.xml"));
            Element racine = document.getRootElement();
            List site = racine.getChildren("site");
            Element theSite = (Element) site.get(rank - 1);
            out.setName(theSite.getChildText("name"));
            out.setRank(rank);
            String[] parentsProba = new String[10];
            String[] parentsName = new String[10];
            List par = theSite.getChildren("parent");
            for (int i = 0; i < 10; i++) {
                Element el = (Element) par.get(i);
                parentsProba[i] = el.getAttributeValue("prob");
                parentsName[i] = el.getText();
            }
            out.setProbabilities(parentsProba);
            out.setParents(parentsName);
        } catch (Exception e) {
            System.out.println("ERROR opening data.xml");
        }
        return out;
    }

    public static Site getSiteByName(String n) {
        Site out = new Site(0, n, null, null);
        try {
            SAXBuilder sxb = new SAXBuilder();
            Document document = sxb.build(new File("src/projet3a/data/data.xml"));
            Element racine = document.getRootElement();
            List site = racine.getChildren("site");
            Iterator it = site.iterator();
            while (it.hasNext()) {
                Element el = (Element) it.next();
                if (String.valueOf(el.getChildText("name")).equals(n)) {
                    out.setRank(Integer.parseInt(String.valueOf(el.getAttributeValue("rank"))));
                    out.setName(n);
                    String[] parentsProba = new String[10];
                    String[] parentsName = new String[10];
                    List par = el.getChildren("parent");
                    for (int i = 0; i < 10; i++) {
                        Element p = (Element) par.get(i);
                        parentsProba[i] = p.getAttributeValue("prob");
                        parentsName[i] = p.getText();
                    }
                    out.setProbabilities(parentsProba);
                    out.setParents(parentsName);
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR opening data.xml in getSiteByName");
            e.printStackTrace();
        }
        return out;
    }

    public static String getCategory(String site) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static int getCategoryIndex(String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static int getNbOfCategories() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
