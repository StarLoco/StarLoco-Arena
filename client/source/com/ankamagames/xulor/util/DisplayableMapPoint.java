/*     */ package com.ankamagames.xulor.util;
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
/*     */ public class DisplayableMapPoint
/*     */ {
/*     */   private double m_isoX;
/*     */   private double m_isoY;
/*  25 */   private Object m_value = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String m_texturePath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] m_color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayableMapPoint(double isoX, double isoY, String texturePath, float[] color) {
/*  46 */     this.m_isoX = isoX;
/*  47 */     this.m_isoY = isoY;
/*  48 */     this.m_texturePath = texturePath;
/*  49 */     this.m_color = color;
/*     */   }
/*     */   
/*     */   public DisplayableMapPoint(double isoX, double isoY, Object value, String texturePath, float[] color) {
/*  53 */     this.m_isoX = isoX;
/*  54 */     this.m_isoY = isoY;
/*  55 */     this.m_value = value;
/*  56 */     this.m_texturePath = texturePath;
/*  57 */     this.m_color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getIsoX() {
/*  64 */     return this.m_isoX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsoX(double isoX) {
/*  71 */     this.m_isoX = isoX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getIsoY() {
/*  78 */     return this.m_isoY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsoY(double isoY) {
/*  85 */     this.m_isoY = isoY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTexturePath() {
/*  92 */     return this.m_texturePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexturePath(String texturePath) {
/* 101 */     this.m_texturePath = texturePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getColor() {
/* 108 */     return this.m_color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(float[] color) {
/* 118 */     this.m_color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 125 */     return this.m_value;
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 129 */     this.m_value = value;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\DisplayableMapPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */