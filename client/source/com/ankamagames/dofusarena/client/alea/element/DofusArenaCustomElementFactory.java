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
/*    */   
/*    */   public static DofusArenaCustomElementFactory getInstance() {
/* 27 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicElement createElement(int elementId, int elementType) {
/*    */     BonusElement bonusElement;
/* 37 */     BasicElement element = null;
/*    */     
/* 39 */     switch (elementType) {
/*    */       case 1000:
/* 41 */         element = new FightStartPointElement(elementId);
/*    */         break;
/*    */       
/*    */       case 1001:
/* 45 */         element = new FightStartCoachPointElement(elementId);
/*    */         break;
/*    */       
/*    */       case 1002:
/* 49 */         bonusElement = new BonusElement(elementId);
/*    */         break;
/*    */     } 
/*    */     
/* 53 */     return (BasicElement)bonusElement;
/*    */   }
/*    */   
/*    */   public WorldElement createWorldElement(BasicElement element, int paramsCount, byte[] params, int state, int groupId) {
/*    */     GraphicalParametrizedWorldElement graphicalParametrizedWorldElement;
/* 58 */     int elementType = element.getType();
/*    */     
/* 60 */     WorldElement worldElement = null;
/*    */     
/* 62 */     switch (elementType) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       case 1002:
/* 70 */         graphicalParametrizedWorldElement = new GraphicalParametrizedWorldElement(element.getId(), paramsCount, params, state, groupId);
/*    */         break;
/*    */     } 
/*    */     
/* 74 */     return (WorldElement)graphicalParametrizedWorldElement;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\element\DofusArenaCustomElementFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */