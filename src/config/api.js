
let Api = {};
console.error = (string) => {

};
// 试运行版本控制
Api.isTempRelease = true;  // 试运行

// 发布地址
Api.ReleaseIP = 'http://192.168.31.160/danger'; //'http://120.27.197.238';

// 系统名称
Api.SystemName = "隐患系统";

// 接口API地址配置
Api.AppApiPrefix = Api.ReleaseIP;   //接口API前缀

// Pc隐患接口
Api.Danger = {};
Api.Danger.GetPageDangerHistoryInfo = Api.AppApiPrefix + '/api/services/app/pcDangerManager/GetPageDangerHistoryInfo';

module.exports = Api;