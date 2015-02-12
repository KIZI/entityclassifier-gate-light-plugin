# Entityclassifier.eu API LIGHT Plugin for GATE #

This is a GATE plugin for the Entityclassifier.eu NER REST API. You can use it perform Named Entity Recognition over English, German and Dutch written texts. It is a **light version** of the GATE plugin. If you don't want to depend on the REST API you can use the **stand-alone** version of the API. Using the light version you can perform:

* ***Entity Spotting*** - each detected named entity in the text will be marked with ```NamedEntity``` annotation.

* ***Entity Disambiguation*** - each spotted entity is further disambiguated with a an URI from the *DBpedia namespace*. E.g. http://dbpedia.org/resource/Prague for the entity Prague.

* ***Entity Classification*** - for each disambiguated entity we provide set of types represented as *DBpedia instances* or *DBpedia Ontology* types. When using the plugin you can set the ```typesFilter``` parameter to filter out only types as DBpedia instances, DBpedia Ontology types, or both. The possible parameter values are:
    * **dbo** - filter only DBpedia Ontology types
    * **dbinstance** - filter only types defined as DBpedia instances
    * **all** - the entity types can be either DBpedia Ontology clases or DBpedia instances


### How to start using it? ###

#### Steps: ####
1. **Add our plugin repository.** Open ```CREOLE Plugin Manager -> Configuration```, check if the ```User Plugin Directory``` is set, otherwise configure it. Finally, add the following repository to the list of plugin repositories:

    ```
    http://ner.vse.cz/GATE/gate-update-site.xml
    ```

2. **Install the plugin.**  In the ```CREOLE Plugin Manager``` go to ```Available to Install``` tab, check ```Entityclassifier_NER_Stand_Alone``` and click ```Apply all```, then go to the ```Available plugins``` tab and select ```load now``` and ```load always```.

3. **Request a free API key for the REST API.** Request an API key using the Web form http://entityclassifier.eu/thd/docs/#apikeyformfree!

4. **Create a corpus pipeline.** We recommend following order of the processing resources in the pipeline:

    * Document Reset PR
    * Entityclassifier.eu Light REST API PR - create an instance of the processing resource and add it at the end of the pipeline.

5. **Create a document corpus and run the pipeline.**

6. **Check the results!** - the spotted entities are annotated as ```NamedEntity``` annotations. Each entity has a ```disambiguation URI``` which is encoded as annotation feature ```itsrdf:taIdentRefX=...```. Each assigned type is also present as annotation feature in the form of ```rdf:typeX=...```

![light-gate-plugin-1.png](https://bitbucket.org/repo/M6EXM6/images/1690205146-light-gate-plugin-1.png)

***Enjoy discovering entities!***



Comparison: Stand-Alone vs. Light REST API plugin
------

![Plugin comparison](https://docs.google.com/drawings/d/1bdfpW38qSLj806KvDd6eMeq3YBOS4dTMynlQ-_5OLBg/pub?w=554&h=337)



If you need any help/support with the plugin free to contact us. Bugs please report as issues to this repository.

License
------

Licensed under the [GNU General Public License Version 3 (GNU GPLv3)](http://www.gnu.org/licenses/gpl.html).

Copyright (c) 2014-2015

* Milan Dojchinovski - <milan.dojchinovski@fit.cvut.cz>

* Tomas Kliegr - <tomas.kliegr@vse.cz>