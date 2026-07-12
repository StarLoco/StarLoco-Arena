/*     */ package org.fenggui;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.fenggui.io.IOStreamSaveable;
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
/*     */ public abstract class LayoutManager
/*     */   implements IOStreamSaveable
/*     */ {
/*     */   public int getValidMinHeight(IWidget w) {
/*  42 */     if (w.isShrinkable()) return w.getMinSize().getHeight(); 
/*  43 */     return w.getSize().getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getValidMinWidth(IWidget w) {
/*  48 */     if (w.isShrinkable()) return w.getMinSize().getWidth(); 
/*  49 */     return w.getSize().getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValidSize(IWidget widget, int width, int height) {
/*  54 */     int height2Set = widget.getSize().getHeight();
/*  55 */     int width2Set = widget.getSize().getWidth();
/*     */     
/*  57 */     if (widget.getSize().getHeight() > height)
/*     */     
/*  59 */     { if (widget.isShrinkable()) height2Set = height;
/*     */       
/*     */        }
/*     */     
/*  63 */     else if (widget.isExpandable()) { height2Set = height; }
/*     */ 
/*     */     
/*  66 */     if (widget.getSize().getWidth() > width)
/*     */     
/*  68 */     { if (widget.isShrinkable()) width2Set = width;
/*     */       
/*     */        }
/*     */     
/*  72 */     else if (widget.isExpandable()) { width2Set = width; }
/*     */ 
/*     */     
/*  75 */     widget.setSize(new Dimension(width2Set, height2Set));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValidHeight(IWidget widget, int height) {
/*  80 */     if (widget.getSize().getHeight() > height) {
/*     */       
/*  82 */       if (widget.isShrinkable()) {
/*  83 */         widget.setSize(new Dimension(widget.getSize().getWidth(), height));
/*     */       
/*     */       }
/*     */     }
/*  87 */     else if (widget.isExpandable()) {
/*  88 */       widget.setSize(new Dimension(widget.getSize().getWidth(), height));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValidWidth(IWidget widget, int width) {
/*  94 */     if (widget.getSize().getWidth() > width) {
/*     */       
/*  96 */       if (widget.isShrinkable()) {
/*  97 */         widget.setSize(new Dimension(width, widget.getSize().getHeight()));
/*     */       
/*     */       }
/*     */     }
/* 101 */     else if (widget.isExpandable()) {
/* 102 */       widget.setSize(new Dimension(width, widget.getSize().getHeight()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void doLayout(Container paramContainer, List<IWidget> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Dimension computeMinSize(Container paramContainer, List<IWidget> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 121 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\LayoutManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */