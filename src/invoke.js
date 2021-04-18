/* eslint-disable */
import React from './react';

function call(target, method, ...args) {
  if (target instanceof Object && target._nativeTag) {
    target = React.view(target);
  };

  return function() {
    if (typeof target === 'function') {
      target = {
        type: 'Invocation',
        value: target()
      };
    }
    for (let i = 0; i < args.length; i++) {
      if (typeof args[i] === 'function') {
        args[i] = {
          type: 'Invocation',
          value: args[i]()
        };
      }
    }
    return {
      target: target,
      method: method,
      args: args
    };
  };
}

function getScrollView (target) {
  if (target instanceof Object && target._nativeTag) {
    target = React.view(target);
  }

  return call(target, 'scrollView');
}

module.exports = {
  call,
  getScrollView
};
