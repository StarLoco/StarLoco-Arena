/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.collections.GrowingArray;
/*    */ import java.util.BitSet;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class State
/*    */   implements EffectContainer
/*    */ {
/*    */   private byte m_level;
/*    */   private short m_stateBaseId;
/*    */   private int m_uniqueId;
/* 21 */   protected final GrowingArray<Effect> m_effects = new GrowingArray();
/*    */   
/* 23 */   private final BitSet m_endTriggers = new BitSet();
/*    */   
/*    */   public static int getUniqueIdFromBasicInformation(short baseId, byte level) {
/* 26 */     return (baseId << 8) + level;
/*    */   }
/*    */   
/*    */   public State(short basedId, byte level, int[] endTriggers) {
/* 30 */     this.m_stateBaseId = basedId;
/* 31 */     this.m_level = level;
/* 32 */     this.m_uniqueId = getUniqueIdFromBasicInformation(basedId, level);
/* 33 */     if (endTriggers != null) { int[] arrayOfInt;
/* 34 */       int j = (arrayOfInt = endTriggers).length; for (int i = 0; i < j; i++) { int i = arrayOfInt[i];
/* 35 */         if (i > 0)
/* 36 */           this.m_endTriggers.set(i);
/*    */       }
/*    */     } }
/*    */   
/* 40 */   public int getUniqueId() { return this.m_uniqueId; }
/*    */   
/*    */ 
/*    */   public int getContainerType()
/*    */   {
/* 45 */     return 1;
/*    */   }
/*    */   
/*    */   public long getEffectContainerId() {
/* 49 */     return getUniqueId();
/*    */   }
/*    */   
/*    */   public void addEffect(Effect effect) {
/* 53 */     this.m_effects.add(effect);
/*    */   }
/*    */   
/*    */   public void addEffects(Effect[] effects) {
/* 57 */     this.m_effects.add(effects);
/*    */   }
/*    */   
/*    */   public Iterator<Effect> iterator()
/*    */   {
/* 62 */     return this.m_effects.iterator();
/*    */   }
/*    */   
/*    */   public int getEffectsCount() {
/* 66 */     return this.m_effects.size();
/*    */   }
/*    */   
/*    */   public BitSet getEndTriggers()
/*    */   {
/* 71 */     return this.m_endTriggers;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\State.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */