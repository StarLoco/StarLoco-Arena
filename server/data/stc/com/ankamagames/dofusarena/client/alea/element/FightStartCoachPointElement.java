/*    */ package com.ankamagames.dofusarena.client.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FightStartCoachPointElement
/*    */   extends BasicElement
/*    */ {
/*    */   public FightStartCoachPointElement(int elementId)
/*    */   {
/* 15 */     super(elementId);
/* 16 */     setType(1001);
/*    */   }
/*    */   
/*    */   public static byte getTeamId(WorldElement element) {
/* 20 */     return element.getParams()[1];
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\element\FightStartCoachPointElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */