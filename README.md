# forceLock
NOT WORKING. When included in a project, I get the error, below. When I try to add an activity to my AndroidManifest.xml, I get a "permissions not granted" error and the phone forcequits the app (on startup). 

```javascript
Unable to find explicit activity class {io.cordova.hellocordova/com.adamhanna.plugin.LockMyScreen}; have you declared this activity in your AndroidManifest.xml?
```

Here are some of the errors that happen on startup when I try to add an activity to my AndroidManifest.xml:

```bash
08-28 21:09:29.469 16255 16255 I         : SS_ART_lib [I]: permission is absent: /data/app/io.cordova.hellocordova-2/base.apk
08-28 21:09:29.469 16255 16255 I         : SS_ART_lib [I]: access to SS denied
08-28 21:09:29.469  1070  1495 E SecureStorageService: Process failed status=0x0200
08-28 21:09:29.469  1070  1495 W PackageManager: Can't sign the dex file
```

```bash
08-28 22:27:55.668  1070  1495 D AASAinstall: there is not AASApackages.xml file
08-28 22:27:55.668  1070  1495 W PackageManager: Not granting permission android.permission.ACCESS_MOCK_LOCATION to package io.cordova.hellocordova (protectionLevel=2 flags=0x3808be46)
08-28 22:27:55.668  1070  1495 W PackageManager: Not granting permission android.permission.BIND_DEVICE_ADMIN to package io.cordova.hellocordova (protectionLevel=2 flags=0x3808be46)
08-28 22:27:55.668  1070  1263 I ActivityManager:   Force finishing activity ActivityRecord{eb4b940 u0 io.cordova.hellocordova/.MainActivity t7250}
08-28 22:27:55.668  1070  1263 I ActivityManager:   Force stopping service ServiceRecord{7ef5ca4 u0 
```

A cordova plugin to programatically lock an android phone

This is a fork of [XavierTalpe/android-lock-my-screen](https://github.com/XavierTalpe/android-lock-my-screen). This package wraps the code from that package into a cordova plugin.

# Installation

```bash
$ cordova plugin add cordova-plugin-force-lock
```

# Usage
```javascript
window.forceLock.lock()
```

# TODO
1. Add callbacks
2. Move text to strings.xml