import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import Service.SentenceTokenizer;
import model.Sentence;
import model.WrapperText;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SentenceTokenizerTest {

    private String data = null;
    private String bigData = null;

    @Test
    public void testMarshalling() throws JAXBException {
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        Sentence sentence = new Sentence();
        List<Sentence> sentenceList = new ArrayList<>();
        sentence.getWord().add("Hi");
        sentence.getWord().add("Hello");
        sentenceList.add(sentence);
        WrapperText text = new WrapperText();
        text.setSentence(sentenceList);
        sentenceTokenizer.toXML(text, "small");
        File file = new File("src//main//resources//small.xml");
        assertTrue(file.exists());

    }

    @Before
    public void readFile() throws Exception {
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        data = sentenceTokenizer.readFileAsString("small.in");
        bigData = sentenceTokenizer.readFileAsString("large.in");
        assertNotNull(data);
    }

    @Test
    public void convertSmallDataToXML() throws Exception {
        this.readFile();
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        sentenceTokenizer.convertToFile(data, "XML", "small");
        File file = new File("src//main//resources//small.xml");
        assertTrue(file.exists());
    }

    @Test
    public void convertBigDataToXML() throws Exception {
        this.readFile();
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        sentenceTokenizer.convertToFile(bigData, "XML", "large");
        File file = new File("src//main//resources//large.xml");
        assertTrue(file.exists());
    }

    @Test
    public void convertSmallDataToCSV() throws Exception {
        this.readFile();
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        sentenceTokenizer.convertToFile(data, "CSV", "small");
        File file = new File("src//main//resources//small.csv");
        assertTrue(file.exists());
    }

    @Test
    public void convertBigDataToCSV() throws Exception {
        this.readFile();
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        sentenceTokenizer.convertToFile(bigData, "CSV", "large");
        File file = new File("src//main//resources//large.csv");
        assertTrue(file.exists());
    }

}
