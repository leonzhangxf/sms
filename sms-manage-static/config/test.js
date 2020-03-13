const config = {
  keycloak: {
    authUrl: 'http://auth-t.leonzhangxf.com/auth',
    realm: 'employee',
    clientId: 'sms-manage-static'
  },
  api: {
    baseUrl: 'http://localhost:8081',
    timeout: 15000
  }
};

export default config