/*     */ package gnu.trove;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutput;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SerializationProcedure
/*     */   implements TDoubleDoubleProcedure, TDoubleFloatProcedure, TDoubleIntProcedure, TDoubleLongProcedure, TDoubleShortProcedure, TDoubleByteProcedure, TDoubleObjectProcedure, TDoubleProcedure, TFloatDoubleProcedure, TFloatFloatProcedure, TFloatIntProcedure, TFloatLongProcedure, TFloatShortProcedure, TFloatByteProcedure, TFloatObjectProcedure, TFloatProcedure, TIntDoubleProcedure, TIntFloatProcedure, TIntIntProcedure, TIntLongProcedure, TIntShortProcedure, TIntByteProcedure, TIntObjectProcedure, TIntProcedure, TLongDoubleProcedure, TLongFloatProcedure, TLongIntProcedure, TLongLongProcedure, TLongShortProcedure, TLongByteProcedure, TLongObjectProcedure, TLongProcedure, TShortDoubleProcedure, TShortFloatProcedure, TShortIntProcedure, TShortLongProcedure, TShortShortProcedure, TShortByteProcedure, TShortObjectProcedure, TShortProcedure, TByteDoubleProcedure, TByteFloatProcedure, TByteIntProcedure, TByteLongProcedure, TByteShortProcedure, TByteByteProcedure, TByteObjectProcedure, TByteProcedure, TObjectDoubleProcedure, TObjectFloatProcedure, TObjectIntProcedure, TObjectLongProcedure, TObjectShortProcedure, TObjectByteProcedure, TObjectObjectProcedure, TObjectProcedure
/*     */ {
/*     */   private final ObjectOutput stream;
/*     */   IOException exception;
/*     */   
/*     */   SerializationProcedure(ObjectOutput stream) {
/* 103 */     this.stream = stream;
/*     */   }
/*     */   
/*     */   public boolean execute(byte val) {
/*     */     try {
/* 108 */       this.stream.writeByte(val);
/* 109 */     } catch (IOException e) {
/* 110 */       this.exception = e;
/* 111 */       return false;
/*     */     } 
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short val) {
/*     */     try {
/* 118 */       this.stream.writeShort(val);
/* 119 */     } catch (IOException e) {
/* 120 */       this.exception = e;
/* 121 */       return false;
/*     */     } 
/* 123 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int val) {
/*     */     try {
/* 128 */       this.stream.writeInt(val);
/* 129 */     } catch (IOException e) {
/* 130 */       this.exception = e;
/* 131 */       return false;
/*     */     } 
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double val) {
/*     */     try {
/* 138 */       this.stream.writeDouble(val);
/* 139 */     } catch (IOException e) {
/* 140 */       this.exception = e;
/* 141 */       return false;
/*     */     } 
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long val) {
/*     */     try {
/* 148 */       this.stream.writeLong(val);
/* 149 */     } catch (IOException e) {
/* 150 */       this.exception = e;
/* 151 */       return false;
/*     */     } 
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float val) {
/*     */     try {
/* 158 */       this.stream.writeFloat(val);
/* 159 */     } catch (IOException e) {
/* 160 */       this.exception = e;
/* 161 */       return false;
/*     */     } 
/* 163 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object val) {
/*     */     try {
/* 168 */       this.stream.writeObject(val);
/* 169 */     } catch (IOException e) {
/* 170 */       this.exception = e;
/* 171 */       return false;
/*     */     } 
/* 173 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object key, Object val) {
/*     */     try {
/* 178 */       this.stream.writeObject(key);
/* 179 */       this.stream.writeObject(val);
/* 180 */     } catch (IOException e) {
/* 181 */       this.exception = e;
/* 182 */       return false;
/*     */     } 
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object key, byte val) {
/*     */     try {
/* 189 */       this.stream.writeObject(key);
/* 190 */       this.stream.writeByte(val);
/* 191 */     } catch (IOException e) {
/* 192 */       this.exception = e;
/* 193 */       return false;
/*     */     } 
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object key, short val) {
/*     */     try {
/* 200 */       this.stream.writeObject(key);
/* 201 */       this.stream.writeShort(val);
/* 202 */     } catch (IOException e) {
/* 203 */       this.exception = e;
/* 204 */       return false;
/*     */     } 
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object key, int val) {
/*     */     try {
/* 211 */       this.stream.writeObject(key);
/* 212 */       this.stream.writeInt(val);
/* 213 */     } catch (IOException e) {
/* 214 */       this.exception = e;
/* 215 */       return false;
/*     */     } 
/* 217 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object key, long val) {
/*     */     try {
/* 222 */       this.stream.writeObject(key);
/* 223 */       this.stream.writeLong(val);
/* 224 */     } catch (IOException e) {
/* 225 */       this.exception = e;
/* 226 */       return false;
/*     */     } 
/* 228 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object key, double val) {
/*     */     try {
/* 233 */       this.stream.writeObject(key);
/* 234 */       this.stream.writeDouble(val);
/* 235 */     } catch (IOException e) {
/* 236 */       this.exception = e;
/* 237 */       return false;
/*     */     } 
/* 239 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(Object key, float val) {
/*     */     try {
/* 244 */       this.stream.writeObject(key);
/* 245 */       this.stream.writeFloat(val);
/* 246 */     } catch (IOException e) {
/* 247 */       this.exception = e;
/* 248 */       return false;
/*     */     } 
/* 250 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int key, byte val) {
/*     */     try {
/* 255 */       this.stream.writeInt(key);
/* 256 */       this.stream.writeByte(val);
/* 257 */     } catch (IOException e) {
/* 258 */       this.exception = e;
/* 259 */       return false;
/*     */     } 
/* 261 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int key, short val) {
/*     */     try {
/* 266 */       this.stream.writeInt(key);
/* 267 */       this.stream.writeShort(val);
/* 268 */     } catch (IOException e) {
/* 269 */       this.exception = e;
/* 270 */       return false;
/*     */     } 
/* 272 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int key, Object val) {
/*     */     try {
/* 277 */       this.stream.writeInt(key);
/* 278 */       this.stream.writeObject(val);
/* 279 */     } catch (IOException e) {
/* 280 */       this.exception = e;
/* 281 */       return false;
/*     */     } 
/* 283 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int key, int val) {
/*     */     try {
/* 288 */       this.stream.writeInt(key);
/* 289 */       this.stream.writeInt(val);
/* 290 */     } catch (IOException e) {
/* 291 */       this.exception = e;
/* 292 */       return false;
/*     */     } 
/* 294 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int key, long val) {
/*     */     try {
/* 299 */       this.stream.writeInt(key);
/* 300 */       this.stream.writeLong(val);
/* 301 */     } catch (IOException e) {
/* 302 */       this.exception = e;
/* 303 */       return false;
/*     */     } 
/* 305 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int key, double val) {
/*     */     try {
/* 310 */       this.stream.writeInt(key);
/* 311 */       this.stream.writeDouble(val);
/* 312 */     } catch (IOException e) {
/* 313 */       this.exception = e;
/* 314 */       return false;
/*     */     } 
/* 316 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(int key, float val) {
/*     */     try {
/* 321 */       this.stream.writeInt(key);
/* 322 */       this.stream.writeFloat(val);
/* 323 */     } catch (IOException e) {
/* 324 */       this.exception = e;
/* 325 */       return false;
/*     */     } 
/* 327 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long key, Object val) {
/*     */     try {
/* 332 */       this.stream.writeLong(key);
/* 333 */       this.stream.writeObject(val);
/* 334 */     } catch (IOException e) {
/* 335 */       this.exception = e;
/* 336 */       return false;
/*     */     } 
/* 338 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long key, byte val) {
/*     */     try {
/* 343 */       this.stream.writeLong(key);
/* 344 */       this.stream.writeByte(val);
/* 345 */     } catch (IOException e) {
/* 346 */       this.exception = e;
/* 347 */       return false;
/*     */     } 
/* 349 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long key, short val) {
/*     */     try {
/* 354 */       this.stream.writeLong(key);
/* 355 */       this.stream.writeShort(val);
/* 356 */     } catch (IOException e) {
/* 357 */       this.exception = e;
/* 358 */       return false;
/*     */     } 
/* 360 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long key, int val) {
/*     */     try {
/* 365 */       this.stream.writeLong(key);
/* 366 */       this.stream.writeInt(val);
/* 367 */     } catch (IOException e) {
/* 368 */       this.exception = e;
/* 369 */       return false;
/*     */     } 
/* 371 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long key, long val) {
/*     */     try {
/* 376 */       this.stream.writeLong(key);
/* 377 */       this.stream.writeLong(val);
/* 378 */     } catch (IOException e) {
/* 379 */       this.exception = e;
/* 380 */       return false;
/*     */     } 
/* 382 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long key, double val) {
/*     */     try {
/* 387 */       this.stream.writeLong(key);
/* 388 */       this.stream.writeDouble(val);
/* 389 */     } catch (IOException e) {
/* 390 */       this.exception = e;
/* 391 */       return false;
/*     */     } 
/* 393 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(long key, float val) {
/*     */     try {
/* 398 */       this.stream.writeLong(key);
/* 399 */       this.stream.writeFloat(val);
/* 400 */     } catch (IOException e) {
/* 401 */       this.exception = e;
/* 402 */       return false;
/*     */     } 
/* 404 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double key, Object val) {
/*     */     try {
/* 409 */       this.stream.writeDouble(key);
/* 410 */       this.stream.writeObject(val);
/* 411 */     } catch (IOException e) {
/* 412 */       this.exception = e;
/* 413 */       return false;
/*     */     } 
/* 415 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double key, byte val) {
/*     */     try {
/* 420 */       this.stream.writeDouble(key);
/* 421 */       this.stream.writeByte(val);
/* 422 */     } catch (IOException e) {
/* 423 */       this.exception = e;
/* 424 */       return false;
/*     */     } 
/* 426 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double key, short val) {
/*     */     try {
/* 431 */       this.stream.writeDouble(key);
/* 432 */       this.stream.writeShort(val);
/* 433 */     } catch (IOException e) {
/* 434 */       this.exception = e;
/* 435 */       return false;
/*     */     } 
/* 437 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double key, int val) {
/*     */     try {
/* 442 */       this.stream.writeDouble(key);
/* 443 */       this.stream.writeInt(val);
/* 444 */     } catch (IOException e) {
/* 445 */       this.exception = e;
/* 446 */       return false;
/*     */     } 
/* 448 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double key, long val) {
/*     */     try {
/* 453 */       this.stream.writeDouble(key);
/* 454 */       this.stream.writeLong(val);
/* 455 */     } catch (IOException e) {
/* 456 */       this.exception = e;
/* 457 */       return false;
/*     */     } 
/* 459 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double key, double val) {
/*     */     try {
/* 464 */       this.stream.writeDouble(key);
/* 465 */       this.stream.writeDouble(val);
/* 466 */     } catch (IOException e) {
/* 467 */       this.exception = e;
/* 468 */       return false;
/*     */     } 
/* 470 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(double key, float val) {
/*     */     try {
/* 475 */       this.stream.writeDouble(key);
/* 476 */       this.stream.writeFloat(val);
/* 477 */     } catch (IOException e) {
/* 478 */       this.exception = e;
/* 479 */       return false;
/*     */     } 
/* 481 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float key, Object val) {
/*     */     try {
/* 486 */       this.stream.writeFloat(key);
/* 487 */       this.stream.writeObject(val);
/* 488 */     } catch (IOException e) {
/* 489 */       this.exception = e;
/* 490 */       return false;
/*     */     } 
/* 492 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float key, byte val) {
/*     */     try {
/* 497 */       this.stream.writeFloat(key);
/* 498 */       this.stream.writeByte(val);
/* 499 */     } catch (IOException e) {
/* 500 */       this.exception = e;
/* 501 */       return false;
/*     */     } 
/* 503 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float key, short val) {
/*     */     try {
/* 508 */       this.stream.writeFloat(key);
/* 509 */       this.stream.writeShort(val);
/* 510 */     } catch (IOException e) {
/* 511 */       this.exception = e;
/* 512 */       return false;
/*     */     } 
/* 514 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float key, int val) {
/*     */     try {
/* 519 */       this.stream.writeFloat(key);
/* 520 */       this.stream.writeInt(val);
/* 521 */     } catch (IOException e) {
/* 522 */       this.exception = e;
/* 523 */       return false;
/*     */     } 
/* 525 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float key, long val) {
/*     */     try {
/* 530 */       this.stream.writeFloat(key);
/* 531 */       this.stream.writeLong(val);
/* 532 */     } catch (IOException e) {
/* 533 */       this.exception = e;
/* 534 */       return false;
/*     */     } 
/* 536 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float key, double val) {
/*     */     try {
/* 541 */       this.stream.writeFloat(key);
/* 542 */       this.stream.writeDouble(val);
/* 543 */     } catch (IOException e) {
/* 544 */       this.exception = e;
/* 545 */       return false;
/*     */     } 
/* 547 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(float key, float val) {
/*     */     try {
/* 552 */       this.stream.writeFloat(key);
/* 553 */       this.stream.writeFloat(val);
/* 554 */     } catch (IOException e) {
/* 555 */       this.exception = e;
/* 556 */       return false;
/*     */     } 
/* 558 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(byte key, Object val) {
/*     */     try {
/* 563 */       this.stream.writeByte(key);
/* 564 */       this.stream.writeObject(val);
/* 565 */     } catch (IOException e) {
/* 566 */       this.exception = e;
/* 567 */       return false;
/*     */     } 
/* 569 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(byte key, byte val) {
/*     */     try {
/* 574 */       this.stream.writeByte(key);
/* 575 */       this.stream.writeByte(val);
/* 576 */     } catch (IOException e) {
/* 577 */       this.exception = e;
/* 578 */       return false;
/*     */     } 
/* 580 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(byte key, short val) {
/*     */     try {
/* 585 */       this.stream.writeByte(key);
/* 586 */       this.stream.writeShort(val);
/* 587 */     } catch (IOException e) {
/* 588 */       this.exception = e;
/* 589 */       return false;
/*     */     } 
/* 591 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(byte key, int val) {
/*     */     try {
/* 596 */       this.stream.writeByte(key);
/* 597 */       this.stream.writeInt(val);
/* 598 */     } catch (IOException e) {
/* 599 */       this.exception = e;
/* 600 */       return false;
/*     */     } 
/* 602 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(byte key, long val) {
/*     */     try {
/* 607 */       this.stream.writeByte(key);
/* 608 */       this.stream.writeLong(val);
/* 609 */     } catch (IOException e) {
/* 610 */       this.exception = e;
/* 611 */       return false;
/*     */     } 
/* 613 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(byte key, double val) {
/*     */     try {
/* 618 */       this.stream.writeByte(key);
/* 619 */       this.stream.writeDouble(val);
/* 620 */     } catch (IOException e) {
/* 621 */       this.exception = e;
/* 622 */       return false;
/*     */     } 
/* 624 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(byte key, float val) {
/*     */     try {
/* 629 */       this.stream.writeByte(key);
/* 630 */       this.stream.writeFloat(val);
/* 631 */     } catch (IOException e) {
/* 632 */       this.exception = e;
/* 633 */       return false;
/*     */     } 
/* 635 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short key, Object val) {
/*     */     try {
/* 640 */       this.stream.writeShort(key);
/* 641 */       this.stream.writeObject(val);
/* 642 */     } catch (IOException e) {
/* 643 */       this.exception = e;
/* 644 */       return false;
/*     */     } 
/* 646 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short key, byte val) {
/*     */     try {
/* 651 */       this.stream.writeShort(key);
/* 652 */       this.stream.writeByte(val);
/* 653 */     } catch (IOException e) {
/* 654 */       this.exception = e;
/* 655 */       return false;
/*     */     } 
/* 657 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short key, short val) {
/*     */     try {
/* 662 */       this.stream.writeShort(key);
/* 663 */       this.stream.writeShort(val);
/* 664 */     } catch (IOException e) {
/* 665 */       this.exception = e;
/* 666 */       return false;
/*     */     } 
/* 668 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short key, int val) {
/*     */     try {
/* 673 */       this.stream.writeShort(key);
/* 674 */       this.stream.writeInt(val);
/* 675 */     } catch (IOException e) {
/* 676 */       this.exception = e;
/* 677 */       return false;
/*     */     } 
/* 679 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short key, long val) {
/*     */     try {
/* 684 */       this.stream.writeShort(key);
/* 685 */       this.stream.writeLong(val);
/* 686 */     } catch (IOException e) {
/* 687 */       this.exception = e;
/* 688 */       return false;
/*     */     } 
/* 690 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short key, double val) {
/*     */     try {
/* 695 */       this.stream.writeShort(key);
/* 696 */       this.stream.writeDouble(val);
/* 697 */     } catch (IOException e) {
/* 698 */       this.exception = e;
/* 699 */       return false;
/*     */     } 
/* 701 */     return true;
/*     */   }
/*     */   
/*     */   public boolean execute(short key, float val) {
/*     */     try {
/* 706 */       this.stream.writeShort(key);
/* 707 */       this.stream.writeFloat(val);
/* 708 */     } catch (IOException e) {
/* 709 */       this.exception = e;
/* 710 */       return false;
/*     */     } 
/* 712 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\SerializationProcedure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */