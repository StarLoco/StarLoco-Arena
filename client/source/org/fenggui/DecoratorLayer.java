/*    */ package org.fenggui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.IOStreamSaveable;
/*    */ import org.fenggui.io.InputOnlyStream;
/*    */ import org.fenggui.io.InputOutputStream;
/*    */ import org.fenggui.render.Graphics;
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
/*    */ public class DecoratorLayer
/*    */   implements IOStreamSaveable
/*    */ {
/* 40 */   private ArrayList<IDecorator> decorators = new ArrayList<IDecorator>();
/*    */ 
/*    */   
/*    */   public DecoratorLayer(InputOnlyStream stream) throws IOException, IOStreamException {
/* 44 */     process((InputOutputStream)stream);
/*    */   }
/*    */ 
/*    */   
/*    */   public DecoratorLayer(IDecorator d) {
/* 49 */     this.decorators.add(d);
/*    */   } public DecoratorLayer(IDecorator... array) {
/*    */     byte b;
/*    */     int i;
/*    */     IDecorator[] arrayOfIDecorator;
/* 54 */     for (arrayOfIDecorator = array, b = 0, i = arrayOfIDecorator.length; b < i; ) { IDecorator d = arrayOfIDecorator[b]; this.decorators.add(d); b++; }
/*    */   
/*    */   }
/*    */   
/*    */   public DecoratorLayer(List<IDecorator> list) {
/* 59 */     this.decorators.addAll(list);
/*    */   }
/*    */ 
/*    */   
/*    */   public void paint(Graphics g, int x, int y, int width, int height) {
/* 64 */     for (IDecorator deco : this.decorators) {
/*    */       
/* 66 */       if (!deco.isEnabled())
/*    */         continue; 
/* 68 */       deco.paint(g, x, y, width, height);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 74 */     stream.processChildren(this.decorators, FengGUI.TYPE_REGISTRY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(IDecorator d) {
/* 79 */     this.decorators.add(d);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 84 */     this.decorators.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUniqueName() {
/* 91 */     return "--generate-name--";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\DecoratorLayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */