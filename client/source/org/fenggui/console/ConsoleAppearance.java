/*     */ package org.fenggui.console;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.render.DirectTextRenderer;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.ICarretRenderer;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.render.ITextRenderer;
/*     */ import org.fenggui.render.LineCarretRenderer;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Timer;
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
/*     */ public class ConsoleAppearance
/*     */   extends DecoratorAppearance
/*     */ {
/*  38 */   private ITextRenderer textRenderer = (ITextRenderer)new DirectTextRenderer();
/*  39 */   private ITextRenderer promtRenderer = (ITextRenderer)new DirectTextRenderer();
/*  40 */   public static Font font = null;
/*  41 */   private ICarretRenderer carretRenderer = null;
/*  42 */   private Console widget = null;
/*  43 */   private Timer carretTimer = new Timer(2, 400L);
/*     */ 
/*     */   
/*     */   public ConsoleAppearance(Console w) {
/*  47 */     super((IWidget)w);
/*  48 */     this.widget = w;
/*     */     
/*     */     try {
/*  51 */       if (font == null) {
/*  52 */         font = new Font("data/fonts/Courier.png", "data/fonts/Courier.font");
/*     */       }
/*  54 */     } catch (FileNotFoundException e) {
/*     */       
/*  56 */       e.printStackTrace();
/*     */     }
/*  58 */     catch (IOException e) {
/*     */       
/*  60 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  63 */     this.textRenderer.setFont(font);
/*  64 */     this.promtRenderer.setFont(font);
/*  65 */     this.carretRenderer = (ICarretRenderer)new LineCarretRenderer(font.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/*  77 */     this.promtRenderer.render(0, 0, g, gl);
/*  78 */     if (this.carretTimer.getState() == 0 && this.widget.hasFocus())
/*  79 */       this.promtRenderer.renderCarret(0, 0, this.widget.getCarretIndex() - 1, this.carretRenderer, g, gl); 
/*  80 */     this.textRenderer.render(0, this.promtRenderer.getHeight(), g, gl);
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextRenderer getTextRenderer() {
/*  85 */     return this.textRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextRenderer getPromtRenderer() {
/*  90 */     return this.promtRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCarretRenderer(ICarretRenderer carretRenderer) {
/*  95 */     this.carretRenderer = carretRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public Console getWidget() {
/* 100 */     return this.widget;
/*     */   }
/*     */ 
/*     */   
/*     */   public Timer getCarretTimer() {
/* 105 */     return this.carretTimer;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\console\ConsoleAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */