package ka.commune.business;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ka.commune.entity.DataBaseInfo;

public class ReadXMLFile 
{
    
  public DataBaseInfo readFile() 
  {
    /*
     * Etape 1 : r�cup�ration d'une instance de la classe "DocumentBuilderFactory"
     */
	  
	  DataBaseInfo dbi=new DataBaseInfo();
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      	
    try 
    {
      /*
       * Etape 2 : cr�ation d'un parseur
       */
      final DocumentBuilder builder = factory.newDocumentBuilder();
			
	  /*
	   * Etape 3 : cr�ation d'un Document
	   */
	  final Document document= builder.parse(new File("./database.xml")); // /Commune001/src/ka/commune/business/App.java
			
	  //Affichage du prologue
		/*
	  System.out.println("*************PROLOGUE************");
	  System.out.println("version : " + document.getXmlVersion());
	  System.out.println("encodage : " + document.getXmlEncoding());
      System.out.println("standalone : " + document.getXmlStandalone());
					

	   * Etape 4 : r�cup�ration de l'Element racine
	   */
	  final Element racine = document.getDocumentElement();
		
	  //Affichage de l'�l�ment racine
		/*
	  System.out.println("\n*************RACINE************");
	  System.out.println(racine.getNodeName());
		

	   * Etape 5 : r�cup�ration des elements
	   */
	  final NodeList racineNoeuds = racine.getChildNodes();
	  final int nbRacineNoeuds = racineNoeuds.getLength();
	  
			
	  for (int i = 0; i<nbRacineNoeuds; i++) 
	  {
	    if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) 
	    {
	      final Element element = (Element) racineNoeuds.item(i);
				
		  //Affichage d'une element
		  if(element.getNodeName().equals("url"))
			  dbi.setUrl(element.getTextContent());
		  else if(element.getNodeName().equals("user"))
			  dbi.setUser(element.getTextContent());
		  else if(element.getNodeName().equals("password"))
			  dbi.setPassword(element.getTextContent());
	  	  
	    }				
	  }			
    }
    catch (final ParserConfigurationException e) 
    {
      e.printStackTrace();
      return null;
    }
    catch (final SAXException e) 
    {
      e.printStackTrace();
      return null;
    }
    catch (final IOException e) 
    {
      e.printStackTrace();
      return null;
    }
    return dbi;
  }
}