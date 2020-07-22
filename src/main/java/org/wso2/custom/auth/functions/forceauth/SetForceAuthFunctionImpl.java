package org.wso2.custom.auth.functions.forceauth;

import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;

public class SetForceAuthFunctionImpl implements SetForceAuthFunction {

    @Override
    public void setForceAuth(JsAuthenticationContext context, boolean forceAuth) {

        context.getWrapped().setForceAuthenticate(forceAuth);
    }
}
