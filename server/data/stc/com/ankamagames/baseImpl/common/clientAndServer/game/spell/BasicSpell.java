/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.spell;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public abstract class BasicSpell<BSP extends BasicSpellStep>
/*    */ {
/*    */   private int m_id;
/* 22 */   private final List<BSP> m_steps = new ArrayList();
/*    */   
/*    */   public BasicSpell(int id) {
/* 25 */     this.m_id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 29 */     return this.m_id;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addStep(BSP step)
/*    */   {
/* 37 */     if (!this.m_steps.contains(step)) {
/* 38 */       this.m_steps.add(step);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public BSP getStep(byte level)
/*    */   {
/* 47 */     for (BSP bss : this.m_steps) {
/* 48 */       if ((level >= bss.getStartLevel()) && (level <= bss.getStartLevel()))
/* 49 */         return bss;
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\spell\BasicSpell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */