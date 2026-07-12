/*    */ package org.fenggui.layout;
/*    */ 
/*    */ import org.fenggui.Widget;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FormAttachment
/*    */ {
/*    */   private int offset;
/* 35 */   private Widget attachedWidget = null;
/* 36 */   private int numerator = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FormAttachment(Widget w, int offset) {
/* 44 */     this.attachedWidget = w;
/* 45 */     if (this.attachedWidget == null) {
/*    */       
/* 47 */       System.err.println("FormAttachment Constructor Warning: The Widget you were trying to attach to is null!");
/* 48 */       System.err.println("This leads to unexepected behavior in the FormLayout.");
/* 49 */       System.err.println("Make sure you attach Widgets that are both initiated!");
/*    */     } 
/* 51 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FormAttachment(int numerator, int offset) {
/* 61 */     this.numerator = numerator;
/* 62 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Widget getAttachedWidget() {
/* 67 */     return this.attachedWidget;
/*    */   }
/*    */   protected int getNumerator() {
/* 70 */     return this.numerator;
/*    */   }
/*    */   protected int getOffset() {
/* 73 */     return this.offset;
/*    */   }
/*    */   
/*    */   protected boolean isStatic() {
/* 77 */     return (this.attachedWidget == null);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\FormAttachment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */