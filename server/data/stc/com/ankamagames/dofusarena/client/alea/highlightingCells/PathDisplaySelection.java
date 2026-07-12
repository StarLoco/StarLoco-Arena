/*    */ package com.ankamagames.dofusarena.client.alea.highlightingCells;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathDisplaySelection
/*    */   extends ElementSelection
/*    */ {
/*    */   private static final String LAYER_NAME = "pathDisplayer";
/*    */   
/*    */   public PathDisplaySelection()
/*    */   {
/* 22 */     super("pathDisplayer", DofusArenaClientConstants.PATH_COLOR);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setPath(PathFindResult path)
/*    */   {
/* 29 */     clear();
/*    */     
/* 31 */     int numCells = path.getPathLength();
/* 32 */     for (int i = 0; i < numCells; i++) {
/* 33 */       int[] step = path.getPathStep(i);
/* 34 */       Point3 p = new Point3(step[0], step[1], (short)step[2]);
/* 35 */       add(p);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\highlightingCells\PathDisplaySelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */