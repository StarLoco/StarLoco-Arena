/*     */ package com.ankamagames.framework.ai.pathfinder;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.ArrayIterator;
/*     */ import com.ankamagames.framework.kernel.core.common.collections.iterators.EmptyIterator;
/*     */ import java.util.Iterator;
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
/*     */ public class PathFindResult
/*     */   implements Iterable<int[]>
/*     */ {
/*  19 */   public static int STEP_X = 0;
/*  20 */   public static int STEP_Y = 1;
/*  21 */   public static int STEP_Z = 2;
/*     */ 
/*     */   
/*     */   private boolean m_pathFound;
/*     */   
/*     */   private int[][] m_datas;
/*     */ 
/*     */   
/*     */   PathFindResult() {
/*  30 */     this.m_pathFound = false;
/*     */   }
/*     */   
/*     */   public PathFindResult(int stepsCount) {
/*  34 */     this.m_datas = new int[stepsCount][3];
/*  35 */     this.m_pathFound = true;
/*     */   }
/*     */   
/*     */   public void setStep(int stepIndex, int x, int y, short z) {
/*  39 */     this.m_datas[stepIndex][STEP_X] = x;
/*  40 */     this.m_datas[stepIndex][STEP_Y] = y;
/*  41 */     this.m_datas[stepIndex][STEP_Z] = z;
/*     */   }
/*     */   
/*     */   public void setStep(int stepIndex, int[] step) {
/*  45 */     this.m_datas[stepIndex] = step;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPathLength() {
/*  56 */     if (!this.m_pathFound || this.m_datas == null)
/*  57 */       return 0; 
/*  58 */     return this.m_datas.length;
/*     */   }
/*     */   
/*     */   public boolean isPathFound() {
/*  62 */     return this.m_pathFound;
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
/*     */   public int[] getPathStep(int stepIndex) {
/*  78 */     return this.m_datas[stepIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getFirstStep() {
/*  84 */     if (this.m_datas != null && this.m_datas.length != 0) {
/*  85 */       return this.m_datas[0];
/*     */     }
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getLastStep() {
/*  94 */     if (this.m_datas != null && this.m_datas.length != 0) {
/*  95 */       return this.m_datas[this.m_datas.length - 1];
/*     */     }
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<int[]> iterator() {
/* 108 */     if (this.m_datas == null)
/* 109 */       return (Iterator<int[]>)new EmptyIterator(); 
/* 110 */     return (Iterator<int[]>)new ArrayIterator((Object[])this.m_datas, true);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFindResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */