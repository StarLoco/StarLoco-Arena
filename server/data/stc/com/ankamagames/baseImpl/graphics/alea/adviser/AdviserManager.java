/*     */ package com.ankamagames.baseImpl.graphics.alea.adviser;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.graphics.isometric.IsoCamera;
/*     */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
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
/*     */ public class AdviserManager
/*     */   implements RenderProcessHandler<AleaWorldScene>
/*     */ {
/*  23 */   private static AdviserManager m_instance = new AdviserManager();
/*     */   
/*     */ 
/*     */ 
/*     */   private List<Adviser> m_advisers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AdviserManager()
/*     */   {
/*  34 */     this.m_advisers = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static AdviserManager getInstance()
/*     */   {
/*  41 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAdviser(Adviser adviser)
/*     */   {
/*  50 */     if (!this.m_advisers.contains(adviser)) {
/*  51 */       this.m_advisers.add(adviser);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAdviser(Adviser adviser)
/*     */   {
/*  61 */     if (this.m_advisers.contains(adviser)) {
/*  62 */       this.m_advisers.remove(adviser);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/*  70 */     this.m_advisers.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prepareBeforeRendering(AleaWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY)
/*     */   {
/*  80 */     for (Adviser adviser : this.m_advisers)
/*     */     {
/*  82 */       int elevationUnit = (int)Math.floor(scene.getElevationUnit());
/*  83 */       int adviserAltitudePx = (int)adviser.getAltitude() * elevationUnit;
/*     */       
/*  85 */       double isoLocalX = adviser.getWorldX() - centerScreenIsoWorldX;
/*  86 */       double isoLocalY = adviser.getWorldY() - centerScreenIsoWorldY;
/*     */       
/*  88 */       IsoCamera isoCamera = scene.getIsoCamera();
/*     */       
/*  90 */       int rx = (int)(scene.isoToScreenX(isoLocalX, isoLocalY) + adviser.getXOffset());
/*  91 */       int ry = (int)(scene.isoToScreenY(isoLocalX, isoLocalY) + adviser.getYOffset());
/*     */       
/*  93 */       adviser.setPosition(rx, ry + adviserAltitudePx, (float)-isoCamera.getCameraDeltaScreenX(), (float)-isoCamera.getCameraDeltaScreenY());
/*     */       
/*  95 */       scene.addChild(adviser);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(AleaWorldScene scene, long realTime, int frameCount)
/*     */   {
/* 106 */     for (int i = this.m_advisers.size() - 1; i >= 0; i--) {
/* 107 */       Adviser adviser = (Adviser)this.m_advisers.get(i);
/* 108 */       if (!adviser.isAlive()) {
/* 109 */         removeAdviser(adviser);
/*     */       } else {
/* 111 */         adviser.process(scene, realTime, frameCount);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\adviser\AdviserManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */