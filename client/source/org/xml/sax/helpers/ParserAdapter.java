/*      */ package org.xml.sax.helpers;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Vector;
/*      */ import org.xml.sax.AttributeList;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.DocumentHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.Locator;
/*      */ import org.xml.sax.Parser;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.XMLReader;
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
/*      */ public class ParserAdapter
/*      */   implements XMLReader, DocumentHandler
/*      */ {
/*      */   private static final String FEATURES = "http://xml.org/sax/features/";
/*      */   private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
/*      */   private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
/*      */   private static final String XMLNS_URIs = "http://xml.org/sax/features/xmlns-uris";
/*      */   private NamespaceSupport nsSupport;
/*      */   private AttributeListAdapter attAdapter;
/*      */   
/*      */   public ParserAdapter() throws SAXException {
/*   81 */     String str = System.getProperty("org.xml.sax.parser");
/*      */     
/*      */     try {
/*   84 */       setup(ParserFactory.makeParser());
/*   85 */     } catch (ClassNotFoundException classNotFoundException) {
/*   86 */       throw new SAXException("Cannot find SAX1 driver class " + str, classNotFoundException);
/*      */     
/*      */     }
/*   89 */     catch (IllegalAccessException illegalAccessException) {
/*   90 */       throw new SAXException("SAX1 driver class " + str + " found but cannot be loaded", illegalAccessException);
/*      */ 
/*      */     
/*      */     }
/*   94 */     catch (InstantiationException instantiationException) {
/*   95 */       throw new SAXException("SAX1 driver class " + str + " loaded but cannot be instantiated", instantiationException);
/*      */ 
/*      */     
/*      */     }
/*   99 */     catch (ClassCastException classCastException) {
/*  100 */       throw new SAXException("SAX1 driver class " + str + " does not implement org.xml.sax.Parser");
/*      */ 
/*      */     
/*      */     }
/*  104 */     catch (NullPointerException nullPointerException) {
/*  105 */       throw new SAXException("System property org.xml.sax.parser not specified");
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
/*      */ 
/*      */   
/*      */   public ParserAdapter(Parser paramParser) {
/*  125 */     setup(paramParser);
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
/*      */   private void setup(Parser paramParser) {
/*  138 */     if (paramParser == null) {
/*  139 */       throw new NullPointerException("Parser argument must not be null");
/*      */     }
/*      */     
/*  142 */     this.parser = paramParser;
/*  143 */     this.atts = new AttributesImpl();
/*  144 */     this.nsSupport = new NamespaceSupport();
/*  145 */     this.attAdapter = new AttributeListAdapter();
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String paramString, boolean paramBoolean) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  181 */     if (paramString.equals("http://xml.org/sax/features/namespaces")) {
/*  182 */       checkNotParsing("feature", paramString);
/*  183 */       this.namespaces = paramBoolean;
/*  184 */       if (!this.namespaces && !this.prefixes) {
/*  185 */         this.prefixes = true;
/*      */       }
/*  187 */     } else if (paramString.equals("http://xml.org/sax/features/namespace-prefixes")) {
/*  188 */       checkNotParsing("feature", paramString);
/*  189 */       this.prefixes = paramBoolean;
/*  190 */       if (!this.prefixes && !this.namespaces) {
/*  191 */         this.namespaces = true;
/*      */       }
/*  193 */     } else if (paramString.equals("http://xml.org/sax/features/xmlns-uris")) {
/*  194 */       checkNotParsing("feature", paramString);
/*  195 */       this.uris = paramBoolean;
/*      */     } else {
/*  197 */       throw new SAXNotRecognizedException("Feature: " + paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFeature(String paramString) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  219 */     if (paramString.equals("http://xml.org/sax/features/namespaces"))
/*  220 */       return this.namespaces; 
/*  221 */     if (paramString.equals("http://xml.org/sax/features/namespace-prefixes"))
/*  222 */       return this.prefixes; 
/*  223 */     if (paramString.equals("http://xml.org/sax/features/xmlns-uris")) {
/*  224 */       return this.uris;
/*      */     }
/*  226 */     throw new SAXNotRecognizedException("Feature: " + paramString);
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
/*      */   public void setProperty(String paramString, Object paramObject) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  247 */     throw new SAXNotRecognizedException("Property: " + paramString);
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
/*      */   public Object getProperty(String paramString) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  267 */     throw new SAXNotRecognizedException("Property: " + paramString);
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
/*      */   public void setEntityResolver(EntityResolver paramEntityResolver) {
/*  279 */     this.entityResolver = paramEntityResolver;
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
/*      */   public EntityResolver getEntityResolver() {
/*  291 */     return this.entityResolver;
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
/*      */   public void setDTDHandler(DTDHandler paramDTDHandler) {
/*  303 */     this.dtdHandler = paramDTDHandler;
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
/*      */   public DTDHandler getDTDHandler() {
/*  315 */     return this.dtdHandler;
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
/*      */   public void setContentHandler(ContentHandler paramContentHandler) {
/*  327 */     this.contentHandler = paramContentHandler;
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
/*      */   public ContentHandler getContentHandler() {
/*  339 */     return this.contentHandler;
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
/*      */   public void setErrorHandler(ErrorHandler paramErrorHandler) {
/*  351 */     this.errorHandler = paramErrorHandler;
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
/*      */   public ErrorHandler getErrorHandler() {
/*  363 */     return this.errorHandler;
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
/*      */   public void parse(String paramString) throws IOException, SAXException {
/*  381 */     parse(new InputSource(paramString));
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
/*      */   public void parse(InputSource paramInputSource) throws IOException, SAXException {
/*  399 */     if (this.parsing) {
/*  400 */       throw new SAXException("Parser is already in use");
/*      */     }
/*  402 */     setupParser();
/*  403 */     this.parsing = true;
/*      */     try {
/*  405 */       this.parser.parse(paramInputSource);
/*      */     } finally {
/*  407 */       this.parsing = false;
/*      */     } 
/*  409 */     this.parsing = false;
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
/*      */   public void setDocumentLocator(Locator paramLocator) {
/*  428 */     this.locator = paramLocator;
/*  429 */     if (this.contentHandler != null) {
/*  430 */       this.contentHandler.setDocumentLocator(paramLocator);
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
/*      */   public void startDocument() throws SAXException {
/*  446 */     if (this.contentHandler != null) {
/*  447 */       this.contentHandler.startDocument();
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
/*      */   public void endDocument() throws SAXException {
/*  463 */     if (this.contentHandler != null) {
/*  464 */       this.contentHandler.endDocument();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(String paramString, AttributeList paramAttributeList) throws SAXException {
/*  487 */     Vector<SAXException> vector = null;
/*      */ 
/*      */ 
/*      */     
/*  491 */     if (!this.namespaces) {
/*  492 */       if (this.contentHandler != null) {
/*  493 */         this.attAdapter.setAttributeList(paramAttributeList);
/*  494 */         this.contentHandler.startElement("", "", paramString.intern(), this.attAdapter);
/*      */       } 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  502 */     this.nsSupport.pushContext();
/*  503 */     int i = paramAttributeList.getLength();
/*      */     
/*      */     byte b;
/*  506 */     for (b = 0; b < i; b++) {
/*  507 */       String str2, str1 = paramAttributeList.getName(b);
/*      */       
/*  509 */       if (!str1.startsWith("xmlns")) {
/*      */         continue;
/*      */       }
/*      */       
/*  513 */       int j = str1.indexOf(':');
/*      */ 
/*      */       
/*  516 */       if (j == -1 && str1.length() == 5)
/*  517 */       { str2 = ""; }
/*  518 */       else { if (j != 5) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  523 */         str2 = str1.substring(j + 1); }
/*      */       
/*  525 */       String str3 = paramAttributeList.getValue(b);
/*  526 */       if (!this.nsSupport.declarePrefix(str2, str3)) {
/*  527 */         reportError("Illegal Namespace prefix: " + str2);
/*      */       
/*      */       }
/*  530 */       else if (this.contentHandler != null) {
/*  531 */         this.contentHandler.startPrefixMapping(str2, str3);
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/*      */     
/*  537 */     this.atts.clear();
/*  538 */     for (b = 0; b < i; b++) {
/*  539 */       String str1 = paramAttributeList.getName(b);
/*  540 */       String str2 = paramAttributeList.getType(b);
/*  541 */       String str3 = paramAttributeList.getValue(b);
/*      */ 
/*      */       
/*  544 */       if (str1.startsWith("xmlns")) {
/*      */         String str;
/*  546 */         int j = str1.indexOf(':');
/*      */         
/*  548 */         if (j == -1 && str1.length() == 5) {
/*  549 */           str = "";
/*  550 */         } else if (j != 5) {
/*      */ 
/*      */           
/*  553 */           str = null;
/*      */         } else {
/*  555 */           str = str1.substring(6);
/*      */         } 
/*      */         
/*  558 */         if (str != null) {
/*  559 */           if (this.prefixes) {
/*  560 */             if (this.uris) {
/*      */ 
/*      */ 
/*      */               
/*  564 */               this.atts.addAttribute("http://www.w3.org/XML/1998/namespace", str, str1.intern(), str2, str3);
/*      */             } else {
/*      */               
/*  567 */               this.atts.addAttribute("", "", str1.intern(), str2, str3);
/*      */             } 
/*      */           }
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/*  576 */         String[] arrayOfString = processName(str1, true, true);
/*  577 */         this.atts.addAttribute(arrayOfString[0], arrayOfString[1], arrayOfString[2], str2, str3);
/*      */       }
/*  579 */       catch (SAXException sAXException) {
/*  580 */         if (vector == null)
/*  581 */           vector = new Vector(); 
/*  582 */         vector.addElement(sAXException);
/*  583 */         this.atts.addAttribute("", str1, str1, str2, str3);
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/*  588 */     if (vector != null && this.errorHandler != null) {
/*  589 */       for (b = 0; b < vector.size(); b++) {
/*  590 */         this.errorHandler.error((SAXParseException)vector.elementAt(b));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  595 */     if (this.contentHandler != null) {
/*  596 */       String[] arrayOfString = processName(paramString, false, false);
/*  597 */       this.contentHandler.startElement(arrayOfString[0], arrayOfString[1], arrayOfString[2], this.atts);
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
/*      */   
/*      */   public void endElement(String paramString) throws SAXException {
/*  616 */     if (!this.namespaces) {
/*  617 */       if (this.contentHandler != null) {
/*  618 */         this.contentHandler.endElement("", "", paramString.intern());
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  624 */     String[] arrayOfString = processName(paramString, false, false);
/*  625 */     if (this.contentHandler != null) {
/*  626 */       this.contentHandler.endElement(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
/*  627 */       Enumeration<String> enumeration = this.nsSupport.getDeclaredPrefixes();
/*  628 */       while (enumeration.hasMoreElements()) {
/*  629 */         String str = enumeration.nextElement();
/*  630 */         this.contentHandler.endPrefixMapping(str);
/*      */       } 
/*      */     } 
/*  633 */     this.nsSupport.popContext();
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
/*      */   public void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException {
/*  651 */     if (this.contentHandler != null) {
/*  652 */       this.contentHandler.characters(paramArrayOfchar, paramInt1, paramInt2);
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
/*      */   
/*      */   public void ignorableWhitespace(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException {
/*  671 */     if (this.contentHandler != null) {
/*  672 */       this.contentHandler.ignorableWhitespace(paramArrayOfchar, paramInt1, paramInt2);
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
/*      */   public void processingInstruction(String paramString1, String paramString2) throws SAXException {
/*  690 */     if (this.contentHandler != null) {
/*  691 */       this.contentHandler.processingInstruction(paramString1, paramString2);
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
/*      */   private void setupParser() {
/*  708 */     if (!this.prefixes && !this.namespaces) {
/*  709 */       throw new IllegalStateException();
/*      */     }
/*  711 */     this.nsSupport.reset();
/*  712 */     if (this.uris) {
/*  713 */       this.nsSupport.setNamespaceDeclUris(true);
/*      */     }
/*  715 */     if (this.entityResolver != null) {
/*  716 */       this.parser.setEntityResolver(this.entityResolver);
/*      */     }
/*  718 */     if (this.dtdHandler != null) {
/*  719 */       this.parser.setDTDHandler(this.dtdHandler);
/*      */     }
/*  721 */     if (this.errorHandler != null) {
/*  722 */       this.parser.setErrorHandler(this.errorHandler);
/*      */     }
/*  724 */     this.parser.setDocumentHandler(this);
/*  725 */     this.locator = null;
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
/*      */   private String[] processName(String paramString, boolean paramBoolean1, boolean paramBoolean2) throws SAXException {
/*  746 */     String[] arrayOfString = this.nsSupport.processName(paramString, this.nameParts, paramBoolean1);
/*      */     
/*  748 */     if (arrayOfString == null) {
/*  749 */       if (paramBoolean2)
/*  750 */         throw makeException("Undeclared prefix: " + paramString); 
/*  751 */       reportError("Undeclared prefix: " + paramString);
/*  752 */       arrayOfString = new String[3];
/*  753 */       arrayOfString[1] = ""; arrayOfString[0] = "";
/*  754 */       arrayOfString[2] = paramString.intern();
/*      */     } 
/*  756 */     return arrayOfString;
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
/*      */   void reportError(String paramString) throws SAXException {
/*  770 */     if (this.errorHandler != null) {
/*  771 */       this.errorHandler.error(makeException(paramString));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SAXParseException makeException(String paramString) {
/*  782 */     if (this.locator != null) {
/*  783 */       return new SAXParseException(paramString, this.locator);
/*      */     }
/*  785 */     return new SAXParseException(paramString, null, null, -1, -1);
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
/*      */   private void checkNotParsing(String paramString1, String paramString2) throws SAXNotSupportedException {
/*  804 */     if (this.parsing) {
/*  805 */       throw new SAXNotSupportedException("Cannot change " + paramString1 + ' ' + paramString2 + " while parsing");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean parsing = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  822 */   private String[] nameParts = new String[3];
/*      */   
/*  824 */   private Parser parser = null;
/*      */   
/*  826 */   private AttributesImpl atts = null;
/*      */ 
/*      */   
/*      */   private boolean namespaces = true;
/*      */ 
/*      */   
/*      */   private boolean prefixes = false;
/*      */   
/*      */   private boolean uris = false;
/*      */   
/*      */   Locator locator;
/*      */   
/*  838 */   EntityResolver entityResolver = null;
/*  839 */   DTDHandler dtdHandler = null;
/*  840 */   ContentHandler contentHandler = null;
/*  841 */   ErrorHandler errorHandler = null;
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
/*      */   final class AttributeListAdapter
/*      */     implements Attributes
/*      */   {
/*      */     private AttributeList qAtts;
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
/*      */     void setAttributeList(AttributeList param1AttributeList) {
/*  882 */       this.qAtts = param1AttributeList;
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
/*      */     public int getLength() {
/*  894 */       return this.qAtts.getLength();
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
/*      */     
/*      */     public String getURI(int param1Int) {
/*  907 */       return "";
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
/*      */     
/*      */     public String getLocalName(int param1Int) {
/*  920 */       return "";
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
/*      */     public String getQName(int param1Int) {
/*  932 */       return this.qAtts.getName(param1Int).intern();
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
/*      */     public String getType(int param1Int) {
/*  944 */       return this.qAtts.getType(param1Int).intern();
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
/*      */     public String getValue(int param1Int) {
/*  956 */       return this.qAtts.getValue(param1Int);
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
/*      */ 
/*      */     
/*      */     public int getIndex(String param1String1, String param1String2) {
/*  970 */       return -1;
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
/*      */     
/*      */     public int getIndex(String param1String) {
/*  983 */       int i = ParserAdapter.this.atts.getLength();
/*  984 */       for (byte b = 0; b < i; b++) {
/*  985 */         if (this.qAtts.getName(b).equals(param1String)) {
/*  986 */           return b;
/*      */         }
/*      */       } 
/*  989 */       return -1;
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
/*      */     
/*      */     public String getType(String param1String1, String param1String2) {
/* 1002 */       return null;
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
/*      */     public String getType(String param1String) {
/* 1014 */       return this.qAtts.getType(param1String).intern();
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
/*      */     
/*      */     public String getValue(String param1String1, String param1String2) {
/* 1027 */       return null;
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
/*      */     public String getValue(String param1String) {
/* 1039 */       return this.qAtts.getValue(param1String);
/*      */     }
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\helpers\ParserAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */