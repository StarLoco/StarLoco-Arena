/*      */ package org.jdom.input;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import org.jdom.DefaultJDOMFactory;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.JDOMException;
/*      */ import org.jdom.JDOMFactory;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.XMLFilter;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.helpers.XMLReaderFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SAXBuilder
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: SAXBuilder.java,v $ $Revision: 1.89 $ $Date: 2004/09/03 18:24:28 $ $Name: jdom_1_0 $";
/*      */   private static final String DEFAULT_SAX_DRIVER = "org.apache.xerces.parsers.SAXParser";
/*      */   private boolean validate;
/*      */   private boolean expand = true;
/*      */   private String saxDriverClass;
/*  111 */   private ErrorHandler saxErrorHandler = null;
/*      */ 
/*      */   
/*  114 */   private EntityResolver saxEntityResolver = null;
/*      */ 
/*      */   
/*  117 */   private DTDHandler saxDTDHandler = null;
/*      */ 
/*      */   
/*  120 */   private XMLFilter saxXMLFilter = null;
/*      */ 
/*      */   
/*  123 */   private JDOMFactory factory = (JDOMFactory)new DefaultJDOMFactory();
/*      */ 
/*      */   
/*      */   private boolean ignoringWhite = false;
/*      */ 
/*      */   
/*  129 */   private HashMap features = new HashMap(5);
/*      */ 
/*      */   
/*  132 */   private HashMap properties = new HashMap(5);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean reuseParser = true;
/*      */ 
/*      */ 
/*      */   
/*  141 */   private XMLReader saxParser = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXBuilder() {
/*  149 */     this(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXBuilder(boolean validate) {
/*  162 */     this.validate = validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXBuilder(String saxDriverClass) {
/*  173 */     this(saxDriverClass, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SAXBuilder(String saxDriverClass, boolean validate) {
/*  187 */     this.saxDriverClass = saxDriverClass;
/*  188 */     this.validate = validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDriverClass() {
/*  197 */     return this.saxDriverClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JDOMFactory getFactory() {
/*  205 */     return this.factory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFactory(JDOMFactory factory) {
/*  215 */     this.factory = factory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getValidation() {
/*  224 */     return this.validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValidation(boolean validate) {
/*  234 */     this.validate = validate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*  242 */     return this.saxErrorHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setErrorHandler(ErrorHandler errorHandler) {
/*  251 */     this.saxErrorHandler = errorHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  260 */     return this.saxEntityResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityResolver(EntityResolver entityResolver) {
/*  269 */     this.saxEntityResolver = entityResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/*  278 */     return this.saxDTDHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDHandler(DTDHandler dtdHandler) {
/*  287 */     this.saxDTDHandler = dtdHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLFilter getXMLFilter() {
/*  296 */     return this.saxXMLFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXMLFilter(XMLFilter xmlFilter) {
/*  305 */     this.saxXMLFilter = xmlFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIgnoringElementContentWhitespace() {
/*  316 */     return this.ignoringWhite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoringElementContentWhitespace(boolean ignoringWhite) {
/*  331 */     this.ignoringWhite = ignoringWhite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReuseParser() {
/*  342 */     return this.reuseParser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReuseParser(boolean reuseParser) {
/*  358 */     this.reuseParser = reuseParser;
/*  359 */     this.saxParser = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String name, boolean value) {
/*  379 */     this.features.put(name, new Boolean(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String name, Object value) {
/*  399 */     this.properties.put(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(InputSource in) throws JDOMException, IOException {
/*  414 */     SAXHandler contentHandler = null;
/*      */ 
/*      */     
/*      */     try {
/*  418 */       contentHandler = createContentHandler();
/*  419 */       configureContentHandler(contentHandler);
/*      */       
/*  421 */       XMLReader parser = this.saxParser;
/*  422 */       if (parser == null) {
/*      */         
/*  424 */         parser = createParser();
/*      */ 
/*      */         
/*  427 */         if (this.saxXMLFilter != null) {
/*      */           
/*  429 */           XMLFilter root = this.saxXMLFilter;
/*  430 */           while (root.getParent() instanceof XMLFilter) {
/*  431 */             root = (XMLFilter)root.getParent();
/*      */           }
/*  433 */           root.setParent(parser);
/*      */ 
/*      */           
/*  436 */           parser = this.saxXMLFilter;
/*      */         } 
/*      */ 
/*      */         
/*  440 */         configureParser(parser, contentHandler);
/*      */         
/*  442 */         if (this.reuseParser == true) {
/*  443 */           this.saxParser = parser;
/*      */         
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  449 */         configureParser(parser, contentHandler);
/*      */       } 
/*      */ 
/*      */       
/*  453 */       parser.parse(in);
/*      */       
/*  455 */       return contentHandler.getDocument();
/*      */     }
/*  457 */     catch (SAXParseException e) {
/*  458 */       Document doc = contentHandler.getDocument();
/*  459 */       if (doc.hasRootElement() == false) {
/*  460 */         doc = null;
/*      */       }
/*      */       
/*  463 */       String systemId = e.getSystemId();
/*  464 */       if (systemId != null) {
/*  465 */         throw new JDOMParseException("Error on line " + 
/*  466 */             e.getLineNumber() + " of document " + systemId, e, doc);
/*      */       }
/*  468 */       throw new JDOMParseException("Error on line " + 
/*  469 */           e.getLineNumber(), e, doc);
/*      */     
/*      */     }
/*  472 */     catch (SAXException e) {
/*  473 */       throw new JDOMParseException("Error in building: " + 
/*  474 */           e.getMessage(), e, contentHandler.getDocument());
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*  480 */       contentHandler = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SAXHandler createContentHandler() {
/*  490 */     SAXHandler contentHandler = new SAXHandler(this.factory);
/*  491 */     return contentHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureContentHandler(SAXHandler contentHandler) {
/*  504 */     contentHandler.setExpandEntities(this.expand);
/*  505 */     contentHandler.setIgnoringElementContentWhitespace(this.ignoringWhite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLReader createParser() throws JDOMException {
/*  521 */     XMLReader parser = null;
/*  522 */     if (this.saxDriverClass != null) {
/*      */       
/*      */       try {
/*  525 */         parser = XMLReaderFactory.createXMLReader(this.saxDriverClass);
/*      */ 
/*      */         
/*  528 */         setFeaturesAndProperties(parser, true);
/*      */       }
/*  530 */       catch (SAXException e) {
/*  531 */         throw new JDOMException("Could not load " + this.saxDriverClass, e);
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/*  540 */         Class factoryClass = 
/*  541 */           Class.forName("org.jdom.input.JAXPParserFactory");
/*      */         
/*  543 */         Method createParser = 
/*  544 */           factoryClass.getMethod("createParser", 
/*  545 */             new Class[] { boolean.class, Map.class, Map.class });
/*      */ 
/*      */         
/*  548 */         parser = (XMLReader)createParser.invoke(null, 
/*  549 */             new Object[] { new Boolean(this.validate), 
/*  550 */               this.features, this.properties });
/*      */ 
/*      */         
/*  553 */         setFeaturesAndProperties(parser, false);
/*      */       }
/*  555 */       catch (JDOMException e) {
/*  556 */         throw e;
/*      */       }
/*  558 */       catch (NoClassDefFoundError noClassDefFoundError) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  564 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  571 */     if (parser == null) {
/*      */       try {
/*  573 */         parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
/*      */         
/*  575 */         this.saxDriverClass = parser.getClass().getName();
/*      */ 
/*      */         
/*  578 */         setFeaturesAndProperties(parser, true);
/*      */       }
/*  580 */       catch (SAXException e) {
/*  581 */         throw new JDOMException(
/*  582 */             "Could not load default SAX parser: org.apache.xerces.parsers.SAXParser", e);
/*      */       } 
/*      */     }
/*      */     
/*  586 */     return parser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureParser(XMLReader parser, SAXHandler contentHandler) throws JDOMException {
/*  605 */     parser.setContentHandler(contentHandler);
/*      */     
/*  607 */     if (this.saxEntityResolver != null) {
/*  608 */       parser.setEntityResolver(this.saxEntityResolver);
/*      */     }
/*      */     
/*  611 */     if (this.saxDTDHandler != null) {
/*  612 */       parser.setDTDHandler(this.saxDTDHandler);
/*      */     } else {
/*  614 */       parser.setDTDHandler(contentHandler);
/*      */     } 
/*      */     
/*  617 */     if (this.saxErrorHandler != null) {
/*  618 */       parser.setErrorHandler(this.saxErrorHandler);
/*      */     } else {
/*  620 */       parser.setErrorHandler(new BuilderErrorHandler());
/*      */     } 
/*      */ 
/*      */     
/*  624 */     boolean lexicalReporting = false;
/*      */     try {
/*  626 */       parser.setProperty("http://xml.org/sax/handlers/LexicalHandler", 
/*  627 */           contentHandler);
/*  628 */       lexicalReporting = true;
/*  629 */     } catch (SAXNotSupportedException sAXNotSupportedException) {
/*      */     
/*  631 */     } catch (SAXNotRecognizedException sAXNotRecognizedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  636 */     if (!lexicalReporting) {
/*      */       try {
/*  638 */         parser.setProperty(
/*  639 */             "http://xml.org/sax/properties/lexical-handler", 
/*  640 */             contentHandler);
/*  641 */         lexicalReporting = true;
/*  642 */       } catch (SAXNotSupportedException sAXNotSupportedException) {
/*      */       
/*  644 */       } catch (SAXNotRecognizedException sAXNotRecognizedException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     if (!this.expand) {
/*      */       try {
/*  652 */         parser.setProperty(
/*  653 */             "http://xml.org/sax/properties/declaration-handler", 
/*  654 */             contentHandler);
/*  655 */       } catch (SAXNotSupportedException sAXNotSupportedException) {
/*      */       
/*  657 */       } catch (SAXNotRecognizedException sAXNotRecognizedException) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFeaturesAndProperties(XMLReader parser, boolean coreFeatures) throws JDOMException {
/*  667 */     Iterator iter = this.features.keySet().iterator();
/*  668 */     while (iter.hasNext()) {
/*  669 */       String name = iter.next();
/*  670 */       Boolean value = (Boolean)this.features.get(name);
/*  671 */       internalSetFeature(parser, name, value.booleanValue(), name);
/*      */     } 
/*      */ 
/*      */     
/*  675 */     iter = this.properties.keySet().iterator();
/*  676 */     while (iter.hasNext()) {
/*  677 */       String name = iter.next();
/*  678 */       internalSetProperty(parser, name, this.properties.get(name), name);
/*      */     } 
/*      */     
/*  681 */     if (coreFeatures) {
/*      */       
/*      */       try {
/*  684 */         internalSetFeature(parser, 
/*  685 */             "http://xml.org/sax/features/validation", 
/*  686 */             this.validate, "Validation");
/*  687 */       } catch (JDOMException e) {
/*      */ 
/*      */ 
/*      */         
/*  691 */         if (this.validate) {
/*  692 */           throw e;
/*      */         }
/*      */       } 
/*      */       
/*  696 */       internalSetFeature(parser, 
/*  697 */           "http://xml.org/sax/features/namespaces", 
/*  698 */           true, "Namespaces");
/*  699 */       internalSetFeature(parser, 
/*  700 */           "http://xml.org/sax/features/namespace-prefixes", 
/*  701 */           true, "Namespace prefixes");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  712 */     try { if (parser.getFeature("http://xml.org/sax/features/external-general-entities") != this.expand) {
/*  713 */         parser.setFeature("http://xml.org/sax/features/external-general-entities", this.expand);
/*      */       } }
/*      */     
/*  716 */     catch (SAXNotRecognizedException sAXNotRecognizedException) {  }
/*  717 */     catch (SAXNotSupportedException sAXNotSupportedException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void internalSetFeature(XMLReader parser, String feature, boolean value, String displayName) throws JDOMException {
/*      */     try {
/*  727 */       parser.setFeature(feature, value);
/*  728 */     } catch (SAXNotSupportedException sAXNotSupportedException) {
/*  729 */       throw new JDOMException(
/*  730 */           String.valueOf(displayName) + " feature not supported for SAX driver " + parser.getClass().getName());
/*  731 */     } catch (SAXNotRecognizedException sAXNotRecognizedException) {
/*  732 */       throw new JDOMException(
/*  733 */           String.valueOf(displayName) + " feature not recognized for SAX driver " + parser.getClass().getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void internalSetProperty(XMLReader parser, String property, Object value, String displayName) throws JDOMException {
/*      */     try {
/*  746 */       parser.setProperty(property, value);
/*  747 */     } catch (SAXNotSupportedException sAXNotSupportedException) {
/*  748 */       throw new JDOMException(
/*  749 */           String.valueOf(displayName) + " property not supported for SAX driver " + parser.getClass().getName());
/*  750 */     } catch (SAXNotRecognizedException sAXNotRecognizedException) {
/*  751 */       throw new JDOMException(
/*  752 */           String.valueOf(displayName) + " property not recognized for SAX driver " + parser.getClass().getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(InputStream in) throws JDOMException, IOException {
/*  770 */     return build(new InputSource(in));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(File file) throws JDOMException, IOException {
/*      */     try {
/*  788 */       URL url = fileToURL(file);
/*  789 */       return build(url);
/*  790 */     } catch (MalformedURLException e) {
/*  791 */       throw new JDOMException("Error in building", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(URL url) throws JDOMException, IOException {
/*  809 */     String systemID = url.toExternalForm();
/*  810 */     return build(new InputSource(systemID));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(InputStream in, String systemId) throws JDOMException, IOException {
/*  829 */     InputSource src = new InputSource(in);
/*  830 */     src.setSystemId(systemId);
/*  831 */     return build(src);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(Reader characterStream) throws JDOMException, IOException {
/*  851 */     return build(new InputSource(characterStream));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(Reader characterStream, String systemId) throws JDOMException, IOException {
/*  873 */     InputSource src = new InputSource(characterStream);
/*  874 */     src.setSystemId(systemId);
/*  875 */     return build(src);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document build(String systemId) throws JDOMException, IOException {
/*  891 */     return build(new InputSource(systemId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static URL fileToURL(File file) throws MalformedURLException {
/*  924 */     StringBuffer buffer = new StringBuffer();
/*  925 */     String path = file.getAbsolutePath();
/*      */ 
/*      */     
/*  928 */     if (File.separatorChar != '/') {
/*  929 */       path = path.replace(File.separatorChar, '/');
/*      */     }
/*      */ 
/*      */     
/*  933 */     if (!path.startsWith("/")) {
/*  934 */       buffer.append('/');
/*      */     }
/*      */ 
/*      */     
/*  938 */     int len = path.length();
/*  939 */     for (int i = 0; i < len; i++) {
/*  940 */       char c = path.charAt(i);
/*  941 */       if (c == ' ') {
/*  942 */         buffer.append("%20");
/*  943 */       } else if (c == '#') {
/*  944 */         buffer.append("%23");
/*  945 */       } else if (c == '%') {
/*  946 */         buffer.append("%25");
/*  947 */       } else if (c == '&') {
/*  948 */         buffer.append("%26");
/*  949 */       } else if (c == ';') {
/*  950 */         buffer.append("%3B");
/*  951 */       } else if (c == '<') {
/*  952 */         buffer.append("%3C");
/*  953 */       } else if (c == '=') {
/*  954 */         buffer.append("%3D");
/*  955 */       } else if (c == '>') {
/*  956 */         buffer.append("%3E");
/*  957 */       } else if (c == '?') {
/*  958 */         buffer.append("%3F");
/*  959 */       } else if (c == '~') {
/*  960 */         buffer.append("%7E");
/*      */       } else {
/*  962 */         buffer.append(c);
/*      */       } 
/*      */     } 
/*      */     
/*  966 */     if (!path.endsWith("/") && file.isDirectory()) {
/*  967 */       buffer.append('/');
/*      */     }
/*      */ 
/*      */     
/*  971 */     return new URL("file", "", buffer.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getExpandEntities() {
/*  981 */     return this.expand;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExpandEntities(boolean expand) {
/* 1008 */     this.expand = expand;
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\input\SAXBuilder.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */