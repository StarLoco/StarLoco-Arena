/*    */ package com.ankamagames.framework.ai.pathfinder;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class PathFindNode
/*    */   implements Poolable, Comparable<PathFindNode>
/*    */ {
/*    */   short m_z;
/*    */   double m_f;
/*    */   double m_h;
/*    */   double m_g;
/*    */   long m_d;
/*    */   PathFindCell m_cell;
/*    */   boolean m_bClosed;
/*    */   PathFindNode m_parent;
/* 35 */   Direction8 m_direction = Direction8.NONE;
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckOut() {
/* 40 */     this.m_bClosed = false;
/* 41 */     this.m_parent = null;
/* 42 */     this.m_d = 0L;
/* 43 */     this.m_g = 0.0D;
/* 44 */     this.m_z = 0;
/* 45 */     this.m_h = 0.0D;
/* 46 */     this.m_f = 0.0D;
/* 47 */     this.m_direction = Direction8.NONE;
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 51 */     this.m_parent = null;
/*    */   }
/*    */   
/*    */   public int compareTo(PathFindNode o) {
/* 55 */     return (this.m_f == o.m_f) ? 0 : ((this.m_f < o.m_f) ? -1 : 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClosed() {
/* 60 */     return this.m_bClosed;
/*    */   }
/*    */   
/*    */   public void setClosed(boolean bClosed) {
/* 64 */     this.m_bClosed = bClosed;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 68 */     if (this == o) return true; 
/* 69 */     if (o == null || getClass() != o.getClass()) return false; 
/* 70 */     PathFindNode that = (PathFindNode)o;
/* 71 */     return (this.m_cell == that.m_cell);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFindNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */