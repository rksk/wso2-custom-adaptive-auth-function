package org.wso2.custom.auth.functions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.StepConfig;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;

import java.util.Map;

public class GetUsernameFromContextFunctionImpl implements GetUsernameFromContextFunction {

    private static final Log LOG = LogFactory.getLog(GetUsernameFromContextFunctionImpl.class);

    @Override
    public String getUsernameFromContext(JsAuthenticationContext context, int step) {

        String username = null;
        Map<Integer, StepConfig> stepMap = context.getWrapped().getSequenceConfig().getStepMap();
        if (stepMap == null) {
            LOG.error("StepMap is null in the SequenceConfig.");
        } else if (stepMap.get(step) == null) {
            LOG.error("Step " + step + " is not found in the StepMap.");
        } else if (stepMap.get(step).getAuthenticatedUser() == null) {
            LOG.error("AuthenticatedUser is null in the step " + step + ".");
        } else {
            username = stepMap.get(step).getAuthenticatedUser().getUserName();
        }

        return username;
    }
}
