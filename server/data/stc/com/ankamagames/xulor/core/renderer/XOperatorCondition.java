/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XOperatorCondition
/*    */   extends XCondition
/*    */ {
/*    */   public void add(IElement childElement)
/*    */   {
/* 18 */     if ((childElement instanceof XCondition)) {
/* 19 */       addCondition((XCondition)childElement);
/*    */     }
/* 21 */     super.add(childElement);
/*    */   }
/*    */   
/*    */   public abstract void addCondition(XCondition paramXCondition);
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XOperatorCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */