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

import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Milan Dojchinovski
 <milan.dojchinovski@fit.cvut.cz>
 Twitter: @m1ci
 www: http://dojchinovski.mk
 */
public class TextProcessor {
    
    private static TextProcessor textProcessor = null;
    
    public static TextProcessor getInstance(){
        if(textProcessor == null){
            textProcessor = new TextProcessor();
        }
        return textProcessor;
    }
    
    public void processDocument(
            Document doc,
            String apikey,
            String knowledgeBase,
            String entityType,
            String lang,
            String provenance,
            String typesFilter,
            String linkingMethod,
            String spottingMethod,
            String apiEndpoint){
        
        try {

            String path = apiEndpoint+"?apikey="+apikey+"&format=xml";

            if(knowledgeBase != null){
                path += "&knowledge_base="+knowledgeBase;
            }
            if(entityType != null){
                path += "&entity_type="+entityType;
            }
            if(lang != null){
                path += "&lang="+lang;
            }
            if(provenance != null){
                path += "&provenance="+provenance;
            }
            if(typesFilter != null){
                path += "&types_filter="+typesFilter;
            }
            if(linkingMethod != null){
                path += "&linking_method="+linkingMethod;
            }
            if(spottingMethod != null){
                path += "&spotting_method="+spottingMethod;
            }

            URL url = new URL(path);

            StringBuffer buffer = new StringBuffer();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "text/plain; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            
            OutputStream os = connection.getOutputStream();
            os.write(doc.getContent().toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            InputStream is = connection.getInputStream();
            Reader isr = new InputStreamReader(is,"UTF-8");
            Reader in = new BufferedReader(isr);
            int ch;

            while ((ch = in.read()) > -1) {
                buffer.append((char) ch);
            }
            in.close();
            isr.close();
            String result = buffer.toString();
//            System.out.println(result);
            AnnotationSet as_default = doc.getAnnotations();
//            AnnotationSet annSetEntities = as_default.get("NamedEntity");            
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document xmldoc = (org.w3c.dom.Document) dBuilder.parse(new ByteArrayInputStream(result.getBytes()));
            NodeList eList = xmldoc.getElementsByTagName("entity");
                    
            for (int i = 0; i < eList.getLength(); i++) {
                
                Node nEntity = eList.item(i);

                if (nEntity.getNodeType() == Node.ELEMENT_NODE) {
                            
                    Element entity = (Element)nEntity;
                    String foundEntityType = entity.getElementsByTagName("entityType").item(0).getTextContent();
                    long startOffset = Long.parseLong(entity.getElementsByTagName("startOffset").item(0).getTextContent());
                    long endOffset = Long.parseLong(entity.getElementsByTagName("endOffset").item(0).getTextContent());
                    NodeList underlyingStringObj = entity.getElementsByTagName("underlyingString");
                    
                    if(underlyingStringObj.getLength() > 0) {
                        
                        String underlyingString = underlyingStringObj.item(0).getTextContent();
                        NodeList tList = entity.getElementsByTagName("type");
                        FeatureMap fm = gate.Factory.newFeatureMap();

                        int entityURIcounter    = 0;
                        int entityTypeCounter   = 0;
                        
                        ArrayList<String> assignedEntityUris = new ArrayList();
                        ArrayList<String> assignedTypes      = new ArrayList();

                        for (int j = 0; j < tList.getLength(); j++) {
                            
                            Node nType = tList.item(j);

                            if (nType.getNodeType() == Node.ELEMENT_NODE) {

                                Element type = (Element)nType;
                                Node entityLabel = type.getElementsByTagName("entityLabel").item(0);
                                Node entityURI = type.getElementsByTagName("entityURI").item(0);

                                if(entityLabel != null && entityURI != null) {
                                    if(!assignedEntityUris.contains(entityURI.getTextContent())) {
                                        fm.put("entity", entityLabel.getTextContent());
                                        fm.put("itsrdf:taIdentRef"+entityURIcounter, entityURI.getTextContent());
                                        fm.put("entityType", foundEntityType);
                                        assignedEntityUris.add(entityURI.getTextContent());
                                        entityURIcounter++;
                                    }
                                }

                                Node typeLabel = type.getElementsByTagName("typeLabel").item(0);
                                Node typeURI = type.getElementsByTagName("typeURI").item(0);
//
                                if(typeLabel != null && typeURI != null) {
                                    if(!assignedTypes.contains(typeURI.getTextContent())) {
                                        fm.put("typeLabel"+entityTypeCounter, typeLabel.getTextContent());
                                        fm.put("rdf:type"+entityTypeCounter, typeURI.getTextContent());
//                                        fm.put("entityType", foundEntityType);
                                        assignedTypes.add(typeURI.getTextContent());
                                        entityTypeCounter++;
                                    }
                                }
                            }
                        }
                        as_default.add(startOffset, endOffset, "NamedEntity", fm);
                    } else {
//                        System.out.println("Underlying string not present");
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TextProcessor.class.getName()).log(Level.SEVERE, null, ex);        
        }
    }
}
