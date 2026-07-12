/*    */ package org.fenggui.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fenggui.io.IOStreamException;
/*    */ import org.fenggui.io.InputOutputStream;
/*    */ import org.fenggui.io.MalformedElementException;
/*    */ import org.fenggui.render.ITexture;
/*    */ import org.fenggui.render.Pixmap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharacterPixmap
/*    */   extends Pixmap
/*    */ {
/*    */   private static final int DEFAULT_CHAR_WIDTH = 10;
/*    */   private static final String ATTR_CHARACTER = "char";
/*    */   private static final String ATTR_CHAR_WIDTH = "char-width";
/*    */   private char character;
/* 47 */   private int charWidth = 10;
/*    */   
/*    */   public CharacterPixmap(ITexture texture, int x, int y, int width, int height, char c, int charWidth) {
/* 50 */     super(texture, x, y, width, height);
/* 51 */     this.charWidth = charWidth;
/* 52 */     this.character = c;
/*    */   }
/*    */   
/*    */   public char getCharacter() {
/* 56 */     return this.character;
/*    */   }
/*    */   
/*    */   public int getCharWidth() {
/* 60 */     return this.charWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public void toXML(String blankOffset, StringBuilder buffer) {
/* 65 */     buffer.append(blankOffset);
/* 66 */     buffer.append("<CharacterPixmap");
/*    */     
/* 68 */     buffer.append(" x=\"" + getX() + "\"");
/* 69 */     buffer.append(" y=\"" + getY() + "\"");
/*    */     
/* 71 */     buffer.append(" width=\"" + getWidth() + "\"");
/* 72 */     buffer.append(" height=\"" + getHeight() + "\"");
/*    */     
/* 74 */     buffer.append(" charWidth=\"" + getCharWidth() + "\">\n");
/*    */     
/* 76 */     buffer.append(String.valueOf(blankOffset) + "   ");
/* 77 */     buffer.append("<character><![CDATA[" + this.character);
/* 78 */     buffer.append("]]></character>\n");
/* 79 */     buffer.append(String.valueOf(blankOffset) + "</CharacterPixmap>\n\n");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 87 */     super.process(stream);
/* 88 */     String charStr = stream.processAttribute("char", (new StringBuilder(String.valueOf(this.character))).toString());
/*    */     
/* 90 */     if (charStr.length() != 1) throw MalformedElementException.createDefaultMalformedAttributeException("char", "a single character");
/*    */     
/* 92 */     this.character = charStr.charAt(0);
/* 93 */     this.charWidth = stream.processAttribute("char-width", this.charWidth, 10);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\CharacterPixmap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */