/*     */ package com.ankamagames.xulor.core.impl;
/*     */ 
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IPixmapable;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import com.ankamagames.xulor.util.ThemeTexture;
/*     */ import java.net.URL;
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
/*     */ public class XPixmap
/*     */   extends XElement
/*     */ {
/*     */   public static final String TAG = "Pixmap";
/*  25 */   private Pixmap m_pixmap = new Pixmap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/*  41 */     return this.m_pixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  48 */     updateParent();
/*  49 */     for (IElement child : this.m_children) {
/*  50 */       child.buildGUI();
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateParent() {
/*  55 */     IElement parent = getParent();
/*  56 */     if (parent instanceof IPixmapable) {
/*  57 */       ((IPixmapable)parent).setPixmap(this.m_pixmap);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTexture(ThemeTexture texture) {
/*  68 */     this.m_pixmap.setTexture(texture);
/*  69 */     updateParent();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setURL(URL url) {}
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/*  77 */     this.m_pixmap.setX(x);
/*  78 */     updateParent();
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/*  82 */     this.m_pixmap.setY(y);
/*  83 */     updateParent();
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/*  87 */     this.m_pixmap.setWidth(width);
/*  88 */     updateParent();
/*     */   }
/*     */   
/*     */   public void setHeight(int height) {
/*  92 */     this.m_pixmap.setHeight(height);
/*  93 */     updateParent();
/*     */   }
/*     */   
/*     */   public String getTag() {
/*  97 */     return "Pixmap";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 105 */     XPixmap pixmap = (XPixmap)element;
/* 106 */     if (this.m_pixmap != null) pixmap.m_pixmap = this.m_pixmap.clone(); 
/* 107 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 114 */     XPixmap pixmap = new XPixmap();
/* 115 */     copyElementData(pixmap);
/* 116 */     return pixmap;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\impl\XPixmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */