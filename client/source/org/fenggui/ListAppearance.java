/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.layout.Alignment;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListAppearance
/*     */   extends DecoratorAppearance
/*     */ {
/*  43 */   private Font font = Font.getDefaultFont();
/*  44 */   private int rowHeight = this.font.getHeight();
/*  45 */   private Color textColor = Color.BLACK;
/*  46 */   private Alignment alignment = Alignment.LEFT;
/*  47 */   private DecoratorLayer selectionUnderlay = new DecoratorLayer(new IDecorator[0]);
/*  48 */   private DecoratorLayer mouseHoverUnderlay = new DecoratorLayer(new IDecorator[0]);
/*     */   
/*  50 */   private List list = null;
/*     */ 
/*     */   
/*     */   public ListAppearance(List w, InputOnlyStream stream) throws IOException, IOStreamException {
/*  54 */     super(w, stream);
/*  55 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListAppearance(List w) {
/*  60 */     super(w);
/*  61 */     this.list = w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  67 */     int minWidth = 100;
/*     */     
/*  69 */     if (this.list.isEmpty()) return new Dimension(0, 0);
/*     */     
/*  71 */     return new Dimension(minWidth, this.list.size() * this.rowHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/*  77 */     if (this.list.isEmpty()) {
/*     */       return;
/*     */     }
/*  80 */     int lowerClipBound = g.getClipSpace().getY() - this.rowHeight;
/*  81 */     int upperClipBound = g.getClipSpace().getY() + g.getClipSpace().getHeight();
/*     */     
/*  83 */     int lowerContentBound = this.list.getDisplayY();
/*  84 */     int upperContentBound = lowerContentBound + this.rowHeight * this.list.size();
/*     */ 
/*     */     
/*  87 */     int row = (upperContentBound - upperClipBound) / this.rowHeight;
/*  88 */     if (row < 0)
/*     */     {
/*  90 */       row = 0;
/*     */     }
/*     */     
/*  93 */     if (row > this.list.size()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  98 */     g.setFont(this.font);
/*     */     
/* 100 */     int y = getContentHeight() - (row + 1) * this.rowHeight;
/* 101 */     while (y + lowerContentBound > lowerClipBound && row < this.list.size()) {
/*     */       
/* 103 */       ListItem<?> item = this.list.getItem(row);
/*     */       
/* 105 */       if (item.isSelected()) {
/*     */         
/* 107 */         this.selectionUnderlay.paint(g, 0, y, getContentWidth(), this.rowHeight);
/*     */       }
/* 109 */       else if (this.list.getMouseOverRow() == row) {
/*     */         
/* 111 */         this.mouseHoverUnderlay.paint(g, 0, y, getContentWidth(), this.rowHeight);
/*     */       } 
/*     */       
/* 114 */       g.setColor(this.textColor);
/* 115 */       String s = item.getText();
/*     */ 
/*     */       
/* 118 */       int alignedX = this.alignment.alignX(getContentWidth(), this.font.getWidth(s));
/* 119 */       int alignedY = this.alignment.alignY(this.rowHeight, this.font.getHeight());
/*     */       
/* 121 */       g.drawString(s, alignedX, y + alignedY);
/*     */       
/* 123 */       row++;
/* 124 */       y -= this.rowHeight;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DecoratorLayer getSelectionUnderlay() {
/* 131 */     return this.selectionUnderlay;
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 136 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 141 */     this.font = font;
/* 142 */     this.rowHeight = font.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/* 147 */     return this.textColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/* 152 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowHeight() {
/* 158 */     return this.rowHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRowHeight(int rowHeight) {
/* 163 */     this.rowHeight = rowHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 169 */     super.process(stream);
/*     */     
/* 171 */     this.selectionUnderlay = (DecoratorLayer)stream.processChild("SelectionUnderlay", this.selectionUnderlay, new DecoratorLayer(new IDecorator[0]), DecoratorLayer.class);
/* 172 */     this.mouseHoverUnderlay = (DecoratorLayer)stream.processChild("MouseHoverUnderlay", this.mouseHoverUnderlay, new DecoratorLayer(new IDecorator[0]), DecoratorLayer.class);
/*     */     
/* 174 */     this.textColor = (Color)stream.processChild("Color", (IOStreamSaveable)this.textColor, (IOStreamSaveable)Color.BLACK, Color.class);
/*     */     
/* 176 */     if (stream.isInputStream()) {
/* 177 */       this.font = (Font)stream.processChild("Font", (IOStreamSaveable)this.font, (IOStreamSaveable)Font.getDefaultFont(), Font.class);
/*     */     }
/* 179 */     this.rowHeight = stream.processAttribute("rowHeight", this.rowHeight, this.font.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecoratorLayer getMouseHoverUnderlay() {
/* 186 */     return this.mouseHoverUnderlay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Alignment getAlignment() {
/* 194 */     return this.alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlignment(Alignment alignment) {
/* 202 */     this.alignment = alignment;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ListAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */