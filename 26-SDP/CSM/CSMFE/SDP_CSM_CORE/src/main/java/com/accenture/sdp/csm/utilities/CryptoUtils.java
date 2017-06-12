package com.accenture.sdp.csm.utilities;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.accenture.sdp.csm.exceptions.EncryptionException;

public abstract class CryptoUtils {

	private static final String ENCRYPTION_KEY = "ADBSJ!JS1_547896";

	private static final String ALG = "AES";
	private static final String CHARSET = "UTF-8";

	public static String crypt(String dataToEncrypt) throws EncryptionException {
		String base64Data = null;
		try {
			byte[] key = ENCRYPTION_KEY.getBytes(CHARSET);

			Cipher c = Cipher.getInstance(ALG);
			SecretKeySpec k = new SecretKeySpec(key, ALG);
			c.init(Cipher.ENCRYPT_MODE, k);

			byte[] encryptedData = c.doFinal(dataToEncrypt.getBytes(CHARSET));
			base64Data = new String(Base64.encodeBase64(encryptedData, false, false, Constants.MAX_ENCRYPTED_PASSWORD_SIZE), CHARSET);
		} catch (Exception e) {
			throw new EncryptionException(String.format("Unable to encrypt input data. Exception: %s", e.getMessage()), e);
		}
		return base64Data;
	}

	public static String decrypt(String dataToDecrypt) throws EncryptionException {
		String result = null;
		try {
			byte[] key = ENCRYPTION_KEY.getBytes(CHARSET);

			Cipher c = Cipher.getInstance(ALG);
			SecretKeySpec k = new SecretKeySpec(key, ALG);
			c.init(Cipher.DECRYPT_MODE, k);

			byte[] decryptedData = c.doFinal(Base64.decodeBase64(dataToDecrypt.getBytes(CHARSET)));
			result = new String(decryptedData, CHARSET);
		} catch (Exception e) {
			throw new EncryptionException(String.format("Unable to decrypt input data. Exception: %s", e.getMessage()), e);
		}
		return result;
	}
}
