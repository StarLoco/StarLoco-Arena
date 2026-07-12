/*     */ package org.fenggui.render.jogl;
/*     */ 
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.mouse.MouseButton;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventHelper
/*     */ {
/*     */   public static MouseButton getMouseButton(MouseEvent me) {
/*  42 */     switch (me.getButton()) {
/*     */       case 1:
/*  44 */         return MouseButton.LEFT;
/*     */       case 2:
/*  46 */         return MouseButton.MIDDLE;
/*     */       case 3:
/*  48 */         return MouseButton.RIGHT;
/*     */       case 507:
/*  50 */         return MouseButton.WHEEL;
/*     */     } 
/*  52 */     return MouseButton.LEFT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Key getKeyPressed(KeyEvent ke) {
/*     */     Key keyClass;
/*  60 */     switch (ke.getKeyCode())
/*     */     
/*     */     { case 8:
/*  63 */         keyClass = Key.BACKSPACE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         return keyClass;case 10: keyClass = Key.ENTER; return keyClass;case 27: keyClass = Key.ESCAPE; return keyClass;case 127: keyClass = Key.DELETE; return keyClass;case 38: keyClass = Key.UP; return keyClass;case 39: keyClass = Key.RIGHT; return keyClass;case 37: keyClass = Key.LEFT; return keyClass;case 40: keyClass = Key.DOWN; return keyClass;case 35: keyClass = Key.END; return keyClass;case 36: keyClass = Key.HOME; return keyClass;case 16: keyClass = Key.SHIFT; return keyClass;case 18: keyClass = Key.ALT; return keyClass;case 17: keyClass = Key.CTRL; return keyClass;case 155: keyClass = Key.INSERT; return keyClass;case 9: keyClass = Key.TAB; return keyClass;case 123: keyClass = Key.F12; return keyClass;case 122: keyClass = Key.F11; return keyClass;case 121: keyClass = Key.F10; return keyClass;case 120: keyClass = Key.F9; return keyClass;case 119: keyClass = Key.F8; return keyClass;case 118: keyClass = Key.F7; return keyClass;case 117: keyClass = Key.F6; return keyClass;case 116: keyClass = Key.F5; return keyClass;case 115: keyClass = Key.F4; return keyClass;case 114: keyClass = Key.F3; return keyClass;case 113: keyClass = Key.F2; return keyClass;case 112: keyClass = Key.F1; return keyClass; }  if ("1234567890".indexOf(ke.getKeyChar()) != -1) { keyClass = Key.DIGIT; } else { keyClass = Key.LETTER; }  return keyClass;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\jogl\EventHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */