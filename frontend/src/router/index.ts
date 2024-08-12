import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PageNotFoundView from '../views/PageNotFoundView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/construct',
      name: 'construct',
      component: () => import('../views/ConstructFormView.vue'),
      children: [
        {
          path: 'form',
          component: () => import('../views/ConstructFormView.vue'),
        },
      ],
    },
    {
      path: '/:pathMatch(.*)*',
      component: PageNotFoundView,
    },
  ]
})

export default router
