import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import PageNotFoundView from '@/views/PageNotFoundView.vue'
import ConstructListView from '@/views/construct/ConstructListView.vue'
import ConstructFormView from '@/views/construct/ConstructFormView.vue'
import ConstructView from '@/views/construct/ConstructView.vue'

const router = createRouter({
  //history: createWebHistory(import.meta.env.BASE_URL),
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/construct',
      name: 'construct',
      component: ConstructView,
      children: [
        {
          path: 'list',
          component: ConstructListView
        },
        {
          path: 'form',
          component: ConstructFormView
        }
      ]
    },
    {
      path: '/:pathMatch(.*)*',
      component: PageNotFoundView
    }
  ]
})

export default router
