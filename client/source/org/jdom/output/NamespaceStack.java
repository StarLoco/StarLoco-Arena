/*     */ package org.jdom.output;
/*     */ 
/*     */ import java.util.Stack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NamespaceStack
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: NamespaceStack.java,v $ $Revision: 1.13 $ $Date: 2004/02/06 09:28:32 $ $Name: jdom_1_0 $";
/*  87 */   private Stack prefixes = new Stack();
/*  88 */   private Stack uris = new Stack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void push(Namespace ns) {
/*  98 */     this.prefixes.push(ns.getPrefix());
/*  99 */     this.uris.push(ns.getURI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String pop() {
/* 109 */     String prefix = this.prefixes.pop();
/* 110 */     this.uris.pop();
/*     */     
/* 112 */     return prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 121 */     return this.prefixes.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI(String prefix) {
/* 132 */     int index = this.prefixes.lastIndexOf(prefix);
/* 133 */     if (index == -1) {
/* 134 */       return null;
/*     */     }
/* 136 */     String uri = this.uris.elementAt(index);
/* 137 */     return uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     StringBuffer buf = new StringBuffer();
/* 147 */     String sep = System.getProperty("line.separator");
/* 148 */     buf.append("Stack: " + this.prefixes.size() + sep);
/* 149 */     for (int i = 0; i < this.prefixes.size(); i++) {
/* 150 */       buf.append(String.valueOf(String.valueOf(this.prefixes.elementAt(i))) + "&" + this.uris.elementAt(i) + sep);
/*     */     }
/* 152 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\output\NamespaceStack.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */