import { useEffect } from "react";
export function useAmbience(enabled: boolean) {
  useEffect(() => {
    if (!enabled) return;
    let ctx: AudioContext | undefined;
    try {
      ctx = new AudioContext();
      const length = ctx.sampleRate * 6,
        buffer = ctx.createBuffer(1, length, ctx.sampleRate),
        samples = buffer.getChannelData(0);
      let brown = 0;
      for (let i = 0; i < length; i++) {
        brown = (brown + 0.025 * (Math.random() * 2 - 1)) / 1.025;
        samples[i] = brown * 3;
      }
      const source = ctx.createBufferSource();
      source.buffer = buffer;
      source.loop = true;
      const filter = ctx.createBiquadFilter();
      filter.type = "lowpass";
      filter.frequency.value = 600;
      const gain = ctx.createGain();
      gain.gain.value = 0.12;
      source.connect(filter).connect(gain).connect(ctx.destination);
      source.start();
      void ctx.resume();
    } catch {
      /* Audio can be unavailable; visual gameplay is independent. */
    }
    return () => {
      void ctx?.close();
    };
  }, [enabled]);
}
