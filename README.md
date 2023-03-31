# react-native-bottom-sheet-alert

## Getting started


###### package.json
`"react-native-bottom-alert": "sergeymild/react-native-bottom-alert#0.8.3"`

`$ yarn`

## Usage
```typescript
import {BottomSheetAlert} from 'react-native-bottom-sheet-alert';

export type BottomSheetAlertButtonStyle = 'default' | 'destructive' | 'cancel';
type Appearance = {textAlign?: 'center'; fontSize?: number; color?: string; fontFamily?: string}
export interface BottomSheetAlertButton {
  readonly text: string;
  readonly style?: BottomSheetAlertButtonStyle;
  readonly data?: any;
  readonly icon?: ImageRequireSource;
  readonly appearance?: Appearance
}

interface BottomSheetAlertProperties {
  iosTintColor?: string,
  readonly title?: {
    text: string;
    readonly appearance?: Appearance
  }
  readonly message?: {
    text: string;
    readonly appearance?: Appearance
  }
  readonly buttons: BottomSheetAlertButton[];
  readonly theme?: 'light' | 'dark';
  readonly buttonsBorderRadius?: number
  readonly cancelButtonBorderRadius?: number
}
// show method signature
show(properties: BottomSheetAlertProperties): Promise<BottomSheetAlertButton | undefined>

const selected = await BottomSheetAlert.show({
  title? : {text: string},
  message? : {text: string},
  buttons: []
})
if (selected === undefined) return
```
