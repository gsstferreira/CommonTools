package Base64Tools;

import java.io.IOException;
import static AssertTools.Assert.*;

abstract class Methods {

    static String encodeBytes(byte[] bytes) {

        final int len = bytes.length;
        final int outLen = (len/3 + (len%3 == 0 ? 0 : 1)) * 4;
        final char[] chars = new char[outLen];

        int i = 0;
        int j = 0;

        while(i < len - 2) {
            chars[j++] = valueToChar((bytes[i] >>> 2) & 0x003F);
            chars[j++] = valueToChar(((bytes[i++] << 4) & 0x0030) + ((bytes[i] >>> 4) & 0x000F));
            chars[j++] = valueToChar(((bytes[i++] << 2) & 0x003C) + ((bytes[i] >>> 6) & 0x0003));
            chars[j++] = valueToChar(bytes[i++] & 0x003F);
        }

        switch (len - i) {
            case 0:
                break;
            case 1:
                chars[j++] = valueToChar((bytes[i] >>> 2) & 0x003F);
                chars[j++] = valueToChar((bytes[i] << 4) & 0x0030);
                chars[j++] = valueToChar(64);
                chars[j] = valueToChar(64);
                break;
            case 2:
                chars[j++] = valueToChar((bytes[i] >>> 2) & 0x003F);
                chars[j++] = valueToChar(((bytes[i++] << 4) & 0x0030) + ((bytes[i] >>> 4) & 0x000F));
                chars[j++] = valueToChar((bytes[i] << 2) & 0x003C);
                chars[j] = valueToChar(64);
                break;
        }
        return new String(chars);
    }

    static String encodeChars(char[] chs) {

        final int len = chs.length;
        final int outLen = (len/3 + (len%3 == 0 ? 0 : 1)) * 4;
        final char[] chars = new char[outLen];

        int i = 0;
        int j = 0;

        while(i < len - 2) {
            chars[j++] = valueToChar((chs[i] >>> 2) & 0x003F);
            chars[j++] = valueToChar(((chs[i++] << 4) & 0x0030) + ((chs[i] >>> 4) & 0x000F));
            chars[j++] = valueToChar(((chs[i++] << 2) & 0x003C) + ((chs[i] >>> 6) & 0x0003));
            chars[j++] = valueToChar(chs[i++] & 0x003F);
        }

        switch (len - i) {
            case 0:
                break;
            case 1:
                chars[j++] = valueToChar((chs[i] >>> 2) & 0x003F);
                chars[j++] = valueToChar((chs[i] << 4) & 0x0030);
                chars[j++] = valueToChar(64);
                chars[j] = valueToChar(64);
                break;
            case 2:
                chars[j++] = valueToChar((chs[i] >>> 2) & 0x003F);
                chars[j++] = valueToChar(((chs[i++] << 4) & 0x0030) + ((chs[i] >>> 4) & 0x000F));
                chars[j++] = valueToChar((chs[i] << 2) & 0x003C);
                chars[j] = valueToChar(64);
                break;
        }
        return new String(chars);
    }

    static String encodeBytes_UrlEncoding(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        final int len = bytes.length;
        int i = 0;

        while(i < len - 2) {
            sb.append(encValuesUrl[(bytes[i] >>> 2) & 0x3F]);
            sb.append(encValuesUrl[((bytes[i++] << 4) & 0x30) + ((bytes[i] >>> 4) & 0x0F)]);
            sb.append(encValuesUrl[((bytes[i++] << 2) & 0x3C) + ((bytes[i] >>> 6) & 0x03)]);
            sb.append(encValuesUrl[bytes[i++] & 0x3F]);
        }

        switch (len - i) {
            case 0:
                break;
            case 1:
                sb.append(encValuesUrl[(bytes[i] >>> 2) & 0x3F]);
                sb.append(encValuesUrl[((bytes[i] << 4) & 0x30)]);
                sb.append(encValuesUrl[64]);
                sb.append(encValuesUrl[64]);
                break;
            case 2:
                sb.append(encValuesUrl[(bytes[i] >>> 2) & 0x3F]);
                sb.append(encValuesUrl[((bytes[i++] << 4) & 0x30) + ((bytes[i] >>> 4) & 0x0F)]);
                sb.append(encValuesUrl[((bytes[i] << 2) & 0x3C)]);
                sb.append(encValuesUrl[64]);
                break;
        }
        return sb.toString();
    }

    static byte[] decodeBytes(byte[] bytes) throws IOException {

        final int len = bytes.length;

        if(len%4 != 0) {
            throw new IOException("Input String is not a valid Base64 string.");
        }
        else {
            final int padding = (bytes[len - 1] == '=' ? 1 : 0) + (bytes[len - 2] == '=' ? 1 : 0);

            final int outLen = (len/4)*3 - padding;

            final byte[] outBytes = new byte[outLen];

            int i = 0;
            int j = 0;
            final byte[] cVal = new byte[4];

            while(outLen - j >= 3) {

                cVal[0] = charToValue(bytes[i++]);
                cVal[1] = charToValue(bytes[i++]);
                cVal[2] = charToValue(bytes[i++]);
                cVal[3] = charToValue(bytes[i++]);

                outBytes[j++] = (byte)((cVal[0] * 4) + (cVal[1] / 16));
                outBytes[j++] = (byte)((cVal[1] * 16) + (cVal[2] / 4));
                outBytes[j++] = (byte)((cVal[2] * 64) + cVal[3]);

            }

            switch (padding) {
                case 0:
                    break;
                case 1:
                    cVal[0] = charToValue(bytes[i++]);
                    cVal[1] = charToValue(bytes[i++]);
                    cVal[2] = charToValue(bytes[i]);

                    outBytes[j++] = (byte) ((cVal[0] * 4) + (cVal[1] / 16));
                    outBytes[j] = (byte)((cVal[1] * 16) + (cVal[2] / 4));
                    break;
                case 2:
                    cVal[0] = charToValue(bytes[i++]);
                    cVal[1] = charToValue(bytes[i]);

                    outBytes[j] = (byte)((cVal[0] * 4) + (cVal[1] / 16));
                    break;
            }
            return outBytes;
        }
    }

    static byte[] decodeBytes_UrlEncoding(byte[] bytes) throws IOException {

        StringBuilder sb = new StringBuilder();

        for (int k = 0; k < bytes.length; k++) {

            final byte c = bytes[k];

            if(c != '%') {
                sb.append((char)c);
            }
            else {
                final byte c1 = bytes[k+1];
                final byte c2 = bytes[k+2];

                switch (String.format("%c%c",c1,c2).toUpperCase()) {
                    case "2B":
                        sb.append((char) encValues[62]);
                        k+=2;
                        break;
                    case "2F":
                        sb.append((char) encValues[63]);
                        k+=2;
                        break;
                    case "3D":
                        sb.append((char) encValues[64]);
                        k+=2;
                        break;
                    default:
                        throw new IOException("Input String is not a valid Base64 string.");
                }
            }
        }

        String b64String = sb.toString();

        final int len = b64String.length();

        if(len%4 != 0) {
            throw new IOException("Input String is not a valid Base64 string.");
        }
        else {
            final char[] chs = b64String.toCharArray();
            final int padding = (chs[len - 1] == '=' ? 1 : 0) + (chs[len - 2] == '=' ? 1 : 0);

            final int outLen = (len/4)*3 - padding;

            final byte[] outBytes = new byte[outLen];

            int i = 0;
            int j = 0;
            final byte[] cVal = new byte[4];

            while(outLen - j >= 3) {
                cVal[0] = charToValue(chs[i++]);
                cVal[1] = charToValue(chs[i++]);
                cVal[2] = charToValue(chs[i++]);
                cVal[3] = charToValue(chs[i++]);

                outBytes[j++] = (byte)((cVal[0] * 4) + (cVal[1] / 16));
                outBytes[j++] = (byte)((cVal[1] * 16) + (cVal[2] / 4));
                outBytes[j++] = (byte)((cVal[2] * 64) + cVal[3]);
            }

            switch (padding) {
                case 0:
                    break;
                case 1:
                    cVal[0] = charToValue(chs[i++]);
                    cVal[1] = charToValue(chs[i++]);
                    cVal[2] = charToValue(chs[i]);

                    outBytes[j++] = (byte) ((cVal[0] * 4) + (cVal[1] / 16));
                    outBytes[j] = (byte)((cVal[1] * 16) + (cVal[2] / 4));
                    break;
                case 2:
                    cVal[0] = charToValue(chs[i++]);
                    cVal[1] = charToValue(chs[i]);

                    outBytes[j] = (byte)((cVal[0] * 4) + (cVal[1] / 16));
                    break;
            }
            return outBytes;
        }
    }

    private static char valueToChar(int value) {

        return encValues[value];
    }

    private static byte charToValue(int c) throws IOException {

        try {
            byte val = decValues[c];
            _assert(val != -1);
            return val;
        }
        catch (Exception e) {
            throw new IOException("Input string is not a valid Base64 string");
        }
    }

    private final static char[] encValues = new char[] {
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
            '+','/','='
    };

    private final static CharSequence[] encValuesUrl = new CharSequence[] {
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "0","1","2","3","4","5","6","7","8","9",
            "%2B","%2F","%3D"
    };

    private final static byte[] decValues = new byte[128];
    static {

        for(int i = 0; i < 128; i++) {
            decValues[i] = -1;
        }

        decValues['A'] = 0;
        decValues['B'] = 1;
        decValues['C'] = 2;
        decValues['D'] = 3;
        decValues['E'] = 4;
        decValues['F'] = 5;
        decValues['G'] = 6;
        decValues['H'] = 7;
        decValues['I'] = 8;
        decValues['J'] = 9;
        decValues['K'] = 10;
        decValues['L'] = 11;
        decValues['M'] = 12;
        decValues['N'] = 13;
        decValues['O'] = 14;
        decValues['P'] = 15;
        decValues['Q'] = 16;
        decValues['R'] = 17;
        decValues['S'] = 18;
        decValues['T'] = 19;
        decValues['U'] = 20;
        decValues['V'] = 21;
        decValues['W'] = 22;
        decValues['X'] = 23;
        decValues['Y'] = 24;
        decValues['Z'] = 25;

        decValues['a'] = 26;
        decValues['b'] = 27;
        decValues['c'] = 28;
        decValues['d'] = 29;
        decValues['e'] = 30;
        decValues['f'] = 31;
        decValues['g'] = 32;
        decValues['h'] = 33;
        decValues['i'] = 34;
        decValues['j'] = 35;
        decValues['k'] = 36;
        decValues['l'] = 37;
        decValues['m'] = 38;
        decValues['n'] = 39;
        decValues['o'] = 40;
        decValues['p'] = 41;
        decValues['q'] = 42;
        decValues['r'] = 43;
        decValues['s'] = 44;
        decValues['t'] = 45;
        decValues['u'] = 46;
        decValues['v'] = 47;
        decValues['w'] = 48;
        decValues['x'] = 49;
        decValues['y'] = 50;
        decValues['z'] = 51;

        decValues['0'] = 52;
        decValues['1'] = 53;
        decValues['2'] = 54;
        decValues['3'] = 55;
        decValues['4'] = 56;
        decValues['5'] = 57;
        decValues['6'] = 58;
        decValues['7'] = 59;
        decValues['8'] = 60;
        decValues['9'] = 61;

        decValues['+'] = 62;
        decValues['/'] = 63;
    }
}
