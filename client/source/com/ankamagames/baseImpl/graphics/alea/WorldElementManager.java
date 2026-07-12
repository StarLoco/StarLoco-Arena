/*     */ package com.ankamagames.baseImpl.graphics.alea;
/*     */ 
/*     */ import com.ankamagames.alea.AleaDocumentAccessor;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.BrightnessElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.GroupElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.LevelUnpiledElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.OffsetElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.ParticleElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.TeintElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.element.UnknownElement;
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElementFactory;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainerEventsHandler;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ public final class WorldElementManager
/*     */   extends AleaDocumentAccessor
/*     */   implements DocumentContainer
/*     */ {
/*  25 */   private static final WorldElementManager m_instance = new WorldElementManager();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private TIntObjectHashMap<BasicElement> m_elements = new TIntObjectHashMap();
/*     */   
/*     */   private OffsetElement m_offsetElement;
/*     */   
/*     */   private GroupElement m_groupElement;
/*     */   
/*     */   private LevelUnpiledElement m_levelUnpiledElement;
/*     */   
/*     */   private BrightnessElement m_brightnessElement;
/*     */   
/*     */   private TeintElement m_teintElement;
/*     */   private ParticleElement m_particleElement;
/*  42 */   private TIntObjectHashMap<GraphicalElement> m_graphicalElements = new TIntObjectHashMap();
/*     */ 
/*     */ 
/*     */   
/*  46 */   private TIntObjectHashMap<BasicElement> m_customElements = new TIntObjectHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CustomElementFactory m_customElementFactory;
/*     */ 
/*     */ 
/*     */   
/*  55 */   private String m_elementsFileName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldElementManager() {
/*  61 */     setBasePath("contents/data");
/*  62 */     setDocumentExtension(".ade");
/*  63 */     setAleaDocumentTypeCode((byte)69);
/*  64 */     setAleaDocumentVersion((byte)1);
/*  65 */     this.m_elementsFileName = "contents/data/elements.ade";
/*     */   }
/*     */ 
/*     */   
/*     */   public static WorldElementManager getInstance() {
/*  70 */     return m_instance;
/*     */   }
/*     */   
/*     */   public void loadElementFile() {
/*     */     try {
/*  75 */       open(this.m_elementsFileName);
/*  76 */       close();
/*  77 */     } catch (Exception e) {
/*  78 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicalElement getGraphicalElement(int elementId) {
/*  84 */     return (GraphicalElement)this.m_graphicalElements.get(elementId);
/*     */   }
/*     */   
/*     */   public BasicElement getCustomElement(int elementId) {
/*  88 */     return (BasicElement)this.m_customElements.get(elementId);
/*     */   }
/*     */ 
/*     */   
/*     */   public BrightnessElement getBrightnessElement() {
/*  93 */     return this.m_brightnessElement;
/*     */   }
/*     */   public TeintElement getTeintElement() {
/*  96 */     return this.m_teintElement;
/*     */   }
/*     */   
/*     */   public TIntObjectHashMap<BasicElement> getCustomElements() {
/* 100 */     return this.m_customElements;
/*     */   }
/*     */   
/*     */   public GroupElement getGroupElement() {
/* 104 */     return this.m_groupElement;
/*     */   }
/*     */   
/*     */   public LevelUnpiledElement getLevelUnpiledElement() {
/* 108 */     return this.m_levelUnpiledElement;
/*     */   }
/*     */   
/*     */   public OffsetElement getOffsetElement() {
/* 112 */     return this.m_offsetElement;
/*     */   }
/*     */   
/*     */   public ParticleElement getParticleElement() {
/* 116 */     return this.m_particleElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public TIntObjectHashMap<BasicElement> getElements() {
/* 121 */     return this.m_elements;
/*     */   }
/*     */   
/*     */   public void setElements(TIntObjectHashMap<BasicElement> elements) {
/* 125 */     this.m_elements = elements;
/*     */   }
/*     */   
/*     */   public String getElementsFileName() {
/* 129 */     return this.m_elementsFileName;
/*     */   }
/*     */   
/*     */   public void setElementsFileName(String elementsFileName) {
/* 133 */     this.m_elementsFileName = elementsFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void open(String fileName) throws Exception {
/* 143 */     super.open(fileName);
/* 144 */     read(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DocumentContainer container) {
/* 154 */     super.read(container);
/*     */     
/* 156 */     if (this.m_streamBuffer == null || container == null) {
/*     */       return;
/*     */     }
/* 159 */     while (this.m_streamBuffer.position() < this.m_streamBuffer.limit()) {
/* 160 */       GraphicalElement graphicalElement1; TeintElement teintElement; OffsetElement offsetElement; GroupElement groupElement; LevelUnpiledElement levelUnpiledElement; ParticleElement particleElement; BrightnessElement brightnessElement; BasicElement basicElement1; UnknownElement unknownElement; GraphicalElement graphicalElement; int elementId = this.m_streamBuffer.getInt();
/* 161 */       int elementType = this.m_streamBuffer.getShort();
/*     */       
/* 163 */       BasicElement newElement = null;
/* 164 */       switch (elementType) {
/*     */         
/*     */         case 2:
/* 167 */           graphicalElement = new GraphicalElement(elementId);
/* 168 */           this.m_graphicalElements.put(elementId, graphicalElement);
/* 169 */           graphicalElement1 = graphicalElement;
/*     */           break;
/*     */         
/*     */         case 3:
/* 173 */           this.m_teintElement = new TeintElement(elementId);
/* 174 */           teintElement = this.m_teintElement;
/*     */           break;
/*     */         
/*     */         case 4:
/* 178 */           this.m_offsetElement = new OffsetElement(elementId);
/* 179 */           offsetElement = this.m_offsetElement;
/*     */           break;
/*     */         
/*     */         case 6:
/* 183 */           this.m_groupElement = new GroupElement(elementId);
/* 184 */           groupElement = this.m_groupElement;
/*     */           break;
/*     */         
/*     */         case 8:
/* 188 */           this.m_levelUnpiledElement = new LevelUnpiledElement(elementId);
/* 189 */           levelUnpiledElement = this.m_levelUnpiledElement;
/*     */           break;
/*     */         
/*     */         case 9:
/* 193 */           this.m_particleElement = new ParticleElement(elementId);
/* 194 */           particleElement = this.m_particleElement;
/*     */           break;
/*     */         
/*     */         case 10:
/* 198 */           this.m_brightnessElement = new BrightnessElement(elementId);
/* 199 */           brightnessElement = this.m_brightnessElement;
/*     */           break;
/*     */         
/*     */         default:
/* 203 */           if (this.m_customElementFactory != null) {
/* 204 */             basicElement1 = this.m_customElementFactory.createElement(elementId, elementType);
/* 205 */             if (basicElement1 instanceof GraphicalElement) {
/* 206 */               this.m_graphicalElements.put(elementId, basicElement1);
/*     */             }
/*     */           } 
/*     */           
/* 210 */           if (basicElement1 == null)
/*     */           {
/* 212 */             unknownElement = new UnknownElement(elementId);
/*     */           }
/* 214 */           this.m_customElements.put(elementId, unknownElement);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 219 */       unknownElement.read(this.m_streamBuffer);
/* 220 */       addElement((BasicElement)unknownElement);
/*     */     } 
/*     */     
/* 223 */     container.notifyOnLoadComplete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElement(BasicElement element) {
/* 230 */     this.m_elements.put(element.getId(), element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicElement getElement(int elementId) {
/* 238 */     return (BasicElement)this.m_elements.get(elementId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeElement(int elementId) {
/* 245 */     this.m_elements.remove(elementId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentContainer getNewDocumentContainer() {
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentEntry getEntryByName(String name) {
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<DocumentEntry> getEntriesByName(String name) {
/* 275 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEventsHandler(DocumentContainerEventsHandler handler) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnLoadBegin() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnLoadComplete() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnLoadError(String errorMessage) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnSaveBegin() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnSaveComplete() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOnSaveError(String errorMessage) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomElementFactory(CustomElementFactory factory) {
/* 332 */     this.m_customElementFactory = factory;
/* 333 */     WorldElementFactory.setCustomElementFactory(this.m_customElementFactory);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldElementManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */