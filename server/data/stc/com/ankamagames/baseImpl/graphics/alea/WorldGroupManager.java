/*     */ package com.ankamagames.baseImpl.graphics.alea;
/*     */ 
/*     */ import com.ankamagames.alea.AleaDocumentAccessor;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainerEventsHandler;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGroupManager
/*     */   extends AleaDocumentAccessor
/*     */   implements DocumentContainer
/*     */ {
/*     */   private static final int GROUP_ID_INDEX = 0;
/*     */   private static final int GROUP_LEVEL_INDEX = 1;
/*  26 */   private static final WorldGroupManager m_instance = new WorldGroupManager();
/*  27 */   private TIntObjectHashMap<WorldGroup> m_groups = new TIntObjectHashMap();
/*     */   
/*  29 */   private String m_groupsFileName = "";
/*     */   
/*  31 */   private TIntObjectHashMap<int[]> m_groupFromInstance = new TIntObjectHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldGroupManager()
/*     */   {
/*  38 */     setBasePath("contents/data");
/*  39 */     setDocumentExtension(".adg");
/*  40 */     setAleaDocumentTypeCode((byte)71);
/*  41 */     setAleaDocumentVersion((byte)1);
/*  42 */     this.m_groupsFileName = "file:\\\\Waked\\WakfuContents\\groups.adg";
/*     */   }
/*     */   
/*     */   public static WorldGroupManager getInstance() {
/*  46 */     return m_instance;
/*     */   }
/*     */   
/*     */   public void loadGroupFile() {
/*     */     try {
/*  51 */       open(this.m_groupsFileName);
/*  52 */       close();
/*     */     } catch (Exception e) {
/*  54 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void open(String fileName)
/*     */     throws Exception
/*     */   {
/*  65 */     super.open(fileName);
/*  66 */     read(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void read(DocumentContainer container)
/*     */   {
/*  76 */     super.read(container);
/*     */     
/*  78 */     if ((this.m_streamBuffer == null) || (container == null)) {
/*  79 */       return;
/*     */     }
/*  81 */     while (this.m_streamBuffer.position() < this.m_streamBuffer.limit()) {
/*  82 */       int groupId = this.m_streamBuffer.getInt();
/*     */       
/*  84 */       WorldGroup group = new WorldGroup(groupId);
/*     */       
/*  86 */       int groupLayerCount = this.m_streamBuffer.getInt();
/*  87 */       for (int i = 0; i < groupLayerCount; i++)
/*     */       {
/*  89 */         int layerId = this.m_streamBuffer.getInt();
/*  90 */         int groupVisibleLayerCount = this.m_streamBuffer.getInt();
/*     */         
/*  92 */         for (int j = 0; j < groupVisibleLayerCount; j++)
/*     */         {
/*  94 */           int visibleLayerId = this.m_streamBuffer.getInt();
/*  95 */           group.addVisibleLayer(layerId, visibleLayerId);
/*     */         }
/*     */       }
/*     */       
/*  99 */       addWorldGroup(group);
/*     */     }
/*     */     
/* 102 */     container.notifyOnLoadComplete();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addWorldGroup(WorldGroup group)
/*     */   {
/* 111 */     this.m_groups.put(group.getId(), group);
/*     */   }
/*     */   
/*     */   public WorldGroup getWorldGroup(int id)
/*     */   {
/* 116 */     return (WorldGroup)this.m_groups.get(id);
/*     */   }
/*     */   
/*     */ 
/*     */   public DocumentContainer getNewDocumentContainer()
/*     */   {
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   public DocumentEntry getEntryByName(String name) {
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   public ArrayList<DocumentEntry> getEntriesByName(String name) {
/* 130 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addEventsHandler(DocumentContainerEventsHandler handler) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void notifyOnLoadBegin() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void notifyOnLoadComplete() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void notifyOnLoadError(String errorMessage) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void notifyOnSaveBegin() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void notifyOnSaveComplete() {}
/*     */   
/*     */ 
/*     */   public void notifyOnSaveError(String errorMessage) {}
/*     */   
/*     */ 
/*     */   public void registerInstanceGroupInformation(int instanceId, byte[] param, int level)
/*     */   {
/* 163 */     int[] groupParam = new int[2];
/* 164 */     groupParam[1] = level;
/*     */     
/* 166 */     ByteBuffer parseByte = ByteBuffer.allocate(4);
/* 167 */     parseByte.order(ByteOrder.LITTLE_ENDIAN);
/* 168 */     parseByte.put(param, 1, 4);
/* 169 */     parseByte.rewind();
/*     */     
/*     */ 
/* 172 */     groupParam[0] = parseByte.getInt();
/*     */     
/* 174 */     this.m_groupFromInstance.put(instanceId, groupParam);
/*     */   }
/*     */   
/*     */   public WorldGroup getGroupFromInstance(int instanceId)
/*     */   {
/* 179 */     int[] groupInstance = (int[])this.m_groupFromInstance.get(instanceId);
/*     */     
/* 181 */     if (groupInstance == null) {
/* 182 */       return null;
/*     */     }
/* 184 */     return getWorldGroup(groupInstance[0]);
/*     */   }
/*     */   
/*     */   public int getLevelFromInstance(int instanceId)
/*     */   {
/* 189 */     int[] groupInstance = (int[])this.m_groupFromInstance.get(instanceId);
/*     */     
/* 191 */     if (groupInstance == null) {
/* 192 */       return 0;
/*     */     }
/* 194 */     return groupInstance[1];
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldGroupManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */