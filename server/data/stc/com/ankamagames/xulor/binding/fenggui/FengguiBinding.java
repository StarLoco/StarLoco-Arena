/*     */ package com.ankamagames.xulor.binding.fenggui;
/*     */ 
/*     */ import com.ankamagames.xulor.core.ConverterLibrary;
/*     */ import com.ankamagames.xulor.core.EnvironmentWidgetCleaner;
/*     */ import com.ankamagames.xulor.core.TagLibrary;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.util.Cursor.CursorType;
/*     */ import com.ankamagames.xulor.util.ThemeTexture;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import org.fenggui.render.CursorFactory;
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
/*     */ public class FengguiBinding
/*     */   implements com.ankamagames.xulor.binding.Binding
/*     */ {
/*  27 */   private static FengguiBinding m_instance = new FengguiBinding();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FengguiBinding getInstance()
/*     */   {
/*  39 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConverterLibrary getConverterLibrary()
/*     */   {
/*  48 */     return FengguiConverterLibrary.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TagLibrary getTagLibrary()
/*     */   {
/*  57 */     return FengguiTagLibrary.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnvironmentWidgetCleaner getEnvironmentWidgetCleaner(HashMap<Object, IElement> elementMap)
/*     */   {
/*  64 */     return new FengguiEnvironmentWidgetCleaner(elementMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void loadCursors(Collection<com.ankamagames.xulor.util.Cursor> cursors)
/*     */   {
/*  71 */     if (cursors == null) {
/*  72 */       return;
/*     */     }
/*     */     try
/*     */     {
/*  76 */       binding = org.fenggui.render.Binding.getInstance();
/*     */     } catch (IllegalStateException e) { org.fenggui.render.Binding binding;
/*     */       return;
/*     */     }
/*     */     org.fenggui.render.Binding binding;
/*  81 */     CursorFactory factory = binding.getCursorFactory();
/*  82 */     if (factory == null) {
/*  83 */       return;
/*     */     }
/*     */     
/*  86 */     for (com.ankamagames.xulor.util.Cursor cursor : cursors) {
/*  87 */       org.fenggui.render.Cursor fengguiCursor = null;
/*  88 */       if (cursor.getType().equals(Cursor.CursorType.DEFAULT)) {
/*  89 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/*  90 */         factory.setDefaultCursor(fengguiCursor);
/*  91 */         if (fengguiCursor != null) {
/*  92 */           fengguiCursor.show();
/*     */         }
/*  94 */       } else if (cursor.getType().equals(Cursor.CursorType.FORBIDDEN)) {
/*  95 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/*  96 */         factory.setForbiddenCursor(fengguiCursor);
/*  97 */       } else if (cursor.getType().equals(Cursor.CursorType.HAND)) {
/*  98 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/*  99 */         factory.setHandCursor(fengguiCursor);
/* 100 */       } else if (cursor.getType().equals(Cursor.CursorType.HORIZONTAL_RESIZE)) {
/* 101 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 102 */         factory.setHorizontalResizeCursor(fengguiCursor);
/* 103 */       } else if (cursor.getType().equals(Cursor.CursorType.VERTICAL_RESIZE)) {
/* 104 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 105 */         factory.setVerticalResizeCursor(fengguiCursor);
/* 106 */       } else if (cursor.getType().equals(Cursor.CursorType.MOVE)) {
/* 107 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 108 */         factory.setMoveCursor(fengguiCursor);
/* 109 */       } else if (cursor.getType().equals(Cursor.CursorType.NW_RESIZE)) {
/* 110 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 111 */         factory.setNWResizeCursor(fengguiCursor);
/* 112 */       } else if (cursor.getType().equals(Cursor.CursorType.SW_RESIZE)) {
/* 113 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 114 */         factory.setSWResizeCursor(fengguiCursor);
/* 115 */       } else if (cursor.getType().equals(Cursor.CursorType.TEXT)) {
/* 116 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 117 */         factory.setTextCursor(fengguiCursor);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiBinding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */