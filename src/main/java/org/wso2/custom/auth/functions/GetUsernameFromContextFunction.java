package org.wso2.custom.auth.functions;

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;

@FunctionalInterface
public interface GetUsernameFromContextFunction {

    String getUsernameFromContext(JsAuthenticationContext context, int step);
}
