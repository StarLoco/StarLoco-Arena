/*    */ package com.ankamagames.dofusarena.common.game.effectArea;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.GroupEffectManager;
/*    */ import gnu.trove.TLongObjectHashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StaticEffectAreaManager
/*    */ {
/* 14 */   protected static final Logger m_logger = Logger.getLogger(GroupEffectManager.class);
/*    */   
/* 16 */   private static StaticEffectAreaManager m_instance = new StaticEffectAreaManager();
/*    */   
/* 18 */   private final TLongObjectHashMap<AbstractEffectArea> m_staticEffectArea = new TLongObjectHashMap();
/*    */   
/* 20 */   private final TLongObjectHashMap<AbstractEffectArea> m_specialCell = new TLongObjectHashMap();
/*    */ 
/*    */   
/*    */   public static StaticEffectAreaManager getInstance() {
/* 24 */     return m_instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addEffectArea(AbstractEffectArea trap) {
/* 29 */     this.m_staticEffectArea.put(trap.getBaseId(), trap);
/*    */   }
/*    */   
/*    */   public AbstractEffectArea getEffectArea(long trapId) {
/* 33 */     return (AbstractEffectArea)this.m_staticEffectArea.get(trapId);
/*    */   }
/*    */   
/*    */   public void addSpecialCell(AbstractEffectArea sc) {
/* 37 */     this.m_specialCell.put(sc.getBaseId(), sc);
/*    */   }
/*    */   
/*    */   public AbstractEffectArea getSpecialCell(long specialCellId) {
/* 41 */     return (AbstractEffectArea)this.m_specialCell.get(specialCellId);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effectArea\StaticEffectAreaManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */