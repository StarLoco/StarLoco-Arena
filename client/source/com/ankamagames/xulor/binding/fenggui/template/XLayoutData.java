/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.core.impl.XElement;
/*    */ import com.ankamagames.xulor.template.ILayoutData;
/*    */ import org.fenggui.layout.ILayoutData;
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
/*    */   implements ILayoutData
/*    */ {
/*    */   public void applyAllAttributes() {}
/*    */   
/*    */   public Object getEncapsulatedObject() {
/* 24 */     return getLayoutData();
/*    */   }
/*    */   
/*    */   public abstract ILayoutData getLayoutData();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XLayoutData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */