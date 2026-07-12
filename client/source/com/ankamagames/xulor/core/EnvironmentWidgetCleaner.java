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
/*    */   public void setElementMap(HashMap<Object, IElement> elementMap) {
/* 27 */     this.m_elementMap = elementMap;
/*    */   }
/*    */   protected abstract boolean isToRemove(Object paramObject);
/*    */   
/*    */   protected abstract void removeElement(IElement paramIElement);
/*    */   
/*    */   public void run() {
/* 34 */     if (this.m_elementMap == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/*    */     while (true) {
/* 40 */       synchronized (this.m_elementMap) {
/*    */         
/* 42 */         Set<Object> keys = this.m_elementMap.keySet();
/* 43 */         Object[] widgets = new Object[keys.size()];
/* 44 */         keys.toArray(widgets); byte b; int i;
/*    */         Object[] arrayOfObject1;
/* 46 */         for (i = (arrayOfObject1 = widgets).length, b = 0; b < i; ) { Object widget = arrayOfObject1[b];
/* 47 */           if (isToRemove(widget)) {
/* 48 */             removeElement(this.m_elementMap.get(widget));
/*    */           }
/*    */           b++; }
/*    */       
/*    */       } 
/*    */       try {
/* 54 */         sleep(10000L);
/* 55 */       } catch (InterruptedException e) {
/* 56 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\EnvironmentWidgetCleaner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */