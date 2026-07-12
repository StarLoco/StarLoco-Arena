/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapPointClickEvent
/*    */   extends MouseClickEvent
/*    */ {
/* 12 */   Object m_target = null;
/*    */   
/*    */   public MapPointClickEvent(IComponent c, Object point, int x, int y, int clickCount, MouseButtons button) {
/* 15 */     super(c, x, y, clickCount, button);
/* 16 */     this.m_target = point;
/*    */   }
/*    */   
/*    */   public Object getTarget()
/*    */   {
/* 21 */     return this.m_target;
/*    */   }
/*    */   
/*    */   public void setTarget(Object target) {
/* 25 */     this.m_target = target;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\MapPointClickEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */