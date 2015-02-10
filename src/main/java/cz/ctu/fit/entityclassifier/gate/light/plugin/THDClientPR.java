/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.ctu.fit.entityclassifier.gate.light.plugin;

import gate.Corpus;
import gate.Document;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.RunTime;

/**
 * @author Milan Dojchinovski
 * <milan (at) dojchinovski (dot) mk>
 * Twitter: @m1ci
 * www: http://dojchinovski.mk
 */
@CreoleResource(name = "Entityclassifier.eu Light REST API PR", comment = "Perform named entity recognition over a GATE corpus")
public class THDClientPR extends AbstractLanguageAnalyser implements ProcessingResource {

    private String apikey;
    private String knowledgeBase;
    private String entityType;
    private String lang;
    private String provenance;
    private String typesFilter;
    private String linkingMethod;
    private String spottingMethod;
    
    public THDClientPR() {
    }

    @Override
    public Resource init() throws ResourceInstantiationException {
        System.out.println("THD Client was successfully inicialized.");
        return super.init();
    }
    
    @Override
    public void reInit() throws ResourceInstantiationException {
        init();
    }
        
    @Override
    public void execute() throws ExecutionException {
        Document doc = getDocument();
        TextProcessor.getInstance().processDocument(
                doc,
                apikey,
                knowledgeBase,
                entityType,
                lang,
                provenance,
                typesFilter,
                linkingMethod,
                spottingMethod);
    }
    
    @Override
    public Corpus getCorpus() {
        return corpus;
    }

    @Override
    public void setCorpus(Corpus corpus) {
        this.corpus = corpus;
    }

    /**
     * @return the apikey
     */
    public String getApikey() {
        return apikey;
    }

    /**
     * @param apikey the apikey to set
     */
    @CreoleParameter(comment = "API key.", defaultValue="")
    @RunTime
    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    /**
     * @return the knowledgeBase
     */
    public String getKnowledgeBase() {
        return knowledgeBase;
    }

    /**
     * @param knowledgeBase the knowledgeBase to set
     */
    @CreoleParameter(comment = "Specify which knowledge base should be used to retrieve types. Possible values: linkedHypernymsDataset/local/live", defaultValue="linkedHypernymsDataset")
    @RunTime
    public void setKnowledgeBase(String knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    /**
     * @return the entityType
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * @param entityType the entityType to set
     */
    @CreoleParameter(comment = "The types of entities to be processed from the text. Possible values: ne/ce/all", defaultValue="ne")
    @RunTime
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang the lang to set
     */
    @CreoleParameter(comment = "Language of the input text. You can choose between English, German and Dutch. Values: en/de/nl", defaultValue="en")
    @RunTime
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the provenance
     */
    public String getProvenance() {
        return provenance;
    }

    /**
     * @param provenance the provenance to set
     */
    @CreoleParameter(comment = "Provenance of the results. Possible values: thd/dbpedia/yago", defaultValue="thd,dbpedia")
    @RunTime
    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    /**
     * @return the typesFilter
     */
    public String getTypesFilter() {
        return typesFilter;
    }

    /**
     * @param typesFilter
     */
    @CreoleParameter(comment = "Filter types to selected namespaces. You can filter out only types as DBpedia instances, DBpedia Ontology types, or both of them. Values: dbo/dbinstance/all", defaultValue="dbo")
    @RunTime
    public void setTypesFilter(String typesFilter) {
        this.typesFilter = typesFilter;
    }

    /**
     * @return the linkingMethod
     */
    public String getLinkingMethod() {
        return linkingMethod;
    }

    /**
     * @param linkingMethod
     */
    @CreoleParameter(comment = "You can choose preferred entity linking (disambiguation) method. Possible values: SFISearch/LuceneSearch/LuceneSearchSkipDisPage/WikipediaSearch/AllVoting/SurfaceFormSimilarity", defaultValue="LuceneSearchSkipDisPage")
    @RunTime
    public void setLinkingMethod(String linkingMethod) {
        this.linkingMethod = linkingMethod;
    }

    /**
     * @return the typesFilter
     */
    public String getSpottingMethod() {
        return spottingMethod;
    }

    /**
     * @param typesFilter
     */
    @CreoleParameter(comment = "You can choose preferred entity spotting (recognition) method. Possible values: grammars/CRF", defaultValue="grammars")
    @RunTime
    public void setSpottingMethod(String spottingMethod) {
        this.spottingMethod = spottingMethod;
    }

}
