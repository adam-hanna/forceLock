package com.adamhanna.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.content.Context;
import android.content.Intent;

/**
 * This class echoes a string called from JavaScript.
 */
public class forceLock extends CordovaPlugin {
  boolean myReturn;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("lock")) {
      myReturn = true;
      final CallbackContext myCallbackContext = callbackContext;

      cordova.getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
            try {
              Context context = cordova.getActivity().getApplicationContext();
              Intent intent = new Intent(context, LockMyScreen.class);
              cordova.getActivity().startActivity(intent);
              myCallbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
            }
            catch (Exception e) {
              Log.e("forceLockPlugin", "Error occurred calling plugin: " + e.toString());
              myCallbackContext.error(e.getMessage());
              myReturn = false;
            }
        }
      });

      return myReturn;
    }
    return false;
  }
}
