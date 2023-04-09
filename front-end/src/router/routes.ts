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
        name: 'Myspace',
        path: '/myspace',
        meta: {keepAlive: true},
        component: () => import('@/view/Myspace.vue'),
        children:[
        // {
        //   path: '/myspace/userInfo',
        //   name: 'userInfo',
        //   component: () => import('@/components/myspace/UserInfo.vue')
        // },

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
            path: '/myspace/chatList',
            name: 'chatList',
            component: () => import('@/components/myspace/ChatList.vue'),
            children:[
              {
                path: '/myspace/chatList/:id',
                name: 'chatRoom',
                component: () => import('@/components/myspace/ChatRoom.vue')
              },
    
            ]
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
