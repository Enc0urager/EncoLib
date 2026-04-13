package dev.enco.encolib.utils.text.colorizer;

public class LegacyColorizer implements IColorizer {
    private static final char COLOR_CHAR = '§';

    private static final char[] COLOR_TABLE = new char[128];
    private static final boolean[] HEX_TABLE = new boolean[128];

    static {
        for (char c = '0'; c <= '9'; c++) HEX_TABLE[c] = true;
        for (char c = 'a'; c <= 'f'; c++) HEX_TABLE[c] = true;
        for (char c = 'A'; c <= 'F'; c++) HEX_TABLE[c] = true;

        for (char c = '0'; c <= '9'; c++) COLOR_TABLE[c] = c;
        for (char c = 'a'; c <= 'f'; c++) { COLOR_TABLE[c] = c; COLOR_TABLE[Character.toUpperCase(c)] = c; }
        for (char c = 'k'; c <= 'o'; c++) { COLOR_TABLE[c] = c; COLOR_TABLE[Character.toUpperCase(c)] = c; }
        COLOR_TABLE['r'] = 'r'; COLOR_TABLE['R'] = 'r';
        COLOR_TABLE['x'] = 'x'; COLOR_TABLE['X'] = 'x';
    }

    @Override
    public String colorize(String s) {
        int firstAmp = s.indexOf('&');
        if (firstAmp == -1) return s;

        int len = s.length();
        char[] res = new char[len + ((len >>> 3) * 6)];
        s.getChars(0, firstAmp, res, 0);

        int outIdx = firstAmp;
        for (int i = firstAmp; i < len; i++) {
            char c = s.charAt(i);

            if (c == '&') {
                if (i + 1 < len) {
                    char next = s.charAt(i + 1);

                    if (next == '#' && i + 7 < len) {
                        if (isHex(s, i + 2)) {
                            res[outIdx++] = COLOR_CHAR;
                            res[outIdx++] = 'x';
                            res[outIdx++] = COLOR_CHAR; res[outIdx++] = s.charAt(i + 2);
                            res[outIdx++] = COLOR_CHAR; res[outIdx++] = s.charAt(i + 3);
                            res[outIdx++] = COLOR_CHAR; res[outIdx++] = s.charAt(i + 4);
                            res[outIdx++] = COLOR_CHAR; res[outIdx++] = s.charAt(i + 5);
                            res[outIdx++] = COLOR_CHAR; res[outIdx++] = s.charAt(i + 6);
                            res[outIdx++] = COLOR_CHAR; res[outIdx++] = s.charAt(i + 7);
                            i += 7;
                            continue;
                        }
                    }

                    char mapped = next < 128 ? COLOR_TABLE[next] : 0;
                    if (mapped != 0) {
                        res[outIdx++] = COLOR_CHAR;
                        res[outIdx++] = mapped;
                        i++;
                        continue;
                    }
                }
            }
            res[outIdx++] = c;
        }

        return new String(res, 0, outIdx);
    }

    private boolean isHex(String s, int start) {
        char c;
        return (c = s.charAt(start)) < 128 && HEX_TABLE[c] &&
                (c = s.charAt(start + 1)) < 128 && HEX_TABLE[c] &&
                (c = s.charAt(start + 2)) < 128 && HEX_TABLE[c] &&
                (c = s.charAt(start + 3)) < 128 && HEX_TABLE[c] &&
                (c = s.charAt(start + 4)) < 128 && HEX_TABLE[c] &&
                (c = s.charAt(start + 5)) < 128 && HEX_TABLE[c];
    }
}
