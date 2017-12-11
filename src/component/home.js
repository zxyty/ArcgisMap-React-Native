// var { requireNativeComponent } = require('react-native');
import React, { Component } from 'react';
import {
    Text,
    View,
    Modal,
    Image
} from 'react-native';
import { connect } from 'react-redux';
import { action } from '../redux/action/index.js';
import MapView, { ImageButton } from '../react-native-acgis/src/index';
// import MapView, { ImageButton } from '../react-native-acgis/src/index';
// 计划审批
class Home extends Component {

    componentDidMount() {
        this.props.getDangerHistoryInfo();
    }

    render() {
        var htmlStr = '';
        if (this.props.dangerData.items.length <= 0) {
            htmlStr = '无数据'
        } else {
            htmlStr = '有数据ss';
        }
        return (
            <MapView style={{
                position: 'relative'
            }}>
                <Text style={{
                    position: 'absolute',
                    left: 0,
                    top: 0,
                    zIndex: 999
                }}>{htmlStr}</Text>
            </MapView>
        );
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        getDangerHistoryInfo: (params, success) => {
            dispatch(action.getDangerHistoryInfo(params, success));
        }
    }
}
const mapStateToProps = (state, ownProps) => {
    return {
        dangerData: state.dangerData,
    }
}

const HomeC = connect(mapStateToProps, mapDispatchToProps)(Home);

export default HomeC;