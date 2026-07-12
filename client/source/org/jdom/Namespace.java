/*     */ package org.jdom;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Namespace
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: Namespace.java,v $ $Revision: 1.41 $ $Date: 2004/02/27 11:32:57 $ $Name: jdom_1_0 $";
/*     */   private static HashMap namespaces;
/*  97 */   public static final Namespace NO_NAMESPACE = new Namespace("", "");
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static final Namespace XML_NAMESPACE = new Namespace("xml", "http://www.w3.org/XML/1998/namespace");
/*     */ 
/*     */ 
/*     */   
/*     */   private String prefix;
/*     */ 
/*     */ 
/*     */   
/*     */   private String uri;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 114 */     namespaces = new HashMap();
/*     */ 
/*     */     
/* 117 */     namespaces.put("&", NO_NAMESPACE);
/* 118 */     namespaces.put("xml&http://www.w3.org/XML/1998/namespace", 
/* 119 */         XML_NAMESPACE);
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
/*     */   public static Namespace getNamespace(String prefix, String uri) {
/* 135 */     if (prefix == null || prefix.trim().equals("")) {
/* 136 */       prefix = "";
/*     */     }
/* 138 */     if (uri == null || uri.trim().equals("")) {
/* 139 */       uri = "";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     String lookup = (new StringBuffer(64))
/* 147 */       .append(prefix).append('&').append(uri).toString();
/* 148 */     Namespace preexisting = (Namespace)namespaces.get(lookup);
/* 149 */     if (preexisting != null) {
/* 150 */       return preexisting;
/*     */     }
/*     */     
/*     */     String reason;
/*     */     
/* 155 */     if ((reason = Verifier.checkNamespacePrefix(prefix)) != null) {
/* 156 */       throw new IllegalNameException(prefix, "Namespace prefix", reason);
/*     */     }
/* 158 */     if ((reason = Verifier.checkNamespaceURI(uri)) != null) {
/* 159 */       throw new IllegalNameException(uri, "Namespace URI", reason);
/*     */     }
/*     */ 
/*     */     
/* 163 */     if (!prefix.equals("") && uri.equals("")) {
/* 164 */       throw new IllegalNameException("", "namespace", 
/* 165 */           "Namespace URIs must be non-null and non-empty Strings");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     if (prefix.equals("xml")) {
/* 175 */       throw new IllegalNameException(prefix, "Namespace prefix", 
/* 176 */           "The xml prefix can only be bound to http://www.w3.org/XML/1998/namespace");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (uri.equals("http://www.w3.org/XML/1998/namespace")) {
/* 182 */       throw new IllegalNameException(uri, "Namespace URI", 
/* 183 */           "The http://www.w3.org/XML/1998/namespace must be bound to the xml prefix.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 188 */     Namespace ns = new Namespace(prefix, uri);
/* 189 */     namespaces.put(lookup, ns);
/* 190 */     return ns;
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
/*     */   public static Namespace getNamespace(String uri) {
/* 202 */     return getNamespace("", uri);
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
/*     */   private Namespace(String prefix, String uri) {
/* 214 */     this.prefix = prefix;
/* 215 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 224 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 233 */     return this.uri;
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
/*     */   public boolean equals(Object ob) {
/* 245 */     if (this == ob) {
/* 246 */       return true;
/*     */     }
/* 248 */     if (ob instanceof Namespace) {
/* 249 */       return this.uri.equals(((Namespace)ob).uri);
/*     */     }
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 261 */     return "[Namespace: prefix \"" + this.prefix + "\" is mapped to URI \"" + 
/* 262 */       this.uri + "\"]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 273 */     return this.uri.hashCode();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\Namespace.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */