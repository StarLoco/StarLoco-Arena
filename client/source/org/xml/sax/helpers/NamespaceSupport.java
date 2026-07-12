/*     */ package org.xml.sax.helpers;
/*     */ 
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamespaceSupport
/*     */ {
/*     */   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
/*     */   public static final String NSDECL = "http://www.w3.org/xmlns/2000/";
/* 116 */   private static final Enumeration EMPTY_ENUMERATION = (new Vector()).elements();
/*     */ 
/*     */   
/*     */   private Context[] contexts;
/*     */ 
/*     */   
/*     */   private Context currentContext;
/*     */   
/*     */   private int contextPos;
/*     */   
/*     */   private boolean namespaceDeclUris;
/*     */ 
/*     */   
/*     */   public NamespaceSupport() {
/* 130 */     reset();
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
/*     */   public void reset() {
/* 153 */     this.contexts = new Context[32];
/* 154 */     this.namespaceDeclUris = false;
/* 155 */     this.contextPos = 0;
/* 156 */     this.contexts[this.contextPos] = this.currentContext = new Context();
/* 157 */     this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushContext() {
/* 199 */     int i = this.contexts.length;
/*     */     
/* 201 */     this.contextPos++;
/*     */ 
/*     */     
/* 204 */     if (this.contextPos >= i) {
/* 205 */       Context[] arrayOfContext = new Context[i * 2];
/* 206 */       System.arraycopy(this.contexts, 0, arrayOfContext, 0, i);
/* 207 */       i *= 2;
/* 208 */       this.contexts = arrayOfContext;
/*     */     } 
/*     */ 
/*     */     
/* 212 */     this.currentContext = this.contexts[this.contextPos];
/* 213 */     if (this.currentContext == null) {
/* 214 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/*     */     }
/*     */ 
/*     */     
/* 218 */     if (this.contextPos > 0) {
/* 219 */       this.currentContext.setParent(this.contexts[this.contextPos - 1]);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void popContext() {
/* 239 */     this.contexts[this.contextPos].clear();
/* 240 */     this.contextPos--;
/* 241 */     if (this.contextPos < 0) {
/* 242 */       throw new EmptyStackException();
/*     */     }
/* 244 */     this.currentContext = this.contexts[this.contextPos];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean declarePrefix(String paramString1, String paramString2) {
/* 289 */     if (paramString1.equals("xml") || paramString1.equals("xmlns")) {
/* 290 */       return false;
/*     */     }
/* 292 */     this.currentContext.declarePrefix(paramString1, paramString2);
/* 293 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] processName(String paramString, String[] paramArrayOfString, boolean paramBoolean) {
/* 341 */     String[] arrayOfString = this.currentContext.processName(paramString, paramBoolean);
/* 342 */     if (arrayOfString == null) {
/* 343 */       return null;
/*     */     }
/* 345 */     paramArrayOfString[0] = arrayOfString[0];
/* 346 */     paramArrayOfString[1] = arrayOfString[1];
/* 347 */     paramArrayOfString[2] = arrayOfString[2];
/* 348 */     return paramArrayOfString;
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
/*     */   public String getURI(String paramString) {
/* 367 */     return this.currentContext.getURI(paramString);
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
/*     */   public Enumeration getPrefixes() {
/* 387 */     return this.currentContext.getPrefixes();
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
/*     */   public String getPrefix(String paramString) {
/* 412 */     return this.currentContext.getPrefix(paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration getPrefixes(String paramString) {
/* 441 */     Vector<String> vector = new Vector();
/* 442 */     Enumeration<String> enumeration = getPrefixes();
/* 443 */     while (enumeration.hasMoreElements()) {
/* 444 */       String str = enumeration.nextElement();
/* 445 */       if (paramString.equals(getURI(str))) {
/* 446 */         vector.addElement(str);
/*     */       }
/*     */     } 
/* 449 */     return vector.elements();
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
/*     */   public Enumeration getDeclaredPrefixes() {
/* 467 */     return this.currentContext.getDeclaredPrefixes();
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
/*     */   public void setNamespaceDeclUris(boolean paramBoolean) {
/* 483 */     if (this.contextPos != 0)
/* 484 */       throw new IllegalStateException(); 
/* 485 */     if (paramBoolean == this.namespaceDeclUris)
/*     */       return; 
/* 487 */     this.namespaceDeclUris = paramBoolean;
/* 488 */     if (paramBoolean) {
/* 489 */       this.currentContext.declarePrefix("xmlns", "http://www.w3.org/xmlns/2000/");
/*     */     } else {
/* 491 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/* 492 */       this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNamespaceDeclUris() {
/* 503 */     return this.namespaceDeclUris;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final class Context
/*     */   {
/*     */     Hashtable prefixTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Hashtable uriTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Hashtable elementNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Hashtable attributeNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String defaultNS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Vector declarations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean declSeen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Context parent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Context() {
/* 810 */       this.defaultNS = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 818 */       this.declarations = null;
/* 819 */       this.declSeen = false;
/* 820 */       this.parent = null;
/*     */       copyTables();
/*     */     }
/*     */     
/*     */     void setParent(Context param1Context) {
/*     */       this.parent = param1Context;
/*     */       this.declarations = null;
/*     */       this.prefixTable = param1Context.prefixTable;
/*     */       this.uriTable = param1Context.uriTable;
/*     */       this.elementNameTable = param1Context.elementNameTable;
/*     */       this.attributeNameTable = param1Context.attributeNameTable;
/*     */       this.defaultNS = param1Context.defaultNS;
/*     */       this.declSeen = false;
/*     */     }
/*     */     
/*     */     void clear() {
/*     */       this.parent = null;
/*     */       this.prefixTable = null;
/*     */       this.uriTable = null;
/*     */       this.elementNameTable = null;
/*     */       this.attributeNameTable = null;
/*     */       this.defaultNS = null;
/*     */     }
/*     */     
/*     */     void declarePrefix(String param1String1, String param1String2) {
/*     */       if (!this.declSeen)
/*     */         copyTables(); 
/*     */       if (this.declarations == null)
/*     */         this.declarations = new Vector(); 
/*     */       param1String1 = param1String1.intern();
/*     */       param1String2 = param1String2.intern();
/*     */       if ("".equals(param1String1)) {
/*     */         if ("".equals(param1String2)) {
/*     */           this.defaultNS = null;
/*     */         } else {
/*     */           this.defaultNS = param1String2;
/*     */         } 
/*     */       } else {
/*     */         this.prefixTable.put(param1String1, param1String2);
/*     */         this.uriTable.put(param1String2, param1String1);
/*     */       } 
/*     */       this.declarations.addElement(param1String1);
/*     */     }
/*     */     
/*     */     String[] processName(String param1String, boolean param1Boolean) {
/*     */       Hashtable<String, String[]> hashtable;
/*     */       if (param1Boolean) {
/*     */         hashtable = this.attributeNameTable;
/*     */       } else {
/*     */         hashtable = this.elementNameTable;
/*     */       } 
/*     */       String[] arrayOfString = (String[])hashtable.get(param1String);
/*     */       if (arrayOfString != null)
/*     */         return arrayOfString; 
/*     */       arrayOfString = new String[3];
/*     */       arrayOfString[2] = param1String.intern();
/*     */       int i = param1String.indexOf(':');
/*     */       if (i == -1) {
/*     */         if (param1Boolean) {
/*     */           if (param1String == "xmlns" && NamespaceSupport.this.namespaceDeclUris) {
/*     */             arrayOfString[0] = "http://www.w3.org/xmlns/2000/";
/*     */           } else {
/*     */             arrayOfString[0] = "";
/*     */           } 
/*     */         } else if (this.defaultNS == null) {
/*     */           arrayOfString[0] = "";
/*     */         } else {
/*     */           arrayOfString[0] = this.defaultNS;
/*     */         } 
/*     */         arrayOfString[1] = arrayOfString[2];
/*     */       } else {
/*     */         String str3, str1 = param1String.substring(0, i);
/*     */         String str2 = param1String.substring(i + 1);
/*     */         if ("".equals(str1)) {
/*     */           str3 = this.defaultNS;
/*     */         } else {
/*     */           str3 = (String)this.prefixTable.get(str1);
/*     */         } 
/*     */         if (str3 == null || (!param1Boolean && "xmlns".equals(str1)))
/*     */           return null; 
/*     */         arrayOfString[0] = str3;
/*     */         arrayOfString[1] = str2.intern();
/*     */       } 
/*     */       hashtable.put(arrayOfString[2], arrayOfString);
/*     */       return arrayOfString;
/*     */     }
/*     */     
/*     */     String getURI(String param1String) {
/*     */       if ("".equals(param1String))
/*     */         return this.defaultNS; 
/*     */       if (this.prefixTable == null)
/*     */         return null; 
/*     */       return (String)this.prefixTable.get(param1String);
/*     */     }
/*     */     
/*     */     String getPrefix(String param1String) {
/*     */       if (this.uriTable == null)
/*     */         return null; 
/*     */       return (String)this.uriTable.get(param1String);
/*     */     }
/*     */     
/*     */     Enumeration getDeclaredPrefixes() {
/*     */       if (this.declarations == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.declarations.elements();
/*     */     }
/*     */     
/*     */     Enumeration getPrefixes() {
/*     */       if (this.prefixTable == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.prefixTable.keys();
/*     */     }
/*     */     
/*     */     private void copyTables() {
/*     */       if (this.prefixTable != null) {
/*     */         this.prefixTable = (Hashtable)this.prefixTable.clone();
/*     */       } else {
/*     */         this.prefixTable = new Hashtable<Object, Object>();
/*     */       } 
/*     */       if (this.uriTable != null) {
/*     */         this.uriTable = (Hashtable)this.uriTable.clone();
/*     */       } else {
/*     */         this.uriTable = new Hashtable<Object, Object>();
/*     */       } 
/*     */       this.elementNameTable = new Hashtable<Object, Object>();
/*     */       this.attributeNameTable = new Hashtable<Object, Object>();
/*     */       this.declSeen = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\helpers\NamespaceSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */