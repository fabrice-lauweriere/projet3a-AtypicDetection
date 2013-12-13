package getwebsitesdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

/**
 *
 * @author fabrice
 */
public class GetWebSitesData {

    public static org.jdom2.Document document;

    public static void main(String[] args) {
        //getWebSites();
        //getAllParents();
        System.out.println(getHTML("http://www.alexa.com/siteinfo/google.com"));
    }

    public static void getWebSites() {
        try {
            SAXBuilder sxb = new SAXBuilder();
            document = sxb.build(new File("src/getwebsitesdata/data.xml"));
            Element racine = document.getRootElement();

            for (int page = 0; page < 20; page++) {
                String htmlCode = getHTML("http://www.alexa.com/topsites/countries;" + page + "/FR");
                String[] tab = htmlCode.split("<a href=\"/siteinfo/");
                for (int i = 1; i < 26; i++) {
                    String[] subtab = tab[i].split("\">", 2);

                    Element site = new Element("site");
                    racine.addContent(site);
                    Attribute rank = new Attribute("rank", String.valueOf(page * 25 + i));
                    site.setAttribute(rank);
                    Element name = new Element("name");
                    name.setText(subtab[0]);
                    site.addContent(name);
                }
            }
            
            save(document, "src/getwebsitesdata/data.xml");
        } catch (Exception e) {
            System.out.println("Error I/O XML file (function getWebSites)");
        }
    }

    public static void getAllParents() {
        try {
            SAXBuilder sxb = new SAXBuilder();
            document = sxb.build(new File("src/getwebsitesdata/data.xml"));
            Element racine = document.getRootElement();
            List allSites = racine.getChildren("site");
            Iterator iterator = allSites.iterator();
            while (iterator.hasNext()) {
                Element current = (Element) iterator.next();
                String[][] currentParents = get10Parents(current.getChild("name").getText());
                for (int i = 0; i < 10; i++) {
                    Element parent = new Element("parent");
                    Attribute prob = new Attribute("prob", currentParents[1][i]);
                    parent.setAttribute(prob);
                    parent.setText(currentParents[0][i]);
                    current.addContent(parent);
                }
            }
            save(document, "src/getwebsitesdata/data.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error I/O XML file (function getAllParents)");
        }
    }

    public static String[][] get10Parents(String site) {
        String[][] result = new String[2][10];
        String html = getHTML("http://www.alexa.com/siteinfo/" + site);
        String pattern = "&nbsp;&nbsp<a href='/siteinfo/";
        String[] tab = html.split(pattern, 11);
        for (int i = 1; i < 11; i++) {
            String[] subtab = tab[i].split("'>", 3);
            result[0][i - 1] = subtab[0];
            result[1][i - 1] = subtab[2].split("%", 2)[0];
        }
        return result;
    }

    public static String getHTML(String urlToRead) {
        URL url; // The URL to read
        HttpURLConnection conn; // The actual connection to the web page
        BufferedReader rd; // Used to read results from the web page
        String line; // An individual line of the web page HTML
        String result = ""; // A long string containing all the HTML
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void save(Document doc, String fichier) {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(doc, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {
        }
    }
}
