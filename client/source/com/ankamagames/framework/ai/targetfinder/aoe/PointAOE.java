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
/*    */ public class PointAOE
/*    */   extends AreaOfEffect
/*    */ {
/* 20 */   private static final PointAOE m_staticInstance = new PointAOE();
/*    */   
/* 22 */   private List<int[]> m_patternList = (List)new ArrayList<int>(1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PointAOE getInstance() {
/* 29 */     return m_staticInstance;
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
/*    */   public List<int[]> getPattern() {
/* 41 */     return this.m_patternList;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initialize(int[] params) throws IllegalArgumentException {
/* 46 */     if (params != null && params.length > 0)
/* 47 */       throw new IllegalArgumentException("Paramètres invalides pour une AOE de type Point : 0 attendu, " + params.length + " fourni(s)"); 
/* 48 */     this.m_patternList.clear();
/* 49 */     this.m_patternList.add(new int[2]);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPointInside(Point3 effectSourcePosition, Point3 areaCenter, Point3 p) {
/* 54 */     return (p != null && p.equalsIgnoringAltitude(areaCenter));
/*    */   }
/*    */   
/*    */   public AreaOfEffectEnum getType() {
/* 58 */     return AreaOfEffectEnum.POINT;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\PointAOE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */