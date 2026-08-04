/*
 * Decompiled with CFR 0.152.
 */
package com.xuggle.xuggler;

import com.xuggle.xuggler.Xuggler;

public class XugglerJNI {
    XugglerJNI() {
    }

    public static void noop() {
    }

    public static final native int IProperty_PROPERTY_CONST_get();

    public static final native int IProperty_PROPERTY_UNKNOWN_get();

    public static final native int IProperty_FLAG_ENCODING_PARAM_get();

    public static final native int IProperty_FLAG_DECODING_PARAM_get();

    public static final native int IProperty_FLAG_METADATA_get();

    public static final native int IProperty_FLAG_AUDIO_PARAM_get();

    public static final native int IProperty_FLAG_VIDEO_PARAM_get();

    public static final native int IProperty_FLAG_SUBTITLE_PARAM_get();

    public static final native String IProperty_getName(long var0, aoc_2 var2);

    public static final native String IProperty_getHelp(long var0, aoc_2 var2);

    public static final native String IProperty_getUnit(long var0, aoc_2 var2);

    public static final native int IProperty_getType(long var0, aoc_2 var2);

    public static final native int IProperty_getFlags(long var0, aoc_2 var2);

    public static final native long IProperty_getDefault(long var0, aoc_2 var2);

    public static final native double IProperty_getDefaultAsDouble(long var0, aoc_2 var2);

    public static final native int IProperty_getNumFlagSettings(long var0, aoc_2 var2);

    public static final native long IProperty_getFlagConstant__SWIG_0(long var0, aoc_2 var2, int var3);

    public static final native long IProperty_getFlagConstant__SWIG_1(long var0, aoc_2 var2, String var3);

    public static final native int IPixelFormat_NONE_get();

    public static final native int IPixelFormat_YUV_Y_get();

    public static final native int IPixelFormat_YUV_U_get();

    public static final native int IPixelFormat_YUV_V_get();

    public static final native short IPixelFormat_getYUV420PPixel(long var0, ayh var2, int var3, int var4, int var5);

    public static final native void IPixelFormat_setYUV420PPixel(long var0, ayh var2, int var3, int var4, int var5, short var6);

    public static final native int IPixelFormat_getYUV420PPixelOffset(long var0, ayh var2, int var3, int var4, int var5);

    public static final native int IRational_getNumerator(long var0, xv_1 var2);

    public static final native int IRational_getDenominator(long var0, xv_1 var2);

    public static final native long IRational_copy(long var0, xv_1 var2);

    public static final native int IRational_compareTo(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native int IRational_sCompareTo(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native double IRational_getDouble(long var0, xv_1 var2);

    public static final native int IRational_reduce(long var0, xv_1 var2, long var3, long var5, long var7);

    public static final native int IRational_sReduce(long var0, xv_1 var2, long var3, long var5, long var7);

    public static final native long IRational_multiply(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_sMultiply(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_divide(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_sDivide(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_subtract(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_sSubtract(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_add(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_sAdd(long var0, xv_1 var2, long var3, xv_1 var5);

    public static final native long IRational_rescale__SWIG_0(long var0, xv_1 var2, long var3, long var5, xv_1 var7);

    public static final native long IRational_sRescale__SWIG_0(long var0, long var2, xv_1 var4, long var5, xv_1 var7);

    public static final native long IRational_make__SWIG_0();

    public static final native long IRational_make__SWIG_1(double var0);

    public static final native long IRational_make__SWIG_2(long var0, xv_1 var2);

    public static final native long IRational_make__SWIG_3(int var0, int var1);

    public static final native int IRational_ROUND_ZERO_get();

    public static final native int IRational_ROUND_INF_get();

    public static final native int IRational_ROUND_DOWN_get();

    public static final native int IRational_ROUND_UP_get();

    public static final native int IRational_ROUND_NEAR_INF_get();

    public static final native long IRational_rescale__SWIG_1(long var0, xv_1 var2, long var3, long var5, xv_1 var7, int var8);

    public static final native long IRational_sRescale__SWIG_1(long var0, long var2, xv_1 var4, long var5, xv_1 var7, int var8);

    public static final native long IRational_rescale__SWIG_2(long var0, int var2, int var3, int var4, int var5, int var6);

    public static final native void IRational_setNumerator(long var0, xv_1 var2, int var3);

    public static final native void IRational_setDenominator(long var0, xv_1 var2, int var3);

    public static final native void IRational_setValue(long var0, xv_1 var2, double var3);

    public static final native double IRational_getValue(long var0, xv_1 var2);

    public static final native boolean IRational_isFinalized(long var0, xv_1 var2);

    public static final native void IRational_init(long var0, xv_1 var2);

    public static final native long ITimeValue_make__SWIG_0(long var0, int var2);

    public static final native long ITimeValue_make__SWIG_1(long var0, uv_2 var2);

    public static final native long ITimeValue_get(long var0, uv_2 var2, int var3);

    public static final native int ITimeValue_compareTo(long var0, uv_2 var2, long var3, uv_2 var5);

    public static final native int ITimeValue_compare__SWIG_0(long var0, uv_2 var2, long var3, uv_2 var5);

    public static final native int ITimeValue_compare__SWIG_1(long var0, long var2);

    public static final native int IMetaData_METADATA_NONE_get();

    public static final native int IMetaData_METADATA_MATCH_CASE_get();

    public static final native int IMetaData_getNumKeys(long var0, aaw_1 var2);

    public static final native String IMetaData_getKey(long var0, aaw_1 var2, int var3);

    public static final native String IMetaData_getValue(long var0, aaw_1 var2, String var3, int var4);

    public static final native int IMetaData_setValue(long var0, aaw_1 var2, String var3, String var4);

    public static final native long IMetaData_make();

    public static final native long IMediaData_getTimeStamp(long var0, atv var2);

    public static final native void IMediaData_setTimeStamp(long var0, atv var2, long var3);

    public static final native long IMediaData_getTimeBase(long var0, atv var2);

    public static final native void IMediaData_setTimeBase(long var0, atv var2, long var3, xv_1 var5);

    public static final native long IMediaData_getData_internal(long var0, atv var2);

    public static final native int IMediaData_getSize(long var0, atv var2);

    public static final native boolean IMediaData_isKey(long var0, atv var2);

    public static final native void IMediaData_setData_internal(long var0, atv var2, long var3, di var5);

    public static final native void IPacket_reset(long var0, ala_1 var2);

    public static final native boolean IPacket_isComplete(long var0, ala_1 var2);

    public static final native long IPacket_getPts(long var0, ala_1 var2);

    public static final native void IPacket_setPts(long var0, ala_1 var2, long var3);

    public static final native long IPacket_getDts(long var0, ala_1 var2);

    public static final native void IPacket_setDts(long var0, ala_1 var2, long var3);

    public static final native int IPacket_getSize(long var0, ala_1 var2);

    public static final native int IPacket_getMaxSize(long var0, ala_1 var2);

    public static final native int IPacket_getStreamIndex(long var0, ala_1 var2);

    public static final native int IPacket_getFlags(long var0, ala_1 var2);

    public static final native boolean IPacket_isKeyPacket(long var0, ala_1 var2);

    public static final native long IPacket_getDuration(long var0, ala_1 var2);

    public static final native long IPacket_getPosition(long var0, ala_1 var2);

    public static final native int IPacket_allocateNewPayload(long var0, ala_1 var2, int var3);

    public static final native long IPacket_make__SWIG_0();

    public static final native long IPacket_make__SWIG_1(long var0, di var2);

    public static final native void IPacket_setKeyPacket(long var0, ala_1 var2, boolean var3);

    public static final native void IPacket_setFlags(long var0, ala_1 var2, int var3);

    public static final native void IPacket_setComplete(long var0, ala_1 var2, boolean var3, int var4);

    public static final native void IPacket_setStreamIndex(long var0, ala_1 var2, int var3);

    public static final native void IPacket_setDuration(long var0, ala_1 var2, long var3);

    public static final native void IPacket_setPosition(long var0, ala_1 var2, long var3);

    public static final native long IPacket_getConvergenceDuration(long var0, ala_1 var2);

    public static final native void IPacket_setConvergenceDuration(long var0, ala_1 var2, long var3);

    public static final native long IPacket_make__SWIG_2(long var0, ala_1 var2, boolean var3);

    public static final native long IPacket_make__SWIG_3(int var0);

    public static final native int IAudioSamples_FMT_NONE_get();

    public static final native boolean IAudioSamples_isComplete(long var0, yX var2);

    public static final native int IAudioSamples_getSampleRate(long var0, yX var2);

    public static final native int IAudioSamples_getChannels(long var0, yX var2);

    public static final native long IAudioSamples_getSampleBitDepth(long var0, yX var2);

    public static final native int IAudioSamples_getFormat(long var0, yX var2);

    public static final native long IAudioSamples_getNumSamples(long var0, yX var2);

    public static final native long IAudioSamples_getMaxBufferSize(long var0, yX var2);

    public static final native long IAudioSamples_getMaxSamples(long var0, yX var2);

    public static final native long IAudioSamples_getSampleSize(long var0, yX var2);

    public static final native long IAudioSamples_getPts(long var0, yX var2);

    public static final native void IAudioSamples_setPts(long var0, yX var2, long var3);

    public static final native long IAudioSamples_getNextPts(long var0, yX var2);

    public static final native void IAudioSamples_setComplete(long var0, yX var2, boolean var3, long var4, int var6, int var7, int var8, long var9);

    public static final native int IAudioSamples_setSample(long var0, yX var2, long var3, int var5, int var6, int var7);

    public static final native int IAudioSamples_getSample(long var0, yX var2, long var3, int var5, int var6);

    public static final native long IAudioSamples_findSampleBitDepth(int var0);

    public static final native long IAudioSamples_make__SWIG_0(long var0, long var2);

    public static final native long IAudioSamples_samplesToDefaultPts(long var0, int var2);

    public static final native long IAudioSamples_defaultPtsToSamples(long var0, int var2);

    public static final native int IAudioSamples_CH_FRONT_LEFT_get();

    public static final native int IAudioSamples_CH_FRONT_RIGHT_get();

    public static final native int IAudioSamples_CH_FRONT_CENTER_get();

    public static final native int IAudioSamples_CH_LOW_FREQUENCY_get();

    public static final native int IAudioSamples_CH_BACK_LEFT_get();

    public static final native int IAudioSamples_CH_BACK_RIGHT_get();

    public static final native int IAudioSamples_CH_FRONT_LEFT_OF_CENTER_get();

    public static final native int IAudioSamples_CH_FRONT_RIGHT_OF_CENTER_get();

    public static final native int IAudioSamples_CH_BACK_CENTER_get();

    public static final native int IAudioSamples_CH_SIDE_LEFT_get();

    public static final native int IAudioSamples_CH_SIDE_RIGHT_get();

    public static final native int IAudioSamples_CH_TOP_CENTER_get();

    public static final native int IAudioSamples_CH_TOP_FRONT_LEFT_get();

    public static final native int IAudioSamples_CH_TOP_FRONT_CENTER_get();

    public static final native int IAudioSamples_CH_TOP_FRONT_RIGHT_get();

    public static final native int IAudioSamples_CH_TOP_BACK_LEFT_get();

    public static final native int IAudioSamples_CH_TOP_BACK_CENTER_get();

    public static final native int IAudioSamples_CH_TOP_BACK_RIGHT_get();

    public static final native int IAudioSamples_CH_STEREO_LEFT_get();

    public static final native int IAudioSamples_CH_STEREO_RIGHT_get();

    public static final native int IAudioSamples_CH_LAYOUT_MONO_get();

    public static final native int IAudioSamples_CH_LAYOUT_STEREO_get();

    public static final native int IAudioSamples_CH_LAYOUT_2_1_get();

    public static final native int IAudioSamples_CH_LAYOUT_SURROUND_get();

    public static final native int IAudioSamples_CH_LAYOUT_4POINT0_get();

    public static final native int IAudioSamples_CH_LAYOUT_2_2_get();

    public static final native int IAudioSamples_CH_LAYOUT_QUAD_get();

    public static final native int IAudioSamples_CH_LAYOUT_5POINT0_get();

    public static final native int IAudioSamples_CH_LAYOUT_5POINT1_get();

    public static final native int IAudioSamples_CH_LAYOUT_5POINT0_BACK_get();

    public static final native int IAudioSamples_CH_LAYOUT_5POINT1_BACK_get();

    public static final native int IAudioSamples_CH_LAYOUT_7POINT1_get();

    public static final native int IAudioSamples_CH_LAYOUT_7POINT1_WIDE_get();

    public static final native int IAudioSamples_CH_LAYOUT_STEREO_DOWNMIX_get();

    public static final native long IAudioSamples_make__SWIG_1(long var0, di var2, int var3, int var4);

    public static final native long IAudioSamples_make__SWIG_2(long var0, long var2, int var4);

    public static final native boolean IVideoPicture_isKeyFrame(long var0, ayh var2);

    public static final native void IVideoPicture_setKeyFrame(long var0, ayh var2, boolean var3);

    public static final native boolean IVideoPicture_isComplete(long var0, ayh var2);

    public static final native int IVideoPicture_getSize(long var0, ayh var2);

    public static final native int IVideoPicture_getWidth(long var0, ayh var2);

    public static final native int IVideoPicture_getHeight(long var0, ayh var2);

    public static final native int IVideoPicture_getPixelType(long var0, ayh var2);

    public static final native long IVideoPicture_getPts(long var0, ayh var2);

    public static final native void IVideoPicture_setPts(long var0, ayh var2, long var3);

    public static final native int IVideoPicture_getQuality(long var0, ayh var2);

    public static final native void IVideoPicture_setQuality(long var0, ayh var2, int var3);

    public static final native int IVideoPicture_getDataLineSize(long var0, ayh var2, int var3);

    public static final native void IVideoPicture_setComplete(long var0, ayh var2, boolean var3, int var4, int var5, int var6, long var7);

    public static final native boolean IVideoPicture_copy(long var0, ayh var2, long var3, ayh var5);

    public static final native long IVideoPicture_make__SWIG_0(int var0, int var1, int var2);

    public static final native long IVideoPicture_make__SWIG_1(long var0, ayh var2);

    public static final native int IVideoPicture_DEFAULT_TYPE_get();

    public static final native int IVideoPicture_I_TYPE_get();

    public static final native int IVideoPicture_P_TYPE_get();

    public static final native int IVideoPicture_B_TYPE_get();

    public static final native int IVideoPicture_S_TYPE_get();

    public static final native int IVideoPicture_SI_TYPE_get();

    public static final native int IVideoPicture_SP_TYPE_get();

    public static final native int IVideoPicture_BI_TYPE_get();

    public static final native int IVideoPicture_getPictureType(long var0, ayh var2);

    public static final native void IVideoPicture_setPictureType(long var0, ayh var2, int var3);

    public static final native long IVideoPicture_make__SWIG_2(long var0, di var2, int var3, int var4, int var5);

    public static final native int ICodec_CODEC_ID_PCM_S16LE_get();

    public static final native int ICodec_CODEC_ID_ADPCM_IMA_QT_get();

    public static final native int ICodec_CODEC_ID_AMR_NB_get();

    public static final native int ICodec_CODEC_ID_RA_144_get();

    public static final native int ICodec_CODEC_ID_ROQ_DPCM_get();

    public static final native int ICodec_CODEC_ID_MP2_get();

    public static final native int ICodec_CODEC_ID_DVD_SUBTITLE_get();

    public static final native int ICodec_CODEC_ID_TTF_get();

    public static final native int ICodec_CODEC_ID_PROBE_get();

    public static final native int ICodec_CODEC_ID_MPEG2TS_get();

    public static final native int ICodec_CODEC_TYPE_UNKNOWN_get();

    public static final native String ICodec_getName(long var0, ch_1 var2);

    public static final native int ICodec_getIDAsInt(long var0, ch_1 var2);

    public static final native int ICodec_getID(long var0, ch_1 var2);

    public static final native int ICodec_getType(long var0, ch_1 var2);

    public static final native boolean ICodec_canDecode(long var0, ch_1 var2);

    public static final native boolean ICodec_canEncode(long var0, ch_1 var2);

    public static final native long ICodec_findEncodingCodec(int var0);

    public static final native long ICodec_findEncodingCodecByIntID(int var0);

    public static final native long ICodec_findEncodingCodecByName(String var0);

    public static final native long ICodec_findDecodingCodec(int var0);

    public static final native long ICodec_findDecodingCodecByIntID(int var0);

    public static final native long ICodec_findDecodingCodecByName(String var0);

    public static final native long ICodec_guessEncodingCodec(long var0, Sg var2, String var3, String var4, String var5, int var6);

    public static final native String ICodec_getLongName(long var0, ch_1 var2);

    public static final native int ICodec_CAP_DRAW_HORIZ_BAND_get();

    public static final native int ICodec_CAP_DR1_get();

    public static final native int ICodec_CAP_PARSE_ONLY_get();

    public static final native int ICodec_CAP_TRUNCATED_get();

    public static final native int ICodec_CAP_HWACCEL_get();

    public static final native int ICodec_CAP_DELAY_get();

    public static final native int ICodec_CAP_SMALL_LAST_FRAME_get();

    public static final native int ICodec_CAP_HWACCEL_VDPAU_get();

    public static final native int ICodec_getCapabilities(long var0, ch_1 var2);

    public static final native boolean ICodec_hasCapability(long var0, ch_1 var2, int var3);

    public static final native int ICodec_getNumInstalledCodecs();

    public static final native long ICodec_getInstalledCodec(int var0);

    public static final native int ICodec_getNumSupportedVideoFrameRates(long var0, ch_1 var2);

    public static final native long ICodec_getSupportedVideoFrameRate(long var0, ch_1 var2, int var3);

    public static final native int ICodec_getNumSupportedVideoPixelFormats(long var0, ch_1 var2);

    public static final native int ICodec_getSupportedVideoPixelFormat(long var0, ch_1 var2, int var3);

    public static final native int ICodec_getNumSupportedAudioSampleRates(long var0, ch_1 var2);

    public static final native int ICodec_getSupportedAudioSampleRate(long var0, ch_1 var2, int var3);

    public static final native int ICodec_getNumSupportedAudioSampleFormats(long var0, ch_1 var2);

    public static final native int ICodec_getSupportedAudioSampleFormat(long var0, ch_1 var2, int var3);

    public static final native int ICodec_getNumSupportedAudioChannelLayouts(long var0, ch_1 var2);

    public static final native long ICodec_getSupportedAudioChannelLayout(long var0, ch_1 var2, int var3);

    public static final native int IAudioResampler_getOutputChannels(long var0, rL var2);

    public static final native int IAudioResampler_getOutputRate(long var0, rL var2);

    public static final native int IAudioResampler_getInputChannels(long var0, rL var2);

    public static final native int IAudioResampler_getInputRate(long var0, rL var2);

    public static final native int IAudioResampler_resample(long var0, rL var2, long var3, yX var5, long var6, yX var8, long var9);

    public static final native long IAudioResampler_make__SWIG_0(int var0, int var1, int var2, int var3);

    public static final native int IAudioResampler_getOutputFormat(long var0, rL var2);

    public static final native int IAudioResampler_getInputFormat(long var0, rL var2);

    public static final native int IAudioResampler_getFilterLen(long var0, rL var2);

    public static final native int IAudioResampler_getLog2PhaseCount(long var0, rL var2);

    public static final native boolean IAudioResampler_isLinear(long var0, rL var2);

    public static final native double IAudioResampler_getCutoffFrequency(long var0, rL var2);

    public static final native long IAudioResampler_make__SWIG_1(int var0, int var1, int var2, int var3, int var4, int var5);

    public static final native long IAudioResampler_make__SWIG_2(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, double var9);

    public static final native int IAudioResampler_getMinimumNumSamplesRequiredInOutputSamples__SWIG_0(long var0, rL var2, long var3, yX var5);

    public static final native int IAudioResampler_getMinimumNumSamplesRequiredInOutputSamples__SWIG_1(long var0, rL var2, int var3);

    public static final native int IVideoResampler_getInputWidth(long var0, Xz var2);

    public static final native int IVideoResampler_getInputHeight(long var0, Xz var2);

    public static final native int IVideoResampler_getInputPixelFormat(long var0, Xz var2);

    public static final native int IVideoResampler_getOutputWidth(long var0, Xz var2);

    public static final native int IVideoResampler_getOutputHeight(long var0, Xz var2);

    public static final native int IVideoResampler_getOutputPixelFormat(long var0, Xz var2);

    public static final native int IVideoResampler_resample(long var0, Xz var2, long var3, ayh var5, long var6, ayh var8);

    public static final native int IVideoResampler_getNumProperties(long var0, Xz var2);

    public static final native long IVideoResampler_getPropertyMetaData__SWIG_0(long var0, Xz var2, int var3);

    public static final native long IVideoResampler_getPropertyMetaData__SWIG_1(long var0, Xz var2, String var3);

    public static final native int IVideoResampler_setProperty__SWIG_0(long var0, Xz var2, String var3, String var4);

    public static final native int IVideoResampler_setProperty__SWIG_1(long var0, Xz var2, String var3, double var4);

    public static final native int IVideoResampler_setProperty__SWIG_2(long var0, Xz var2, String var3, long var4);

    public static final native int IVideoResampler_setProperty__SWIG_3(long var0, Xz var2, String var3, boolean var4);

    public static final native int IVideoResampler_setProperty__SWIG_4(long var0, Xz var2, String var3, long var4, xv_1 var6);

    public static final native String IVideoResampler_getPropertyAsString(long var0, Xz var2, String var3);

    public static final native double IVideoResampler_getPropertyAsDouble(long var0, Xz var2, String var3);

    public static final native long IVideoResampler_getPropertyAsLong(long var0, Xz var2, String var3);

    public static final native long IVideoResampler_getPropertyAsRational(long var0, Xz var2, String var3);

    public static final native boolean IVideoResampler_getPropertyAsBoolean(long var0, Xz var2, String var3);

    public static final native long IVideoResampler_make(int var0, int var1, int var2, int var3, int var4, int var5);

    public static final native boolean IVideoResampler_isSupported(int var0);

    public static final native int IStreamCoder_FLAG_QSCALE_get();

    public static final native int IStreamCoder_FLAG_4MV_get();

    public static final native int IStreamCoder_FLAG_QPEL_get();

    public static final native int IStreamCoder_FLAG_GMC_get();

    public static final native int IStreamCoder_FLAG_MV0_get();

    public static final native int IStreamCoder_FLAG_PART_get();

    public static final native int IStreamCoder_FLAG_INPUT_PRESERVED_get();

    public static final native int IStreamCoder_FLAG_PASS1_get();

    public static final native int IStreamCoder_FLAG_PASS2_get();

    public static final native int IStreamCoder_FLAG_EXTERN_HUFF_get();

    public static final native int IStreamCoder_FLAG_GRAY_get();

    public static final native int IStreamCoder_FLAG_EMU_EDGE_get();

    public static final native int IStreamCoder_FLAG_PSNR_get();

    public static final native int IStreamCoder_FLAG_TRUNCATED_get();

    public static final native int IStreamCoder_FLAG_NORMALIZE_AQP_get();

    public static final native int IStreamCoder_FLAG_INTERLACED_DCT_get();

    public static final native int IStreamCoder_FLAG_LOW_DELAY_get();

    public static final native int IStreamCoder_FLAG_ALT_SCAN_get();

    public static final native int IStreamCoder_FLAG_TRELLIS_QUANT_get();

    public static final native int IStreamCoder_FLAG_GLOBAL_HEADER_get();

    public static final native int IStreamCoder_FLAG_BITEXACT_get();

    public static final native int IStreamCoder_FLAG_AC_PRED_get();

    public static final native int IStreamCoder_FLAG_H263P_UMV_get();

    public static final native int IStreamCoder_FLAG_CBP_RD_get();

    public static final native int IStreamCoder_FLAG_QP_RD_get();

    public static final native int IStreamCoder_FLAG_H263P_AIV_get();

    public static final native int IStreamCoder_FLAG_OBMC_get();

    public static final native int IStreamCoder_FLAG_LOOP_FILTER_get();

    public static final native int IStreamCoder_FLAG_H263P_SLICE_STRUCT_get();

    public static final native int IStreamCoder_FLAG_INTERLACED_ME_get();

    public static final native int IStreamCoder_FLAG_SVCD_SCAN_OFFSET_get();

    public static final native int IStreamCoder_FLAG_CLOSED_GOP_get();

    public static final native int IStreamCoder_FLAG2_FAST_get();

    public static final native int IStreamCoder_FLAG2_STRICT_GOP_get();

    public static final native int IStreamCoder_FLAG2_NO_OUTPUT_get();

    public static final native int IStreamCoder_FLAG2_LOCAL_HEADER_get();

    public static final native int IStreamCoder_FLAG2_BPYRAMID_get();

    public static final native int IStreamCoder_FLAG2_WPRED_get();

    public static final native int IStreamCoder_FLAG2_MIXED_REFS_get();

    public static final native int IStreamCoder_FLAG2_8X8DCT_get();

    public static final native int IStreamCoder_FLAG2_FASTPSKIP_get();

    public static final native int IStreamCoder_FLAG2_AUD_get();

    public static final native int IStreamCoder_FLAG2_BRDO_get();

    public static final native int IStreamCoder_FLAG2_INTRA_VLC_get();

    public static final native int IStreamCoder_FLAG2_MEMC_ONLY_get();

    public static final native int IStreamCoder_FLAG2_DROP_FRAME_TIMECODE_get();

    public static final native int IStreamCoder_FLAG2_SKIP_RD_get();

    public static final native int IStreamCoder_FLAG2_CHUNKS_get();

    public static final native int IStreamCoder_FLAG2_NON_LINEAR_QUANT_get();

    public static final native int IStreamCoder_FLAG2_BIT_RESERVOIR_get();

    public static final native int IStreamCoder_getDirection(long var0, zw_2 var2);

    public static final native long IStreamCoder_getStream(long var0, zw_2 var2);

    public static final native long IStreamCoder_getCodec(long var0, zw_2 var2);

    public static final native int IStreamCoder_getCodecType(long var0, zw_2 var2);

    public static final native int IStreamCoder_getCodecID(long var0, zw_2 var2);

    public static final native void IStreamCoder_setCodec__SWIG_0(long var0, zw_2 var2, long var3, ch_1 var5);

    public static final native void IStreamCoder_setCodec__SWIG_1(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getBitRate(long var0, zw_2 var2);

    public static final native void IStreamCoder_setBitRate(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getBitRateTolerance(long var0, zw_2 var2);

    public static final native void IStreamCoder_setBitRateTolerance(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getHeight(long var0, zw_2 var2);

    public static final native void IStreamCoder_setHeight(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getWidth(long var0, zw_2 var2);

    public static final native void IStreamCoder_setWidth(long var0, zw_2 var2, int var3);

    public static final native long IStreamCoder_getTimeBase(long var0, zw_2 var2);

    public static final native void IStreamCoder_setTimeBase(long var0, zw_2 var2, long var3, xv_1 var5);

    public static final native long IStreamCoder_getFrameRate(long var0, zw_2 var2);

    public static final native void IStreamCoder_setFrameRate(long var0, zw_2 var2, long var3, xv_1 var5);

    public static final native int IStreamCoder_getNumPicturesInGroupOfPictures(long var0, zw_2 var2);

    public static final native void IStreamCoder_setNumPicturesInGroupOfPictures(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getPixelType(long var0, zw_2 var2);

    public static final native void IStreamCoder_setPixelType(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getSampleRate(long var0, zw_2 var2);

    public static final native void IStreamCoder_setSampleRate(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getSampleFormat(long var0, zw_2 var2);

    public static final native void IStreamCoder_setSampleFormat(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getChannels(long var0, zw_2 var2);

    public static final native void IStreamCoder_setChannels(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getAudioFrameSize(long var0, zw_2 var2);

    public static final native int IStreamCoder_getGlobalQuality(long var0, zw_2 var2);

    public static final native void IStreamCoder_setGlobalQuality(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getFlags(long var0, zw_2 var2);

    public static final native void IStreamCoder_setFlags(long var0, zw_2 var2, int var3);

    public static final native boolean IStreamCoder_getFlag(long var0, zw_2 var2, int var3);

    public static final native void IStreamCoder_setFlag(long var0, zw_2 var2, int var3, boolean var4);

    public static final native long IStreamCoder_getNextPredictedPts(long var0, zw_2 var2);

    public static final native int IStreamCoder_open(long var0, zw_2 var2);

    public static final native int IStreamCoder_close(long var0, zw_2 var2);

    public static final native int IStreamCoder_decodeAudio(long var0, zw_2 var2, long var3, yX var5, long var6, ala_1 var8, int var9);

    public static final native int IStreamCoder_decodeVideo(long var0, zw_2 var2, long var3, ayh var5, long var6, ala_1 var8, int var9);

    public static final native int IStreamCoder_encodeVideo(long var0, zw_2 var2, long var3, ala_1 var5, long var6, ayh var8, int var9);

    public static final native int IStreamCoder_encodeAudio(long var0, zw_2 var2, long var3, ala_1 var5, long var6, yX var8, long var9);

    public static final native long IStreamCoder_make__SWIG_0(int var0);

    public static final native int IStreamCoder_getCodecTag(long var0, zw_2 var2);

    public static final native void IStreamCoder_setCodecTag(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_getNumProperties(long var0, zw_2 var2);

    public static final native long IStreamCoder_getPropertyMetaData__SWIG_0(long var0, zw_2 var2, int var3);

    public static final native long IStreamCoder_getPropertyMetaData__SWIG_1(long var0, zw_2 var2, String var3);

    public static final native int IStreamCoder_setProperty__SWIG_0(long var0, zw_2 var2, String var3, String var4);

    public static final native int IStreamCoder_setProperty__SWIG_1(long var0, zw_2 var2, String var3, double var4);

    public static final native int IStreamCoder_setProperty__SWIG_2(long var0, zw_2 var2, String var3, long var4);

    public static final native int IStreamCoder_setProperty__SWIG_3(long var0, zw_2 var2, String var3, boolean var4);

    public static final native int IStreamCoder_setProperty__SWIG_4(long var0, zw_2 var2, String var3, long var4, xv_1 var6);

    public static final native String IStreamCoder_getPropertyAsString(long var0, zw_2 var2, String var3);

    public static final native double IStreamCoder_getPropertyAsDouble(long var0, zw_2 var2, String var3);

    public static final native long IStreamCoder_getPropertyAsLong(long var0, zw_2 var2, String var3);

    public static final native long IStreamCoder_getPropertyAsRational(long var0, zw_2 var2, String var3);

    public static final native boolean IStreamCoder_getPropertyAsBoolean(long var0, zw_2 var2, String var3);

    public static final native boolean IStreamCoder_isOpen(long var0, zw_2 var2);

    public static final native int IStreamCoder_getDefaultAudioFrameSize(long var0, zw_2 var2);

    public static final native void IStreamCoder_setDefaultAudioFrameSize(long var0, zw_2 var2, int var3);

    public static final native long IStreamCoder_make__SWIG_1(int var0, long var1, zw_2 var3);

    public static final native long IStreamCoder_getNumDroppedFrames(long var0, zw_2 var2);

    public static final native void IStreamCoder_setAutomaticallyStampPacketsForStream(long var0, zw_2 var2, boolean var3);

    public static final native boolean IStreamCoder_getAutomaticallyStampPacketsForStream(long var0, zw_2 var2);

    public static final native void IStreamCoder_setCodecID(long var0, zw_2 var2, int var3);

    public static final native int IStreamCoder_setExtraData(long var0, zw_2 var2, long var3, di var5, int var6, int var7, boolean var8);

    public static final native int IStreamCoder_getExtraData(long var0, zw_2 var2, long var3, di var5, int var6, int var7);

    public static final native int IStreamCoder_getExtraDataSize(long var0, zw_2 var2);

    public static final native int IIndexEntry_IINDEX_FLAG_KEYFRAME_get();

    public static final native long IIndexEntry_make(long var0, long var2, int var4, int var5, int var6);

    public static final native long IIndexEntry_getPosition(long var0, zt_0 var2);

    public static final native long IIndexEntry_getTimeStamp(long var0, zt_0 var2);

    public static final native int IIndexEntry_getFlags(long var0, zt_0 var2);

    public static final native int IIndexEntry_getSize(long var0, zt_0 var2);

    public static final native int IIndexEntry_getMinDistance(long var0, zt_0 var2);

    public static final native boolean IIndexEntry_isKeyFrame(long var0, zt_0 var2);

    public static final native int IStream_getDirection(long var0, at_2 var2);

    public static final native int IStream_getIndex(long var0, at_2 var2);

    public static final native int IStream_getId(long var0, at_2 var2);

    public static final native long IStream_getStreamCoder(long var0, at_2 var2);

    public static final native long IStream_getFrameRate(long var0, at_2 var2);

    public static final native long IStream_getTimeBase(long var0, at_2 var2);

    public static final native long IStream_getStartTime(long var0, at_2 var2);

    public static final native long IStream_getDuration(long var0, at_2 var2);

    public static final native long IStream_getCurrentDts(long var0, at_2 var2);

    public static final native int IStream_getNumIndexEntries(long var0, at_2 var2);

    public static final native long IStream_getNumFrames(long var0, at_2 var2);

    public static final native long IStream_getSampleAspectRatio(long var0, at_2 var2);

    public static final native void IStream_setSampleAspectRatio(long var0, at_2 var2, long var3, xv_1 var5);

    public static final native String IStream_getLanguage(long var0, at_2 var2);

    public static final native void IStream_setLanguage(long var0, at_2 var2, String var3);

    public static final native long IStream_getContainer(long var0, at_2 var2);

    public static final native int IStream_setStreamCoder__SWIG_0(long var0, at_2 var2, long var3, zw_2 var5);

    public static final native int IStream_getParseType(long var0, at_2 var2);

    public static final native void IStream_setParseType(long var0, at_2 var2, int var3);

    public static final native long IStream_getMetaData(long var0, at_2 var2);

    public static final native void IStream_setMetaData(long var0, at_2 var2, long var3, aaw_1 var5);

    public static final native int IStream_stampOutputPacket(long var0, at_2 var2, long var3, ala_1 var5);

    public static final native int IStream_setStreamCoder__SWIG_1(long var0, at_2 var2, long var3, zw_2 var5, boolean var6);

    public static final native long IStream_findTimeStampEntryInIndex(long var0, at_2 var2, long var3, int var5);

    public static final native int IStream_findTimeStampPositionInIndex(long var0, at_2 var2, long var3, int var5);

    public static final native long IStream_getIndexEntry(long var0, at_2 var2, int var3);

    public static final native int IStream_addIndexEntry(long var0, at_2 var2, long var3, zt_0 var5);

    public static final native long IContainerParameters_getTimeBase(long var0, aow_1 var2);

    public static final native void IContainerParameters_setTimeBase(long var0, aow_1 var2, long var3, xv_1 var5);

    public static final native int IContainerParameters_getAudioSampleRate(long var0, aow_1 var2);

    public static final native void IContainerParameters_setAudioSampleRate(long var0, aow_1 var2, int var3);

    public static final native int IContainerParameters_getAudioChannels(long var0, aow_1 var2);

    public static final native void IContainerParameters_setAudioChannels(long var0, aow_1 var2, int var3);

    public static final native int IContainerParameters_getVideoWidth(long var0, aow_1 var2);

    public static final native void IContainerParameters_setVideoWidth(long var0, aow_1 var2, int var3);

    public static final native int IContainerParameters_getVideoHeight(long var0, aow_1 var2);

    public static final native void IContainerParameters_setVideoHeight(long var0, aow_1 var2, int var3);

    public static final native int IContainerParameters_getPixelFormat(long var0, aow_1 var2);

    public static final native void IContainerParameters_setPixelFormat(long var0, aow_1 var2, int var3);

    public static final native int IContainerParameters_getTVChannel(long var0, aow_1 var2);

    public static final native void IContainerParameters_setTVChannel(long var0, aow_1 var2, int var3);

    public static final native String IContainerParameters_getTVStandard(long var0, aow_1 var2);

    public static final native void IContainerParameters_setTVStandard(long var0, aow_1 var2, String var3);

    public static final native boolean IContainerParameters_isMPEG2TSRaw(long var0, aow_1 var2);

    public static final native void IContainerParameters_setMPEG2TSRaw(long var0, aow_1 var2, boolean var3);

    public static final native boolean IContainerParameters_isMPEG2TSComputePCR(long var0, aow_1 var2);

    public static final native void IContainerParameters_setMPEG2TSComputePCR(long var0, aow_1 var2, boolean var3);

    public static final native boolean IContainerParameters_isInitialPause(long var0, aow_1 var2);

    public static final native void IContainerParameters_setInitialPause(long var0, aow_1 var2, boolean var3);

    public static final native long IContainerParameters_make();

    public static final native int IContainerFormat_setInputFormat(long var0, Sg var2, String var3);

    public static final native int IContainerFormat_setOutputFormat(long var0, Sg var2, String var3, String var4, String var5);

    public static final native String IContainerFormat_getInputFormatShortName(long var0, Sg var2);

    public static final native String IContainerFormat_getInputFormatLongName(long var0, Sg var2);

    public static final native String IContainerFormat_getOutputFormatShortName(long var0, Sg var2);

    public static final native String IContainerFormat_getOutputFormatLongName(long var0, Sg var2);

    public static final native String IContainerFormat_getOutputFormatMimeType(long var0, Sg var2);

    public static final native long IContainerFormat_make();

    public static final native int IContainerFormat_FLAG_NOFILE_get();

    public static final native int IContainerFormat_FLAG_NEEDNUMBER_get();

    public static final native int IContainerFormat_FLAG_SHOW_IDS_get();

    public static final native int IContainerFormat_FLAG_RAWPICTURE_get();

    public static final native int IContainerFormat_FLAG_GLOBALHEADER_get();

    public static final native int IContainerFormat_FLAG_NOTIMESTAMPS_get();

    public static final native int IContainerFormat_FLAG_GENERIC_INDEX_get();

    public static final native int IContainerFormat_FLAG_TS_DISCONT_get();

    public static final native int IContainerFormat_getInputFlags(long var0, Sg var2);

    public static final native void IContainerFormat_setInputFlags(long var0, Sg var2, int var3);

    public static final native boolean IContainerFormat_getInputFlag(long var0, Sg var2, int var3);

    public static final native void IContainerFormat_setInputFlag(long var0, Sg var2, int var3, boolean var4);

    public static final native int IContainerFormat_getOutputFlags(long var0, Sg var2);

    public static final native void IContainerFormat_setOutputFlags(long var0, Sg var2, int var3);

    public static final native boolean IContainerFormat_getOutputFlag(long var0, Sg var2, int var3);

    public static final native void IContainerFormat_setOutputFlag(long var0, Sg var2, int var3, boolean var4);

    public static final native boolean IContainerFormat_isOutput(long var0, Sg var2);

    public static final native boolean IContainerFormat_isInput(long var0, Sg var2);

    public static final native String IContainerFormat_getOutputExtensions(long var0, Sg var2);

    public static final native int IContainerFormat_getOutputDefaultAudioCodec(long var0, Sg var2);

    public static final native int IContainerFormat_getOutputDefaultVideoCodec(long var0, Sg var2);

    public static final native int IContainerFormat_getOutputDefaultSubtitleCodec(long var0, Sg var2);

    public static final native int IContainerFormat_getOutputNumCodecsSupported(long var0, Sg var2);

    public static final native int IContainerFormat_getOutputCodecID(long var0, Sg var2, int var3);

    public static final native int IContainerFormat_getOutputCodecTag__SWIG_0(long var0, Sg var2, int var3);

    public static final native int IContainerFormat_getOutputCodecTag__SWIG_1(long var0, Sg var2, int var3);

    public static final native boolean IContainerFormat_isCodecSupportedForOutput(long var0, Sg var2, int var3);

    public static final native int IContainerFormat_getNumInstalledInputFormats();

    public static final native long IContainerFormat_getInstalledInputFormat(int var0);

    public static final native int IContainerFormat_getNumInstalledOutputFormats();

    public static final native long IContainerFormat_getInstalledOutputFormat(int var0);

    public static final native int IContainer_setInputBufferLength(long var0, aip_2 var2, long var3);

    public static final native long IContainer_getInputBufferLength(long var0, aip_2 var2);

    public static final native boolean IContainer_isOpened(long var0, aip_2 var2);

    public static final native boolean IContainer_isHeaderWritten(long var0, aip_2 var2);

    public static final native int IContainer_open__SWIG_0(long var0, aip_2 var2, String var3, int var4, long var5, Sg var7);

    public static final native int IContainer_open__SWIG_1(long var0, aip_2 var2, String var3, int var4, long var5, Sg var7, boolean var8, boolean var9);

    public static final native long IContainer_getContainerFormat(long var0, aip_2 var2);

    public static final native int IContainer_close(long var0, aip_2 var2);

    public static final native int IContainer_getType(long var0, aip_2 var2);

    public static final native int IContainer_getNumStreams(long var0, aip_2 var2);

    public static final native long IContainer_getStream(long var0, aip_2 var2, long var3);

    public static final native long IContainer_addNewStream(long var0, aip_2 var2, int var3);

    public static final native int IContainer_writeHeader(long var0, aip_2 var2);

    public static final native int IContainer_writeTrailer(long var0, aip_2 var2);

    public static final native int IContainer_readNextPacket(long var0, aip_2 var2, long var3, ala_1 var5);

    public static final native int IContainer_writePacket__SWIG_0(long var0, aip_2 var2, long var3, ala_1 var5, boolean var6);

    public static final native int IContainer_writePacket__SWIG_1(long var0, aip_2 var2, long var3, ala_1 var5);

    public static final native long IContainer_make();

    public static final native int IContainer_queryStreamMetaData(long var0, aip_2 var2);

    public static final native int IContainer_seekKeyFrame__SWIG_0(long var0, aip_2 var2, int var3, long var4, int var6);

    public static final native long IContainer_getDuration(long var0, aip_2 var2);

    public static final native long IContainer_getStartTime(long var0, aip_2 var2);

    public static final native long IContainer_getFileSize(long var0, aip_2 var2);

    public static final native int IContainer_getBitRate(long var0, aip_2 var2);

    public static final native int IContainer_getNumProperties(long var0, aip_2 var2);

    public static final native long IContainer_getPropertyMetaData__SWIG_0(long var0, aip_2 var2, int var3);

    public static final native long IContainer_getPropertyMetaData__SWIG_1(long var0, aip_2 var2, String var3);

    public static final native int IContainer_setProperty__SWIG_0(long var0, aip_2 var2, String var3, String var4);

    public static final native int IContainer_setProperty__SWIG_1(long var0, aip_2 var2, String var3, double var4);

    public static final native int IContainer_setProperty__SWIG_2(long var0, aip_2 var2, String var3, long var4);

    public static final native int IContainer_setProperty__SWIG_3(long var0, aip_2 var2, String var3, boolean var4);

    public static final native int IContainer_setProperty__SWIG_4(long var0, aip_2 var2, String var3, long var4, xv_1 var6);

    public static final native String IContainer_getPropertyAsString(long var0, aip_2 var2, String var3);

    public static final native double IContainer_getPropertyAsDouble(long var0, aip_2 var2, String var3);

    public static final native long IContainer_getPropertyAsLong(long var0, aip_2 var2, String var3);

    public static final native long IContainer_getPropertyAsRational(long var0, aip_2 var2, String var3);

    public static final native boolean IContainer_getPropertyAsBoolean(long var0, aip_2 var2, String var3);

    public static final native int IContainer_FLAG_GENPTS_get();

    public static final native int IContainer_FLAG_IGNIDX_get();

    public static final native int IContainer_FLAG_NONBLOCK_get();

    public static final native int IContainer_FLAG_IGNDTS_get();

    public static final native int IContainer_getFlags(long var0, aip_2 var2);

    public static final native void IContainer_setFlags(long var0, aip_2 var2, int var3);

    public static final native boolean IContainer_getFlag(long var0, aip_2 var2, int var3);

    public static final native void IContainer_setFlag(long var0, aip_2 var2, int var3, boolean var4);

    public static final native String IContainer_getURL(long var0, aip_2 var2);

    public static final native int IContainer_flushPackets(long var0, aip_2 var2);

    public static final native int IContainer_getReadRetryCount(long var0, aip_2 var2);

    public static final native void IContainer_setReadRetryCount(long var0, aip_2 var2, int var3);

    public static final native long IContainer_getParameters(long var0, aip_2 var2);

    public static final native void IContainer_setParameters(long var0, aip_2 var2, long var3, aow_1 var5);

    public static final native boolean IContainer_canStreamsBeAddedDynamically(long var0, aip_2 var2);

    public static final native long IContainer_getMetaData(long var0, aip_2 var2);

    public static final native void IContainer_setMetaData(long var0, aip_2 var2, long var3, aaw_1 var5);

    public static final native int IContainer_createSDPData(long var0, aip_2 var2, long var3, di var5);

    public static final native int IContainer_setForcedAudioCodec(long var0, aip_2 var2, int var3);

    public static final native int IContainer_setForcedVideoCodec(long var0, aip_2 var2, int var3);

    public static final native int IContainer_setForcedSubtitleCodec(long var0, aip_2 var2, int var3);

    public static final native int IContainer_SEEK_FLAG_BACKWARDS_get();

    public static final native int IContainer_SEEK_FLAG_BYTE_get();

    public static final native int IContainer_SEEK_FLAG_ANY_get();

    public static final native int IContainer_SEEK_FLAG_FRAME_get();

    public static final native int IContainer_seekKeyFrame__SWIG_1(long var0, aip_2 var2, int var3, long var4, long var6, long var8, int var10);

    public static final native void IMediaDataWrapper_wrap(long var0, abg_2 var2, long var3, atv var5);

    public static final native void IMediaDataWrapper_setKey(long var0, abg_2 var2, boolean var3);

    public static final native long IMediaDataWrapper_make(long var0, atv var2);

    public static final native long IMediaDataWrapper_getPacket(long var0, abg_2 var2);

    public static final native long IMediaDataWrapper_getAudioSamples(long var0, abg_2 var2);

    public static final native long IMediaDataWrapper_getVideoPicture(long var0, abg_2 var2);

    public static final native long IMediaDataWrapper_getMediaDataWrapper(long var0, abg_2 var2);

    public static final native long IMediaDataWrapper_unwrapPacket(long var0, abg_2 var2);

    public static final native long IMediaDataWrapper_unwrapAudioSamples(long var0, abg_2 var2);

    public static final native long IMediaDataWrapper_unwrapVideoPicture(long var0, abg_2 var2);

    public static final native long IMediaDataWrapper_unwrapMediaDataWrapper(long var0, abg_2 var2);

    public static final native long Global_NO_PTS_get();

    public static final native long Global_DEFAULT_PTS_PER_SECOND_get();

    public static final native long Global_getVersion();

    public static final native int Global_getVersionMajor();

    public static final native int Global_getVersionMinor();

    public static final native int Global_getVersionRevision();

    public static final native String Global_getVersionStr();

    public static final native int Global_getAVFormatVersion();

    public static final native String Global_getAVFormatVersionStr();

    public static final native int Global_getAVCodecVersion();

    public static final native String Global_getAVCodecVersionStr();

    public static final native void Global_init();

    public static final native void Global_setFFmpegLoggingLevel(int var0);

    public static final native int IError_getType(long var0, asj var2);

    public static final native String IError_getDescription(long var0, asj var2);

    public static final native int IError_getErrorNumber(long var0, asj var2);

    public static final native long IError_make__SWIG_0(int var0);

    public static final native long IError_make__SWIG_1(int var0);

    public static final native int IError_errorNumberToType(int var0);

    public static final native int IError_typeToErrorNumber(int var0);

    public static final native long SWIGIPropertyUpcast(long var0);

    public static final native long SWIGIPixelFormatUpcast(long var0);

    public static final native long SWIGIRationalUpcast(long var0);

    public static final native long SWIGITimeValueUpcast(long var0);

    public static final native long SWIGIMetaDataUpcast(long var0);

    public static final native long SWIGIMediaDataUpcast(long var0);

    public static final native long SWIGIPacketUpcast(long var0);

    public static final native long SWIGIAudioSamplesUpcast(long var0);

    public static final native long SWIGIVideoPictureUpcast(long var0);

    public static final native long SWIGICodecUpcast(long var0);

    public static final native long SWIGIAudioResamplerUpcast(long var0);

    public static final native long SWIGIVideoResamplerUpcast(long var0);

    public static final native long SWIGIStreamCoderUpcast(long var0);

    public static final native long SWIGIIndexEntryUpcast(long var0);

    public static final native long SWIGIStreamUpcast(long var0);

    public static final native long SWIGIContainerParametersUpcast(long var0);

    public static final native long SWIGIContainerFormatUpcast(long var0);

    public static final native long SWIGIContainerUpcast(long var0);

    public static final native long SWIGIMediaDataWrapperUpcast(long var0);

    public static final native long SWIGGlobalUpcast(long var0);

    public static final native long SWIGIErrorUpcast(long var0);

    static {
        atA.a("xuggle-xuggler", new Long(3L));
        Xuggler.init();
        va_0.init();
    }
}

