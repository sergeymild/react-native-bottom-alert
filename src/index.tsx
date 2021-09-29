import { NativeModules } from 'react-native';

export type BottomSheetAlertButtonStyle = 'default' | 'destructive' | 'cancel';

export interface BottomSheetAlertButton {
  readonly text: string;
  readonly style?: BottomSheetAlertButtonStyle;
  readonly data?: any;
}

interface BottomSheetAlertProperties {
  readonly title?: string;
  readonly message?: string;
  readonly buttons: BottomSheetAlertButton[];
  readonly theme?: 'light' | 'dark';
}

type Callback = (selected: BottomSheetAlertButton) => void;

export class BottomSheetAlert {
  static show(properties: BottomSheetAlertProperties, callback: Callback) {
    NativeModules.BottomSheetAlert.show(properties, (index: number) => {
      const selected = properties.buttons[index];
      callback(selected);
    });
  }
}
