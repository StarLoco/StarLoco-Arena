/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
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
/*     */ 
/*     */ public abstract class UIAbstractMenuBarFrame
/*     */   implements MessageFrame
/*     */ {
/*     */   public boolean onMessage(Message message) {
/*  30 */     switch (message.getId()) {
/*     */ 
/*     */       
/*     */       case 20007:
/*  34 */         if (Xulor.getInstance().isLoaded("menuDialog")) {
/*     */           
/*  36 */           DofusArenaGameEntity.getInstance().removeFrame(UIMenuFrame.getInstance());
/*     */         } else {
/*     */           
/*  39 */           DofusArenaGameEntity.getInstance().pushFrame(UIMenuFrame.getInstance());
/*     */         } 
/*     */         
/*  42 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 20001:
/*  47 */         if (Xulor.getInstance().isLoaded("chatDialog")) {
/*  48 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("chat.dialogView").setFieldValue("input", "");
/*  49 */           Xulor.getInstance().unload("chatDialog");
/*  50 */           if (Xulor.getInstance().isLoaded("contactListDialog")) {
/*  51 */             Xulor.getInstance().unload("contactListDialog");
/*     */           }
/*     */         } else {
/*  54 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("chat.isMaximize", Boolean.valueOf(false));
/*  55 */           Xulor.getInstance().load("chatDialog", Dialogs.getDialogPath("chatDialog"), 257L, (short)19000);
/*     */         } 
/*     */         
/*  58 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 20002:
/*  63 */         if (!Xulor.getInstance().isLoaded("chatDialog")) {
/*  64 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("chat.isMaximize", Boolean.valueOf(false));
/*  65 */           Xulor.getInstance().load("chatDialog", Dialogs.getDialogPath("chatDialog"), 257L, (short)19000);
/*     */         } 
/*     */         
/*  68 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 20003:
/*  73 */         if (Xulor.getInstance().isLoaded("chatDialog")) {
/*  74 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("chat.dialogView").setFieldValue("input", "");
/*  75 */           Xulor.getInstance().unload("chatDialog");
/*  76 */           if (Xulor.getInstance().isLoaded("contactListDialog")) {
/*  77 */             Xulor.getInstance().unload("contactListDialog");
/*     */           }
/*     */         } 
/*     */         
/*  81 */         return false;
/*     */ 
/*     */ 
/*     */       
/*     */       case 20011:
/*  86 */         if (Xulor.getInstance().isLoaded("optionsDialog")) {
/*  87 */           Xulor.getInstance().unload("optionsDialog");
/*     */         } else {
/*  89 */           Xulor.getInstance().load("optionsDialog", Dialogs.getDialogPath("optionsDialog"), (short)10000);
/*     */         } 
/*     */         
/*  92 */         return false;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {
/* 106 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */       
/* 109 */       Xulor.getInstance().hideTooltip();
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\frame\UIAbstractMenuBarFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */