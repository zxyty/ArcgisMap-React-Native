

// export const reducer = {
//     ...systemReducer,   // 系统设置模块
// };
// import {
//     routerReducer as routing,
// } from 'react-router-redux'
import {
    combineReducers,
} from 'redux';

import * as dangerReducer from './danger';

const rootReducer = combineReducers({
    // routing,
    config: (state = {}) => state,
    ...dangerReducer,
});

export default rootReducer;