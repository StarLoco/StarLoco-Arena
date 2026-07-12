/*    */ package org.fenggui.event;
/*    */ 
/*    */ import org.fenggui.IWidget;
/*    */ import org.fenggui.Slider;
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
/*    */ public class SliderMovedEvent
/*    */   extends Event
/*    */ {
/*    */   private Slider slider;
/*    */   
/*    */   public SliderMovedEvent(Slider slider) {
/* 35 */     super((IWidget)slider);
/* 36 */     this.slider = slider;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPosition() {
/* 41 */     return this.slider.getValue();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\SliderMovedEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */