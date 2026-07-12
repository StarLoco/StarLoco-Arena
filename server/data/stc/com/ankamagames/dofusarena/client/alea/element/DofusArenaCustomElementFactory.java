/*    */ package com.ankamagames.dofusarena.client.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.CustomElementFactory;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalParametrizedWorldElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DofusArenaCustomElementFactory
/*    */   implements CustomElementFactory
/*    */ {
/*    */   public static final int ELEMENT_TYPE_FIGHT_START_POINT = 1000;
/*    */   public static final int ELEMENT_TYPE_COACH_POINT = 1001;
/*    */   public static final int ELEMENT_TYPE_BONUS = 1002;
/* 21 */   private static DofusArenaCustomElementFactory m_instance = new DofusArenaCustomElementFactory();
/*    */   
/*    */ 
/*    */ 
/*    */   public static DofusArenaCustomElementFactory getInstance()
/*    */   {
/* 27 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BasicElement createElement(int elementId, int elementType)
/*    */   {
/* 37 */     BasicElement element = null;
/*    */     
/* 39 */     switch (elementType) {
/*    */     case 1000: 
/* 41 */       element = new FightStartPointElement(elementId);
/* 42 */       break;
/*    */     
/*    */     case 1001: 
/* 45 */       element = new FightStartCoachPointElement(elementId);
/* 46 */       break;
/*    */     
/*    */     case 1002: 
/* 49 */       element = new BonusElement(elementId);
/*    */     }
/*    */     
/*    */     
/* 53 */     return element;
/*    */   }
/*    */   
/*    */   public WorldElement createWorldElement(BasicElement element, int paramsCount, byte[] params, int state, int groupId)
/*    */   {
/* 58 */     int elementType = element.getType();
/*    */     
/* 60 */     WorldElement worldElement = null;
/*    */     
/* 62 */     switch (elementType)
/*    */     {
/*    */     case 1000: 
/*    */       break;
/*    */     case 1001: 
/*    */       break;
/*    */     
/*    */     case 1002: 
/* 70 */       worldElement = new GraphicalParametrizedWorldElement(element.getId(), paramsCount, params, state, groupId);
/*    */     }
/*    */     
/*    */     
/* 74 */     return worldElement;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\element\DofusArenaCustomElementFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */