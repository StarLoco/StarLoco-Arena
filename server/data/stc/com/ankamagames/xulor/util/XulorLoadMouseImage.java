/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XulorLoadMouseImage
/*    */   implements XulorLoadUnload
/*    */ {
/* 16 */   public URL URL = null;
/* 17 */   public int XOFFSET = 0;
/* 18 */   public int YOFFSET = 0;
/* 19 */   public Alignment HOTPOINT = Alignment.SOUTH_EAST;
/*    */   
/*    */   public XulorLoadMouseImage(URL url, int xOffset, int yOffset, Alignment hotpoint) {
/* 22 */     this.URL = url;
/* 23 */     this.XOFFSET = xOffset;
/* 24 */     this.YOFFSET = yOffset;
/* 25 */     this.HOTPOINT = hotpoint;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\XulorLoadMouseImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */