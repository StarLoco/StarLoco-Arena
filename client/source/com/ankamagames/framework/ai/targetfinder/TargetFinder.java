/*    */ package com.ankamagames.framework.ai.targetfinder;
/*    */ 
/*    */ import com.ankamagames.framework.ai.dataProvider.TargetInformationProvider;
/*    */ import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
/*    */ import com.ankamagames.framework.kernel.core.common.collections.EmptyIterable;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class TargetFinder
/*    */ {
/* 25 */   public static final TargetFinder m_instance = new TargetFinder();
/*    */   
/*    */   public static TargetFinder getInstance() {
/* 28 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 33 */   protected static final Logger m_logger = Logger.getLogger(TargetFinder.class);
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
/*    */   public <T extends Target> Iterable<T> getTargets(Target applicant, TargetInformationProvider<T> provider, AreaOfEffect area, Point3 searchCenter) {
/* 46 */     if (area == null || provider == null)
/* 47 */       return (Iterable<T>)new EmptyIterable(); 
/* 48 */     Point3 fromPos = (applicant != null) ? applicant.getPosition() : null;
/*    */     
/* 50 */     return area.getTargets(fromPos, searchCenter, provider.getPossibleTargets());
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends Target, TV extends TargetValidator> Iterable<T> getTargets(Target applicant, TargetInformationProvider<T> provider, AreaOfEffect area, Point3 searchCenter, TV filter) {
/* 69 */     if (filter == null) {
/* 70 */       m_logger.error("Impossible de rechercher une cible si aucun Target Validator n'est fourni");
/* 71 */       return getTargets(applicant, provider, area, searchCenter);
/*    */     } 
/*    */     
/* 74 */     if (area == null || provider == null) {
/* 75 */       return (Iterable<T>)new EmptyIterable();
/*    */     }
/* 77 */     List<T> targets = new LinkedList<T>();
/*    */     
/* 79 */     for (Iterator<T> it = provider.getPossibleTargets(); it.hasNext(); ) {
/* 80 */       Point3 applicantPosition; Target target = (Target)it.next();
/* 81 */       switch (filter.getTargetValidity(target, applicant)) {
/*    */         case VALID:
/* 83 */           targets.add((T)target);
/*    */         
/*    */         case VALID_IF_IN_AOE:
/* 86 */           applicantPosition = (applicant == null) ? null : applicant.getPosition();
/* 87 */           if (area.isPointInside(applicantPosition, searchCenter, target.getPosition())) {
/* 88 */             targets.add((T)target);
/*    */           }
/*    */       } 
/*    */ 
/*    */ 
/*    */     
/*    */     } 
/* 95 */     return targets;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\TargetFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */