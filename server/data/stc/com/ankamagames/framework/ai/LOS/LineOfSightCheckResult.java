/*    */ package com.ankamagames.framework.ai.LOS;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
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
/*    */ public class LineOfSightCheckResult
/*    */ {
/*    */   boolean m_lineOfSightOk;
/*    */   List<LineOfSightUtils.CellInput> m_cellInputs;
/*    */   List<Vector3i> m_checkedCells;
/*    */   List<Point3> m_blockingCells;
/*    */   
/*    */   public boolean isLineOfSightOk()
/*    */   {
/* 27 */     return this.m_lineOfSightOk;
/*    */   }
/*    */   
/*    */   public List<LineOfSightUtils.CellInput> getCellInputs() {
/* 31 */     return this.m_cellInputs;
/*    */   }
/*    */   
/*    */   public List<Vector3i> getCheckedCells() {
/* 35 */     return this.m_checkedCells;
/*    */   }
/*    */   
/*    */   public List<Point3> getBlockingCells() {
/* 39 */     return this.m_blockingCells;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\LOS\LineOfSightCheckResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */