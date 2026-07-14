// gifenc.ts -- a compact, dependency-free animated GIF89a encoder. It takes a
// set of equally-sized RGBA frames (Uint8ClampedArray from a canvas), builds a
// shared 255-color palette via median-cut quantization (reserving one slot for
// transparency), and emits LZW-compressed frames with per-frame delays and an
// infinite-loop application extension.
//
// This exists so the animation player can export a shareable looping GIF without
// pulling in a third-party encoder. Quality is "good enough for previews": a
// global palette + nearest-color mapping, alpha thresholded to a single
// transparent index (GIF has no partial alpha).

export interface GifFrame {
  rgba: Uint8ClampedArray; // width*height*4
  delayMs: number; // frame duration
}

interface RGB {
  r: number;
  g: number;
  b: number;
}

// --- median-cut quantization -------------------------------------------------

interface Box {
  pixels: RGB[];
  rMin: number;
  rMax: number;
  gMin: number;
  gMax: number;
  bMin: number;
  bMax: number;
}

function makeBox(pixels: RGB[]): Box {
  let rMin = 255,
    rMax = 0,
    gMin = 255,
    gMax = 0,
    bMin = 255,
    bMax = 0;
  for (const p of pixels) {
    if (p.r < rMin) rMin = p.r;
    if (p.r > rMax) rMax = p.r;
    if (p.g < gMin) gMin = p.g;
    if (p.g > gMax) gMax = p.g;
    if (p.b < bMin) bMin = p.b;
    if (p.b > bMax) bMax = p.b;
  }
  return { pixels, rMin, rMax, gMin, gMax, bMin, bMax };
}

function longestAxis(b: Box): "r" | "g" | "b" {
  const dr = b.rMax - b.rMin;
  const dg = b.gMax - b.gMin;
  const db = b.bMax - b.bMin;
  if (dr >= dg && dr >= db) return "r";
  if (dg >= db) return "g";
  return "b";
}

// quantize returns up to maxColors representative RGB colors for the sampled
// opaque pixels using median cut.
function quantize(samples: RGB[], maxColors: number): RGB[] {
  if (samples.length === 0) return [{ r: 0, g: 0, b: 0 }];
  let boxes: Box[] = [makeBox(samples)];
  while (boxes.length < maxColors) {
    // Split the box with the most pixels and a non-zero axis.
    let idx = -1;
    let best = 0;
    for (let i = 0; i < boxes.length; i++) {
      const b = boxes[i];
      const size = b.pixels.length;
      const spread = b.rMax - b.rMin + (b.gMax - b.gMin) + (b.bMax - b.bMin);
      if (size > 1 && spread > 0 && size > best) {
        best = size;
        idx = i;
      }
    }
    if (idx < 0) break;
    const box = boxes[idx];
    const axis = longestAxis(box);
    box.pixels.sort((p, q) => p[axis] - q[axis]);
    const mid = box.pixels.length >> 1;
    const left = box.pixels.slice(0, mid);
    const right = box.pixels.slice(mid);
    if (left.length === 0 || right.length === 0) break;
    boxes.splice(idx, 1, makeBox(left), makeBox(right));
  }
  return boxes.map((b) => {
    let r = 0,
      g = 0,
      bl = 0;
    for (const p of b.pixels) {
      r += p.r;
      g += p.g;
      bl += p.b;
    }
    const n = b.pixels.length || 1;
    return { r: Math.round(r / n), g: Math.round(g / n), b: Math.round(bl / n) };
  });
}

// --- byte buffer -------------------------------------------------------------

class ByteBuf {
  private bytes: number[] = [];
  u8(v: number) {
    this.bytes.push(v & 0xff);
  }
  u16(v: number) {
    this.bytes.push(v & 0xff, (v >> 8) & 0xff);
  }
  str(s: string) {
    for (let i = 0; i < s.length; i++) this.bytes.push(s.charCodeAt(i) & 0xff);
  }
  push(arr: number[]) {
    for (const b of arr) this.bytes.push(b & 0xff);
  }
  toUint8(): Uint8Array {
    return new Uint8Array(this.bytes);
  }
}

// --- LZW compression (GIF variable-width) ------------------------------------

function lzwEncode(minCodeSize: number, indices: Uint8Array): number[] {
  const clearCode = 1 << minCodeSize;
  const eoiCode = clearCode + 1;
  let codeSize = minCodeSize + 1;
  let dict = new Map<string, number>();
  const initDict = () => {
    dict = new Map();
    for (let i = 0; i < clearCode; i++) dict.set(String(i), i);
  };
  initDict();
  let next = eoiCode + 1;

  const out: number[] = [];
  let cur = 0;
  let curBits = 0;
  const emit = (code: number) => {
    cur |= code << curBits;
    curBits += codeSize;
    while (curBits >= 8) {
      out.push(cur & 0xff);
      cur >>= 8;
      curBits -= 8;
    }
  };

  emit(clearCode);
  let prefix = String(indices[0]);
  for (let i = 1; i < indices.length; i++) {
    const k = indices[i];
    const combined = prefix + "," + k;
    if (dict.has(combined)) {
      prefix = combined;
    } else {
      emit(dict.get(prefix)!);
      dict.set(combined, next++);
      if (next > (1 << codeSize) && codeSize < 12) codeSize++;
      if (next > 4095) {
        emit(clearCode);
        initDict();
        next = eoiCode + 1;
        codeSize = minCodeSize + 1;
      }
      prefix = String(k);
    }
  }
  emit(dict.get(prefix)!);
  emit(eoiCode);
  if (curBits > 0) out.push(cur & 0xff);
  return out;
}

// --- palette mapping ---------------------------------------------------------

// buildPalette samples opaque pixels across all frames and returns a palette of
// exactly `size` entries (padded), plus a nearest-color lookup.
function buildPalette(frames: GifFrame[], size: number) {
  const samples: RGB[] = [];
  const stride = 4;
  // Sample sparsely to keep quantization fast on big frames.
  for (const f of frames) {
    const px = f.rgba;
    const step = Math.max(1, Math.floor(px.length / stride / 4096)) * stride;
    for (let i = 0; i < px.length; i += step) {
      if (px[i + 3] >= 128) samples.push({ r: px[i], g: px[i + 1], b: px[i + 2] });
    }
  }
  const colors = quantize(samples, size);
  while (colors.length < size) colors.push({ r: 0, g: 0, b: 0 });
  return colors.slice(0, size);
}

function nearestIndex(palette: RGB[], r: number, g: number, b: number): number {
  let best = 0;
  let bestD = Infinity;
  for (let i = 0; i < palette.length; i++) {
    const p = palette[i];
    const dr = p.r - r;
    const dg = p.g - g;
    const db = p.b - b;
    const d = dr * dr + dg * dg + db * db;
    if (d < bestD) {
      bestD = d;
      best = i;
    }
  }
  return best;
}

// --- public API --------------------------------------------------------------

// encodeGif builds an animated GIF89a from equally-sized RGBA frames. Pixels
// with alpha < 128 map to a dedicated transparent color index. loop=0 means
// loop forever. Returns the GIF bytes.
export function encodeGif(
  width: number,
  height: number,
  frames: GifFrame[],
  loop = 0
): Uint8Array {
  // Reserve index 255 for transparency; quantize the rest to 255 colors.
  const TRANSPARENT = 255;
  const palette = buildPalette(frames, 255);
  // Pad palette to 256 entries (256 = 2^8 -> 8-bit color table).
  const fullPalette: RGB[] = palette.slice();
  while (fullPalette.length < 256) fullPalette.push({ r: 0, g: 0, b: 0 });

  const buf = new ByteBuf();
  buf.str("GIF89a");
  buf.u16(width);
  buf.u16(height);
  // Global color table flag=1, color resolution=7, sort=0, size=7 (256).
  buf.u8(0xf7);
  buf.u8(TRANSPARENT); // background color index
  buf.u8(0); // pixel aspect ratio
  for (let i = 0; i < 256; i++) {
    buf.u8(fullPalette[i].r);
    buf.u8(fullPalette[i].g);
    buf.u8(fullPalette[i].b);
  }

  // NETSCAPE2.0 looping extension.
  buf.u8(0x21);
  buf.u8(0xff);
  buf.u8(11);
  buf.str("NETSCAPE2.0");
  buf.u8(3);
  buf.u8(1);
  buf.u16(loop); // 0 = infinite
  buf.u8(0);

  for (const f of frames) {
    // Graphic control extension: transparency + delay (in 1/100 s).
    const delayCs = Math.max(1, Math.round(f.delayMs / 10));
    buf.u8(0x21);
    buf.u8(0xf9);
    buf.u8(4);
    // disposal=2 (restore to background) + transparent color flag=1.
    buf.u8(0x09);
    buf.u16(delayCs);
    buf.u8(TRANSPARENT);
    buf.u8(0);

    // Image descriptor.
    buf.u8(0x2c);
    buf.u16(0);
    buf.u16(0);
    buf.u16(width);
    buf.u16(height);
    buf.u8(0); // no local color table

    // Map pixels to indices.
    const px = f.rgba;
    const indices = new Uint8Array(width * height);
    for (let p = 0, j = 0; p < px.length; p += 4, j++) {
      if (px[p + 3] < 128) indices[j] = TRANSPARENT;
      else indices[j] = nearestIndex(palette, px[p], px[p + 1], px[p + 2]);
    }

    const minCodeSize = 8;
    buf.u8(minCodeSize);
    const lzw = lzwEncode(minCodeSize, indices);
    // Emit as sub-blocks of <=255 bytes.
    for (let i = 0; i < lzw.length; i += 255) {
      const chunk = lzw.slice(i, i + 255);
      buf.u8(chunk.length);
      buf.push(chunk);
    }
    buf.u8(0); // block terminator
  }

  buf.u8(0x3b); // trailer
  return buf.toUint8();
}
