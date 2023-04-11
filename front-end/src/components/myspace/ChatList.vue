<template>
    <div class="chat" style=" width: 100%; height: 100%;">
        <div class="row" style="width: 100%; height:100%;">
            <div class="col-2 " style="height: 800px; background-color:#ced4da; padding: 0px; overflow-y:auto;">
              <el-menu
                active-text-color="#a3cef1"
                background-color="#ced4da"
                class="el-menu-vertical-demo"
                text-color="#000"
                style="width: 100%; border-right: 0px;"
              >
              <div v-for="userChat in userChatList" :key="userChat.id">
                <router-link class="text-white" :to="{ name: 'chatRoom', params: { userId : userChat.userId} }" >
                    <el-menu-item :index="userChat.id">
                      <el-avatar :size="30" :src="circleUrl" />
                      <div class="pl-3">联系人</div>
                      <div>{{userChat.userId}}</div>
                    </el-menu-item>
                  </router-link>       
              </div>
                    
              </el-menu>
            
            </div>
    
            <div class="col-10">
              <el-container>
                <router-view ></router-view>
              </el-container>
            </div>
          </div>
    </div>
</template>

<script>
import { watch, ref } from 'vue'
import axios from "axios";

export default{  
  data(){

      return{
          userChatList:[
              {
                  id:1,
                  userId:'111',
                  userName:'张三',
              },
              {
                  id:2,
                  userId:'222',
                  userName:'张三',
              },
              {
                  id:3,
                  userId:'333',
                  userName:'张三',
              }, 
          ]
      }
  },
  methods: {
      async getUserChatList(){

      }
  },
}

</script>

<style scoped>

</style>