const routes = [
  {
    path: '/login',component: () => import('pages/Login.vue')
  },
  {
    path: '/verify', component: () => import('components/Verify.vue')
  },
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    meta: {
      requiresAuth: true,
      titleLabel: "펜타폰"
    },
    children: [
      { path: "", component: () => import("pages/Main.vue") },
      { path: "main", component: () => import("pages/Main.vue") },
      { path: "board", component: () => import("pages/Board.vue") },
      {
        path: "board/:brdNo",
        component: () => import("pages/BoardDetail.vue"),
        props: true
      },
      {
        path: "product/:carrier",
        component: () => import("pages/ProductView.vue"),
        props: true
      },
      { path: 'status', component: () => import('pages/Status.vue') },
      {
        path: "status/detail/:csNo",
        component: () => import("pages/Status_Detail.vue"),
        meta: {
          titleLabel: "상담상세정보"
        },
        props: true
      },
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "*",
    component: () => import("pages/Error404.vue")
  }
];

export default routes;
