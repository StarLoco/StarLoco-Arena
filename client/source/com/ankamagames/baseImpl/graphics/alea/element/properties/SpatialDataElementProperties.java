/*     */ package com.ankamagames.baseImpl.graphics.alea.element.properties;
/*     */ 
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class SpatialDataElementProperties
/*     */   extends BasicElementProperties
/*     */ {
/*     */   protected int m_weight;
/*     */   protected boolean m_uniqueWeightInLevel;
/*     */   protected int m_height;
/*     */   protected boolean m_virtualHeight;
/*     */   protected byte m_slope;
/*     */   protected boolean m_lineOfSight1;
/*     */   protected boolean m_lineOfSight3;
/*     */   protected boolean m_lineOfSight5;
/*     */   protected boolean m_lineOfSight7;
/*     */   protected boolean m_lineOfSightTop;
/*     */   protected boolean m_lineOfSightBottom;
/*     */   protected boolean m_move1;
/*     */   protected boolean m_move3;
/*     */   protected boolean m_move5;
/*     */   protected boolean m_move7;
/*     */   protected boolean m_moveTop;
/*     */   protected boolean m_moveBottom;
/*     */   protected boolean m_walkable;
/*     */   protected boolean m_mobileFront;
/*     */   protected boolean m_piled;
/*     */   
/*     */   public SpatialDataElementProperties(ByteBuffer buffer) {
/*  46 */     read(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SpatialDataElementProperties() {}
/*     */ 
/*     */   
/*     */   public int getWeight() {
/*  55 */     return this.m_weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeight(int weight) {
/*  63 */     this.m_weight = weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUniqueWeightInLevel() {
/*  70 */     return this.m_uniqueWeightInLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUniqueWeightInLevel(boolean uniqueWeightInLevel) {
/*  78 */     this.m_uniqueWeightInLevel = uniqueWeightInLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/*  84 */     if (this.m_slope != 0) {
/*  85 */       return this.m_height * 0.5F;
/*     */     }
/*  87 */     return this.m_height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getVisualHeight() {
/*  94 */     return this.m_height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/* 101 */     this.m_height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSight1() {
/* 108 */     return this.m_lineOfSight1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineOfSight1(boolean lineOfSight1) {
/* 115 */     this.m_lineOfSight1 = lineOfSight1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSight3() {
/* 121 */     return this.m_lineOfSight3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineOfSight3(boolean lineOfSight3) {
/* 128 */     this.m_lineOfSight3 = lineOfSight3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSight5() {
/* 135 */     return this.m_lineOfSight5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineOfSight5(boolean lineOfSight5) {
/* 142 */     this.m_lineOfSight5 = lineOfSight5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSight7() {
/* 148 */     return this.m_lineOfSight7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineOfSight7(boolean lineOfSight7) {
/* 155 */     this.m_lineOfSight7 = lineOfSight7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSightTop() {
/* 161 */     return this.m_lineOfSightTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineOfSightTop(boolean lineOfSightTop) {
/* 168 */     this.m_lineOfSightTop = lineOfSightTop;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSightBottom() {
/* 174 */     return this.m_lineOfSightBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineOfSightBottom(boolean lineOfSightBottom) {
/* 181 */     this.m_lineOfSightBottom = lineOfSightBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMobileOnTop() {
/* 189 */     return this.m_mobileFront;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMobileOnTop(boolean mobileOnTop) {
/* 196 */     this.m_mobileFront = mobileOnTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMove1() {
/* 203 */     return this.m_move1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMove1(boolean move1) {
/* 210 */     this.m_move1 = move1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMove3() {
/* 217 */     return this.m_move3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMove3(boolean move3) {
/* 224 */     this.m_move3 = move3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMove5() {
/* 231 */     return this.m_move5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMove5(boolean move5) {
/* 238 */     this.m_move5 = move5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMove7() {
/* 245 */     return this.m_move7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMove7(boolean move7) {
/* 252 */     this.m_move7 = move7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMoveTop() {
/* 259 */     return this.m_moveTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMoveTop(boolean moveTop) {
/* 266 */     this.m_moveTop = moveTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMoveBottom() {
/* 273 */     return this.m_moveBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMoveBottom(boolean moveBottom) {
/* 280 */     this.m_move7 = moveBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPiled() {
/* 287 */     return this.m_piled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPiled(boolean piled) {
/* 294 */     this.m_piled = piled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSlope() {
/* 301 */     return this.m_slope;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlope(byte slope) {
/* 308 */     this.m_slope = slope;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVirtualHeight() {
/* 315 */     return this.m_virtualHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlat() {
/* 322 */     return (this.m_height == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVirtualHeight(boolean virtualHeight) {
/* 330 */     this.m_virtualHeight = virtualHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWalkable() {
/* 337 */     return this.m_walkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWalkable(boolean walkable) {
/* 344 */     this.m_walkable = walkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(ByteBuffer buffer) {
/* 352 */     super.read(buffer);
/*     */     
/* 354 */     this.m_weight = buffer.getShort();
/* 355 */     this.m_uniqueWeightInLevel = (buffer.get() == 1);
/*     */     
/* 357 */     this.m_piled = (buffer.get() == 1);
/* 358 */     this.m_mobileFront = (buffer.get() == 1);
/*     */     
/* 360 */     this.m_height = buffer.get();
/* 361 */     this.m_virtualHeight = (buffer.get() == 1);
/* 362 */     this.m_slope = buffer.get();
/*     */     
/* 364 */     this.m_lineOfSight1 = (buffer.get() == 1);
/* 365 */     this.m_lineOfSight3 = (buffer.get() == 1);
/* 366 */     this.m_lineOfSight5 = (buffer.get() == 1);
/* 367 */     this.m_lineOfSight7 = (buffer.get() == 1);
/* 368 */     this.m_lineOfSightTop = (buffer.get() == 1);
/* 369 */     this.m_lineOfSightBottom = (buffer.get() == 1);
/*     */     
/* 371 */     this.m_move1 = (buffer.get() == 1);
/* 372 */     this.m_move3 = (buffer.get() == 1);
/* 373 */     this.m_move5 = (buffer.get() == 1);
/* 374 */     this.m_move7 = (buffer.get() == 1);
/* 375 */     this.m_moveTop = (buffer.get() == 1);
/* 376 */     this.m_moveBottom = (buffer.get() == 1);
/* 377 */     this.m_walkable = (buffer.get() == 1);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\properties\SpatialDataElementProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */