package system.backend.services;

import system.backend.WSystem;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptoService {
    private final String UNICODE_FORMAT = "UTF-8";
    private SecretKey key;
    private Cipher cipher;
    private static CryptoService cryptoService;

    public static CryptoService getInstance(){
        if(cryptoService == null)
            cryptoService = new CryptoService();
        return cryptoService;
    }

    public CryptoService(){
        try {
            key = generateKey("AES");
            cipher = Cipher.getInstance("AES");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public SecretKey generateKey(String encryptionType){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptionType);
            SecretKey key = keyGenerator.generateKey();
            return key;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String encrypt(String data, SecretKey key, Cipher cipher){
        try{
            byte[] text = data.getBytes(UNICODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(text);

            return toHex(encrypted);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String toHex(byte[] encrypted){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < encrypted.length; i++) {
            String hex = Integer.toHexString(encrypted[i] & 0xFF);
            if (hex.length() == 1)
                hex = '0' + hex;
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public Cipher getCipher() {
        return cipher;
    }

    public String getUNICODE_FORMAT() {
        return UNICODE_FORMAT;
    }

    public SecretKey getKey() {
        return key;
    }
}
