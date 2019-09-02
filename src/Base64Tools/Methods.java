package Base64Tools;

import java.io.IOException;
import static AssertTools.Assert.*;

abstract class Methods {

    static String encodeBytes(byte[] bytes) {

        int len = bytes.length;

        final int outLen = (len/3 + (len%3 == 0 ? 0 : 1)) * 4;

        byte[] outputBytes = new byte[outLen];

        int i = 0;
        int j = 0;

        int[] chs = new int[4];

        while(len - i > 2) {

            chs[0] = bytes[i] >> 2;
            chs[1] = (bytes[i] << 4) + (bytes[i+1] >> 4);
            chs[2] = (bytes[i+1] << 2) + (bytes[i +2] >> 6);
            chs[3] = bytes[i+2];

            outputBytes[j] = valueToChar(chs[0]);
            outputBytes[j+1] = valueToChar(chs[1]);
            outputBytes[j+2] = valueToChar(chs[2]);
            outputBytes[j+3] = valueToChar(chs[3]);

            i+=3;
            j+=4;
        }

        switch (len - i) {
            case 0:
                break;
            case 1:
                chs[0] = (bytes[i] >> 2);
                chs[1] = (bytes[i] << 4);
                outputBytes[j] = valueToChar(chs[0]);
                outputBytes[j+1] = valueToChar(chs[1]);
                outputBytes[j+2] = valueToChar(64);
                outputBytes[j+3] = valueToChar(64);
                break;
            case 2:
                chs[0] = (bytes[i] >> 2);
                chs[1] = ((bytes[i] << 4) + (bytes[i+1] >> 4));
                chs[2] = (bytes[i+1] << 2);

                outputBytes[j] = valueToChar(chs[0]);
                outputBytes[j+1] = valueToChar(chs[1]);
                outputBytes[j+2] = valueToChar(chs[2]);
                outputBytes[j+3] = valueToChar(64);
                break;
        }
        return new String(outputBytes);
    }

    static String encodeBytes_UrlEncoding(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        int len = bytes.length;

        int i = 0;

        int[] chs = new int[4];

        while(len - i > 2) {

            chs[0] = (bytes[i] >> 2) & 0b00111111;
            chs[1] = ((bytes[i] << 4) + (bytes[i+1] >> 4)) & 0b00111111;
            chs[2] = ((bytes[i+1] << 2) + (bytes[i +2] >> 6)) & 0b00111111;
            chs[3] = bytes[i+2] & 0b00111111;

            sb.append(Values.encoderDictionaryURL[chs[0]]);
            sb.append(Values.encoderDictionaryURL[chs[1]]);
            sb.append(Values.encoderDictionaryURL[chs[2]]);
            sb.append(Values.encoderDictionaryURL[chs[3]]);

            i+=3;
        }

        switch (len - i) {
            case 0:
                break;
            case 1:
                chs[0] = (bytes[i] >> 2) & 0b00111111;
                chs[1] = (bytes[i] << 4) & 0b00111111;
                sb.append(Values.encoderDictionaryURL[chs[0]]);
                sb.append(Values.encoderDictionaryURL[chs[1]]);
                sb.append(Values.encoderDictionaryURL[64]);
                sb.append(Values.encoderDictionaryURL[64]);

                break;
            case 2:
                chs[0] = (bytes[i] >> 2) & 0b00111111;
                chs[1] = ((bytes[i] << 4) + (bytes[i+1] >> 4)) & 0b00111111;
                chs[2] = (bytes[i+1] << 2) & 0b00111111;
                sb.append(Values.encoderDictionaryURL[chs[0]]);
                sb.append(Values.encoderDictionaryURL[chs[1]]);
                sb.append(Values.encoderDictionaryURL[chs[2]]);
                sb.append(Values.encoderDictionaryURL[64]);

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
                        sb.append((char) Values.encoderDictionary[62]);
                        k+=2;
                        break;
                    case "2F":
                        sb.append((char) Values.encoderDictionary[63]);
                        k+=2;
                        break;
                    case "3D":
                        sb.append((char) Values.encoderDictionary[64]);
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

    private static byte valueToChar(int value) {

        return Values.encoderDictionary[value & 0b00111111];
    }

    private static byte charToValue(int c) throws IOException {

        try {
            byte val = Values.decoderDictionary[c];
            _assert(val != -1);
            return val;
        }
        catch (Exception e) {
            throw new IOException("Input string is not a valid Base64 string");
        }
    }
}