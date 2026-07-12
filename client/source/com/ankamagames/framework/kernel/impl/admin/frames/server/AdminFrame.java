/*     */ package com.ankamagames.framework.kernel.impl.admin.frames.server;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.framework.kernel.impl.MonitoredProperty;
/*     */ import com.ankamagames.framework.kernel.impl.MonitoredPropertyManager;
/*     */ import com.ankamagames.framework.kernel.impl.admin.entity.AdminEntity;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.clientToServer.PropertyQueryMessage;
/*     */ import com.ankamagames.framework.kernel.impl.admin.messages.serverToClient.PropertyItemMessage;
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
/*     */ public class AdminFrame
/*     */   implements MessageFrame
/*     */ {
/*  27 */   private static final Logger m_logger = Logger.getLogger(AdminFrame.class);
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
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
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
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
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
/*     */   public boolean onMessage(Message message) {
/*  64 */     boolean bForward = true;
/*  65 */     AdminEntity entity = (AdminEntity)message.getHandler();
/*     */     
/*  67 */     switch (message.getId()) {
/*     */       
/*     */       case 12:
/*     */         try {
/*  71 */           PropertyQueryMessage msg = (PropertyQueryMessage)message;
/*  72 */           MonitoredProperty property = MonitoredPropertyManager.getInstance().getPropery(msg.getPropertyName());
/*  73 */           if (property != null) {
/*  74 */             if ((property.getIntArrayEntries()).length == 0 && (property.getStringArrayEntries()).length == 0)
/*  75 */               sendMonitoredProperty(property, entity, null, -1);  byte b; int i;
/*     */             String[] arrayOfString;
/*  77 */             for (i = (arrayOfString = property.getStringArrayEntries()).length, b = 0; b < i; ) { String stringIndex = arrayOfString[b]; byte b1; int j, arrayOfInt[];
/*  78 */               for (j = (arrayOfInt = property.getIntArrayEntries()).length, b1 = 0; b1 < j; ) { int intIndex = arrayOfInt[b1];
/*  79 */                 sendMonitoredProperty(property, entity, stringIndex, intIndex); b1++; }  b++; } 
/*     */           } 
/*  81 */         } catch (Exception e) {
/*  82 */           e.printStackTrace();
/*     */         } 
/*  84 */         bForward = false;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 10:
/*     */         try {
/*  90 */           for (MonitoredProperty property : MonitoredPropertyManager.getInstance().getProperties()) {
/*  91 */             if ((property.getIntArrayEntries()).length == 0 && (property.getStringArrayEntries()).length == 0)
/*  92 */               sendMonitoredProperty(property, entity, null, -1);  byte b; int i;
/*     */             String[] arrayOfString;
/*  94 */             for (i = (arrayOfString = property.getStringArrayEntries()).length, b = 0; b < i; ) { String stringIndex = arrayOfString[b]; byte b1; int j, arrayOfInt[];
/*  95 */               for (j = (arrayOfInt = property.getIntArrayEntries()).length, b1 = 0; b1 < j; ) { int intIndex = arrayOfInt[b1];
/*  96 */                 sendMonitoredProperty(property, entity, stringIndex, intIndex); b1++; }  b++; } 
/*     */           } 
/*  98 */         } catch (Exception e) {
/*  99 */           e.printStackTrace();
/*     */         } 
/*     */         
/* 102 */         bForward = false;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return bForward;
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendMonitoredProperty(MonitoredProperty property, AdminEntity recipient, String stringIndex, int intIndex) {
/* 113 */     Object propertyObject = property.getPropertyValue(stringIndex, intIndex);
/*     */     
/* 115 */     if (propertyObject != null) {
/*     */       
/*     */       try {
/* 118 */         PropertyItemMessage itemMessage = new PropertyItemMessage();
/* 119 */         itemMessage.setPropertyName(property.getPropertyName());
/* 120 */         itemMessage.setPropertyType(property.getPropertyType());
/* 121 */         itemMessage.setStringIndex(stringIndex);
/* 122 */         itemMessage.setIntIndex(intIndex);
/*     */         
/* 124 */         switch (property.getPropertyType()) {
/*     */           case 1:
/* 126 */             itemMessage.setByteValue(((Byte)propertyObject).byteValue());
/*     */             break;
/*     */           case 2:
/* 129 */             itemMessage.setShortValue(((Short)propertyObject).shortValue());
/*     */             break;
/*     */           case 3:
/* 132 */             itemMessage.setIntValue(((Integer)propertyObject).intValue());
/*     */             break;
/*     */           case 4:
/* 135 */             itemMessage.setLongValue(((Long)propertyObject).longValue());
/*     */             break;
/*     */           case 5:
/* 138 */             itemMessage.setDoubleValue(((Double)propertyObject).doubleValue());
/*     */             break;
/*     */           case 6:
/* 141 */             itemMessage.setFloatValue(((Float)propertyObject).floatValue());
/*     */             break;
/*     */           case 7:
/* 144 */             itemMessage.setStringValue((String)propertyObject);
/*     */             break;
/*     */         } 
/*     */         
/* 148 */         recipient.sendMessage((Message)itemMessage);
/* 149 */       } catch (Throwable e) {
/* 150 */         m_logger.error("AdminFrame.sendMonitoredProoperty(stringIndex=" + stringIndex + ", intIndex=" + intIndex + ") exception raised : ", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 162 */     return 1L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\frames\server\AdminFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */