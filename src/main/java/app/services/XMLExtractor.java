package app.services;

import app.dto.LawDataDTO;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class XMLExtractor  {

    public static LawDataDTO extract(String xml) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));

        String title = "";
        NodeList titleNodes = doc.getElementsByTagName("DocumentTitle");
        if(titleNodes.getLength() > 0){
            title = titleNodes.item(0).getTextContent();
        }

        NodeList textNodes = doc.getElementsByTagName("Char");
        StringBuilder warrantyText = new StringBuilder();

        for(int i = 0; i < textNodes.getLength(); i++){
        String text = textNodes.item(i).getTextContent();

        if(text.toLowerCase().contains("garanti")){
            warrantyText.append(text).append("\n\n");
            }
        }
        return new LawDataDTO(title, warrantyText.toString());
    }
}
