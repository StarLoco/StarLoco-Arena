/*     */ package org.fenggui.render;
/*     */ 
/*     */ import org.fenggui.util.CharacterPixmap;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Point;
/*     */ import org.fenggui.util.Rectangle;
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
/*     */ public class Graphics
/*     */ {
/*     */   private IOpenGL gl;
/*     */   private Font font;
/*  53 */   private final Rectangle clipSpace = new Rectangle(0, 0, 10000, 10000);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private final WritablePoint offset = new WritablePoint(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/*  67 */     return this.font;
/*     */   }
/*     */   
/*     */   public Point getTranslation() {
/*  71 */     return (Point)this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/*  82 */     this.font = font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Graphics(IOpenGL gl) {
/*  93 */     this.gl = gl;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClipSpace(int x, int y, int width, int height) {
/* 112 */     x += this.offset.getX();
/* 113 */     y += this.offset.getY();
/*     */ 
/*     */     
/* 116 */     this.clipSpace.set(x, y, width, height);
/*     */     
/* 118 */     this.gl.setScissor(x, width, y, height);
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
/*     */   
/*     */   public void drawFilledRectangle(int x, int y, int width, int height) {
/* 135 */     x += this.offset.getX();
/* 136 */     y += this.offset.getY();
/*     */     
/* 138 */     this.gl.startQuads();
/* 139 */     this.gl.vertex(x, y);
/* 140 */     this.gl.vertex((x + width), y);
/* 141 */     this.gl.vertex((x + width), (y + height));
/* 142 */     this.gl.vertex(x, (y + height));
/* 143 */     this.gl.end();
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
/*     */   public void drawFilledBevelRectangle(int x, int y, int width, int height, Color bright, Color dark, Color fill) {
/* 159 */     drawBevelRectangle(x, y, width, height, bright, dark);
/*     */     
/* 161 */     setColor(fill);
/*     */     
/* 163 */     drawFilledRectangle(x + 1, y + 1, width - 2, height - 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IOpenGL getOpenGL() {
/* 173 */     return this.gl;
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
/*     */ 
/*     */   
/*     */   public void drawString(String text, int x, int y) {
/* 191 */     if (text == null)
/*     */       return; 
/* 193 */     x += this.offset.getX();
/* 194 */     y += this.offset.getY();
/*     */     
/* 196 */     this.gl.enableTexture2D(true);
/*     */     
/* 198 */     CharacterPixmap pixmap = null;
/*     */     
/* 200 */     for (int i = 0; i < text.length(); i++) {
/*     */       
/* 202 */       pixmap = getFont().getCharPixMap(text.charAt(i));
/*     */       
/* 204 */       if (i == 0) {
/*     */         
/* 206 */         ITexture tex = pixmap.getTexture();
/*     */         
/* 208 */         if (tex.hasAlpha())
/*     */         {
/* 210 */           this.gl.setTexEnvModeModulate();
/*     */         }
/*     */         
/* 213 */         tex.bind();
/* 214 */         this.gl.startQuads();
/*     */       } 
/*     */       
/* 217 */       int imgWidth = pixmap.getWidth();
/* 218 */       int imgHeight = pixmap.getHeight();
/*     */       
/* 220 */       float endY = pixmap.getEndY();
/* 221 */       float endX = pixmap.getEndX();
/*     */       
/* 223 */       float startX = pixmap.getStartX();
/* 224 */       float startY = pixmap.getStartY();
/*     */       
/* 226 */       this.gl.texCoord(startX, endY);
/* 227 */       this.gl.vertex(x, y);
/*     */       
/* 229 */       this.gl.texCoord(startX, startY);
/* 230 */       this.gl.vertex(x, (imgHeight + y));
/*     */       
/* 232 */       this.gl.texCoord(endX, startY);
/* 233 */       this.gl.vertex((imgWidth + x), (imgHeight + y));
/*     */       
/* 235 */       this.gl.texCoord(endX, endY);
/* 236 */       this.gl.vertex((imgWidth + x), y);
/*     */       
/* 238 */       x += pixmap.getCharWidth();
/*     */     } 
/* 240 */     this.gl.end();
/*     */     
/* 242 */     this.gl.enableTexture2D(false);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRotatedString(String text, int x, int y, float angle) {
/* 261 */     x += this.offset.getX();
/* 262 */     y += this.offset.getY();
/* 263 */     this.gl.pushMatrix();
/* 264 */     this.gl.translateXY(x, y);
/* 265 */     this.gl.rotate(angle);
/* 266 */     drawString(text, -this.offset.getX(), -this.offset.getY());
/* 267 */     this.gl.popMatrix();
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
/*     */   public void drawScaledImage(Pixmap pixmap, int x, int y, int imgWidth, int imgHeight) {
/* 281 */     x += this.offset.getX();
/* 282 */     y += this.offset.getY();
/*     */     
/* 284 */     this.gl.enableTexture2D(true);
/*     */     
/* 286 */     ITexture tex = pixmap.getTexture();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (tex == null)
/*     */     {
/* 296 */       throw new NullPointerException("pixmap " + pixmap + " has no texture! pixmap.getTexture() == null");
/*     */     }
/*     */ 
/*     */     
/* 300 */     if (tex.hasAlpha())
/*     */     {
/* 302 */       this.gl.setTexEnvModeModulate();
/*     */     }
/* 304 */     tex.bind();
/*     */     
/* 306 */     this.gl.startQuads();
/*     */     
/* 308 */     float endY = pixmap.getEndY();
/* 309 */     float endX = pixmap.getEndX();
/*     */     
/* 311 */     float startX = pixmap.getStartX();
/* 312 */     float startY = pixmap.getStartY();
/*     */     
/* 314 */     this.gl.texCoord(startX, endY);
/* 315 */     this.gl.vertex(x, y);
/*     */     
/* 317 */     this.gl.texCoord(startX, startY);
/* 318 */     this.gl.vertex(x, (imgHeight + y));
/*     */     
/* 320 */     this.gl.texCoord(endX, startY);
/* 321 */     this.gl.vertex((imgWidth + x), (imgHeight + y));
/*     */     
/* 323 */     this.gl.texCoord(endX, endY);
/* 324 */     this.gl.vertex((imgWidth + x), y);
/* 325 */     this.gl.end();
/*     */     
/* 327 */     this.gl.enableTexture2D(false);
/*     */   }
/*     */   
/*     */   public void drawImage(Pixmap pixmap, int x, int y) {
/* 331 */     x += this.offset.getX();
/* 332 */     y += this.offset.getY();
/*     */     
/* 334 */     this.gl.enableTexture2D(true);
/*     */     
/* 336 */     ITexture tex = pixmap.getTexture();
/*     */     
/* 338 */     if (tex == null)
/*     */     {
/* 340 */       throw new NullPointerException("pixmap " + pixmap + " has no texture! pixmap.getTexture() == null");
/*     */     }
/*     */     
/* 343 */     if (tex.hasAlpha())
/*     */     {
/* 345 */       this.gl.setTexEnvModeModulate();
/*     */     }
/*     */     
/* 348 */     tex.bind();
/*     */     
/* 350 */     this.gl.startQuads();
/*     */     
/* 352 */     int imgWidth = pixmap.getWidth();
/* 353 */     int imgHeight = pixmap.getHeight();
/*     */     
/* 355 */     float endY = pixmap.getEndY();
/* 356 */     float endX = pixmap.getEndX();
/*     */     
/* 358 */     float startX = pixmap.getStartX();
/* 359 */     float startY = pixmap.getStartY();
/*     */     
/* 361 */     this.gl.texCoord(startX, endY);
/* 362 */     this.gl.vertex(x, y);
/*     */     
/* 364 */     this.gl.texCoord(startX, startY);
/* 365 */     this.gl.vertex(x, (imgHeight + y));
/*     */     
/* 367 */     this.gl.texCoord(endX, startY);
/* 368 */     this.gl.vertex((imgWidth + x), (imgHeight + y));
/*     */     
/* 370 */     this.gl.texCoord(endX, endY);
/* 371 */     this.gl.vertex((imgWidth + x), y);
/* 372 */     this.gl.end();
/*     */     
/* 374 */     this.gl.enableTexture2D(false);
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
/*     */   public void drawImage(ITexture tex, int x, int y) {
/* 390 */     x += this.offset.getX();
/* 391 */     y += this.offset.getY();
/*     */     
/* 393 */     this.gl.enableTexture2D(true);
/*     */     
/* 395 */     if (tex.hasAlpha())
/*     */     {
/* 397 */       this.gl.setTexEnvModeModulate();
/*     */     }
/*     */     
/* 400 */     tex.bind();
/*     */     
/* 402 */     this.gl.startQuads();
/*     */     
/* 404 */     int imgWidth = tex.getImageWidth();
/* 405 */     int imgHeight = tex.getImageHeight();
/*     */     
/* 407 */     float endY = imgHeight / tex.getTextureHeight();
/* 408 */     float endX = imgWidth / tex.getTextureWidth();
/* 409 */     float startX = 0.0F;
/* 410 */     float startY = 0.0F;
/*     */     
/* 412 */     this.gl.texCoord(startX, endY);
/* 413 */     this.gl.vertex(x, y);
/*     */     
/* 415 */     this.gl.texCoord(startX, startY);
/* 416 */     this.gl.vertex(x, (imgHeight + y));
/*     */     
/* 418 */     this.gl.texCoord(endX, startY);
/* 419 */     this.gl.vertex((imgWidth + x), (imgHeight + y));
/*     */     
/* 421 */     this.gl.texCoord(endX, endY);
/* 422 */     this.gl.vertex((imgWidth + x), y);
/* 423 */     this.gl.end();
/*     */     
/* 425 */     this.gl.enableTexture2D(false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScaledImage(ITexture tex, int x, int y, int width, int height) {
/* 448 */     x += this.offset.getX();
/* 449 */     y += this.offset.getY();
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
/* 463 */     this.gl.enableTexture2D(true);
/*     */     
/* 465 */     if (tex.hasAlpha())
/*     */     {
/* 467 */       this.gl.setTexEnvModeModulate();
/*     */     }
/*     */     
/* 470 */     tex.bind();
/*     */     
/* 472 */     this.gl.startQuads();
/*     */ 
/*     */     
/* 475 */     float startY = 0.0F;
/* 476 */     float startX = 0.0F;
/* 477 */     float endY = 1.0F;
/* 478 */     float endX = 1.0F;
/*     */     
/* 480 */     int rWidth = width;
/* 481 */     int rHeight = height;
/*     */ 
/*     */ 
/*     */     
/* 485 */     if (x < this.clipSpace.getX()) {
/*     */       
/* 487 */       rWidth -= this.clipSpace.getX() - x;
/* 488 */       startX = (this.clipSpace.getX() - x) / width;
/* 489 */       x = this.clipSpace.getX();
/*     */     } 
/*     */     
/* 492 */     if (x + rWidth > this.clipSpace.getX() + this.clipSpace.getWidth()) {
/*     */       
/* 494 */       rWidth = this.clipSpace.getX() + this.clipSpace.getWidth() - x;
/* 495 */       endX = rWidth / width;
/*     */     } 
/*     */     
/* 498 */     if (y < this.clipSpace.getY()) {
/*     */       
/* 500 */       rHeight -= this.clipSpace.getY() - y;
/* 501 */       endY = rHeight / height;
/* 502 */       y = this.clipSpace.getY();
/*     */     } 
/*     */     
/* 505 */     if (y + rHeight > this.clipSpace.getY() + this.clipSpace.getHeight()) {
/*     */       
/* 507 */       rHeight = this.clipSpace.getY() + this.clipSpace.getHeight() - y;
/* 508 */       startY = (height - rHeight) / height;
/*     */     } 
/*     */     
/* 511 */     this.gl.texCoord(startX, endY);
/* 512 */     this.gl.vertex(x, y);
/*     */     
/* 514 */     this.gl.texCoord(startX, startY);
/* 515 */     this.gl.vertex(x, (rHeight + y));
/*     */     
/* 517 */     this.gl.texCoord(endX, startY);
/* 518 */     this.gl.vertex((rWidth + x), (rHeight + y));
/*     */     
/* 520 */     this.gl.texCoord(endX, endY);
/* 521 */     this.gl.vertex((rWidth + x), y);
/* 522 */     this.gl.end();
/* 523 */     this.gl.enableTexture2D(false);
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
/*     */   public void setColor(Color c) {
/* 535 */     this.gl.color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
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
/*     */   public void setColor(float red, float green, float blue) {
/* 547 */     this.gl.color(red, green, blue, 1.0F);
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
/*     */   public void setColor(float red, float green, float blue, float alpha) {
/* 561 */     this.gl.color(red, green, blue, alpha);
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
/*     */   public void setLineWidth(float width) {
/* 574 */     if (width > 0.0F)
/*     */     {
/* 576 */       this.gl.lineWidth(width);
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
/*     */   public void setLineStipple(int stretch, short pattern) {
/* 588 */     this.gl.enableStipple();
/* 589 */     this.gl.lineStipple(stretch, pattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineStipple(boolean enable) {
/* 599 */     if (enable) {
/*     */       
/* 601 */       this.gl.enableStipple();
/*     */     }
/*     */     else {
/*     */       
/* 605 */       this.gl.disableStipple();
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
/*     */ 
/*     */   
/*     */   public void drawLine(int x1, int y1, int x2, int y2) {
/* 619 */     x1 += this.offset.getX();
/* 620 */     y1 += this.offset.getY();
/* 621 */     x2 += this.offset.getX();
/* 622 */     y2 += this.offset.getY();
/*     */     
/* 624 */     this.gl.startLines();
/* 625 */     this.gl.vertex(x1, y1);
/* 626 */     this.gl.vertex(x2, y2);
/* 627 */     this.gl.end();
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
/*     */   public void translate(int x, int y) {
/* 639 */     this.offset.translate(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTransformations() {
/* 647 */     this.offset.setXY(0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getClipSpace() {
/* 657 */     return this.clipSpace;
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
/*     */   public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, boolean filled) {
/* 672 */     x1 += this.offset.getX();
/* 673 */     y1 += this.offset.getY();
/*     */     
/* 675 */     x2 += this.offset.getX();
/* 676 */     y2 += this.offset.getY();
/*     */     
/* 678 */     x3 += this.offset.getX();
/* 679 */     y3 += this.offset.getY();
/*     */     
/* 681 */     this.gl.startTriangles();
/* 682 */     this.gl.vertex(x3, y3);
/* 683 */     this.gl.vertex(x2, y2);
/* 684 */     this.gl.vertex(x1, y1);
/* 685 */     this.gl.end();
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
/*     */   
/*     */   public void drawWireRectangle(int x, int y, int width, int height) {
/* 702 */     drawLine(x, y, x + width - 1, y);
/* 703 */     drawLine(x, y, x, y + height - 1);
/* 704 */     drawLine(x + width - 1, y, x + width - 1, y + height - 1);
/* 705 */     drawLine(x, y + height - 1, x + width, y + height - 1);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBlendedFilledRect(int x, int y, int width, int height, Color c1, Color c2, Color c3, Color c4) {
/* 724 */     x += this.offset.getX();
/* 725 */     y += this.offset.getY();
/*     */     
/* 727 */     this.gl.startQuads();
/*     */ 
/*     */     
/* 730 */     this.gl.color(c1.getRed(), c1.getGreen(), c1.getBlue(), c1.getAlpha());
/* 731 */     this.gl.vertex(x, y);
/*     */ 
/*     */     
/* 734 */     this.gl.color(c2.getRed(), c2.getGreen(), c2.getBlue(), c2.getAlpha());
/* 735 */     this.gl.vertex((x + width), y);
/*     */ 
/*     */     
/* 738 */     this.gl.color(c3.getRed(), c3.getGreen(), c3.getBlue(), c3.getAlpha());
/* 739 */     this.gl.vertex((x + width), (y + height));
/*     */ 
/*     */     
/* 742 */     this.gl.color(c4.getRed(), c4.getGreen(), c4.getBlue(), c4.getAlpha());
/* 743 */     this.gl.vertex(x, (y + height));
/* 744 */     this.gl.end();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBevelRectangle(int x, int y, int width, int height, Color bright, Color dark) {
/* 766 */     x += this.offset.getX();
/* 767 */     y += this.offset.getY();
/*     */     
/* 769 */     setColor(bright);
/*     */ 
/*     */     
/* 772 */     this.gl.startLines();
/* 773 */     this.gl.vertex(x, y);
/* 774 */     this.gl.vertex((x + width), y);
/*     */     
/* 776 */     this.gl.vertex((x + width), y);
/* 777 */     this.gl.vertex((x + width), (y + height));
/* 778 */     setColor(dark);
/* 779 */     this.gl.vertex((x + width + 1), (y + height));
/* 780 */     this.gl.vertex(x, (y + height));
/* 781 */     this.gl.vertex(x, (y + height));
/* 782 */     this.gl.vertex(x, y);
/* 783 */     this.gl.end();
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
/*     */   public void drawBevelCircle(int x, int y, double radius, Color light, Color dark) {
/* 799 */     double d1 = 6.283185307179586D;
/* 800 */     x += this.offset.getX();
/* 801 */     y += this.offset.getY();
/*     */ 
/*     */     
/* 804 */     setColor(light);
/*     */     
/* 806 */     this.gl.startLineLoop();
/*     */ 
/*     */     
/* 809 */     this.gl.vertex(((int)radius + x), (0 + y));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 815 */     for (double d = 0.0D; d <= 6.283185307179586D; d += 0.3490658503988659D)
/*     */     {
/* 817 */       this.gl.vertex((int)(Math.cos(d) * radius + x + 0.5D), (int)(Math.sin(d) * radius + y + 0.5D));
/*     */     }
/* 819 */     this.gl.end();
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
/*     */   public void drawPixel(int x, int y) {}
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
/*     */   public void drawRoundedCorner(int x, int y, double a1, double a2, double radius, int resolution) {
/* 845 */     if (a2 < a1) {
/*     */       
/* 847 */       double t = a1;
/* 848 */       a1 = a2;
/* 849 */       a2 = t;
/*     */     } 
/*     */     
/* 852 */     double step = a2 - a1 / resolution;
/*     */     
/* 854 */     x += this.offset.getX();
/* 855 */     y += this.offset.getY();
/*     */     
/* 857 */     this.gl.startLineLoop();
/*     */     
/* 859 */     for (double d = a1; d <= a2; d += step)
/*     */     {
/* 861 */       this.gl.vertex((int)(Math.cos(d) * radius + x + 0.5D), (int)(Math.sin(d) * radius + y + 0.5D));
/*     */     }
/* 863 */     this.gl.end();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRoundedRectangle(int x, int y, int width, int height, int radius) {
/* 883 */     double top = 4.71238898038469D;
/* 884 */     double right = 0.0D;
/* 885 */     double bottom = 1.5707963267948966D;
/* 886 */     double left = Math.PI;
/*     */     
/* 888 */     drawRoundedCorner(x + radius, y + radius, left, top, radius, 4);
/* 889 */     drawLine(x + radius, y, x + width - radius - 1, y);
/* 890 */     drawRoundedCorner(x + radius, y + radius, top, right, radius, 4);
/* 891 */     drawLine(x + width - 1, y + radius, x + width - 1, y + height - radius - 1);
/* 892 */     drawRoundedCorner(x + radius, y + radius, right, bottom, radius, 4);
/* 893 */     drawLine(x + width - radius - 1, y + height - 1, x + radius, y + height - 1);
/* 894 */     drawRoundedCorner(x + radius, y + radius, bottom, left, radius, 4);
/* 895 */     drawLine(x, y + height - radius - 1, x, y + radius);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\Graphics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */