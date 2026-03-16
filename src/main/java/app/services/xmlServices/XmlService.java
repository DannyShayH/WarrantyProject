package app.services.xmlServices;

import app.daos.LawDataDAO;
import app.dto.LawDataDTO;
import app.entity.LawData;
import app.utils.ApiFetcher;
import jakarta.persistence.EntityManagerFactory;

public class XmlService {
    private final String url = "https://www.retsinformation.dk/eli/lta/2021/1853/xml";
    private final XMLExtractor extractor = new XMLExtractor();
    private final LawDataDAO lawDataDAO;

    public XmlService(EntityManagerFactory emf) {
        this.lawDataDAO = new LawDataDAO(emf);
    }

    public LawData extractAndPersistXML(){
        String xml = ApiFetcher.getXml(url);
        LawDataDTO data = extractor.extract(xml);

        LawData lawData = LawData.builder()
                .title(data.getTitle())
                .content(data.getContent())
                .build();

        lawDataDAO.create(lawData);

        System.out.println("\n########LAW CONTENT########");
        System.out.println("Title:\n" + lawData.getTitle());
        System.out.println("Content:\n" + lawData.getContent());

        return lawData;
    }
}
