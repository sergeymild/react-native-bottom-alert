import * as React from 'react';

import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { useEffect } from 'react';
import { BottomSheetAlert } from 'react-native-bottom-alert';
import type { BottomSheetAlertButton } from 'react-native-bottom-alert';

export default function App() {
  useEffect(() => {
    BottomSheetAlert.show(
      {
        theme: 'dark',
        title: 'Are you sure want delete all data?',
        buttons: [
          { text: 'Ok', style: 'default', data: 'ok' },
          { text: 'None', style: 'destructive', data: 'none' },

          { text: 'Cancel', style: 'cancel', data: 'cancel' },
        ],
      },
      (selected: BottomSheetAlertButton) => {
        if (selected.style === 'cancel') return;
        console.log('-======', selected.data);
      }
    );
  });

  return (
    <View style={styles.container}>
      <TouchableOpacity
        onPress={() => {
          BottomSheetAlert.show(
            {
              title: 'Are you sure want delete all data?',
              message: 'Awesome message',
              buttons: [
                { text: 'Sign Out', style: 'default' },
                { text: 'Cancel', style: 'cancel' },
              ],
            },
            (selected: BottomSheetAlertButton) => {
              if (selected.style === 'cancel') return;
              console.log('-======');
            }
          );
        }}
      >
        <Text>present</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
