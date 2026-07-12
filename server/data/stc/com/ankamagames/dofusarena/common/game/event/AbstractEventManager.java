/*    */ package com.ankamagames.dofusarena.common.game.event;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AbstractEventManager
/*    */ {
/* 14 */   private static final AbstractEventManager m_instance = new AbstractEventManager();
/*    */   
/* 16 */   private final HashMap<Long, AbstractEvent> m_events = new HashMap();
/*    */   
/*    */   public static AbstractEventManager getInstance() {
/* 19 */     return m_instance;
/*    */   }
/*    */   
/*    */   public void addEvent(AbstractEvent event) {
/* 23 */     if (!this.m_events.containsKey(Long.valueOf(event.getId()))) {
/* 24 */       this.m_events.put(Long.valueOf(event.getId()), event);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getEventCount() {
/* 29 */     return this.m_events.size();
/*    */   }
/*    */   
/*    */   public AbstractEvent getAbstractEventFromId(long eventId)
/*    */   {
/* 34 */     if (this.m_events.containsKey(Long.valueOf(eventId))) {
/* 35 */       return (AbstractEvent)this.m_events.get(Long.valueOf(eventId));
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ArrayList<AbstractEvent> newShufflizedEvents()
/*    */   {
/* 46 */     ArrayList<AbstractEvent> events = new ArrayList();
/* 47 */     events.addAll(this.m_events.values());
/* 48 */     Collections.shuffle(events);
/* 49 */     return events;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\event\AbstractEventManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */