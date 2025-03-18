import { createRouter, createWebHistory } from 'vue-router'
import HomePresenter from '@/presenters/HomePresenter'
import RegisterPresenter from '@/presenters/RegisterPresenter'
import LoginPresenter from '@/presenters/LoginPresenter'
import ApplicationPresenter from '@/presenters/ApplicationPresenter'
import ApplicationListPresenter from '@/presenters/ApplicationListPresenter'
import LoginSuccessPresenter from '@/presenters/LoginSuccessPresenter'
import AdRecruiterPresenter from '@/presenters/AdRecruiterPresenter'
import ForbiddenPagePresenter from '@/presenters/ForbiddenPagePresenter'

import store from '../store/storeIndex.js';
/**
 * Is used to navigate between different presenters.
 * @param {*} model 
 * @returns A vue router
 */

function createAppRouter(model) {
  const routerInstance = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
      {
        path: '/',
        name: 'home',
        component: <HomePresenter model={model}/>
      },
      {
        path: '/register',
        name: 'register',
        component: <RegisterPresenter model={model}/>
      },
      {
        path: '/login',
        name: 'login',
        component: <LoginPresenter model={model}/>
      },
      {
        path: '/apply',
        name: 'apply',
        component: <ApplicationPresenter model={model}/>
      },
      {
        path: '/applications',
        name: 'applications',
        component: <ApplicationListPresenter model={model}/>
      },
      {
        path: '/login-success',
        name: 'login-success',
        component: <LoginSuccessPresenter model={model}/>
      },
      {
        path: '/manage-ads',
        name: 'manage-ads',
        component: <AdRecruiterPresenter model={model}/>
      },
      {
        path: '/forbidden',
        name: 'forbidden',
        component: <ForbiddenPagePresenter model={model}/>
      },
    ],
  });

  routerInstance.beforeEach((to, from, next) => {
    const protectedRoutes = ['/apply', '/applications', '/manage-ads',  '/login-success'];
    const allowedRoutes = ['/apply', '/applications', '/manage-ads', '/', '/forbidden',
       '/login-success', '/login', '/register'];

    const protectedRoutesrec = ['/apply'];
    const protectedRoutesuser = ['/applications'];
    let user = JSON.parse(sessionStorage.getItem('user'));

    if(!allowedRoutes.includes(to.path)) {
      return next('/');
    }
    if (protectedRoutes.includes(to.path) && !store.state.auth.status.loggedIn) {
      return next('/login');
    }
    if (protectedRoutesuser.includes(to.path) && user.roles == "ROLE_USER") {
      return next('/forbidden');
    }
    if (protectedRoutesrec.includes(to.path) && user.roles == "ROLE_RECRUITER") {
      return next('/forbidden');
    }
    next();
  });

  return routerInstance;
}

export default createAppRouter;