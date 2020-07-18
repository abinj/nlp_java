package org.example.nlp_java.stanfordnlp;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.Mention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.IntPair;

import java.util.*;

public class CoReference {

    public static void main(String[] args) {
        Annotation document = new Annotation("Barack Obama was born in Hawaii.  He is the president. Obama was elected in 2008. Emily is beautiful. She just started her journey.");
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
        pipeline.annotate(document);

        for (CorefChain cc: document.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
            System.out.println("\t" + cc);
        }
        //cc.mentions.get(0).mentionSpan
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            System.out.println("-------");
            System.out.println("mentions");
            for (Mention m: sentence.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)) {
                System.out.println("\t" + m);
            }
        }
    }

//    public static List<String> getSentencesHaveCoReference(Annotation document) {
//        List<String> CoRefSentences = new ArrayList<>();
//
//        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
//
//        }
//    }
}
