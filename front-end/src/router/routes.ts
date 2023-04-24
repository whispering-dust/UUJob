const routes = [
    {
      name: '/',
      path: '/',
      meta: {keepAlive: true},
      component: () => import('@/view/Home.vue')
    },
    {
        name: 'Login',
        path: '/login',
        meta: {keepAlive: false},
        component: () => import('@/view/LoginRegister.vue')
    },
    {
      name: 'Demo',
      path: '/demo',
      meta: {keepAlive: true},
      component: () => import('@/components/TiptapEditorDemo.vue')
  },

    {
        name: 'Home',
        path: '/home',
        meta: {keepAlive: true},
        component: () => import('@/view/Home.vue')
    },
    {
      name: 'Forum',
      path: '/forum',
      meta: {keepAlive: true},
      component: () => import('@/view/Forum.vue')
    },
    {
      name: 'PublishPost',
      path: '/forum/publishPost',
      meta: {keepAlive: true},
      component: () => import('@/components/forum/PublishPost.vue')
    },
    {
      name: 'ManagePosted',
      path: '/manageposted',
      meta: {keepAlive: true},
      component: () => import('@/view/ManagePosted.vue')
    },
    {
      name: 'Position',
      path: '/Position',
      meta: {keepAlive: true},
      component: () => import('@/view/PositionView.vue')
    },
    {
      name: 'Job',
      path: '/job/:jobId',
      meta: {keepAlive: true},
      component: () => import('@/view/Job.vue')
    },
    {
      name: 'Apply',
      path: '/job/:jobId/apply',
      meta: {keepAlive: true},
      component: () => import('@/view/Apply.vue')
    },
    {
        name: 'Myspace',
        path: '/myspace',
        meta: {keepAlive: true},
        component: () => import('@/view/Myspace.vue'),
        children:[
        {
            path: '/myspace/historyJob',
            name: 'history',
            component: () => import('@//components/myspace/HistoryJob.vue')
          },

          {
            path: '/myspace/profile',
            name: 'myProfile',
            component: () => import('@/components/myspace/Profile.vue')
          },
          {
            path: '/myspace/password',
            name: 'password',
            component: () => import('@/components/myspace/Password.vue')
          },
          {
            path:'/myspace/userAccount',
            name:'userAccount',
            component:()=>import('@/components/myspace/UserAccount.vue')
          },
          {
            path: '/myspace/chatList',
            name: 'chatList',
            component: () => import('@/components/myspace/ChatList.vue'),
            children:[
              {
                path: '/myspace/chatList/chatRoom/:userId',
                name: 'chatRoom',
                component: () => import('@/components/myspace/ChatRoom.vue')
              },
    
            ]
          },
          {
            path: '/myspace/myPost',
            name: 'myPost',
            component: () => import('@/components/myspace/MyPost.vue')
          },
          
        ]

    },
    // {
    //     name: 'FaultPage',
    //     path: '/:catchAll(.*)',
    //     component: () => import('@/view/404.vue')
    // },

];

export default routes
