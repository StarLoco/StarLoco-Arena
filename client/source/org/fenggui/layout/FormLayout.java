/*     */ package org.fenggui.layout;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */ public class FormLayout
/*     */   extends LayoutManager
/*     */ {
/*  46 */   private ArrayList<IWidget> order = new ArrayList<IWidget>();
/*  47 */   private Hashtable<IWidget, IWidget> sorted = new Hashtable<IWidget, IWidget>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean debug = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sort(IWidget w, String offset) {
/*     */     try {
/*  65 */       if (this.sorted.containsKey(w)) {
/*     */         
/*  67 */         if (this.debug) System.out.println("oh, " + w + " is already in the order list");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  73 */       FormData fd = (FormData)w.getLayoutData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  79 */       if (fd.allStatic()) {
/*     */         
/*  81 */         if (this.debug) System.out.println("all static for " + w + ", abort");
/*     */         
/*     */         return;
/*     */       } 
/*  85 */       if (fd.left != null && !fd.left.isStatic())
/*     */       {
/*     */         
/*  88 */         sort((IWidget)fd.left.getAttachedWidget(), String.valueOf(offset) + "  ");
/*     */       }
/*     */       
/*  91 */       if (fd.right != null && !fd.right.isStatic())
/*     */       {
/*     */         
/*  94 */         sort((IWidget)fd.right.getAttachedWidget(), String.valueOf(offset) + "  ");
/*     */       }
/*     */       
/*  97 */       if (fd.top != null && !fd.top.isStatic())
/*     */       {
/*     */         
/* 100 */         sort((IWidget)fd.top.getAttachedWidget(), String.valueOf(offset) + "  ");
/*     */       }
/*     */       
/* 103 */       if (fd.bottom != null && !fd.bottom.isStatic())
/*     */       {
/*     */         
/* 106 */         sort((IWidget)fd.bottom.getAttachedWidget(), String.valueOf(offset) + "  ");
/*     */       }
/*     */       
/* 109 */       this.order.add(w);
/* 110 */       this.sorted.put(w, w);
/*     */     }
/* 112 */     catch (StackOverflowError soe) {
/*     */       
/* 114 */       System.err.println("There seems to be a cyclic dependency for widget " + 
/* 115 */           w + "\n" + soe.getMessage());
/* 116 */       System.exit(-1);
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
/*     */   public void doLayout(Container container, List<IWidget> content) {
/* 128 */     int innerWidth = container.getAppearance().getContentWidth();
/* 129 */     int innerHeight = container.getAppearance().getContentHeight();
/*     */ 
/*     */     
/* 132 */     for (IWidget w : content) {
/*     */ 
/*     */ 
/*     */       
/* 136 */       w.setSize(new Dimension(getValidMinWidth(w), getValidMinHeight(w)));
/*     */ 
/*     */       
/* 139 */       FormData fd = (FormData)w.getLayoutData();
/* 140 */       if (fd == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       if (fd.left != null)
/*     */       {
/* 152 */         if (fd.left.isStatic()) {
/*     */           
/* 154 */           w.setX(innerWidth * fd.left.getNumerator() / 100);
/* 155 */           w.setX(w.getX() + fd.left.getOffset());
/*     */         } else {
/* 157 */           sort(w, "");
/*     */         } 
/*     */       }
/* 160 */       if (fd.right != null)
/*     */       {
/* 162 */         if (fd.right.isStatic()) {
/*     */ 
/*     */           
/* 165 */           if (fd.left != null && fd.left.isStatic()) {
/*     */             
/* 167 */             setValidWidth(w, innerWidth * fd.right.getNumerator() / 100 - w.getX());
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 172 */             w.setX(innerWidth * fd.right.getNumerator() / 100 - w.getSize().getWidth());
/* 173 */             w.setX(w.getX() + fd.right.getOffset());
/*     */           } 
/*     */         } else {
/* 176 */           sort(w, "");
/*     */         } 
/*     */       }
/* 179 */       if (fd.bottom != null) {
/*     */         
/* 181 */         if (this.debug) System.out.println("fd.bottom: " + w.getClass().getSimpleName() + " " + fd.bottom.isStatic());
/*     */         
/* 183 */         if (fd.bottom.isStatic()) {
/*     */           
/* 185 */           w.setY(innerHeight * fd.bottom.getNumerator() / 100);
/* 186 */           w.setY(w.getY() + fd.bottom.getOffset());
/*     */         } else {
/* 188 */           sort(w, "");
/*     */         } 
/*     */       } 
/* 191 */       if (fd.top != null) {
/*     */         
/* 193 */         if (fd.top.isStatic()) {
/*     */ 
/*     */           
/* 196 */           if (fd.bottom != null && fd.bottom.isStatic()) {
/* 197 */             setValidHeight(w, innerHeight - innerHeight * fd.top.getNumerator() / 100);
/*     */             
/*     */             continue;
/*     */           } 
/* 201 */           w.setY(innerHeight * fd.top.getNumerator() / 100 - w.getSize().getHeight());
/* 202 */           w.setY(w.getY() + fd.top.getOffset());
/*     */           continue;
/*     */         } 
/* 205 */         sort(w, "");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     for (IWidget w : this.order) {
/*     */ 
/*     */       
/* 216 */       if (this.debug) System.out.println("processing in order : " + w);
/*     */ 
/*     */       
/* 219 */       FormData fd = (FormData)w.getLayoutData();
/* 220 */       if (fd == null)
/*     */         continue; 
/* 222 */       if (fd.left != null && !fd.left.isStatic()) {
/*     */         
/* 224 */         int rightSideOfAttachedWidget = fd.left.getAttachedWidget().getX() + fd.left.getAttachedWidget().getWidth() + fd.left.getOffset();
/*     */         
/* 226 */         if (fd.right == null) {
/* 227 */           w.setX(rightSideOfAttachedWidget);
/*     */         } else {
/*     */           
/* 230 */           setValidWidth(w, w.getX() + w.getSize().getWidth() - rightSideOfAttachedWidget);
/* 231 */           w.setX(rightSideOfAttachedWidget);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 236 */       if (fd.right != null && !fd.right.isStatic()) {
/*     */         
/* 238 */         int leftSideofAttachedWidget = fd.right.getAttachedWidget().getX() + fd.right.getOffset();
/*     */         
/* 240 */         if (fd.left == null) {
/* 241 */           w.setX(leftSideofAttachedWidget - w.getSize().getWidth());
/*     */         } else {
/*     */           
/* 244 */           setValidWidth(w, leftSideofAttachedWidget - w.getX());
/*     */         } 
/*     */       } 
/*     */       
/* 248 */       if (fd.bottom != null && !fd.bottom.isStatic()) {
/*     */         
/* 250 */         int topSideOfAttachedWidget = fd.bottom.getAttachedWidget().getY() + 
/* 251 */           fd.bottom.getAttachedWidget().getHeight() + fd.bottom.getOffset();
/*     */         
/* 253 */         if (fd.top == null) {
/*     */           
/* 255 */           w.setY(topSideOfAttachedWidget);
/* 256 */           if (this.debug) System.out.println("upSideOfAttachedWidget 1: " + w);
/*     */         
/*     */         } else {
/*     */           
/* 260 */           setValidHeight(w, w.getY() + w.getSize().getHeight() - topSideOfAttachedWidget);
/* 261 */           w.setY(topSideOfAttachedWidget);
/* 262 */           if (this.debug) System.out.println("upSideOfAttachedWidget 2: " + w);
/*     */         
/*     */         } 
/*     */       } 
/* 266 */       if (fd.top != null && !fd.top.isStatic()) {
/*     */         
/* 268 */         int bottomSideOfAttachedWidget = fd.top.getAttachedWidget().getY() + fd.top.getOffset();
/* 269 */         if (fd.bottom == null) {
/* 270 */           w.setY(bottomSideOfAttachedWidget - w.getSize().getHeight());
/*     */           continue;
/*     */         } 
/* 273 */         setValidHeight(w, bottomSideOfAttachedWidget - w.getY());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 279 */     this.sorted.clear();
/* 280 */     this.order.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension computeMinSize(Container container, List<IWidget> list) {
/* 289 */     int width = 0;
/* 290 */     int height = 0;
/*     */     
/* 292 */     for (IWidget w : list) {
/* 293 */       width = Math.max(getValidMinWidth(w), width);
/* 294 */       height = Math.max(getValidMinHeight(w), height);
/*     */     } 
/*     */     
/* 297 */     return new Dimension(width, height);
/*     */   }
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\FormLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */