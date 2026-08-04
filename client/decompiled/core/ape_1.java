/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from aPe
 */
public enum ape_1 {
    enI(XugglerJNI.IStreamCoder_FLAG_QSCALE_get()),
    enJ(XugglerJNI.IStreamCoder_FLAG_4MV_get()),
    enK(XugglerJNI.IStreamCoder_FLAG_QPEL_get()),
    enL(XugglerJNI.IStreamCoder_FLAG_GMC_get()),
    enM(XugglerJNI.IStreamCoder_FLAG_MV0_get()),
    enN(XugglerJNI.IStreamCoder_FLAG_PART_get()),
    enO(XugglerJNI.IStreamCoder_FLAG_INPUT_PRESERVED_get()),
    enP(XugglerJNI.IStreamCoder_FLAG_PASS1_get()),
    enQ(XugglerJNI.IStreamCoder_FLAG_PASS2_get()),
    enR(XugglerJNI.IStreamCoder_FLAG_EXTERN_HUFF_get()),
    enS(XugglerJNI.IStreamCoder_FLAG_GRAY_get()),
    enT(XugglerJNI.IStreamCoder_FLAG_EMU_EDGE_get()),
    enU(XugglerJNI.IStreamCoder_FLAG_PSNR_get()),
    enV(XugglerJNI.IStreamCoder_FLAG_TRUNCATED_get()),
    enW(XugglerJNI.IStreamCoder_FLAG_NORMALIZE_AQP_get()),
    enX(XugglerJNI.IStreamCoder_FLAG_INTERLACED_DCT_get()),
    enY(XugglerJNI.IStreamCoder_FLAG_LOW_DELAY_get()),
    enZ(XugglerJNI.IStreamCoder_FLAG_ALT_SCAN_get()),
    eoa(XugglerJNI.IStreamCoder_FLAG_TRELLIS_QUANT_get()),
    eob(XugglerJNI.IStreamCoder_FLAG_GLOBAL_HEADER_get()),
    eoc(XugglerJNI.IStreamCoder_FLAG_BITEXACT_get()),
    eod(XugglerJNI.IStreamCoder_FLAG_AC_PRED_get()),
    eoe(XugglerJNI.IStreamCoder_FLAG_H263P_UMV_get()),
    eof(XugglerJNI.IStreamCoder_FLAG_CBP_RD_get()),
    eog(XugglerJNI.IStreamCoder_FLAG_QP_RD_get()),
    eoh(XugglerJNI.IStreamCoder_FLAG_H263P_AIV_get()),
    eoi(XugglerJNI.IStreamCoder_FLAG_OBMC_get()),
    eoj(XugglerJNI.IStreamCoder_FLAG_LOOP_FILTER_get()),
    eok(XugglerJNI.IStreamCoder_FLAG_H263P_SLICE_STRUCT_get()),
    eol(XugglerJNI.IStreamCoder_FLAG_INTERLACED_ME_get()),
    eom(XugglerJNI.IStreamCoder_FLAG_SVCD_SCAN_OFFSET_get()),
    eon(XugglerJNI.IStreamCoder_FLAG_CLOSED_GOP_get()),
    eoo(XugglerJNI.IStreamCoder_FLAG2_FAST_get()),
    eop(XugglerJNI.IStreamCoder_FLAG2_STRICT_GOP_get()),
    eoq(XugglerJNI.IStreamCoder_FLAG2_NO_OUTPUT_get()),
    eor(XugglerJNI.IStreamCoder_FLAG2_LOCAL_HEADER_get()),
    eos(XugglerJNI.IStreamCoder_FLAG2_BPYRAMID_get()),
    eot(XugglerJNI.IStreamCoder_FLAG2_WPRED_get()),
    eou(XugglerJNI.IStreamCoder_FLAG2_MIXED_REFS_get()),
    eov(XugglerJNI.IStreamCoder_FLAG2_8X8DCT_get()),
    eow(XugglerJNI.IStreamCoder_FLAG2_FASTPSKIP_get()),
    eox(XugglerJNI.IStreamCoder_FLAG2_AUD_get()),
    eoy(XugglerJNI.IStreamCoder_FLAG2_BRDO_get()),
    eoz(XugglerJNI.IStreamCoder_FLAG2_INTRA_VLC_get()),
    eoA(XugglerJNI.IStreamCoder_FLAG2_MEMC_ONLY_get()),
    eoB(XugglerJNI.IStreamCoder_FLAG2_DROP_FRAME_TIMECODE_get()),
    eoC(XugglerJNI.IStreamCoder_FLAG2_SKIP_RD_get()),
    eoD(XugglerJNI.IStreamCoder_FLAG2_CHUNKS_get()),
    eoE(XugglerJNI.IStreamCoder_FLAG2_NON_LINEAR_QUANT_get()),
    eoF(XugglerJNI.IStreamCoder_FLAG2_BIT_RESERVOIR_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static ape_1 pS(int n2) {
        ape_1[] ape_1Array = (ape_1[])ape_1.class.getEnumConstants();
        if (n2 < ape_1Array.length && n2 >= 0 && ape_1Array[n2].hU == n2) {
            return ape_1Array[n2];
        }
        for (ape_1 ape_12 : ape_1Array) {
            if (ape_12.hU != n2) continue;
            return ape_12;
        }
        throw new IllegalArgumentException("No enum " + ape_1.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ape_1() {
        this.hU = ka.oA();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ape_1() {
        void var3_1;
        this.hU = var3_1;
        ka.bF((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ape_1() {
        void var3_1;
        this.hU = var3_1.hU;
        ka.bF(this.hU + 1);
    }
}

