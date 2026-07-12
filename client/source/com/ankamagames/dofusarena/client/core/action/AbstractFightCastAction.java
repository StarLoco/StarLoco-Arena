/*     */ package com.ankamagames.dofusarena.client.core.action;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.MobileFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.ParticleSystemFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.SoundFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.VisualEffectFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaIsoCamera;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.script.FightCastActionFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.action.ScriptedAction;
/*     */ import com.ankamagames.graphics.isometric.DefaultIsoWorldTarget;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFightCastAction
/*     */   extends ScriptedAction
/*     */ {
/*  28 */   private static int CRITICAL_MISS_SCRIPT = 999;
/*  29 */   private static float CRITICAL_ZOOM_FACTOR = 1.4F;
/*     */   
/*  31 */   protected static FightLogger m_fightLogger = new FightLogger();
/*     */ 
/*     */   
/*     */   private IsoWorldTarget m_savedCameraTarget;
/*     */ 
/*     */   
/*     */   private boolean m_isCriticalHit;
/*     */ 
/*     */   
/*     */   private boolean m_isCriticalMiss;
/*     */ 
/*     */   
/*     */   private int m_x;
/*     */ 
/*     */   
/*     */   private int m_y;
/*     */ 
/*     */   
/*     */   private short m_z;
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractFightCastAction(int uniqueId, int actionType, int actionId, boolean criticalHit, boolean criticalMiss, long casterId, int x, int y, short z) {
/*  54 */     super(uniqueId, actionType, actionId);
/*     */ 
/*     */     
/*  57 */     addJavaFunctionsLibrary((JavaFunctionsLibrary)MobileFunctionsLibrary.getInstance());
/*  58 */     addJavaFunctionsLibrary((JavaFunctionsLibrary)ParticleSystemFunctionsLibrary.getInstance());
/*  59 */     addJavaFunctionsLibrary((JavaFunctionsLibrary)SoundFunctionsLibrary.getInstance());
/*  60 */     addJavaFunctionsLibrary((JavaFunctionsLibrary)VisualEffectFunctionsLibrary.getInstance());
/*  61 */     addJavaFunctionsLibrary((JavaFunctionsLibrary)new FightCastActionFunctionsLibrary(this));
/*     */     
/*  63 */     setInstigatorId(casterId);
/*     */     
/*  65 */     this.m_isCriticalHit = criticalHit;
/*  66 */     this.m_isCriticalMiss = criticalMiss;
/*  67 */     this.m_x = x;
/*  68 */     this.m_y = y;
/*  69 */     this.m_z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  79 */     if (this.m_isCriticalMiss) {
/*  80 */       setScriptFileId(CRITICAL_MISS_SCRIPT);
/*     */     }
/*     */ 
/*     */     
/*  84 */     if (this.m_isCriticalHit || this.m_isCriticalMiss) {
/*  85 */       AleaIsoCamera camera = DofusArenaClientInstance.getInstance().getWorldScene().getIsoCamera();
/*  86 */       this.m_savedCameraTarget = camera.getTrackingTarget();
/*     */       
/*  88 */       FighterActor fighterActor = ((Fighter)DofusArenaGameEntity.getInstance().getFight().getFighterById(getInstigatorId())).getActor();
/*     */       
/*  90 */       double cameraX = (fighterActor.getWorldX() + getX()) / 2.0D;
/*  91 */       double cameraY = (fighterActor.getWorldY() + getY()) / 2.0D;
/*  92 */       double cameraZ = (fighterActor.getAltitude() + getZ()) / 2.0D;
/*     */       
/*  94 */       DefaultIsoWorldTarget actionCameraTarget = new DefaultIsoWorldTarget(cameraX, cameraY, cameraZ);
/*     */       
/*  96 */       camera.setTrackingTarget((IsoWorldTarget)actionCameraTarget);
/*  97 */       camera.setDesiredZoomFactor(CRITICAL_ZOOM_FACTOR);
/*     */     } 
/*     */     
/* 100 */     super.run();
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
/*     */   protected void onActionFinished() {
/* 112 */     if (this.m_isCriticalHit || this.m_isCriticalMiss) {
/* 113 */       AleaIsoCamera camera = DofusArenaClientInstance.getInstance().getWorldScene().getIsoCamera();
/* 114 */       camera.setTrackingTarget(this.m_savedCameraTarget);
/* 115 */       camera.setDesiredZoomFactor(0.8999999761581421D);
/*     */     } 
/* 117 */     super.onActionFinished();
/*     */   }
/*     */   
/*     */   public boolean isCriticalHit() {
/* 121 */     return this.m_isCriticalHit;
/*     */   }
/*     */   
/*     */   public void setCriticalHit(boolean criticalHit) {
/* 125 */     this.m_isCriticalHit = criticalHit;
/*     */   }
/*     */   
/*     */   public boolean isCriticalMiss() {
/* 129 */     return this.m_isCriticalMiss;
/*     */   }
/*     */   
/*     */   public void setCriticalMiss(boolean criticalMiss) {
/* 133 */     this.m_isCriticalMiss = criticalMiss;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 137 */     return this.m_x;
/*     */   }
/*     */   
/*     */   public void setX(int x) {
/* 141 */     this.m_x = x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 145 */     return this.m_y;
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/* 149 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public short getZ() {
/* 153 */     return this.m_z;
/*     */   }
/*     */   
/*     */   public void setZ(short z) {
/* 157 */     this.m_z = z;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\action\AbstractFightCastAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */