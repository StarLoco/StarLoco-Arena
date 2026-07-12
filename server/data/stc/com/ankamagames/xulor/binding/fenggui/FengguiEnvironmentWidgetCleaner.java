/*    */ package com.ankamagames.xulor.binding.fenggui;
/*    */ 
/*    */ import com.ankamagames.xulor.core.EnvironmentWidgetCleaner;
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import java.util.HashMap;
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
/*    */ public class FengguiEnvironmentWidgetCleaner
/*    */   extends EnvironmentWidgetCleaner
/*    */ {
/*    */   public FengguiEnvironmentWidgetCleaner(HashMap<Object, IElement> elementMap)
/*    */   {
/* 26 */     super(elementMap);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected boolean isToRemove(Object object)
/*    */   {
/* 34 */     IElement element = (IElement)this.m_elementMap.get(object);
/* 35 */     if ((object == null) || (!(element instanceof IComponent))) {
/* 36 */       return false;
/*    */     }
/*    */     
/* 39 */     Widget widget = (Widget)object;
/* 40 */     if ((!widget.isInWidgetTree()) && (((IComponent)element).isAddedToWidgetTree()))
/*    */     {
/* 42 */       return true;
/*    */     }
/* 44 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void removeElement(IElement element)
/*    */   {
/* 52 */     if (element != null) {
/* 53 */       if (element.getParent() != null)
/*    */       {
/* 55 */         element.getParent().removeChild(element);
/*    */       }
/*    */       else {
/* 58 */         element.removeSelfFromParent();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiEnvironmentWidgetCleaner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */