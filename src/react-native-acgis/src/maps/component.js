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
    ...View.propTypes,
  }
  render() {
    return <ArcgisMapView {...this.props}/>
  }

  name = 'ArcgisMapView'
}

const ArcgisMapView = requireNativeComponent('ArcgisMapView', RctMapView)
export default ArcgisMapView;