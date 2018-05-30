import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class BlowfishPasswordManager {

	private static byte[] kbytes = "jaas is the way".getBytes();
	private static SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
	private static final String BLOWFISH = "Blowfish";

	public static void main(String[] args) throws GeneralSecurityException {

		validateArguments(args);

		if (args[0].equals("e")) {
			System.out.println("plainText is:" + args[1]);
			System.out.println(encrypt(args[1]));
		} else if (args[0].equals("d")) {
			System.out.println("encryptedText is:" + args[1]);
			System.out.println(decrypt(args[1]));
		}
	}

	private static String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance(BLOWFISH);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encoding = cipher.doFinal(plainText.getBytes());
		BigInteger n = new BigInteger(encoding);
		return n.toString(16);
	}

	public static String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		BigInteger n = new BigInteger(encryptedText, 16);
		byte[] encoding = n.toByteArray();
		Cipher cipher = Cipher.getInstance(BLOWFISH);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decode = cipher.doFinal(encoding);
		return new String(decode);
	}

	private static void validateArguments(String[] args) {

		if (args == null || args.length != 2) {
			fail();
		}

		if ((args[0].equals("e") || args[0].equals("d")) == false) {
			fail();
		}

		for (int i = 0; i < 2; ++i) {
			if (args[i].equals(null)) {
				fail();
			}
		}
	}

	private static void fail() {
		System.err.println("Sample usages:");
		System.err.println("For Encryption: BlowfishPasswordManager e myPlainTextPlease");
		System.err.println("For Decryption: BlowfishPasswordManager d encryptedText");
		System.exit(-1);

	}
}
