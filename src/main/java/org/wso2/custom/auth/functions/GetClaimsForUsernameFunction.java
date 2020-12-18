package org.wso2.custom.auth.functions;

import org.wso2.carbon.identity.application.authentication.framework.exception.FrameworkException;

import java.util.Map;

@FunctionalInterface
public interface GetClaimsForUsernameFunction {

    public Map getClaimsForUsername(String username, Object... parameters) throws FrameworkException;
}
