/*    */ package org.fenggui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.IOStreamSaveable;
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
/*    */ 
/*    */ 
/*    */ public class Item
/*    */   implements IOStreamSaveable
/*    */ {
/* 39 */   private String text = null;
/* 40 */   private Pixmap pixmap = null;
/*    */ 
/*    */   
/*    */   public Item(String text, Pixmap pixmap) {
/* 44 */     this.text = text;
/* 45 */     this.pixmap = pixmap;
/*    */   }
/*    */   
/*    */   public Item(String text) {
/* 49 */     this(text, null);
/*    */   }
/*    */   
/*    */   public String getText() {
/* 53 */     return this.text;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 57 */     this.text = text;
/*    */   }
/*    */ 
/*    */   
/*    */   public Pixmap getPixmap() {
/* 62 */     return this.pixmap;
/*    */   }
/*    */   
/*    */   public void setPixmap(Pixmap pixmap) {
/* 66 */     this.pixmap = pixmap;
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 71 */     this.text = stream.processAttribute("text", this.text);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUniqueName() {
/* 78 */     return "--generate-name--";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Item.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */