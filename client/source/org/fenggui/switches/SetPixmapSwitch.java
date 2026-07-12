/*    */ package org.fenggui.switches;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fenggui.IPixmapWidget;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.Switch;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.InputOnlyStream;
/*    */ import org.fenggui.io.InputOutputStream;
/*    */ import org.fenggui.render.Pixmap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetPixmapSwitch
/*    */   extends Switch
/*    */ {
/* 40 */   private Pixmap pixmap = null;
/*    */ 
/*    */   
/*    */   public SetPixmapSwitch(InputOnlyStream stream) throws IOException, IOStreamException {
/* 44 */     super("not set yet");
/* 45 */     process((InputOutputStream)stream);
/*    */   }
/*    */ 
/*    */   
/*    */   public SetPixmapSwitch(String label, Pixmap pixmap) {
/* 50 */     super(label);
/* 51 */     this.pixmap = pixmap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setup(IWidget widget) {
/* 57 */     IPixmapWidget pw = (IPixmapWidget)widget;
/*    */     
/* 59 */     pw.setPixmap(this.pixmap);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 64 */     super.process(stream);
/*    */ 
/*    */ 
/*    */     
/* 68 */     if (this.pixmap == null) {
/* 69 */       this.pixmap = new Pixmap((InputOnlyStream)stream);
/*    */     } else {
/* 71 */       this.pixmap.process(stream);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\switches\SetPixmapSwitch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */