/*     */ package com.ankamagames.baseImpl.graphics.alea.worldElement;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldElementManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.graphics.isometric.highlight.UniqueHandleReference;
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
/*     */ public abstract class WorldElement
/*     */   implements UniqueHandleReference, Comparable<WorldElement>
/*     */ {
/*     */   BasicElement m_element;
/*     */   protected int m_state;
/*     */   protected int m_paramsCount;
/*     */   protected byte[] m_params;
/*     */   protected int m_groupInstanceId;
/*     */   protected int m_level;
/*     */   protected byte m_altitude;
/*     */   protected float m_brightness;
/*     */   protected float[] m_teint;
/*     */   protected float m_altitudeOrder;
/*  31 */   protected Point3 m_coordinates = new Point3();
/*     */   
/*  33 */   protected long m_handle = 1L;
/*     */   
/*     */   public WorldElement(int elementId, int paramsCount, byte[] params, int state, int groupId) {
/*  36 */     this.m_element = WorldElementManager.getInstance().getElement(elementId);
/*  37 */     this.m_params = params;
/*  38 */     this.m_paramsCount = paramsCount;
/*  39 */     this.m_groupInstanceId = groupId;
/*  40 */     this.m_state = state;
/*     */   }
/*     */   
/*     */   public long getHandle()
/*     */   {
/*  45 */     return this.m_handle;
/*     */   }
/*     */   
/*     */   public void SetHandle(long value) {
/*  49 */     this.m_handle = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPrecalculatedInformations(int level, byte altitude, float brightness, float[] teint, float altitudeOrder)
/*     */   {
/*  61 */     this.m_level = level;
/*  62 */     this.m_altitude = altitude;
/*  63 */     this.m_brightness = brightness;
/*  64 */     this.m_teint = teint;
/*  65 */     this.m_altitudeOrder = altitudeOrder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getAltitude()
/*     */   {
/*  73 */     return this.m_altitude;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAltitude(byte altitude)
/*     */   {
/*  80 */     this.m_altitude = altitude;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getLevel()
/*     */   {
/*  87 */     return this.m_level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getBrightness()
/*     */   {
/*  94 */     return this.m_brightness;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] getTeint()
/*     */   {
/* 102 */     return this.m_teint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getAltitudeOrder()
/*     */   {
/* 109 */     return this.m_altitudeOrder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BasicElement getElement()
/*     */   {
/* 116 */     return this.m_element;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getGroupInstanceId()
/*     */   {
/* 123 */     return this.m_groupInstanceId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getParams()
/*     */   {
/* 130 */     return this.m_params;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getParamsCount()
/*     */   {
/* 137 */     return this.m_paramsCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getState()
/*     */   {
/* 144 */     return this.m_state;
/*     */   }
/*     */   
/*     */   public double getWeight() {
/* 148 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public double getHeight() {
/* 152 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public double getVisualHeight() {
/* 156 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public byte getSlope() {
/* 160 */     return 0;
/*     */   }
/*     */   
/*     */   public int compareTo(WorldElement o)
/*     */   {
/* 165 */     float a1 = getAltitude();
/* 166 */     float a2 = o.getAltitude();
/*     */     
/* 168 */     if (a1 < a2)
/* 169 */       return -1;
/* 170 */     if (a1 > a2) {
/* 171 */       return 1;
/*     */     }
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
/* 183 */     return 0;
/*     */   }
/*     */   
/*     */   public void setCoordinates(int x, int y, byte z)
/*     */   {
/* 188 */     this.m_coordinates.set(x, y, (short)(int)(z + getHeight()));
/*     */   }
/*     */   
/*     */   public Point3 getCoordinates() {
/* 192 */     return this.m_coordinates;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\worldElement\WorldElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */