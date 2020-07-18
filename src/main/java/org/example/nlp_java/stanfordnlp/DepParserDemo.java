package org.example.nlp_java.stanfordnlp;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.logging.Redwood;

import java.util.*;

public class DepParserDemo {
    /** A logger for this class */
    private static final Redwood.RedwoodChannels log = Redwood.channels(DepParserDemo.class);

    private DepParserDemo() {} // static main method only

    public static void main(String[] args) {

        String text;
        if (args.length > 0) {
            text = IOUtils.slurpFileNoExceptions(args[0], "utf-8");
        } else {
            text = "John smith is the lead actor in hollywood movie Sniper.";
        }
        Annotation ann = new Annotation(text);

        Properties props = PropertiesUtils.asProperties(
                "annotators", "tokenize,ssplit,pos,depparse",
                "depparse.model", DependencyParser.DEFAULT_MODEL
        );


        AnnotationPipeline pipeline = new StanfordCoreNLP(props);
        pipeline.annotate(ann);
        SemanticGraph sg = null;
        for (CoreMap sent : ann.get(CoreAnnotations.SentencesAnnotation.class)) {
            sg = sent.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            log.info(IOUtils.eolChar + sg.toString(SemanticGraph.OutputFormat.LIST));
        }
        List<SemanticGraphEdge> edges = sg.edgeListSorted();
        System.out.println("Test");
        String indexWord = "Sniper";
        List<String> connections = new ArrayList<>();
        for (SemanticGraphEdge edge : edges) {
            if (edge.getGovernor().word().equals(indexWord)) {
                connections.add(edge.getDependent().word());
            }
        }
        System.out.println(connections.toString());
    }
}
