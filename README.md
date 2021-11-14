# react-native-bottom-sheet-alert

## Getting started


###### package.json
`"react-native-bottom-alert": "sergeymild/react-native-bottom-alert#0.6.0"`

`$ yarn`

## Usage
```typescript
import {BottomSheetAlert} from 'react-native-bottom-sheet-alert';

BottomSheetAlert.show({
  title?: string,
  message?: string,
  buttons: [
    {text: string, style?: 'cancel' | 'destructive' | 'default', data?: any}
  ]},
  (selected: BottomSheetAlertButton) => console.log('ButtonPressed', selected)
)
```
