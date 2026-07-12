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
/*    */ public class CircleAOE
/*    */   extends AreaOfEffect
/*    */ {
/*    */   private double m_squaredRadius;
/* 22 */   private List<int[]> m_patternList = new ArrayList(1);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<int[]> getPattern()
/*    */   {
/* 33 */     return this.m_patternList;
/*    */   }
/*    */   
/*    */   public void initialize(int[] params) throws IllegalArgumentException
/*    */   {
/* 38 */     if ((params == null) || (params.length != 1)) {
/* 39 */       if ((params == null) || (params.length == 0)) {
/* 40 */         throw new IllegalArgumentException("Paramètres invalides pour une AOE de type cercle : 1 paramètre attendu, 0 trouvé(s)");
/*    */       }
/* 42 */       throw new IllegalArgumentException("Paramètres invalides pour une AOE de type cercle : 1 paramètre attendu, " + params.length + " trouvé(s)");
/*    */     }
/* 44 */     int radius = params[0];
/* 45 */     this.m_squaredRadius = Math.pow(radius, 2.0D);
/* 46 */     this.m_patternList.clear();
/* 47 */     for (int x = -radius; x <= radius; x++) {
/* 48 */       int yBounds = radius - Math.abs(x);
/* 49 */       for (int y = -yBounds; y <= yBounds; y++) {
/* 50 */         this.m_patternList.add(new int[] { x, y });
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isPointInside(Point3 effectSourcePosition, Point3 areaCenter, Point3 p) {
/* 56 */     if (p == null)
/* 57 */       return false;
/* 58 */     double squaredDistance = Math.pow(p.getX() - areaCenter.getX(), 2.0D) + Math.pow(p.getY() - areaCenter.getY(), 2.0D);
/* 59 */     return squaredDistance <= this.m_squaredRadius;
/*    */   }
/*    */   
/*    */   public AreaOfEffectEnum getType() {
/* 63 */     return AreaOfEffectEnum.CIRCLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\CircleAOE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */