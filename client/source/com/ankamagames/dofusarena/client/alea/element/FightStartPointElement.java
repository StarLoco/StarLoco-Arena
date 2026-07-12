/*    */ package com.ankamagames.dofusarena.client.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FightStartPointElement
/*    */   extends BasicElement
/*    */ {
/*    */   public FightStartPointElement(int elementId) {
/* 15 */     super(elementId);
/* 16 */     setType(1000);
/*    */   }
/*    */   
/*    */   public static byte getTeamId(WorldElement element) {
/* 20 */     return element.getParams()[1];
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\element\FightStartPointElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */