package app.services.xmlServices;

import app.dto.LawDataDTO;
import app.utils.ApiFetcher;

public class XmlService {
    String url = "https://www.retsinformation.dk/eli/lta/2021/1853/xml";
    String xml = ApiFetcher.getXml(url);
    LawDataDTO data;
    XMLExtractor extractor = new XMLExtractor();

    public void extractAndPrintXML(){
        data = extractor.extract(xml);
        System.out.println("\n########LAW CONTENT########");
        System.out.println("Title:\n" + data.getTitle());
        System.out.println("Content:\n" + data.getContent());
    }
}


