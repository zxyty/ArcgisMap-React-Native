import React, { Component } from 'react';
import { AppRegistry } from 'react-native';
import { Provider } from 'react-redux';
import configure from './src/redux/store/store';   // store配置
import App from './App';

let store = configure({ config: global.$GLOBALCONFIG });

class ReactNativeApp extends Component {
	render() {
		return (
			<Provider store={ store }>
				<App/>
			</Provider>
		);
	}
}



AppRegistry.registerComponent('SisDanger', () => ReactNativeApp);
