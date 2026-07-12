/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.time;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.filter.Filter;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.filter.Filterable;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.pool.ObjectPool;
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
/*     */ public abstract class BasicTimeline<TU extends TimeUnit<TU, TI>, TI extends TimeInterval>
/*     */   implements Poolable, TimeEventListener, Filterable<TimeEvent<TU, TI>>
/*     */ {
/*  35 */   protected static final Logger m_logger = Logger.getLogger("Timeline");
/*     */   
/*     */   private ObjectPool m_pool;
/*     */   
/*     */   protected List<TimeEvent<TU, TI>> m_timeline;
/*     */   
/*  41 */   protected boolean m_isRunning = false;
/*     */   private TimeEventListener m_globalListener;
/*     */   
/*     */   public TimeEventListener getGlobalListener()
/*     */   {
/*  46 */     return this.m_globalListener;
/*     */   }
/*     */   
/*     */   public void setGlobalListener(TimeEventListener globalListener) {
/*  50 */     this.m_globalListener = globalListener;
/*     */   }
/*     */   
/*     */   public void setPool(ObjectPool pool)
/*     */   {
/*  55 */     this.m_pool = pool;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {}
/*     */   
/*     */   public void onCheckIn()
/*     */   {
/*  62 */     if (this.m_timeline != null) {
/*  63 */       for (TimeEvent te : this.m_timeline) {
/*  64 */         te.release();
/*     */       }
/*  66 */       this.m_timeline.clear();
/*     */     }
/*  68 */     this.m_timeline = null;
/*  69 */     this.m_globalListener = null;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  73 */     for (TimeEvent te : this.m_timeline) {
/*  74 */       te.release();
/*     */     }
/*  76 */     this.m_timeline.clear();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  81 */     return (this.m_timeline == null) || (this.m_timeline.size() == 0);
/*     */   }
/*     */   
/*     */   public void release() {
/*  85 */     if (this.m_pool != null) {
/*     */       try {
/*  87 */         this.m_pool.returnObject(this);
/*     */       } catch (Exception e) {
/*  89 */         m_logger.error("Exception dans le release de " + getClass().toString() + " normalement impossible");
/*     */       }
/*     */     } else {
/*  92 */       onCheckIn();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addTimeEventAt(TimeEvent<TU, TI> te, TU tu)
/*     */   {
/* 109 */     int timeDiff = -1;
/*     */     
/* 111 */     if (tu != null) { timeDiff = tu.compareTo(now());
/*     */     }
/* 113 */     if (timeDiff < 0) {
/* 114 */       this.m_timeline.add(0, te);
/* 115 */       nextTimeEvent();
/* 116 */       return;
/*     */     }
/*     */     
/* 119 */     int index = 0;
/* 120 */     for (TimeEvent<TU, TI> event : this.m_timeline) {
/* 121 */       int diff = -1;
/* 122 */       if ((tu != null) && (event.when() != null)) diff = tu.compareTo(event.when());
/* 123 */       if (diff > 0) {
/* 124 */         index++;
/*     */       } else {
/* 126 */         if ((diff != 0) || 
/* 127 */           (te.getPriority() > event.getPriority())) break;
/* 128 */         index++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 138 */     if (index >= this.m_timeline.size()) {
/* 139 */       this.m_timeline.add(te);
/*     */     } else {
/* 141 */       this.m_timeline.add(index, te);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 146 */     if ((timeDiff == 0) && (getFirstTimeEvent() == te)) {
/* 147 */       nextTimeEvent();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addTimeEvent(TimeEvent<TU, TI> te)
/*     */   {
/* 159 */     addTimeEventAt(te, te.when());
/*     */   }
/*     */   
/*     */   public void removeTimeEvent(TimeEvent te) {
/* 163 */     if (this.m_timeline.remove(te)) {
/* 164 */       te.release();
/*     */     }
/*     */   }
/*     */   
/*     */   public List<TimeEvent<TU, TI>> filter(Filter<TimeEvent<TU, TI>> filter)
/*     */   {
/* 170 */     ArrayList<TimeEvent<TU, TI>> filteredTimeEvents = new ArrayList();
/* 171 */     for (TimeEvent<TU, TI> te : this.m_timeline) {
/* 172 */       if (filter.isValid(te))
/* 173 */         filteredTimeEvents.add(te);
/*     */     }
/* 175 */     return filteredTimeEvents;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void nextTimeEvent()
/*     */   {
/* 187 */     if (!this.m_isRunning) { return;
/*     */     }
/* 189 */     TimeEvent<TU, TI> te = getFirstTimeEvent();
/*     */     
/* 191 */     if (te == null) {
/* 192 */       m_logger.error("nextTimeEvent sur une timeline sans aucun TimeEvent");
/* 193 */       return;
/*     */     }
/*     */     
/* 196 */     if (te.needValidation()) { return;
/*     */     }
/* 198 */     if (te.isActive())
/*     */     {
/* 200 */       this.m_timeline.remove(te);
/* 201 */       if (!te.isInstant())
/*     */       {
/* 203 */         if (!te.isInfinite()) {
/* 204 */           te.switchStatus();
/* 205 */           te.shiftStart(now(), te.getDuration());
/* 206 */           addTimeEvent(te);
/*     */         }
/*     */       }
/* 209 */       if (te.getListener() != null) {
/* 210 */         te.getListener().onTimeEventActivated(te);
/*     */       } else {
/* 212 */         m_logger.error("Listener null pour un timeEvent de type " + te.getType() + " et d'id " + te.getId());
/*     */       }
/*     */     } else {
/* 215 */       this.m_timeline.remove(0);
/* 216 */       te.getListener().onTimeEventDesactivated(te);
/*     */       
/* 218 */       te.release();
/*     */     }
/* 220 */     if (!isEmpty()) {
/* 221 */       nextTimeEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public void start() {
/* 226 */     if (!this.m_isRunning) {
/* 227 */       this.m_isRunning = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void stop() {
/* 232 */     this.m_isRunning = false;
/*     */   }
/*     */   
/*     */   public boolean isRunning()
/*     */   {
/* 237 */     return this.m_isRunning;
/*     */   }
/*     */   
/*     */   public TimeEvent<TU, TI> getFirstTimeEvent() {
/* 241 */     if (this.m_timeline.size() > 0) {
/* 242 */       return (TimeEvent)this.m_timeline.get(0);
/*     */     }
/* 244 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract TU now();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onTimeEventActivated(TimeEvent te)
/*     */   {
/* 258 */     this.m_globalListener.onTimeEventActivated(te);
/*     */   }
/*     */   
/*     */   public void onTimeEventDesactivated(TimeEvent te) {
/* 262 */     this.m_globalListener.onTimeEventDesactivated(te);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\time\BasicTimeline.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */