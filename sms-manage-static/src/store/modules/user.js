import Keycloak from "keycloak-js";
import {UserStorage} from "../../storage";
import axios from "../../components/axios";
import config from '../../../config'
import {KEYCLOAK, LOGOUT, UPDATE_KEYCLOAK} from '../types'

const user = {
  state: {
    //在此初始化keycloak实例
    keycloak: new Keycloak({
      url: config.keycloak.authUrl,
      realm: config.keycloak.realm,
      clientId: config.keycloak.clientId
    }),
    username: UserStorage.getUsername(),
    name: UserStorage.getName()
  },
  mutations: {
    [KEYCLOAK]: (state, keycloak, deleted = false) => {
      try {
        //这里直接更新新的keycloak对象，而不从storage中获取
        state.keycloak = keycloak;
      } catch (ex) {
      }

      if (deleted) {
        UserStorage.removeKeycloak();
      } else {
        UserStorage.setKeycloak(keycloak);
        axios.defaults.headers.common = {'Authorization': 'Bearer ' + keycloak.token}
      }
    }
  },
  actions: {
    updateKeycloak: ({commit}, keycloak) => {
      commit(KEYCLOAK, keycloak, false);
    },
    logout: ({commit}) => {
      commit(KEYCLOAK, null, true);
    }
  }
};

export default user