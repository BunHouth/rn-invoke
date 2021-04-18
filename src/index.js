import {NativeModules} from 'react-native';
import Invoke from './invoke';

const RNInvokeManager = NativeModules.RNInvoke;

function execute(invocation) {
  if (typeof invocation === 'function') {
    invocation = invocation();
  }
  return RNInvokeManager.execute(invocation);
}

module.exports = {
  React: require('./react.js'),
  IOS: require('./ios.js'),
  call: Invoke.call,
  execute: execute
};
