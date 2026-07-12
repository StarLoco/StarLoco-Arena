/*     */ package org.jdom;
/*     */ 
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Comment
/*     */   extends Content
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: Comment.java,v $ $Revision: 1.32 $ $Date: 2004/02/11 21:12:43 $ $Name: jdom_1_0 $";
/*     */   protected String text;
/*     */   
/*     */   protected Comment() {}
/*     */   
/*     */   public Comment(String text) {
/*  86 */     setText(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  97 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 106 */     return this.text;
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
/*     */   public Comment setText(String text) {
/*     */     String reason;
/* 119 */     if ((reason = Verifier.checkCommentData(text)) != null) {
/* 120 */       throw new IllegalDataException(text, "comment", reason);
/*     */     }
/*     */     
/* 123 */     this.text = text;
/* 124 */     return this;
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
/*     */   public String toString() {
/* 138 */     return 
/* 139 */       "[Comment: " + (
/* 140 */       new XMLOutputter()).outputString(this) + 
/* 141 */       "]";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\Comment.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */