# forceLock
A cordova plugin to programatically lock an android phone

This is a fork of [XavierTalpe/android-lock-my-screen](https://github.com/XavierTalpe/android-lock-my-screen). This package wraps the code from that package into a cordova plugin.

# Installation

```bash
$ cordova plugin add cordova-plugin-force-lock
```

# Usage
```javascript
window.forceLock.lock(
  function () {
    // success
    console.log("initiated forceLock")
  },
  function (e) {
    // err
    console.log("faled to initiate forceLock\n\n", e)
  }
)
```

# TODO
1. Add callbacks
2. Move text to strings.xml