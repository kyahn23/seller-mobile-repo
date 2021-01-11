
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    meta: { titleLabel: "펜타폰" },
    children: [
      { path: '', component: () => import('pages/Main.vue') },
      { path: 'main', component: () => import('pages/Main.vue') },
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
