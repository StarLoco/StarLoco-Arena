/*     */ package com.ankamagames.framework.ai.pathfinder;
/*     */ 
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import gnu.trove.TShortObjectHashMap;
/*     */ import gnu.trove.TShortObjectIterator;
/*     */ import java.util.HashMap;
/*     */ import java.util.PriorityQueue;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathFinder
/*     */   implements Poolable
/*     */ {
/*     */   protected static final double MOVEMENT_UNIT = 1.0D;
/*     */   protected static final double REAL_DIAGONAL_VALUE = 1.4142D;
/*     */   
/*  32 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<PathFinder>() { public PathFinder makeObject() {
/*  33 */           return new PathFinder();
/*     */         } }
/*     */     );
/*  36 */   private static final PathFindNodePool m_nodesPool = new PathFindNodePool();
/*     */ 
/*     */   
/*  39 */   private final PriorityQueue<PathFindNode> m_openNodesQueue = new PriorityQueue<PathFindNode>();
/*     */   
/*  41 */   private final HashMap<PathFindCell, TShortObjectHashMap<PathFindNode>> m_nodes = new HashMap<PathFindCell, TShortObjectHashMap<PathFindNode>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PathFinder checkOut() {
/*     */     try {
/*  55 */       return (PathFinder)m_staticPool.borrowObject();
/*  56 */     } catch (Exception e) {
/*  57 */       e.printStackTrace();
/*     */       
/*  59 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*     */     try {
/*  67 */       m_staticPool.returnObject(this);
/*  68 */     } catch (Exception e) {
/*  69 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final PathFindResult compute(PathFindMover mover, CellInformationProvider cells, Point3 from, Point3 to, PathFindParameters parameters) {
/*  76 */     PriorityQueue<PathFindNode> openNodes = this.m_openNodesQueue;
/*     */     
/*  78 */     Direction8[] directions = parameters.m_useDiagonals ? Direction8.getDirection8Values() : Direction8.getDirection4Values();
/*     */     
/*  80 */     PathFindCell cellFrom = cells.getPathFindCell(from.getX(), from.getY(), from.getZ());
/*  81 */     if (cellFrom == null) {
/*  82 */       return new PathFindResult();
/*     */     }
/*  84 */     PathFindCell cellTo = cells.getPathFindCell(to.getX(), to.getY(), to.getZ());
/*  85 */     if (cellTo == null) {
/*  86 */       return new PathFindResult();
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (!parameters.m_stopJustBeforeEndCell && parameters.m_obstacleInformationProvider != null) {
/*  91 */       MovementObstacle obstacle = parameters.m_obstacleInformationProvider.getMovementObstacle(to.getX(), to.getY(), to.getZ());
/*  92 */       if (obstacle != null && obstacle.getMovementObstruction() == 0.0F) {
/*  93 */         return new PathFindResult();
/*     */       }
/*     */     } 
/*  96 */     PathFindNode nodeTo = getPathFindNode(cellTo, to.getZ());
/*  97 */     PathFindNode nodeFrom = getPathFindNode(cellFrom, from.getZ());
/*  98 */     nodeFrom.m_d = 0L;
/*  99 */     nodeFrom.m_h = nodeFrom.m_f = estimateDistance(nodeFrom, nodeTo, parameters);
/*     */ 
/*     */     
/* 102 */     openNodes.add(nodeFrom);
/*     */     
/* 104 */     int testedNodesCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateNodeWeight(PathFindNode nodeFrom, PathFindCell cellTo, short zTo, Direction8 direction, PathFindMover mover, PathFindParameters params) {
/* 231 */     double weight = 1.0D;
/*     */     
/* 233 */     if (params.m_punishDirectionChange && 
/* 234 */       nodeFrom.m_direction != direction) {
/* 235 */       weight += PathFindParameters.DIRECTION_PUNISHEMENT_WEIGHT;
/*     */     }
/*     */     
/* 238 */     return weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double estimateDistance(PathFindNode from, PathFindNode to, PathFindParameters params) {
/* 253 */     if (!params.m_useDiagonals) {
/* 254 */       return (Math.abs(from.m_cell.getX() - to.m_cell.getX()) + Math.abs(from.m_cell.getY() - to.m_cell.getY())) * 1.0D;
/*     */     }
/*     */     
/* 257 */     if (params.m_shortDiagonals) {
/*     */ 
/*     */       
/* 260 */       long l1 = Math.abs(from.m_cell.getX() - to.m_cell.getX());
/* 261 */       long l2 = Math.abs(from.m_cell.getY() - to.m_cell.getY());
/* 262 */       return (Math.min(l1, l2) + Math.abs(l1 - l2)) * 1.0D;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     long diffX = Math.abs(from.m_cell.getX() - to.m_cell.getX());
/* 269 */     long diffY = Math.abs(from.m_cell.getY() - to.m_cell.getY());
/* 270 */     return Math.min(diffX, diffY) * 1.4142D + Math.abs(diffX - diffY) * 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void releaseNodes() {
/* 278 */     for (TShortObjectHashMap<PathFindNode> map : this.m_nodes.values()) {
/* 279 */       for (TShortObjectIterator<PathFindNode> iterator = map.iterator(); iterator.hasNext(); ) {
/* 280 */         iterator.advance();
/* 281 */         m_nodesPool.release((PathFindNode)iterator.value());
/*     */       } 
/* 283 */       map.clear();
/*     */     } 
/* 285 */     this.m_nodes.clear();
/* 286 */     this.m_openNodesQueue.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathFindNode getPathFindNode(PathFindCell cell, short z) {
/* 297 */     TShortObjectHashMap<PathFindNode> map = this.m_nodes.get(cell);
/*     */     
/* 299 */     if (map == null) {
/* 300 */       map = new TShortObjectHashMap();
/* 301 */       this.m_nodes.put(cell, map);
/* 302 */       node = null;
/*     */     } else {
/* 304 */       node = (PathFindNode)map.get(z);
/*     */     } 
/*     */     
/* 307 */     if (node != null) {
/* 308 */       return node;
/*     */     }
/* 310 */     PathFindNode node = m_nodesPool.checkOut(cell, z);
/*     */     
/* 312 */     map.put(z, node);
/* 313 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathFindResult computeResult(PathFindNode node, PathFindParameters params) {
/* 323 */     int steps = 0;
/*     */ 
/*     */     
/* 326 */     for (PathFindNode n = node; n != null; n = n.m_parent) {
/* 327 */       steps++;
/*     */     }
/*     */     
/* 330 */     if (!params.m_includeStartCell) {
/* 331 */       steps--;
/*     */     }
/* 333 */     if (params.m_stopJustBeforeEndCell) {
/* 334 */       node = node.m_parent;
/* 335 */       steps--;
/*     */     } 
/*     */     
/* 338 */     PathFindResult result = new PathFindResult(steps);
/* 339 */     for (int i = steps - 1; i >= 0 && node != null; i--) {
/* 340 */       result.setStep(i, node.m_cell.getX(), node.m_cell.getY(), node.m_z);
/* 341 */       node = node.m_parent;
/*     */     } 
/* 343 */     return result;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {}
/*     */   
/*     */   public void onCheckIn() {
/* 349 */     releaseNodes();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */