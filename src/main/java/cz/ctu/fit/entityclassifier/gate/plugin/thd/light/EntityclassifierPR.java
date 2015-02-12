/*
 * #%L
 * Stand Alone GATE plugin of the Entityclassifier.eu NER
 * %%
 * Copyright (C) 2015 Knowledge Engineering Group (KEG) and Web Intelligence Research Group (WIRG) - Milan Dojchinovski (milan.dojchinovski@fit.cvut.cz)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package cz.ctu.fit.entityclassifier.gate.plugin.thd.light;

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
 <milan.dojchinovski@fit.cvut.cz>
 Twitter: @m1ci
 www: http://dojchinovski.mk
 */
@CreoleResource(name = "Entityclassifier.eu Light REST API PR",
        comment = "Perform named entity recognition using a REST API.")
public class EntityclassifierPR extends AbstractLanguageAnalyser implements ProcessingResource {

    private String apikey;
    private String knowledgeBase;
    private String entityType;
    private String lang;
    private String provenance;
    private String typesFilter;
    private String linkingMethod;
    private String spottingMethod;
    private String apiEndpoint;
    
    public EntityclassifierPR() {
    }

    @Override
    public Resource init() throws ResourceInstantiationException {
        System.out.println("Entityclassifier.eu REST API client was successfully inicialized.");
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
                spottingMethod,
                apiEndpoint);
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
     * @param apikey parameter
     */
    @CreoleParameter(comment = "API key.", defaultValue="")
    @RunTime
    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getKnowledgeBase() {
        return knowledgeBase;
    }

    /**
     * @param knowledgeBase parameter
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
     * @param entityType parameter - the types of entities to recognize.
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
     * @param lang parameter - the language of the input text
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
     * @param typesFilter parameter
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
     * @param linkingMethod parameter
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
     * @param typesFilter parameter
     */
    @CreoleParameter(comment = "You can choose preferred entity spotting (recognition) method. Possible values: grammars/CRF", defaultValue="grammars")
    @RunTime
    public void setSpottingMethod(String spottingMethod) {
        this.spottingMethod = spottingMethod;
    }

    /**
     * @return the apiEndpoint
     */
    public String getApiEndpoint() {
        return apiEndpoint;
    }

    /**
     * @param apiEndpoint the apiEndpoint to set
     */
    @CreoleParameter(comment = "You can specify the URL of the API endpoint.", defaultValue="http://entityclassifier.eu/thd/api/v2/extraction")
    @RunTime
    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

}
