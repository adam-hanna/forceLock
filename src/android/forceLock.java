package com.adamhanna.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.Toast;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;

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
