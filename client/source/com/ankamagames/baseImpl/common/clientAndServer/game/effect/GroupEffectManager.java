/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect;
/*    */ 
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class GroupEffectManager
/*    */ {
/* 22 */   protected static final Logger m_logger = Logger.getLogger(GroupEffectManager.class);
/* 23 */   private static final GroupEffectManager m_uniqueInstance = new GroupEffectManager();
/* 24 */   private final TIntObjectHashMap<List<Effect>> m_effects = new TIntObjectHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addEffect(int groupeffectId, Effect effect) {
/* 30 */     if (this.m_effects.contains(groupeffectId)) {
/* 31 */       ((List<Effect>)this.m_effects.get(groupeffectId)).add(effect);
/*    */     } else {
/* 33 */       ArrayList<Effect> aEffects = new ArrayList<Effect>();
/* 34 */       aEffects.add(effect);
/* 35 */       this.m_effects.put(groupeffectId, aEffects);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Effect> getEffect(int groupeffectId) {
/* 41 */     return (List<Effect>)this.m_effects.get(groupeffectId);
/*    */   }
/*    */ 
/*    */   
/*    */   public static GroupEffectManager getInstance() {
/* 46 */     return m_uniqueInstance;
/*    */   }
/*    */   
/*    */   public long getId() {
/* 50 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\GroupEffectManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */