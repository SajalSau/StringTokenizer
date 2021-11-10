package Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.Sentence;
import model.WrapperText;

public class SentenceTokenizer {


    public String readFileAsString(String fileName) throws Exception {
        SentenceTokenizer sentenceBreaker = new SentenceTokenizer();
        URL url = sentenceBreaker.getClass()
                .getClassLoader()
                .getResource(fileName);
        File file = new File(url.getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        return content;
    }

    public void convertToFile(String sentenceInput, String inputType, String inputSize) throws JAXBException, FileNotFoundException {
        sentenceInput = sentenceInput.replaceAll("\\s+", " ").replaceAll("[():]", "");
        String[] arrOfSentence = sentenceInput.trim().split("(?<!\\bMr|\\bMs|\\bMrs)[!\\.]");
        List<Sentence> listOfSentence = new ArrayList<>();
        prepareWrapperText(arrOfSentence, listOfSentence);
        WrapperText text = new WrapperText();
        text.setSentence(listOfSentence);
        if (!inputType.isEmpty() && inputType.equalsIgnoreCase("CSV")) {
            toCSV(text, inputSize);
        } else if (!inputType.isEmpty() && inputType.equalsIgnoreCase("XML")) {
            toXML(text, inputSize);
        } else {
            throw new RuntimeException("Invalid Input");
        }

    }

    private void prepareWrapperText(String[] arrOfSentence, List<Sentence> listOfSentence) {
        String[] arrOfWord;
        Sentence sentence;
        for (String str : arrOfSentence) {
            sentence = new Sentence();
            arrOfWord = str.trim().split("[ ,\\-]+");
            List<String> list = Arrays.stream(arrOfWord).map(String::trim).collect(Collectors.toList());
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
            sentence.setWord(list);
            listOfSentence.add(sentence);
        }
    }

    public void toXML(WrapperText listOfSentence, String inputSize) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WrapperText.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(listOfSentence, new File("src//main//resources//" + inputSize + ".xml"));
    }

    public void toCSV(WrapperText listOfSentence, String inputSize) {
        try {
            int counter = 1;
            int headerLength = listOfSentence.getSentence().stream().mapToInt(sentence -> sentence.getWord().size())
                    .filter(sentence -> sentence >= 0).max().orElse(0);

            PrintWriter printWriter = new PrintWriter(new File("src//main//resources//" + inputSize + ".csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("SL No. ,");
            for (int i = 1; i <= headerLength; i++) {
                sb.append(" Word " + i + ",");
            }
            for (Sentence sentence : listOfSentence.getSentence()) {
                sb.append("\n");
                sb.append("Sentence " + counter + ", ");
                for (String str : sentence.getWord()) {
                    sb.append(" " + str + ",");
                }
                counter++;
            }
            printWriter.write(sb.toString());
            printWriter.close();
        } catch (Exception e) {
            System.err.println("Exception occurred");
            e.printStackTrace();
        }

    }

}
