import axios from 'axios'
import qs from 'qs'
import config from '../../../config/index'

const api = axios.create({
  baseURL: config.api.baseUrl,
  timeout: config.api.timeout,// 请求超时时间
  withCredentials: false,
  headers: {'X-Requested-With': 'XMLHttpRequest'},
  transformRequest: [function (data) { //对请求数据进行处理
    let trans = qs.stringify(data, { allowDots: true });
    return trans;
  }],
  validateStatus: function (status) {
    return status >= 200 && status < 300 || status === 400 || status === 401
      || status === 403 || status === 500; // default
  },
});

// respone拦截器
api.interceptors.response.use(
  response => {
    const res = response.data;
    if (response.status === 200) {
      return res;//直接返回数据
    } else if (response.status === 400) {
      //请求参数错误
      return Promise.reject(response);
    } else if (response.status === 401) {
      // 未认证，或者token过期，登出
      return Promise.reject(response);
    } else if (response.status === 403) {
      //没有权限
      return Promise.reject(response);
    } else {
      return Promise.reject(res);
    }
  },
  error => {
    return Promise.reject(error);
  }
);

export default api