/*     */ package org.fenggui.border;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.IDecorator;
/*     */ import org.fenggui.Span;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.util.Spacing;
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
/*     */ public abstract class Border
/*     */   extends Spacing
/*     */   implements IDecorator
/*     */ {
/*  40 */   private String label = "default";
/*     */   private boolean enabled = true;
/*  42 */   private Span span = Span.BORDER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Span getSpan() {
/*  59 */     return this.span;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpan(Span span) {
/*  64 */     this.span = span;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  69 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String label) {
/*  75 */     this.label = label;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  81 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel() {
/*  86 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Border(int top, int left, int right, int bottom) {
/*  92 */     super(top, left, right, bottom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/*  99 */     super.process(stream);
/*     */     
/* 101 */     this.label = stream.processAttribute("label", this.label, "default");
/* 102 */     this.enabled = stream.processAttribute("enabled", this.enabled, true);
/* 103 */     this.span = (Span)stream.processEnum("span", (Enum)this.span, (Enum)Span.BORDER, Span.class, Span.STORAGE_FORMAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public String getUniqueName() {
/* 110 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\border\Border.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */