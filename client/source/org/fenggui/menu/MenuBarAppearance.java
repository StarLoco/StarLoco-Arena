/*     */ package org.fenggui.menu;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.DecoratorLayer;
/*     */ import org.fenggui.FengGUI;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.background.Background;
/*     */ import org.fenggui.background.PlainBackground;
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
/*     */ 
/*     */ 
/*     */ public class MenuBarAppearance
/*     */   implements IAppearance, IOStreamSaveable
/*     */ {
/*  40 */   private Font font = Font.getDefaultFont();
/*  41 */   private Color textColor = Color.BLACK;
/*  42 */   private int GAP = 10;
/*  43 */   private MenuBar menuBar = null;
/*  44 */   private Background background = (Background)new PlainBackground(Color.LIGHT_GRAY);
/*  45 */   private DecoratorLayer selectionUnderlay = new DecoratorLayer(new org.fenggui.IDecorator[0]);
/*  46 */   private Color selectionTextColor = Color.BLACK;
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuBarAppearance(MenuBar bar) {
/*  51 */     this.menuBar = bar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Background getBackground() {
/*  58 */     return this.background;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, IOpenGL gl) {
/*  66 */     if (this.background != null) this.background.paint(g, 0, 0, this.menuBar.getWidth(), this.menuBar.getHeight()); 
/*  67 */     int x = 0;
/*     */     
/*  69 */     g.setFont(this.font);
/*     */ 
/*     */     
/*  72 */     for (MenuBarItem item : this.menuBar.getMenuBarItems()) {
/*     */       
/*  74 */       int itemWidth = item.getWidth();
/*     */       
/*  76 */       g.setColor(this.textColor);
/*     */       
/*  78 */       if (item.equals(this.menuBar.getMouseOver())) {
/*     */         
/*  80 */         this.selectionUnderlay.paint(g, x, 0, itemWidth + this.GAP, this.font.getHeight());
/*     */         
/*  82 */         g.setColor(this.selectionTextColor);
/*     */       } 
/*     */       
/*  85 */       item.getTextRenderer().render(x + this.GAP / 2, 0, g, gl);
/*     */ 
/*     */ 
/*     */       
/*  89 */       x += itemWidth + this.GAP;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getSelectionTextColor() {
/*  98 */     return this.selectionTextColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionTextColor(Color selectionTextColor) {
/* 105 */     this.selectionTextColor = selectionTextColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinSizeHint() {
/* 112 */     int sum = 0;
/*     */     
/* 114 */     for (MenuBarItem item : this.menuBar.getMenuBarItems()) {
/*     */       
/* 116 */       int itemWidth = this.font.getWidth(item.getName());
/* 117 */       sum += itemWidth + this.GAP / 2;
/*     */     } 
/*     */     
/* 120 */     return new Dimension(sum, this.font.getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public DecoratorLayer getSelectionUnderlay() {
/* 125 */     return this.selectionUnderlay;
/*     */   }
/*     */   
/*     */   public Font getFont() {
/* 129 */     return this.font;
/*     */   }
/*     */   
/*     */   public void setFont(Font font) {
/* 133 */     this.font = font;
/*     */   }
/*     */   
/*     */   public Color getTextColor() {
/* 137 */     return this.textColor;
/*     */   }
/*     */   
/*     */   public void setTextColor(Color textColor) {
/* 141 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackground(Background background) {
/* 148 */     this.background = background;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGap() {
/* 155 */     return this.GAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 161 */     this.selectionTextColor = (Color)stream.processChild("SelectionTextColor", (IOStreamSaveable)this.selectionTextColor, (IOStreamSaveable)Color.BLACK, Color.class);
/* 162 */     this.background = (Background)stream.processChild((IOStreamSaveable)this.background, FengGUI.TYPE_REGISTRY);
/* 163 */     this.selectionUnderlay = (DecoratorLayer)stream.processChild("SelectionUnderlay", (IOStreamSaveable)this.selectionUnderlay, (IOStreamSaveable)this.selectionUnderlay, DecoratorLayer.class);
/* 164 */     this.textColor = (Color)stream.processChild("Color", (IOStreamSaveable)this.textColor, Color.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 173 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\menu\MenuBarAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */