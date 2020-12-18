# wso2-custom-adaptive-auth-function

This repository includes a set of sample custom adaptive authentication function implementations for [WSO2 Idenity Server](https://wso2.com/identity-and-access-management). The binary can be built using maven `mvn clean install` and has to be copied into `<IS_HOME>/repository/components/dropins`.


**setForceAuth()**

This custom adaptive authentication function can be used to set forceAuth property from the adaptive scripts.

Example usage.
```
function onLoginRequest(context) {
  setForceAuth(context, true);
  executeStep(1);
}
```


**getClaimsForUsername()**

This custom fuction can be used to retrive claims for a give username. We need to pass the username and the tenant domain in order to specify the user.

Example usage.
```
var userClaims = getClaimsForUsername(associatedUser.username, context.tenantDomain);
var country = userClaims["http://wso2.org/claims/country"];
```
