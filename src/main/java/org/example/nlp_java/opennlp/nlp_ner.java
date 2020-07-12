package org.example.nlp_java.opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class nlp_ner {
    public static void main(String[] args) {
        try (InputStream tokenStream =
                     new FileInputStream(new File("/home/abin/my_works/nlp/opennlp/en-token.bin"));
             InputStream personModelStream = new FileInputStream(
                     new File("/home/abin/my_works/nlp/opennlp/en-ner-person.bin"));) {
            TokenizerModel tm = new TokenizerModel(tokenStream);
            TokenizerME tokenizerME = new TokenizerME(tm);

            TokenNameFinderModel tnfm = new TokenNameFinderModel(personModelStream);
            NameFinderME nf = new NameFinderME(tnfm);
            String sentence = "Mr. David wants to purchase the property.";
            String[] tokens = tokenizerME.tokenize(sentence);
            Span[] spans = nf.find(tokens);
            Arrays.stream(spans).forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
