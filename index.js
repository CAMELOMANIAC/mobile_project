/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import notifee from '@notifee/react-native';

requestPermission();
AppRegistry.registerHeadlessTask(
  'SomeTaskName',
  () => require('./headlessJS/test').default,
);
AppRegistry.registerComponent(appName, () => App);

async function requestPermission() {
  await notifee.requestPermission();
}
