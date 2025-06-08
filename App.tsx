/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, {useRef, useState} from 'react';
import {
  Button,
  Dimensions,
  NativeModules,
  SafeAreaView,
  ScrollView,
  StatusBar,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import {Colors, Header} from 'react-native/Libraries/NewAppScreen';
import {WebView} from 'react-native-webview';

const {CalculatorModule, NetworkModule} = NativeModules;

function App(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  const webviewRef = useRef<WebView>(null);
  const [message, setMessage] = useState<string>();
  const safePadding = '5%';

  const sendMessage = (data: object) => {
    webviewRef.current?.postMessage(JSON.stringify(data));
  };

  return (
    <SafeAreaView style={[backgroundStyle, {flex: 1}]}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView style={backgroundStyle}>
        <View style={{paddingRight: safePadding}}>
          <Header />
        </View>
        <Button
          title="CalculatorModule.add(1, 2)"
          onPress={() =>
            CalculatorModule.add(1, 2).then((result: number) =>
              console.log(result),
            )
          }
        />
        <Button
          title="isWifiConnected"
          onPress={() =>
            NetworkModule.isWifiConnected().then((result: boolean) =>
              console.log(result),
            )
          }
        />
        <Button
          title="Send message"
          onPress={() => sendMessage({data: 'hello'})}
        />
        <WebView
          ref={webviewRef}
          style={{
            flex: 1,
            width: Dimensions.get('window').width,
            height: Dimensions.get('window').height,
          }}
          originWhitelist={['*']}
          source={{
            uri: 'file:///android_asset/index.html',
          }}
          allowFileAccess={true}
          allowUniversalAccessFromFileURLs={true}
          onMessage={event => setMessage(event.nativeEvent.data)}
        />
        <Text>웹뷰부터 받은 데이터: {message}</Text>
      </ScrollView>
    </SafeAreaView>
  );
}

export default App;
