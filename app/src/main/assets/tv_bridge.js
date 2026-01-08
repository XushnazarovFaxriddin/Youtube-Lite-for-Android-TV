(function () {
  'use strict';

  // TV Bridge - Communication layer between Android and YouTube TV
  // Handles remote control key events and navigation

  /**
   * Dispatch a keyboard event to the active element or document
   * @param {string} key - The key to dispatch (e.g., 'Escape', 'ArrowUp', 'Enter')
   * @returns {boolean} - Whether the event was dispatched successfully
   */
  function dispatchKey(key) {
    try {
      var target = document.activeElement || document.body || document;

      // Create and dispatch keydown event
      var evDown = new KeyboardEvent('keydown', {
        key: key,
        code: getKeyCode(key),
        keyCode: getKeyCodeNumber(key),
        which: getKeyCodeNumber(key),
        bubbles: true,
        cancelable: true
      });

      // Create and dispatch keyup event
      var evUp = new KeyboardEvent('keyup', {
        key: key,
        code: getKeyCode(key),
        keyCode: getKeyCodeNumber(key),
        which: getKeyCodeNumber(key),
        bubbles: true,
        cancelable: true
      });

      target.dispatchEvent(evDown);
      target.dispatchEvent(evUp);

      return true;
    } catch (e) {
      console.error('[TV Bridge] Error dispatching key:', e);
      return false;
    }
  }

  /**
   * Get the code property for a key
   */
  function getKeyCode(key) {
    var codes = {
      'Escape': 'Escape',
      'Enter': 'Enter',
      'ArrowUp': 'ArrowUp',
      'ArrowDown': 'ArrowDown',
      'ArrowLeft': 'ArrowLeft',
      'ArrowRight': 'ArrowRight',
      'Backspace': 'Backspace',
      ' ': 'Space'
    };
    return codes[key] || key;
  }

  /**
   * Get the keyCode number for a key (legacy support)
   */
  function getKeyCodeNumber(key) {
    var keyCodes = {
      'Escape': 27,
      'Enter': 13,
      'ArrowUp': 38,
      'ArrowDown': 40,
      'ArrowLeft': 37,
      'ArrowRight': 39,
      'Backspace': 8,
      ' ': 32
    };
    return keyCodes[key] || 0;
  }

  /**
   * Click the currently focused element
   * @returns {boolean} - Whether the click was successful
   */
  function clickActive() {
    try {
      var el = document.activeElement;
      if (el && typeof el.click === 'function') {
        el.click();
        return true;
      }
      return false;
    } catch (e) {
      console.error('[TV Bridge] Error clicking active element:', e);
      return false;
    }
  }

  /**
   * Check if a video is currently playing
   * @returns {boolean}
   */
  function isVideoPlaying() {
    try {
      var video = document.querySelector('video');
      return video && !video.paused && !video.ended && video.readyState > 2;
    } catch (e) {
      return false;
    }
  }

  /**
   * Check if we're on the home/browse page
   * @returns {boolean}
   */
  function isOnHomePage() {
    try {
      var path = window.location.pathname + window.location.hash;
      return path === '/tv' || path === '/tv#' || path === '/tv#/' ||
             path === '/tv/' || path === '/tv/#' || path === '/tv/#/';
    } catch (e) {
      return false;
    }
  }

  /**
   * Check if any overlay/modal is open
   * @returns {boolean}
   */
  function isOverlayOpen() {
    try {
      // Check for common YouTube TV overlay elements
      var overlays = document.querySelectorAll(
        '[class*="overlay"]:not([hidden]), ' +
        '[class*="modal"]:not([hidden]), ' +
        '[class*="dialog"]:not([hidden]), ' +
        '[role="dialog"]:not([hidden])'
      );

      for (var i = 0; i < overlays.length; i++) {
        var style = window.getComputedStyle(overlays[i]);
        if (style.display !== 'none' && style.visibility !== 'hidden') {
          return true;
        }
      }
      return false;
    } catch (e) {
      return false;
    }
  }

  /**
   * Get current navigation state
   * @returns {object} - Navigation state info
   */
  function getNavigationState() {
    return {
      isVideoPlaying: isVideoPlaying(),
      isOnHomePage: isOnHomePage(),
      isOverlayOpen: isOverlayOpen(),
      currentUrl: window.location.href
    };
  }

  // Expose functions to global scope for Android bridge
  window.__tvBridge = {
    key: dispatchKey,
    click: clickActive,
    isVideoPlaying: isVideoPlaying,
    isOnHomePage: isOnHomePage,
    isOverlayOpen: isOverlayOpen,
    getNavigationState: getNavigationState
  };

  console.log('[TV Bridge] Initialized successfully');
})();
