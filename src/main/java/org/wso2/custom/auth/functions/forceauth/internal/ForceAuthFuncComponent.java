package org.wso2.custom.auth.functions.forceauth.internal;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.application.authentication.framework.JsFunctionRegistry;
import org.wso2.custom.auth.functions.forceauth.SetForceAuthFunction;
import org.wso2.custom.auth.functions.forceauth.SetForceAuthFunctionImpl;

@Component(
        name = "custom.auth.functions.forceauth.component",
        immediate = true
)
public class ForceAuthFuncComponent {

    private static JsFunctionRegistry jsFunctionRegistry;

    @Reference(
            service = JsFunctionRegistry.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetJsFunctionRegistry"
    )
    public void setJsFunctionRegistry(JsFunctionRegistry jsFunctionRegistry) {
        this.jsFunctionRegistry = jsFunctionRegistry;
    }

    public void unsetJsFunctionRegistry(JsFunctionRegistry jsFunctionRegistry) {
        this.jsFunctionRegistry = null;
    }

    @Activate
    protected void activate(ComponentContext ctxt) {

        SetForceAuthFunction setForceAuthFunctionImpl = new SetForceAuthFunctionImpl();
        jsFunctionRegistry.register(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "setForceAuth",
                setForceAuthFunctionImpl);
    }

    @Deactivate
    protected void deactivate(ComponentContext ctxt) {

        if (jsFunctionRegistry != null) {
            jsFunctionRegistry.deRegister(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "setForceAuth");
        }
    }

}
