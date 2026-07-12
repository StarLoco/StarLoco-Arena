/*     */ package com.ankamagames.graphics.isometric.lights;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*     */ import java.util.ArrayList;
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
/*     */ public class LightManager
/*     */   implements RenderProcessHandler
/*     */ {
/*  20 */   private static float DEFAULT_CONTRAST = 0.02F;
/*  21 */   private static float DAY_GLOBAL_LIGHT = 0.0F;
/*  22 */   private static float NIGHT_GLOBAL_LIGHT = 0.42F;
/*     */   
/*  24 */   private static LightManager m_instance = new LightManager();
/*     */   
/*     */   public static LightManager getInstance() {
/*  27 */     return m_instance;
/*     */   }
/*     */   
/*  30 */   public float m_lightContrast = DEFAULT_CONTRAST;
/*     */   
/*     */   public double m_lightBlink;
/*     */   
/*     */   public float m_globalLight;
/*     */   private float m_globalLightDesired;
/*  36 */   private ArrayList<LightSpot> m_spots = new ArrayList<LightSpot>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LightManager() {
/*  42 */     reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  49 */     this.m_spots.clear();
/*  50 */     this.m_lightContrast = DEFAULT_CONTRAST;
/*  51 */     this.m_globalLight = 0.0F;
/*  52 */     this.m_globalLightDesired = DAY_GLOBAL_LIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addLightSpot(LightSpot lightSpot) {
/*  57 */     this.m_spots.add(lightSpot);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeLightSpot(LightSpot lightSpot) {
/*  62 */     this.m_spots.remove(lightSpot);
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
/*     */   public void applyLightToMesh(Mesh2D mesh, double x, double y, double altitude) {
/*  76 */     float[] topLeft = applyLightToMeshBound(x, y, 0.7D, 0.0D, altitude);
/*  77 */     float[] topRight = applyLightToMeshBound(x, y, 0.0D, 0.7D, altitude);
/*  78 */     float[] bottomRight = applyLightToMeshBound(x, y, -0.7D, 0.0D, altitude);
/*  79 */     float[] bottomLeft = applyLightToMeshBound(x, y, 0.0D, -0.7D, altitude);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     mesh.getMaterial().addToDiffuseTopLeft(topLeft[0], topLeft[1], topLeft[2], 0.0F);
/*  87 */     mesh.getMaterial().addToDiffuseTopRight(topRight[0], topRight[1], topRight[2], 0.0F);
/*  88 */     mesh.getMaterial().addToDiffuseBottomRight(bottomRight[0], bottomRight[1], bottomRight[2], 0.0F);
/*  89 */     mesh.getMaterial().addToDiffuseBottomLeft(bottomLeft[0], bottomLeft[1], bottomLeft[2], 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] applyLightToMeshBound(double x, double y, double offsetX, double offsetY, double altitude) {
/*  94 */     float[] color = new float[3];
/*     */ 
/*     */     
/*  97 */     for (LightSpot spot : this.m_spots) {
/*     */       
/*  99 */       double xV = Math.abs(spot.getTarget().getWorldX() - x + offsetX);
/* 100 */       double yV = Math.abs(spot.getTarget().getWorldY() - y + offsetY);
/*     */       
/* 102 */       double zV = Math.abs(spot.getTarget().getAltitude() - altitude) / 3.0D;
/*     */       
/* 104 */       double distance = Math.sqrt(xV * xV + yV * yV + zV * zV);
/*     */       
/* 106 */       color[0] = (float)(color[0] + (spot.getIntensityR() + this.m_lightBlink) / distance);
/* 107 */       color[1] = (float)(color[1] + (spot.getIntensityG() + this.m_lightBlink) / distance);
/* 108 */       color[2] = (float)(color[2] + (spot.getIntensityB() + this.m_lightBlink) / distance);
/*     */     } 
/*     */ 
/*     */     
/* 112 */     color[0] = Math.min(1.0F, color[0] + getGlobalLight());
/* 113 */     color[1] = Math.min(1.0F, color[1] + getGlobalLight());
/* 114 */     color[2] = Math.min(1.0F, color[2] + getGlobalLight());
/*     */     
/* 116 */     return color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(IsoWorldScene scene, long realTime, int frameCount) {
/* 126 */     if (getGlobalLightDesired() != getGlobalLight())
/*     */     {
/* 128 */       setGlobalLight(getGlobalLight() + (getGlobalLightDesired() - getGlobalLight()) / 100.0F);
/*     */     }
/*     */ 
/*     */     
/* 132 */     this.m_lightBlink = (float)(Math.random() * 0.009999999776482582D);
/*     */   }
/*     */   
/*     */   public float getLightContrast() {
/* 136 */     return this.m_lightContrast;
/*     */   }
/*     */   
/*     */   public void setLightContrast(float lightContrast) {
/* 140 */     this.m_lightContrast = lightContrast;
/*     */   }
/*     */   
/*     */   public float getGlobalLight() {
/* 144 */     return this.m_globalLight;
/*     */   }
/*     */   
/*     */   public void setGlobalLight(float globalLight) {
/* 148 */     this.m_globalLight = globalLight;
/*     */   }
/*     */   
/*     */   public float getGlobalLightDesired() {
/* 152 */     return this.m_globalLightDesired;
/*     */   }
/*     */   
/*     */   public void setGlobalLightDesired(float globalLightDesired) {
/* 156 */     this.m_globalLightDesired = globalLightDesired;
/*     */   }
/*     */   
/*     */   public void prepareBeforeRendering(IsoWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\lights\LightManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */