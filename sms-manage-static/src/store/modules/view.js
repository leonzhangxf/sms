import {
  ADD_VISITED_VIEW,
  COLLAPSE,
  DEL_ALL_VIEW,
  DEL_OTHERS_VIEW,
  DEL_VISITED_VIEW,
  DISPOSE_VIEWS,
  TOGGLE_COLLAPSE
} from '../types'
import {ViewStorage} from '../../storage'

const view = {
  state: {
    sider: {
      collapsed: ViewStorage.getCollapsed()
    },
    //已经看过的视图
    visitedViews: [],
    //缓存的视图
    cachedViews: []
  },
  mutations: {
    //切换侧边栏伸缩
    [COLLAPSE]: (state, collapsed = null) => {
      if (null != collapsed) {
        ViewStorage.setCollapsed(collapsed);
        state.sider.collapsed = collapsed;
      } else {
        if (state.sider.collapsed) {
          ViewStorage.setCollapsed(false);
        } else {
          ViewStorage.setCollapsed(true);
        }
        state.sider.collapsed = !state.sider.collapsed;
      }
    },
    [DISPOSE_VIEWS]: (state, {type, view}) => {

      switch (type) {
        case ADD_VISITED_VIEW: { //添加看过的视图

          if (state.visitedViews.some(v => v.path === view.path)) return;

          state.visitedViews.push({
            name: view.name,
            path: view.path,
            title: view.meta.title || 'no-name'
          });

          if (view.meta.cacheView) {
            state.cachedViews.push(view.name);
          }

          break;
        }

        case DEL_VISITED_VIEW: { //删除指定视图
          for (const [i, v] of state.visitedViews.entries()) {
            if (v.name === view.name) {
              state.visitedViews.splice(i, 1);
              break;
            }
          }

          for (const i of state.cachedViews) {
            if (i === view.name) {
              const index = state.cachedViews.indexOf(i);
              state.cachedViews.splice(index, 1);
              break;
            }
          }

          break;
        }

        case DEL_OTHERS_VIEW: { //删除除指定视图外的其他视图
          for (const [i, v] of state.visitedViews.entries()) {
            if (v.path === view.path) {
              state.visitedViews = state.visitedViews.slice(i, i + 1);
              break;
            }
          }
          for (const i of state.cachedViews) {
            if (i === view.name) {
              const index = state.cachedViews.indexOf(i);
              state.cachedViews = state.cachedViews.slice(index, i + 1);
              break;
            }
          }

          break;
        }

        case DEL_ALL_VIEW: { //清空所有的视图，包括看过的，和缓存的
          state.visitedViews = [];
          state.cachedViews = [];

          break;
        }
      }
    }
  },
  actions: {
    [TOGGLE_COLLAPSE]: ({commit}, collapsed = null) => {
      commit(COLLAPSE, collapsed);
    },
    [ADD_VISITED_VIEW]: ({commit}, view) => {
      commit(DISPOSE_VIEWS, {type: ADD_VISITED_VIEW, view});
    },
    //删除指定视图完成后，返回最新的已看视图列表
    [DEL_VISITED_VIEW]: ({commit, state}, view) => {
      return new Promise((resolve) => {
        commit(DISPOSE_VIEWS, {type: DEL_VISITED_VIEW, view});
        resolve([...state.visitedViews]);
      });
    },
    //删除除目标视图外其他视图后，返回最新的已看视图列表
    [DEL_OTHERS_VIEW]: ({commit, state}, view) => {
      return new Promise((resolve) => {
        commit(DISPOSE_VIEWS, {type: DEL_OTHERS_VIEW, view});
        resolve([...state.visitedViews]);
      })
    },
    //删除所有视图后，返回最新的已看视图列表
    [DEL_ALL_VIEW]: ({commit, state}) => {
      return new Promise((resolve) => {
        commit(DISPOSE_VIEWS, {type: DEL_ALL_VIEW});
        resolve([...state.visitedViews]);
      })
    }
  }
};

export default view