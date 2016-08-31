package com.adamhanna.plugin;

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

public class LockMyScreen extends Activity {
  private static final int MAX_RETRY_COUNT = 4;
  private static final int RETRY_DELAY = 100;

  private static final int REQUEST_CODE_ENABLE_ADMIN = 2;

  @Override
  protected void onCreate( Bundle aSavedInstanceState ) {
    super.onCreate( aSavedInstanceState );

    ComponentName adminComponent = new ComponentName( LockMyScreen.this, PermissionReceiver.class );
    DevicePolicyManager policyManager = ( DevicePolicyManager ) getSystemService( Context.DEVICE_POLICY_SERVICE );

    if ( policyManager.isAdminActive( adminComponent ) ) {
      lockScreen( policyManager );
    }
    else {
      requestPermission( adminComponent );
    }
  }

  @Override
  protected void onActivityResult( int aRequestCode, int aResultCode, Intent aData ) {
    super.onActivityResult( aRequestCode, aResultCode, aData );

    if ( aResultCode == RESULT_OK ) {
      DevicePolicyManager policyManager = ( DevicePolicyManager ) getSystemService( Context.DEVICE_POLICY_SERVICE );
      lockScreen( policyManager );
    }
    else {
      askUserToRetry();
    }
  }

  private void lockScreen( final DevicePolicyManager aPolicyManager ) {
    final PowerManager powerManager = ( PowerManager ) getSystemService( Context.POWER_SERVICE );
    final Handler handler = new Handler( getMainLooper() );
    final int[] retryCount = new int[]{ 0 };

    handler.post( new Runnable() {
      @Override
      public void run() {
        if ( powerManager.isScreenOn() && retryCount[ 0 ] <= MAX_RETRY_COUNT ) {
          aPolicyManager.lockNow();
          retryCount[ 0 ]++;
          handler.postDelayed( this, RETRY_DELAY * retryCount[ 0 ] );
        }
        else {
          finish();
        }
      }
    } );
  }

  private void requestPermission( ComponentName aAdminComponent ) {
    String explanation = "Remember that you can only uninstall this app after you disabled the device administrator permission.";

    Intent intent = new Intent( DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN );
    intent.putExtra( DevicePolicyManager.EXTRA_DEVICE_ADMIN, aAdminComponent );
    intent.putExtra( DevicePolicyManager.EXTRA_ADD_EXPLANATION, explanation );

    startActivityForResult( intent, REQUEST_CODE_ENABLE_ADMIN );
  }

  private void askUserToRetry() {
    AlertDialog.Builder builder;
    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {
      builder = new AlertDialog.Builder( this, AlertDialog.THEME_DEVICE_DEFAULT_DARK );
    }
    else {
      builder = new AlertDialog.Builder( this );
    }

    builder.setTitle( "This app requires permissions" );
    builder.setMessage( "Please allow this app to lock your phone, programmatically." );
    builder.setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick( DialogInterface aDialog, int aButton ) {
        ComponentName adminComponent = new ComponentName( LockMyScreen.this, PermissionReceiver.class );
        requestPermission( adminComponent );
      }
    } );

    builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
      @Override
      public void onClick( DialogInterface aDialog, int aButton ) {
        aDialog.dismiss();
        finish();
      }
    } );

    builder.create().show();
  }

  public static final class PermissionReceiver extends DeviceAdminReceiver {

    @Override
    public void onDisabled( Context aContext, Intent aIntent ) {
      Toast.makeText( aContext, "The application can now be uninstalled.", Toast.LENGTH_LONG ).show();
    }
  }
}