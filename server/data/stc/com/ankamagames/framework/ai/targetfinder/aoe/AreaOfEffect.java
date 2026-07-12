/*    */ package com.ankamagames.framework.ai.targetfinder.aoe;
/*    */ 
/*    */ import com.ankamagames.framework.ai.targetfinder.Target;
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
/*    */ public abstract class AreaOfEffect
/*    */ {
/*    */   public abstract void initialize(int[] paramArrayOfInt)
/*    */     throws IllegalArgumentException;
/*    */   
/*    */   public <T extends Target> Iterable<T> getTargets(Point3 effectSourcePosition, Point3 areaCenter, Iterator<T> possibleTargets)
/*    */   {
/* 25 */     List<T> targets = new ArrayList();
/* 26 */     while (possibleTargets.hasNext()) {
/* 27 */       T target = (Target)possibleTargets.next();
/* 28 */       if (isPointInside(effectSourcePosition, areaCenter, target.getPosition()))
/* 29 */         targets.add(target);
/*    */     }
/* 31 */     return targets;
/*    */   }
/*    */   
/*    */   public abstract boolean isPointInside(Point3 paramPoint31, Point3 paramPoint32, Point3 paramPoint33);
/*    */   
/*    */   public abstract AreaOfEffectEnum getType();
/*    */   
/*    */   public abstract List<int[]> getPattern();
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\AreaOfEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */