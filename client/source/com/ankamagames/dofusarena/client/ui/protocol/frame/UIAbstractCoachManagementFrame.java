/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.CoachManagementActions;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class UIAbstractCoachManagementFrame
/*     */   implements MessageFrame
/*     */ {
/*     */   public boolean onMessage(Message message) {
/*     */     LocalCoach localCoach;
/*  32 */     switch (message.getId()) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 16405:
/*  37 */         localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  38 */         if (localCoach != null) {
/*  39 */           localCoach.setActorLinkageDirection(localCoach.getActorLinkageDirection().getNextDirection8(1));
/*  40 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged((FieldProvider)localCoach, "actorLinkage");
/*     */         } 
/*     */         
/*  43 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 16406:
/*  49 */         localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  50 */         if (localCoach != null) {
/*  51 */           localCoach.setActorLinkageDirection(localCoach.getActorLinkageDirection().getNextDirection8(-1));
/*  52 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged((FieldProvider)localCoach, "actorLinkage");
/*     */         } 
/*     */         
/*  55 */         return false;
/*     */     } 
/*     */ 
/*     */     
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  68 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {
/*  86 */     if (!isAboutToBeAdded) {
/*     */ 
/*     */       
/*  89 */       Xulor.getInstance().putActionClass("dofusarena.coachManagement", CoachManagementActions.class);
/*     */ 
/*     */       
/*  92 */       openDialog();
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
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 104 */     if (!isAboutToBeRemoved) {
/*     */ 
/*     */       
/* 107 */       Xulor.getInstance().removeActionClass("dofusarena.coachManagement");
/*     */ 
/*     */       
/* 110 */       closeDialog();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void openDialog();
/*     */   
/*     */   protected abstract void closeDialog();
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIAbstractCoachManagementFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */