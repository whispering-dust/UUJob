<template>
    <div>
      <!-- 聊天界面 -->
      <ul>
        <li v-for="message in messages" :key="message.id">
          {{ message.content }}
        </li>
      </ul>
  
      <!-- 发送消息表单 -->
      <form @submit.prevent="sendMessage">
        <input type="text" v-model="newMessage" />
        <button type="submit">发送</button>
      </form>
    </div>
  </template>
  
  <script>
  import {useRoute} from 'vue-router'
  import { reactive, toRefs, watch} from 'vue'
  export default {
    data() {
      return {
        messages: [
            {
                content:"nihao"
            },
            {
                content:"nihao"
            }
        ], // 聊天记录
        newMessage: '', // 新消息
        ID: useRoute().params.userId,
      }
    },
  
    methods: {
      // 发送消息
      sendMessage() {
        // TODO: 发送消息到服务器
        this.newMessage = ''
      }
    },

    mounted() {
      const route = useRoute();

      // 监听路由参数变化
      watch(() => route.params.userId, (userId) => {
        // 根据新的userId获取聊天记录
        alert(userId);
      })
    }
  }
  </script>
  