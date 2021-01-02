package cn.net.mayh.user.security;

import java.security.MessageDigest;

/**
 * by rock yang, 2001-9
 */
public class OneWayHash {

  // default algorithm is MD5
  public static String encrypt(String message) throws Exception {
      return encrypt(message, "MD5");
  }

  // algorithm: MD5 or SHA-1
  // return string length: 32 if algorithm = MD5, or 40 if algorithm = SHA-1
  public static String encrypt(String message, String algorithm) throws Exception {
      if (message == null) {
          throw new Exception("message is null.");
      }
      if (!"MD5".equals(algorithm) && !"SHA-1".equals(algorithm)) {
          throw new Exception("algorithm must be MD5 or SHA-1.");
      }
      byte[] buffer = message.getBytes();

      // The SHA algorithm results in a 20-byte digest, while MD5 is 16 bytes long.
      MessageDigest md = MessageDigest.getInstance(algorithm);

      // Ensure the digest's buffer is empty. This isn't necessary the first time used.
      // However, it is good practice to always empty the buffer out in case you later reuse it.
      md.reset();

      // Fill the digest's buffer with data to compute a message digest from.
      md.update(buffer);

      // Generate the digest. This does any necessary padding required by the algorithm.
      byte[] digest = md.digest();

      // Save or print digest bytes. Integer.toHexString() doesn't print leading zeros.
      StringBuffer hexString = new StringBuffer();
      String sHexBit = null;
      for (int i=0; i<digest.length; i++) {
        sHexBit = Integer.toHexString(0xFF & digest[i]);
        if (sHexBit.length() == 1) {
            sHexBit = "0" + sHexBit;
        }
        hexString.append(sHexBit);
      }
      return hexString.toString();
  }

}
