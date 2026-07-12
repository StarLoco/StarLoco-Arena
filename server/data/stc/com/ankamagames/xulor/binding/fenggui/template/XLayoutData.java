/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.core.impl.XElement;
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
/*    */ public abstract class XLayoutData
/*    */   extends XElement
/*    */   implements com.ankamagames.xulor.template.ILayoutData
/*    */ {
/*    */   public void applyAllAttributes() {}
/*    */   
/*    */   public Object getEncapsulatedObject()
/*    */   {
/* 24 */     return getLayoutData();
/*    */   }
/*    */   
/*    */   public abstract org.fenggui.layout.ILayoutData getLayoutData();
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XLayoutData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */