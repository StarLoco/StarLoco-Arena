/*    */ package com.ankamagames.framework.ai.targetfinder.aoe;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.collections.EmptyIterable;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ public class EmptyAOE
/*    */   extends AreaOfEffect
/*    */ {
/*    */   private int m_radius;
/*    */   private double m_squaredRadius;
/* 25 */   private static final List<int[]> m_emptyList = (List)new ArrayList<int>(0);
/*    */   
/*    */   public void initialize(int[] params) throws IllegalArgumentException {}
/*    */   
/*    */   public boolean isPointInside(Point3 effectSourcePosition, Point3 areaCenter, Point3 p) {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */   public AreaOfEffectEnum getType() {
/* 34 */     return AreaOfEffectEnum.EMPTY;
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
/* 46 */     return m_emptyList;
/*    */   }
/*    */   
/*    */   public <T extends com.ankamagames.framework.ai.targetfinder.Target> Iterable<T> getTargets(Point3 effectSourcePosition, Point3 areaCenter, Iterator<T> possibleTargets) {
/* 50 */     return (Iterable<T>)new EmptyIterable();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\EmptyAOE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */