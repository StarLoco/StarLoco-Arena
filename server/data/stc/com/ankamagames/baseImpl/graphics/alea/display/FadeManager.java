/*     */ package com.ankamagames.baseImpl.graphics.alea.display;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*     */ import gnu.trove.TLongArrayList;
/*     */ import gnu.trove.TLongIntHashMap;
/*     */ import gnu.trove.TLongIntIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FadeManager
/*     */   implements RenderProcessHandler
/*     */ {
/*     */   private static final int GROUP_FADE_MAX = 25;
/*     */   private static final int OUTDOOR_FADE_MAX = 10;
/*     */   private static final float TEINT_FACTOR = 0.03F;
/*  24 */   private static final FadeManager m_instance = new FadeManager();
/*     */   
/*  26 */   private TLongIntHashMap m_groupAlphaFading = new TLongIntHashMap();
/*  27 */   private int m_outdoorBlackFading = 10;
/*  28 */   private int m_outdoorAlphaFading = 10;
/*     */   
/*     */ 
/*  31 */   private TLongArrayList m_maskedGroupLayers = new TLongArrayList();
/*  32 */   private boolean m_outdoorVisible = true;
/*     */   
/*     */   public static FadeManager getInstance()
/*     */   {
/*  36 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void apply(DisplayedElement displayedElement, float brightnessValue, float[] teint)
/*     */   {
/*  46 */     int groupInstanceId = displayedElement.getWorldElement().getGroupInstanceId();
/*  47 */     int groupLayer = displayedElement.getLevel();
/*     */     
/*  49 */     long hashLayer = groupLayer & 0xFFFFFFFF;
/*  50 */     long hashGroup = groupInstanceId & 0xFFFFFFFF;
/*  51 */     long hashIndex = hashLayer << 32 | hashGroup;
/*     */     
/*     */     float blackFade;
/*     */     float alphaFade;
/*     */     float blackFade;
/*  56 */     if (groupInstanceId > 0)
/*     */     {
/*  58 */       if (!this.m_groupAlphaFading.contains(hashIndex))
/*     */       {
/*  60 */         this.m_groupAlphaFading.put(hashIndex, 25);
/*     */       }
/*     */       
/*  63 */       float alphaFade = this.m_groupAlphaFading.get(hashIndex) / 25.0F;
/*  64 */       blackFade = alphaFade / 2.0F;
/*     */     }
/*     */     else
/*     */     {
/*  68 */       alphaFade = this.m_outdoorAlphaFading / 10.0F;
/*  69 */       blackFade = this.m_outdoorBlackFading / 10.0F * brightnessValue;
/*     */     }
/*     */     
/*  72 */     if (teint == null) {
/*  73 */       displayedElement.getMesh().setColor(blackFade, blackFade, blackFade, alphaFade);
/*     */     } else {
/*  75 */       displayedElement.getMesh().setColor(blackFade + teint[0], blackFade + teint[1], blackFade + teint[2], alphaFade);
/*     */     }
/*  77 */     displayedElement.setVisible(alphaFade > 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addMaskedLayers(int layer, int groupInstance)
/*     */   {
/*  87 */     if (layer == -1)
/*     */     {
/*  89 */       this.m_outdoorVisible = false;
/*  90 */       return;
/*     */     }
/*     */     
/*  93 */     long hashLayer = layer & 0xFFFFFFFF;
/*  94 */     long hashGroup = groupInstance & 0xFFFFFFFF;
/*  95 */     long hashIndex = hashLayer << 32 | hashGroup;
/*     */     
/*  97 */     this.m_maskedGroupLayers.add(hashIndex);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearMaskedLayers()
/*     */   {
/* 105 */     this.m_maskedGroupLayers.clear();
/* 106 */     this.m_outdoorVisible = true;
/*     */   }
/*     */   
/*     */   public void setOutdoorVisible(boolean outdoorVisible) {
/* 110 */     this.m_outdoorVisible = outdoorVisible;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(IsoWorldScene scene, long realTime, int frameCount)
/*     */   {
/* 118 */     for (TLongIntIterator it = this.m_groupAlphaFading.iterator(); it.hasNext();)
/*     */     {
/* 120 */       it.advance();
/*     */       
/* 122 */       if (this.m_maskedGroupLayers.contains(it.key()))
/*     */       {
/* 124 */         if (it.value() > 0) {
/* 125 */           this.m_groupAlphaFading.adjustValue(it.key(), -1);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 130 */       else if (it.value() < 25) {
/* 131 */         this.m_groupAlphaFading.adjustValue(it.key(), 1);
/*     */       }
/*     */     }
/*     */     
/* 135 */     if (this.m_outdoorVisible)
/*     */     {
/* 137 */       if (this.m_outdoorAlphaFading < 10) {
/* 138 */         this.m_outdoorAlphaFading += 1;
/* 139 */       } else if (this.m_outdoorBlackFading < 10) {
/* 140 */         this.m_outdoorBlackFading += 1;
/*     */       }
/*     */       
/*     */     }
/* 144 */     else if (this.m_outdoorBlackFading > 0) {
/* 145 */       this.m_outdoorBlackFading -= 1;
/* 146 */     } else if (this.m_outdoorAlphaFading > 0) {
/* 147 */       this.m_outdoorAlphaFading -= 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public void prepareBeforeRendering(IsoWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\FadeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */