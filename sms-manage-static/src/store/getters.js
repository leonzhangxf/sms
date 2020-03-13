
const getters = {
  keycloak: state => state.user.keycloak,
  username: (state) => {
    try {
      //eagerly get newest user username
      if (state.user.keycloak.tokenParsed) {
        state.user.username = state.user.keycloak.tokenParsed.preferred_username
      }
    } catch (e) {}
    return state.user.username
  },
  name: (state) => {
    try {
      //eagerly get newest user name
      if (state.user.keycloak.tokenParsed) {
        state.user.name = state.user.keycloak.tokenParsed.name
      }
    } catch (e) {}
    return state.user.name;
  },
  siderCollapsed: (state) => {
    if (state.view.sider.collapsed === undefined || state.view.sider.collapsed === null) return false;
    return state.view.sider.collapsed;
  },
  cachedViews: state => state.view.cachedViews,
  visitedViews: state => state.view.visitedViews
};

export default getters