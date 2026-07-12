/*    */ package com.ankamagames.dofusarena.common.game.event;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*    */ import com.ankamagames.framework.kernel.core.common.collections.GrowingArray;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AbstractEvent
/*    */   implements EffectContainer
/*    */ {
/* 17 */   private final GrowingArray<Effect> m_eventEffects = new GrowingArray();
/*    */   
/*    */   private int m_id;
/*    */   
/*    */   public AbstractEvent(int id) {
/* 22 */     this.m_id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 26 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public long getEffectContainerId() {
/* 30 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public void addEffect(Effect effect) {
/* 34 */     this.m_eventEffects.add(effect);
/*    */   }
/*    */   
/*    */   public void addEffects(Effect[] effects) {
/* 38 */     this.m_eventEffects.add((Object[])effects);
/*    */   }
/*    */   
/*    */   public int getContainerType() {
/* 42 */     return 14;
/*    */   }
/*    */   
/*    */   public Iterator<Effect> iterator() {
/* 46 */     return this.m_eventEffects.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public GrowingArray<Effect> getEventEffects() {
/* 51 */     return this.m_eventEffects;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\event\AbstractEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */