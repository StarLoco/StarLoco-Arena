/*     */ package org.fenggui.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.CharacterPixmap;
/*     */ import org.fenggui.util.Rectangle;
/*     */ import org.fenggui.util.WritableDimension;
/*     */ import org.fenggui.util.WritablePoint;
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
/*     */ public class TextRun
/*     */ {
/*     */   private char[] chars;
/*     */   private TextStyle style;
/*  45 */   private Rectangle boundingRect = new Rectangle(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Substring> substrings;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean newLineFixed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextRun(char[] chars, TextStyle style) {
/*  59 */     this.chars = chars;
/*  60 */     this.style = style;
/*     */     
/*  62 */     this.substrings = new ArrayList<Substring>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextRun(String text, TextStyle style) {
/*  74 */     this(text.toCharArray(), style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBoundingRect() {
/*  83 */     return this.boundingRect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getChars() {
/*  92 */     return this.chars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 101 */     return this.style.getFont();
/*     */   }
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
/*     */   public boolean contains(int x, int y) {
/* 115 */     if (this.boundingRect.contains(x, y)) {
/*     */       
/* 117 */       Substring target = null;
/* 118 */       for (Substring substring : this.substrings) {
/*     */         
/* 120 */         if (x >= substring.xOff && y >= substring.yOff)
/*     */         {
/* 122 */           target = substring;
/*     */         }
/*     */       } 
/*     */       
/* 126 */       if (target != null && x < target.xOff + getFont().getWidth(this.chars, target.begin, target.end)) return true;
/*     */     
/*     */     } 
/* 129 */     return false;
/*     */   }
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
/*     */   void paint(Graphics g, int xOff, int yOff) {
/* 142 */     IOpenGL gl = g.getOpenGL();
/* 143 */     Font font = getFont();
/*     */     
/* 145 */     if (this.chars.length == 0) {
/*     */       return;
/*     */     }
/* 148 */     font.getCharPixMap('a').getTexture().bind();
/*     */     
/* 150 */     gl.enableTexture2D(true);
/* 151 */     gl.setTexEnvModeModulate();
/* 152 */     g.setColor(this.style.getColor());
/* 153 */     gl.startQuads();
/*     */     
/* 155 */     for (Substring substring : this.substrings) {
/*     */       
/* 157 */       int begin = substring.begin;
/* 158 */       int end = substring.end;
/*     */       
/* 160 */       int x = xOff + substring.xOff;
/* 161 */       int y = yOff + substring.yOff;
/*     */ 
/*     */       
/* 164 */       for (int i = begin; i < end; i++) {
/*     */         
/* 166 */         char character = this.chars[i];
/*     */         
/* 168 */         CharacterPixmap charMap = font.getCharPixMap(character);
/*     */         
/* 170 */         int imgWidth = charMap.getWidth();
/* 171 */         int imgHeight = charMap.getHeight();
/* 172 */         float endY = charMap.getEndY();
/* 173 */         float endX = charMap.getEndX();
/* 174 */         float startX = charMap.getStartX();
/* 175 */         float startY = charMap.getStartY();
/*     */         
/* 177 */         gl.texCoord(startX, endY);
/* 178 */         gl.vertex(x, y);
/*     */         
/* 180 */         gl.texCoord(startX, startY);
/* 181 */         gl.vertex(x, (imgHeight + y));
/*     */         
/* 183 */         gl.texCoord(endX, startY);
/* 184 */         gl.vertex((imgWidth + x), (imgHeight + y));
/*     */         
/* 186 */         gl.texCoord(endX, endY);
/* 187 */         gl.vertex((imgWidth + x), y);
/*     */         
/* 189 */         x += charMap.getCharWidth();
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     gl.end();
/* 194 */     gl.enableTexture2D(false);
/*     */   }
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
/*     */   void prepare(int xMax, WritableDimension scratchDimension, WritablePoint point) {
/* 210 */     Font font = getFont();
/*     */     
/* 212 */     this.substrings.clear();
/*     */     
/* 214 */     int x = point.getX();
/* 215 */     int y = point.getY() - font.getHeight();
/* 216 */     if (xMax <= 0)
/*     */     {
/* 218 */       xMax = 600;
/*     */     }
/*     */     
/* 221 */     int xOff = x;
/* 222 */     int yOff = y;
/*     */     
/* 224 */     int height = Math.max(scratchDimension.getHeight(), -yOff);
/*     */     
/* 226 */     int begin = 0;
/* 227 */     int len = this.chars.length;
/* 228 */     int end = 0;
/*     */     
/* 230 */     int width = 0;
/*     */     
/*     */     do {
/* 233 */       if (this.chars[begin] == '\n') {
/*     */         
/* 235 */         begin++;
/* 236 */         if (!this.newLineFixed) {
/*     */           
/* 238 */           height += font.getHeight();
/* 239 */           yOff = -height;
/* 240 */           xOff = 0;
/*     */         }
/*     */         else {
/*     */           
/* 244 */           this.newLineFixed = false;
/*     */         } 
/* 246 */         if (begin == this.chars.length) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 251 */       end = findEnd(begin, xOff, xMax);
/* 252 */       width = xOff + font.getWidth(this.chars, begin, end);
/*     */       
/* 254 */       this.substrings.add(new Substring(begin, end, xOff, yOff));
/*     */       
/* 256 */       xOff = 0;
/* 257 */       if (end == len)
/*     */         continue; 
/* 259 */       height += font.getHeight();
/* 260 */       yOff = -height;
/* 261 */       begin = end;
/*     */     }
/* 263 */     while (end != len);
/*     */     
/* 265 */     scratchDimension.setWidth(xMax);
/* 266 */     scratchDimension.setHeight(Math.max(height, scratchDimension.getHeight()));
/*     */     
/* 268 */     this.boundingRect.setX(0);
/* 269 */     this.boundingRect.setY(-scratchDimension.getHeight());
/* 270 */     this.boundingRect.setWidth(scratchDimension.getWidth());
/* 271 */     this.boundingRect.setHeight(point.getY() + scratchDimension.getHeight());
/*     */     
/* 273 */     point.setX(width);
/* 274 */     point.setY(yOff + font.getHeight());
/*     */   }
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
/*     */   private int findEnd(int begin, int xOff, int xMax) {
/* 289 */     Font font = getFont();
/* 290 */     int end = begin;
/* 291 */     int width = xOff;
/* 292 */     while (end != this.chars.length) {
/*     */       
/* 294 */       char endChar = this.chars[end];
/*     */ 
/*     */       
/* 297 */       int endCharWidth = font.getWidth(endChar);
/*     */       
/* 299 */       if (width + endCharWidth <= xMax && this.chars[end] != '\n') {
/*     */         
/* 301 */         width += endCharWidth;
/* 302 */         end++;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/*     */     
/* 310 */     if (end == this.chars.length) return end;
/*     */     
/* 312 */     if (this.chars[end] == '\n') {
/*     */       
/* 314 */       this.newLineFixed = true;
/* 315 */       return end;
/*     */     } 
/*     */ 
/*     */     
/* 319 */     int oldEnd = end;
/* 320 */     while (end > begin) {
/*     */       
/* 322 */       if (this.chars[end - 1] == ' ')
/*     */       {
/* 324 */         return end;
/*     */       }
/*     */ 
/*     */       
/* 328 */       end--;
/*     */     } 
/*     */ 
/*     */     
/* 332 */     int newEnd = (xOff == 0) ? oldEnd : end;
/*     */ 
/*     */     
/* 335 */     return (newEnd != begin) ? newEnd : (newEnd + 1);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\text\TextRun.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */