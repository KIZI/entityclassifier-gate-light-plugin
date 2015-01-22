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
import gate.creole.metadata.CreoleResource;

/**
 * @author Milan Dojchinovski
 * <milan (at) dojchinovski (dot) mk>
 * Twitter: @m1ci
 * www: http://dojchinovski.mk
 */
@CreoleResource(name = "Entityclassifier.eu Light REST API PR", comment = "Perform named entity recognition over a GATE corpus")
public class THDClientPR extends AbstractLanguageAnalyser implements ProcessingResource {

    private String apikey;
    private String outputASName;
    private String annotationsName;
    private String dbpediaOntologyLocation;
    private String knowledgeBase;
    private String entityType;
    private String lang;
    private String provenance;
    private String longEntityLinking;
    
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
                outputASName,
                annotationsName,
                knowledgeBase,
                entityType,
                lang,
                provenance,
                longEntityLinking);
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
    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    /**
     * @return the outputASName
     */
    public String getOutputASName() {
        return outputASName;
    }

    /**
     * @param outputASName the outputASName to set
     */
    public void setOutputASName(String outputASName) {
        this.outputASName = outputASName;
    }

    /**
     * @return the annotationsName
     */
    public String getAnnotationsName() {
        return annotationsName;
    }

    /**
     * @param annotationsName the annotationsName to set
     */
    public void setAnnotationsName(String annotationsName) {
        this.annotationsName = annotationsName;
    }

    /**
     * @return the dbpediaOntologyLocation
     */
    public String getDbpediaOntologyLocation() {
        return dbpediaOntologyLocation;
    }

    /**
     * @param dbpediaOntologyLocation the dbpediaOntologyLocation to set
     */
    public void setDbpediaOntologyLocation(String dbpediaOntologyLocation) {
        this.dbpediaOntologyLocation = dbpediaOntologyLocation;
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
    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    /**
     * @return the longEntityLinking
     */
    public String getLongEntityLinking() {
        return longEntityLinking;
    }

    /**
     * @param longEntityLinking the longEntityLinking to set
     */
    public void setLongEntityLinking(String longEntityLinking) {
        this.longEntityLinking = longEntityLinking;
    }
}
