import {
    GET_DANGER_HISTORY_INFO,
} from '../action/danger.js';

// 隐患历史台账表格数据
export const dangerData = (state = {
    items: [],
    currPage: 1,
    totalPage: 1,
    pageSize: 10,
    totalCount: 0
}, action = {}) => {
    switch (action.type) {
        case GET_DANGER_HISTORY_INFO:
            action.success ? action.success(action.json) : null;
            return Object.assign({}, state, action.json.result);
        default:
            return state;
    }
}