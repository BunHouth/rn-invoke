import { NativeModules } from 'react-native';

type RnInvokeType = {
  multiply(a: number, b: number): Promise<number>;
};

const { RnInvoke } = NativeModules;

export default RnInvoke as RnInvokeType;
