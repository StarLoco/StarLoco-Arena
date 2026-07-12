/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.core.impl.XElement;
/*    */ import com.ankamagames.xulor.template.ILayoutManager;
/*    */ import org.fenggui.LayoutManager;
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
/*    */ public abstract class XLayoutManager
/*    */   extends XElement
/*    */   implements ILayoutManager
/*    */ {
/*    */   public void applyAllAttributes() {}
/*    */   
/*    */   public Object getEncapsulatedObject()
/*    */   {
/* 26 */     return getLayoutManager();
/*    */   }
/*    */   
/*    */   public abstract LayoutManager getLayoutManager();
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XLayoutManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */