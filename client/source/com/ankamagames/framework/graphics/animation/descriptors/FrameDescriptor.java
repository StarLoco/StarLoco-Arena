/*     */ package com.ankamagames.framework.graphics.animation.descriptors;
/*     */ 
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class FrameDescriptor
/*     */ {
/*     */   public static final int DURATION_INFINITE = -1;
/*  30 */   private TIntObjectHashMap<FrameDataDescriptor> m_datas = new TIntObjectHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> m_actions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FrameDataDescriptor getDataAt(int depth) {
/*  41 */     return (FrameDataDescriptor)this.m_datas.get(depth);
/*     */   }
/*     */   
/*     */   public boolean containsDataAt(int depth) {
/*  45 */     return this.m_datas.containsKey(depth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAt(int depth, FrameDataDescriptor data) {
/*  56 */     this.m_datas.put(depth, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FrameDataDescriptor removeAt(int depth) {
/*  67 */     return (FrameDataDescriptor)this.m_datas.remove(depth);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getActions() {
/*  72 */     return this.m_actions;
/*     */   }
/*     */   
/*     */   public void addAction(String action) {
/*  76 */     if (this.m_actions == null)
/*  77 */       this.m_actions = new ArrayList<String>(); 
/*  78 */     this.m_actions.add(action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(FrameDescriptor frameDescriptor) {
/*  88 */     this.m_datas.clear();
/*  89 */     TIntObjectHashMap<FrameDataDescriptor> oldDatas = frameDescriptor.m_datas;
/*     */     
/*  91 */     TIntObjectIterator<FrameDataDescriptor> iterator = oldDatas.iterator();
/*     */     
/*  93 */     for (int i = oldDatas.size(); --i >= 0; ) {
/*  94 */       iterator.advance();
/*  95 */       this.m_datas.put(iterator.key(), ((FrameDataDescriptor)iterator.value()).duplicate());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntObjectIterator<FrameDataDescriptor> iterator() {
/* 105 */     return this.m_datas.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 113 */     if (this.m_actions != null) {
/* 114 */       this.m_actions.clear();
/* 115 */       this.m_actions = null;
/*     */     } 
/*     */     
/* 118 */     TIntObjectIterator<FrameDataDescriptor> iterator = this.m_datas.iterator();
/* 119 */     for (int i = this.m_datas.size(); --i >= 0; ) {
/* 120 */       iterator.advance();
/* 121 */       ((FrameDataDescriptor)iterator.value()).dispose();
/*     */     } 
/* 123 */     this.m_datas.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 128 */     return this.m_datas.size();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\FrameDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */