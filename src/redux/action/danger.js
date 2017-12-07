// import fetch from 'isomorphic-fetch';
import Api from '../../config/api';


export const GET_DANGER_HISTORY_INFO = 'GET_DANGER_HISTORY_INFO';

//获取数据成功
const getDataSuccess = (json, success, type) => {
    return {
        type: type,
        json,
        success
    }
}


// 查询获取隐患信息表格
export const getDangerHistoryInfo = (params, success) => {
    var path = Api.Danger.GetPageDangerHistoryInfo;
    return dispatch => {
        return fetch(path, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            mode: 'cors'
        })
            .then(response => response.json())
            .then(json => dispatch(getDataSuccess(json, success, GET_DANGER_HISTORY_INFO)))
            .catch(error => console.log(error))
    }
}