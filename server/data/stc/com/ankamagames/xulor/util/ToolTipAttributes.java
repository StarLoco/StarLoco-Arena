/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.text.GLTextArea;
/*    */ import com.ankamagames.graphics.isometric.text.AbstractTesselBackground;
/*    */ import com.ankamagames.graphics.isometric.text.BackgroundedText.BackgroundedTextHotPointPosition;
/*    */ import java.awt.Font;
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
/*    */ public class ToolTipAttributes
/*    */ {
/* 20 */   public String TEXT = null;
/* 21 */   public Integer X_OFFSET = null;
/* 22 */   public Integer Y_OFFSET = null;
/* 23 */   public Integer DURATION = null;
/* 24 */   public Integer MAX_WIDTH = null;
/* 25 */   public Color TEXT_COLOR = new Color(GLTextArea.DEFAULT_TEXT_COLOR);
/* 26 */   public Color BACKGROUND_COLOR = new Color(AbstractTesselBackground.DEFAULT_BACKGROUND_COLOR);
/* 27 */   public Color BORDER_COLOR = new Color(AbstractTesselBackground.DEFAULT_BORDER_COLOR);
/* 28 */   public Font FONT = null;
/* 29 */   public BackgroundedText.BackgroundedTextHotPointPosition HOT_POINT_POSITION = null;
/* 30 */   public Alignment POSITION = null;
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\ToolTipAttributes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */