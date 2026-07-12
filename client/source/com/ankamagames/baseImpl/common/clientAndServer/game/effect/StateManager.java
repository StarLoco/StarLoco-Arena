/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.effect;
/*    */ 
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StateManager
/*    */ {
/* 12 */   protected static final Logger m_logger = Logger.getLogger(StateManager.class);
/* 13 */   private static final StateManager m_uniqueInstance = new StateManager();
/* 14 */   private final TIntObjectHashMap<State> m_states = new TIntObjectHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addState(State state) {
/* 21 */     this.m_states.put(state.getUniqueId(), state);
/*    */   }
/*    */   
/*    */   public TIntObjectHashMap<State> getStates() {
/* 25 */     return this.m_states;
/*    */   }
/*    */   
/*    */   public State getState(int stateId) {
/* 29 */     return (State)this.m_states.get(stateId);
/*    */   }
/*    */   
/*    */   public State getState(short stateBaseId, byte level) {
/* 33 */     return (State)this.m_states.get(State.getUniqueIdFromBasicInformation(stateBaseId, level));
/*    */   }
/*    */ 
/*    */   
/*    */   public static StateManager getInstance() {
/* 38 */     return m_uniqueInstance;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effect\StateManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */