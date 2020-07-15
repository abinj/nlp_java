package org.example.nlp_java.opennlp.train;

import opennlp.tools.namefind.*;
import opennlp.tools.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        TokenNameFinder nameFinder = new NameFinderME(nameFinderModel);
        String[] testSentence = {"Alisa", "Fernandes", "is", "a", "tourist", "from", "Spain"};
        Span[] names = nameFinder.find(testSentence);
        Arrays.stream(names).forEach(System.out::println);
    }
}
