package org.example.nlp_java.stanfordnlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class NER_POS {

    public static void main(String[] args) {
        String text = "John smith is the lead actor in hollywood movie Sniper.";

        // Set up pipeline properties
        Properties prop = new Properties();
        try {
            InputStream input = new FileInputStream(new File("/home/abin/my_works/github_works/nlp_java/src/main/java/org/example/nlp_java/stanfordnlp/eng.properties"));
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //build pipeline using annotators given in the "annotators" property
        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);

        CoreDocument document = new CoreDocument(text);
        // To run all annotators in the text
        pipeline.annotate(document);

        // List all the part-of-speech tags for the sentence
        CoreSentence sentence = document.sentences().get(0);
        System.out.println("POS TAGS:- ");
        List<String> posTags = sentence.posTags();
        System.out.println(posTags);

        // List all the NER tags for the sentence
        List<String> nerTags = sentence.nerTags();
        System.out.println("NER TAGS:- ");
        System.out.println(nerTags);

        for (CoreEntityMention em : document.entityMentions())
            System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType());

        System.out.println("tokens and ner tags");

        String tokensAndNERTags = document.tokens().stream().map(token -> "("+token.word()+","+token.ner()+")").collect(
                Collectors.joining(" "));
        System.out.println(tokensAndNERTags);
    }
}
