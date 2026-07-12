/*     */ package com.ankamagames.dofusarena.common.game.team;
/*     */ 
/*     */ import gnu.trove.TLongArrayList;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeamPreset
/*     */ {
/*     */   private short m_id;
/*     */   private String m_name;
/*  22 */   private final TLongArrayList m_fightersIds = new TLongArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   public TeamPreset()
/*     */   {
/*  28 */     this.m_id = -1;
/*  29 */     this.m_name = "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getId()
/*     */   {
/*  37 */     return this.m_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  44 */     return this.m_name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  51 */     this.m_name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TLongArrayList getFightersIds()
/*     */   {
/*  60 */     return this.m_fightersIds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  69 */     return this.m_fightersIds.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/*  78 */     return this.m_fightersIds.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/*  85 */     this.m_fightersIds.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(long fighterInformationId)
/*     */   {
/*  95 */     this.m_fightersIds.add(fighterInformationId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(long[] fighterInformationIds)
/*     */   {
/* 103 */     this.m_fightersIds.clear();
/* 104 */     long[] arrayOfLong; int j = (arrayOfLong = fighterInformationIds).length; for (int i = 0; i < j; i++) { long id = arrayOfLong[i];
/* 105 */       add(id);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(long fighterInformationId)
/*     */   {
/* 116 */     this.m_fightersIds.remove(this.m_fightersIds.indexOf(fighterInformationId));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(long fighterInformationId)
/*     */   {
/* 124 */     return this.m_fightersIds.contains(fighterInformationId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int getBinarSerialisationAverageLength()
/*     */   {
/* 131 */     return 404;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] serialize()
/*     */   {
/* 143 */     byte[] name = this.m_name.getBytes();
/*     */     
/* 145 */     ByteBuffer buffer = ByteBuffer.allocate(3 + name.length + 1 + this.m_fightersIds.size() * 8);
/*     */     
/*     */ 
/* 148 */     buffer.putShort(this.m_id);
/*     */     
/* 150 */     buffer.put((byte)name.length);
/* 151 */     buffer.put(name);
/*     */     
/* 153 */     buffer.put((byte)this.m_fightersIds.size());
/* 154 */     long[] arrayOfLong; int j = (arrayOfLong = this.m_fightersIds.toNativeArray()).length; for (int i = 0; i < j; i++) { long id = arrayOfLong[i];
/* 155 */       buffer.putLong(id);
/*     */     }
/* 157 */     return buffer.array();
/*     */   }
/*     */   
/*     */   public void setId(short id)
/*     */   {
/* 162 */     this.m_id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unserialize(byte[] serial)
/*     */   {
/* 172 */     unserialize(ByteBuffer.wrap(serial));
/*     */   }
/*     */   
/*     */   public void unserialize(ByteBuffer buffer)
/*     */   {
/* 177 */     this.m_id = buffer.getShort();
/*     */     
/* 179 */     byte[] name = new byte[buffer.get()];
/* 180 */     buffer.get(name);
/* 181 */     this.m_name = new String(name);
/*     */     
/* 183 */     int i = buffer.get();
/* 184 */     for (int j = 0; j < i; j++) {
/* 185 */       add(buffer.getLong());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 236 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 244 */     return getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\team\TeamPreset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */