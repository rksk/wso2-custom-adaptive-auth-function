package org.wso2.custom.auth.functions.forceauth.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.application.authentication.framework.JsFunctionRegistry;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.custom.auth.functions.forceauth.GetClaimsForUsernameFunction;
import org.wso2.custom.auth.functions.forceauth.GetClaimsForUsernameFunctionImpl;
import org.wso2.custom.auth.functions.forceauth.GetUsernameFromContextFunction;
import org.wso2.custom.auth.functions.forceauth.GetUsernameFromContextFunctionImpl;
import org.wso2.custom.auth.functions.forceauth.SetForceAuthFunction;
import org.wso2.custom.auth.functions.forceauth.SetForceAuthFunctionImpl;

@Component(
        name = "custom.auth.functions.forceauth.component",
        immediate = true
)
public class CustomAuthFuncComponent {

    private static final Log LOG = LogFactory.getLog(CustomAuthFuncComponent.class);
    private static JsFunctionRegistry jsFunctionRegistry;

    @Activate
    protected void activate(ComponentContext ctxt) {

        SetForceAuthFunction setForceAuthFunctionImpl = new SetForceAuthFunctionImpl();
        jsFunctionRegistry.register(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "setForceAuth",
                setForceAuthFunctionImpl);

        GetUsernameFromContextFunction getUsernameFromContextFunctionImpl = new GetUsernameFromContextFunctionImpl();
        jsFunctionRegistry.register(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "getUsernameFromContext",
                getUsernameFromContextFunctionImpl);

        GetClaimsForUsernameFunction getClaimsForUsernameFunctionImpl = new GetClaimsForUsernameFunctionImpl();
        jsFunctionRegistry.register(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "getClaimsForUsername",
                getClaimsForUsernameFunctionImpl);
    }

    @Deactivate
    protected void deactivate(ComponentContext ctxt) {

        if (jsFunctionRegistry != null) {
            jsFunctionRegistry.deRegister(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "setForceAuth");
            jsFunctionRegistry.deRegister(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "getUsernameFromContext");
            jsFunctionRegistry.deRegister(JsFunctionRegistry.Subsystem.SEQUENCE_HANDLER, "getClaimsForUsername");
        }
    }

    @Reference(
            name = "user.realmservice.default",
            service = RealmService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRealmService"
    )
    protected void setRealmService(RealmService realmService) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("RealmService is set in the custom conditional authentication user functions bundle");
        }
        CustomAuthFuncHolder.getInstance().setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("RealmService is unset in the custom conditional authentication user functions bundle");
        }
        CustomAuthFuncHolder.getInstance().setRealmService(null);
    }

    @Reference(
            name = "registry.service",
            service = RegistryService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRegistryService"
    )
    protected void setRegistryService(RegistryService registryService) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("RegistryService is set in the custom conditional authentication user functions bundle");
        }
        CustomAuthFuncHolder.getInstance().setRegistryService(registryService);
    }

    protected void unsetRegistryService(RegistryService registryService) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("RegistryService is unset in the custom conditional authentication user functions bundle");
        }
        CustomAuthFuncHolder.getInstance().setRegistryService(null);
    }

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
}
