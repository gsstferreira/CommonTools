package Base64Tools;

abstract class Values {

    final static byte[] encoderDictionary = new byte[] {
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
            '+','/','='
    };
    final static CharSequence[] encoderDictionaryURL = new String[] {
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "0","1","2","3","4","5","6","7","8","9",
            "%2B","%2F","%3D"
    };
    final static byte[] decoderDictionary = new byte[128];

    static {

        for(int i = 0; i < 128; i++) {
            decoderDictionary[i] = -1;
        }

        decoderDictionary['A'] = 0;
        decoderDictionary['B'] = 1;
        decoderDictionary['C'] = 2;
        decoderDictionary['D'] = 3;
        decoderDictionary['E'] = 4;
        decoderDictionary['F'] = 5;
        decoderDictionary['G'] = 6;
        decoderDictionary['H'] = 7;
        decoderDictionary['I'] = 8;
        decoderDictionary['J'] = 9;
        decoderDictionary['K'] = 10;
        decoderDictionary['L'] = 11;
        decoderDictionary['M'] = 12;
        decoderDictionary['N'] = 13;
        decoderDictionary['O'] = 14;
        decoderDictionary['P'] = 15;
        decoderDictionary['Q'] = 16;
        decoderDictionary['R'] = 17;
        decoderDictionary['S'] = 18;
        decoderDictionary['T'] = 19;
        decoderDictionary['U'] = 20;
        decoderDictionary['V'] = 21;
        decoderDictionary['W'] = 22;
        decoderDictionary['X'] = 23;
        decoderDictionary['Y'] = 24;
        decoderDictionary['Z'] = 25;

        decoderDictionary['a'] = 26;
        decoderDictionary['b'] = 27;
        decoderDictionary['c'] = 28;
        decoderDictionary['d'] = 29;
        decoderDictionary['e'] = 30;
        decoderDictionary['f'] = 31;
        decoderDictionary['g'] = 32;
        decoderDictionary['h'] = 33;
        decoderDictionary['i'] = 34;
        decoderDictionary['j'] = 35;
        decoderDictionary['k'] = 36;
        decoderDictionary['l'] = 37;
        decoderDictionary['m'] = 38;
        decoderDictionary['n'] = 39;
        decoderDictionary['o'] = 40;
        decoderDictionary['p'] = 41;
        decoderDictionary['q'] = 42;
        decoderDictionary['r'] = 43;
        decoderDictionary['s'] = 44;
        decoderDictionary['t'] = 45;
        decoderDictionary['u'] = 46;
        decoderDictionary['v'] = 47;
        decoderDictionary['w'] = 48;
        decoderDictionary['x'] = 49;
        decoderDictionary['y'] = 50;
        decoderDictionary['z'] = 51;

        decoderDictionary['0'] = 52;
        decoderDictionary['1'] = 53;
        decoderDictionary['2'] = 54;
        decoderDictionary['3'] = 55;
        decoderDictionary['4'] = 56;
        decoderDictionary['5'] = 57;
        decoderDictionary['6'] = 58;
        decoderDictionary['7'] = 59;
        decoderDictionary['8'] = 60;
        decoderDictionary['9'] = 61;

        decoderDictionary['+'] = 62;
        decoderDictionary['/'] = 63;
    }
}
