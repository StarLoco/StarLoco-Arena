/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Pixmap;
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
/*     */ public class Label
/*     */   extends StandardWidget
/*     */   implements ILabel
/*     */ {
/*  37 */   private Pixmap pixmap = null;
/*  38 */   private LabelAppearance appearance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label(String text) {
/*  46 */     initializeAppearance();
/*  47 */     setupTheme(Label.class);
/*  48 */     setText(text);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAppearance(LabelAppearance appearance) {
/*  53 */     this.appearance = appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeAppearance() {
/*  61 */     this.appearance = new LabelAppearance(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LabelAppearance getAppearance() {
/*  68 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getPixmap() {
/*  74 */     return this.pixmap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap pixmap) {
/*  80 */     this.pixmap = pixmap;
/*  81 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label() {
/*  91 */     this((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 100 */     return getAppearance().getTextRenderer().getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 109 */     getAppearance().getTextRenderer().setText(text);
/* 110 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 116 */     super.process(stream);
/*     */     
/* 118 */     setText(stream.processAttribute("text", getText(), getText()));
/*     */     
/* 120 */     if (stream.isInputStream())
/* 121 */       this.pixmap = (Pixmap)stream.processChild("Pixmap", (IOStreamSaveable)this.pixmap, null, Pixmap.class); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Label.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */