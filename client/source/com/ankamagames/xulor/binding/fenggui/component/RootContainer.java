/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import org.fenggui.Display;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RootContainer
/*    */   extends Container
/*    */ {
/*    */   private RootContainerLevel m_level;
/*    */   
/*    */   public enum RootContainerLevel
/*    */   {
/* 19 */     BOTTOM, TOP, MSGBOX;
/*    */     
/*    */     public boolean isGreaterOrEqualThan(RootContainerLevel level) {
/* 22 */       switch (level) {
/*    */         case null:
/* 24 */           if (equals(BOTTOM)) {
/* 25 */             return true;
/*    */           }
/*    */         case TOP:
/* 28 */           if (equals(TOP)) {
/* 29 */             return true;
/*    */           }
/*    */         case MSGBOX:
/* 32 */           if (equals(MSGBOX)) {
/* 33 */             return true;
/*    */           }
/*    */           break;
/*    */       } 
/* 37 */       return false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RootContainer(RootContainerLevel level) {
/* 46 */     setNonBlocking(true);
/* 47 */     this.m_level = level;
/*    */   }
/*    */   
/*    */   public RootContainerLevel getLevel() {
/* 51 */     return this.m_level;
/*    */   }
/*    */   
/*    */   public void setLevel(RootContainerLevel level) {
/* 55 */     this.m_level = level;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void layout() {
/* 65 */     Display display = getDisplay();
/* 66 */     if (display != null) {
/* 67 */       int width = display.getWidth();
/* 68 */       int height = display.getHeight();
/* 69 */       setSize(width, height);
/*    */     } 
/* 71 */     super.layout();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\RootContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */