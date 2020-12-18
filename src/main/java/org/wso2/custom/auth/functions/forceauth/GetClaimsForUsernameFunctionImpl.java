package org.wso2.custom.auth.functions.forceauth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.exception.FrameworkException;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.claim.Claim;
import org.wso2.custom.auth.functions.forceauth.internal.CustomAuthFuncHolder;

import java.util.HashMap;
import java.util.Map;

public class GetClaimsForUsernameFunctionImpl implements GetClaimsForUsernameFunction {

    private static final Log LOG = LogFactory.getLog(GetClaimsForUsernameFunctionImpl.class);

    @Override
    public Map getClaimsForUsername(String username, Object... parameters)
            throws FrameworkException {

        String tenantDomain = null;
        String profile = null;
        if (username == null || parameters == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Passed parameter to getClaimsForUsername method has null values");
            }
            return null;
        }
        if (parameters.length == 2) {
            if (parameters[0] instanceof String) {
                tenantDomain = (String) parameters[0];
            }
            if (parameters[1] instanceof String) {
                profile = (String) parameters[1];
            }
        }
        if (parameters.length == 1 && parameters[0] instanceof String) {
            tenantDomain = (String) parameters[0];
        }

        if (tenantDomain != null) {
            int tenantId = IdentityTenantUtil.getTenantId(tenantDomain);
            try {
                UserRealm userRealm = CustomAuthFuncHolder.getInstance().getRealmService()
                        .getTenantUserRealm(tenantId);
                if (userRealm != null) {
                    UserStoreManager userStoreManager = (UserStoreManager) userRealm.getUserStoreManager();

                    // Get the user list using the uername
                    String[] userList = userStoreManager.listUsers(username, 1);
                    if (userList == null || userList.length == 0) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("No users found with the given username: " + username);
                        }
                        return null;
                    }
                    String selectedUsername = userList[0];
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Found user: " + selectedUsername);
                    }

                    Claim[] claims = userStoreManager.getUserClaimValues(selectedUsername, profile);
                    Map returningClaims = new HashMap();
                    if (claims != null) {
                        for (Claim claim : claims) {
                            returningClaims.put(claim.getClaimUri(), claim.getValue());
                        }
                    }
                    return returningClaims;
                } else {
                    LOG.error("Cannot find the user realm for the given tenant: " + tenantId);
                }
            } catch (UserStoreException e) {
                String msg = "getClaimsForUsername Function failed while getting user";
                if (LOG.isDebugEnabled()) {
                    LOG.debug(msg, e);
                }
                throw new FrameworkException(msg, e);
            }
        }
        return null;
    }
}
