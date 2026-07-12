/*     */ package com.ankamagames.xulor.event;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.ClockMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import com.ankamagames.xulor.template.IListenerManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MouseManager
/*     */   implements MessageHandler
/*     */ {
/*     */   public static final int DEFAULT_DOUBLE_CLICK_LATENCY = 220;
/*  21 */   protected static final MouseManager m_manager = new MouseManager();
/*     */   
/*  23 */   private int m_doubleClickLatency = 220;
/*     */   private IListenerManager m_componentPressed;
/*  25 */   private final Map<IListenerManager, MouseReleasedEvent> m_componentMap = new HashMap<IListenerManager, MouseReleasedEvent>();
/*     */   private long m_id;
/*  27 */   private MouseButtons m_buttonPressed = null;
/*     */   
/*     */   private MouseManager() {
/*  30 */     this.m_id = hashCode();
/*  31 */     MessageScheduler.getInstance().start();
/*  32 */     Worker.getInstance().start();
/*     */   }
/*     */   
/*     */   public static MouseManager getInstance() {
/*  36 */     return m_manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  45 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {
/*  54 */     this.m_id = id;
/*     */   }
/*     */   
/*     */   public int getDoubleClickLatency() {
/*  58 */     return this.m_doubleClickLatency;
/*     */   }
/*     */   
/*     */   public void setDoubleClickLatency(int doubleClickLatency) {
/*  62 */     this.m_doubleClickLatency = doubleClickLatency;
/*     */   }
/*     */   
/*     */   public void notifyPressed(IListenerManager component, MousePressedEvent mousePressedEvent) {
/*  66 */     this.m_componentPressed = component;
/*  67 */     this.m_buttonPressed = mousePressedEvent.getButton();
/*     */   }
/*     */   
/*     */   public void notifyReleased(IListenerManager component, MouseReleasedEvent mouseReleaseEvent) {
/*  71 */     if (this.m_componentPressed == component) {
/*  72 */       if (component.hasDoubleClickListener()) {
/*  73 */         if (this.m_componentMap.containsKey(component) && this.m_buttonPressed == mouseReleaseEvent.getButton()) {
/*  74 */           this.m_componentMap.remove(component);
/*  75 */           component.doubleClick(mouseReleaseEvent);
/*     */         } else {
/*  77 */           this.m_componentMap.put(component, mouseReleaseEvent);
/*  78 */           MessageScheduler.getInstance().addClock(this, this.m_doubleClickLatency, component.hashCode(), 1);
/*     */         } 
/*     */       } else {
/*  81 */         component.simpleClick(mouseReleaseEvent);
/*     */       } 
/*     */     } else {
/*  84 */       this.m_componentPressed = null;
/*     */     } 
/*  86 */     this.m_buttonPressed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMessage(Message message) {
/*  96 */     MessageScheduler.getInstance().removeClock(message.getId());
/*  97 */     for (IListenerManager component : this.m_componentMap.keySet()) {
/*  98 */       if (component.hashCode() == ((ClockMessage)message).getSubId()) {
/*  99 */         component.simpleClick(this.m_componentMap.get(component));
/* 100 */         this.m_componentMap.remove(component);
/* 101 */         return false;
/*     */       } 
/*     */     } 
/* 104 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MouseManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */