/*     */ package com.ankamagames.framework.graphics.painting.brushes;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class BaseBrush
/*     */ {
/*     */   protected boolean[][] m_datas;
/*     */   protected int m_sizeX;
/*     */   protected int m_sizeY;
/*     */   protected int m_centerX;
/*     */   
/*     */   public static enum ResizeStyle {
/*  13 */     CLAMP, 
/*  14 */     REPEAT, 
/*  15 */     MIRROR, 
/*  16 */     SCALE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int m_centerY;
/*     */   
/*     */ 
/*     */   protected boolean m_optimized;
/*     */   
/*     */ 
/*     */   static final char CHAR_TRUE = 'X';
/*     */   
/*     */ 
/*     */   static final char CHAR_FALSE = '-';
/*     */   
/*     */ 
/*     */   static final char CHAR_EMPTY = ' ';
/*     */   
/*     */ 
/*     */   protected BaseBrush() {}
/*     */   
/*     */ 
/*     */   protected BaseBrush(int radius)
/*     */   {
/*  41 */     create(radius);
/*     */   }
/*     */   
/*  44 */   protected BaseBrush(int radiusX, int radiusY) { create(radiusX, radiusY); }
/*     */   
/*     */   protected BaseBrush(boolean[][] pattern) {
/*  47 */     this.m_sizeX = pattern.length;
/*  48 */     if (this.m_sizeX > 0) {
/*  49 */       this.m_sizeY = pattern[0].length;
/*  50 */       this.m_datas = pattern;
/*     */     }
/*     */   }
/*     */   
/*  54 */   public BaseBrush(int radiusX, int radiusY, List<int[]> coords) { create(radiusX, radiusY);
/*  55 */     setDatas(coords);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void create(int radius)
/*     */   {
/*  63 */     this.m_sizeX = (2 * radius + 1);
/*  64 */     this.m_sizeY = (2 * radius + 1);
/*     */     
/*  66 */     this.m_centerX = radius;
/*  67 */     this.m_centerY = radius;
/*     */     
/*  69 */     this.m_datas = new boolean[this.m_sizeX][this.m_sizeY];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void create(int radiusX, int radiusY)
/*     */   {
/*  77 */     this.m_sizeX = (2 * radiusX + 1);
/*  78 */     this.m_sizeY = (2 * radiusY + 1);
/*     */     
/*  80 */     this.m_centerX = radiusX;
/*  81 */     this.m_centerY = radiusY;
/*     */     
/*  83 */     this.m_datas = new boolean[this.m_sizeX][this.m_sizeY];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void copyFrom(BaseBrush brush)
/*     */   {
/*  92 */     this.m_datas = ((boolean[][])brush.m_datas.clone());
/*  93 */     for (int i = 0; i < this.m_sizeX; i++) {
/*  94 */       this.m_datas[i] = ((boolean[])brush.m_datas[i].clone());
/*     */     }
/*  96 */     this.m_sizeX = brush.m_sizeX;
/*  97 */     this.m_sizeY = brush.m_sizeY;
/*  98 */     this.m_centerX = brush.m_centerX;
/*  99 */     this.m_centerY = brush.m_centerY;
/* 100 */     this.m_optimized = brush.m_optimized;
/*     */   }
/*     */   
/*     */   public int getCenterX()
/*     */   {
/* 105 */     return this.m_centerX;
/*     */   }
/*     */   
/* 108 */   public int getCenterY() { return this.m_centerY; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void center()
/*     */   {
/* 116 */     this.m_centerX = (this.m_sizeX / 2);
/* 117 */     this.m_centerY = (this.m_sizeY / 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCenter(int centerX, int centerY)
/*     */   {
/* 126 */     this.m_centerX = centerX;
/* 127 */     this.m_centerY = centerY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getData(int x, int y)
/*     */   {
/* 138 */     return this.m_datas[x][y];
/*     */   }
/*     */   
/*     */   public void setDatas(boolean[][] datas) {
/* 142 */     this.m_datas = datas;
/*     */     
/* 144 */     this.m_optimized = false;
/*     */   }
/*     */   
/*     */   public void setDatas(List<int[]> coords)
/*     */   {
/* 149 */     for (int[] coord : coords) {
/* 150 */       this.m_datas[(coord[0] + this.m_sizeX)][(coord[1] + this.m_sizeY)] = 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSizeX()
/*     */   {
/* 158 */     return this.m_sizeX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSizeY()
/*     */   {
/* 165 */     return this.m_sizeY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resize(int sizeX, int sizeY, ResizeStyle resizeStyleX, ResizeStyle resizeStyleY)
/*     */   {
/* 176 */     boolean[][] datas = (boolean[][])null;
/* 177 */     int i = 0;
/*     */     
/* 179 */     if (sizeX != this.m_sizeX) {
/* 180 */       float ratio = this.m_sizeX / sizeX;
/* 181 */       datas = this.m_datas;
/* 182 */       this.m_datas = new boolean[sizeX][];
/* 183 */       if (sizeX < this.m_sizeX) {
/* 184 */         switch (resizeStyleX) {
/*     */         case CLAMP: 
/*     */         case MIRROR: 
/*     */         case REPEAT: 
/* 188 */           for (i = 0; i < sizeX; i++) {
/* 189 */             this.m_datas[i] = datas[i];
/*     */           }
/* 191 */           break;
/*     */         
/*     */ 
/*     */         case SCALE: 
/* 195 */           for (i = 0; i < sizeX; i++) {
/* 196 */             this.m_datas[i] = datas[Math.round(i * ratio)];
/*     */           }
/*     */         
/*     */         }
/*     */         
/*     */       } else {
/* 202 */         switch (resizeStyleX) {
/*     */         case CLAMP: 
/* 204 */           for (i = 0; i < this.m_sizeX; i++) {
/* 205 */             this.m_datas[i] = datas[i];
/*     */           }
/*     */           
/* 208 */           for (i = this.m_sizeX; i < sizeX; i++) {
/* 209 */             this.m_datas[i] = new boolean[sizeY];
/*     */           }
/*     */           
/* 212 */           break;
/*     */         
/*     */         case MIRROR: 
/* 215 */           for (i = 0; i < sizeX; i++) {
/* 216 */             this.m_datas[i] = datas[(i % this.m_sizeX)];
/*     */           }
/* 218 */           break;
/*     */         
/*     */         case SCALE: 
/* 221 */           for (i = 0; i < sizeX; i++) {
/* 222 */             this.m_datas[i] = datas[((int)(i * ratio))];
/*     */           }
/*     */           
/* 225 */           break;
/*     */         
/*     */         case REPEAT: 
/* 228 */           for (i = 0; i < sizeX; i++) {
/* 229 */             if (i / this.m_sizeX % 2 == 0) {
/* 230 */               this.m_datas[i] = datas[(i % this.m_sizeX)];
/*     */             } else {
/* 232 */               this.m_datas[i] = datas[(this.m_sizeX - i % this.m_sizeX - 1)];
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */       }
/* 238 */       this.m_sizeX = sizeX;
/*     */     }
/*     */     
/* 241 */     if (sizeY != this.m_sizeY) {
/* 242 */       boolean[] data = (boolean[])null;
/*     */       
/* 244 */       float ratio = this.m_sizeY / sizeY;
/*     */       
/* 246 */       if (sizeY < this.m_sizeY) {
/* 247 */         switch (resizeStyleY) {
/*     */         case CLAMP: 
/*     */         case MIRROR: 
/*     */         case REPEAT: 
/* 251 */           for (int x = 0; x < this.m_sizeX; x++) {
/* 252 */             data = this.m_datas[x];
/* 253 */             this.m_datas[x] = new boolean[sizeY];
/* 254 */             for (i = 0; i < sizeY; i++) {
/* 255 */               this.m_datas[x][i] = data[i];
/*     */             }
/*     */           }
/* 258 */           break;
/*     */         
/*     */ 
/*     */         case SCALE: 
/* 262 */           for (int x = 0; x < this.m_sizeX; x++) {
/* 263 */             data = this.m_datas[x];
/* 264 */             this.m_datas[x] = new boolean[sizeY];
/* 265 */             for (i = 0; i < sizeY; i++) {
/* 266 */               this.m_datas[x][i] = data[((int)(i * ratio))];
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */         
/*     */       } else {
/* 273 */         switch (resizeStyleY) {
/*     */         case CLAMP: 
/* 275 */           for (int x = 0; x < this.m_sizeX; x++) {
/* 276 */             data = this.m_datas[x];
/* 277 */             this.m_datas[x] = new boolean[sizeY];
/* 278 */             for (i = 0; i < sizeY; i++) {
/* 279 */               this.m_datas[x][i] = data[i];
/*     */             }
/*     */           }
/* 282 */           break;
/*     */         
/*     */         case MIRROR: 
/* 285 */           for (int x = 0; x < this.m_sizeX; x++) {
/* 286 */             data = this.m_datas[x];
/* 287 */             this.m_datas[x] = new boolean[sizeY];
/* 288 */             for (i = 0; i < sizeY; i++) {
/* 289 */               this.m_datas[x][i] = data[(i % this.m_sizeY)];
/*     */             }
/*     */           }
/* 292 */           break;
/*     */         
/*     */         case SCALE: 
/* 295 */           for (int x = 0; x < this.m_sizeX; x++) {
/* 296 */             data = this.m_datas[x];
/* 297 */             this.m_datas[x] = new boolean[sizeY];
/*     */             try {
/* 299 */               for (i = 0; i < sizeY; i++) {
/* 300 */                 this.m_datas[x][i] = data[((int)(i * ratio))];
/*     */               }
/*     */             }
/*     */             catch (Exception ex) {
/* 304 */               System.out.println((int)(i * ratio));
/*     */             }
/*     */           }
/*     */           
/* 308 */           break;
/*     */         
/*     */         case REPEAT: 
/* 311 */           for (int x = 0; x < this.m_sizeX; x++) {
/* 312 */             data = this.m_datas[x];
/* 313 */             this.m_datas[x] = new boolean[sizeY];
/*     */             
/* 315 */             for (i = 0; i < sizeY; i++) {
/* 316 */               if (i / this.m_sizeX % 2 == 0) {
/* 317 */                 this.m_datas[x][i] = data[(i % this.m_sizeY)];
/*     */               } else {
/* 319 */                 this.m_datas[x][i] = data[(this.m_sizeY - i % this.m_sizeY - 1)];
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */       }
/* 326 */       this.m_sizeY = sizeY;
/*     */     }
/*     */     
/* 329 */     this.m_optimized = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void optimize()
/*     */   {
/* 339 */     if (!this.m_optimized) {
/* 340 */       this.m_optimized = true;
/*     */       
/*     */ 
/* 343 */       int left = 0;int right = this.m_sizeX;int top = 0;int bottom = this.m_sizeY;
/* 344 */       boolean columnEmpty = false;
/*     */       
/* 346 */       int x = 0;
/* 347 */       while (x < this.m_sizeX) {
/* 348 */         x++;
/*     */       }
/* 350 */       for (x = 0; x < this.m_sizeX; x++) {
/* 351 */         columnEmpty = true;
/* 352 */         for (int y = 0; y < this.m_sizeY; y++) {
/* 353 */           if (this.m_datas[x][y] != 0) {
/* 354 */             columnEmpty = false;
/*     */           }
/*     */         }
/*     */       }
/* 358 */       this.m_centerX += left;
/* 359 */       this.m_centerY += top;
/*     */       
/* 361 */       int newSizeX = right - left;
/* 362 */       int newSizeY = bottom - top;
/*     */       
/* 364 */       boolean[][] data = this.m_datas;
/* 365 */       for (int i = 0; i < this.m_sizeX; i++) {
/* 366 */         data[i] = this.m_datas[i];
/*     */       }
/*     */       
/* 369 */       this.m_sizeX = newSizeX;
/* 370 */       this.m_sizeY = newSizeY;
/*     */       
/* 372 */       this.m_datas = new boolean[this.m_sizeX][];
/* 373 */       for (int i = 0; i < this.m_sizeX; i++) {
/* 374 */         this.m_datas[i] = new boolean[this.m_sizeY];
/* 375 */         for (int j = 0; j < this.m_sizeY; j++) {
/* 376 */           this.m_datas[i][j] = data[(i + left)][(j + top)];
/*     */         }
/*     */       }
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
/*     */   public void union(BaseBrush brush, int posX, int posY)
/*     */   {
/* 392 */     int i = 0;
/* 393 */     int j = 0;
/*     */     
/* 395 */     posX += this.m_centerX - brush.m_centerX;
/* 396 */     posY += this.m_centerY - brush.m_centerY;
/*     */     
/* 398 */     for (int x = 0; x < brush.m_sizeX; x++) {
/* 399 */       i = x + posX;
/* 400 */       if ((i >= 0) && (i < this.m_sizeX))
/* 401 */         for (int y = 0; y < brush.m_sizeY; y++) {
/* 402 */           j = y + posY;
/* 403 */           if ((j >= 0) && (j < this.m_sizeY))
/* 404 */             this.m_datas[i][j] |= brush.m_datas[x][y];
/*     */         }
/*     */     }
/*     */   }
/*     */   
/*     */   public void union(BaseBrush brush) {
/* 410 */     union(brush, 0, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void union2(BaseBrush brush, int posX, int posY)
/*     */   {
/* 422 */     int i = 0;
/* 423 */     int j = 0;
/*     */     
/* 425 */     posX += this.m_centerX - brush.m_centerX;
/* 426 */     posY += this.m_centerY - brush.m_centerY;
/*     */     
/* 428 */     for (int x = 0; x < brush.m_sizeX; x++) {
/* 429 */       i = x + posX;
/* 430 */       if ((i >= 0) && (i < this.m_sizeX))
/* 431 */         for (int y = 0; y < brush.m_sizeY; y++) {
/* 432 */           j = y + posY;
/* 433 */           if ((j >= 0) && (j < this.m_sizeY))
/* 434 */             this.m_datas[i][j] |= brush.m_datas[x][y];
/*     */         }
/*     */     }
/*     */   }
/*     */   
/*     */   public void union2(BaseBrush brush) {
/* 440 */     union2(brush, 0, 0);
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
/*     */   public void intersection(BaseBrush brush, int posX, int posY)
/*     */   {
/* 453 */     int i = 0;
/* 454 */     int j = 0;
/*     */     
/* 456 */     posX += this.m_centerX - brush.m_centerX;
/* 457 */     posY += this.m_centerY - brush.m_centerY;
/*     */     
/* 459 */     for (int x = 0; x < brush.m_sizeX; x++) {
/* 460 */       i = x + posX;
/* 461 */       if ((i >= 0) && (i < this.m_sizeX)) {
/* 462 */         for (int y = 0; y < brush.m_sizeY; y++) {
/* 463 */           j = y + posY;
/* 464 */           if ((j >= 0) && (j < this.m_sizeY))
/* 465 */             this.m_datas[i][j] &= brush.m_datas[x][y];
/*     */         }
/*     */       }
/*     */     }
/* 469 */     this.m_optimized = false;
/*     */   }
/*     */   
/* 472 */   public void intersection(BaseBrush brush) { intersection(brush, 0, 0); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void difference(BaseBrush brush, int posX, int posY)
/*     */   {
/* 483 */     int i = 0;
/* 484 */     int j = 0;
/*     */     
/* 486 */     posX += this.m_centerX - brush.m_centerX;
/* 487 */     posY += this.m_centerY - brush.m_centerY;
/*     */     
/* 489 */     for (int x = 0; x < brush.m_sizeX; x++) {
/* 490 */       i = x + posX;
/* 491 */       if ((i >= 0) && (i < this.m_sizeX)) {
/* 492 */         for (int y = 0; y < brush.m_sizeY; y++) {
/* 493 */           j = y + posY;
/* 494 */           if ((j >= 0) && (j < this.m_sizeY))
/* 495 */             this.m_datas[i][j] &= (brush.m_datas[x][y] != 0 ? 0 : 1);
/*     */         }
/*     */       }
/*     */     }
/* 499 */     this.m_optimized = false;
/*     */   }
/*     */   
/* 502 */   public void difference(BaseBrush brush) { difference(brush, 0, 0); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void invert()
/*     */   {
/* 509 */     for (int x = 0; x < this.m_sizeX; x++) {
/* 510 */       for (int y = 0; y < this.m_sizeY; y++) {
/* 511 */         this.m_datas[x][y] = (this.m_datas[x][y] != 0 ? 0 : 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 521 */     for (int x = 0; x < this.m_sizeX; x++) {
/* 522 */       for (int y = 0; y < this.m_sizeY; y++) {
/* 523 */         this.m_datas[x][y] = 0;
/*     */       }
/*     */     }
/* 526 */     this.m_optimized = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flipX()
/*     */   {
/* 535 */     this.m_centerX = (this.m_sizeX - this.m_centerX - 1);
/*     */     
/* 537 */     for (int x = 0; x < this.m_sizeX / 2; x++) {
/* 538 */       boolean[] temp = this.m_datas[x];
/* 539 */       this.m_datas[x] = this.m_datas[(this.m_sizeX - x - 1)];
/* 540 */       this.m_datas[(this.m_sizeX - x - 1)] = temp;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flipY()
/*     */   {
/* 550 */     this.m_centerY = (this.m_sizeY - this.m_centerY - 1);
/*     */     
/* 552 */     for (int x = 0; x < this.m_sizeX; x++) {
/* 553 */       for (int y = 0; y < this.m_sizeY / 2; y++) {
/* 554 */         boolean temp = this.m_datas[x][y];
/* 555 */         this.m_datas[x][y] = this.m_datas[x][(this.m_sizeY - 1 - y)];
/* 556 */         this.m_datas[x][(this.m_sizeY - 1 - y)] = temp;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flipXY()
/*     */   {
/* 567 */     flipX();
/* 568 */     flipY();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void rotate90()
/*     */   {
/* 576 */     boolean[][] datas = this.m_datas;
/* 577 */     int temp = this.m_sizeX;
/* 578 */     this.m_sizeX = this.m_sizeY;
/* 579 */     this.m_sizeY = temp;
/*     */     
/* 581 */     int sizeYMinusOne = this.m_sizeY - 1;
/*     */     
/* 583 */     temp = this.m_centerX;
/* 584 */     this.m_centerX = this.m_centerY;
/* 585 */     this.m_centerY = (sizeYMinusOne - temp);
/*     */     
/* 587 */     this.m_datas = new boolean[this.m_sizeX][];
/*     */     
/* 589 */     for (int i = 0; i < this.m_sizeX; i++) {
/* 590 */       this.m_datas[i] = new boolean[this.m_sizeY];
/*     */       
/* 592 */       for (int j = 0; j < this.m_sizeY; j++) {
/* 593 */         this.m_datas[i][j] = datas[(sizeYMinusOne - j)][i];
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void rotate180()
/*     */   {
/* 602 */     flipXY();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void rotate270()
/*     */   {
/* 609 */     boolean[][] datas = this.m_datas;
/* 610 */     int temp = this.m_sizeX;
/* 611 */     this.m_sizeX = this.m_sizeY;
/* 612 */     this.m_sizeY = temp;
/*     */     
/* 614 */     int sizeXMinusOne = this.m_sizeX - 1;
/*     */     
/* 616 */     temp = this.m_centerX;
/* 617 */     this.m_centerX = (sizeXMinusOne - this.m_centerY);
/* 618 */     this.m_centerY = temp;
/*     */     
/*     */ 
/* 621 */     this.m_datas = new boolean[this.m_sizeX][];
/*     */     
/*     */ 
/* 624 */     for (int i = 0; i < this.m_sizeX; i++) {
/* 625 */       this.m_datas[i] = new boolean[this.m_sizeY];
/*     */       
/* 627 */       for (int j = 0; j < this.m_sizeY; j++) {
/* 628 */         this.m_datas[i][j] = datas[j][(sizeXMinusOne - i)];
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 641 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 643 */     int left = this.m_centerX < 0 ? this.m_centerX : 0;
/* 644 */     int right = this.m_centerX >= this.m_sizeX ? this.m_centerX + 1 : this.m_sizeX;
/* 645 */     int top = this.m_centerY < 0 ? this.m_centerY : 0;
/* 646 */     int bottom = this.m_centerY >= this.m_sizeY ? this.m_centerY + 1 : this.m_sizeY;
/*     */     
/* 648 */     for (int y = top; y < bottom; y++) {
/* 649 */       for (int x = left; x < right; x++) {
/* 650 */         if ((x == this.m_centerX) && (y == this.m_centerY)) {
/* 651 */           sb.append("(");
/* 652 */           if ((x < 0) || (x >= this.m_sizeX) || (y < 0) || (y >= this.m_sizeY)) {
/* 653 */             sb.append(' ');
/*     */           } else
/* 655 */             sb.append(this.m_datas[x][y] != 0 ? 'X' : '-');
/* 656 */           sb.append(")");
/*     */         }
/*     */         else {
/* 659 */           sb.append(" ");
/* 660 */           if ((x < 0) || (x >= this.m_sizeX) || (y < 0) || (y >= this.m_sizeY)) {
/* 661 */             sb.append(' ');
/*     */           } else {
/* 663 */             sb.append(this.m_datas[x][y] != 0 ? 'X' : '-');
/*     */           }
/* 665 */           sb.append(" ");
/*     */         }
/*     */       }
/* 668 */       sb.append("\n");
/*     */     }
/* 670 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\painting\brushes\BaseBrush.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */