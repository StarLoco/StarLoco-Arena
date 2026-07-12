/*     */ package com.ankamagames.framework.ai.targetfinder.aoe;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
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
/*     */ public class TAOE
/*     */   extends AreaOfEffect
/*     */ {
/*     */   private int m_height;
/*     */   private int m_width;
/*  25 */   private List<int[]> m_patternList = (List)new ArrayList<int>(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<int[]> getPattern() {
/*  36 */     return this.m_patternList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize(int[] params) throws IllegalArgumentException {
/*  41 */     if (params == null)
/*  42 */       throw new IllegalArgumentException("Paramètres invalides pour une AOE de type T : 2 paramètres attendus, 0 trouvé"); 
/*  43 */     if (params.length != 2) {
/*  44 */       throw new IllegalArgumentException("Paramètres invalides pour une AOE de type T : 2 paramètres attendus, " + params.length + " trouvé(s)");
/*     */     }
/*  46 */     this.m_height = Math.abs(params[0]);
/*  47 */     this.m_width = Math.abs(params[1]);
/*     */     
/*  49 */     if (this.m_width != 0 && this.m_width % 2 == 0) {
/*  50 */       this.m_width++;
/*     */     }
/*  52 */     this.m_patternList.clear();
/*  53 */     for (int i = 0; i < this.m_height; i++) {
/*  54 */       this.m_patternList.add(new int[] { i });
/*  55 */     }  int midWidth = (this.m_width - 1) / 2;
/*  56 */     for (int j = 0; j < this.m_width; j++) {
/*  57 */       this.m_patternList.add(new int[] { this.m_height, j - midWidth });
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPointInside(Point3 effectSourcePosition, Point3 areaCenter, Point3 p) {
/*  62 */     if (p == null) {
/*  63 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  67 */     if (effectSourcePosition == null || effectSourcePosition == areaCenter) {
/*  68 */       return (p == areaCenter && (this.m_height != 0 || this.m_width != 0));
/*     */     }
/*     */     
/*  71 */     Direction8 dir = (new Vector3i(effectSourcePosition, areaCenter)).toDirection4();
/*  72 */     int[] vector = dir.getVector();
/*     */ 
/*     */     
/*  75 */     if (vector[0] == 0) {
/*     */       
/*  77 */       int dist = p.getY() - areaCenter.getY();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  84 */       int relativeDist = dist * vector[1];
/*     */ 
/*     */       
/*  87 */       if (relativeDist < 0) {
/*  88 */         return false;
/*     */       }
/*     */       
/*  91 */       if (relativeDist > this.m_height) {
/*  92 */         return false;
/*     */       }
/*     */       
/*  95 */       if (relativeDist < this.m_height) {
/*  96 */         return (p.getX() == areaCenter.getX());
/*     */       }
/*     */       
/*  99 */       return (this.m_width > Math.abs(p.getX() - areaCenter.getX()) * 2);
/*     */     } 
/*     */ 
/*     */     
/* 103 */     if (vector[1] == 0) {
/* 104 */       int dist = p.getX() - areaCenter.getX();
/* 105 */       int relativeDist = dist * vector[0];
/* 106 */       if (relativeDist < 0)
/* 107 */         return false; 
/* 108 */       if (relativeDist > this.m_height)
/* 109 */         return false; 
/* 110 */       if (relativeDist < this.m_height)
/* 111 */         return (p.getY() == areaCenter.getY()); 
/* 112 */       return (this.m_width > Math.abs(p.getY() - areaCenter.getY()) * 2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AreaOfEffectEnum getType() {
/* 123 */     return AreaOfEffectEnum.T;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\TAOE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */