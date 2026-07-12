/*     */ package com.ankamagames.baseImpl.graphics.alea;
/*     */ 
/*     */ import com.ankamagames.alea.AleaWorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.struct.space.Partition;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class WorldCell
/*     */   implements AleaWorldCell
/*     */ {
/*     */   private int m_worldX;
/*     */   private int m_worldY;
/*  37 */   private ArrayList<GraphicalWorldElement> m_visualElements = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*  41 */   private ArrayList<WorldElement> m_customElements = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCell(int worldX, int worldY)
/*     */   {
/*  50 */     this.m_worldX = worldX;
/*  51 */     this.m_worldY = worldY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setWorldX(int worldX)
/*     */   {
/*  58 */     this.m_worldX = worldX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setWorldY(int worldY)
/*     */   {
/*  65 */     this.m_worldY = worldY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addVisualElement(GraphicalWorldElement element)
/*     */   {
/*  75 */     if (this.m_visualElements.size() >= 512)
/*  76 */       System.err.println("Coordonnées de cellules dépasse la capacité d'un int");
/*  77 */     long handle = getHandle() | this.m_visualElements.size();
/*  78 */     element.SetHandle(handle);
/*  79 */     this.m_visualElements.add(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getHandle()
/*     */   {
/*  86 */     long ux = this.m_worldX & 0xFFFFFFF;
/*  87 */     long uy = this.m_worldY & 0xFFFFFFF;
/*     */     
/*  89 */     if ((ux >= 536870912L) || (uy >= 536870912L))
/*  90 */       System.err.println("Coordonnées de cellules dépasse la capacité d'un int");
/*  91 */     return ux << 36 | uy << 8;
/*     */   }
/*     */   
/*     */   public ArrayList<GraphicalWorldElement> getVisualElements() {
/*  95 */     return this.m_visualElements;
/*     */   }
/*     */   
/*     */   public void addCustomElement(WorldElement element)
/*     */   {
/* 100 */     this.m_customElements.add(element);
/*     */   }
/*     */   
/*     */   public ArrayList<WorldElement> getCustomElement() {
/* 104 */     return this.m_customElements;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldElement getHighestElement()
/*     */   {
/* 112 */     int altitude = Integer.MIN_VALUE;
/* 113 */     WorldElement highest = null;
/*     */     
/* 115 */     for (int i = this.m_visualElements.size() - 1; i >= 0; i--) {
/* 116 */       WorldElement elt = (WorldElement)this.m_visualElements.get(i);
/* 117 */       if (elt.getCoordinates().getZ() > altitude) {
/* 118 */         altitude = elt.getCoordinates().getZ();
/* 119 */         highest = elt;
/*     */       }
/*     */     }
/* 122 */     return highest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<WorldElement> getElementsAtTop()
/*     */   {
/* 133 */     int altitude = Integer.MIN_VALUE;
/* 134 */     int lastLevel = -1;
/* 135 */     List<WorldElement> elements = new ArrayList();
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
/* 152 */     WorldElement element = getHighestElement();
/* 153 */     if (element != null) {
/* 154 */       elements.add(element);
/*     */     }
/*     */     
/* 157 */     return elements;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Partition getPartitionFromPoint(float x, float y, float z)
/*     */   {
/* 168 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAllPartitions() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPartition(Partition subPartition) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removePartition(Partition subPartition) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/* 201 */     return this.m_worldX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getY()
/*     */   {
/* 210 */     return this.m_worldY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLineOfSightValid(short height, Direction8 direction)
/*     */   {
/* 220 */     if (this.m_visualElements == null) {
/* 221 */       return true;
/*     */     }
/* 223 */     for (GraphicalWorldElement element : getVisualElements())
/*     */     {
/* 225 */       if ((element.getHeight() > 0.0D) && (element.getAltitude() <= height) && (element.getCoordinates().getZ() > height)) {
/* 226 */         GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/*     */         
/* 228 */         switch (direction) {
/*     */         case EAST: 
/* 230 */           if (!properties.isLineOfSight1())
/* 231 */             return false;
/*     */           break;
/*     */         case NORTH: 
/* 234 */           if (!properties.isLineOfSight3())
/* 235 */             return false;
/*     */           break;
/*     */         case NORTH_WEST: 
/* 238 */           if (!properties.isLineOfSight5())
/* 239 */             return false;
/*     */           break;
/*     */         case SOUTH_EAST: 
/* 242 */           if (!properties.isLineOfSight7())
/* 243 */             return false;
/*     */           break;
/*     */         case SOUTH_WEST: 
/* 246 */           if (!properties.isLineOfSightTop())
/* 247 */             return false;
/*     */           break;
/*     */         case TOP: 
/* 250 */           if (!properties.isLineOfSightBottom())
/* 251 */             return false;
/*     */           break;
/*     */         case SOUTH: 
/* 254 */           if ((!properties.isLineOfSight5()) || (!properties.isLineOfSight7()))
/* 255 */             return false;
/*     */           break;
/*     */         case NONE: 
/* 258 */           if ((!properties.isLineOfSight1()) || (!properties.isLineOfSight3()))
/* 259 */             return false;
/*     */           break;
/*     */         case BOTTOM: 
/* 262 */           if ((!properties.isLineOfSight1()) || (!properties.isLineOfSight7()))
/* 263 */             return false;
/*     */           break;
/*     */         case NORTH_EAST: 
/* 266 */           if ((!properties.isLineOfSight3()) || (!properties.isLineOfSight5())) {
/* 267 */             return false;
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 273 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLineOfSightEndValid(short height)
/*     */   {
/* 281 */     if (this.m_visualElements == null) {
/* 282 */       return false;
/*     */     }
/* 284 */     List<GraphicalWorldElement> visualElement = getVisualElements();
/* 285 */     for (int i = visualElement.size() - 1; i >= 0; i--) {
/* 286 */       GraphicalWorldElement element = (GraphicalWorldElement)visualElement.get(i);
/* 287 */       if (element.getCoordinates().getZ() == height) {
/* 288 */         GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/* 289 */         if (properties.isWalkable()) break;
/* 290 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 295 */     return true;
/*     */   }
/*     */   
/*     */   public short getMaximumAltitude()
/*     */   {
/* 300 */     if ((this.m_visualElements != null) && (this.m_visualElements.size() > 0)) {
/* 301 */       GraphicalWorldElement element = (GraphicalWorldElement)this.m_visualElements.get(this.m_visualElements.size() - 1);
/* 302 */       return (short)(int)(element.getCoordinates().getZ() + element.getHeight());
/*     */     }
/* 304 */     return Short.MIN_VALUE;
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
/*     */   public short getArrivalAltitude(PathFindMover mover, short z, Direction8 inputDir, PathFindParameters params)
/*     */   {
/* 317 */     short candidateHeight = 0;
/* 318 */     int nextMinimumAltitude = Integer.MIN_VALUE;
/* 319 */     boolean foundValidCandidate = false;
/*     */     
/* 321 */     for (GraphicalWorldElement element : getVisualElements())
/*     */     {
/* 323 */       GraphicalElement graphicalElement = element.getElement();
/* 324 */       int topAltitude = element.getCoordinates().getZ();
/*     */       
/* 326 */       boolean sideBlocking = false;
/*     */       
/* 328 */       switch (inputDir) {
/*     */       case SOUTH_EAST: 
/* 330 */         if (!graphicalElement.getStateProperties(element.getState()).isMove3())
/* 331 */           sideBlocking = true;
/* 332 */         break;
/*     */       case NORTH_WEST: 
/* 334 */         if (!graphicalElement.getStateProperties(element.getState()).isMove1())
/* 335 */           sideBlocking = true;
/* 336 */         break;
/*     */       case EAST: 
/* 338 */         if (!graphicalElement.getStateProperties(element.getState()).isMove5())
/* 339 */           sideBlocking = true;
/* 340 */         break;
/*     */       case NORTH: 
/* 342 */         if (!graphicalElement.getStateProperties(element.getState()).isMove7())
/* 343 */           sideBlocking = true;
/* 344 */         break;
/*     */       case NONE: 
/* 346 */         if ((!graphicalElement.getStateProperties(element.getState()).isMove5()) || (!graphicalElement.getStateProperties(element.getState()).isMove7()))
/* 347 */           sideBlocking = true;
/* 348 */         break;
/*     */       case SOUTH: 
/* 350 */         if ((!graphicalElement.getStateProperties(element.getState()).isMove3()) || (!graphicalElement.getStateProperties(element.getState()).isMove1()))
/* 351 */           sideBlocking = true;
/* 352 */         break;
/*     */       case BOTTOM: 
/* 354 */         if ((!graphicalElement.getStateProperties(element.getState()).isMove3()) || (!graphicalElement.getStateProperties(element.getState()).isMove5()))
/* 355 */           sideBlocking = true;
/* 356 */         break;
/*     */       case NORTH_EAST: 
/* 358 */         if ((!graphicalElement.getStateProperties(element.getState()).isMove1()) || (!graphicalElement.getStateProperties(element.getState()).isMove7())) {
/* 359 */           sideBlocking = true;
/*     */         }
/*     */         break;
/*     */       }
/*     */       
/* 364 */       if ((foundValidCandidate) && (
/* 365 */         ((graphicalElement.getStateProperties(element.getState()).isMoveBottom()) && 
/* 366 */         (!sideBlocking)) || (
/* 367 */         ((element.getAltitude() >= candidateHeight) && (element.getAltitude() - candidateHeight < mover.getHeight())) || (
/*     */         
/* 369 */         ((!graphicalElement.getStateProperties(element.getState()).isMoveTop()) || 
/* 370 */         (sideBlocking)) && 
/* 371 */         (element.getAltitude() + element.getHeight() >= candidateHeight) && (element.getAltitude() + element.getHeight() - candidateHeight < mover.getHeight()))))) {
/* 372 */         foundValidCandidate = false;
/*     */       }
/*     */       
/*     */ 
/* 376 */       if (!foundValidCandidate)
/*     */       {
/* 378 */         if ((!graphicalElement.getStateProperties(element.getState()).isMoveTop()) && (graphicalElement.getStateProperties(element.getState()).isWalkable()) && 
/* 379 */           (topAltitude >= nextMinimumAltitude)) { short maxZMovement;
/*     */           short maxZMovement;
/* 381 */           if (topAltitude < z) {
/* 382 */             maxZMovement = mover.getJumpMaxDescendingHeight();
/*     */           } else {
/* 384 */             maxZMovement = mover.getJumpMaxAscendingHeight();
/*     */           }
/* 386 */           boolean limitHeightWithJumpCapacity = true;
/* 387 */           if (params != null) { limitHeightWithJumpCapacity = params.m_limitHeightWithJumpCapacity;
/*     */           }
/*     */           
/* 390 */           if ((Math.abs(topAltitude - z) <= maxZMovement) || (!limitHeightWithJumpCapacity)) {
/* 391 */             foundValidCandidate = true;
/* 392 */             candidateHeight = (short)topAltitude;
/* 393 */             continue;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 399 */         if ((!graphicalElement.getStateProperties(element.getState()).isMoveBottom()) && 
/* 400 */           (!sideBlocking) && (element.getAltitude() >= nextMinimumAltitude)) { short maxZMovement;
/*     */           short maxZMovement;
/* 402 */           if (element.getAltitude() < z) {
/* 403 */             maxZMovement = mover.getJumpMaxDescendingHeight();
/*     */           } else {
/* 405 */             maxZMovement = mover.getJumpMaxAscendingHeight();
/*     */           }
/* 407 */           if (Math.abs(element.getAltitude()) - z <= maxZMovement) {
/* 408 */             foundValidCandidate = true;
/* 409 */             candidateHeight = element.getAltitude();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 415 */       if (sideBlocking) {
/* 416 */         nextMinimumAltitude = topAltitude;
/*     */       }
/*     */     }
/* 419 */     if (!foundValidCandidate) {
/* 420 */       return Short.MIN_VALUE;
/*     */     }
/*     */     
/* 423 */     return candidateHeight;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isWalkable(short z)
/*     */   {
/* 429 */     if ((this.m_visualElements != null) && (this.m_visualElements.size() > 0)) {
/* 430 */       for (GraphicalWorldElement element : getVisualElements()) {
/* 431 */         if (element.getAltitude() == z) {
/* 432 */           return element.getElement().getStateProperties(element.getState()).isWalkable();
/*     */         }
/*     */       }
/*     */     }
/* 436 */     return false;
/*     */   }
/*     */   
/*     */   public boolean getMovementValidity(PathFindMover mover, short z, Direction8 direction)
/*     */   {
/* 441 */     if (this.m_visualElements == null) {
/* 442 */       return false;
/*     */     }
/* 444 */     for (GraphicalWorldElement element : getVisualElements())
/*     */     {
/* 446 */       if ((element.getHeight() > 0.0D) && (element.getAltitude() >= z) && (element.getAltitude() + element.getHeight() <= z + mover.getHeight())) {
/* 447 */         GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/*     */         
/* 449 */         switch (direction) {
/*     */         case EAST: 
/* 451 */           if (!properties.isMove1())
/* 452 */             return false;
/*     */           break;
/*     */         case NORTH: 
/* 455 */           if (!properties.isMove3())
/* 456 */             return false;
/*     */           break;
/*     */         case NORTH_WEST: 
/* 459 */           if (!properties.isMove5())
/* 460 */             return false;
/*     */           break;
/*     */         case SOUTH_EAST: 
/* 463 */           if (!properties.isMove7())
/* 464 */             return false;
/*     */           break;
/*     */         case SOUTH_WEST: 
/* 467 */           if (!properties.isMoveTop())
/* 468 */             return false;
/*     */           break;
/*     */         case TOP: 
/* 471 */           if (!properties.isMoveBottom()) {
/* 472 */             return false;
/*     */           }
/*     */           break;
/*     */         case SOUTH: 
/* 476 */           if ((!properties.isMove5()) || (!properties.isMove7()))
/* 477 */             return false;
/*     */           break;
/*     */         case NONE: 
/* 480 */           if ((!properties.isMove1()) || (!properties.isMove3()))
/* 481 */             return false;
/*     */           break;
/*     */         case BOTTOM: 
/* 484 */           if ((!properties.isMove1()) || (!properties.isMove7()))
/* 485 */             return false;
/*     */           break;
/*     */         case NORTH_EAST: 
/* 488 */           if ((!properties.isMove3()) || (!properties.isMove5())) {
/* 489 */             return false;
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 495 */     return true;
/*     */   }
/*     */   
/*     */   public boolean getMovementAcrossValidity(PathFindMover mover, short incomingZ, Direction8 incomingDir, short outgoingZ, Direction8 outgoingDir, PathFindParameters params)
/*     */   {
/* 500 */     if (this.m_visualElements == null) {
/* 501 */       return true;
/*     */     }
/* 503 */     for (GraphicalWorldElement element : getVisualElements())
/*     */     {
/* 505 */       GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/*     */       
/* 507 */       if ((element.getHeight() > 0.0D) && (element.getAltitude() >= incomingZ)) {
/* 508 */         switch (incomingDir) {
/*     */         case EAST: 
/* 510 */           if (!properties.isMove1())
/* 511 */             return false;
/*     */           break;
/*     */         case NORTH: 
/* 514 */           if (!properties.isMove3())
/* 515 */             return false;
/*     */           break;
/*     */         case NORTH_WEST: 
/* 518 */           if (!properties.isMove5())
/* 519 */             return false;
/*     */           break;
/*     */         case SOUTH_EAST: 
/* 522 */           if (!properties.isMove7())
/* 523 */             return false;
/*     */           break;
/*     */         }
/*     */       }
/* 527 */       if ((element.getHeight() > 0.0D) && (element.getAltitude() >= outgoingZ)) {
/* 528 */         switch (outgoingDir) {
/*     */         case EAST: 
/* 530 */           if (!properties.isMove5())
/* 531 */             return false;
/*     */           break;
/*     */         case NORTH: 
/* 534 */           if (!properties.isMove7())
/* 535 */             return false;
/*     */           break;
/*     */         case NORTH_WEST: 
/* 538 */           if (!properties.isMove1())
/* 539 */             return false;
/*     */           break;
/*     */         case SOUTH_EAST: 
/* 542 */           if (!properties.isMove3())
/* 543 */             return false;
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 548 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldElement getHighestElement(int levelId)
/*     */   {
/* 556 */     double hh = -1.7976931348623157E308D;
/* 557 */     GraphicalWorldElement e = null;
/* 558 */     if (this.m_visualElements != null) {
/* 559 */       for (GraphicalWorldElement element : this.m_visualElements) {
/* 560 */         if ((element.getLevel() == levelId) && (element.getWeight() >= hh)) {
/* 561 */           hh = element.getWeight() + element.getHeight();
/* 562 */           e = element;
/*     */         }
/*     */       }
/*     */     }
/* 566 */     return e;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getHighestNotEmptyLevel()
/*     */   {
/* 575 */     int level = 0;
/*     */     
/* 577 */     for (GraphicalWorldElement element : this.m_visualElements) {
/* 578 */       if (element.getLevel() > level) {
/* 579 */         level = element.getLevel();
/*     */       }
/*     */     }
/* 582 */     return level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldElement getElementWithTopAtAltitude(short altitude)
/*     */   {
/* 593 */     for (int i = this.m_visualElements.size() - 1; i >= 0; i--) {
/* 594 */       WorldElement element = (WorldElement)this.m_visualElements.get(i);
/* 595 */       if (element.getCoordinates().getZ() == altitude)
/* 596 */         return element;
/*     */     }
/* 598 */     return null;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 603 */     return 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 610 */       "{WorldCell : (" + this.m_worldX + ", " + this.m_worldY + ") @" + Integer.toHexString(hashCode()) + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */