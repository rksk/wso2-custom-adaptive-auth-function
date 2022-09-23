package org.wso2.custom.auth.functions;

@FunctionalInterface
public interface GenerateHashFunction {

    String generateHash(String input, String digestFunction);
}
