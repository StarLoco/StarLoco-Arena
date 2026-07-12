/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image.ImageAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XImageAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IImage;
/*     */ import com.ankamagames.xulor.template.IPixmapable;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeImageAppearance;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import com.ankamagames.xulor.util.ThemeTexture;
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
/*     */ public class XImage
/*     */   extends XObservableComponent
/*     */   implements IImage, IPixmapable
/*     */ {
/*     */   public static final String TAG = "image";
/*  35 */   private Image m_image = null;
/*     */   
/*  37 */   protected Pixmap m_pixmap = null;
/*  38 */   protected boolean m_scaled = false;
/*  39 */   protected boolean m_keepAspectRatio = true;
/*  40 */   protected Alignment m_align = null;
/*     */   
/*  42 */   protected boolean m_scaledInit = false;
/*  43 */   protected boolean m_keepAspectRatioInit = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void displayNonBlockingAvailability() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  61 */     if (this.m_image == null) {
/*  62 */       this.m_image = new Image();
/*     */       
/*  64 */       applyAllAttributes();
/*     */       
/*  66 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  67 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_image, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  70 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement component = arrayOfIElement[i];
/*  71 */       component.buildGUI();
/*     */     }
/*  73 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Image getWidget()
/*     */   {
/*  83 */     return this.m_image;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  93 */     if (getWidget() != null) {
/*  94 */       if (this.m_align != null) {
/*  95 */         getWidget().setAlignment(FengguiConstant.toFengguiAlignment(this.m_align));
/*     */       }
/*  97 */       if (this.m_pixmap != null) {
/*  98 */         getWidget().setPixmap(FengguiConstant.toFengguiPixmap(this.m_pixmap));
/*     */       }
/* 100 */       if (this.m_scaledInit) {
/* 101 */         getWidget().setScaled(this.m_scaled);
/*     */       }
/* 103 */       if (this.m_keepAspectRatioInit) {
/* 104 */         getWidget().setKeepAspectRatio(this.m_keepAspectRatio);
/*     */       }
/* 106 */       applyComponentAttributes();
/*     */     }
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 111 */     if (this.m_themeNeedToBeApplied) {
/* 112 */       this.m_themeNeedToBeApplied = false;
/* 113 */       applyImageTheme(getWidget(), this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*     */     IElement[] arrayOfIElement;
/*     */     
/* 123 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement component = arrayOfIElement[i];
/* 124 */       component.buildXML();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getAlignment()
/*     */   {
/* 132 */     return this.m_align;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAlign(Alignment alignment)
/*     */   {
/* 141 */     this.m_align = alignment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isKeepAspectRatio()
/*     */   {
/* 148 */     return this.m_keepAspectRatio;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKeepAspectRatio(boolean keepAspectRatio)
/*     */   {
/* 157 */     this.m_keepAspectRatio = keepAspectRatio;
/* 158 */     this.m_keepAspectRatioInit = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isScaled()
/*     */   {
/* 165 */     return this.m_scaled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScaled(boolean scaled)
/*     */   {
/* 174 */     this.m_scaled = scaled;
/* 175 */     this.m_scaledInit = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPixmap(Pixmap pixmap)
/*     */   {
/* 184 */     if (getWidget() != null) {
/* 185 */       getWidget().setPixmap(FengguiConstant.toFengguiPixmap(pixmap));
/*     */     }
/* 187 */     this.m_pixmap = pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Pixmap getPixmap()
/*     */   {
/* 197 */     return this.m_pixmap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTexture(ThemeTexture texture)
/*     */   {
/* 206 */     this.m_pixmap = new Pixmap(texture);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 215 */     return "image";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 224 */     XImage elem = (XImage)element;
/* 225 */     elem.m_align = this.m_align;
/* 226 */     elem.m_keepAspectRatio = this.m_keepAspectRatio;
/* 227 */     elem.m_keepAspectRatioInit = this.m_keepAspectRatioInit;
/* 228 */     if (this.m_pixmap != null) elem.m_pixmap = this.m_pixmap.clone();
/* 229 */     elem.m_scaled = this.m_scaled;
/* 230 */     elem.m_scaledInit = this.m_scaledInit;
/* 231 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 240 */     XImage elem = new XImage();
/* 241 */     copyElementData(elem);
/* 242 */     return elem;
/*     */   }
/*     */   
/*     */   public static void applyImageTheme(Image image, ThemeElement element) {
/* 246 */     if ((image == null) || (element == null)) {
/* 247 */       return;
/*     */     }
/*     */     
/* 250 */     image.getAppearance().removeAll();
/* 251 */     XComponent.applyThemeAttributes(image, element.getAttributes());
/* 252 */     XSpacingAppearance.setAppearance(image, element);
/* 253 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 254 */     for (ThemeAppearance app : appearances) {
/* 255 */       if (app != null) {
/* 256 */         XDecoratorAppearance.setAppearance(image, app);
/* 257 */         if ((app instanceof ThemeImageAppearance)) {
/* 258 */           XImageAppearance.setAppearance(image, (ThemeImageAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */