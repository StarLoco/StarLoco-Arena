/*     */ package org.fenggui.background;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.IDecorator;
/*     */ import org.fenggui.Span;
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
/*     */ public abstract class Background
/*     */   implements IOStreamSaveable, IDecorator
/*     */ {
/*  41 */   private String label = "default";
/*     */   private boolean enabled = true;
/*  43 */   private Span span = Span.PADDING;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  61 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Span getSpan() {
/*  67 */     return this.span;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpan(Span span) {
/*  73 */     this.span = span;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String label) {
/*  79 */     this.label = label;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  84 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel() {
/*  89 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/*  95 */     this.span = (Span)stream.processEnum("span", (Enum)this.span, (Enum)this.span, Span.class, Span.STORAGE_FORMAT);
/*  96 */     this.label = stream.processAttribute("label", this.label, "default");
/*  97 */     this.enabled = stream.processAttribute("enabled", this.enabled, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 102 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\background\Background.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */