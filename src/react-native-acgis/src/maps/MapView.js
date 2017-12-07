// MapView.js
import React, { Component } from 'react';
import {
  requireNativeComponent,
  View,
  Image,
  TouchableHighlight
} from 'react-native';
import BaseComponent from '../BaseComponent';
import PropTypes from 'prop-types';

// var iface = {
//   name: 'ArcgisMapView',
//   propTypes: {
//     // width: PropTypes.number,
//     // height: PropTypes.number,
//     ...View.propTypes
//   },
// };

// 注册原生Arcgis组件
class RctMapView extends BaseComponent {
  static propTypes = {
    ...View.propTypes
  }

  render() {
    return <RCTArcgisMapView {...this.props} />
  }

  name = 'RCTArcgisMapView'
}

const RCTArcgisMapView = requireNativeComponent('RCTArcgisMapView', RctMapView)
export default RCTArcgisMapView;


// 接入注册好的原生组件
// 封装基本功能
class MapView extends Component {
  componentDidMount() {
  }

  render() {
    return (
      <View
        style={{
          width: '100%',
          height: '50%',
          position: 'relative'
        }}>
        <RctMapView
          ref="mapview"
          style={{
            position: 'absolute',
            left: 0,
            right: 0,
            height: '100%'
          }}
        >
        </RctMapView>
        {/* // 放大按钮 */}
        <TouchableHighlight
          style={{
            position: 'absolute',
            left: 10,
            top: 0,
            height: 35,
            width: 35
          }}
          onPress={() => {
            this.refs.mapview._sendCommand('zoomIn', null);
          }}
        >
          <Image
            source={require('./images/mapfangda.png')}
          />
        </TouchableHighlight>
        {/* // 缩小按钮 */}
        <TouchableHighlight
          style={{
            position: 'absolute',
            left: 10,
            top: 40,
            height: 35,
            width: 35
          }}
          onPress={() => {
            this.refs.mapview._sendCommand('zoomOut', null);
          }}
        >
          <Image
            source={require('./images/mapsuoxiao.png')}
          />
        </TouchableHighlight>
        {/* // 查询定位 */}
        <Image
          style={{
            position: 'absolute',
            right: 10,
            bottom: 200,
            height: 35,
            width: 35
          }}
          source={require('./images/mapdingwei.png')}
        />
        {/* // 人员定位 */}
        <Image
          style={{
            position: 'absolute',
            right: 10,
            bottom: 160,
            height: 35,
            width: 35
          }}
          source={require('./images/inspector.png')}
        />
        {/* // 标注 */}
        <Image
          style={{
            position: 'absolute',
            right: 10,
            bottom: 120,
            height: 35,
            width: 35
          }}
          source={require('./images/biaoxuan.png')}
        />
        {/* // 清除 */}
        <Image
          style={{
            position: 'absolute',
            right: 10,
            bottom: 80,
            height: 35,
            width: 35
          }}
          source={require('./images/mapclear.png')}
        />
        {/* // 测量距离 */}
        <TouchableHighlight
          style={{
            position: 'absolute',
            right: 10,
            bottom: 40,
            height: 35,
            width: 35
          }}
          onPress={() => {
            this.refs.mapview._sendCommand('length', null);
          }}
        >
          <Image
            source={require('./images/maplength.png')}
          />
        </TouchableHighlight>
        {/* // 测量面积 */}
        <TouchableHighlight
          style={{
            position: 'absolute',
            right: 10,
            bottom: 0,
            height: 35,
            width: 35
          }}
          onPress={() => {
            this.refs.mapview._sendCommand('area', null);
          }}
        >
        <Image
          source={require('./images/maparea.png')}
        />
        </TouchableHighlight>
      </View>
    );
  }
}

export {
  MapView
};