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
/* 33 */     if (endTriggers != null) {
/* 34 */       byte b; int i; int[] arrayOfInt; for (i = (arrayOfInt = endTriggers).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/* 35 */         if (j > 0)
/* 36 */           this.m_endTriggers.set(j); 
/*    */         b++; }
/*    */     
/*    */     }  } public int getUniqueId() {
/* 40 */     return this.m_uniqueId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getContainerType() {
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
/* 57 */     this.m_effects.add((Object[])effects);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<Effect> iterator() {
/* 62 */     return this.m_effects.iterator();
/*    */   }
/*    */   
/*    */   public int getEffectsCount() {
/* 66 */     return this.m_effects.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public BitSet getEndTriggers() {
/* 71 */     return this.m_endTriggers;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\State.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */