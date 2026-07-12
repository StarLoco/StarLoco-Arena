package net.java.games.joal;

public interface ALConstants {
  public static final int AL_NONE = 0;
  
  public static final int AL_FALSE = 0;
  
  public static final int AL_TRUE = 1;
  
  public static final int AL_SOURCE_RELATIVE = 514;
  
  public static final int AL_CONE_INNER_ANGLE = 4097;
  
  public static final int AL_CONE_OUTER_ANGLE = 4098;
  
  public static final int AL_PITCH = 4099;
  
  public static final int AL_POSITION = 4100;
  
  public static final int AL_DIRECTION = 4101;
  
  public static final int AL_VELOCITY = 4102;
  
  public static final int AL_LOOPING = 4103;
  
  public static final int AL_BUFFER = 4105;
  
  public static final int AL_GAIN = 4106;
  
  public static final int AL_MIN_GAIN = 4109;
  
  public static final int AL_MAX_GAIN = 4110;
  
  public static final int AL_ORIENTATION = 4111;
  
  public static final int AL_CHANNEL_MASK = 12288;
  
  public static final int AL_SOURCE_STATE = 4112;
  
  public static final int AL_INITIAL = 4113;
  
  public static final int AL_PLAYING = 4114;
  
  public static final int AL_PAUSED = 4115;
  
  public static final int AL_STOPPED = 4116;
  
  public static final int AL_BUFFERS_QUEUED = 4117;
  
  public static final int AL_BUFFERS_PROCESSED = 4118;
  
  public static final int AL_SEC_OFFSET = 4132;
  
  public static final int AL_SAMPLE_OFFSET = 4133;
  
  public static final int AL_BYTE_OFFSET = 4134;
  
  public static final int AL_SOURCE_TYPE = 4135;
  
  public static final int AL_STATIC = 4136;
  
  public static final int AL_STREAMING = 4137;
  
  public static final int AL_UNDETERMINED = 4144;
  
  public static final int AL_FORMAT_MONO8 = 4352;
  
  public static final int AL_FORMAT_MONO16 = 4353;
  
  public static final int AL_FORMAT_STEREO8 = 4354;
  
  public static final int AL_FORMAT_STEREO16 = 4355;
  
  public static final int AL_REFERENCE_DISTANCE = 4128;
  
  public static final int AL_ROLLOFF_FACTOR = 4129;
  
  public static final int AL_CONE_OUTER_GAIN = 4130;
  
  public static final int AL_MAX_DISTANCE = 4131;
  
  public static final int AL_FREQUENCY = 8193;
  
  public static final int AL_BITS = 8194;
  
  public static final int AL_CHANNELS = 8195;
  
  public static final int AL_SIZE = 8196;
  
  public static final int AL_UNUSED = 8208;
  
  public static final int AL_PENDING = 8209;
  
  public static final int AL_PROCESSED = 8210;
  
  public static final int AL_NO_ERROR = 0;
  
  public static final int AL_INVALID_NAME = 40961;
  
  public static final int AL_ILLEGAL_ENUM = 40962;
  
  public static final int AL_INVALID_ENUM = 40962;
  
  public static final int AL_INVALID_VALUE = 40963;
  
  public static final int AL_ILLEGAL_COMMAND = 40964;
  
  public static final int AL_INVALID_OPERATION = 40964;
  
  public static final int AL_OUT_OF_MEMORY = 40965;
  
  public static final int AL_VENDOR = 45057;
  
  public static final int AL_VERSION = 45058;
  
  public static final int AL_RENDERER = 45059;
  
  public static final int AL_EXTENSIONS = 45060;
  
  public static final int AL_DOPPLER_FACTOR = 49152;
  
  public static final int AL_DOPPLER_VELOCITY = 49153;
  
  public static final int AL_SPEED_OF_SOUND = 49155;
  
  public static final int AL_DISTANCE_MODEL = 53248;
  
  public static final int AL_INVERSE_DISTANCE = 53249;
  
  public static final int AL_INVERSE_DISTANCE_CLAMPED = 53250;
  
  public static final int AL_LINEAR_DISTANCE = 53251;
  
  public static final int AL_LINEAR_DISTANCE_CLAMPED = 53252;
  
  public static final int AL_EXPONENT_DISTANCE = 53253;
  
  public static final int AL_EXPONENT_DISTANCE_CLAMPED = 53254;
  
  public static final int AL_METERS_PER_UNIT = 131076;
  
  public static final int AL_DIRECT_FILTER = 131077;
  
  public static final int AL_AUXILIARY_SEND_FILTER = 131078;
  
  public static final int AL_AIR_ABSORPTION_FACTOR = 131079;
  
  public static final int AL_ROOM_ROLLOFF_FACTOR = 131080;
  
  public static final int AL_CONE_OUTER_GAINHF = 131081;
  
  public static final int AL_DIRECT_FILTER_GAINHF_AUTO = 131082;
  
  public static final int AL_AUXILIARY_SEND_FILTER_GAIN_AUTO = 131083;
  
  public static final int AL_AUXILIARY_SEND_FILTER_GAINHF_AUTO = 131084;
  
  public static final int AL_REVERB_DENSITY = 1;
  
  public static final int AL_REVERB_DIFFUSION = 2;
  
  public static final int AL_REVERB_GAIN = 3;
  
  public static final int AL_REVERB_GAINHF = 4;
  
  public static final int AL_REVERB_DECAY_TIME = 5;
  
  public static final int AL_REVERB_DECAY_HFRATIO = 6;
  
  public static final int AL_REVERB_REFLECTIONS_GAIN = 7;
  
  public static final int AL_REVERB_REFLECTIONS_DELAY = 8;
  
  public static final int AL_REVERB_LATE_REVERB_GAIN = 9;
  
  public static final int AL_REVERB_LATE_REVERB_DELAY = 10;
  
  public static final int AL_REVERB_AIR_ABSORPTION_GAINHF = 11;
  
  public static final int AL_REVERB_ROOM_ROLLOFF_FACTOR = 12;
  
  public static final int AL_REVERB_DECAY_HFLIMIT = 13;
  
  public static final int AL_CHORUS_WAVEFORM = 1;
  
  public static final int AL_CHORUS_PHASE = 2;
  
  public static final int AL_CHORUS_RATE = 3;
  
  public static final int AL_CHORUS_DEPTH = 4;
  
  public static final int AL_CHORUS_FEEDBACK = 5;
  
  public static final int AL_CHORUS_DELAY = 6;
  
  public static final int AL_DISTORTION_EDGE = 1;
  
  public static final int AL_DISTORTION_GAIN = 2;
  
  public static final int AL_DISTORTION_LOWPASS_CUTOFF = 3;
  
  public static final int AL_DISTORTION_EQCENTER = 4;
  
  public static final int AL_DISTORTION_EQBANDWIDTH = 5;
  
  public static final int AL_ECHO_DELAY = 1;
  
  public static final int AL_ECHO_LRDELAY = 2;
  
  public static final int AL_ECHO_DAMPING = 3;
  
  public static final int AL_ECHO_FEEDBACK = 4;
  
  public static final int AL_ECHO_SPREAD = 5;
  
  public static final int AL_FLANGER_WAVEFORM = 1;
  
  public static final int AL_FLANGER_PHASE = 2;
  
  public static final int AL_FLANGER_RATE = 3;
  
  public static final int AL_FLANGER_DEPTH = 4;
  
  public static final int AL_FLANGER_FEEDBACK = 5;
  
  public static final int AL_FLANGER_DELAY = 6;
  
  public static final int AL_FREQUENCY_SHIFTER_FREQUENCY = 1;
  
  public static final int AL_FREQUENCY_SHIFTER_LEFT_DIRECTION = 2;
  
  public static final int AL_FREQUENCY_SHIFTER_RIGHT_DIRECTION = 3;
  
  public static final int AL_VOCAL_MORPHER_PHONEMEA = 1;
  
  public static final int AL_VOCAL_MORPHER_PHONEMEA_COARSE_TUNING = 2;
  
  public static final int AL_VOCAL_MORPHER_PHONEMEB = 3;
  
  public static final int AL_VOCAL_MORPHER_PHONEMEB_COARSE_TUNING = 4;
  
  public static final int AL_VOCAL_MORPHER_WAVEFORM = 5;
  
  public static final int AL_VOCAL_MORPHER_RATE = 6;
  
  public static final int AL_PITCH_SHIFTER_COARSE_TUNE = 1;
  
  public static final int AL_PITCH_SHIFTER_FINE_TUNE = 2;
  
  public static final int AL_RING_MODULATOR_FREQUENCY = 1;
  
  public static final int AL_RING_MODULATOR_HIGHPASS_CUTOFF = 2;
  
  public static final int AL_RING_MODULATOR_WAVEFORM = 3;
  
  public static final int AL_AUTOWAH_ATTACK_TIME = 1;
  
  public static final int AL_AUTOWAH_RELEASE_TIME = 2;
  
  public static final int AL_AUTOWAH_RESONANCE = 3;
  
  public static final int AL_AUTOWAH_PEAK_GAIN = 4;
  
  public static final int AL_COMPRESSOR_ONOFF = 1;
  
  public static final int AL_EQUALIZER_LOW_GAIN = 1;
  
  public static final int AL_EQUALIZER_LOW_CUTOFF = 2;
  
  public static final int AL_EQUALIZER_MID1_GAIN = 3;
  
  public static final int AL_EQUALIZER_MID1_CENTER = 4;
  
  public static final int AL_EQUALIZER_MID1_WIDTH = 5;
  
  public static final int AL_EQUALIZER_MID2_GAIN = 6;
  
  public static final int AL_EQUALIZER_MID2_CENTER = 7;
  
  public static final int AL_EQUALIZER_MID2_WIDTH = 8;
  
  public static final int AL_EQUALIZER_HIGH_GAIN = 9;
  
  public static final int AL_EQUALIZER_HIGH_CUTOFF = 10;
  
  public static final int AL_EFFECT_FIRST_PARAMETER = 0;
  
  public static final int AL_EFFECT_LAST_PARAMETER = 32768;
  
  public static final int AL_EFFECT_TYPE = 32769;
  
  public static final int AL_EFFECT_NULL = 0;
  
  public static final int AL_EFFECT_REVERB = 1;
  
  public static final int AL_EFFECT_CHORUS = 2;
  
  public static final int AL_EFFECT_DISTORTION = 3;
  
  public static final int AL_EFFECT_ECHO = 4;
  
  public static final int AL_EFFECT_FLANGER = 5;
  
  public static final int AL_EFFECT_FREQUENCY_SHIFTER = 6;
  
  public static final int AL_EFFECT_VOCAL_MORPHER = 7;
  
  public static final int AL_EFFECT_PITCH_SHIFTER = 8;
  
  public static final int AL_EFFECT_RING_MODULATOR = 9;
  
  public static final int AL_EFFECT_AUTOWAH = 10;
  
  public static final int AL_EFFECT_COMPRESSOR = 11;
  
  public static final int AL_EFFECT_EQUALIZER = 12;
  
  public static final int AL_EFFECTSLOT_EFFECT = 1;
  
  public static final int AL_EFFECTSLOT_GAIN = 2;
  
  public static final int AL_EFFECTSLOT_AUXILIARY_SEND_AUTO = 3;
  
  public static final int AL_EFFECTSLOT_NULL = 0;
  
  public static final int AL_LOWPASS_GAIN = 1;
  
  public static final int AL_LOWPASS_GAINHF = 2;
  
  public static final int AL_HIGHPASS_GAIN = 1;
  
  public static final int AL_HIGHPASS_GAINLF = 2;
  
  public static final int AL_BANDPASS_GAIN = 1;
  
  public static final int AL_BANDPASS_GAINLF = 2;
  
  public static final int AL_BANDPASS_GAINHF = 3;
  
  public static final int AL_FILTER_FIRST_PARAMETER = 0;
  
  public static final int AL_FILTER_LAST_PARAMETER = 32768;
  
  public static final int AL_FILTER_TYPE = 32769;
  
  public static final int AL_FILTER_NULL = 0;
  
  public static final int AL_FILTER_LOWPASS = 1;
  
  public static final int AL_FILTER_HIGHPASS = 2;
  
  public static final int AL_FILTER_BANDPASS = 3;
  
  public static final double AL_REVERB_MIN_DENSITY = 0.0D;
  
  public static final float AL_REVERB_MAX_DENSITY = 1.0F;
  
  public static final float AL_REVERB_DEFAULT_DENSITY = 1.0F;
  
  public static final double AL_REVERB_MIN_DIFFUSION = 0.0D;
  
  public static final float AL_REVERB_MAX_DIFFUSION = 1.0F;
  
  public static final float AL_REVERB_DEFAULT_DIFFUSION = 1.0F;
  
  public static final double AL_REVERB_MIN_GAIN = 0.0D;
  
  public static final float AL_REVERB_MAX_GAIN = 1.0F;
  
  public static final float AL_REVERB_DEFAULT_GAIN = 0.32F;
  
  public static final double AL_REVERB_MIN_GAINHF = 0.0D;
  
  public static final float AL_REVERB_MAX_GAINHF = 1.0F;
  
  public static final float AL_REVERB_DEFAULT_GAINHF = 0.89F;
  
  public static final float AL_REVERB_MIN_DECAY_TIME = 0.1F;
  
  public static final float AL_REVERB_MAX_DECAY_TIME = 20.0F;
  
  public static final float AL_REVERB_DEFAULT_DECAY_TIME = 1.49F;
  
  public static final float AL_REVERB_MIN_DECAY_HFRATIO = 0.1F;
  
  public static final float AL_REVERB_MAX_DECAY_HFRATIO = 2.0F;
  
  public static final float AL_REVERB_DEFAULT_DECAY_HFRATIO = 0.83F;
  
  public static final double AL_REVERB_MIN_REFLECTIONS_GAIN = 0.0D;
  
  public static final float AL_REVERB_MAX_REFLECTIONS_GAIN = 3.16F;
  
  public static final float AL_REVERB_DEFAULT_REFLECTIONS_GAIN = 0.05F;
  
  public static final double AL_REVERB_MIN_REFLECTIONS_DELAY = 0.0D;
  
  public static final float AL_REVERB_MAX_REFLECTIONS_DELAY = 0.3F;
  
  public static final float AL_REVERB_DEFAULT_REFLECTIONS_DELAY = 0.007F;
  
  public static final double AL_REVERB_MIN_LATE_REVERB_GAIN = 0.0D;
  
  public static final float AL_REVERB_MAX_LATE_REVERB_GAIN = 10.0F;
  
  public static final float AL_REVERB_DEFAULT_LATE_REVERB_GAIN = 1.26F;
  
  public static final double AL_REVERB_MIN_LATE_REVERB_DELAY = 0.0D;
  
  public static final float AL_REVERB_MAX_LATE_REVERB_DELAY = 0.1F;
  
  public static final float AL_REVERB_DEFAULT_LATE_REVERB_DELAY = 0.011F;
  
  public static final float AL_REVERB_MIN_AIR_ABSORPTION_GAINHF = 0.892F;
  
  public static final float AL_REVERB_MAX_AIR_ABSORPTION_GAINHF = 1.0F;
  
  public static final float AL_REVERB_DEFAULT_AIR_ABSORPTION_GAINHF = 0.994F;
  
  public static final double AL_REVERB_MIN_ROOM_ROLLOFF_FACTOR = 0.0D;
  
  public static final float AL_REVERB_MAX_ROOM_ROLLOFF_FACTOR = 10.0F;
  
  public static final double AL_REVERB_DEFAULT_ROOM_ROLLOFF_FACTOR = 0.0D;
  
  public static final int AL_REVERB_MIN_DECAY_HFLIMIT = 0;
  
  public static final int AL_REVERB_MAX_DECAY_HFLIMIT = 1;
  
  public static final int AL_REVERB_DEFAULT_DECAY_HFLIMIT = 1;
  
  public static final int AL_CHORUS_MIN_WAVEFORM = 0;
  
  public static final int AL_CHORUS_MAX_WAVEFORM = 1;
  
  public static final int AL_CHORUS_DEFAULT_WAVEFORM = 1;
  
  public static final int AL_CHORUS_WAVEFORM_SINUSOID = 0;
  
  public static final int AL_CHORUS_WAVEFORM_TRIANGLE = 1;
  
  public static final int AL_CHORUS_MAX_PHASE = 180;
  
  public static final int AL_CHORUS_DEFAULT_PHASE = 90;
  
  public static final double AL_CHORUS_MIN_RATE = 0.0D;
  
  public static final float AL_CHORUS_MAX_RATE = 10.0F;
  
  public static final float AL_CHORUS_DEFAULT_RATE = 1.1F;
  
  public static final double AL_CHORUS_MIN_DEPTH = 0.0D;
  
  public static final float AL_CHORUS_MAX_DEPTH = 1.0F;
  
  public static final float AL_CHORUS_DEFAULT_DEPTH = 0.1F;
  
  public static final float AL_CHORUS_MAX_FEEDBACK = 1.0F;
  
  public static final float AL_CHORUS_DEFAULT_FEEDBACK = 0.25F;
  
  public static final double AL_CHORUS_MIN_DELAY = 0.0D;
  
  public static final float AL_CHORUS_MAX_DELAY = 0.016F;
  
  public static final float AL_CHORUS_DEFAULT_DELAY = 0.016F;
  
  public static final double AL_DISTORTION_MIN_EDGE = 0.0D;
  
  public static final float AL_DISTORTION_MAX_EDGE = 1.0F;
  
  public static final float AL_DISTORTION_DEFAULT_EDGE = 0.2F;
  
  public static final float AL_DISTORTION_MIN_GAIN = 0.01F;
  
  public static final float AL_DISTORTION_MAX_GAIN = 1.0F;
  
  public static final float AL_DISTORTION_DEFAULT_GAIN = 0.05F;
  
  public static final float AL_DISTORTION_MIN_LOWPASS_CUTOFF = 80.0F;
  
  public static final float AL_DISTORTION_MAX_LOWPASS_CUTOFF = 24000.0F;
  
  public static final float AL_DISTORTION_DEFAULT_LOWPASS_CUTOFF = 8000.0F;
  
  public static final float AL_DISTORTION_MIN_EQCENTER = 80.0F;
  
  public static final float AL_DISTORTION_MAX_EQCENTER = 24000.0F;
  
  public static final float AL_DISTORTION_DEFAULT_EQCENTER = 3600.0F;
  
  public static final float AL_DISTORTION_MIN_EQBANDWIDTH = 80.0F;
  
  public static final float AL_DISTORTION_MAX_EQBANDWIDTH = 24000.0F;
  
  public static final float AL_DISTORTION_DEFAULT_EQBANDWIDTH = 3600.0F;
  
  public static final double AL_ECHO_MIN_DELAY = 0.0D;
  
  public static final float AL_ECHO_MAX_DELAY = 0.207F;
  
  public static final float AL_ECHO_DEFAULT_DELAY = 0.1F;
  
  public static final double AL_ECHO_MIN_LRDELAY = 0.0D;
  
  public static final float AL_ECHO_MAX_LRDELAY = 0.404F;
  
  public static final float AL_ECHO_DEFAULT_LRDELAY = 0.1F;
  
  public static final double AL_ECHO_MIN_DAMPING = 0.0D;
  
  public static final float AL_ECHO_MAX_DAMPING = 0.99F;
  
  public static final float AL_ECHO_DEFAULT_DAMPING = 0.5F;
  
  public static final double AL_ECHO_MIN_FEEDBACK = 0.0D;
  
  public static final float AL_ECHO_MAX_FEEDBACK = 1.0F;
  
  public static final float AL_ECHO_DEFAULT_FEEDBACK = 0.5F;
  
  public static final float AL_ECHO_MAX_SPREAD = 1.0F;
  
  public static final int AL_FLANGER_MIN_WAVEFORM = 0;
  
  public static final int AL_FLANGER_MAX_WAVEFORM = 1;
  
  public static final int AL_FLANGER_DEFAULT_WAVEFORM = 1;
  
  public static final int AL_FLANGER_WAVEFORM_SINUSOID = 0;
  
  public static final int AL_FLANGER_WAVEFORM_TRIANGLE = 1;
  
  public static final int AL_FLANGER_MAX_PHASE = 180;
  
  public static final int AL_FLANGER_DEFAULT_PHASE = 90;
  
  public static final double AL_FLANGER_MIN_RATE = 0.0D;
  
  public static final float AL_FLANGER_MAX_RATE = 10.0F;
  
  public static final float AL_FLANGER_DEFAULT_RATE = 0.27F;
  
  public static final double AL_FLANGER_MIN_DEPTH = 0.0D;
  
  public static final float AL_FLANGER_MAX_DEPTH = 1.0F;
  
  public static final float AL_FLANGER_DEFAULT_DEPTH = 1.0F;
  
  public static final float AL_FLANGER_MAX_FEEDBACK = 1.0F;
  
  public static final double AL_FLANGER_MIN_DELAY = 0.0D;
  
  public static final float AL_FLANGER_MAX_DELAY = 0.004F;
  
  public static final float AL_FLANGER_DEFAULT_DELAY = 0.002F;
  
  public static final double AL_FREQUENCY_SHIFTER_MIN_FREQUENCY = 0.0D;
  
  public static final float AL_FREQUENCY_SHIFTER_MAX_FREQUENCY = 24000.0F;
  
  public static final double AL_FREQUENCY_SHIFTER_DEFAULT_FREQUENCY = 0.0D;
  
  public static final int AL_FREQUENCY_SHIFTER_MIN_LEFT_DIRECTION = 0;
  
  public static final int AL_FREQUENCY_SHIFTER_MAX_LEFT_DIRECTION = 2;
  
  public static final int AL_FREQUENCY_SHIFTER_DEFAULT_LEFT_DIRECTION = 0;
  
  public static final int AL_FREQUENCY_SHIFTER_MIN_RIGHT_DIRECTION = 0;
  
  public static final int AL_FREQUENCY_SHIFTER_MAX_RIGHT_DIRECTION = 2;
  
  public static final int AL_FREQUENCY_SHIFTER_DEFAULT_RIGHT_DIRECTION = 0;
  
  public static final int AL_FREQUENCY_SHIFTER_DIRECTION_DOWN = 0;
  
  public static final int AL_FREQUENCY_SHIFTER_DIRECTION_UP = 1;
  
  public static final int AL_FREQUENCY_SHIFTER_DIRECTION_OFF = 2;
  
  public static final int AL_VOCAL_MORPHER_MIN_PHONEMEA = 0;
  
  public static final int AL_VOCAL_MORPHER_MAX_PHONEMEA = 29;
  
  public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEA = 0;
  
  public static final int AL_VOCAL_MORPHER_MAX_PHONEMEA_COARSE_TUNING = 24;
  
  public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEA_COARSE_TUNING = 0;
  
  public static final int AL_VOCAL_MORPHER_MIN_PHONEMEB = 0;
  
  public static final int AL_VOCAL_MORPHER_MAX_PHONEMEB = 29;
  
  public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEB = 10;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_A = 0;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_E = 1;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_I = 2;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_O = 3;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_U = 4;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_AA = 5;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_AE = 6;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_AH = 7;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_AO = 8;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_EH = 9;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_ER = 10;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_IH = 11;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_IY = 12;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_UH = 13;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_UW = 14;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_B = 15;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_D = 16;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_F = 17;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_G = 18;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_J = 19;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_K = 20;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_L = 21;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_M = 22;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_N = 23;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_P = 24;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_R = 25;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_S = 26;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_T = 27;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_V = 28;
  
  public static final int AL_VOCAL_MORPHER_PHONEME_Z = 29;
  
  public static final int AL_VOCAL_MORPHER_MAX_PHONEMEB_COARSE_TUNING = 24;
  
  public static final int AL_VOCAL_MORPHER_DEFAULT_PHONEMEB_COARSE_TUNING = 0;
  
  public static final int AL_VOCAL_MORPHER_MIN_WAVEFORM = 0;
  
  public static final int AL_VOCAL_MORPHER_MAX_WAVEFORM = 2;
  
  public static final int AL_VOCAL_MORPHER_DEFAULT_WAVEFORM = 0;
  
  public static final int AL_VOCAL_MORPHER_WAVEFORM_SINUSOID = 0;
  
  public static final int AL_VOCAL_MORPHER_WAVEFORM_TRIANGLE = 1;
  
  public static final int AL_VOCAL_MORPHER_WAVEFORM_SAWTOOTH = 2;
  
  public static final double AL_VOCAL_MORPHER_MIN_RATE = 0.0D;
  
  public static final float AL_VOCAL_MORPHER_MAX_RATE = 10.0F;
  
  public static final float AL_VOCAL_MORPHER_DEFAULT_RATE = 1.41F;
  
  public static final int AL_PITCH_SHIFTER_MAX_COARSE_TUNE = 12;
  
  public static final int AL_PITCH_SHIFTER_DEFAULT_COARSE_TUNE = 12;
  
  public static final int AL_PITCH_SHIFTER_MAX_FINE_TUNE = 50;
  
  public static final int AL_PITCH_SHIFTER_DEFAULT_FINE_TUNE = 0;
  
  public static final double AL_RING_MODULATOR_MIN_FREQUENCY = 0.0D;
  
  public static final float AL_RING_MODULATOR_MAX_FREQUENCY = 8000.0F;
  
  public static final float AL_RING_MODULATOR_DEFAULT_FREQUENCY = 440.0F;
  
  public static final double AL_RING_MODULATOR_MIN_HIGHPASS_CUTOFF = 0.0D;
  
  public static final float AL_RING_MODULATOR_MAX_HIGHPASS_CUTOFF = 24000.0F;
  
  public static final float AL_RING_MODULATOR_DEFAULT_HIGHPASS_CUTOFF = 800.0F;
  
  public static final int AL_RING_MODULATOR_MIN_WAVEFORM = 0;
  
  public static final int AL_RING_MODULATOR_MAX_WAVEFORM = 2;
  
  public static final int AL_RING_MODULATOR_DEFAULT_WAVEFORM = 0;
  
  public static final int AL_RING_MODULATOR_SINUSOID = 0;
  
  public static final int AL_RING_MODULATOR_SAWTOOTH = 1;
  
  public static final int AL_RING_MODULATOR_SQUARE = 2;
  
  public static final float AL_AUTOWAH_MIN_ATTACK_TIME = 1.0E-4F;
  
  public static final float AL_AUTOWAH_MAX_ATTACK_TIME = 1.0F;
  
  public static final float AL_AUTOWAH_DEFAULT_ATTACK_TIME = 0.06F;
  
  public static final float AL_AUTOWAH_MIN_RELEASE_TIME = 1.0E-4F;
  
  public static final float AL_AUTOWAH_MAX_RELEASE_TIME = 1.0F;
  
  public static final float AL_AUTOWAH_DEFAULT_RELEASE_TIME = 0.06F;
  
  public static final float AL_AUTOWAH_MIN_RESONANCE = 2.0F;
  
  public static final float AL_AUTOWAH_MAX_RESONANCE = 1000.0F;
  
  public static final float AL_AUTOWAH_DEFAULT_RESONANCE = 1000.0F;
  
  public static final float AL_AUTOWAH_MIN_PEAK_GAIN = 3.0E-5F;
  
  public static final float AL_AUTOWAH_MAX_PEAK_GAIN = 31621.0F;
  
  public static final float AL_AUTOWAH_DEFAULT_PEAK_GAIN = 11.22F;
  
  public static final int AL_COMPRESSOR_MIN_ONOFF = 0;
  
  public static final int AL_COMPRESSOR_MAX_ONOFF = 1;
  
  public static final int AL_COMPRESSOR_DEFAULT_ONOFF = 1;
  
  public static final float AL_EQUALIZER_MIN_LOW_GAIN = 0.126F;
  
  public static final float AL_EQUALIZER_MAX_LOW_GAIN = 7.943F;
  
  public static final float AL_EQUALIZER_DEFAULT_LOW_GAIN = 1.0F;
  
  public static final float AL_EQUALIZER_MIN_LOW_CUTOFF = 50.0F;
  
  public static final float AL_EQUALIZER_MAX_LOW_CUTOFF = 800.0F;
  
  public static final float AL_EQUALIZER_DEFAULT_LOW_CUTOFF = 200.0F;
  
  public static final float AL_EQUALIZER_MIN_MID1_GAIN = 0.126F;
  
  public static final float AL_EQUALIZER_MAX_MID1_GAIN = 7.943F;
  
  public static final float AL_EQUALIZER_DEFAULT_MID1_GAIN = 1.0F;
  
  public static final float AL_EQUALIZER_MIN_MID1_CENTER = 200.0F;
  
  public static final float AL_EQUALIZER_MAX_MID1_CENTER = 3000.0F;
  
  public static final float AL_EQUALIZER_DEFAULT_MID1_CENTER = 500.0F;
  
  public static final float AL_EQUALIZER_MIN_MID1_WIDTH = 0.01F;
  
  public static final float AL_EQUALIZER_MAX_MID1_WIDTH = 1.0F;
  
  public static final float AL_EQUALIZER_DEFAULT_MID1_WIDTH = 1.0F;
  
  public static final float AL_EQUALIZER_MIN_MID2_GAIN = 0.126F;
  
  public static final float AL_EQUALIZER_MAX_MID2_GAIN = 7.943F;
  
  public static final float AL_EQUALIZER_DEFAULT_MID2_GAIN = 1.0F;
  
  public static final float AL_EQUALIZER_MIN_MID2_CENTER = 1000.0F;
  
  public static final float AL_EQUALIZER_MAX_MID2_CENTER = 8000.0F;
  
  public static final float AL_EQUALIZER_DEFAULT_MID2_CENTER = 3000.0F;
  
  public static final float AL_EQUALIZER_MIN_MID2_WIDTH = 0.01F;
  
  public static final float AL_EQUALIZER_MAX_MID2_WIDTH = 1.0F;
  
  public static final float AL_EQUALIZER_DEFAULT_MID2_WIDTH = 1.0F;
  
  public static final float AL_EQUALIZER_MIN_HIGH_GAIN = 0.126F;
  
  public static final float AL_EQUALIZER_MAX_HIGH_GAIN = 7.943F;
  
  public static final float AL_EQUALIZER_DEFAULT_HIGH_GAIN = 1.0F;
  
  public static final float AL_EQUALIZER_MIN_HIGH_CUTOFF = 4000.0F;
  
  public static final float AL_EQUALIZER_MAX_HIGH_CUTOFF = 16000.0F;
  
  public static final float AL_EQUALIZER_DEFAULT_HIGH_CUTOFF = 6000.0F;
  
  public static final int AL_INVALID = -1;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\ALConstants.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */