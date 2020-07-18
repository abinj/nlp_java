# 1)  Running all annotators on the text.
* Creating a Stanford Core NLP Object with Stanford Core NLP (Properties props) 
* Analyze arbitrary text by annotate(Annotation document).

# 2) CoreMap and CoreLabel
* The output of the annotators is accessed using the data structures CoreMap and CoreLabel.
* A CoreMap is essentially a Map that uses class objects as keys and has values with custom types
* CoreMap<class object,custom types>
* You can get all the sentences in the text  using CoreMap.
```
List <CoreMap> sentences = document.get(SentencesAnnotation.class); 
```
* A CoreLabel is a CoreMap with additional token-specific methods
```
for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
      .....
  }
```

# 3) Interpreting the output
* The output of Annotators needs to be obtained using CoreMap and CoreLabel. We can get token, POS tag and NER label using CoreLabel.

# 4) Getting Token, POS Tag, and NER Label
* To access text of the token 
```
token.get(TextAnnotation.class);
```

* To access POS tag of the token
 ```
 token.get(PartOfSpeechAnnotation.class);
```

* To access NER label of the token
```
token.get(NamedEntityTagAnnotation.class);
```

# 5) Syntactic Tree, Dependency graph and Others
* We can syntactic tree using TreeAnnotation
```
Tree tree = sentence.get(TreeAnnotation.class);  
```

* We can get dependency graph using CollapsedDependenciesAnnotation
```
SemanticGraph dependencies = sentence.get(CollapsedDependenciesAnnotation.class);
```

* We can get map for chain using CorefChainAnnotation
```
Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);  
```



