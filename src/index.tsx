import { Image, ImageRequireSource, NativeModules } from 'react-native';

export type BottomSheetAlertButtonStyle = 'default' | 'destructive' | 'cancel';

export interface BottomSheetAlertButton {
  readonly text: string;
  readonly style?: BottomSheetAlertButtonStyle;
  readonly data?: any;
  readonly icon?: ImageRequireSource;
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
  ): Promise<BottomSheetAlertButton | undefined> {
    return new Promise((resolve) => {
      NativeModules.BottomSheetAlert.show(
        {
          ...properties,
          buttons: properties.buttons.map((b) => ({
            ...b,
            icon: b.icon ? Image.resolveAssetSource(b.icon).uri : undefined,
          })),
        },
        (index: number) => {
          console.log('[Index.]', index);
          if (index === -1) return resolve(undefined);
          const selected = properties.buttons[index];
          resolve(selected);
        }
      );
    });
  }
}
