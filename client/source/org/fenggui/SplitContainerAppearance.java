/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */ public class SplitContainerAppearance
/*     */   extends SpacingAppearance
/*     */ {
/*  34 */   private SplitContainer cont = null;
/*  35 */   private DecoratorLayer barDecorator = new DecoratorLayer(new IDecorator[0]);
/*     */ 
/*     */   
/*     */   public SplitContainerAppearance(SplitContainer w) {
/*  39 */     super(w);
/*  40 */     this.cont = w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  46 */     int firstMinHeight = 0;
/*  47 */     int firstMinWidth = 0;
/*     */     
/*  49 */     int secondMinHeight = 0;
/*  50 */     int secondMinWidth = 0;
/*     */     
/*  52 */     IWidget firstWidget = this.cont.getFirstWidget();
/*  53 */     IWidget secondWidget = this.cont.getSecondWidget();
/*     */     
/*  55 */     if (firstWidget != null) {
/*     */ 
/*     */ 
/*     */       
/*  59 */       firstMinHeight = firstWidget.getMinSize().getHeight();
/*  60 */       firstMinWidth = firstWidget.getMinSize().getWidth();
/*     */     } 
/*     */     
/*  63 */     if (secondWidget != null) {
/*     */ 
/*     */ 
/*     */       
/*  67 */       secondMinHeight = secondWidget.getMinSize().getHeight();
/*  68 */       secondMinWidth = secondWidget.getMinSize().getWidth();
/*     */     } 
/*     */     
/*  71 */     if (this.cont.isHorizontal())
/*     */     {
/*  73 */       return new Dimension(
/*  74 */           Math.max(firstMinWidth, secondMinWidth), 
/*  75 */           firstMinHeight + this.cont.getBarSize() + secondMinHeight);
/*     */     }
/*     */ 
/*     */     
/*  79 */     return new Dimension(
/*  80 */         firstMinWidth + this.cont.getBarSize() + secondMinWidth, 
/*  81 */         Math.max(firstMinHeight, secondMinHeight));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/*     */     int width, height, x, y;
/*  88 */     IWidget firstWidget = this.cont.getFirstWidget();
/*  89 */     IWidget secondWidget = this.cont.getSecondWidget();
/*     */     
/*  91 */     if (firstWidget != null) {
/*     */       
/*  93 */       g.translate(firstWidget.getX(), firstWidget.getY());
/*  94 */       firstWidget.paint(g);
/*  95 */       g.translate(-firstWidget.getX(), -firstWidget.getY());
/*     */     } 
/*     */     
/*  98 */     if (secondWidget != null) {
/*     */       
/* 100 */       g.translate(secondWidget.getX(), secondWidget.getY());
/* 101 */       secondWidget.paint(g);
/* 102 */       g.translate(-secondWidget.getX(), -secondWidget.getY());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (this.cont.isHorizontal()) {
/*     */       
/* 110 */       width = getContentWidth();
/* 111 */       height = this.cont.getBarSize();
/* 112 */       x = 0;
/* 113 */       y = this.cont.getValue();
/*     */     }
/*     */     else {
/*     */       
/* 117 */       width = this.cont.getBarSize();
/* 118 */       height = getContentHeight();
/* 119 */       x = this.cont.getValue();
/* 120 */       y = 0;
/*     */     } 
/*     */     
/* 123 */     this.barDecorator.paint(g, x, y, width, height);
/*     */     
/* 125 */     g.setColor(Color.WHITE);
/* 126 */     Pixmap pixmap = this.cont.getPixmap();
/* 127 */     if (pixmap != null) {
/* 128 */       g.drawImage(pixmap, x + width / 2 - pixmap.getWidth() / 2, y + height / 2 - pixmap.getHeight() / 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public DecoratorLayer getBarDecorator() {
/* 133 */     return this.barDecorator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 139 */     super.process(stream);
/*     */     
/* 141 */     this.barDecorator = (DecoratorLayer)stream.processChild("BarDecorator", this.barDecorator, this.barDecorator, DecoratorLayer.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\SplitContainerAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */