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
/*     */ import java.util.Iterator;
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
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  64 */     boolean bForward = true;
/*  65 */     AdminEntity entity = (AdminEntity)message.getHandler();
/*     */     MonitoredProperty property;
/*  67 */     String[] arrayOfString; int j; int i; int[] arrayOfInt; int m; int k; switch (message.getId())
/*     */     {
/*     */     case 12: 
/*     */       try {
/*  71 */         PropertyQueryMessage msg = (PropertyQueryMessage)message;
/*  72 */         property = MonitoredPropertyManager.getInstance().getPropery(msg.getPropertyName());
/*  73 */         if (property != null) {
/*  74 */           if ((property.getIntArrayEntries().length == 0) && (property.getStringArrayEntries().length == 0)) {
/*  75 */             sendMonitoredProperty(property, entity, null, -1);
/*     */           }
/*  77 */           j = (arrayOfString = property.getStringArrayEntries()).length; for (i = 0; i < j; i++) { String stringIndex = arrayOfString[i];
/*  78 */             m = (arrayOfInt = property.getIntArrayEntries()).length; for (k = 0; k < m; k++) { int intIndex = arrayOfInt[k];
/*  79 */               sendMonitoredProperty(property, entity, stringIndex, intIndex);
/*     */             }
/*     */           }
/*  82 */         } } catch (Exception e) { e.printStackTrace();
/*     */       }
/*  84 */       bForward = false;
/*     */       
/*  86 */       break;
/*     */     case 10: 
/*     */       try
/*     */       {
/*  90 */         for (property = MonitoredPropertyManager.getInstance().getProperties().iterator(); property.hasNext(); 
/*     */             
/*     */ 
/*     */ 
/*  94 */             i < j)
/*     */         {
/*  90 */           MonitoredProperty property = (MonitoredProperty)property.next();
/*  91 */           if ((property.getIntArrayEntries().length == 0) && (property.getStringArrayEntries().length == 0)) {
/*  92 */             sendMonitoredProperty(property, entity, null, -1);
/*     */           }
/*  94 */           j = (arrayOfString = property.getStringArrayEntries()).length;i = 0; continue;String stringIndex = arrayOfString[i];
/*  95 */           m = (arrayOfInt = property.getIntArrayEntries()).length; for (k = 0; k < m; k++) { int intIndex = arrayOfInt[k];
/*  96 */             sendMonitoredProperty(property, entity, stringIndex, intIndex);
/*     */           }
/*  94 */           i++;
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  99 */         e.printStackTrace();
/*     */       }
/*     */       
/* 102 */       bForward = false;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 108 */     return bForward;
/*     */   }
/*     */   
/*     */   private void sendMonitoredProperty(MonitoredProperty property, AdminEntity recipient, String stringIndex, int intIndex)
/*     */   {
/* 113 */     Object propertyObject = property.getPropertyValue(stringIndex, intIndex);
/*     */     
/* 115 */     if (propertyObject != null) {
/*     */       try
/*     */       {
/* 118 */         PropertyItemMessage itemMessage = new PropertyItemMessage();
/* 119 */         itemMessage.setPropertyName(property.getPropertyName());
/* 120 */         itemMessage.setPropertyType(property.getPropertyType());
/* 121 */         itemMessage.setStringIndex(stringIndex);
/* 122 */         itemMessage.setIntIndex(intIndex);
/*     */         
/* 124 */         switch (property.getPropertyType()) {
/*     */         case 1: 
/* 126 */           itemMessage.setByteValue(((Byte)propertyObject).byteValue());
/* 127 */           break;
/*     */         case 2: 
/* 129 */           itemMessage.setShortValue(((Short)propertyObject).shortValue());
/* 130 */           break;
/*     */         case 3: 
/* 132 */           itemMessage.setIntValue(((Integer)propertyObject).intValue());
/* 133 */           break;
/*     */         case 4: 
/* 135 */           itemMessage.setLongValue(((Long)propertyObject).longValue());
/* 136 */           break;
/*     */         case 5: 
/* 138 */           itemMessage.setDoubleValue(((Double)propertyObject).doubleValue());
/* 139 */           break;
/*     */         case 6: 
/* 141 */           itemMessage.setFloatValue(((Float)propertyObject).floatValue());
/* 142 */           break;
/*     */         case 7: 
/* 144 */           itemMessage.setStringValue((String)propertyObject);
/*     */         }
/*     */         
/*     */         
/* 148 */         recipient.sendMessage(itemMessage);
/*     */       } catch (Throwable e) {
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
/*     */   public long getId()
/*     */   {
/* 162 */     return 1L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\frames\server\AdminFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */