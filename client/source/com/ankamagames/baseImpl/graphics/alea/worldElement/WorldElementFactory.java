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
/* 21 */     switch (element.getType())
/*    */     { case 2:
/* 23 */         worldElement = new GraphicalWorldElement(element.getId(), paramsCount, params, state, groupId);
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
/* 39 */         return worldElement; }  if (m_customFactory != null) worldElement = m_customFactory.createWorldElement(element, paramsCount, params, state, groupId);  if (worldElement == null) worldElement = new CustomWorldElement(element.getId(), paramsCount, params, state, groupId);  return worldElement;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\worldElement\WorldElementFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */