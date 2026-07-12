/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.event.IPaintListener;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.BufferedTextRenderer;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.render.ITextRenderer;
/*     */ import org.fenggui.util.CharacterPixmap;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Timer;
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
/*     */ public class TextEditorAppearance
/*     */   extends DecoratorAppearance
/*     */   implements ITextAppearance
/*     */ {
/*  40 */   private TextEditor editor = null;
/*  41 */   private Color textColor = Color.BLACK;
/*  42 */   private Color selectionColor = Color.BLUE;
/*  43 */   private Font font = Font.getDefaultFont();
/*  44 */   private ITextRenderer textRenderer = (ITextRenderer)new BufferedTextRenderer();
/*     */   
/*  46 */   private TextCursorPainter cursorPainter = new TextCursorPainter();
/*     */   
/*     */   protected boolean useBufferedTextRenderer = false;
/*     */ 
/*     */   
/*     */   public TextEditorAppearance(TextEditor w) {
/*  52 */     super(w);
/*  53 */     this.editor = w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  59 */     int width = 0;
/*  60 */     int height = 0;
/*     */     
/*  62 */     String[] lines = this.editor.getText().split("\n");
/*     */     
/*  64 */     if (!this.editor.isMultiline()) {
/*     */       
/*  66 */       height = this.font.getHeight();
/*  67 */       if (lines.length > 0) { width = this.font.getWidth(lines[0]); }
/*  68 */       else { width = 20; }
/*     */     
/*     */     } else {
/*     */       
/*  72 */       height = lines.length * this.font.getHeight(); byte b;
/*     */       int i;
/*     */       String[] arrayOfString;
/*  75 */       for (arrayOfString = lines, b = 0, i = arrayOfString.length; b < i; ) { String s = arrayOfString[b];
/*     */         
/*  77 */         width = Math.max(width, this.font.getWidth(s));
/*     */         b++; }
/*     */     
/*     */     } 
/*  81 */     return new Dimension(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/*  87 */     if (this.useBufferedTextRenderer) {
/*     */       
/*  89 */       this.textRenderer.render(0, 0, g, gl);
/*     */     }
/*     */     else {
/*     */       
/*  93 */       String text = this.editor.getText();
/*  94 */       TextEditor.Selection selection = this.editor.getSelection();
/*     */ 
/*     */       
/*  97 */       if ((text == null || text.length() == 0) && !this.editor.isInWritingState())
/*     */         return; 
/*  99 */       int fontHeight = this.font.getHeight();
/*     */ 
/*     */       
/* 102 */       int y = getContentHeight() - fontHeight;
/* 103 */       int x = 0;
/*     */       
/* 105 */       x += g.getTranslation().getX();
/* 106 */       y += g.getTranslation().getY();
/*     */ 
/*     */       
/* 109 */       if ((text == null || text.length() == 0) && this.editor.isInWritingState()) {
/*     */         
/* 111 */         g.setColor(this.textColor);
/* 112 */         paintCursor(g, x, y);
/*     */         
/*     */         return;
/*     */       } 
/* 116 */       CharacterPixmap charMap = null;
/*     */       
/* 118 */       gl.enableTexture2D(true);
/*     */ 
/*     */       
/* 121 */       this.font.getCharPixMap(text.charAt(0)).getTexture().bind();
/*     */       
/* 123 */       gl.setTexEnvModeModulate();
/* 124 */       g.setColor(this.textColor);
/*     */       
/* 126 */       gl.startQuads();
/*     */ 
/*     */       
/* 129 */       for (int charIndex = 0; charIndex < text.length(); charIndex++) {
/*     */         
/* 131 */         char character = text.charAt(charIndex);
/*     */ 
/*     */         
/* 134 */         if (charIndex == selection.startIndex && selection.startIndex < selection.endIndex) {
/*     */           
/* 136 */           int tmpX = x;
/* 137 */           int tmpY = y;
/*     */           
/* 139 */           gl.end();
/* 140 */           gl.enableTexture2D(false);
/*     */ 
/*     */           
/* 143 */           gl.startQuads();
/* 144 */           g.setColor(this.selectionColor);
/* 145 */           gl.vertex(tmpX, tmpY);
/* 146 */           gl.vertex(tmpX, (tmpY + fontHeight));
/*     */ 
/*     */           
/* 149 */           for (int k = charIndex; k < selection.endIndex && k < text.length(); k++) {
/*     */             
/* 151 */             char tmpChar = text.charAt(k);
/*     */             
/* 153 */             if (tmpChar == '\n')
/*     */             
/* 155 */             { gl.vertex((g.getTranslation().getX() + getContentWidth()), (tmpY + fontHeight));
/* 156 */               gl.vertex((g.getTranslation().getX() + getContentWidth()), tmpY);
/*     */               
/* 158 */               tmpY -= fontHeight;
/* 159 */               tmpX = g.getTranslation().getX();
/*     */               
/* 161 */               gl.vertex(0.0F, tmpY);
/* 162 */               gl.vertex(0.0F, (tmpY + fontHeight));
/*     */               
/*     */                }
/*     */             
/* 166 */             else if (this.editor.isPasswordField()) { tmpX += this.font.getCharPixMap('*').getCharWidth(); }
/* 167 */             else { tmpX += this.font.getCharPixMap(tmpChar).getCharWidth(); }
/*     */           
/*     */           } 
/*     */           
/* 171 */           gl.vertex(tmpX, (tmpY + fontHeight));
/* 172 */           gl.vertex(tmpX, tmpY);
/*     */ 
/*     */           
/* 175 */           gl.end();
/* 176 */           gl.enableTexture2D(true);
/* 177 */           gl.startQuads();
/* 178 */           g.setColor(Color.WHITE);
/*     */         } 
/*     */         
/* 181 */         if (charIndex == this.editor.getCursorIndex() && this.editor.isInWritingState()) {
/*     */           
/* 183 */           gl.end();
/* 184 */           paintCursor(g, x, y);
/* 185 */           gl.enableTexture2D(true);
/* 186 */           gl.startQuads();
/*     */         } 
/*     */         
/* 189 */         if (character == '\n') {
/*     */ 
/*     */           
/* 192 */           if (!this.editor.isMultiline()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 197 */           x = g.getTranslation().getX();
/* 198 */           y -= fontHeight;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 203 */           if (charIndex == selection.endIndex)
/*     */           {
/* 205 */             g.setColor(this.textColor);
/*     */           }
/*     */           
/* 208 */           if (this.editor.isPasswordField()) { charMap = this.font.getCharPixMap('*'); }
/* 209 */           else { charMap = this.font.getCharPixMap(character); }
/*     */           
/* 211 */           int imgWidth = charMap.getWidth();
/* 212 */           int imgHeight = charMap.getHeight();
/* 213 */           float endY = charMap.getEndY();
/* 214 */           float endX = charMap.getEndX();
/* 215 */           float startX = charMap.getStartX();
/* 216 */           float startY = charMap.getStartY();
/*     */ 
/*     */ 
/*     */           
/* 220 */           if (x - getLeftMargins() - this.editor.getDisplayX() + imgWidth < getContentWidth()) {
/*     */             
/* 222 */             gl.texCoord(startX, endY);
/* 223 */             gl.vertex(x, y);
/*     */             
/* 225 */             gl.texCoord(startX, startY);
/* 226 */             gl.vertex(x, (imgHeight + y));
/*     */             
/* 228 */             gl.texCoord(endX, startY);
/* 229 */             gl.vertex((imgWidth + x), (imgHeight + y));
/*     */             
/* 231 */             gl.texCoord(endX, endY);
/* 232 */             gl.vertex((imgWidth + x), y);
/*     */           } 
/*     */           
/* 235 */           x += charMap.getCharWidth();
/*     */         } 
/*     */       } 
/* 238 */       gl.end();
/* 239 */       gl.enableTexture2D(false);
/*     */ 
/*     */ 
/*     */       
/* 243 */       if (this.editor.getCursorIndex() == text.length() && this.editor.isInWritingState())
/*     */       {
/* 245 */         paintCursor(g, x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 253 */     return this.font;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 259 */     this.font = font;
/* 260 */     if (this.cursorPainter != null) this.cursorPainter.setHeight(font.getHeight()); 
/* 261 */     this.editor.updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/* 269 */     return this.textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/* 280 */     this.textColor = textColor;
/*     */   }
/*     */   
/*     */   public class TextCursorPainter
/*     */     implements IPaintListener
/*     */   {
/* 286 */     private Timer timer = new Timer(2, 500L); private int x;
/*     */     private int y;
/* 288 */     private int height = 17;
/*     */     
/* 290 */     private Color cursorColor = Color.BLACK;
/*     */ 
/*     */ 
/*     */     
/*     */     public int getX() {
/* 295 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setX(int x) {
/* 301 */       this.x = x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getY() {
/* 307 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setY(int y) {
/* 313 */       this.y = y;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Color getCursorColor() {
/* 319 */       return this.cursorColor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCursorColor(Color cursorColor) {
/* 325 */       this.cursorColor = cursorColor;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void resetTimer() {
/* 331 */       this.timer.reset();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics g) {
/* 337 */       if (this.timer.getState() == 0) {
/*     */ 
/*     */         
/* 340 */         g.setColor(this.cursorColor);
/* 341 */         g.drawLine(this.x, this.y, this.x, this.y + this.height);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 353 */       return this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setHeight(int height) {
/* 362 */       this.height = height;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPainter(TextCursorPainter cursorPainter) {
/* 374 */     this.cursorPainter = cursorPainter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextCursorPainter getCursorPainter() {
/* 383 */     return this.cursorPainter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintCursor(Graphics g, int x, int y) {
/* 389 */     if (x - getLeftMargins() - this.editor.getDisplayX() > getContentWidth()) {
/*     */       return;
/*     */     }
/* 392 */     IOpenGL gl = g.getOpenGL();
/* 393 */     gl.enableTexture2D(false);
/* 394 */     this.cursorPainter.setX(x - g.getTranslation().getX());
/* 395 */     this.cursorPainter.setY(y - g.getTranslation().getY());
/* 396 */     this.cursorPainter.paint(g);
/*     */     
/* 398 */     TextEditor.Selection selection = this.editor.getSelection();
/*     */     
/* 400 */     if (this.editor.getCursorIndex() == selection.startIndex && this.editor.getCursorIndex() != selection.endIndex) {
/*     */       
/* 402 */       g.setColor(Color.WHITE);
/*     */     }
/*     */     else {
/*     */       
/* 406 */       g.setColor(this.textColor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 413 */     super.process(stream);
/*     */     
/* 415 */     this.textColor = (Color)stream.processChild("Color", (IOStreamSaveable)this.textColor, (IOStreamSaveable)Color.BLACK, Color.class);
/*     */     
/* 417 */     if (stream.isInputStream()) {
/* 418 */       this.font = (Font)stream.processChild("Font", (IOStreamSaveable)this.font, (IOStreamSaveable)Font.getDefaultFont(), Font.class);
/*     */     }
/*     */   }
/*     */   
/*     */   public ITextRenderer getTextRenderer() {
/* 423 */     return this.textRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextRenderer(ITextRenderer textRendered) {
/* 428 */     this.textRenderer = textRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getSelectionColor() {
/* 436 */     return this.selectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionColor(Color selectionColor) {
/* 444 */     this.selectionColor = selectionColor;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\TextEditorAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */