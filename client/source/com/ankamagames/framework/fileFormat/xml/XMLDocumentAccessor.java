/*     */ package com.ankamagames.framework.fileFormat.xml;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentAccessor;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLDocumentAccessor
/*     */   implements DocumentAccessor
/*     */ {
/*  33 */   protected static final Logger m_logger = Logger.getLogger(XMLDocumentAccessor.class);
/*     */   
/*     */   private DocumentBuilder m_documentBuilder;
/*     */   
/*     */   private Document m_document;
/*  38 */   private String m_lastCreatedFile = null;
/*     */   
/*  40 */   private static final XMLDocumentAccessor m_instance = new XMLDocumentAccessor();
/*     */   
/*     */   public static XMLDocumentAccessor getInstance() {
/*  43 */     return m_instance;
/*     */   }
/*     */   
/*     */   public XMLDocumentAccessor() {
/*     */     try {
/*  48 */       this.m_documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/*  49 */     } catch (ParserConfigurationException e) {
/*  50 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(String fileName) throws Exception {
/*     */     try {
/*  62 */       open((new URL(fileName)).openStream());
/*  63 */     } catch (Exception e) {
/*  64 */       m_logger.warn("Impossible d'ouvrir un stream pour l'URL : " + fileName + ", tentative d'ouverture du fichier standard.");
/*  65 */       File xmlFile = new File(fileName);
/*  66 */       if (xmlFile.exists()) {
/*  67 */         this.m_document = this.m_documentBuilder.parse(xmlFile);
/*  68 */         if (this.m_document == null)
/*  69 */           m_logger.error("Impossible de lire le document : " + fileName); 
/*     */       } else {
/*  71 */         this.m_document = null;
/*  72 */         m_logger.error("Le document XML spécifié n'existe pas : " + fileName);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(InputStream stream) throws Exception {
/*  85 */     this.m_document = this.m_documentBuilder.parse(stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean create(String fileName) throws Exception {
/*  90 */     this.m_lastCreatedFile = fileName;
/*     */     
/*  92 */     File xmlfile = new File(fileName);
/*  93 */     return xmlfile.createNewFile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DocumentContainer container) {
/*     */     try {
/* 109 */       if (this.m_document == null) {
/* 110 */         m_logger.error("read() invoqué sur un document non ouvert ( voir : open() )");
/*     */         
/*     */         return;
/*     */       } 
/* 114 */       container.notifyOnLoadBegin();
/*     */       
/* 116 */       Node node = this.m_document.getFirstChild();
/* 117 */       for (; node != null && node.getNodeType() != 1; node = node.getNextSibling());
/*     */       
/* 119 */       XMLDocumentNode rootNode = readNode(node);
/* 120 */       if (rootNode != null) {
/* 121 */         ((XMLDocumentContainer)container).setRootNode(rootNode);
/*     */       }
/* 123 */       container.notifyOnLoadComplete();
/*     */     }
/* 125 */     catch (Exception e) {
/* 126 */       container.notifyOnLoadError("Exception : " + e.getMessage());
/* 127 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private XMLDocumentNode readNode(Node node) {
/* 132 */     if (node == null) {
/* 133 */       return null;
/*     */     }
/* 135 */     String name = node.getNodeName();
/* 136 */     String value = node.getNodeValue();
/*     */ 
/*     */ 
/*     */     
/* 140 */     XMLDocumentNode docNode = new XMLDocumentNode(name, value);
/*     */ 
/*     */     
/* 143 */     NamedNodeMap attributes = node.getAttributes();
/* 144 */     if (attributes != null) {
/* 145 */       for (int i = 0; i < attributes.getLength(); i++) {
/* 146 */         Node a = attributes.item(i);
/* 147 */         name = a.getNodeName();
/* 148 */         value = a.getNodeValue();
/*     */         
/* 150 */         docNode.addParameter(new XMLNodeAttribute(name, value));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 155 */     Node nde = node.getFirstChild();
/* 156 */     while (nde != null) {
/* 157 */       docNode.addChild(readNode(nde));
/* 158 */       nde = nde.getNextSibling();
/*     */     } 
/*     */     
/* 161 */     return docNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(DocumentContainer container) {
/* 171 */     if (container == null)
/* 172 */       return;  if (!(container instanceof XMLDocumentContainer))
/*     */       return; 
/* 174 */     XMLDocumentContainer xmlContainer = (XMLDocumentContainer)container;
/* 175 */     XMLDocumentNode rootNode = xmlContainer.getRootNode();
/*     */     
/* 177 */     xmlContainer.notifyOnSaveBegin();
/*     */ 
/*     */     
/*     */     try {
/* 181 */       FileOutputStream output = new FileOutputStream(new File(this.m_lastCreatedFile));
/*     */       
/* 183 */       writeHeader(output, "UTF-8");
/* 184 */       if (rootNode != null) writeNode(rootNode, output, "UTF-8", 0);
/*     */       
/* 186 */       output.close();
/* 187 */     } catch (Exception e) {
/* 188 */       container.notifyOnLoadError("Exception : " + e.getMessage());
/* 189 */       e.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/* 193 */     xmlContainer.notifyOnSaveComplete();
/*     */   }
/*     */   
/*     */   private void writeHeader(FileOutputStream outStream, String charSet) throws IOException {
/* 197 */     outStream.write(("<?xml version=\"1.0\" encoding=\"" + charSet + "\"?>\n").getBytes(charSet));
/*     */   }
/*     */   
/*     */   private void writeNode(DocumentEntry node, FileOutputStream outStream, String charSet, int tabs) throws IOException {
/* 201 */     if (node == null)
/*     */       return; 
/* 203 */     String name = node.getName();
/* 204 */     String value = node.getStringValue();
/*     */     
/* 206 */     StringBuffer buffer = new StringBuffer();
/* 207 */     for (int i = 0; i < tabs; ) { buffer.append("\t"); i++; }
/* 208 */      String prefix = buffer.toString();
/*     */     
/* 210 */     if (name.equals("#text")) {
/*     */       
/* 212 */       outStream.write(prefix.getBytes(charSet));
/* 213 */       outStream.write(value.getBytes(charSet));
/* 214 */       outStream.write("\n".getBytes(charSet));
/*     */     } else {
/*     */       
/* 217 */       outStream.write(prefix.getBytes(charSet));
/* 218 */       outStream.write("<".getBytes(charSet));
/* 219 */       outStream.write(name.getBytes(charSet));
/* 220 */       writeParameters(node, outStream, charSet);
/* 221 */       if (node.getChildren().size() == 0) outStream.write("/".getBytes(charSet)); 
/* 222 */       outStream.write(">\n".getBytes(charSet));
/*     */ 
/*     */       
/* 225 */       for (DocumentEntry de : node.getChildren()) {
/* 226 */         writeNode(de, outStream, charSet, tabs + 1);
/*     */       }
/*     */ 
/*     */       
/* 230 */       if (node.getChildren().size() > 0) {
/* 231 */         outStream.write(prefix.getBytes(charSet));
/* 232 */         outStream.write("</".getBytes(charSet));
/* 233 */         outStream.write(name.getBytes(charSet));
/* 234 */         outStream.write(">\n".getBytes(charSet));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeParameters(DocumentEntry node, FileOutputStream outStream, String charSet) throws IOException {
/* 240 */     if (node == null)
/*     */       return; 
/* 242 */     for (DocumentEntry de : node.getParameters()) {
/* 243 */       String name = de.getName();
/* 244 */       String value = de.getStringValue();
/*     */       
/* 246 */       outStream.write(" ".getBytes(charSet));
/* 247 */       outStream.write(name.getBytes(charSet));
/* 248 */       outStream.write("=\"".getBytes(charSet));
/* 249 */       outStream.write(value.getBytes(charSet));
/* 250 */       outStream.write("\"".getBytes(charSet));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentContainer getNewDocumentContainer() {
/* 261 */     return new XMLDocumentContainer();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\xml\XMLDocumentAccessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */