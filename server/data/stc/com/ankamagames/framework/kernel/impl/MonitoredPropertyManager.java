/*     */ package com.ankamagames.framework.kernel.impl;
/*     */ 
/*     */ import com.ankamagames.framework.annotations.Nullable;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import java.util.ArrayList;
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
/*     */ public final class MonitoredPropertyManager
/*     */   implements MessageFrame
/*     */ {
/*  23 */   protected static final Logger m_logger = Logger.getLogger(MonitoredPropertyManager.class);
/*  24 */   private static final MonitoredPropertyManager m_instance = new MonitoredPropertyManager();
/*     */   
/*  26 */   private final ArrayList<MonitoredProperty> m_properties = new ArrayList();
/*     */   
/*     */   public static MonitoredPropertyManager getInstance() {
/*  29 */     return m_instance;
/*     */   }
/*     */   
/*     */   private MonitoredPropertyManager() {
/*  33 */     MessageScheduler.getInstance().addClock(this, 10000L, 1);
/*     */   }
/*     */   
/*     */   public void addMonitoredProperty(MonitoredProperty property) {
/*  37 */     if ((!this.m_properties.contains(property)) && 
/*  38 */       (property.getPropertyName() != null) && (!property.getPropertyName().equals(""))) {
/*  39 */       this.m_properties.add(property);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeMonitoredProperty(MonitoredProperty property) {
/*  44 */     this.m_properties.remove(property);
/*     */   }
/*     */   
/*     */   public int getMonitoredPropertyCount() {
/*  48 */     return this.m_properties.size();
/*     */   }
/*     */   
/*     */   public Iterable<MonitoredProperty> getProperties() {
/*  52 */     return this.m_properties;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MonitoredProperty getPropery(String propertyName) {
/*  57 */     for (MonitoredProperty property : this.m_properties) {
/*  58 */       if (property.getPropertyName().equals(propertyName))
/*  59 */         return property;
/*     */     }
/*  61 */     return null;
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
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  96 */     message.getId();
/*     */     
/*     */ 
/*  99 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 108 */     return 1L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\MonitoredPropertyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */