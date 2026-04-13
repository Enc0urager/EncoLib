package dev.enco.encolib.text.formatting;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PlaceholderFormatter {

    public static String format(String text, String[] keys, String[] values) {
        if (text == null || keys == null || values == null) return text;
        int keysLen = keys.length;
        int valuesLen = values.length;
        if (keysLen == 0 || valuesLen == 0) return text;
        if (valuesLen != keysLen) throw new IllegalArgumentException("Values and keys must have the same lengths");
        if (keysLen < 5) return formatShort(text, keys, values);
        return formatBig(text, keys, values);
    }

    public static String formatBig(String text, String[] keys, String[] values) {
        int first = text.indexOf('{');
        if (first == -1) return text;

        StringBuilder sb = new StringBuilder(text.length() + 64);
        int cur = 0; int pos = first;

        while (pos != -1) {
            sb.append(text, cur, pos);

            int end = text.indexOf('}', pos + 1);
            if (end == -1) break;

            int len = end - pos + 1;

            boolean replaced = false;
            char firstChar = text.charAt(pos + 1);

            for (int i = 0; i < keys.length; i++) {
                String k = keys[i];

                if (k.charAt(1) != firstChar) continue;

                if (k.length() == len && text.regionMatches(pos, k, 0, len)) {
                    sb.append(values[i]);
                    cur = end + 1;
                    pos = text.indexOf('{', cur);
                    replaced = true;
                    break;
                }
            }

            if (replaced) continue;

            sb.append(text, pos, end + 1);
            cur = end + 1;
            pos = text.indexOf('{', cur);
        }

        if (cur < text.length()) sb.append(text, cur, text.length());
        return sb.toString();
    }

    public static String formatShort(String text, String[] keys, String[] values) {
        final StringBuilder result = new StringBuilder(text);

        for (int i = 0; i < keys.length; i++) {
            String val = values[i];
            String key = keys[i];

            int start = 0;

            while ((start = result.indexOf(key, start)) != -1) {
                result.replace(start, start + key.length(), val);
                start += val.length();
            }
        }

        return result.toString();
    }
}
