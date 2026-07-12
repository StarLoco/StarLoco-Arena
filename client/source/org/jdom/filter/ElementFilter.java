/*     */ package org.jdom.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.Namespace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElementFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: ElementFilter.java,v $ $Revision: 1.18 $ $Date: 2004/09/07 06:37:20 $ $Name: jdom_1_0 $";
/*     */   private String name;
/*     */   private transient Namespace namespace;
/*     */   
/*     */   public ElementFilter() {}
/*     */   
/*     */   public ElementFilter(String name) {
/*  91 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementFilter(Namespace namespace) {
/* 100 */     this.namespace = namespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementFilter(String name, Namespace namespace) {
/* 110 */     this.name = name;
/* 111 */     this.namespace = namespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(Object obj) {
/* 122 */     if (obj instanceof Element) {
/* 123 */       Element el = (Element)obj;
/* 124 */       return 
/* 125 */         !((this.name != null && !this.name.equals(el.getName())) || (
/* 126 */         this.namespace != null && !this.namespace.equals(el.getNamespace())));
/*     */     } 
/* 128 */     return false;
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
/*     */   public boolean equals(Object obj) {
/* 140 */     if (this == obj) return true; 
/* 141 */     if (!(obj instanceof ElementFilter)) return false;
/*     */     
/* 143 */     ElementFilter filter = (ElementFilter)obj;
/*     */     
/* 145 */     if ((this.name != null) ? (this.name.equals(filter.name) ^ true) : (filter.name != null)) return false; 
/* 146 */     if ((this.namespace != null) ? (this.namespace.equals(filter.namespace) ^ true) : (filter.namespace != null)) return false;
/*     */     
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     int result = (this.name != null) ? this.name.hashCode() : 0;
/* 155 */     result = 29 * result + ((this.namespace != null) ? this.namespace.hashCode() : 0);
/* 156 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 163 */     out.defaultWriteObject();
/*     */ 
/*     */ 
/*     */     
/* 167 */     out.writeObject(this.namespace.getPrefix());
/* 168 */     out.writeObject(this.namespace.getURI());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 174 */     in.defaultReadObject();
/*     */     
/* 176 */     this.namespace = Namespace.getNamespace(
/* 177 */         (String)in.readObject(), (String)in.readObject());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\filter\ElementFilter.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */