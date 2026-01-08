(function () {
  // Basic TV/remote helper.
  // Kotlin calls window.__tvBridge.key('ArrowUp') etc.
  function dispatchKey(key) {
    try {
      var evDown = new KeyboardEvent('keydown', { key: key, bubbles: true });
      var evUp = new KeyboardEvent('keyup', { key: key, bubbles: true });
      (document.activeElement || document.body || document).dispatchEvent(evDown);
      (document.activeElement || document.body || document).dispatchEvent(evUp);
      return true;
    } catch (e) {
      return false;
    }
  }

  function clickActive() {
    try {
      var el = document.activeElement;
      if (el && typeof el.click === 'function') {
        el.click();
        return true;
      }
      return false;
    } catch (e) {
      return false;
    }
  }

  window.__tvBridge = {
    key: dispatchKey,
    click: clickActive
  };
})();
