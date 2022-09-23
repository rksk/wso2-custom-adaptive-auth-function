package org.wso2.custom.auth.functions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class GenerateHashFunctionImpl implements GenerateHashFunction {

    private static final Log LOG = LogFactory.getLog(GenerateHashFunctionImpl.class);

    @Override
    public String generateHash(String input, String digestFunction) {

        try {
            if (input != null) {
                MessageDigest digest = MessageDigest.getInstance(digestFunction);
                byte[] data = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                return convertByteArrayToHexString(data);
            }
        } catch (Exception e) {
            LOG.error("Error occurred while generating hash with digestFunction: " + digestFunction, e);
        }
        return null;
    }

    private String convertByteArrayToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
