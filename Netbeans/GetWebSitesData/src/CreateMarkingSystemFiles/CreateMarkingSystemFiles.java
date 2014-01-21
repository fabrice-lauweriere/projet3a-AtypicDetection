/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CreateMarkingSystemFiles;

import getwebsitesdata.GetWebSitesData;
import java.io.File;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author fabrice
 */
public class CreateMarkingSystemFiles {
    
    public static org.jdom2.Document document;
    
    
    public static void main(String[] args){
        String[] c = new String[2];
        c[0] = "importance";
        c[1] = "weight";
        init(1, 410, c);
    }
    
    public static void init(int ref, int nbCat, String[] coefs){
        try {
            SAXBuilder sxb = new SAXBuilder();
            document = sxb.build(new File("src/createmarkingsystemfiles/MarkingSystem"+ref+".xml"));
            Element racine = document.getRootElement();
            Attribute name = new Attribute("name", "MarkerSystem1");
            Attribute nbCoef = new Attribute("nbOfCoef", String.valueOf(coefs.length));
            racine.setAttribute(name);
            racine.setAttribute(nbCoef);
            for(int i=0; i<nbCat; i++){
                Element cat = new Element("category");
                for(String s:coefs){
                    Attribute at = new Attribute(s, "0");
                    cat.setAttribute(at);
                }
                cat.setText(String.valueOf(i));
                racine.addContent(cat);
            }
            

            GetWebSitesData.save(document, "src/createmarkingsystemfiles/MarkingSystem"+ref+".xml");
        } catch (Exception e) {
            System.out.println("Error I/O XML file (function init)");
        }
    }
    
}
