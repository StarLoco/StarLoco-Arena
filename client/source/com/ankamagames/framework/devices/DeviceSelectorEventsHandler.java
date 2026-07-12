package com.ankamagames.framework.devices;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;

public interface DeviceSelectorEventsHandler {
  void initialize(DeviceSelector paramDeviceSelector);
  
  void enumDevice(GraphicsDevice paramGraphicsDevice);
  
  void enumDisplayMode(DisplayMode paramDisplayMode);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\devices\DeviceSelectorEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */