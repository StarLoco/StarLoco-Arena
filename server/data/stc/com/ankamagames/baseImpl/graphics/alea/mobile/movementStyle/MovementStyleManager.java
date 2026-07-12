/*    */ package com.ankamagames.baseImpl.graphics.alea.mobile.movementStyle;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.util.HashMap;
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
/*    */ public class MovementStyleManager
/*    */ {
/* 19 */   public static String WALK_STYLE = "WALK";
/* 20 */   public static String RUN_STYLE = "RUN";
/* 21 */   public static String SLIDE_STYLE = "SLIDE";
/* 22 */   public static String SWIMM_STYLE = "SWIM";
/* 23 */   public static String WALK_CARRY_STYLE = "WALK_CARRY";
/*    */   
/* 25 */   private static MovementStyleManager m_instance = new MovementStyleManager();
/*    */   
/*    */ 
/*    */ 
/*    */   public static MovementStyleManager getInstance()
/*    */   {
/* 31 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private MovementStyleManager()
/*    */   {
/* 38 */     registerStyle(WALK_STYLE, WalkMovementStyle.class);
/* 39 */     registerStyle(RUN_STYLE, RunMovementStyle.class);
/* 40 */     registerStyle(SLIDE_STYLE, SlideMovementStyle.class);
/* 41 */     registerStyle(WALK_CARRY_STYLE, WalkCarryMovementStyle.class);
/*    */   }
/*    */   
/* 44 */   private HashMap<String, Constructor<? extends PathMovementStyle>> m_styles = new HashMap();
/*    */   
/*    */   public void registerStyle(String key, Class<? extends PathMovementStyle> style) {
/*    */     try {
/* 48 */       this.m_styles.put(key, style.getConstructor(new Class[0]));
/*    */     } catch (NoSuchMethodException e) {
/* 50 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   public PathMovementStyle getMovementStyle(String key)
/*    */   {
/* 56 */     Constructor<? extends PathMovementStyle> constructor = (Constructor)this.m_styles.get(key);
/*    */     
/* 58 */     if (constructor != null) {
/*    */       try {
/* 60 */         return (PathMovementStyle)constructor.newInstance(new Object[0]);
/*    */       } catch (InstantiationException e) {
/* 62 */         e.printStackTrace();
/*    */       } catch (IllegalAccessException e) {
/* 64 */         e.printStackTrace();
/*    */       } catch (InvocationTargetException e) {
/* 66 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */     
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\movementStyle\MovementStyleManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */