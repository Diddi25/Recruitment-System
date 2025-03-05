import { createRouter, createWebHistory } from 'vue-router'
import HomePresenter from '@/presenters/HomePresenter'
import RegisterPresenter from '@/presenters/RegisterPresenter'
import LoginPresenter from '@/presenters/LoginPresenter'
import ApplicationPresenter from '@/presenters/ApplicationPresenter'
import ApplicationListPresenter from '@/presenters/ApplicationListPresenter'

function router(model) {
  return createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
      {
        path: '/',
        name: 'home',
        component: <HomePresenter model={model}/>,
      },
      {
        path: '/register',
        name: 'register',
        component: <RegisterPresenter model={model}/>,
      },
      {
        path: '/login',
        name: 'login',
        component: <LoginPresenter model={model}/>,
      },
      {
        path: '/apply',
        name: 'apply',
        component: <ApplicationPresenter model={model}/>,
      },
      {
        path: '/applications',
        name: 'applications',
        component: <ApplicationListPresenter model={model}/>,
      },
    ],
  })  
}
export default router
