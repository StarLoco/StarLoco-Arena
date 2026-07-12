/*    */ package com.ankamagames.xulor.core;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
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
/*    */ public abstract class EnvironmentWidgetCleaner
/*    */   extends Thread
/*    */ {
/* 19 */   protected HashMap<Object, IElement> m_elementMap = null;
/*    */   
/*    */   public EnvironmentWidgetCleaner(HashMap<Object, IElement> elementMap) {
/* 22 */     setElementMap(elementMap);
/* 23 */     setPriority(1);
/*    */   }
/*    */   
/*    */ 
/* 27 */   public void setElementMap(HashMap<Object, IElement> elementMap) { this.m_elementMap = elementMap; }
/*    */   
/*    */   protected abstract boolean isToRemove(Object paramObject);
/*    */   
/*    */   protected abstract void removeElement(IElement paramIElement);
/*    */   
/*    */   public void run() {
/* 34 */     if (this.m_elementMap == null) {
/* 35 */       return;
/*    */     }
/*    */     
/*    */     for (;;)
/*    */     {
/* 40 */       synchronized (this.m_elementMap)
/*    */       {
/* 42 */         Set<Object> keys = this.m_elementMap.keySet();
/* 43 */         Object[] widgets = new Object[keys.size()];
/* 44 */         keys.toArray(widgets);
/*    */         Object[] arrayOfObject1;
/* 46 */         int j = (arrayOfObject1 = widgets).length;int i = 0; continue;Object widget = arrayOfObject1[i];
/* 47 */         if (isToRemove(widget)) {
/* 48 */           removeElement((IElement)this.m_elementMap.get(widget));
/*    */         }
/* 46 */         i++; if (i < j) {
/*    */           continue;
/*    */         }
/*    */       }
/*    */       
/*    */ 
/*    */       try
/*    */       {
/* 54 */         sleep(10000L);
/*    */       } catch (InterruptedException e) {
/* 56 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\EnvironmentWidgetCleaner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */