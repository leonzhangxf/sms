import constant from '../../constant'

let UserStorage = {
  setKeycloak(keycloak) {
    localStorage.setItem(constant.KEYCLOAK, keycloak);
    try {
      UserStorage.setUsername(keycloak.tokenParsed.preferred_username);
      UserStorage.setName(keycloak.tokenParsed.name);
    } catch (e) {
      //可能获取值异常
      UserStorage.setUsername('Anonymous');
      UserStorage.setName('Anonymous');
    }
  },
  getKeycloak() {
    let keyStr = localStorage.getItem(constant.KEYCLOAK);
    let res = (keyStr === undefined || null === keyStr || '' === keyStr) ? {authenticated: false} : keyStr;
    return res;
  },
  removeKeycloak() {
    localStorage.removeItem(constant.KEYCLOAK);
    localStorage.removeItem(constant.USERNAME);
    localStorage.removeItem(constant.NAME);
  },
  setUsername(username) {
    localStorage.setItem(constant.USERNAME, username);
  },
  getUsername() {
    return localStorage.getItem(constant.USERNAME);
  },
  setName(username) {
    localStorage.setItem(constant.NAME, username);
  },
  getName() {
    return localStorage.getItem(constant.NAME);
  }
};

export default UserStorage