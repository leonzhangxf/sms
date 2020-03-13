import configDev from './dev'
import configTest from './test'
import configProd from './prod'

let config;
if (process.env.NODE_ENV == 'development') {
  config = configDev;
} else if (process.env.NODE_ENV == 'test') {
  config = configTest;
} else if (process.env.NODE_ENV == 'production') {
  config = configProd;
}

export default config