/*     */ package com.ankamagames.framework.ai.pathfinder;
/*     */ 
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.dataProvider.MovementObstacleInformationProvider;
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
/*     */ public class PathFinder
/*     */   implements Poolable
/*     */ {
/*     */   protected static final double MOVEMENT_UNIT = 1.0D;
/*     */   protected static final double REAL_DIAGONAL_VALUE = 1.4142D;
/*  32 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/*  33 */     public PathFinder makeObject() { return new PathFinder(); }
/*  32 */   });
/*     */   
/*     */ 
/*     */ 
/*  36 */   private static final PathFindNodePool m_nodesPool = new PathFindNodePool();
/*     */   
/*     */ 
/*  39 */   private final PriorityQueue<PathFindNode> m_openNodesQueue = new PriorityQueue();
/*     */   
/*  41 */   private final HashMap<PathFindCell, TShortObjectHashMap<PathFindNode>> m_nodes = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PathFinder checkOut()
/*     */   {
/*     */     try
/*     */     {
/*  55 */       return (PathFinder)m_staticPool.borrowObject();
/*     */     } catch (Exception e) {
/*  57 */       e.printStackTrace();
/*     */     }
/*  59 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void release()
/*     */   {
/*     */     try
/*     */     {
/*  67 */       m_staticPool.returnObject(this);
/*     */     } catch (Exception e) {
/*  69 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public final PathFindResult compute(PathFindMover mover, CellInformationProvider cells, Point3 from, Point3 to, PathFindParameters parameters)
/*     */   {
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
/*  90 */     if ((!parameters.m_stopJustBeforeEndCell) && (parameters.m_obstacleInformationProvider != null)) {
/*  91 */       MovementObstacle obstacle = parameters.m_obstacleInformationProvider.getMovementObstacle(to.getX(), to.getY(), to.getZ());
/*  92 */       if ((obstacle != null) && (obstacle.getMovementObstruction() == 0.0F)) {
/*  93 */         return new PathFindResult();
/*     */       }
/*     */     }
/*  96 */     PathFindNode nodeTo = getPathFindNode(cellTo, to.getZ());
/*  97 */     PathFindNode nodeFrom = getPathFindNode(cellFrom, from.getZ());
/*  98 */     nodeFrom.m_d = 0L;
/*  99 */     nodeFrom.m_h = (nodeFrom.m_f = estimateDistance(nodeFrom, nodeTo, parameters));
/*     */     
/*     */ 
/* 102 */     openNodes.add(nodeFrom);
/*     */     
/* 104 */     int testedNodesCount = 0;
/*     */     
/* 106 */     while (!openNodes.isEmpty())
/*     */     {
/*     */ 
/* 109 */       if (parameters.m_searchLimit > 0) { testedNodesCount++; if (testedNodesCount >= parameters.m_searchLimit) {
/*     */           break;
/*     */         }
/*     */       }
/* 113 */       PathFindNode node = (PathFindNode)openNodes.remove();
/* 114 */       PathFindCell currentCell = node.m_cell;
/*     */       
/*     */ 
/* 117 */       if (node == nodeTo) {
/* 118 */         PathFindResult result = computeResult(nodeTo, parameters);
/* 119 */         releaseNodes();
/* 120 */         return result;
/*     */       }
/*     */       
/*     */ 
/* 124 */       if ((parameters.m_maxNodes > 0) && (parameters.m_maxNodes <= node.m_d)) {
/* 125 */         releaseNodes();
/* 126 */         return new PathFindResult();
/*     */       }
/*     */       
/*     */       Direction8[] arrayOfDirection81;
/* 130 */       int j = (arrayOfDirection81 = directions).length; for (int i = 0; i < j; i++) { Direction8 direction = arrayOfDirection81[i];
/*     */         
/*     */ 
/* 133 */         int[] directionVector = direction.getVector();
/*     */         
/*     */ 
/* 136 */         PathFindCell nextCell = cells.getPathFindCell(currentCell.getX() + directionVector[0], currentCell.getY() + directionVector[1], node.m_z);
/* 137 */         if (nextCell != null)
/*     */         {
/*     */ 
/*     */ 
/* 141 */           if ((node.m_parent == null) || (node.m_parent.m_cell != nextCell))
/*     */           {
/*     */ 
/*     */ 
/* 145 */             if (currentCell.getMovementValidity(mover, node.m_z, direction))
/*     */             {
/*     */ 
/*     */ 
/* 149 */               short nextZ = nextCell.getArrivalAltitude(mover, node.m_z, direction, parameters);
/* 150 */               if (nextZ == Short.MIN_VALUE) {
/* 151 */                 nextCell.getArrivalAltitude(mover, node.m_z, direction, parameters);
/*     */ 
/*     */ 
/*     */ 
/*     */               }
/* 156 */               else if (direction.isDiagonal())
/*     */               {
/* 158 */                 Direction8 verticalDirection = direction.getVerticalDirection();
/* 159 */                 Direction8 horizontalDirection = direction.getHorizontalDirection();
/*     */                 
/*     */ 
/* 162 */                 PathFindCell c = cells.getPathFindCell(currentCell.getX() + directionVector[0], currentCell.getY(), node.m_z);
/* 163 */                 if (c.getMovementAcrossValidity(mover, node.m_z, horizontalDirection.opposite(), nextZ, verticalDirection, parameters))
/*     */                 {
/*     */ 
/* 166 */                   c = cells.getPathFindCell(currentCell.getX(), currentCell.getY() + directionVector[1], node.m_z);
/* 167 */                   if (!c.getMovementAcrossValidity(mover, node.m_z, horizontalDirection, nextZ, verticalDirection.opposite(), parameters)) {}
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 172 */                 float obstacleObstruction = 1.0F;
/* 173 */                 if (parameters.m_obstacleInformationProvider != null)
/*     */                 {
/* 175 */                   if (!to.equals(nextCell.getX(), nextCell.getY())) {
/* 176 */                     MovementObstacle obstacle = parameters.m_obstacleInformationProvider.getMovementObstacle(nextCell.getX(), nextCell.getY(), nextZ);
/* 177 */                     if (obstacle != null) {
/* 178 */                       obstacleObstruction = obstacle.getMovementObstruction();
/*     */                     }
/*     */                   }
/*     */                 }
/* 182 */                 if (obstacleObstruction != 0.0F)
/*     */                 {
/*     */ 
/*     */ 
/* 186 */                   double nextG = node.m_g + obstacleObstruction * calculateNodeWeight(node, nextCell, nextZ, direction, mover, parameters);
/*     */                   
/*     */ 
/* 189 */                   PathFindNode nextNode = getPathFindNode(nextCell, nextZ);
/*     */                   
/*     */ 
/* 192 */                   boolean bInClosed = nextNode.isClosed();
/* 193 */                   if ((!bInClosed) || (nextNode.m_g > nextG))
/*     */                   {
/* 195 */                     boolean bInOpen = openNodes.contains(nextNode);
/* 196 */                     if ((!bInOpen) || (nextNode.m_g > nextG))
/*     */                     {
/*     */ 
/*     */ 
/* 200 */                       nextNode.m_parent = node;
/* 201 */                       nextNode.m_direction = direction;
/* 202 */                       node.m_d += 1L;
/* 203 */                       nextNode.m_g = nextG;
/* 204 */                       if (!bInClosed)
/* 205 */                         nextNode.m_h = estimateDistance(nextNode, nodeTo, parameters);
/* 206 */                       nextNode.m_f = (nextNode.m_g + nextNode.m_h);
/* 207 */                       nextNode.setClosed(false);
/* 208 */                       if (!bInOpen)
/* 209 */                         openNodes.add(nextNode);
/*     */                     }
/*     */                   } } } } } } }
/* 212 */       node.setClosed(true);
/*     */     }
/* 214 */     releaseNodes();
/* 215 */     return new PathFindResult();
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
/*     */   protected double calculateNodeWeight(PathFindNode nodeFrom, PathFindCell cellTo, short zTo, Direction8 direction, PathFindMover mover, PathFindParameters params)
/*     */   {
/* 231 */     double weight = 1.0D;
/*     */     
/* 233 */     if ((params.m_punishDirectionChange) && 
/* 234 */       (nodeFrom.m_direction != direction)) {
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
/*     */   private double estimateDistance(PathFindNode from, PathFindNode to, PathFindParameters params)
/*     */   {
/* 253 */     if (!params.m_useDiagonals) {
/* 254 */       return (Math.abs(from.m_cell.getX() - to.m_cell.getX()) + Math.abs(from.m_cell.getY() - to.m_cell.getY())) * 1.0D;
/*     */     }
/*     */     
/* 257 */     if (params.m_shortDiagonals)
/*     */     {
/*     */ 
/* 260 */       long diffX = Math.abs(from.m_cell.getX() - to.m_cell.getX());
/* 261 */       long diffY = Math.abs(from.m_cell.getY() - to.m_cell.getY());
/* 262 */       return (Math.min(diffX, diffY) + Math.abs(diffX - diffY)) * 1.0D;
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
/*     */   private void releaseNodes()
/*     */   {
/* 278 */     for (TShortObjectHashMap<PathFindNode> map : this.m_nodes.values()) {
/* 279 */       for (TShortObjectIterator<PathFindNode> iterator = map.iterator(); iterator.hasNext();) {
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
/*     */   private PathFindNode getPathFindNode(PathFindCell cell, short z)
/*     */   {
/* 297 */     TShortObjectHashMap<PathFindNode> map = (TShortObjectHashMap)this.m_nodes.get(cell);
/*     */     PathFindNode node;
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
/*     */   private PathFindResult computeResult(PathFindNode node, PathFindParameters params)
/*     */   {
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
/* 339 */     for (int i = steps - 1; (i >= 0) && (node != null); i--) {
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */