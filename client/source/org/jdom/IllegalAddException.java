/*     */ package org.jdom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IllegalAddException
/*     */   extends IllegalArgumentException
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: IllegalAddException.java,v $ $Revision: 1.25 $ $Date: 2004/02/06 09:28:30 $ $Name: jdom_1_0 $";
/*     */   
/*     */   IllegalAddException(Element base, Attribute added, String reason) {
/*  82 */     super(
/*  83 */         "The attribute \"" + 
/*  84 */         added.getQualifiedName() + 
/*  85 */         "\" could not be added to the element \"" + 
/*  86 */         base.getQualifiedName() + 
/*  87 */         "\": " + 
/*  88 */         reason);
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
/*     */   IllegalAddException(Element base, Element added, String reason) {
/* 103 */     super(
/* 104 */         "The element \"" + 
/* 105 */         added.getQualifiedName() + 
/* 106 */         "\" could not be added as a child of \"" + 
/* 107 */         base.getQualifiedName() + 
/* 108 */         "\": " + 
/* 109 */         reason);
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
/*     */   IllegalAddException(Element added, String reason) {
/* 122 */     super(
/* 123 */         "The element \"" + 
/* 124 */         added.getQualifiedName() + 
/* 125 */         "\" could not be added as the root of the document: " + 
/* 126 */         reason);
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
/*     */   IllegalAddException(Element base, ProcessingInstruction added, String reason) {
/* 142 */     super(
/* 143 */         "The PI \"" + 
/* 144 */         added.getTarget() + 
/* 145 */         "\" could not be added as content to \"" + 
/* 146 */         base.getQualifiedName() + 
/* 147 */         "\": " + 
/* 148 */         reason);
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
/*     */   IllegalAddException(ProcessingInstruction added, String reason) {
/* 162 */     super(
/* 163 */         "The PI \"" + 
/* 164 */         added.getTarget() + 
/* 165 */         "\" could not be added to the top level of the document: " + 
/* 166 */         reason);
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
/*     */   IllegalAddException(Element base, Comment added, String reason) {
/* 181 */     super(
/* 182 */         "The comment \"" + 
/* 183 */         added.getText() + 
/* 184 */         "\" could not be added as content to \"" + 
/* 185 */         base.getQualifiedName() + 
/* 186 */         "\": " + 
/* 187 */         reason);
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
/*     */   IllegalAddException(Element base, CDATA added, String reason) {
/* 202 */     super(
/* 203 */         "The CDATA \"" + 
/* 204 */         added.getText() + 
/* 205 */         "\" could not be added as content to \"" + 
/* 206 */         base.getQualifiedName() + 
/* 207 */         "\": " + 
/* 208 */         reason);
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
/*     */   IllegalAddException(Element base, Text added, String reason) {
/* 224 */     super(
/* 225 */         "The Text \"" + 
/* 226 */         added.getText() + 
/* 227 */         "\" could not be added as content to \"" + 
/* 228 */         base.getQualifiedName() + 
/* 229 */         "\": " + 
/* 230 */         reason);
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
/*     */   IllegalAddException(Comment added, String reason) {
/* 243 */     super(
/* 244 */         "The comment \"" + 
/* 245 */         added.getText() + 
/* 246 */         "\" could not be added to the top level of the document: " + 
/* 247 */         reason);
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
/*     */   IllegalAddException(Element base, EntityRef added, String reason) {
/* 262 */     super(
/* 263 */         "The entity reference\"" + 
/* 264 */         added.getName() + 
/* 265 */         "\" could not be added as content to \"" + 
/* 266 */         base.getQualifiedName() + 
/* 267 */         "\": " + 
/* 268 */         reason);
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
/*     */   IllegalAddException(Element base, Namespace added, String reason) {
/* 283 */     super(
/* 284 */         "The namespace xmlns" + ((
/* 285 */         added.getPrefix() == null || 
/* 286 */         added.getPrefix().equals("")) ? "=" : (
/* 287 */         ":" + added.getPrefix() + "=")) + 
/* 288 */         "\"" + 
/* 289 */         added.getURI() + 
/* 290 */         "\" could not be added as a namespace to \"" + 
/* 291 */         base.getQualifiedName() + 
/* 292 */         "\": " + 
/* 293 */         reason);
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
/*     */   IllegalAddException(DocType added, String reason) {
/* 306 */     super(
/* 307 */         "The DOCTYPE " + 
/* 308 */         added.toString() + 
/* 309 */         " could not be added to the document: " + 
/* 310 */         reason);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IllegalAddException(String reason) {
/* 321 */     super(reason);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\IllegalAddException.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */