/*    */ package com.ankamagames.framework.ai.targetfinder.aoe;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CrossAOE
/*    */   extends AreaOfEffect
/*    */ {
/*    */   private int m_size;
/* 22 */   private List<int[]> m_patternList = (List)new ArrayList<int>(1);
/*    */ 
/*    */   
/*    */   public void initialize(int[] params) throws IllegalArgumentException {
/* 26 */     if (params == null || params.length != 1) {
/* 27 */       if (params == null || params.length == 0) {
/* 28 */         throw new IllegalArgumentException("Paramètres invalides pour une AOE de type cross : 1 paramètre attendu, 0 trouvé(s)");
/*    */       }
/* 30 */       throw new IllegalArgumentException("Paramètres invalides pour une AOE de type cross : 1 paramètre attendu, " + params.length + " trouvé(s)");
/*    */     } 
/*    */     
/* 33 */     this.m_size = params[0];
/*    */     
/* 35 */     this.m_patternList.clear();
/* 36 */     for (int i = -this.m_size; i <= this.m_size; i++) {
/* 37 */       if (i == 0) {
/* 38 */         this.m_patternList.add(new int[2]);
/*    */       } else {
/* 40 */         this.m_patternList.add(new int[] { i });
/* 41 */         this.m_patternList.add(new int[] { 0, i });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<int[]> getPattern() {
/* 56 */     return this.m_patternList;
/*    */   }
/*    */   
/*    */   public boolean isPointInside(Point3 effectSourcePosition, Point3 areaCenter, Point3 p) {
/* 60 */     if (p == null)
/* 61 */       return false; 
/* 62 */     if (p.getX() == areaCenter.getX())
/* 63 */       return (Math.abs(p.getY() - areaCenter.getY()) <= this.m_size); 
/* 64 */     if (p.getY() == areaCenter.getY()) {
/* 65 */       return (Math.abs(p.getX() - areaCenter.getX()) <= this.m_size);
/*    */     }
/* 67 */     return false;
/*    */   }
/*    */   
/*    */   public AreaOfEffectEnum getType() {
/* 71 */     return AreaOfEffectEnum.CROSS;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\CrossAOE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */