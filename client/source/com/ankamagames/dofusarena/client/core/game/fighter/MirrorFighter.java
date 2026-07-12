/*    */ package com.ankamagames.dofusarena.client.core.game.fighter;
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
/*    */ public class MirrorFighter
/*    */   extends SummonedFighter
/*    */ {
/*    */   public MirrorFighter(Fighter father, SummoningDefinition definition) {
/* 21 */     super(father, definition);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBlockingLOS(Object lineOfSightChecker) {
/* 30 */     if (getFather() == lineOfSightChecker) {
/* 31 */       return false;
/*    */     }
/* 33 */     return super.isBlockingLOS(lineOfSightChecker);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\MirrorFighter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */