/*     */ package com.ankamagames.baseImpl.graphics.alea.mobile;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.HitTestableMesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class MobileManager
/*     */   implements RenderProcessHandler<AleaWorldScene>
/*     */ {
/*  28 */   private static MobileManager m_instance = new MobileManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ConcurrentHashMap<Long, Mobile> m_mobiles;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<Mobile> m_mobilesToInvalidate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ArrayList<Mobile> m_hitMobiles;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private List<Mobile> m_sortedMobiles = new ArrayList<Mobile>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   Rectangle2D m_checkBoundsRect = new Rectangle2D.Float();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MobileManager() {
/*  60 */     this.m_mobiles = new ConcurrentHashMap<Long, Mobile>();
/*  61 */     this.m_mobilesToInvalidate = new ArrayList<Mobile>();
/*  62 */     this.m_hitMobiles = new ArrayList<Mobile>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MobileManager getInstance() {
/*  69 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMobile(Mobile mobile) {
/*  78 */     this.m_mobiles.put(Long.valueOf(mobile.getId()), mobile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mobile removeMobile(long id) {
/*  88 */     if (this.m_mobiles.containsKey(Long.valueOf(id))) {
/*  89 */       this.m_mobilesToInvalidate.add(this.m_mobiles.get(Long.valueOf(id)));
/*  90 */       return this.m_mobiles.remove(Long.valueOf(id));
/*     */     } 
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mobile removeMobile(Mobile mobile) {
/* 102 */     return removeMobile(mobile.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllMobiles() {
/* 109 */     this.m_mobilesToInvalidate.addAll(this.m_mobiles.values());
/* 110 */     this.m_mobiles.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mobile getMobile(long id) {
/* 120 */     if (this.m_mobiles.containsKey(Long.valueOf(id))) {
/* 121 */       return this.m_mobiles.get(Long.valueOf(id));
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Mobile> getMobiles() {
/* 130 */     return this.m_mobiles.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMobilesCount() {
/* 137 */     return this.m_mobiles.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(AleaWorldScene worldScene, long realTime, int frameCount) {
/* 148 */     for (Mobile mobileToInvalidate : this.m_mobilesToInvalidate) {
/* 149 */       if (mobileToInvalidate.isCarried()) {
/* 150 */         mobileToInvalidate.getCarrierMobile().uncarry();
/*     */       }
/* 152 */       if (mobileToInvalidate.isCarrier()) {
/* 153 */         mobileToInvalidate.uncarry();
/*     */       }
/*     */       
/* 156 */       DisplayObject displayObject = mobileToInvalidate.getDisplayObject();
/* 157 */       if (displayObject != null) {
/* 158 */         displayObject.invalidate();
/*     */       }
/*     */     } 
/* 161 */     this.m_mobilesToInvalidate.clear();
/*     */     
/* 163 */     sortMobile();
/* 164 */     Collection<Mobile> mobiles = this.m_sortedMobiles;
/*     */     
/* 166 */     for (Mobile mobile : mobiles) {
/* 167 */       mobile.process(worldScene, realTime, frameCount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Mobile> sortMobile() {
/* 178 */     Collection<Mobile> mobiles = this.m_mobiles.values();
/* 179 */     this.m_sortedMobiles.clear();
/*     */     
/* 181 */     for (Mobile mobile : mobiles) {
/* 182 */       int index = -1;
/* 183 */       Mobile carrier = mobile.getCarrierMobile();
/*     */       
/* 185 */       if (carrier != null) {
/* 186 */         index = this.m_sortedMobiles.indexOf(carrier);
/* 187 */         if (index != -1) {
/* 188 */           this.m_sortedMobiles.add(index + 1, mobile);
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 193 */       Mobile carried = mobile.getCarriedMobile();
/* 194 */       if (carried != null) {
/* 195 */         index = this.m_sortedMobiles.indexOf(carried);
/* 196 */         if (index != -1) {
/* 197 */           this.m_sortedMobiles.add(index, mobile);
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 202 */       this.m_sortedMobiles.add(mobile);
/*     */     } 
/* 204 */     return this.m_sortedMobiles;
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
/*     */   public void prepareBeforeRendering(AleaWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {
/* 219 */     Rectangle2D screenRect = scene.getBoundsScreenRect();
/* 220 */     double halfCellHeight = scene.getCellHeight() * 0.5D;
/* 221 */     int elevationUnit = (int)Math.floor(scene.getElevationUnit());
/* 222 */     Collection<Mobile> mobiles = this.m_sortedMobiles;
/* 223 */     for (Mobile mobile : mobiles) {
/*     */       
/* 225 */       DisplayObject displayObject = mobile.getDisplayObject();
/* 226 */       if (displayObject != null) {
/* 227 */         HitTestableMesh2D hitTestableMesh2D = displayObject.getMesh();
/* 228 */         if (hitTestableMesh2D != null) {
/* 229 */           double mobileAltitudePx = mobile.getAltitude() * elevationUnit;
/*     */           
/* 231 */           double isoLocalX = mobile.getWorldX() - centerScreenIsoWorldX;
/* 232 */           double isoLocalY = mobile.getWorldY() - centerScreenIsoWorldY;
/*     */           
/* 234 */           double rx = scene.isoToScreenX(isoLocalX, isoLocalY);
/* 235 */           double ry = scene.isoToScreenY(isoLocalX, isoLocalY);
/*     */           
/* 237 */           hitTestableMesh2D.setScreenPosition((float)rx, (float)(ry + mobileAltitudePx));
/*     */ 
/*     */           
/* 240 */           float leftBound = displayObject.getLeftBound();
/* 241 */           float topBound = displayObject.getBottomBound();
/* 242 */           float width = displayObject.getWidth();
/* 243 */           float height = displayObject.getHeight();
/* 244 */           this.m_checkBoundsRect.setRect(leftBound, topBound, width, height);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 249 */           if (screenRect.intersects(this.m_checkBoundsRect)) {
/* 250 */             scene.addChild((GLObject)hitTestableMesh2D);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 255 */             double rz = (-ry + mobile.getAltitude() + halfCellHeight) / scene.getFrustumHeight() + 5.000000237487257E-4D;
/*     */             
/* 257 */             hitTestableMesh2D.setZOrder((float)rz);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   public ArrayList<Mobile> getMobilesUnderPoint(float x, float y) {
/* 275 */     this.m_hitMobiles.clear();
/* 276 */     Collection<Mobile> mobiles = this.m_mobiles.values();
/* 277 */     for (Mobile mobile : mobiles) {
/* 278 */       if (mobile.hitTest(x, y)) {
/* 279 */         this.m_hitMobiles.add(mobile);
/*     */       }
/*     */     } 
/* 282 */     return this.m_hitMobiles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int selectMobilesUnderPoint(float x, float y) {
/* 293 */     int count = 0;
/* 294 */     for (Mobile mobile : this.m_mobiles.values()) {
/* 295 */       if (mobile.hitTest(x, y)) {
/* 296 */         mobile.setSelected(true);
/* 297 */         count++; continue;
/*     */       } 
/* 299 */       mobile.setSelected(false);
/*     */     } 
/*     */     
/* 302 */     return count;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\MobileManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */