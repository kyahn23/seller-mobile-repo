const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    meta: { titleLabel: "펜타폰" },
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
      }
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
