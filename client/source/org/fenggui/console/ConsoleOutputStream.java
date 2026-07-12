/*    */ package org.fenggui.console;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import org.fenggui.render.ITextRenderer;
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
/*    */ public class ConsoleOutputStream
/*    */   extends OutputStream
/*    */ {
/* 29 */   private ITextRenderer textRenderer = null;
/*    */ 
/*    */   
/*    */   public ConsoleOutputStream(ITextRenderer textRenderer) {
/* 33 */     this.textRenderer = textRenderer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 39 */     this.textRenderer.setText(String.valueOf(this.textRenderer.getText()) + new String(new int[] { b }, 0, 1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 45 */     super.close();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {
/* 51 */     super.flush();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 57 */     super.write(b, off, len);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(byte[] b) throws IOException {
/* 63 */     super.write(b);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\console\ConsoleOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */