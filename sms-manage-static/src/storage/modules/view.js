import Cookies from 'js-cookie'
import constant  from '../../constant'

const ViewStorage = {
  setCollapsed: (collapsed) => {
    Cookies.set(constant.VIEW.COLLAPSED, collapsed);
  },
  getCollapsed: () => {
    Cookies.get(constant.VIEW.COLLAPSED);
  }
};

export default ViewStorage