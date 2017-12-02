package com.rest.util.crypt;

import io.jsonwebtoken.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CryptoUtil
{
	private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();
	
	public static String encrypt(String seed, String cleartext) throws Exception
	{
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());
		return toHex(result);
	}
	
	public static String decrypt(String seed, String encrypted) throws Exception
	{
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	private static byte[] getRawKey(byte[] seed) throws Exception
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
	    kgen.init(128, sr); // 192 and 256 bits may not be available
	    SecretKey skey = kgen.generateKey();
	    byte[] raw = skey.getEncoded();
	    return raw;
	}

	
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception
	{
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception
	{
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt)
	{
		return toHex(txt.getBytes());
	}
	
	public static String fromHex(String hex)
	{
		return new String(toByte(hex));
	}
	
	public static byte[] toByte(String hexString)
	{
		int len = hexString.length()/2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf)
	{
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2*buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}
	
	private final static String HEX = "0123456789abcdef";
	
	private static void appendHex(StringBuffer sb, byte b)
	{
		sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
	}

	public static String getRandomKeySalt(String str)
	{
		String salt = null;
        SecureRandom random = new SecureRandom();
        int randomNumber = random.nextInt();
        long time = System.currentTimeMillis();

        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
;
        String keySalt = randomNumber+dayTime.format(new Date(time));

        salt = keySalt + str;
        return salt;
	}
	
	public static String toMd5(String target) {
        try {
            return toMd5(target, "US-ASCII");
        } catch (UnsupportedEncodingException uee) {
            // Unlikely, US-ASCII comes with every JVM
            throw new RuntimeException(
                    "US-ASCII is an unsupported encoding, unable to compute MD5");
        }
    }
	
	public static String toMd5(String target, String charsetName)
            throws UnsupportedEncodingException
    {
        try {
            final byte[] md5 = MessageDigest.getInstance("MD5").digest(
                    target.getBytes(charsetName));
            final char[] md5Chars = new char[32];
            int i = 0;
            for (final byte b : md5) {
                md5Chars[i++] = HEXDIGITS[(b >> 4) & 0xF];
                md5Chars[i++] = HEXDIGITS[b & 0xF];
            }
            return new String(md5Chars);
        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException(
                    "No MD5 algorithm, unable to compute MD5");
        }
    }
	
	public static String getHmacMD5(String privateKey, String input) throws Exception
	{
		String algorithm = "HmacMD5";
		byte[] keyBytes = privateKey.getBytes();
		Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm);
		Mac mac = Mac.getInstance(algorithm);
		mac.init(key); 
		return byteArrayToHex(mac.doFinal(input.getBytes()));
	}
	
	protected static String byteArrayToHex(byte [] a) {
		int hn, ln, cx;
		String hexDigitChars = "0123456789abcdef";
		StringBuffer buf = new StringBuffer(a.length * 2);
		for(cx = 0; cx < a.length; cx++) {
			hn = ((int)(a[cx]) & 0x00ff) / 16;
			ln = ((int)(a[cx]) & 0x000f);
			buf.append(hexDigitChars.charAt(hn));
			buf.append(hexDigitChars.charAt(ln));
		}
		return buf.toString();
	}


	public static String createJWT(String userId, String subject, String key, long ttlMillis)
	{
		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(userId)
						.setIssuedAt(now)
						.setSubject(subject)
						.signWith(signatureAlgorithm, signingKey);

		//if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	public static String parseJWT(String jwt, String key)
	{
		String subject = null;
		//This line will throw an exception if it is not a signed JWS (as expected)
		try
		{
			Claims claims = Jwts.parser()
							.setSigningKey(DatatypeConverter.parseBase64Binary(key))
							.parseClaimsJws(jwt).getBody();

			subject = claims.getSubject();
			/*System.out.println("ID: " + claims.getId());
			System.out.println("Subject: " + claims.getSubject());
			System.out.println("Issuer: " + claims.getIssuer());
			System.out.println("Expiration: " + claims.getExpiration());*/

		}
		catch (ExpiredJwtException eje)
		{
			throw eje;
		}
		catch (MalformedJwtException me)
		{
			throw me;
		}
		catch (Exception e)
		{
			throw e;
		}

		return subject;
	}

	public static void main(String[] args) throws Exception
    {
	    String str = "rudaks";
        //String result = CryptoUtil.encrypt("kmhan", "ABCD");
       // System.err.println(result);
        
        //System.err.println(CryptoUtil.decrypt("kmhan.20160218123542", result));

		//System.err.println(CryptoUtil.encryptToNode("rudaks"));

		String json = "{\"userId\":\"kmhan\", \"password\":\"kmhan\"}";
		String result = CryptoUtil.createJWT("kmhan", json, "B57666B99EEAE1B41981735D4A71598E", 1000*60*60);
		System.err.println("encode:"+result);
		result = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJrbWhhbiIsInBhc3N3b3JkIjoia21oYW4iLCJleHAiOjk5OTk5OTk5OTk5OX0.kGnuOpKHHop-nyZ4n91rzvXQyhcE8rsuNEpuslF_b0c";
		System.err.println("decode:"+CryptoUtil.parseJWT(result, "B57666B99EEAE1B41981735D4A71598E"));
    }


}