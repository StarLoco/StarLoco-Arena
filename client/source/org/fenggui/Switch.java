/*    */ package org.fenggui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.IOStreamSaveable;
/*    */ import org.fenggui.io.InputOutputStream;
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
/*    */ public abstract class Switch
/*    */   implements IOStreamSaveable
/*    */ {
/* 30 */   private String label = "default";
/*    */   
/*    */   private boolean enabled = false;
/*    */   
/*    */   public Switch(String label) {
/* 35 */     this.label = label;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLabel() {
/* 40 */     return this.label;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 45 */     return this.enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 50 */     this.enabled = enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void setup(IWidget paramIWidget);
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 57 */     this.label = stream.processAttribute("label", this.label, this.label);
/*    */   }
/*    */   
/*    */   public String getUniqueName() {
/* 61 */     return "--generate-name--";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Switch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */