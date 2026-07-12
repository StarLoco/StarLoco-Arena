/*     */ package org.fenggui.menu;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.DecoratorLayer;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
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
/*     */ public class MenuAppearance
/*     */   extends DecoratorAppearance
/*     */ {
/*  36 */   private Menu menu = null;
/*  37 */   private DecoratorLayer decoratorUnderlay = new DecoratorLayer(new org.fenggui.IDecorator[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private int cellHeight = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private Font font = Font.getDefaultFont();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private Color textColor = Color.BLACK;
/*     */   
/*  54 */   private Color textSelectionColor = Color.BLACK;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private Color disabledColor = Color.GRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuAppearance(Menu w) {
/*  64 */     super((IWidget)w);
/*  65 */     this.menu = w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  71 */     int minWidth = 0;
/*     */     
/*  73 */     for (MenuItem item : this.menu.getItems()) {
/*     */       
/*  75 */       int w = this.font.getWidth(item.getText()) + 10;
/*     */       
/*  77 */       if (minWidth < w)
/*     */       {
/*  79 */         minWidth = w;
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return new Dimension(minWidth + 10, this.cellHeight * this.menu.getItemCount());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/*  89 */     if (this.menu.getItemCount() == 0)
/*     */       return; 
/*  91 */     int y = getContentHeight() - this.cellHeight;
/*     */     
/*  93 */     g.setFont(this.font);
/*     */     
/*  95 */     for (int row = 0; row < this.menu.getItemCount(); row++) {
/*     */       
/*  97 */       MenuItem item = this.menu.getMenuItem(row);
/*     */       
/*  99 */       if (this.menu.getMouseOverRow() == row)
/*     */       {
/* 101 */         this.decoratorUnderlay.paint(g, 0, y - this.cellHeight / 7, getContentWidth(), this.cellHeight);
/*     */       }
/*     */       
/* 104 */       if (item.isEnabled()) {
/*     */         
/* 106 */         if (this.menu.getMouseOverRow() == row)
/* 107 */         { g.setColor(this.textSelectionColor); }
/* 108 */         else { g.setColor(this.textColor); }
/*     */       
/*     */       } else {
/* 111 */         g.setColor(this.disabledColor);
/*     */       } 
/* 113 */       if (item.menu != null) {
/*     */         
/* 115 */         int tx = getContentWidth();
/* 116 */         g.drawTriangle(tx - 5, y + 2, tx - 5, y + 12, tx - 2, y + 7, true);
/*     */       } 
/*     */       
/* 119 */       item.getTextRenderer().render(3, y, g, gl);
/*     */       
/* 121 */       y -= this.cellHeight;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getDisabledColor() {
/* 130 */     return this.disabledColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisabledColor(Color disabledColor) {
/* 135 */     this.disabledColor = disabledColor;
/*     */   }
/*     */   
/*     */   public Color getTextColor() {
/* 139 */     return this.textColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/* 144 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCellHeight() {
/* 150 */     return this.cellHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCellHeight(int cellHeight) {
/* 155 */     this.cellHeight = cellHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 160 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 165 */     this.font = font;
/*     */   }
/*     */ 
/*     */   
/*     */   public DecoratorLayer getSelectionUnderlay() {
/* 170 */     return this.decoratorUnderlay;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getTextSelectionColor() {
/* 175 */     return this.textSelectionColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextSelectionColor(Color textSelectionColor) {
/* 180 */     this.textSelectionColor = textSelectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 186 */     super.process(stream);
/*     */     
/* 188 */     this.cellHeight = stream.processAttribute("cellHeight", this.cellHeight, this.cellHeight);
/* 189 */     this.disabledColor = (Color)stream.processChild("DisabledTextColor", (IOStreamSaveable)this.disabledColor, (IOStreamSaveable)this.disabledColor, Color.class);
/* 190 */     this.textColor = (Color)stream.processChild("Color", (IOStreamSaveable)this.textColor, Color.class);
/* 191 */     this.textSelectionColor = (Color)stream.processChild("SelectionTextColor", (IOStreamSaveable)this.textSelectionColor, (IOStreamSaveable)this.textSelectionColor, Color.class);
/* 192 */     this.decoratorUnderlay = (DecoratorLayer)stream.processChild("SelectionUnderlay", (IOStreamSaveable)this.decoratorUnderlay, (IOStreamSaveable)this.decoratorUnderlay, DecoratorLayer.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\menu\MenuAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */