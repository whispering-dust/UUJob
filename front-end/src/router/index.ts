import store from '@/store';
import {createRouter, createWebHistory} from 'vue-router'
import routes from './routes'
 
const router = createRouter({
    history: createWebHistory(), 
    routes
})

// router.beforeEach((to, from, next) => {
//     if (to.path === '/login') {
//       next();
//     } else {
//       let token = localStorage.getItem("token");
   
//       if (token === 'null' || token === '' || token!= store.state.userId) {
//         next('/login');
//       } else {
//         next();
//       }
//     }
//   });
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