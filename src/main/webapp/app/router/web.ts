const Web = () => import("@/web/web.vue")
const Blog = () => import("@/web/blog.vue")

export default {
  path: '/web',
  component: Web,
  children: [
    {
      path: 'blog',
      name: 'Blog',
      component: Blog,
      // meta: {authorities: [Authority.USER]},
    },
  ]
}
