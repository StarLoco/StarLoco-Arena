/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
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
/*     */ public abstract class StandardWidget
/*     */   extends Widget
/*     */   implements IOStreamSaveable
/*     */ {
/*     */   public abstract IAppearance getAppearance();
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/*  48 */     IAppearance appearance = getAppearance();
/*  49 */     if (appearance != null && appearance instanceof SpacingAppearance) {
/*  50 */       if (((SpacingAppearance)appearance).insideMargin(x, y)) {
/*  51 */         return this;
/*     */       }
/*  53 */       return null;
/*     */     } 
/*     */     
/*  56 */     return super.getWidget(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics g) {
/*  61 */     if (getAppearance() != null) getAppearance().paint(g, g.getOpenGL());
/*     */   
/*     */   }
/*     */   
/*     */   public void updateMinSize() {
/*  66 */     if (getAppearance() != null) setMinSize(getAppearance().getMinSizeHint());
/*     */     
/*  68 */     if (getParent() != null) getParent().updateMinSize();
/*     */   
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setupTheme(Class clazz) {
/*  87 */     if (getClass().equals(clazz)) FengGUI.getTheme().setUp(this);
/*     */   
/*     */   }
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/*  92 */     setExpandable(stream.processAttribute("expandable", isExpandable(), true));
/*  93 */     setShrinkable(stream.processAttribute("shrinkable", isShrinkable(), true));
/*  94 */     setWidth(stream.processAttribute("width", getWidth(), 10));
/*  95 */     setHeight(stream.processAttribute("height", getHeight(), 10));
/*  96 */     setMinSize(stream.processAttribute("minWidth", getMinWidth(), 10), 
/*  97 */         stream.processAttribute("minHeight", getMinHeight(), 10));
/*  98 */     setX(stream.processAttribute("x", getX(), 10));
/*  99 */     setY(stream.processAttribute("y", getY(), 10));
/*     */     
/* 101 */     if (getAppearance() instanceof IOStreamSaveable)
/*     */     {
/* 103 */       if (stream.startSubcontext("Appearance")) {
/*     */         
/* 105 */         ((IOStreamSaveable)getAppearance()).process(stream);
/* 106 */         stream.endSubcontext();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public String getUniqueName() {
/* 112 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\StandardWidget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */