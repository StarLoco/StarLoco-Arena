/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.IPixmapWidget;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.layout.Alignment;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Dimension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Image
/*     */   extends ObservableWidget
/*     */   implements IPixmapWidget, NonBlocking
/*     */ {
/*     */   protected Pixmap m_pixmap;
/*  29 */   protected boolean m_scaled = false;
/*  30 */   protected boolean m_keepAspectRatio = true;
/*  31 */   protected Alignment m_alignment = Alignment.MIDDLE;
/*     */   
/*     */   protected ImageAppearance m_appearance;
/*  34 */   protected boolean m_nonBlocking = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public Image()
/*     */   {
/*  40 */     this.m_appearance = new ImageAppearance(this);
/*     */   }
/*     */   
/*     */ 
/*     */   public Image(String file)
/*     */     throws IOException
/*     */   {
/*  47 */     this(file, true);
/*     */   }
/*     */   
/*     */ 
/*     */   public Image(String file, boolean scaled)
/*     */     throws IOException
/*     */   {
/*  54 */     this.m_scaled = scaled;
/*  55 */     setImage(file);
/*  56 */     this.m_appearance = new ImageAppearance(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Image(Pixmap pixmap, boolean scaled)
/*     */   {
/*  63 */     this.m_scaled = scaled;
/*  64 */     setPixmap(pixmap);
/*  65 */     this.m_appearance = new ImageAppearance(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Pixmap getPixmap()
/*     */   {
/*  72 */     return this.m_pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPixmap(Pixmap pixmap)
/*     */   {
/*  80 */     this.m_pixmap = pixmap;
/*  81 */     updateMinSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setImage(String file)
/*     */     throws IOException
/*     */   {
/*  89 */     setPixmap(new Pixmap(Binding.getInstance().getTexture(file)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isScaled()
/*     */   {
/*  96 */     return this.m_scaled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScaled(boolean scaled)
/*     */   {
/* 104 */     this.m_scaled = scaled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isKeepAspectRatio()
/*     */   {
/* 111 */     return this.m_keepAspectRatio;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKeepAspectRatio(boolean keepAspectRatio)
/*     */   {
/* 119 */     this.m_keepAspectRatio = keepAspectRatio;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getAlignment()
/*     */   {
/* 126 */     return this.m_alignment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAlignment(Alignment alignment)
/*     */   {
/* 134 */     this.m_alignment = alignment;
/*     */   }
/*     */   
/*     */   public void setNonBlocking(boolean nonBlocking) {
/* 138 */     this.m_nonBlocking = nonBlocking;
/*     */   }
/*     */   
/*     */   public boolean isNonBlocking() {
/* 142 */     return this.m_nonBlocking;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IWidget getWidget(int x, int y)
/*     */   {
/* 152 */     if (this.m_nonBlocking) {
/* 153 */       return null;
/*     */     }
/* 155 */     return super.getWidget(x, y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public class ImageAppearance
/*     */     extends DecoratorAppearance
/*     */   {
/*     */     public ImageAppearance(IWidget w)
/*     */     {
/* 168 */       super();
/*     */     }
/*     */     
/*     */     public Dimension getContentMinSizeHint()
/*     */     {
/* 173 */       if (Image.this.m_pixmap == null) {
/* 174 */         return new Dimension(0, 0);
/*     */       }
/* 176 */       return new Dimension(Image.this.m_pixmap.getWidth(), Image.this.m_pixmap.getHeight());
/*     */     }
/*     */     
/*     */ 
/*     */     public void paintContent(Graphics g, IOpenGL gl)
/*     */     {
/* 182 */       if (Image.this.m_pixmap != null) {
/* 183 */         g.setColor(Color.WHITE);
/* 184 */         if (Image.this.m_scaled) {
/* 185 */           if (Image.this.m_keepAspectRatio) { int width;
/*     */             int width;
/* 187 */             int height; if ((getContentWidth() != 0) && (Image.this.m_pixmap.getWidth() != 0) && (getContentHeight() != 0) && (Image.this.m_pixmap.getHeight() != 0)) {
/* 188 */               float pixmapRatio = Image.this.m_pixmap.getWidth() / Image.this.m_pixmap.getHeight();
/* 189 */               float widgetRatio = getContentWidth() / getContentHeight();
/* 190 */               int height; if (pixmapRatio == widgetRatio) {
/* 191 */                 int width = getContentWidth();
/* 192 */                 height = getContentHeight(); } else { int height;
/* 193 */                 if (pixmapRatio > widgetRatio) {
/* 194 */                   int width = getContentWidth();
/* 195 */                   height = (int)(width / pixmapRatio);
/*     */                 } else {
/* 197 */                   int height = getContentHeight();
/* 198 */                   width = (int)(height * pixmapRatio);
/*     */                 }
/*     */               }
/* 201 */             } else { width = getContentWidth();
/* 202 */               height = getContentHeight();
/*     */             }
/* 204 */             int x = Image.this.m_alignment.alignX(getContentWidth(), width);
/* 205 */             int y = Image.this.m_alignment.alignY(getContentHeight(), height);
/* 206 */             g.drawScaledImage(Image.this.m_pixmap, x, y, width, height);
/*     */           } else {
/* 208 */             g.drawScaledImage(Image.this.m_pixmap, 0, 0, getContentWidth(), getContentHeight());
/*     */           }
/*     */         } else {
/* 211 */           int width = Image.this.m_pixmap.getWidth();
/* 212 */           int height = Image.this.m_pixmap.getHeight();
/* 213 */           int x = Image.this.m_alignment.alignX(getContentWidth(), width);
/* 214 */           int y = Image.this.m_alignment.alignY(getContentHeight(), height);
/* 215 */           g.drawImage(Image.this.m_pixmap, x, y);
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
/*     */   public ImageAppearance getAppearance()
/*     */   {
/* 230 */     return this.m_appearance;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\Image.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */