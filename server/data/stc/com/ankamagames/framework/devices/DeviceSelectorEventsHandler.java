package com.ankamagames.framework.devices;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;

public abstract interface DeviceSelectorEventsHandler
{
  public abstract void initialize(DeviceSelector paramDeviceSelector);
  
  public abstract void enumDevice(GraphicsDevice paramGraphicsDevice);
  
  public abstract void enumDisplayMode(DisplayMode paramDisplayMode);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\devices\DeviceSelectorEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */