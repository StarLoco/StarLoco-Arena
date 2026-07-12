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
/*     */ 
/*     */ 
/*     */ public class ObservableLabelWidget
/*     */   extends ObservableWidget
/*     */   implements ILabel
/*     */ {
/*  39 */   private Pixmap pixmap = null;
/*  40 */   private LabelAppearance appearance = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public ObservableLabelWidget() {
/*  45 */     this.appearance = new LabelAppearance(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public LabelAppearance getAppearance() {
/*  50 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/*  58 */     return getAppearance().getTextRenderer().getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/*  66 */     getAppearance().getTextRenderer().setText(text);
/*  67 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getPixmap() {
/*  73 */     return this.pixmap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap pixmap) {
/*  78 */     this.pixmap = pixmap;
/*     */     
/*  80 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/*  86 */     setMinSize(getAppearance().getMinSizeHint());
/*     */     
/*  88 */     if (getParent() != null) getParent().updateMinSize();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/*  94 */     super.process(stream);
/*     */     
/*  96 */     setText(stream.processAttribute("text", getText(), getText()));
/*     */     
/*  98 */     if (stream.isInputStream())
/*     */     {
/* 100 */       setPixmap((Pixmap)stream.processChild("Pixmap", (IOStreamSaveable)this.pixmap, null, Pixmap.class));
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ObservableLabelWidget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */