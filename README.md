# wso2-custom-adaptive-auth-function

This repository includes a set of sample custom adaptive authentication function implementations for [WSO2 Idenity Server](https://wso2.com/identity-and-access-management). More information on writing custom functions can be found in [this document](https://is.docs.wso2.com/en/latest/develop/writing-custom-functions-for-adaptive-authentication/).

*Steps to deploy*
- Build the project using maven `mvn clean install`
- Copy the binary file from `target` directory into into `<IS_HOME>/repository/components/dropins` directory
- Restart WSO2 IS

**generateHash()**

This function helps to generate hash values for a give string with any MessageDigest Algorithm supported by your JVM (MD5, SHA-1, SHA-256, SHA-512).

Example usage.
```
var input = "abc";
var digestFunction = "SHA-256"
var output = generateHash(input, digestFunction);
```

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

This custom function can be used to retrieve claims for a give username. We need to pass the username and the tenant domain in order to specify the user.

Example usage.
```
var userClaims = getClaimsForUsername(associatedUser.username, context.tenantDomain);
var country = userClaims["http://wso2.org/claims/country"];
```
