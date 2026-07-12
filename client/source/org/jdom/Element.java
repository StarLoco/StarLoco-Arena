/*      */ package org.jdom;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jdom.filter.ElementFilter;
/*      */ import org.jdom.filter.Filter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Element
/*      */   extends Content
/*      */   implements Parent
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: Element.java,v $ $Revision: 1.152 $ $Date: 2004/09/03 06:35:39 $ $Name: jdom_1_0 $";
/*      */   private static final int INITIAL_ARRAY_SIZE = 5;
/*      */   protected String name;
/*      */   protected transient Namespace namespace;
/*      */   protected transient List additionalNamespaces;
/*  105 */   AttributeList attributes = new AttributeList(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   ContentList content = new ContentList(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Element() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(String name, Namespace namespace) {
/*  140 */     setName(name);
/*  141 */     setNamespace(namespace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element(String name) {
/*  152 */     this(name, (Namespace)null);
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
/*      */   public Element(String name, String uri) {
/*  167 */     this(name, Namespace.getNamespace("", uri));
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
/*      */   public Element(String name, String prefix, String uri) {
/*  183 */     this(name, Namespace.getNamespace(prefix, uri));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  192 */     return this.name;
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
/*      */   public Element setName(String name) {
/*  204 */     String reason = Verifier.checkElementName(name);
/*  205 */     if (reason != null) {
/*  206 */       throw new IllegalNameException(name, "element", reason);
/*      */     }
/*  208 */     this.name = name;
/*  209 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Namespace getNamespace() {
/*  218 */     return this.namespace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setNamespace(Namespace namespace) {
/*  229 */     if (namespace == null) {
/*  230 */       namespace = Namespace.NO_NAMESPACE;
/*      */     }
/*      */     
/*  233 */     this.namespace = namespace;
/*  234 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespacePrefix() {
/*  244 */     return this.namespace.getPrefix();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceURI() {
/*  255 */     return this.namespace.getURI();
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
/*      */   public Namespace getNamespace(String prefix) {
/*  270 */     if (prefix == null) {
/*  271 */       return null;
/*      */     }
/*      */     
/*  274 */     if (prefix.equals("xml"))
/*      */     {
/*  276 */       return Namespace.XML_NAMESPACE;
/*      */     }
/*      */ 
/*      */     
/*  280 */     if (prefix.equals(getNamespacePrefix())) {
/*  281 */       return getNamespace();
/*      */     }
/*      */ 
/*      */     
/*  285 */     if (this.additionalNamespaces != null) {
/*  286 */       for (int i = 0; i < this.additionalNamespaces.size(); i++) {
/*  287 */         Namespace ns = this.additionalNamespaces.get(i);
/*  288 */         if (prefix.equals(ns.getPrefix())) {
/*  289 */           return ns;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  295 */     if (this.parent instanceof Element) {
/*  296 */       return ((Element)this.parent).getNamespace(prefix);
/*      */     }
/*      */     
/*  299 */     return null;
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
/*      */   public String getQualifiedName() {
/*  313 */     if (this.namespace.getPrefix().equals("")) {
/*  314 */       return getName();
/*      */     }
/*      */     
/*  317 */     return this.namespace.getPrefix() + ':' + this.name;
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
/*      */   public void addNamespaceDeclaration(Namespace additional) {
/*  339 */     String reason = Verifier.checkNamespaceCollision(additional, this);
/*  340 */     if (reason != null) {
/*  341 */       throw new IllegalAddException(this, additional, reason);
/*      */     }
/*      */     
/*  344 */     if (this.additionalNamespaces == null) {
/*  345 */       this.additionalNamespaces = new ArrayList(5);
/*      */     }
/*      */     
/*  348 */     this.additionalNamespaces.add(additional);
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
/*      */   public void removeNamespaceDeclaration(Namespace additionalNamespace) {
/*  362 */     if (this.additionalNamespaces == null) {
/*      */       return;
/*      */     }
/*  365 */     this.additionalNamespaces.remove(additionalNamespace);
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
/*      */   public List getAdditionalNamespaces() {
/*  382 */     if (this.additionalNamespaces == null) {
/*  383 */       return Collections.EMPTY_LIST;
/*      */     }
/*  385 */     return Collections.unmodifiableList(this.additionalNamespaces);
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
/*      */   public String getValue() {
/*  397 */     StringBuffer buffer = new StringBuffer();
/*      */     
/*  399 */     Iterator itr = getContent().iterator();
/*  400 */     while (itr.hasNext()) {
/*  401 */       Content child = itr.next();
/*  402 */       if (child instanceof Element || child instanceof Text) {
/*  403 */         buffer.append(child.getValue());
/*      */       }
/*      */     } 
/*  406 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRootElement() {
/*  417 */     return this.parent instanceof Document;
/*      */   }
/*      */   
/*      */   public int getContentSize() {
/*  421 */     return this.content.size();
/*      */   }
/*      */   
/*      */   public int indexOf(Content child) {
/*  425 */     return this.content.indexOf(child);
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
/*      */   public String getText() {
/*  451 */     if (this.content.size() == 0) {
/*  452 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  456 */     if (this.content.size() == 1) {
/*  457 */       Object obj = this.content.get(0);
/*  458 */       if (obj instanceof Text) {
/*  459 */         return ((Text)obj).getText();
/*      */       }
/*  461 */       return "";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  466 */     StringBuffer textContent = new StringBuffer();
/*  467 */     boolean hasText = false;
/*      */     
/*  469 */     for (int i = 0; i < this.content.size(); i++) {
/*  470 */       Object obj = this.content.get(i);
/*  471 */       if (obj instanceof Text) {
/*  472 */         textContent.append(((Text)obj).getText());
/*  473 */         hasText = true;
/*      */       } 
/*      */     } 
/*      */     
/*  477 */     if (!hasText) {
/*  478 */       return "";
/*      */     }
/*      */     
/*  481 */     return textContent.toString();
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
/*      */   public String getTextTrim() {
/*  494 */     return getText().trim();
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
/*      */   public String getTextNormalize() {
/*  507 */     return Text.normalizeString(getText());
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
/*      */   public String getChildText(String name) {
/*  520 */     Element child = getChild(name);
/*  521 */     if (child == null) {
/*  522 */       return null;
/*      */     }
/*  524 */     return child.getText();
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
/*      */   public String getChildTextTrim(String name) {
/*  537 */     Element child = getChild(name);
/*  538 */     if (child == null) {
/*  539 */       return null;
/*      */     }
/*  541 */     return child.getTextTrim();
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
/*      */   public String getChildTextNormalize(String name) {
/*  554 */     Element child = getChild(name);
/*  555 */     if (child == null) {
/*  556 */       return null;
/*      */     }
/*  558 */     return child.getTextNormalize();
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
/*      */   public String getChildText(String name, Namespace ns) {
/*  571 */     Element child = getChild(name, ns);
/*  572 */     if (child == null) {
/*  573 */       return null;
/*      */     }
/*  575 */     return child.getText();
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
/*      */   public String getChildTextTrim(String name, Namespace ns) {
/*  588 */     Element child = getChild(name, ns);
/*  589 */     if (child == null) {
/*  590 */       return null;
/*      */     }
/*  592 */     return child.getTextTrim();
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
/*      */   public String getChildTextNormalize(String name, Namespace ns) {
/*  605 */     Element child = getChild(name, ns);
/*  606 */     if (child == null) {
/*  607 */       return null;
/*      */     }
/*  609 */     return child.getTextNormalize();
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
/*      */   public Element setText(String text) {
/*  627 */     this.content.clear();
/*      */     
/*  629 */     if (text != null) {
/*  630 */       addContent(new Text(text));
/*      */     }
/*      */     
/*  633 */     return this;
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
/*      */   public List getContent() {
/*  659 */     return this.content;
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
/*      */   public List getContent(Filter filter) {
/*  675 */     return this.content.getView(filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List removeContent() {
/*  684 */     List old = new ArrayList(this.content);
/*  685 */     this.content.clear();
/*  686 */     return old;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List removeContent(Filter filter) {
/*  696 */     List old = new ArrayList();
/*  697 */     Iterator itr = this.content.getView(filter).iterator();
/*  698 */     while (itr.hasNext()) {
/*  699 */       Content child = itr.next();
/*  700 */       old.add(child);
/*  701 */       itr.remove();
/*      */     } 
/*  703 */     return old;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setContent(Collection newContent) {
/*  742 */     this.content.clearAndSet(newContent);
/*  743 */     return this;
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
/*      */   public Element setContent(int index, Content child) {
/*  762 */     this.content.set(index, child);
/*  763 */     return this;
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
/*      */   public Parent setContent(int index, Collection collection) {
/*  783 */     this.content.remove(index);
/*  784 */     this.content.addAll(index, collection);
/*  785 */     return this;
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
/*      */   public Element addContent(String str) {
/*  799 */     return addContent(new Text(str));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addContent(Content child) {
/*  809 */     this.content.add((E)child);
/*  810 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element addContent(Element child) {
/*  820 */     this.content.add((E)child);
/*  821 */     return this;
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
/*      */   public Element addContent(Collection collection) {
/*  836 */     this.content.addAll(collection);
/*  837 */     return this;
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
/*      */   public Element addContent(int index, Content child) {
/*  851 */     this.content.add(index, child);
/*  852 */     return this;
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
/*      */   public Element addContent(int index, Collection c) {
/*  870 */     this.content.addAll(index, c);
/*  871 */     return this;
/*      */   }
/*      */   
/*      */   public List cloneContent() {
/*  875 */     int size = getContentSize();
/*  876 */     List list = new ArrayList(size);
/*  877 */     for (int i = 0; i < size; i++) {
/*  878 */       Content child = getContent(i);
/*  879 */       list.add(child.clone());
/*      */     } 
/*  881 */     return list;
/*      */   }
/*      */   
/*      */   public Content getContent(int index) {
/*  885 */     return (Content)this.content.get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeContent(Content child) {
/*  894 */     return this.content.remove(child);
/*      */   }
/*      */   
/*      */   public Content removeContent(int index) {
/*  898 */     return (Content)this.content.remove(index);
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
/*      */   public Element setContent(Content child) {
/*  929 */     this.content.clear();
/*  930 */     this.content.add((E)child);
/*  931 */     return this;
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
/*      */   public boolean isAncestor(Element element) {
/*  943 */     Object p = element.getParent();
/*  944 */     while (p instanceof Element) {
/*  945 */       if (p == this) {
/*  946 */         return true;
/*      */       }
/*  948 */       p = ((Element)p).getParent();
/*      */     } 
/*  950 */     return false;
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
/*      */   public List getAttributes() {
/*  965 */     return this.attributes;
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
/*      */   public Attribute getAttribute(String name) {
/*  978 */     return getAttribute(name, Namespace.NO_NAMESPACE);
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
/*      */   public Attribute getAttribute(String name, Namespace ns) {
/*  992 */     return (Attribute)this.attributes.get(name, ns);
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
/*      */   public String getAttributeValue(String name) {
/* 1006 */     return getAttributeValue(name, Namespace.NO_NAMESPACE);
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
/*      */   public String getAttributeValue(String name, String def) {
/* 1021 */     return getAttributeValue(name, Namespace.NO_NAMESPACE, def);
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
/*      */   public String getAttributeValue(String name, Namespace ns) {
/* 1036 */     return getAttributeValue(name, ns, (String)null);
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
/*      */   public String getAttributeValue(String name, Namespace ns, String def) {
/* 1052 */     Attribute attribute = (Attribute)this.attributes.get(name, ns);
/* 1053 */     return (attribute == null) ? def : attribute.getValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element setAttributes(List newAttributes) {
/* 1100 */     this.attributes.clearAndSet(newAttributes);
/* 1101 */     return this;
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
/*      */   public Element setAttribute(String name, String value) {
/* 1120 */     return setAttribute(new Attribute(name, value));
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
/*      */   public Element setAttribute(String name, String value, Namespace ns) {
/* 1143 */     return setAttribute(new Attribute(name, value, ns));
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
/*      */   public Element setAttribute(Attribute attribute) {
/* 1159 */     this.attributes.add(attribute);
/* 1160 */     return this;
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
/*      */   public boolean removeAttribute(String name) {
/* 1173 */     return removeAttribute(name, Namespace.NO_NAMESPACE);
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
/*      */   public boolean removeAttribute(String name, Namespace ns) {
/* 1188 */     return this.attributes.remove(name, ns);
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
/*      */   public boolean removeAttribute(Attribute attribute) {
/* 1200 */     return this.attributes.remove(attribute);
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
/*      */   public String toString() {
/* 1216 */     StringBuffer stringForm = (new StringBuffer(64)).append("[Element: <").append(getQualifiedName());
/*      */ 
/*      */ 
/*      */     
/* 1220 */     String nsuri = getNamespaceURI();
/* 1221 */     if (!nsuri.equals("")) {
/* 1222 */       stringForm.append(" [Namespace: ").append(nsuri).append("]");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1227 */     stringForm.append("/>]");
/*      */     
/* 1229 */     return stringForm.toString();
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
/*      */   public Object clone() {
/* 1245 */     Element element = null;
/*      */     
/* 1247 */     element = (Element)super.clone();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1259 */     element.content = new ContentList(element);
/* 1260 */     element.attributes = new AttributeList(element);
/*      */ 
/*      */     
/* 1263 */     if (this.attributes != null) {
/* 1264 */       for (int i = 0; i < this.attributes.size(); i++) {
/* 1265 */         Object obj = this.attributes.get(i);
/* 1266 */         Attribute attribute = (Attribute)((Attribute)obj).clone();
/* 1267 */         element.attributes.add(attribute);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1272 */     if (this.additionalNamespaces != null) {
/* 1273 */       int additionalSize = this.additionalNamespaces.size();
/* 1274 */       element.additionalNamespaces = new ArrayList(additionalSize);
/* 1275 */       for (int i = 0; i < additionalSize; i++) {
/* 1276 */         Object additional = this.additionalNamespaces.get(i);
/* 1277 */         element.additionalNamespaces.add(additional);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1282 */     if (this.content != null) {
/* 1283 */       for (int i = 0; i < this.content.size(); i++) {
/* 1284 */         Object obj = this.content.get(i);
/* 1285 */         if (obj instanceof Element) {
/* 1286 */           Element elt = (Element)((Element)obj).clone();
/* 1287 */           element.content.add((E)elt);
/* 1288 */         } else if (obj instanceof CDATA) {
/* 1289 */           CDATA cdata = (CDATA)((CDATA)obj).clone();
/* 1290 */           element.content.add((E)cdata);
/* 1291 */         } else if (obj instanceof Text) {
/* 1292 */           Text text = (Text)((Text)obj).clone();
/* 1293 */           element.content.add((E)text);
/* 1294 */         } else if (obj instanceof Comment) {
/* 1295 */           Comment comment = (Comment)((Comment)obj).clone();
/* 1296 */           element.content.add((E)comment);
/* 1297 */         } else if (obj instanceof ProcessingInstruction) {
/* 1298 */           ProcessingInstruction pi = (ProcessingInstruction)((ProcessingInstruction)obj).clone();
/*      */           
/* 1300 */           element.content.add((E)pi);
/* 1301 */         } else if (obj instanceof EntityRef) {
/* 1302 */           EntityRef entity = (EntityRef)((EntityRef)obj).clone();
/* 1303 */           element.content.add((E)entity);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1309 */     if (this.additionalNamespaces != null) {
/*      */       
/* 1311 */       element.additionalNamespaces = new ArrayList();
/* 1312 */       element.additionalNamespaces.addAll(this.additionalNamespaces);
/*      */     } 
/*      */     
/* 1315 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 1322 */     out.defaultWriteObject();
/*      */ 
/*      */ 
/*      */     
/* 1326 */     out.writeObject(this.namespace.getPrefix());
/* 1327 */     out.writeObject(this.namespace.getURI());
/*      */     
/* 1329 */     if (this.additionalNamespaces == null) {
/* 1330 */       out.write(0);
/*      */     } else {
/*      */       
/* 1333 */       int size = this.additionalNamespaces.size();
/* 1334 */       out.write(size);
/* 1335 */       for (int i = 0; i < size; i++) {
/* 1336 */         Namespace additional = this.additionalNamespaces.get(i);
/* 1337 */         out.writeObject(additional.getPrefix());
/* 1338 */         out.writeObject(additional.getURI());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 1346 */     in.defaultReadObject();
/*      */     
/* 1348 */     this.namespace = Namespace.getNamespace((String)in.readObject(), (String)in.readObject());
/*      */ 
/*      */     
/* 1351 */     int size = in.read();
/*      */     
/* 1353 */     if (size != 0) {
/* 1354 */       this.additionalNamespaces = new ArrayList(size);
/* 1355 */       for (int i = 0; i < size; i++) {
/* 1356 */         Namespace additional = Namespace.getNamespace((String)in.readObject(), (String)in.readObject());
/*      */         
/* 1358 */         this.additionalNamespaces.add(additional);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator getDescendants() {
/* 1369 */     return new DescendantIterator(this);
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
/*      */   public Iterator getDescendants(Filter filter) {
/* 1382 */     return new FilterIterator(new DescendantIterator(this), filter);
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
/*      */   
/*      */   public List getChildren() {
/* 1419 */     return this.content.getView((Filter)new ElementFilter());
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
/*      */   public List getChildren(String name) {
/* 1439 */     return getChildren(name, Namespace.NO_NAMESPACE);
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
/*      */   public List getChildren(String name, Namespace ns) {
/* 1460 */     return this.content.getView((Filter)new ElementFilter(name, ns));
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
/*      */   public Element getChild(String name, Namespace ns) {
/* 1474 */     List elements = this.content.getView((Filter)new ElementFilter(name, ns));
/* 1475 */     Iterator i = elements.iterator();
/* 1476 */     if (i.hasNext()) {
/* 1477 */       return i.next();
/*      */     }
/* 1479 */     return null;
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
/*      */   public Element getChild(String name) {
/* 1492 */     return getChild(name, Namespace.NO_NAMESPACE);
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
/*      */   public boolean removeChild(String name) {
/* 1506 */     return removeChild(name, Namespace.NO_NAMESPACE);
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
/*      */   public boolean removeChild(String name, Namespace ns) {
/* 1521 */     List old = this.content.getView((Filter)new ElementFilter(name, ns));
/* 1522 */     Iterator i = old.iterator();
/* 1523 */     if (i.hasNext()) {
/* 1524 */       i.next();
/* 1525 */       i.remove();
/* 1526 */       return true;
/*      */     } 
/*      */     
/* 1529 */     return false;
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
/*      */   public boolean removeChildren(String name) {
/* 1543 */     return removeChildren(name, Namespace.NO_NAMESPACE);
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
/*      */   public boolean removeChildren(String name, Namespace ns) {
/* 1558 */     boolean deletedSome = false;
/*      */     
/* 1560 */     List old = this.content.getView((Filter)new ElementFilter(name, ns));
/* 1561 */     Iterator i = old.iterator();
/* 1562 */     while (i.hasNext()) {
/* 1563 */       i.next();
/* 1564 */       i.remove();
/* 1565 */       deletedSome = true;
/*      */     } 
/*      */     
/* 1568 */     return deletedSome;
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\Element.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */