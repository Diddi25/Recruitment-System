import { createRouter, createWebHistory } from 'vue-router'
import HomePresenter from '@/presenters/HomePresenter'
import RegisterPresenter from '@/presenters/RegisterPresenter'
import LoginPresenter from '@/presenters/LoginPresenter'
import ApplicationPresenter from '@/presenters/ApplicationPresenter'
import ApplicationListPresenter from '@/presenters/ApplicationListPresenter'
import LoginSuccessPresenter from '@/presenters/LoginSuccessPresenter'
import AdRecruiterPresenter from '@/presenters/AdRecruiterPresenter'
import store from '../store/storeIndex.js';

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
    ],
  });

  routerInstance.beforeEach((to, from, next) => {
    const protectedRoutes = ['/apply', '/applications', '/manage-ads'];
    let user = JSON.parse(localStorage.getItem('user'));

    if (protectedRoutes.includes(to.path) && !store.state.auth.status.loggedIn) {
      return next('/login');
    }

    if(protectedRoutes.includes(to.path) && user.roles == "ROLE_USER") {
        return next('/login-success');
    }
    if(protectedRoutes.includes(to.path) && user.roles == "ROLE_RECRUITER") {
        return next('/login-success');
    }

    next();
  });

  return routerInstance;
}

export default createAppRouter;
