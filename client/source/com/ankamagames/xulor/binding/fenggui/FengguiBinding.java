/*     */ package com.ankamagames.xulor.binding.fenggui;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.Binding;
/*     */ import com.ankamagames.xulor.core.ConverterLibrary;
/*     */ import com.ankamagames.xulor.core.EnvironmentWidgetCleaner;
/*     */ import com.ankamagames.xulor.core.TagLibrary;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.util.Cursor;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.Cursor;
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
/*     */ public class FengguiBinding
/*     */   implements Binding
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
/*     */   
/*     */   public static FengguiBinding getInstance() {
/*  39 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConverterLibrary getConverterLibrary() {
/*  48 */     return FengguiConverterLibrary.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagLibrary getTagLibrary() {
/*  57 */     return (TagLibrary)FengguiTagLibrary.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnvironmentWidgetCleaner getEnvironmentWidgetCleaner(HashMap<Object, IElement> elementMap) {
/*  64 */     return new FengguiEnvironmentWidgetCleaner(elementMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCursors(Collection<Cursor> cursors) {
/*     */     Binding binding;
/*  71 */     if (cursors == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  76 */       binding = Binding.getInstance();
/*  77 */     } catch (IllegalStateException e) {
/*     */       return;
/*     */     } 
/*     */     
/*  81 */     CursorFactory factory = binding.getCursorFactory();
/*  82 */     if (factory == null) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     for (Cursor cursor : cursors) {
/*  87 */       Cursor fengguiCursor = null;
/*  88 */       if (cursor.getType().equals(Cursor.CursorType.DEFAULT)) {
/*  89 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/*  90 */         factory.setDefaultCursor(fengguiCursor);
/*  91 */         if (fengguiCursor != null)
/*  92 */           fengguiCursor.show();  continue;
/*     */       } 
/*  94 */       if (cursor.getType().equals(Cursor.CursorType.FORBIDDEN)) {
/*  95 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/*  96 */         factory.setForbiddenCursor(fengguiCursor); continue;
/*  97 */       }  if (cursor.getType().equals(Cursor.CursorType.HAND)) {
/*  98 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/*  99 */         factory.setHandCursor(fengguiCursor); continue;
/* 100 */       }  if (cursor.getType().equals(Cursor.CursorType.HORIZONTAL_RESIZE)) {
/* 101 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 102 */         factory.setHorizontalResizeCursor(fengguiCursor); continue;
/* 103 */       }  if (cursor.getType().equals(Cursor.CursorType.VERTICAL_RESIZE)) {
/* 104 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 105 */         factory.setVerticalResizeCursor(fengguiCursor); continue;
/* 106 */       }  if (cursor.getType().equals(Cursor.CursorType.MOVE)) {
/* 107 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 108 */         factory.setMoveCursor(fengguiCursor); continue;
/* 109 */       }  if (cursor.getType().equals(Cursor.CursorType.NW_RESIZE)) {
/* 110 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 111 */         factory.setNWResizeCursor(fengguiCursor); continue;
/* 112 */       }  if (cursor.getType().equals(Cursor.CursorType.SW_RESIZE)) {
/* 113 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 114 */         factory.setSWResizeCursor(fengguiCursor); continue;
/* 115 */       }  if (cursor.getType().equals(Cursor.CursorType.TEXT)) {
/* 116 */         fengguiCursor = factory.createCursor(cursor.getHotspotX(), cursor.getHotspotY(), cursor.getTexture().getImage());
/* 117 */         factory.setTextCursor(fengguiCursor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiBinding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */