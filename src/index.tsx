import { requireNativeComponent, ViewStyle } from 'react-native';

type BottomAlertProps = {
  color: string;
  style: ViewStyle;
};

export const BottomAlertViewManager = requireNativeComponent<BottomAlertProps>(
'BottomAlertView'
);

export default BottomAlertViewManager;
