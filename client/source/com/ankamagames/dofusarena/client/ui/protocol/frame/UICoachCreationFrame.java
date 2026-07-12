/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.CoachCreationMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.connection.UICoachCreationMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
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
/*     */ public class UICoachCreationFrame
/*     */   extends UIAbstractCoachManagementFrame
/*     */ {
/*  27 */   private static UICoachCreationFrame m_instance = new UICoachCreationFrame();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UICoachCreationFrame getInstance() {
/*  33 */     return m_instance;
/*     */   }
/*     */   
/*     */   public boolean onMessage(Message message) {
/*     */     UIMessage uIMessage;
/*     */     LocalCoach localCoach;
/*     */     UICoachCreationMessage msg;
/*     */     LocalCoach localCoach1;
/*     */     CoachCreationMessage netMessage;
/*  42 */     switch (message.getId()) {
/*     */       
/*     */       case 16401:
/*  45 */         uIMessage = (UIMessage)message;
/*     */ 
/*     */         
/*  48 */         localCoach1 = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  49 */         if (localCoach1 != null) {
/*  50 */           byte sex = uIMessage.getByteValue();
/*  51 */           if (sex != localCoach1.getSex()) {
/*  52 */             localCoach1.setSex(sex);
/*  53 */             localCoach1.updateLibraryDescriptorProperty();
/*     */           } 
/*     */         } 
/*     */         
/*  57 */         return false;
/*     */ 
/*     */       
/*     */       case 16402:
/*  61 */         uIMessage = (UIMessage)message;
/*     */ 
/*     */         
/*  64 */         localCoach1 = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  65 */         if (localCoach1 != null) {
/*  66 */           byte colorIndex = uIMessage.getByteValue();
/*  67 */           if (colorIndex != localCoach1.getHairColorIndex()) {
/*  68 */             localCoach1.setHairColorIndex(colorIndex);
/*     */           }
/*     */         } 
/*     */         
/*  72 */         return false;
/*     */ 
/*     */       
/*     */       case 16403:
/*  76 */         uIMessage = (UIMessage)message;
/*     */ 
/*     */         
/*  79 */         localCoach1 = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  80 */         if (localCoach1 != null) {
/*  81 */           byte colorIndex = uIMessage.getByteValue();
/*  82 */           if (colorIndex != localCoach1.getSkinColorIndex()) {
/*  83 */             localCoach1.setSkinColorIndex(colorIndex);
/*     */           }
/*     */         } 
/*     */         
/*  87 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 16404:
/*  92 */         localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  93 */         if (localCoach != null) {
/*  94 */           localCoach.randomizeLook();
/*     */ 
/*     */ 
/*     */           
/*  98 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged((FieldProvider)localCoach, Coach.FIELDS);
/*     */         } 
/*     */         
/* 101 */         return false;
/*     */ 
/*     */       
/*     */       case 16400:
/* 105 */         msg = (UICoachCreationMessage)message;
/*     */ 
/*     */         
/* 108 */         netMessage = new CoachCreationMessage();
/* 109 */         netMessage.setLocalCoach(msg.getLocalCoach());
/* 110 */         DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */         
/* 112 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 116 */     return super.onMessage(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 125 */     return 0L;
/*     */   }
/*     */ 
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
/*     */   protected void closeDialog() {
/* 144 */     Xulor.getInstance().unload("coachCreationDialog");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void openDialog() {
/* 155 */     Xulor.getInstance().load("coachCreationDialog", Dialogs.getDialogPath("coachCreationDialog"), 0L, (short)10000);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UICoachCreationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */