/*     */ package com.ankamagames.xulor.binding.fenggui;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import com.ankamagames.xulor.util.TextureLoader;
/*     */ import java.net.URL;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.IWidget;
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
/*     */ public class MouseImageManager
/*     */ {
/*  25 */   private static MouseImageManager m_mouseManager = new MouseImageManager();
/*     */   
/*  27 */   private Image m_image = new Image();
/*     */   private boolean m_show = false;
/*  29 */   private Alignment m_hotPoint = Alignment.SOUTH_WEST;
/*  30 */   private int m_xOffset = 0, m_yOffset = 0;
/*  31 */   private int m_x = 0, m_y = 0;
/*     */   
/*     */   private MouseImageManager() {
/*  34 */     this.m_image.setNonBlocking(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MouseImageManager getInstance() {
/*  42 */     if (m_mouseManager == null) {
/*  43 */       m_mouseManager = new MouseImageManager();
/*     */     }
/*  45 */     return m_mouseManager;
/*     */   }
/*     */   
/*     */   public void setXY(int mouseX, int mouseY) {
/*  49 */     this.m_x = mouseX;
/*  50 */     this.m_y = mouseY;
/*  51 */     updatePosition();
/*     */   }
/*     */   
/*     */   public void updatePosition() {
/*  55 */     if (this.m_image != null) {
/*  56 */       int x = this.m_x - this.m_hotPoint.getX(this.m_image.getWidth()) + this.m_xOffset;
/*  57 */       int y = this.m_y - this.m_hotPoint.getY(this.m_image.getHeight()) + this.m_yOffset;
/*  58 */       this.m_image.setXY(x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap pixmap) {
/*  67 */     if (this.m_image != null) {
/*  68 */       this.m_image.setPixmap(FengguiConstant.toFengguiPixmap(pixmap));
/*  69 */       this.m_image.setSizeToMinSize();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setURL(URL url) {
/*  74 */     if (this.m_image != null) {
/*  75 */       Pixmap pixmap = new Pixmap(TextureLoader.getInstance().loadTexture(url));
/*  76 */       setPixmap(pixmap);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/*  85 */     if (this.m_image != null && !this.m_show) {
/*  86 */       Display display = ((FengguiScene)Xulor.getInstance().getScene()).getDisplay();
/*  87 */       display.addWidget((IWidget)this.m_image);
/*  88 */       this.m_show = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide() {
/*  97 */     if (this.m_image != null && this.m_show) {
/*  98 */       Display display = ((FengguiScene)Xulor.getInstance().getScene()).getDisplay();
/*  99 */       display.removeWidget((IWidget)this.m_image);
/* 100 */       this.m_show = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Alignment getHotPoint() {
/* 105 */     return this.m_hotPoint;
/*     */   }
/*     */   
/*     */   public void setHotPoint(Alignment hotPoint) {
/* 109 */     this.m_hotPoint = hotPoint;
/* 110 */     updatePosition();
/*     */   }
/*     */   
/*     */   public int getXOffset() {
/* 114 */     return this.m_xOffset;
/*     */   }
/*     */   
/*     */   public void setXOffset(int offset) {
/* 118 */     this.m_xOffset = offset;
/* 119 */     updatePosition();
/*     */   }
/*     */   
/*     */   public int getYOffset() {
/* 123 */     return this.m_yOffset;
/*     */   }
/*     */   
/*     */   public void setYOffset(int offset) {
/* 127 */     this.m_yOffset = offset;
/* 128 */     updatePosition();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\MouseImageManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */