const config = {
  keycloak: {
    authUrl: 'https://auth.leonzhangxf.com/auth',
    realm: 'employee',
    clientId: 'sms-manage-static'
  },
  api: {
    baseUrl: 'http://localhost:8081',
    timeout: 15000
  }
};

export default config