# react-native-bottom-sheet-alert

## Getting started


###### package.json
`"react-native-bottom-alert": "sergeymild/react-native-bottom-alert#0.7.1"`

`$ yarn`

## Usage
```typescript
import {BottomSheetAlert} from 'react-native-bottom-sheet-alert';

export interface BottomSheetAlertButton {
  readonly text: string;
  readonly style?: BottomSheetAlertButtonStyle;
  readonly data?: any;
  // supported only on Android
  readonly icon?: ImageRequireSource;
}

interface BottomSheetAlertProperties {
  readonly title?: string;
  readonly message?: string;
  readonly buttons: BottomSheetAlertButton[];
  readonly theme?: 'light' | 'dark';
}
// show method signature
show(properties: BottomSheetAlertProperties): Promise<BottomSheetAlertButton | undefined>

const selected = await BottomSheetAlert.show({
  title? : string,
  message? : string,
  buttons: []
})
if (selected === undefined) return
```
