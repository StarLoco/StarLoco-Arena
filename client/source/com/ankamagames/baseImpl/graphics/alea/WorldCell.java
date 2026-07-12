/*     */ package com.ankamagames.baseImpl.graphics.alea;
/*     */ 
/*     */ import com.ankamagames.alea.AleaWorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.struct.space.Partition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class WorldCell
/*     */   implements AleaWorldCell
/*     */ {
/*     */   private int m_worldX;
/*     */   private int m_worldY;
/*  37 */   private ArrayList<GraphicalWorldElement> m_visualElements = new ArrayList<GraphicalWorldElement>();
/*     */ 
/*     */ 
/*     */   
/*  41 */   private ArrayList<WorldElement> m_customElements = new ArrayList<WorldElement>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldCell(int worldX, int worldY) {
/*  50 */     this.m_worldX = worldX;
/*  51 */     this.m_worldY = worldY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldX(int worldX) {
/*  58 */     this.m_worldX = worldX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldY(int worldY) {
/*  65 */     this.m_worldY = worldY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVisualElement(GraphicalWorldElement element) {
/*  75 */     if (this.m_visualElements.size() >= 512)
/*  76 */       System.err.println("Coordonnées de cellules dépasse la capacité d'un int"); 
/*  77 */     long handle = getHandle() | this.m_visualElements.size();
/*  78 */     element.SetHandle(handle);
/*  79 */     this.m_visualElements.add(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getHandle() {
/*  86 */     long ux = this.m_worldX & 0xFFFFFFFL;
/*  87 */     long uy = this.m_worldY & 0xFFFFFFFL;
/*     */     
/*  89 */     if (ux >= 536870912L || uy >= 536870912L)
/*  90 */       System.err.println("Coordonnées de cellules dépasse la capacité d'un int"); 
/*  91 */     return ux << 36L | uy << 8L;
/*     */   }
/*     */   
/*     */   public ArrayList<GraphicalWorldElement> getVisualElements() {
/*  95 */     return this.m_visualElements;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCustomElement(WorldElement element) {
/* 100 */     this.m_customElements.add(element);
/*     */   }
/*     */   
/*     */   public ArrayList<WorldElement> getCustomElement() {
/* 104 */     return this.m_customElements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldElement getHighestElement() {
/* 112 */     int altitude = Integer.MIN_VALUE;
/* 113 */     WorldElement highest = null;
/*     */     
/* 115 */     for (int i = this.m_visualElements.size() - 1; i >= 0; i--) {
/* 116 */       WorldElement elt = (WorldElement)this.m_visualElements.get(i);
/* 117 */       if (elt.getCoordinates().getZ() > altitude) {
/* 118 */         altitude = elt.getCoordinates().getZ();
/* 119 */         highest = elt;
/*     */       } 
/*     */     } 
/* 122 */     return highest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<WorldElement> getElementsAtTop() {
/* 133 */     int altitude = Integer.MIN_VALUE;
/* 134 */     int lastLevel = -1;
/* 135 */     List<WorldElement> elements = new ArrayList<WorldElement>();
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
/* 152 */     WorldElement element = getHighestElement();
/* 153 */     if (element != null) {
/* 154 */       elements.add(element);
/*     */     }
/*     */     
/* 157 */     return elements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Partition getPartitionFromPoint(float x, float y, float z) {
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllPartitions() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPartition(Partition subPartition) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePartition(Partition subPartition) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 201 */     return this.m_worldX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 210 */     return this.m_worldY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSightValid(short height, Direction8 direction) {
/* 220 */     if (this.m_visualElements == null) {
/* 221 */       return true;
/*     */     }
/* 223 */     for (GraphicalWorldElement element : getVisualElements()) {
/*     */       
/* 225 */       if (element.getHeight() > 0.0D && element.getAltitude() <= height && element.getCoordinates().getZ() > height) {
/* 226 */         GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/*     */         
/* 228 */         switch (direction) {
/*     */           case SOUTH_EAST:
/* 230 */             if (!properties.isLineOfSight1()) {
/* 231 */               return false;
/*     */             }
/*     */           case SOUTH_WEST:
/* 234 */             if (!properties.isLineOfSight3()) {
/* 235 */               return false;
/*     */             }
/*     */           case NORTH_WEST:
/* 238 */             if (!properties.isLineOfSight5()) {
/* 239 */               return false;
/*     */             }
/*     */           case NORTH_EAST:
/* 242 */             if (!properties.isLineOfSight7()) {
/* 243 */               return false;
/*     */             }
/*     */           case TOP:
/* 246 */             if (!properties.isLineOfSightTop()) {
/* 247 */               return false;
/*     */             }
/*     */           case null:
/* 250 */             if (!properties.isLineOfSightBottom()) {
/* 251 */               return false;
/*     */             }
/*     */           case NORTH:
/* 254 */             if (!properties.isLineOfSight5() || !properties.isLineOfSight7()) {
/* 255 */               return false;
/*     */             }
/*     */           case SOUTH:
/* 258 */             if (!properties.isLineOfSight1() || !properties.isLineOfSight3()) {
/* 259 */               return false;
/*     */             }
/*     */           case EAST:
/* 262 */             if (!properties.isLineOfSight1() || !properties.isLineOfSight7()) {
/* 263 */               return false;
/*     */             }
/*     */           case WEST:
/* 266 */             if (!properties.isLineOfSight3() || !properties.isLineOfSight5()) {
/* 267 */               return false;
/*     */             }
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineOfSightEndValid(short height) {
/* 281 */     if (this.m_visualElements == null) {
/* 282 */       return false;
/*     */     }
/* 284 */     List<GraphicalWorldElement> visualElement = getVisualElements();
/* 285 */     for (int i = visualElement.size() - 1; i >= 0; i--) {
/* 286 */       GraphicalWorldElement element = visualElement.get(i);
/* 287 */       if (element.getCoordinates().getZ() == height) {
/* 288 */         GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/* 289 */         if (!properties.isWalkable()) {
/* 290 */           return false;
/*     */         }
/*     */         break;
/*     */       } 
/*     */     } 
/* 295 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMaximumAltitude() {
/* 300 */     if (this.m_visualElements != null && this.m_visualElements.size() > 0) {
/* 301 */       GraphicalWorldElement element = this.m_visualElements.get(this.m_visualElements.size() - 1);
/* 302 */       return (short)(int)(element.getCoordinates().getZ() + element.getHeight());
/*     */     } 
/* 304 */     return Short.MIN_VALUE;
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
/*     */   public short getArrivalAltitude(PathFindMover mover, short z, Direction8 inputDir, PathFindParameters params) {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore #5
/*     */     //   3: ldc -2147483648
/*     */     //   5: istore #6
/*     */     //   7: iconst_0
/*     */     //   8: istore #7
/*     */     //   10: aload_0
/*     */     //   11: invokevirtual getVisualElements : ()Ljava/util/ArrayList;
/*     */     //   14: invokevirtual iterator : ()Ljava/util/Iterator;
/*     */     //   17: astore #9
/*     */     //   19: goto -> 673
/*     */     //   22: aload #9
/*     */     //   24: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   29: checkcast com/ankamagames/baseImpl/graphics/alea/worldElement/GraphicalWorldElement
/*     */     //   32: astore #8
/*     */     //   34: aload #8
/*     */     //   36: invokevirtual getElement : ()Lcom/ankamagames/baseImpl/graphics/alea/element/GraphicalElement;
/*     */     //   39: astore #10
/*     */     //   41: aload #8
/*     */     //   43: invokevirtual getCoordinates : ()Lcom/ankamagames/framework/kernel/core/maths/Point3;
/*     */     //   46: invokevirtual getZ : ()S
/*     */     //   49: istore #11
/*     */     //   51: iconst_0
/*     */     //   52: istore #12
/*     */     //   54: invokestatic $SWITCH_TABLE$com$ankamagames$framework$kernel$core$maths$Direction8 : ()[I
/*     */     //   57: aload_3
/*     */     //   58: invokevirtual ordinal : ()I
/*     */     //   61: iaload
/*     */     //   62: tableswitch default -> 345, 1 -> 272, 2 -> 152, 3 -> 196, 4 -> 174, 5 -> 310, 6 -> 130, 7 -> 234, 8 -> 108
/*     */     //   108: aload #10
/*     */     //   110: aload #8
/*     */     //   112: invokevirtual getState : ()I
/*     */     //   115: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   118: invokevirtual isMove3 : ()Z
/*     */     //   121: ifne -> 345
/*     */     //   124: iconst_1
/*     */     //   125: istore #12
/*     */     //   127: goto -> 345
/*     */     //   130: aload #10
/*     */     //   132: aload #8
/*     */     //   134: invokevirtual getState : ()I
/*     */     //   137: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   140: invokevirtual isMove1 : ()Z
/*     */     //   143: ifne -> 345
/*     */     //   146: iconst_1
/*     */     //   147: istore #12
/*     */     //   149: goto -> 345
/*     */     //   152: aload #10
/*     */     //   154: aload #8
/*     */     //   156: invokevirtual getState : ()I
/*     */     //   159: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   162: invokevirtual isMove5 : ()Z
/*     */     //   165: ifne -> 345
/*     */     //   168: iconst_1
/*     */     //   169: istore #12
/*     */     //   171: goto -> 345
/*     */     //   174: aload #10
/*     */     //   176: aload #8
/*     */     //   178: invokevirtual getState : ()I
/*     */     //   181: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   184: invokevirtual isMove7 : ()Z
/*     */     //   187: ifne -> 345
/*     */     //   190: iconst_1
/*     */     //   191: istore #12
/*     */     //   193: goto -> 345
/*     */     //   196: aload #10
/*     */     //   198: aload #8
/*     */     //   200: invokevirtual getState : ()I
/*     */     //   203: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   206: invokevirtual isMove5 : ()Z
/*     */     //   209: ifeq -> 228
/*     */     //   212: aload #10
/*     */     //   214: aload #8
/*     */     //   216: invokevirtual getState : ()I
/*     */     //   219: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   222: invokevirtual isMove7 : ()Z
/*     */     //   225: ifne -> 345
/*     */     //   228: iconst_1
/*     */     //   229: istore #12
/*     */     //   231: goto -> 345
/*     */     //   234: aload #10
/*     */     //   236: aload #8
/*     */     //   238: invokevirtual getState : ()I
/*     */     //   241: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   244: invokevirtual isMove3 : ()Z
/*     */     //   247: ifeq -> 266
/*     */     //   250: aload #10
/*     */     //   252: aload #8
/*     */     //   254: invokevirtual getState : ()I
/*     */     //   257: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   260: invokevirtual isMove1 : ()Z
/*     */     //   263: ifne -> 345
/*     */     //   266: iconst_1
/*     */     //   267: istore #12
/*     */     //   269: goto -> 345
/*     */     //   272: aload #10
/*     */     //   274: aload #8
/*     */     //   276: invokevirtual getState : ()I
/*     */     //   279: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   282: invokevirtual isMove3 : ()Z
/*     */     //   285: ifeq -> 304
/*     */     //   288: aload #10
/*     */     //   290: aload #8
/*     */     //   292: invokevirtual getState : ()I
/*     */     //   295: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   298: invokevirtual isMove5 : ()Z
/*     */     //   301: ifne -> 345
/*     */     //   304: iconst_1
/*     */     //   305: istore #12
/*     */     //   307: goto -> 345
/*     */     //   310: aload #10
/*     */     //   312: aload #8
/*     */     //   314: invokevirtual getState : ()I
/*     */     //   317: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   320: invokevirtual isMove1 : ()Z
/*     */     //   323: ifeq -> 342
/*     */     //   326: aload #10
/*     */     //   328: aload #8
/*     */     //   330: invokevirtual getState : ()I
/*     */     //   333: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   336: invokevirtual isMove7 : ()Z
/*     */     //   339: ifne -> 345
/*     */     //   342: iconst_1
/*     */     //   343: istore #12
/*     */     //   345: iload #7
/*     */     //   347: ifeq -> 468
/*     */     //   350: aload #10
/*     */     //   352: aload #8
/*     */     //   354: invokevirtual getState : ()I
/*     */     //   357: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   360: invokevirtual isMoveBottom : ()Z
/*     */     //   363: ifeq -> 371
/*     */     //   366: iload #12
/*     */     //   368: ifeq -> 398
/*     */     //   371: aload #8
/*     */     //   373: invokevirtual getAltitude : ()B
/*     */     //   376: iload #5
/*     */     //   378: if_icmplt -> 398
/*     */     //   381: aload #8
/*     */     //   383: invokevirtual getAltitude : ()B
/*     */     //   386: iload #5
/*     */     //   388: isub
/*     */     //   389: aload_1
/*     */     //   390: invokeinterface getHeight : ()S
/*     */     //   395: if_icmplt -> 465
/*     */     //   398: aload #10
/*     */     //   400: aload #8
/*     */     //   402: invokevirtual getState : ()I
/*     */     //   405: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   408: invokevirtual isMoveTop : ()Z
/*     */     //   411: ifeq -> 419
/*     */     //   414: iload #12
/*     */     //   416: ifeq -> 468
/*     */     //   419: aload #8
/*     */     //   421: invokevirtual getAltitude : ()B
/*     */     //   424: i2d
/*     */     //   425: aload #8
/*     */     //   427: invokevirtual getHeight : ()D
/*     */     //   430: dadd
/*     */     //   431: iload #5
/*     */     //   433: i2d
/*     */     //   434: dcmpl
/*     */     //   435: iflt -> 468
/*     */     //   438: aload #8
/*     */     //   440: invokevirtual getAltitude : ()B
/*     */     //   443: i2d
/*     */     //   444: aload #8
/*     */     //   446: invokevirtual getHeight : ()D
/*     */     //   449: dadd
/*     */     //   450: iload #5
/*     */     //   452: i2d
/*     */     //   453: dsub
/*     */     //   454: aload_1
/*     */     //   455: invokeinterface getHeight : ()S
/*     */     //   460: i2d
/*     */     //   461: dcmpg
/*     */     //   462: ifge -> 468
/*     */     //   465: iconst_0
/*     */     //   466: istore #7
/*     */     //   468: iload #7
/*     */     //   470: ifne -> 664
/*     */     //   473: aload #10
/*     */     //   475: aload #8
/*     */     //   477: invokevirtual getState : ()I
/*     */     //   480: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   483: invokevirtual isMoveTop : ()Z
/*     */     //   486: ifne -> 580
/*     */     //   489: aload #10
/*     */     //   491: aload #8
/*     */     //   493: invokevirtual getState : ()I
/*     */     //   496: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   499: invokevirtual isWalkable : ()Z
/*     */     //   502: ifeq -> 580
/*     */     //   505: iload #11
/*     */     //   507: iload #6
/*     */     //   509: if_icmplt -> 580
/*     */     //   512: iload #11
/*     */     //   514: iload_2
/*     */     //   515: if_icmpge -> 529
/*     */     //   518: aload_1
/*     */     //   519: invokeinterface getJumpMaxDescendingHeight : ()S
/*     */     //   524: istore #13
/*     */     //   526: goto -> 537
/*     */     //   529: aload_1
/*     */     //   530: invokeinterface getJumpMaxAscendingHeight : ()S
/*     */     //   535: istore #13
/*     */     //   537: iconst_1
/*     */     //   538: istore #14
/*     */     //   540: aload #4
/*     */     //   542: ifnull -> 552
/*     */     //   545: aload #4
/*     */     //   547: getfield m_limitHeightWithJumpCapacity : Z
/*     */     //   550: istore #14
/*     */     //   552: iload #11
/*     */     //   554: iload_2
/*     */     //   555: isub
/*     */     //   556: invokestatic abs : (I)I
/*     */     //   559: iload #13
/*     */     //   561: if_icmple -> 569
/*     */     //   564: iload #14
/*     */     //   566: ifne -> 580
/*     */     //   569: iconst_1
/*     */     //   570: istore #7
/*     */     //   572: iload #11
/*     */     //   574: i2s
/*     */     //   575: istore #5
/*     */     //   577: goto -> 673
/*     */     //   580: aload #10
/*     */     //   582: aload #8
/*     */     //   584: invokevirtual getState : ()I
/*     */     //   587: invokevirtual getStateProperties : (I)Lcom/ankamagames/baseImpl/graphics/alea/element/properties/GraphicalElementProperties;
/*     */     //   590: invokevirtual isMoveBottom : ()Z
/*     */     //   593: ifne -> 664
/*     */     //   596: iload #12
/*     */     //   598: ifne -> 664
/*     */     //   601: aload #8
/*     */     //   603: invokevirtual getAltitude : ()B
/*     */     //   606: iload #6
/*     */     //   608: if_icmplt -> 664
/*     */     //   611: aload #8
/*     */     //   613: invokevirtual getAltitude : ()B
/*     */     //   616: iload_2
/*     */     //   617: if_icmpge -> 631
/*     */     //   620: aload_1
/*     */     //   621: invokeinterface getJumpMaxDescendingHeight : ()S
/*     */     //   626: istore #13
/*     */     //   628: goto -> 639
/*     */     //   631: aload_1
/*     */     //   632: invokeinterface getJumpMaxAscendingHeight : ()S
/*     */     //   637: istore #13
/*     */     //   639: aload #8
/*     */     //   641: invokevirtual getAltitude : ()B
/*     */     //   644: invokestatic abs : (I)I
/*     */     //   647: iload_2
/*     */     //   648: isub
/*     */     //   649: iload #13
/*     */     //   651: if_icmpgt -> 664
/*     */     //   654: iconst_1
/*     */     //   655: istore #7
/*     */     //   657: aload #8
/*     */     //   659: invokevirtual getAltitude : ()B
/*     */     //   662: istore #5
/*     */     //   664: iload #12
/*     */     //   666: ifeq -> 673
/*     */     //   669: iload #11
/*     */     //   671: istore #6
/*     */     //   673: aload #9
/*     */     //   675: invokeinterface hasNext : ()Z
/*     */     //   680: ifne -> 22
/*     */     //   683: iload #7
/*     */     //   685: ifne -> 692
/*     */     //   688: sipush #-32768
/*     */     //   691: ireturn
/*     */     //   692: iload #5
/*     */     //   694: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #317	-> 0
/*     */     //   #318	-> 3
/*     */     //   #319	-> 7
/*     */     //   #321	-> 10
/*     */     //   #323	-> 34
/*     */     //   #324	-> 41
/*     */     //   #326	-> 51
/*     */     //   #328	-> 54
/*     */     //   #328	-> 57
/*     */     //   #330	-> 108
/*     */     //   #331	-> 124
/*     */     //   #332	-> 127
/*     */     //   #334	-> 130
/*     */     //   #335	-> 146
/*     */     //   #336	-> 149
/*     */     //   #338	-> 152
/*     */     //   #339	-> 168
/*     */     //   #340	-> 171
/*     */     //   #342	-> 174
/*     */     //   #343	-> 190
/*     */     //   #344	-> 193
/*     */     //   #346	-> 196
/*     */     //   #347	-> 228
/*     */     //   #348	-> 231
/*     */     //   #350	-> 234
/*     */     //   #351	-> 266
/*     */     //   #352	-> 269
/*     */     //   #354	-> 272
/*     */     //   #355	-> 304
/*     */     //   #356	-> 307
/*     */     //   #358	-> 310
/*     */     //   #359	-> 342
/*     */     //   #364	-> 345
/*     */     //   #365	-> 350
/*     */     //   #366	-> 366
/*     */     //   #367	-> 371
/*     */     //   #369	-> 398
/*     */     //   #370	-> 414
/*     */     //   #371	-> 419
/*     */     //   #372	-> 465
/*     */     //   #376	-> 468
/*     */     //   #378	-> 473
/*     */     //   #379	-> 505
/*     */     //   #381	-> 512
/*     */     //   #382	-> 518
/*     */     //   #384	-> 529
/*     */     //   #386	-> 537
/*     */     //   #387	-> 540
/*     */     //   #390	-> 552
/*     */     //   #391	-> 569
/*     */     //   #392	-> 572
/*     */     //   #393	-> 577
/*     */     //   #399	-> 580
/*     */     //   #400	-> 596
/*     */     //   #402	-> 611
/*     */     //   #403	-> 620
/*     */     //   #405	-> 631
/*     */     //   #407	-> 639
/*     */     //   #408	-> 654
/*     */     //   #409	-> 657
/*     */     //   #415	-> 664
/*     */     //   #416	-> 669
/*     */     //   #321	-> 673
/*     */     //   #419	-> 683
/*     */     //   #420	-> 688
/*     */     //   #423	-> 692
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	695	0	this	Lcom/ankamagames/baseImpl/graphics/alea/WorldCell;
/*     */     //   0	695	1	mover	Lcom/ankamagames/framework/ai/pathfinder/PathFindMover;
/*     */     //   0	695	2	z	S
/*     */     //   0	695	3	inputDir	Lcom/ankamagames/framework/kernel/core/maths/Direction8;
/*     */     //   0	695	4	params	Lcom/ankamagames/framework/ai/pathfinder/PathFindParameters;
/*     */     //   3	692	5	candidateHeight	S
/*     */     //   7	688	6	nextMinimumAltitude	I
/*     */     //   10	685	7	foundValidCandidate	Z
/*     */     //   34	639	8	element	Lcom/ankamagames/baseImpl/graphics/alea/worldElement/GraphicalWorldElement;
/*     */     //   41	632	10	graphicalElement	Lcom/ankamagames/baseImpl/graphics/alea/element/GraphicalElement;
/*     */     //   51	622	11	topAltitude	I
/*     */     //   54	619	12	sideBlocking	Z
/*     */     //   526	3	13	maxZMovement	S
/*     */     //   537	43	13	maxZMovement	S
/*     */     //   540	40	14	limitHeightWithJumpCapacity	Z
/*     */     //   628	3	13	maxZMovement	S
/*     */     //   639	25	13	maxZMovement	S
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
/*     */   public boolean isWalkable(short z) {
/* 429 */     if (this.m_visualElements != null && this.m_visualElements.size() > 0) {
/* 430 */       for (GraphicalWorldElement element : getVisualElements()) {
/* 431 */         if (element.getAltitude() == z) {
/* 432 */           return element.getElement().getStateProperties(element.getState()).isWalkable();
/*     */         }
/*     */       } 
/*     */     }
/* 436 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getMovementValidity(PathFindMover mover, short z, Direction8 direction) {
/* 441 */     if (this.m_visualElements == null) {
/* 442 */       return false;
/*     */     }
/* 444 */     for (GraphicalWorldElement element : getVisualElements()) {
/*     */       
/* 446 */       if (element.getHeight() > 0.0D && element.getAltitude() >= z && element.getAltitude() + element.getHeight() <= (z + mover.getHeight())) {
/* 447 */         GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/*     */         
/* 449 */         switch (direction) {
/*     */           case SOUTH_EAST:
/* 451 */             if (!properties.isMove1()) {
/* 452 */               return false;
/*     */             }
/*     */           case SOUTH_WEST:
/* 455 */             if (!properties.isMove3()) {
/* 456 */               return false;
/*     */             }
/*     */           case NORTH_WEST:
/* 459 */             if (!properties.isMove5()) {
/* 460 */               return false;
/*     */             }
/*     */           case NORTH_EAST:
/* 463 */             if (!properties.isMove7()) {
/* 464 */               return false;
/*     */             }
/*     */           case TOP:
/* 467 */             if (!properties.isMoveTop()) {
/* 468 */               return false;
/*     */             }
/*     */           case null:
/* 471 */             if (!properties.isMoveBottom()) {
/* 472 */               return false;
/*     */             }
/*     */           
/*     */           case NORTH:
/* 476 */             if (!properties.isMove5() || !properties.isMove7()) {
/* 477 */               return false;
/*     */             }
/*     */           case SOUTH:
/* 480 */             if (!properties.isMove1() || !properties.isMove3()) {
/* 481 */               return false;
/*     */             }
/*     */           case EAST:
/* 484 */             if (!properties.isMove1() || !properties.isMove7()) {
/* 485 */               return false;
/*     */             }
/*     */           case WEST:
/* 488 */             if (!properties.isMove3() || !properties.isMove5()) {
/* 489 */               return false;
/*     */             }
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 495 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getMovementAcrossValidity(PathFindMover mover, short incomingZ, Direction8 incomingDir, short outgoingZ, Direction8 outgoingDir, PathFindParameters params) {
/* 500 */     if (this.m_visualElements == null) {
/* 501 */       return true;
/*     */     }
/* 503 */     for (GraphicalWorldElement element : getVisualElements()) {
/*     */       
/* 505 */       GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
/*     */       
/* 507 */       if (element.getHeight() > 0.0D && element.getAltitude() >= incomingZ)
/* 508 */         switch (incomingDir) {
/*     */           case SOUTH_EAST:
/* 510 */             if (!properties.isMove1())
/* 511 */               return false; 
/*     */             break;
/*     */           case SOUTH_WEST:
/* 514 */             if (!properties.isMove3())
/* 515 */               return false; 
/*     */             break;
/*     */           case NORTH_WEST:
/* 518 */             if (!properties.isMove5())
/* 519 */               return false; 
/*     */             break;
/*     */           case NORTH_EAST:
/* 522 */             if (!properties.isMove7()) {
/* 523 */               return false;
/*     */             }
/*     */             break;
/*     */         }  
/* 527 */       if (element.getHeight() > 0.0D && element.getAltitude() >= outgoingZ) {
/* 528 */         switch (outgoingDir) {
/*     */           case SOUTH_EAST:
/* 530 */             if (!properties.isMove5()) {
/* 531 */               return false;
/*     */             }
/*     */           case SOUTH_WEST:
/* 534 */             if (!properties.isMove7()) {
/* 535 */               return false;
/*     */             }
/*     */           case NORTH_WEST:
/* 538 */             if (!properties.isMove1()) {
/* 539 */               return false;
/*     */             }
/*     */           case NORTH_EAST:
/* 542 */             if (!properties.isMove3()) {
/* 543 */               return false;
/*     */             }
/*     */         } 
/*     */       }
/*     */     } 
/* 548 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldElement getHighestElement(int levelId) {
/* 556 */     double hh = -1.7976931348623157E308D;
/* 557 */     GraphicalWorldElement e = null;
/* 558 */     if (this.m_visualElements != null) {
/* 559 */       for (GraphicalWorldElement element : this.m_visualElements) {
/* 560 */         if (element.getLevel() == levelId && element.getWeight() >= hh) {
/* 561 */           hh = element.getWeight() + element.getHeight();
/* 562 */           e = element;
/*     */         } 
/*     */       } 
/*     */     }
/* 566 */     return (WorldElement)e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHighestNotEmptyLevel() {
/* 575 */     int level = 0;
/*     */     
/* 577 */     for (GraphicalWorldElement element : this.m_visualElements) {
/* 578 */       if (element.getLevel() > level) {
/* 579 */         level = element.getLevel();
/*     */       }
/*     */     } 
/* 582 */     return level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldElement getElementWithTopAtAltitude(short altitude) {
/* 593 */     for (int i = this.m_visualElements.size() - 1; i >= 0; i--) {
/* 594 */       WorldElement element = (WorldElement)this.m_visualElements.get(i);
/* 595 */       if (element.getCoordinates().getZ() == altitude)
/* 596 */         return element; 
/*     */     } 
/* 598 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 603 */     return 
/* 604 */       "{WorldCell : (" + 
/* 605 */       this.m_worldX + 
/* 606 */       ", " + 
/* 607 */       this.m_worldY + 
/* 608 */       ") @" + 
/* 609 */       Integer.toHexString(hashCode()) + 
/* 610 */       "}";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */