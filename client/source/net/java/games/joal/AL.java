package net.java.games.joal;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface AL extends ALConstants {
  public static final int ALC_EFX_MAJOR_VERSION = 131073;
  
  public static final int ALC_EFX_MINOR_VERSION = 131074;
  
  public static final int ALC_MAX_AUXILIARY_SENDS = 131075;
  
  public static final double LOWPASS_MIN_GAIN = 0.0D;
  
  public static final float LOWPASS_MAX_GAIN = 1.0F;
  
  public static final float LOWPASS_DEFAULT_GAIN = 1.0F;
  
  public static final double LOWPASS_MIN_GAINHF = 0.0D;
  
  public static final float LOWPASS_MAX_GAINHF = 1.0F;
  
  public static final float LOWPASS_DEFAULT_GAINHF = 1.0F;
  
  public static final double HIGHPASS_MIN_GAIN = 0.0D;
  
  public static final float HIGHPASS_MAX_GAIN = 1.0F;
  
  public static final float HIGHPASS_DEFAULT_GAIN = 1.0F;
  
  public static final double HIGHPASS_MIN_GAINLF = 0.0D;
  
  public static final float HIGHPASS_MAX_GAINLF = 1.0F;
  
  public static final float HIGHPASS_DEFAULT_GAINLF = 1.0F;
  
  public static final double BANDPASS_MIN_GAIN = 0.0D;
  
  public static final float BANDPASS_MAX_GAIN = 1.0F;
  
  public static final float BANDPASS_DEFAULT_GAIN = 1.0F;
  
  public static final double BANDPASS_MIN_GAINHF = 0.0D;
  
  public static final float BANDPASS_MAX_GAINHF = 1.0F;
  
  public static final float BANDPASS_DEFAULT_GAINHF = 1.0F;
  
  public static final double BANDPASS_MIN_GAINLF = 0.0D;
  
  public static final float BANDPASS_MAX_GAINLF = 1.0F;
  
  public static final float BANDPASS_DEFAULT_GAINLF = 1.0F;
  
  void alAuxiliaryEffectSlotf(int paramInt1, int paramInt2, float paramFloat);
  
  void alAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alAuxiliaryEffectSloti(int paramInt1, int paramInt2, int paramInt3);
  
  void alAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alBuffer3f(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3);
  
  void alBuffer3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  void alBufferData(int paramInt1, int paramInt2, Buffer paramBuffer, int paramInt3, int paramInt4);
  
  void alBufferf(int paramInt1, int paramInt2, float paramFloat);
  
  void alBufferfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alBufferfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alBufferi(int paramInt1, int paramInt2, int paramInt3);
  
  void alBufferiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alBufferiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alDeleteAuxiliaryEffectSlots(int paramInt, IntBuffer paramIntBuffer);
  
  void alDeleteAuxiliaryEffectSlots(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alDeleteBuffers(int paramInt, IntBuffer paramIntBuffer);
  
  void alDeleteBuffers(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alDeleteEffects(int paramInt, IntBuffer paramIntBuffer);
  
  void alDeleteEffects(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alDeleteFilters(int paramInt, IntBuffer paramIntBuffer);
  
  void alDeleteFilters(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alDeleteSources(int paramInt, IntBuffer paramIntBuffer);
  
  void alDeleteSources(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alDisable(int paramInt);
  
  void alDistanceModel(int paramInt);
  
  void alDopplerFactor(float paramFloat);
  
  void alDopplerVelocity(float paramFloat);
  
  void alEffectf(int paramInt1, int paramInt2, float paramFloat);
  
  void alEffectfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alEffectfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alEffecti(int paramInt1, int paramInt2, int paramInt3);
  
  void alEffectiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alEffectiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alEnable(int paramInt);
  
  void alFilterf(int paramInt1, int paramInt2, float paramFloat);
  
  void alFilterfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alFilterfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alFilteri(int paramInt1, int paramInt2, int paramInt3);
  
  void alFilteriv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alFilteriv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alGenAuxiliaryEffectSlots(int paramInt, IntBuffer paramIntBuffer);
  
  void alGenAuxiliaryEffectSlots(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGenBuffers(int paramInt, IntBuffer paramIntBuffer);
  
  void alGenBuffers(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGenEffects(int paramInt, IntBuffer paramIntBuffer);
  
  void alGenEffects(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGenFilters(int paramInt, IntBuffer paramIntBuffer);
  
  void alGenFilters(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGenSources(int paramInt, IntBuffer paramIntBuffer);
  
  void alGenSources(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGetAuxiliaryEffectSlotf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetAuxiliaryEffectSlotf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetAuxiliaryEffectSlotfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetAuxiliaryEffectSloti(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetAuxiliaryEffectSloti(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alGetAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetAuxiliaryEffectSlotiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  boolean alGetBoolean(int paramInt);
  
  void alGetBooleanv(int paramInt, ByteBuffer paramByteBuffer);
  
  void alGetBooleanv(int paramInt1, byte[] paramArrayOfbyte, int paramInt2);
  
  void alGetBuffer3f(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3);
  
  void alGetBuffer3f(int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float[] paramArrayOffloat3, int paramInt5);
  
  void alGetBuffer3i(int paramInt1, int paramInt2, IntBuffer paramIntBuffer1, IntBuffer paramIntBuffer2, IntBuffer paramIntBuffer3);
  
  void alGetBuffer3i(int paramInt1, int paramInt2, int[] paramArrayOfint1, int paramInt3, int[] paramArrayOfint2, int paramInt4, int[] paramArrayOfint3, int paramInt5);
  
  void alGetBufferf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetBufferf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetBufferfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetBufferfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetBufferi(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetBufferi(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alGetBufferiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetBufferiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  double alGetDouble(int paramInt);
  
  void alGetDoublev(int paramInt, DoubleBuffer paramDoubleBuffer);
  
  void alGetDoublev(int paramInt1, double[] paramArrayOfdouble, int paramInt2);
  
  void alGetEffectf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetEffectf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetEffectfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetEffectfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetEffecti(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetEffecti(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alGetEffectiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetEffectiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  int alGetEnumValue(String paramString);
  
  int alGetError();
  
  void alGetFilterf(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetFilterf(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetFilterfv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetFilterfv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetFilteri(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetFilteri(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alGetFilteriv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetFilteriv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  float alGetFloat(int paramInt);
  
  void alGetFloatv(int paramInt, FloatBuffer paramFloatBuffer);
  
  void alGetFloatv(int paramInt1, float[] paramArrayOffloat, int paramInt2);
  
  int alGetInteger(int paramInt);
  
  void alGetIntegerv(int paramInt, IntBuffer paramIntBuffer);
  
  void alGetIntegerv(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGetListener3f(int paramInt, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3);
  
  void alGetListener3f(int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3, float[] paramArrayOffloat3, int paramInt4);
  
  void alGetListener3i(int paramInt, IntBuffer paramIntBuffer1, IntBuffer paramIntBuffer2, IntBuffer paramIntBuffer3);
  
  void alGetListener3i(int paramInt1, int[] paramArrayOfint1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int[] paramArrayOfint3, int paramInt4);
  
  void alGetListenerf(int paramInt, FloatBuffer paramFloatBuffer);
  
  void alGetListenerf(int paramInt1, float[] paramArrayOffloat, int paramInt2);
  
  void alGetListenerfv(int paramInt, FloatBuffer paramFloatBuffer);
  
  void alGetListenerfv(int paramInt1, float[] paramArrayOffloat, int paramInt2);
  
  void alGetListeneri(int paramInt, IntBuffer paramIntBuffer);
  
  void alGetListeneri(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGetListeneriv(int paramInt, IntBuffer paramIntBuffer);
  
  void alGetListeneriv(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alGetSource3f(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3);
  
  void alGetSource3f(int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float[] paramArrayOffloat3, int paramInt5);
  
  void alGetSource3i(int paramInt1, int paramInt2, IntBuffer paramIntBuffer1, IntBuffer paramIntBuffer2, IntBuffer paramIntBuffer3);
  
  void alGetSource3i(int paramInt1, int paramInt2, int[] paramArrayOfint1, int paramInt3, int[] paramArrayOfint2, int paramInt4, int[] paramArrayOfint3, int paramInt5);
  
  void alGetSourcef(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetSourcef(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetSourcefv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alGetSourcefv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alGetSourcei(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetSourcei(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alGetSourceiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alGetSourceiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  String alGetString(int paramInt);
  
  boolean alIsAuxiliaryEffectSlot(int paramInt);
  
  boolean alIsBuffer(int paramInt);
  
  boolean alIsEffect(int paramInt);
  
  boolean alIsEnabled(int paramInt);
  
  boolean alIsExtensionPresent(String paramString);
  
  boolean alIsFilter(int paramInt);
  
  boolean alIsSource(int paramInt);
  
  void alListener3f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);
  
  void alListener3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void alListenerf(int paramInt, float paramFloat);
  
  void alListenerfv(int paramInt, FloatBuffer paramFloatBuffer);
  
  void alListenerfv(int paramInt1, float[] paramArrayOffloat, int paramInt2);
  
  void alListeneri(int paramInt1, int paramInt2);
  
  void alListeneriv(int paramInt, IntBuffer paramIntBuffer);
  
  void alListeneriv(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alSource3f(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3);
  
  void alSource3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  void alSourcePause(int paramInt);
  
  void alSourcePausev(int paramInt, IntBuffer paramIntBuffer);
  
  void alSourcePausev(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alSourcePlay(int paramInt);
  
  void alSourcePlayv(int paramInt, IntBuffer paramIntBuffer);
  
  void alSourcePlayv(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alSourceQueueBuffers(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alSourceQueueBuffers(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alSourceRewind(int paramInt);
  
  void alSourceRewindv(int paramInt, IntBuffer paramIntBuffer);
  
  void alSourceRewindv(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alSourceStop(int paramInt);
  
  void alSourceStopv(int paramInt, IntBuffer paramIntBuffer);
  
  void alSourceStopv(int paramInt1, int[] paramArrayOfint, int paramInt2);
  
  void alSourceUnqueueBuffers(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alSourceUnqueueBuffers(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alSourcef(int paramInt1, int paramInt2, float paramFloat);
  
  void alSourcefv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer);
  
  void alSourcefv(int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
  
  void alSourcei(int paramInt1, int paramInt2, int paramInt3);
  
  void alSourceiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  void alSourceiv(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3);
  
  void alSpeedOfSound(float paramFloat);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\AL.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */