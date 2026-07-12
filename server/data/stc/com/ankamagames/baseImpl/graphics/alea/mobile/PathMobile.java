/*     */ package com.ankamagames.baseImpl.graphics.alea.mobile;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.MovementStyleManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle.PathMovementStyle;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFinder;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class PathMobile
/*     */   extends Mobile
/*     */   implements PathFindMover, StyleMobile
/*     */ {
/*  28 */   public static final int[][] EIGHT_DIRECTION_SHIFT = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 0, -1 }, { -1 }, { 0, 1 }, { 1 }, { 1, 1 } };
/*  29 */   public static final int[][] FOUR_DIRECTION_SHIFT = { { -1 }, { 0, -1 }, { 1 }, { 0, 1 } };
/*     */   
/*  31 */   private static int TIME_BETWEEN_POSITION_UPDATE = 35;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final short DEFAULT_MOBILE_JUMP_HEIGHT = 4;
/*     */   
/*     */ 
/*     */ 
/*  40 */   public int[][] m_directionShift = EIGHT_DIRECTION_SHIFT;
/*     */   
/*  42 */   private short m_jumpHeight = 4;
/*     */   
/*     */   private int m_currentPathStepIndex;
/*     */   
/*     */   private long m_lastTime;
/*     */   
/*     */   private long m_timeRest;
/*  49 */   private PathMovementStyle m_movementStyle = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private PathFindResult m_currentPath;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PathMobile(long id)
/*     */   {
/*  63 */     super(id);
/*  64 */     setMovementStyle(MovementStyleManager.WALK_STYLE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PathMobile(long id, double worldX, double worldY, double altitude)
/*     */   {
/*  76 */     super(id, worldX, worldY, altitude);
/*  77 */     setMovementStyle(MovementStyleManager.WALK_STYLE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PathMobile(long id, double worldX, double worldY)
/*     */   {
/*  88 */     super(id, worldX, worldY);
/*  89 */     setMovementStyle(MovementStyleManager.WALK_STYLE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(AleaWorldScene scene, long realTime, int frameCount)
/*     */   {
/* 100 */     long timeSinceLastMovement = this.m_timeRest + realTime - this.m_lastTime;
/*     */     
/* 102 */     if (timeSinceLastMovement >= TIME_BETWEEN_POSITION_UPDATE) {
/* 103 */       boolean newPathCell = false;
/*     */       
/*     */ 
/* 106 */       if (this.m_currentPath != null)
/*     */       {
/* 108 */         while (this.m_currentPath.getPathLength() > this.m_currentPathStepIndex)
/*     */         {
/* 110 */           int[] currentStep = this.m_currentPath.getPathStep(this.m_currentPathStepIndex);
/*     */           
/*     */ 
/*     */ 
/* 114 */           if (this.m_currentPath.getPathLength() <= this.m_currentPathStepIndex + 1)
/*     */           {
/* 116 */             this.m_worldX = currentStep[PathFindResult.STEP_X];
/* 117 */             this.m_worldY = currentStep[PathFindResult.STEP_Y];
/* 118 */             this.m_altitude = currentStep[PathFindResult.STEP_Z];
/*     */             
/* 120 */             if (getCarriedMobile() != null) {
/* 121 */               getCarriedMobile().setWorldPosition(this.m_worldX, this.m_worldY, this.m_altitude + getHeight());
/*     */             }
/*     */             
/* 124 */             this.m_movementStyle.onStandingOnLastCell();
/*     */             
/* 126 */             if (timeSinceLastMovement >= this.m_movementStyle.getCellSpeed()) {
/* 127 */               timeSinceLastMovement -= this.m_movementStyle.getCellSpeed();
/* 128 */               this.m_currentPathStepIndex += 1;
/*     */             }
/*     */             
/*     */           }
/*     */           else
/*     */           {
/* 134 */             int[] nextStep = this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1);
/* 135 */             int dx = nextStep[PathFindResult.STEP_X] - currentStep[PathFindResult.STEP_X];
/* 136 */             int dy = nextStep[PathFindResult.STEP_Y] - currentStep[PathFindResult.STEP_Y];
/* 137 */             int dz = nextStep[PathFindResult.STEP_Z] - currentStep[PathFindResult.STEP_Z];
/*     */             
/* 139 */             double distance = Math.sqrt(Math.pow(dx, 2.0D) + Math.pow(dy, 2.0D));
/*     */             
/* 141 */             if (timeSinceLastMovement >= this.m_movementStyle.getCellSpeed() * distance) {
/* 142 */               timeSinceLastMovement = (timeSinceLastMovement - this.m_movementStyle.getCellSpeed() * distance);
/* 143 */               this.m_currentPathStepIndex += 1;
/* 144 */               newPathCell = true;
/* 145 */               continue;
/*     */             }
/*     */             
/* 148 */             double cellPositionPercent = (float)timeSinceLastMovement / (this.m_movementStyle.getCellSpeed() * distance);
/*     */             
/* 150 */             this.m_worldX = (currentStep[PathFindResult.STEP_X] + cellPositionPercent * dx);
/* 151 */             this.m_worldY = (currentStep[PathFindResult.STEP_Y] + cellPositionPercent * dy);
/* 152 */             this.m_altitude = (currentStep[PathFindResult.STEP_Z] + cellPositionPercent * dz);
/*     */             
/* 154 */             if (getCarriedMobile() != null) {
/* 155 */               getCarriedMobile().setWorldPosition(this.m_worldX, this.m_worldY, this.m_altitude + getHeight());
/*     */             }
/*     */             
/*     */ 
/* 159 */             if (this.m_movementStyle.isAirImpulsionNeeded(dz))
/*     */             {
/*     */               double airImpulsion;
/*     */               double airImpulsion;
/* 163 */               if (cellPositionPercent > 0.5D) {
/* 164 */                 airImpulsion = 1.0D - cellPositionPercent;
/*     */               } else {
/* 166 */                 airImpulsion = cellPositionPercent;
/*     */               }
/*     */               
/* 169 */               this.m_movementStyle.onMovingOnAir(cellPositionPercent);
/*     */               
/* 171 */               if (newPathCell) {
/* 172 */                 forceReloadAnimation();
/*     */               }
/* 174 */               this.m_altitude += airImpulsion * this.m_movementStyle.getAirImpulsion();
/*     */             }
/*     */             else {
/* 177 */               int remainPathLength = this.m_currentPath.getPathLength() - this.m_currentPathStepIndex;
/* 178 */               this.m_movementStyle.onMovingOnGround(remainPathLength);
/*     */             }
/*     */             
/* 181 */             this.m_movementStyle.onDirectionChanged(Vector3i.getDirection8FromVector(dx, dy));
/*     */           }
/*     */           
/* 184 */           this.m_timeRest = timeSinceLastMovement;
/*     */           break label595; }
/* 186 */         this.m_currentPath = null;
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 191 */         this.m_movementStyle.onWaiting();
/* 192 */         this.m_timeRest = 0L;
/*     */       }
/*     */       label595:
/* 195 */       this.m_lastTime = realTime;
/*     */     }
/*     */     
/* 198 */     super.process(scene, realTime, frameCount);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getJumpMaxAscendingHeight()
/*     */   {
/* 209 */     return this.m_jumpHeight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getJumpMaxDescendingHeight()
/*     */   {
/* 218 */     return this.m_jumpHeight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[][] getDirectionShift()
/*     */   {
/* 226 */     return this.m_directionShift;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDirectionShift(int[][] directionShift)
/*     */   {
/* 233 */     this.m_directionShift = directionShift;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getJumpHeight()
/*     */   {
/* 240 */     return this.m_jumpHeight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setJumpHeight(short jumpHeight)
/*     */   {
/* 247 */     this.m_jumpHeight = jumpHeight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMovementStyle(String movementStyleKey)
/*     */   {
/* 255 */     PathMovementStyle movementStyle = MovementStyleManager.getInstance().getMovementStyle(movementStyleKey);
/*     */     
/* 257 */     if (movementStyle == null) {
/* 258 */       m_logger.error("Le style : " + movementStyle + " n'existe pas.");
/* 259 */       return;
/*     */     }
/*     */     
/* 262 */     if (this.m_movementStyle != null) {
/* 263 */       this.m_movementStyle.setMobile(null);
/*     */     }
/* 265 */     movementStyle.setMobile(this);
/* 266 */     this.m_movementStyle = movementStyle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public PathMovementStyle getMovementStyle()
/*     */   {
/* 273 */     return this.m_movementStyle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPath(PathFindResult node, boolean recompute)
/*     */   {
/* 284 */     if (node.getPathLength() < 2) {
/* 285 */       return;
/*     */     }
/* 287 */     if (recompute)
/*     */     {
/*     */ 
/* 290 */       if (this.m_currentPath != null)
/*     */       {
/*     */ 
/* 293 */         if (this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 2)
/*     */         {
/* 295 */           int[] currentNode = this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1);
/*     */           
/*     */ 
/* 298 */           int[] lastStep = node.getLastStep();
/* 299 */           if (lastStep != null)
/*     */           {
/* 301 */             PathFindParameters defaultParameters = new PathFindParameters();
/* 302 */             defaultParameters.m_searchLimit = 1000;
/*     */             
/* 304 */             Point3 from = new Point3(currentNode[PathFindResult.STEP_X], currentNode[PathFindResult.STEP_Y], (short)currentNode[PathFindResult.STEP_Z]);
/* 305 */             Point3 to = new Point3(lastStep[PathFindResult.STEP_X], lastStep[PathFindResult.STEP_Y], (short)lastStep[PathFindResult.STEP_Z]);
/*     */             
/*     */ 
/*     */ 
/* 309 */             PathFinder pathFinder = PathFinder.checkOut();
/* 310 */             PathFindResult result = pathFinder.compute(this, WorldManager.getInstance(), from, to, defaultParameters);
/*     */             
/* 312 */             if (result.isPathFound())
/*     */             {
/* 314 */               int mixPathLength = result.getPathLength() + 1;
/* 315 */               PathFindResult mixPath = new PathFindResult(mixPathLength);
/*     */               
/* 317 */               mixPath.setStep(0, this.m_currentPath.getPathStep(this.m_currentPathStepIndex));
/*     */               
/* 319 */               for (int i = 0; i < result.getPathLength(); i++) {
/* 320 */                 mixPath.setStep(i + 1, result.getPathStep(i));
/*     */               }
/*     */               
/* 323 */               this.m_currentPath = mixPath;
/* 324 */               this.m_currentPathStepIndex = 0;
/* 325 */               return;
/*     */             }
/* 327 */             pathFinder.release();
/*     */           }
/*     */         } else {
/* 330 */           if (this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 1)
/*     */           {
/* 332 */             int[] currentNode = this.m_currentPath.getPathStep(this.m_currentPathStepIndex);
/* 333 */             int[] newFirstNode = node.getPathStep(0);
/*     */             
/*     */ 
/* 336 */             if ((currentNode[0] == newFirstNode[0]) && (currentNode[1] == newFirstNode[1]))
/*     */             {
/* 338 */               int mixPathLength = node.getPathLength() + 1;
/* 339 */               PathFindResult mixPath = new PathFindResult(mixPathLength);
/*     */               
/* 341 */               mixPath.setStep(0, this.m_currentPath.getPathStep(this.m_currentPathStepIndex));
/* 342 */               mixPath.setStep(1, this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1));
/*     */               
/* 344 */               for (int i = 1; i < node.getPathLength(); i++) {
/* 345 */                 mixPath.setStep(i + 1, node.getPathStep(i));
/*     */               }
/* 347 */               this.m_currentPath = mixPath;
/* 348 */               this.m_currentPathStepIndex = 0;
/* 349 */               return;
/*     */             }
/*     */             
/*     */ 
/* 353 */             int mixPathLength = node.getPathLength() + 1;
/* 354 */             PathFindResult mixPath = new PathFindResult(mixPathLength);
/*     */             
/* 356 */             mixPath.setStep(0, this.m_currentPath.getPathStep(this.m_currentPathStepIndex));
/*     */             
/* 358 */             for (int i = 0; i < node.getPathLength(); i++) {
/* 359 */               mixPath.setStep(i + 1, node.getPathStep(i));
/*     */             }
/*     */             
/* 362 */             this.m_currentPath = mixPath;
/* 363 */             this.m_currentPathStepIndex = 0;
/*     */             
/* 365 */             return;
/*     */           }
/*     */           
/*     */ 
/* 369 */           this.m_timeRest = 0L;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 374 */     this.m_currentPath = node;
/* 375 */     this.m_currentPathStepIndex = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDestinationWorldX()
/*     */   {
/* 384 */     if ((this.m_currentPath != null) && (this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 1)) {
/* 385 */       return this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1)[0];
/*     */     }
/* 387 */     return (int)this.m_worldX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDestinationWorldY()
/*     */   {
/* 396 */     if ((this.m_currentPath != null) && (this.m_currentPath.getPathLength() > this.m_currentPathStepIndex + 1)) {
/* 397 */       return this.m_currentPath.getPathStep(this.m_currentPathStepIndex + 1)[1];
/*     */     }
/* 399 */     return (int)this.m_worldY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWorldPosition(double worldX, double worldY, double altitude)
/*     */   {
/* 411 */     if (getMovementStyle().createPathOnSetPosition()) {
/* 412 */       PathFindResult path = new PathFindResult(2);
/* 413 */       path.setStep(0, (int)this.m_worldX, (int)this.m_worldY, (short)(int)this.m_altitude);
/* 414 */       path.setStep(1, (int)worldX, (int)worldY, (short)(int)altitude);
/*     */       
/* 416 */       setPath(path, true);
/* 417 */       return;
/*     */     }
/*     */     
/* 420 */     super.setWorldPosition(worldX, worldY, altitude);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\PathMobile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */