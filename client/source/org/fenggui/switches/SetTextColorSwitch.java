/*    */ package org.fenggui.switches;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fenggui.FengGUI;
/*    */ import org.fenggui.ITextAppearance;
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.LabelAppearance;
/*    */ import org.fenggui.StandardWidget;
/*    */ import org.fenggui.Switch;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.IOStreamSaveable;
/*    */ import org.fenggui.io.InputOutputStream;
/*    */ import org.fenggui.util.Color;
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
/*    */ public class SetTextColorSwitch
/*    */   extends Switch
/*    */ {
/* 36 */   Color c = null;
/* 37 */   LabelAppearance t = null;
/*    */ 
/*    */   
/*    */   public SetTextColorSwitch(String label, Color colorToSet) {
/* 41 */     super(label);
/* 42 */     this.c = colorToSet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setup(IWidget w) {
/* 49 */     ((ITextAppearance)((StandardWidget)w).getAppearance()).setTextColor(this.c);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 55 */     super.process(stream);
/*    */     
/* 57 */     this.c = (Color)stream.processChild((IOStreamSaveable)this.c, FengGUI.TYPE_REGISTRY);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\switches\SetTextColorSwitch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */