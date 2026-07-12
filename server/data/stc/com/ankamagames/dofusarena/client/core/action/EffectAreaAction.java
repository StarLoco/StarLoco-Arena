/*    */ package com.ankamagames.dofusarena.client.core.action;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.EffectAreaManager;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.MobileFunctionsLibrary;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.ParticleSystemFunctionsLibrary;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.effectArea.EffectArea;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.client.core.script.EffectAreaActionFunctionsLibrary;
/*    */ import com.ankamagames.framework.script.action.ScriptedAction;
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
/*    */ public class EffectAreaAction
/*    */   extends ScriptedAction
/*    */ {
/*    */   private final boolean m_apply;
/*    */   
/*    */   public EffectAreaAction(int uniqueId, int actionType, int actionId, boolean apply, EffectArea area)
/*    */   {
/* 31 */     super(uniqueId, actionType, actionId);
/* 32 */     this.m_apply = apply;
/*    */     
/* 34 */     addJavaFunctionsLibrary(ParticleSystemFunctionsLibrary.getInstance());
/* 35 */     addJavaFunctionsLibrary(MobileFunctionsLibrary.getInstance());
/* 36 */     addJavaFunctionsLibrary(new EffectAreaActionFunctionsLibrary(this));
/*    */     
/* 38 */     setScriptFileId(area.getScriptId());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run()
/*    */   {
/* 49 */     Fighter fighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getTargetId());
/*    */     
/* 51 */     if (fighter != null) {
/* 52 */       BasicEffectArea area = DofusArenaGameEntity.getInstance().getFight().getEffectAreaManager().getActiveEffectAreaWithId(getInstigatorId());
/*    */       
/* 54 */       if (area != null) {
/* 55 */         if (this.m_apply) {
/* 56 */           area.apply(fighter);
/*    */           
/*    */ 
/* 59 */           super.run();
/* 60 */           return;
/*    */         }
/* 62 */         area.unapply(fighter);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 67 */     fireActionFinishedEvent();
/*    */   }
/*    */   
/*    */   protected void onActionFinished() {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\EffectAreaAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */