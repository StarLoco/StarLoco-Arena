/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.world.clientToServer.CoachCreationMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.connection.UICoachCreationMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
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
/*     */   public static UICoachCreationFrame getInstance()
/*     */   {
/*  33 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  42 */     switch (message.getId())
/*     */     {
/*     */     case 16401: 
/*  45 */       UIMessage msg = (UIMessage)message;
/*     */       
/*     */ 
/*  48 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  49 */       if (localCoach != null) {
/*  50 */         byte sex = msg.getByteValue();
/*  51 */         if (sex != localCoach.getSex()) {
/*  52 */           localCoach.setSex(sex);
/*  53 */           localCoach.updateLibraryDescriptorProperty();
/*     */         }
/*     */       }
/*     */       
/*  57 */       return false;
/*     */     
/*     */ 
/*     */     case 16402: 
/*  61 */       UIMessage msg = (UIMessage)message;
/*     */       
/*     */ 
/*  64 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  65 */       if (localCoach != null) {
/*  66 */         byte colorIndex = msg.getByteValue();
/*  67 */         if (colorIndex != localCoach.getHairColorIndex()) {
/*  68 */           localCoach.setHairColorIndex(colorIndex);
/*     */         }
/*     */       }
/*     */       
/*  72 */       return false;
/*     */     
/*     */ 
/*     */     case 16403: 
/*  76 */       UIMessage msg = (UIMessage)message;
/*     */       
/*     */ 
/*  79 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  80 */       if (localCoach != null) {
/*  81 */         byte colorIndex = msg.getByteValue();
/*  82 */         if (colorIndex != localCoach.getSkinColorIndex()) {
/*  83 */           localCoach.setSkinColorIndex(colorIndex);
/*     */         }
/*     */       }
/*     */       
/*  87 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */     case 16404: 
/*  92 */       LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/*  93 */       if (localCoach != null) {
/*  94 */         localCoach.randomizeLook();
/*     */         
/*     */ 
/*     */ 
/*  98 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(localCoach, Coach.FIELDS);
/*     */       }
/*     */       
/* 101 */       return false;
/*     */     
/*     */ 
/*     */     case 16400: 
/* 105 */       UICoachCreationMessage msg = (UICoachCreationMessage)message;
/*     */       
/*     */ 
/* 108 */       CoachCreationMessage netMessage = new CoachCreationMessage();
/* 109 */       netMessage.setLocalCoach(msg.getLocalCoach());
/* 110 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */       
/* 112 */       return false;
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
/*     */   public long getId()
/*     */   {
/* 125 */     return 0L;
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
/*     */   protected void closeDialog()
/*     */   {
/* 144 */     Xulor.getInstance().unload("coachCreationDialog");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void openDialog()
/*     */   {
/* 155 */     Xulor.getInstance().load("coachCreationDialog", Dialogs.getDialogPath("coachCreationDialog"), 0L, (short)10000);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UICoachCreationFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */