/*     */ package com.ankamagames.framework.graphics.animation.instances;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Frame
/*     */   implements Poolable
/*     */ {
/*  21 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<Frame>() {
/*     */         public Frame makeObject() {
/*  23 */           return new Frame(null);
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean USE_POOL = false;
/*     */   
/*  31 */   private TIntObjectHashMap<DisplayObject> m_datas = new TIntObjectHashMap();
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
/*     */   public boolean containsObjectAt(int depth) {
/*  47 */     return this.m_datas.containsKey(depth);
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
/*     */   public DisplayObject getDisplayObject(int depth) {
/*  59 */     return (DisplayObject)this.m_datas.get(depth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  69 */     List<DisplayObject> alreadyReleased = new ArrayList<DisplayObject>();
/*     */     
/*  71 */     TIntObjectIterator<DisplayObject> iterator = this.m_datas.iterator();
/*     */     
/*  73 */     for (int i = this.m_datas.size(); --i >= 0; ) {
/*  74 */       iterator.advance();
/*  75 */       DisplayObject displayObject = (DisplayObject)iterator.value();
/*  76 */       if (!alreadyReleased.contains(displayObject)) {
/*  77 */         displayObject.release();
/*  78 */         alreadyReleased.add(displayObject);
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
/*     */   public void put(int depth, DisplayObject displayObject) {
/* 101 */     this.m_datas.put(depth, displayObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDepths() {
/* 110 */     return this.m_datas.keys();
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
/*     */   
/*     */   public static Frame getNewFrame() {
/* 126 */     Frame frame = new Frame();
/*     */     
/* 128 */     return frame;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/* 134 */     this.m_datas.clear();
/*     */   }
/*     */   
/*     */   public void onCheckOut() {}
/*     */   
/*     */   private Frame() {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\instances\Frame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */