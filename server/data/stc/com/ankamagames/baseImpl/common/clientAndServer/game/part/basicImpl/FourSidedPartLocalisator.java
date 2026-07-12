/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.part.basicImpl;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.part.PartLocalisator;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FourSidedPartLocalisator
/*     */   implements PartLocalisator<CharacterPart>
/*     */ {
/*  21 */   protected static final Logger m_logger = Logger.getLogger(FourSidedPartLocalisator.class);
/*     */   
/*     */ 
/*     */   private Point3 m_position;
/*     */   
/*     */ 
/*     */   private Direction8 m_direction;
/*     */   
/*     */ 
/*  30 */   private static final TIntObjectHashMap<CharacterPart> m_parts = new TIntObjectHashMap();
/*     */   
/*     */   static {
/*  33 */     m_parts.put(0, new CharacterPart(0));
/*  34 */     m_parts.put(1, new CharacterPart(1));
/*  35 */     m_parts.put(2, new CharacterPart(2));
/*  36 */     m_parts.put(3, new CharacterPart(3));
/*     */   }
/*     */   
/*     */   public CharacterPart getPartFromId(int id) {
/*  40 */     return (CharacterPart)m_parts.get(id);
/*     */   }
/*     */   
/*     */   public void update(Point3 position, Direction8 currentOrientation)
/*     */   {
/*  45 */     this.m_position = position;
/*  46 */     this.m_direction = currentOrientation;
/*     */   }
/*     */   
/*     */   public List<CharacterPart> getPartsInSightFromPoint(Point3 position) {
/*  50 */     List<CharacterPart> list = new Vector();
/*  51 */     list.add(getMainPartInSightFromPosition(position));
/*  52 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharacterPart getMainPartInSightFromPosition(Point3 position)
/*     */   {
/*  60 */     if ((this.m_direction == null) || (this.m_position == null)) {
/*  61 */       m_logger.error("direction ou position null : update partLocalisator first");
/*  62 */       return null;
/*     */     }
/*     */     
/*     */ 
/*  66 */     if (this.m_position.equals(position)) { return (CharacterPart)m_parts.get(0);
/*     */     }
/*  68 */     Vector3 vDir1 = new Vector3(this.m_direction.getVector()[0], this.m_direction.getVector()[1], 0.0D);
/*     */     
/*     */ 
/*  71 */     Vector3 vDir2 = new Vector3(this.m_position.getX() - position.getX(), 
/*  72 */       this.m_position.getY() - position.getY(), 
/*  73 */       this.m_position.getZ() - position.getZ());
/*     */     
/*  75 */     vDir2 = vDir2.normalize();
/*     */     
/*     */ 
/*  78 */     double result = vDir2.dot(vDir1);
/*     */     
/*  80 */     if (result >= 0.5D) {
/*  81 */       return (CharacterPart)m_parts.get(2);
/*     */     }
/*  83 */     if (result >= -0.5D) {
/*  84 */       return (CharacterPart)m_parts.get(3);
/*     */     }
/*     */     
/*     */ 
/*  88 */     return (CharacterPart)m_parts.get(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharacterPart getMainPartInSightFromVector(Vector3 vector)
/*     */   {
/*  99 */     if (this.m_direction == null) {
/* 100 */       m_logger.error("direction null : update partLocalisator first");
/* 101 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 105 */     if ((vector.getX() == 0.0D) && (vector.getY() == 0.0D)) { return (CharacterPart)m_parts.get(0);
/*     */     }
/* 107 */     Vector3 vDir1 = new Vector3(this.m_direction.getVector()[0], this.m_direction.getVector()[1], 0.0D);
/*     */     
/*     */ 
/* 110 */     vector = vector.normalize();
/*     */     
/*     */ 
/* 113 */     double result = vector.dot(vDir1);
/*     */     
/* 115 */     if (result >= 0.5D) {
/* 116 */       return (CharacterPart)m_parts.get(2);
/*     */     }
/* 118 */     if (result >= -0.5D) {
/* 119 */       return (CharacterPart)m_parts.get(3);
/*     */     }
/*     */     
/*     */ 
/* 123 */     return (CharacterPart)m_parts.get(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 130 */     this.m_position = null;
/* 131 */     this.m_direction = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\part\basicImpl\FourSidedPartLocalisator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */