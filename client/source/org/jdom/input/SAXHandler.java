/*     */ package org.jdom.input;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Content;
/*     */ import org.jdom.DefaultJDOMFactory;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.EntityRef;
/*     */ import org.jdom.JDOMFactory;
/*     */ import org.jdom.Namespace;
/*     */ import org.jdom.Parent;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.DeclHandler;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.DefaultHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXHandler
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler, DeclHandler, DTDHandler
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: SAXHandler.java,v $ $Revision: 1.68 $ $Date: 2004/08/31 06:14:05 $ $Name: jdom_1_0 $";
/*  84 */   private static final Map attrNameToTypeMap = new HashMap(13);
/*     */ 
/*     */ 
/*     */   
/*     */   private Document document;
/*     */ 
/*     */ 
/*     */   
/*     */   private Element currentElement;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean atRoot;
/*     */ 
/*     */   
/*     */   private boolean inDTD = false;
/*     */ 
/*     */   
/*     */   private boolean inInternalSubset = false;
/*     */ 
/*     */   
/*     */   private boolean previousCDATA = false;
/*     */ 
/*     */   
/*     */   private boolean inCDATA = false;
/*     */ 
/*     */   
/*     */   private boolean expand = true;
/*     */ 
/*     */   
/*     */   private boolean suppress = false;
/*     */ 
/*     */   
/* 117 */   private int entityDepth = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private List declaredNamespaces;
/*     */ 
/*     */   
/* 124 */   private StringBuffer internalSubset = new StringBuffer();
/*     */ 
/*     */   
/* 127 */   private TextBuffer textBuffer = new TextBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map externalEntities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JDOMFactory factory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ignoringWhite = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Locator locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 158 */     attrNameToTypeMap.put("CDATA", 
/* 159 */         new Integer(1));
/* 160 */     attrNameToTypeMap.put("ID", 
/* 161 */         new Integer(2));
/* 162 */     attrNameToTypeMap.put("IDREF", 
/* 163 */         new Integer(3));
/* 164 */     attrNameToTypeMap.put("IDREFS", 
/* 165 */         new Integer(4));
/* 166 */     attrNameToTypeMap.put("ENTITY", 
/* 167 */         new Integer(5));
/* 168 */     attrNameToTypeMap.put("ENTITIES", 
/* 169 */         new Integer(6));
/* 170 */     attrNameToTypeMap.put("NMTOKEN", 
/* 171 */         new Integer(7));
/* 172 */     attrNameToTypeMap.put("NMTOKENS", 
/* 173 */         new Integer(8));
/* 174 */     attrNameToTypeMap.put("NOTATION", 
/* 175 */         new Integer(9));
/* 176 */     attrNameToTypeMap.put("ENUMERATION", 
/* 177 */         new Integer(10));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXHandler() {
/* 186 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXHandler(JDOMFactory factory) {
/* 198 */     if (factory != null) {
/* 199 */       this.factory = factory;
/*     */     } else {
/* 201 */       this.factory = (JDOMFactory)new DefaultJDOMFactory();
/*     */     } 
/*     */     
/* 204 */     this.atRoot = true;
/* 205 */     this.declaredNamespaces = new ArrayList();
/* 206 */     this.externalEntities = new HashMap();
/*     */     
/* 208 */     this.document = this.factory.document(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pushElement(Element element) {
/* 219 */     if (this.atRoot) {
/* 220 */       this.document.setRootElement(element);
/* 221 */       this.atRoot = false;
/*     */     } else {
/*     */       
/* 224 */       this.factory.addContent((Parent)this.currentElement, (Content)element);
/*     */     } 
/* 226 */     this.currentElement = element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getDocument() {
/* 235 */     return this.document;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JDOMFactory getFactory() {
/* 247 */     return this.factory;
/*     */   }
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
/*     */   public void setExpandEntities(boolean expand) {
/* 260 */     this.expand = expand;
/*     */   }
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
/*     */   public boolean getExpandEntities() {
/* 273 */     return this.expand;
/*     */   }
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
/*     */   public void setIgnoringElementContentWhitespace(boolean ignoringWhite) {
/* 288 */     this.ignoringWhite = ignoringWhite;
/*     */   }
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
/*     */   public boolean getIgnoringElementContentWhitespace() {
/* 302 */     return this.ignoringWhite;
/*     */   }
/*     */   
/*     */   public void startDocument() {
/* 306 */     if (this.locator != null) {
/* 307 */       this.document.setBaseURI(this.locator.getSystemId());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void externalEntityDecl(String name, String publicID, String systemID) throws SAXException {
/* 324 */     this.externalEntities.put(name, new String[] { publicID, systemID });
/*     */     
/* 326 */     if (!this.inInternalSubset)
/*     */       return; 
/* 328 */     this.internalSubset.append("  <!ENTITY ")
/* 329 */       .append(name);
/* 330 */     appendExternalId(publicID, systemID);
/* 331 */     this.internalSubset.append(">\n");
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value) throws SAXException {
/* 348 */     if (!this.inInternalSubset)
/*     */       return; 
/* 350 */     this.internalSubset.append("  <!ATTLIST ")
/* 351 */       .append(eName)
/* 352 */       .append(' ')
/* 353 */       .append(aName)
/* 354 */       .append(' ')
/* 355 */       .append(type)
/* 356 */       .append(' ');
/* 357 */     if (valueDefault != null) {
/* 358 */       this.internalSubset.append(valueDefault);
/*     */     } else {
/* 360 */       this.internalSubset.append('"')
/* 361 */         .append(value)
/* 362 */         .append('"');
/*     */     } 
/* 364 */     if (valueDefault != null && valueDefault.equals("#FIXED")) {
/* 365 */       this.internalSubset.append(" \"")
/* 366 */         .append(value)
/* 367 */         .append('"');
/*     */     }
/* 369 */     this.internalSubset.append(">\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void elementDecl(String name, String model) throws SAXException {
/* 381 */     if (!this.inInternalSubset)
/*     */       return; 
/* 383 */     this.internalSubset.append("  <!ELEMENT ")
/* 384 */       .append(name)
/* 385 */       .append(' ')
/* 386 */       .append(model)
/* 387 */       .append(">\n");
/*     */   }
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
/*     */   public void internalEntityDecl(String name, String value) throws SAXException {
/* 401 */     if (!this.inInternalSubset)
/*     */       return; 
/* 403 */     this.internalSubset.append("  <!ENTITY ");
/* 404 */     if (name.startsWith("%")) {
/* 405 */       this.internalSubset.append("% ").append(name.substring(1));
/*     */     } else {
/* 407 */       this.internalSubset.append(name);
/*     */     } 
/* 409 */     this.internalSubset.append(" \"")
/* 410 */       .append(value)
/* 411 */       .append("\">\n");
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 428 */     if (this.suppress)
/*     */       return; 
/* 430 */     flushCharacters();
/*     */     
/* 432 */     if (this.atRoot) {
/* 433 */       this.factory.addContent((Parent)this.document, (Content)this.factory.processingInstruction(target, data));
/*     */     } else {
/* 435 */       this.factory.addContent((Parent)getCurrentElement(), 
/* 436 */           (Content)this.factory.processingInstruction(target, data));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {
/* 452 */     if (name.startsWith("%"))
/*     */       return; 
/* 454 */     flushCharacters();
/*     */     
/* 456 */     this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.entityRef(name));
/*     */   }
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
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 469 */     if (this.suppress)
/*     */       return; 
/* 471 */     Namespace ns = Namespace.getNamespace(prefix, uri);
/* 472 */     this.declaredNamespaces.add(ns);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/* 495 */     if (this.suppress)
/*     */       return; 
/* 497 */     Element element = null;
/*     */     
/* 499 */     if (namespaceURI != null && !namespaceURI.equals("")) {
/* 500 */       String prefix = "";
/*     */ 
/*     */       
/* 503 */       if (!qName.equals(localName)) {
/* 504 */         int split = qName.indexOf(":");
/* 505 */         prefix = qName.substring(0, split);
/*     */       } 
/* 507 */       Namespace elementNamespace = 
/* 508 */         Namespace.getNamespace(prefix, namespaceURI);
/* 509 */       element = this.factory.element(localName, elementNamespace);
/*     */     } else {
/* 511 */       element = this.factory.element(localName);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 516 */     if (this.declaredNamespaces.size() > 0) {
/* 517 */       transferNamespaces(element);
/*     */     }
/*     */ 
/*     */     
/* 521 */     for (int i = 0, len = atts.getLength(); i < len; i++) {
/* 522 */       Attribute attribute = null;
/*     */       
/* 524 */       String attLocalName = atts.getLocalName(i);
/* 525 */       String attQName = atts.getQName(i);
/* 526 */       int attType = getAttributeType(atts.getType(i));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 532 */       if (!attQName.startsWith("xmlns:") && !attQName.equals("xmlns")) {
/*     */ 
/*     */ 
/*     */         
/* 536 */         if (!attQName.equals(attLocalName)) {
/* 537 */           String attPrefix = attQName.substring(0, attQName.indexOf(":"));
/* 538 */           Namespace attNs = Namespace.getNamespace(attPrefix, 
/* 539 */               atts.getURI(i));
/*     */           
/* 541 */           attribute = this.factory.attribute(attLocalName, atts.getValue(i), 
/* 542 */               attType, attNs);
/*     */         } else {
/* 544 */           attribute = this.factory.attribute(attLocalName, atts.getValue(i), 
/* 545 */               attType);
/*     */         } 
/* 547 */         this.factory.setAttribute(element, attribute);
/*     */       } 
/*     */     } 
/* 550 */     flushCharacters();
/*     */     
/* 552 */     if (this.atRoot) {
/* 553 */       this.document.setRootElement(element);
/* 554 */       this.atRoot = false;
/*     */     } else {
/* 556 */       this.factory.addContent((Parent)getCurrentElement(), (Content)element);
/*     */     } 
/* 558 */     this.currentElement = element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transferNamespaces(Element element) {
/* 568 */     Iterator i = this.declaredNamespaces.iterator();
/* 569 */     while (i.hasNext()) {
/* 570 */       Namespace ns = i.next();
/* 571 */       if (ns != element.getNamespace()) {
/* 572 */         element.addNamespaceDeclaration(ns);
/*     */       }
/*     */     } 
/* 575 */     this.declaredNamespaces.clear();
/*     */   }
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
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 589 */     if (this.suppress || length == 0) {
/*     */       return;
/*     */     }
/* 592 */     if (this.previousCDATA != this.inCDATA) {
/* 593 */       flushCharacters();
/*     */     }
/*     */     
/* 596 */     this.textBuffer.append(ch, start, length);
/*     */   }
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
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 611 */     if (!this.ignoringWhite) {
/* 612 */       characters(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushCharacters() throws SAXException {
/* 623 */     flushCharacters(this.textBuffer.toString());
/* 624 */     this.textBuffer.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushCharacters(String data) throws SAXException {
/* 635 */     if (data.length() == 0) {
/* 636 */       this.previousCDATA = this.inCDATA;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 651 */     if (this.previousCDATA) {
/* 652 */       this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.cdata(data));
/*     */     } else {
/*     */       
/* 655 */       this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.text(data));
/*     */     } 
/*     */     
/* 658 */     this.previousCDATA = this.inCDATA;
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/* 676 */     if (this.suppress)
/*     */       return; 
/* 678 */     flushCharacters();
/*     */     
/* 680 */     if (!this.atRoot) {
/* 681 */       Parent p = this.currentElement.getParent();
/* 682 */       if (p instanceof Document) {
/* 683 */         this.atRoot = true;
/*     */       } else {
/*     */         
/* 686 */         this.currentElement = (Element)p;
/*     */       } 
/*     */     } else {
/*     */       
/* 690 */       throw new SAXException(
/* 691 */           "Ill-formed XML document (missing opening tag for " + 
/* 692 */           localName + ")");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicID, String systemID) throws SAXException {
/* 709 */     flushCharacters();
/*     */     
/* 711 */     this.factory.addContent((Parent)this.document, (Content)this.factory.docType(name, publicID, systemID));
/* 712 */     this.inDTD = true;
/* 713 */     this.inInternalSubset = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {
/* 723 */     this.document.getDocType().setInternalSubset(this.internalSubset.toString());
/* 724 */     this.inDTD = false;
/* 725 */     this.inInternalSubset = false;
/*     */   }
/*     */   
/*     */   public void startEntity(String name) throws SAXException {
/* 729 */     this.entityDepth++;
/*     */     
/* 731 */     if (this.expand || this.entityDepth > 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 737 */     if (name.equals("[dtd]")) {
/* 738 */       this.inInternalSubset = false;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 743 */     if (!this.inDTD && 
/* 744 */       !name.equals("amp") && 
/* 745 */       !name.equals("lt") && 
/* 746 */       !name.equals("gt") && 
/* 747 */       !name.equals("apos") && 
/* 748 */       !name.equals("quot"))
/*     */     {
/* 750 */       if (!this.expand) {
/* 751 */         String pub = null;
/* 752 */         String sys = null;
/* 753 */         String[] ids = (String[])this.externalEntities.get(name);
/* 754 */         if (ids != null) {
/* 755 */           pub = ids[0];
/* 756 */           sys = ids[1];
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 765 */         if (!this.atRoot) {
/* 766 */           flushCharacters();
/* 767 */           EntityRef entity = this.factory.entityRef(name, pub, sys);
/*     */ 
/*     */           
/* 770 */           this.factory.addContent((Parent)getCurrentElement(), (Content)entity);
/*     */         } 
/* 772 */         this.suppress = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void endEntity(String name) throws SAXException {
/* 778 */     this.entityDepth--;
/* 779 */     if (this.entityDepth == 0)
/*     */     {
/*     */       
/* 782 */       this.suppress = false;
/*     */     }
/* 784 */     if (name.equals("[dtd]")) {
/* 785 */       this.inInternalSubset = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 795 */     if (this.suppress)
/*     */       return; 
/* 797 */     this.inCDATA = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 804 */     if (this.suppress)
/*     */       return; 
/* 806 */     this.previousCDATA = true;
/* 807 */     this.inCDATA = false;
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 824 */     if (this.suppress)
/*     */       return; 
/* 826 */     flushCharacters();
/*     */     
/* 828 */     String commentText = new String(ch, start, length);
/* 829 */     if (this.inDTD && this.inInternalSubset && this.expand == false) {
/* 830 */       this.internalSubset.append("  <!--")
/* 831 */         .append(commentText)
/* 832 */         .append("-->\n");
/*     */       return;
/*     */     } 
/* 835 */     if (!this.inDTD && !commentText.equals("")) {
/* 836 */       if (this.atRoot) {
/* 837 */         this.factory.addContent((Parent)this.document, (Content)this.factory.comment(commentText));
/*     */       } else {
/* 839 */         this.factory.addContent((Parent)getCurrentElement(), (Content)this.factory.comment(commentText));
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
/*     */ 
/*     */   
/*     */   public void notationDecl(String name, String publicID, String systemID) throws SAXException {
/* 854 */     if (!this.inInternalSubset)
/*     */       return; 
/* 856 */     this.internalSubset.append("  <!NOTATION ")
/* 857 */       .append(name);
/* 858 */     appendExternalId(publicID, systemID);
/* 859 */     this.internalSubset.append(">\n");
/*     */   }
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
/*     */   public void unparsedEntityDecl(String name, String publicID, String systemID, String notationName) throws SAXException {
/* 874 */     if (!this.inInternalSubset)
/*     */       return; 
/* 876 */     this.internalSubset.append("  <!ENTITY ")
/* 877 */       .append(name);
/* 878 */     appendExternalId(publicID, systemID);
/* 879 */     this.internalSubset.append(" NDATA ")
/* 880 */       .append(notationName);
/* 881 */     this.internalSubset.append(">\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendExternalId(String publicID, String systemID) {
/* 892 */     if (publicID != null) {
/* 893 */       this.internalSubset.append(" PUBLIC \"")
/* 894 */         .append(publicID)
/* 895 */         .append('"');
/*     */     }
/* 897 */     if (systemID != null) {
/* 898 */       if (publicID == null) {
/* 899 */         this.internalSubset.append(" SYSTEM ");
/*     */       } else {
/*     */         
/* 902 */         this.internalSubset.append(' ');
/*     */       } 
/* 904 */       this.internalSubset.append('"')
/* 905 */         .append(systemID)
/* 906 */         .append('"');
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getCurrentElement() throws SAXException {
/* 917 */     if (this.currentElement == null) {
/* 918 */       throw new SAXException(
/* 919 */           "Ill-formed XML document (multiple root elements detected)");
/*     */     }
/* 921 */     return this.currentElement;
/*     */   }
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
/*     */   
/*     */   private static int getAttributeType(String typeName) {
/* 937 */     Integer type = (Integer)attrNameToTypeMap.get(typeName);
/* 938 */     if (type == null) {
/* 939 */       if (typeName != null && typeName.length() > 0 && 
/* 940 */         typeName.charAt(0) == '(')
/*     */       {
/*     */ 
/*     */         
/* 944 */         return 10;
/*     */       }
/*     */       
/* 947 */       return 0;
/*     */     } 
/*     */     
/* 950 */     return type.intValue();
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 967 */     this.locator = locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locator getDocumentLocator() {
/* 978 */     return this.locator;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\input\SAXHandler.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */