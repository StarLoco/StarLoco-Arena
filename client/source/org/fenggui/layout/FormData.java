/*    */ package org.fenggui.layout;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FormData
/*    */   implements ILayoutData
/*    */ {
/* 28 */   public FormAttachment left = null, right = null, top = null, bottom = null;
/*    */   
/*    */   public boolean allStatic() {
/* 31 */     if ((this.left == null || this.left.isStatic()) && (
/* 32 */       this.right == null || this.right.isStatic()) && (
/* 33 */       this.top == null || this.top.isStatic()) && (
/* 34 */       this.bottom == null || this.bottom.isStatic())) return true; 
/*    */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\FormData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */