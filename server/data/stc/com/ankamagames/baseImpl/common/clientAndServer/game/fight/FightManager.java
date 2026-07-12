/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.fight;
/*     */ 
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FightManager
/*     */ {
/*  22 */   protected static final Logger m_logger = Logger.getLogger(FightManager.class);
/*     */   
/*  24 */   private static final FightManager m_instance = new FightManager();
/*     */   
/*     */ 
/*  27 */   private final TIntObjectHashMap<BasicFight> m_fights = new TIntObjectHashMap();
/*     */   
/*  29 */   private int m_fightId = 0;
/*     */   
/*     */ 
/*     */ 
/*  33 */   private TIntObjectHashMap<BasicFight> m_fightModels = new TIntObjectHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FightManager getInstance()
/*     */   {
/*  46 */     return m_instance;
/*     */   }
/*     */   
/*     */   public void addFightModel(BasicFight model)
/*     */   {
/*  51 */     if (model != null) {
/*  52 */       this.m_fightModels.put(model.getTypeId(), model);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addFight(BasicFight fight)
/*     */   {
/*  61 */     setId(fight);
/*  62 */     this.m_fights.put(fight.getId(), fight);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setId(BasicFight fight)
/*     */   {
/*  72 */     while (this.m_fights.containsKey(this.m_fightId)) {
/*  73 */       if (this.m_fightId == Integer.MAX_VALUE) {
/*  74 */         this.m_fightId = Integer.MIN_VALUE;
/*     */       } else
/*  76 */         this.m_fightId += 1;
/*     */     }
/*  78 */     fight.setId(this.m_fightId);
/*  79 */     if (this.m_fightId == Integer.MAX_VALUE) {
/*  80 */       this.m_fightId = Integer.MIN_VALUE;
/*     */     } else {
/*  82 */       this.m_fightId += 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicFight createFight(int fightId, Map params, CellInformationProvider cellInfoProvider)
/*     */   {
/*  96 */     BasicFight fight = (BasicFight)this.m_fightModels.get(fightId);
/*  97 */     if (fight == null) { return null;
/*     */     }
/*     */     
/* 100 */     BasicFight newFight = fight.newParameterizedInstance(cellInfoProvider, params);
/* 101 */     addFight(newFight);
/* 102 */     newFight.onFightCreatedAndInitialized();
/* 103 */     return newFight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void destroyFight(BasicFight fight)
/*     */   {
/* 113 */     this.m_fights.remove(fight.getId());
/* 114 */     fight.release();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicFight getFightFromId(int id)
/*     */   {
/* 124 */     if (this.m_fights.containsKey(id)) {
/* 125 */       return (BasicFight)this.m_fights.get(id);
/*     */     }
/* 127 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\fight\FightManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */