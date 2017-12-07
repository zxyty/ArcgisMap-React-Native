// MapView.js
import { requireNativeComponent, View } from 'react-native';
import PropTypes from 'prop-types';
var iface = {
  name: 'ImageButton',
  propTypes: {
    // width: PropTypes.number,
    // height: PropTypes.number,
    zoomIn: PropTypes.bool,
    ...View.propTypes
  },
};
const ImageButton = requireNativeComponent('ImageButton', iface)
export default ImageButton;