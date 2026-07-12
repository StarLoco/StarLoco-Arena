/*     */ package org.jdom;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class Attribute
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: Attribute.java,v $ $Revision: 1.52 $ $Date: 2004/03/01 23:58:28 $ $Name: jdom_1_0 $";
/*     */   public static final int UNDECLARED_TYPE = 0;
/*     */   public static final int CDATA_TYPE = 1;
/*     */   public static final int ID_TYPE = 2;
/*     */   public static final int IDREF_TYPE = 3;
/*     */   public static final int IDREFS_TYPE = 4;
/*     */   public static final int ENTITY_TYPE = 5;
/*     */   public static final int ENTITIES_TYPE = 6;
/*     */   public static final int NMTOKEN_TYPE = 7;
/*     */   public static final int NMTOKENS_TYPE = 8;
/*     */   public static final int NOTATION_TYPE = 9;
/*     */   public static final int ENUMERATED_TYPE = 10;
/*     */   protected String name;
/*     */   protected transient Namespace namespace;
/*     */   protected String value;
/* 178 */   protected int type = 0;
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
/*     */   protected Object parent;
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
/*     */   public Attribute(String name, String value, Namespace namespace) {
/* 204 */     setName(name);
/* 205 */     setValue(value);
/* 206 */     setNamespace(namespace);
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
/*     */   public Attribute(String name, String value, int type, Namespace namespace) {
/* 228 */     setName(name);
/* 229 */     setValue(value);
/* 230 */     setAttributeType(type);
/* 231 */     setNamespace(namespace);
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
/*     */   public Attribute(String name, String value) {
/* 252 */     this(name, value, 0, Namespace.NO_NAMESPACE);
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
/*     */   
/*     */   public Attribute(String name, String value, int type) {
/* 276 */     this(name, value, type, Namespace.NO_NAMESPACE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getParent() {
/* 286 */     return (Element)this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getDocument() {
/* 297 */     if (this.parent != null) {
/* 298 */       return ((Element)this.parent).getDocument();
/*     */     }
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attribute setParent(Element parent) {
/* 310 */     this.parent = parent;
/* 311 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute detach() {
/* 321 */     Element p = getParent();
/* 322 */     if (p != null) {
/* 323 */       p.removeAttribute(getName(), getNamespace());
/*     */     }
/* 325 */     return this;
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
/*     */   public String getName() {
/* 347 */     return this.name;
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
/*     */   public Attribute setName(String name) {
/*     */     String reason;
/* 360 */     if ((reason = Verifier.checkAttributeName(name)) != null) {
/* 361 */       throw new IllegalNameException(name, "attribute", reason);
/*     */     }
/* 363 */     this.name = name;
/* 364 */     return this;
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
/*     */ 
/*     */   
/*     */   public String getQualifiedName() {
/* 389 */     String prefix = this.namespace.getPrefix();
/* 390 */     if (prefix != null && !prefix.equals("")) {
/* 391 */       return prefix + 
/* 392 */         ':' + 
/* 393 */         getName();
/*     */     }
/*     */     
/* 396 */     return getName();
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
/*     */   public String getNamespacePrefix() {
/* 413 */     return this.namespace.getPrefix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 424 */     return this.namespace.getURI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Namespace getNamespace() {
/* 434 */     return this.namespace;
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
/*     */   public Attribute setNamespace(Namespace namespace) {
/* 448 */     if (namespace == null) {
/* 449 */       namespace = Namespace.NO_NAMESPACE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 454 */     if (namespace != Namespace.NO_NAMESPACE && 
/* 455 */       namespace.getPrefix().equals("")) {
/* 456 */       throw new IllegalNameException("", "attribute namespace", 
/* 457 */           "An attribute namespace without a prefix can only be the NO_NAMESPACE namespace");
/*     */     }
/*     */     
/* 460 */     this.namespace = namespace;
/* 461 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 471 */     return this.value;
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
/*     */   public Attribute setValue(String value) {
/* 484 */     String reason = null;
/* 485 */     if ((reason = Verifier.checkCharacterData(value)) != null) {
/* 486 */       throw new IllegalDataException(value, "attribute", reason);
/*     */     }
/* 488 */     this.value = value;
/* 489 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttributeType() {
/* 499 */     return this.type;
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
/*     */   public Attribute setAttributeType(int type) {
/* 511 */     if (type < 0 || type > 10) {
/* 512 */       throw new IllegalDataException(String.valueOf(type), 
/* 513 */           "attribute", "Illegal attribute type");
/*     */     }
/* 515 */     this.type = type;
/* 516 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 527 */     return 
/* 528 */       "[Attribute: " + 
/* 529 */       getQualifiedName() + 
/* 530 */       "=\"" + 
/* 531 */       this.value + 
/* 532 */       "\"" + 
/* 533 */       "]";
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
/*     */   public final boolean equals(Object ob) {
/* 546 */     return !(ob != this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 555 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 564 */     Attribute attribute = null;
/*     */     
/*     */     try {
/* 567 */       attribute = (Attribute)super.clone();
/* 568 */     } catch (CloneNotSupportedException cloneNotSupportedException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 576 */     attribute.parent = null;
/* 577 */     return attribute;
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
/*     */   public int getIntValue() throws DataConversionException {
/*     */     try {
/* 595 */       return Integer.parseInt(this.value.trim());
/* 596 */     } catch (NumberFormatException numberFormatException) {
/* 597 */       throw new DataConversionException(this.name, "int");
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
/*     */   public long getLongValue() throws DataConversionException {
/*     */     try {
/* 612 */       return Long.parseLong(this.value.trim());
/* 613 */     } catch (NumberFormatException numberFormatException) {
/* 614 */       throw new DataConversionException(this.name, "long");
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
/*     */   public float getFloatValue() throws DataConversionException {
/*     */     try {
/* 630 */       return Float.valueOf(this.value.trim()).floatValue();
/* 631 */     } catch (NumberFormatException numberFormatException) {
/* 632 */       throw new DataConversionException(this.name, "float");
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
/*     */   public double getDoubleValue() throws DataConversionException {
/*     */     try {
/* 648 */       return Double.valueOf(this.value.trim()).doubleValue();
/* 649 */     } catch (NumberFormatException numberFormatException) {
/* 650 */       throw new DataConversionException(this.name, "double");
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
/*     */   public boolean getBooleanValue() throws DataConversionException {
/* 665 */     String valueTrim = this.value.trim();
/* 666 */     if (valueTrim.equalsIgnoreCase("true") || 
/* 667 */       valueTrim.equalsIgnoreCase("on") || 
/* 668 */       valueTrim.equalsIgnoreCase("1") || 
/* 669 */       valueTrim.equalsIgnoreCase("yes"))
/* 670 */       return true; 
/* 671 */     if (valueTrim.equalsIgnoreCase("false") || 
/* 672 */       valueTrim.equalsIgnoreCase("off") || 
/* 673 */       valueTrim.equalsIgnoreCase("0") || 
/* 674 */       valueTrim.equalsIgnoreCase("no")) {
/* 675 */       return false;
/*     */     }
/* 677 */     throw new DataConversionException(this.name, "boolean");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 685 */     out.defaultWriteObject();
/*     */ 
/*     */ 
/*     */     
/* 689 */     out.writeObject(this.namespace.getPrefix());
/* 690 */     out.writeObject(this.namespace.getURI());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 696 */     in.defaultReadObject();
/*     */     
/* 698 */     this.namespace = Namespace.getNamespace(
/* 699 */         (String)in.readObject(), (String)in.readObject());
/*     */   }
/*     */   
/*     */   protected Attribute() {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\Attribute.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */