/*    */ package com.ankamagames.baseImpl.graphics.alea.worldElement;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.CustomElementFactory;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldElementFactory
/*    */ {
/* 13 */   private static CustomElementFactory m_customFactory = null;
/*    */   
/*    */   public static void setCustomElementFactory(CustomElementFactory factory) {
/* 16 */     m_customFactory = factory;
/*    */   }
/*    */   
/*    */   public static WorldElement create(BasicElement element, int paramsCount, byte[] params, int state, int groupId) {
/* 20 */     WorldElement worldElement = null;
/* 21 */     switch (element.getType()) {
/*    */     case 2: 
/* 23 */       worldElement = new GraphicalWorldElement(element.getId(), paramsCount, params, state, groupId);
/* 24 */       break;
/*    */     
/*    */ 
/*    */ 
/*    */     default: 
/* 29 */       if (m_customFactory != null) {
/* 30 */         worldElement = m_customFactory.createWorldElement(element, paramsCount, params, state, groupId);
/*    */       }
/* 32 */       if (worldElement == null) {
/* 33 */         worldElement = new CustomWorldElement(element.getId(), paramsCount, params, state, groupId);
/*    */       }
/*    */       
/*    */       break;
/*    */     }
/*    */     
/* 39 */     return worldElement;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\worldElement\WorldElementFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */