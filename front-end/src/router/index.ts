import store from '@/store';
import {createRouter, createWebHistory} from 'vue-router'
import routes from './routes'
import storage from "../utils/storage"
 
const router = createRouter({
    history: createWebHistory(), 
    routes
})

router.beforeEach((to, from, next) => {
    if (to.path === '/login' || to.path === '/') {
      next();
    } else {
      let token = storage.get("userId");
   
      if (token === null || token === '') {
        next('/login');
      } else {
        next();
      }
    }
  });
router.afterEach((to,from,next) => {
    window.scrollTo(0,0);
    // chrome
      document.body.scrollTop = 0
      // firefox
      document.documentElement.scrollTop = 0
      // safari
      window.pageYOffset = 0
})
export default router