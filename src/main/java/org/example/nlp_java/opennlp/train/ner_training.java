package org.example.nlp_java.opennlp.train;

import opennlp.tools.namefind.*;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;


//Annotations should be provided for Named Entities in the training file using the below format.
//<START:named_entitiy_type>Named Entity<END> remaining sentence.
public class ner_training {

    public static void main(String[] args) {

        // Read the training data
        InputStreamFactory in = null;
        try {
            in = new MarkableFileInputStreamFactory(
                    new File("/home/abin/my_works/github_works/nlp_java/src/main/java/org/example/nlp_java/opennlp/train/AnnotatedSentences.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectStream sampleStream = null;
        try {
            sampleStream = new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Training Parameters
        TrainingParameters parameters = new TrainingParameters();
        parameters.put(TrainingParameters.ITERATIONS_PARAM, 70);
        parameters.put(TrainingParameters.CUTOFF_PARAM, 1);

        // Train the model
        TokenNameFinderModel nameFinderModel = null;
        try {
            nameFinderModel = NameFinderME.train("en", null, sampleStream,
                    parameters, TokenNameFinderFactory.create(null, null
                            , Collections.emptyMap(), new BioCodec()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save the model to a file
        File output = new File("ner-custom-model.bin");
        try {
            FileOutputStream outputStream = new FileOutputStream(output);
            nameFinderModel.serialize(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Testing the model
        String testSentence = "So I called Julie , a friend who's still in contact with him.";
        getTokens(nameFinderModel, testSentence
                , "/home/abin/my_works/nlp/opennlp/en-token.bin");
    }

    public static Span[] getTokens(TokenNameFinderModel nameFinderModel, String sentence, String tokenizerPath) {
        TokenNameFinder nameFinder = new NameFinderME(nameFinderModel);
        InputStream tokenStream = null;
        Span[] spans = null;
        try {
            tokenStream = new FileInputStream(new File(tokenizerPath));
            TokenizerModel tm = new TokenizerModel(tokenStream);
            TokenizerME tokenizerME = new TokenizerME(tm);
            String[] tokens = tokenizerME.tokenize(sentence);
            spans = nameFinder.find(tokens);
            printResult(spans, tokens);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spans;
    }

    public static void printResult(Span[] names, String[] tokens) {
        Arrays.stream(names).forEach((name)->{
            String personName="";
            for(int i=name.getStart();i<name.getEnd();i++){
                personName+=tokens[i]+" ";
            }
            System.out.println(name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
        });
    }
}
