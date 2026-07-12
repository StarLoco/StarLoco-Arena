/*     */ package com.ankamagames.graphics.isometric;
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
/*     */ public class DefaultIsoWorldTarget
/*     */   implements IsoWorldTarget
/*     */ {
/*     */   private double m_worldX;
/*     */   private double m_worldY;
/*     */   private double m_altitude;
/*     */   
/*     */   public DefaultIsoWorldTarget() {}
/*     */   
/*     */   public DefaultIsoWorldTarget(double worldX, double worldY, double altitude) {
/*  31 */     this.m_worldX = worldX;
/*  32 */     this.m_worldY = worldY;
/*  33 */     this.m_altitude = altitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAltitude() {
/*  42 */     return this.m_altitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldCellX() {
/*  51 */     return (int)Math.floor(this.m_worldX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldCellY() {
/*  60 */     return (int)Math.floor(this.m_worldY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWorldX() {
/*  69 */     return this.m_worldX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWorldY() {
/*  78 */     return this.m_worldY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAltitude(double altitude) {
/*  87 */     this.m_altitude = altitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldX(double worldX) {
/*  96 */     this.m_worldX = worldX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldY(double worldY) {
/* 105 */     this.m_worldY = worldY;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\DefaultIsoWorldTarget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */