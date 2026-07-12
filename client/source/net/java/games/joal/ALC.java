package net.java.games.joal;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public interface ALC extends ALCConstants {
  boolean alcCaptureCloseDevice(ALCdevice paramALCdevice);
  
  ALCdevice alcCaptureOpenDevice(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3);
  
  ALCdevice alcCaptureOpenDevice(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void alcCaptureSamples(ALCdevice paramALCdevice, Buffer paramBuffer, int paramInt);
  
  void alcCaptureStart(ALCdevice paramALCdevice);
  
  void alcCaptureStop(ALCdevice paramALCdevice);
  
  boolean alcCloseDevice(ALCdevice paramALCdevice);
  
  ALCcontext alcCreateContext(ALCdevice paramALCdevice, IntBuffer paramIntBuffer);
  
  ALCcontext alcCreateContext(ALCdevice paramALCdevice, int[] paramArrayOfint, int paramInt);
  
  void alcDestroyContext(ALCcontext paramALCcontext);
  
  ALCdevice alcGetContextsDevice(ALCcontext paramALCcontext);
  
  ALCcontext alcGetCurrentContext();
  
  int alcGetEnumValue(ALCdevice paramALCdevice, ByteBuffer paramByteBuffer);
  
  int alcGetEnumValue(ALCdevice paramALCdevice, byte[] paramArrayOfbyte, int paramInt);
  
  int alcGetError(ALCdevice paramALCdevice);
  
  void alcGetIntegerv(ALCdevice paramALCdevice, int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alcGetIntegerv(ALCdevice paramALCdevice, int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  ByteBuffer alcGetStringImpl(ALCdevice paramALCdevice, int paramInt);
  
  boolean alcIsExtensionPresent(ALCdevice paramALCdevice, String paramString);
  
  boolean alcMakeContextCurrent(ALCcontext paramALCcontext);
  
  ALCdevice alcOpenDevice(String paramString);
  
  void alcProcessContext(ALCcontext paramALCcontext);
  
  void alcSuspendContext(ALCcontext paramALCcontext);
  
  String alcGetString(ALCdevice paramALCdevice, int paramInt);
  
  String[] alcGetDeviceSpecifiers();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\ALC.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */