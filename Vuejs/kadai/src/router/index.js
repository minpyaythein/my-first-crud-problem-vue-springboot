import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import Users from '@/views/Users.vue'
import Create from '@/views/Create.vue'
import CreateConfirm from '@/views/CreateConfirm.vue'
import Delete from '@/views/Delete.vue'
import Update from '@/views/Update.vue'
import UpdateConfirm from '@/views/UpdateConfirm.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/users',
      name: 'users',
      component: Users
    },
    {
      path: '/create',
      name: 'create',
      component: Create
    },
    {
      path: '/create_confirm',
      name: 'create_confirm',
      component: CreateConfirm
    },
    {
      path: '/delete',
      name: 'delete',
      component: Delete
    },
    {
      path: '/update',
      name: 'update',
      component: Update
    },
    {
      path: '/update_confirm',
      name: 'update_confirm',
      component: UpdateConfirm
    }
  ]
})

export default router
