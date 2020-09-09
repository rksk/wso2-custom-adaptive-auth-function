# wso2-custom-adaptive-auth-function

This custom adaptive authentication function can be used to set forceAuth property from the adaptive scripts.

The binary can be built using maven and has to be copied into `<IS_HOME>/repository/components/dropins`.

Example usage.
```
function onLoginRequest(context) {
  setForceAuth(context, true);
  executeStep(1);
}
```
