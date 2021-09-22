# react-native-bottom-sheet-alert

## Getting started


###### package.json
`"react-native-bottom-sheet-alert": "https://github.com/sergeymild/react-native-bottom-sheet-alert"`

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
