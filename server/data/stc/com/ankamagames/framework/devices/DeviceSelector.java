/*     */ package com.ankamagames.framework.devices;
/*     */ 
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceSelector
/*     */ {
/*     */   private DeviceSelectorEventsHandler m_handler;
/*     */   private GraphicsDevice m_currentGraphicsDevice;
/*     */   private DisplayMode m_currentDisplayMode;
/*     */   private ArrayList<DataFilter> m_filters;
/*  24 */   protected static final Logger m_logger = Logger.getLogger(DeviceSelector.class);
/*     */   
/*     */   public DeviceSelector(DeviceSelectorEventsHandler handler) {
/*  27 */     this.m_handler = handler;
/*  28 */     this.m_filters = new ArrayList();
/*     */   }
/*     */   
/*     */   public void initialize() {
/*  32 */     if (this.m_handler != null) {
/*  33 */       this.m_handler.initialize(this);
/*  34 */       this.m_currentGraphicsDevice = null;
/*     */       
/*  36 */       GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
/*     */       GraphicsDevice[] arrayOfGraphicsDevice1;
/*  38 */       int j = (arrayOfGraphicsDevice1 = devices).length; for (int i = 0; i < j; i++) { GraphicsDevice device = arrayOfGraphicsDevice1[i];
/*  39 */         this.m_handler.enumDevice(device);
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
/*     */   public void addFilter(DataFilter filter)
/*     */   {
/*  52 */     if (!this.m_filters.contains(filter))
/*  53 */       this.m_filters.add(filter);
/*     */   }
/*     */   
/*     */   public DisplayMode getCurrentDisplayMode() {
/*  57 */     GraphicsDevice defaultDevice = getDefaultGraphicsDevice();
/*  58 */     if (defaultDevice != null)
/*  59 */       return defaultDevice.getDisplayMode();
/*  60 */     return null;
/*     */   }
/*     */   
/*     */   public DisplayMode findDisplayMode(GraphicsDevice device, int width, int height, int depth) {
/*  64 */     if (device != null)
/*     */     {
/*  66 */       DisplayMode[] modes = device.getDisplayModes();
/*  67 */       DisplayMode bestMode = null;
/*     */       DisplayMode[] arrayOfDisplayMode1;
/*  69 */       int j = (arrayOfDisplayMode1 = modes).length; for (int i = 0; i < j; i++) { DisplayMode mode = arrayOfDisplayMode1[i];
/*     */         
/*     */ 
/*     */ 
/*  73 */         if ((mode.getWidth() == width) && (mode.getHeight() == height) && (mode.getBitDepth() == depth)) {
/*  74 */           if (bestMode == null) {
/*  75 */             bestMode = mode;
/*  76 */           } else if (bestMode.getRefreshRate() < mode.getRefreshRate()) {
/*  77 */             bestMode = mode;
/*     */           }
/*     */         }
/*     */       }
/*  81 */       return bestMode;
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public GraphicsDevice getDefaultGraphicsDevice() {
/*  87 */     return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
/*     */   }
/*     */   
/*     */   public GraphicsDevice getCurrentlySelectedGraphicsDevice() {
/*  91 */     return this.m_currentGraphicsDevice;
/*     */   }
/*     */   
/*     */   public DisplayMode getCurrentlySelectedDisplayMode() {
/*  95 */     return this.m_currentDisplayMode;
/*     */   }
/*     */   
/*     */   public void selectGraphicsDevice(GraphicsDevice device) {
/*  99 */     if ((device != null) && (this.m_handler != null)) {
/* 100 */       this.m_currentGraphicsDevice = device;
/*     */       
/* 102 */       DisplayMode[] modes = device.getDisplayModes();
/* 103 */       DisplayMode[] arrayOfDisplayMode1; int j = (arrayOfDisplayMode1 = modes).length; for (int i = 0; i < j; i++) { DisplayMode mode = arrayOfDisplayMode1[i];
/* 104 */         if (this.m_filters.isEmpty()) {
/* 105 */           this.m_handler.enumDisplayMode(mode);
/*     */         } else
/* 107 */           for (DataFilter filter : this.m_filters)
/* 108 */             if (filter.matchFilter(mode))
/* 109 */               this.m_handler.enumDisplayMode(mode);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void selectDisplayMode(DisplayMode mode) {
/* 115 */     this.m_currentDisplayMode = mode;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\devices\DeviceSelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */