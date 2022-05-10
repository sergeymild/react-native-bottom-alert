import * as React from 'react';

import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { useEffect } from 'react';
import { BottomSheetAlert } from 'react-native-bottom-alert';

export default function App() {
  useEffect(() => {
    BottomSheetAlert.show({
      theme: 'dark',
      title: 'Are you sure want delete all data?',
      buttons: [
        {
          text: 'Ok',
          style: 'default',
          data: 'ok',
          icon: require('../assets/icSettings.png'),
        },
        { text: 'None', style: 'destructive', data: 'none' },

        { text: 'Cancel', style: 'cancel', data: 'cancel' },
      ],
    }).then((selected) => {
      if (!selected) return console.log('undefined');
      if (selected.style === 'cancel') return;
      console.log('-======', selected.data);
    });
  });

  return (
    <View style={styles.container}>
      <TouchableOpacity
        onPress={async () => {
          const selected = await BottomSheetAlert.show({
            title: 'Are you sure want delete all data?',
            message: 'Awesome message',
            buttons: [
              {
                text: 'Ok',
                style: 'default',
                data: 'ok',
                icon: require('../assets/icSettings.png'),
              },
              {
                text: 'None',
                style: 'destructive',
                data: 'none',
                icon: require('../assets/icViews.png'),
              },

              { text: 'Cancel', style: 'cancel', data: 'cancel' },
            ],
          });
          if (!selected) return;
          if (selected.style === 'cancel') return;
          console.log('-======', selected.data);
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
