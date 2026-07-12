/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.effectArea;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectUser;
/*     */ import com.ankamagames.framework.ai.targetfinder.Target;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EffectAreaManager
/*     */   implements Poolable, Iterable<BasicEffectArea>
/*     */ {
/*  23 */   protected static final Logger m_logger = Logger.getLogger(EffectAreaManager.class);
/*     */ 
/*     */   
/*  26 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<EffectAreaManager>() {
/*     */         public EffectAreaManager makeObject() {
/*  28 */           return new EffectAreaManager(null);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private boolean m_pooled;
/*     */   private ArrayList<BasicEffectArea> m_effectAreaList;
/*     */   private HashMap<Long, BasicEffectArea> m_activeEffectArea;
/*     */   private EffectAreaActionListener m_listener;
/*     */   
/*     */   private EffectAreaManager() {
/*  39 */     this.m_activeEffectArea = new HashMap<Long, BasicEffectArea>();
/*  40 */     this.m_effectAreaList = new ArrayList<BasicEffectArea>();
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
/*     */   public static EffectAreaManager checkOut(EffectAreaActionListener listener) {
/*     */     EffectAreaManager manager;
/*     */     try {
/*  55 */       manager = (EffectAreaManager)m_pool.borrowObject();
/*  56 */       manager.m_pooled = true;
/*     */     }
/*  58 */     catch (Exception e) {
/*  59 */       m_logger.error("Erreur lors d'un checkOut sur un message de type EffectAreaManager : " + e.getMessage());
/*  60 */       manager = new EffectAreaManager();
/*  61 */       manager.m_pooled = false;
/*  62 */       manager.onCheckOut();
/*     */     } 
/*  64 */     manager.m_listener = listener;
/*  65 */     return manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  75 */     if (this.m_pooled) {
/*     */       try {
/*  77 */         m_pool.returnObject(this);
/*  78 */       } catch (Exception e) {
/*  79 */         m_logger.error("impossible");
/*     */       } 
/*     */     } else {
/*  82 */       onCheckIn();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {
/*  91 */     this.m_listener = null;
/*  92 */     this.m_activeEffectArea.clear();
/*  93 */     this.m_effectAreaList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/* 101 */     destroyAll();
/* 102 */     this.m_listener = null;
/*     */   }
/*     */   
/*     */   public int getActiveEffectAreaCount() {
/* 106 */     return this.m_activeEffectArea.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<BasicEffectArea> getActiveEffectAreas() {
/* 115 */     return this.m_activeEffectArea.values();
/*     */   }
/*     */   
/*     */   public BasicEffectArea getActiveEffectAreaWithId(long id) {
/* 119 */     return this.m_activeEffectArea.get(Long.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEffectArea(BasicEffectArea area) {
/* 129 */     if (!this.m_activeEffectArea.containsKey(Long.valueOf(area.getId()))) {
/* 130 */       area.setListener(this.m_listener);
/* 131 */       this.m_effectAreaList.add(area);
/* 132 */       this.m_activeEffectArea.put(Long.valueOf(area.getId()), area);
/* 133 */       if (this.m_listener != null) {
/* 134 */         this.m_listener.onEffectAreaAdded(area);
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
/*     */   public void removeEffectArea(BasicEffectArea area) {
/* 146 */     if (this.m_activeEffectArea.containsKey(Long.valueOf(area.getId()))) {
/* 147 */       area.die();
/* 148 */       this.m_activeEffectArea.remove(Long.valueOf(area.getId()));
/* 149 */       if (this.m_listener != null) {
/* 150 */         this.m_listener.onEffectAreaRemoved(area);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeEffectAreaOwnedByEffectUser(EffectUser owner) {
/* 156 */     List<BasicEffectArea> areaOwned = new ArrayList<BasicEffectArea>();
/* 157 */     for (BasicEffectArea area : this.m_activeEffectArea.values()) {
/* 158 */       if (area.getOwner() == owner) {
/* 159 */         areaOwned.add(area);
/*     */       }
/*     */     } 
/* 162 */     for (BasicEffectArea area : areaOwned) {
/* 163 */       removeEffectArea(area);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsActiveArea(BasicEffectArea area) {
/* 169 */     return this.m_activeEffectArea.containsKey(Long.valueOf(area.getId()));
/*     */   }
/*     */   
/*     */   public Iterator<BasicEffectArea> iterator() {
/* 173 */     return this.m_activeEffectArea.values().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroyAll() {
/* 181 */     for (BasicEffectArea area : this.m_effectAreaList) {
/* 182 */       area.release();
/*     */     }
/* 184 */     this.m_activeEffectArea.clear();
/* 185 */     this.m_effectAreaList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkInAndOut(Point3 start, Point3 arrival, EffectUser applicant) {
/* 191 */     ArrayList<BasicEffectArea> areaAlreadyIn = new ArrayList<BasicEffectArea>();
/*     */     
/* 193 */     for (BasicEffectArea area : this.m_activeEffectArea.values()) {
/* 194 */       if (area.contains(start)) {
/* 195 */         areaAlreadyIn.add(area);
/*     */       }
/*     */     } 
/* 198 */     ArrayList<BasicEffectArea> areaIn = new ArrayList<BasicEffectArea>();
/* 199 */     ArrayList<BasicEffectArea> areaOut = new ArrayList<BasicEffectArea>();
/* 200 */     for (BasicEffectArea area : this.m_activeEffectArea.values()) {
/* 201 */       if (area.contains(arrival)) {
/* 202 */         if (!areaAlreadyIn.contains(area))
/* 203 */           areaIn.add(area); 
/*     */         continue;
/*     */       } 
/* 206 */       if (areaAlreadyIn.contains(area)) {
/* 207 */         areaOut.add(area);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 212 */     for (BasicEffectArea area : areaIn) {
/* 213 */       area.triggers(1, (Target)applicant);
/*     */     }
/*     */     
/* 216 */     for (BasicEffectArea area : areaOut)
/* 217 */       area.triggers(2, (Target)applicant); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\effectArea\EffectAreaManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */