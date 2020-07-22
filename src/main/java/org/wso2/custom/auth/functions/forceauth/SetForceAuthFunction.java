package org.wso2.custom.auth.functions.forceauth;

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;

@FunctionalInterface
public interface SetForceAuthFunction {

    void setForceAuth(JsAuthenticationContext context, boolean reAuthenticate);
}
