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

export class BottomSheetAlert {
  static show(
    properties: BottomSheetAlertProperties
  ): Promise<BottomSheetAlertButton> {
    return new Promise((resolve) => {
      NativeModules.BottomSheetAlert.show(properties, (index: number) => {
        const selected = properties.buttons[index];
        resolve(selected);
      });
    });
  }
}
