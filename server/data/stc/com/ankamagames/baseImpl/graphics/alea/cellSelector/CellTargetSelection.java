/*     */ package com.ankamagames.baseImpl.graphics.alea.cellSelector;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import gnu.trove.TLongObjectHashMap;
/*     */ import gnu.trove.TLongObjectIterator;
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
/*     */ public class CellTargetSelection
/*     */ {
/*  21 */   private TLongObjectHashMap<List<Point3>> m_targets = new TLongObjectHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private long getHashcode(int x, int y)
/*     */   {
/*  29 */     long ux = x & 0xFFFFFFFF;
/*  30 */     long uy = y & 0xFFFFFFFF;
/*  31 */     return ux << 32 | uy;
/*     */   }
/*     */   
/*     */   public void addTarget(Point3 target) {
/*  35 */     boolean notFound = true;
/*     */     
/*  37 */     long hash = getHashcode(target.getX(), target.getY());
/*     */     
/*  39 */     List<Point3> list = (List)this.m_targets.get(hash);
/*     */     
/*  41 */     if (list == null) {
/*  42 */       list = new ArrayList();
/*  43 */       this.m_targets.put(hash, list);
/*     */     } else {
/*  45 */       notFound = findIn(target, list) < 0;
/*     */     }
/*     */     
/*  48 */     if (notFound) {
/*  49 */       list.add(target);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(Point3 target) {
/*  54 */     long hash = getHashcode(target.getX(), target.getY());
/*     */     
/*  56 */     List<Point3> list = (List)this.m_targets.get(hash);
/*     */     
/*  58 */     int index = findIn(target, list);
/*  59 */     if (index >= 0) {
/*  60 */       list.remove(index);
/*  61 */       if (list.size() == 0) {
/*  62 */         this.m_targets.remove(hash);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Point3> removeAllAt(int x, int y) {
/*  68 */     long hashIndex = getHashcode(x, y);
/*     */     
/*  70 */     List<Point3> list = (List)this.m_targets.get(hashIndex);
/*  71 */     this.m_targets.remove(hashIndex);
/*  72 */     return list;
/*     */   }
/*     */   
/*     */   public void removeAllAtAltitude(short z) {
/*  76 */     TLongObjectIterator<List<Point3>> iter = this.m_targets.iterator();
/*     */     
/*  78 */     int i = this.m_targets.size();
/*  79 */     do { iter.advance();
/*  80 */       List<Point3> points = (List)iter.value();
/*  81 */       int j = 0;
/*  82 */       while (j < points.size()) {
/*  83 */         if (((Point3)points.get(j)).getZ() == z) {
/*  84 */           points.remove(j);
/*     */         } else {
/*  86 */           j++;
/*     */         }
/*     */       }
/*  78 */       i--; } while (i >= 0);
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
/*     */   public boolean contains(Point3 target)
/*     */   {
/*  93 */     long hash = getHashcode(target.getX(), target.getY());
/*     */     
/*  95 */     List<Point3> list = (List)this.m_targets.get(hash);
/*     */     
/*     */ 
/*     */ 
/*  99 */     if (list != null) {
/* 100 */       return findIn(target, list) >= 0;
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 107 */     this.m_targets.clear();
/*     */   }
/*     */   
/*     */   public int count() {
/* 111 */     return this.m_targets.size();
/*     */   }
/*     */   
/*     */   private int findIn(Point3 target, List<Point3> search) {
/* 115 */     for (int i = 0; i < search.size(); i++) {
/* 116 */       if (((Point3)search.get(i)).getZ() == target.getZ()) {
/* 117 */         return i;
/*     */       }
/*     */     }
/* 120 */     return -1;
/*     */   }
/*     */   
/*     */   public List<Point3> getTargets(int x, int y) {
/* 124 */     long hash = getHashcode(x, y);
/* 125 */     return (List)this.m_targets.get(hash);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 129 */     StringBuilder s = new StringBuilder();
/* 130 */     TLongObjectIterator<List<Point3>> iter = this.m_targets.iterator();
/* 131 */     int i = this.m_targets.size();
/* 132 */     do { iter.advance();
/* 133 */       List<Point3> list = (List)iter.value();
/* 134 */       for (Point3 pt : list) {
/* 135 */         s.append(pt);
/*     */       }
/* 137 */       s.append("\n");i--;
/* 131 */     } while (i >= 0);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 139 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\cellSelector\CellTargetSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */