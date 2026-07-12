/*    */ package com.ankamagames.framework.ai.pathfinder;
/*    */ 
/*    */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
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
/*    */ public class PathFindParameters
/*    */ {
/* 17 */   public static double DIRECTION_PUNISHEMENT_WEIGHT = 0.1D;
/*    */   
/*    */ 
/* 20 */   public boolean m_useDiagonals = true;
/*    */   
/*    */ 
/* 23 */   public boolean m_shortDiagonals = false;
/*    */   
/*    */ 
/* 26 */   public boolean m_punishDirectionChange = true;
/*    */   
/*    */ 
/*    */ 
/* 30 */   public int m_searchLimit = 0;
/*    */   
/*    */ 
/* 33 */   public boolean m_includeStartCell = true;
/*    */   
/*    */ 
/* 36 */   public int m_maxNodes = 0;
/*    */   
/*    */ 
/* 39 */   public boolean m_limitHeightWithJumpCapacity = true;
/*    */   
/*    */ 
/*    */ 
/* 43 */   public MovementObstacleInformationProvider m_obstacleInformationProvider = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 48 */   public boolean m_stopJustBeforeEndCell = false;
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFindParameters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */