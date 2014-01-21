/**
 *
 * @author fabrice
 */
package projet3a.data;

import java.io.File;
import java.util.ArrayList;
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
            ArrayList<String> categories = new ArrayList<String>();
            List par = theSite.getChildren("parent");
            List cat = theSite.getChildren("category");
            for (int i = 0; i < 10; i++) {
                Element el = (Element) par.get(i);
                parentsProba[i] = el.getAttributeValue("prob");
                parentsName[i] = el.getText();
            }
            Iterator itCat = cat.iterator();
            while (itCat.hasNext()) {
                Element c = (Element) itCat.next();
                categories.add(c.getText());
            }
            out.setProbabilities(parentsProba);
            out.setParents(parentsName);
            out.setCategories(categories);
        } catch (Exception e) {
            System.out.println("ERROR opening data.xml");
        }
        return out;
    }

    public static Site getSiteByName(String n) {
        Site out = new Site(0, n, null, null, null);
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
                    ArrayList<String> categories = new ArrayList<String>();
                    List par = el.getChildren("parent");
                    List cat = el.getChildren("category");
                    for (int i = 0; i < 10; i++) {
                        Element p = (Element) par.get(i);
                        parentsProba[i] = p.getAttributeValue("prob");
                        parentsName[i] = p.getText();
                    }
                    Iterator itCat = cat.iterator();
                    while (itCat.hasNext()) {
                        Element c = (Element) itCat.next();
                        categories.add(c.getText());
                    }
                    out.setProbabilities(parentsProba);
                    out.setParents(parentsName);
                    out.setCategories(categories);
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR opening data.xml in getSiteByName");
            e.printStackTrace();
        }
        return out;
    }

    public static ArrayList<String> getCategories(String site) {
        ArrayList<String> list = new ArrayList<String>();
        if (getSiteByName(site).getRank() != 0) {
            list = getSiteByName(site).getCategories();
        }
        return list;
    }

    public static int getCategoryIndex(String category) {
        ArrayList<String> allCategories = getAllCategories();
        return allCategories.indexOf(category);
    }

    public static ArrayList<String> getAllCategories() {
        ArrayList<String> allCategories = new ArrayList<String>();
        try {
            SAXBuilder sxb = new SAXBuilder();
            Document document = sxb.build(new File("src/projet3a/data/data.xml"));
            Element racine = document.getRootElement();
            List site = racine.getChildren("site");
            Iterator it = site.iterator();
            while (it.hasNext()) {
                Element theSite = (Element) it.next();
                List cat = theSite.getChildren("category");
                Iterator itCat = cat.iterator();
                while (itCat.hasNext()) {
                    Element c = (Element) itCat.next();
                    if (!allCategories.contains(c.getText())) {
                        allCategories.add(c.getText());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("=== ERROR while retrieving the list of all categories ===");
            e.printStackTrace();
        }
        return allCategories;
    }

    public static int getNbOfCategories() {
        return getAllCategories().size();
    }

    //========== for the marking system ================
    public static int[][] getMarkingSystem(int ref) {

        try {
            SAXBuilder sxb = new SAXBuilder();
            Document document = sxb.build(new File("src/projet3a/data/MarkingSystem" + ref + ".xml"));
            Element racine = document.getRootElement();
            int nbOfCoef = Integer.parseInt(racine.getAttributeValue("nbOfCoef"));
            int[][] out = new int[NavigateXML.getNbOfCategories()][nbOfCoef];
            List categories = racine.getChildren("category");
            Iterator it = categories.iterator();
            int i = 0;
            while (it.hasNext()) {
                List attributes = ((Element) it.next()).getAttributes();
                Iterator itAttributes = attributes.iterator();
                int j = 0;
                while(itAttributes.hasNext()){
                    out[i][j] = ((Attribute) itAttributes.next()).getIntValue();
                    j++;
                }
                i++;
            }
            return out;
        } catch (Exception e) {
            System.out.println("=== ERROR while retrieving the marking system ===");
            e.printStackTrace();
        }
        return null;
    }

    //====================== MAIN FOR TESTING =======================
//    public static void main(String[] args) {
//     int size = getNbOfCategories();
//     System.out.println(size);
//     System.out.println(getCategoryIndex("World"));
//     
//     }
}
