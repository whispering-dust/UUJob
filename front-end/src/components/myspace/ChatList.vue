<template>
    <div class="chat" style=" width: 100%; height: 100%;">
        <div class="row" style="width: 100%; height:100%;">
            <div class="col-2 " style="height: 800px; background-color:#ced4da; padding: 0px; overflow-y:auto;">

              <!-- 搜索栏 -->
              <el-input round v-model="input" placeholder="Please input" clearable  style=" width:85%;margin:5px;">
              </el-input>


              <el-menu
                active-text-color="#a3cef1"
                background-color="#ced4da"
                class="el-menu-vertical-demo"
                text-color="#000"
                style="width: 100%; border-right: 0px;"
              >
              <div v-for="userChat in userChatList" :key="userChat.id" style="">
                <router-link class="text-white" :to="{ name: 'chatRoom', params: { userId : userChat.userId} }" >
                    <el-menu-item :index="userChat.id" style="width:100%;padding:0%;height:auto" >
                      <div class="chat-item">
                        <el-row>
                          <el-col :span="8">
                            <el-image class="avatar" src="@/assets/images/head.png" fit="cover" :preview-src-list="[]"></el-image>
                            <!-- <div class="unread-msg-number">7</div> -->
                          </el-col>
                          <el-col :span="16">
                            <el-row style="display: flex;flex-direction:row">
                              <div class="name">{{userChat.userName}}</div>
                              <div class="time">下午  03:52</div>
                            </el-row>
                            <el-row>
                              <div class="message">{{userChat.lastMessage}}</div>
                            </el-row>
                            
                          
                          </el-col>
                    
                        </el-row>
                      </div>
                      
                      
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
                userName:'张3',
                lastMessage:'这是张三的最后一条消息',
            },
            {
                id:2,
                userId:'222',
                userName:'张4',
                lastMessage:'放学别走',
            },
            {
                id:3,
                userId:'333',
                userName:'张5',
                lastMessage:'吃我闪焰冲锋',
            }, 
        ],
        input:'',
        
      }
  },
  methods: {
      async getUserChatList(){

      }
  },
}

</script>

<style scoped>
.chat-item {
  display: flex;
  align-items: center;
}

.avatar {
  margin-top: 15px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.unread-msg-number {
  position: absolute;
  top: -5px;
  right: -5px;
  background-color: #FF5722;
  color: #fff;
  font-size: 12px;
  width: 16px;
  height: 16px;
  border-radius: 50%;
  text-align: center;
  line-height: 16px;
}

.name {
  font-size: 16px;
  font-weight: bold;
  color: #000;
  height: 20%;
  margin-bottom: 2px;
}

.message {
  font-size: 14px;
  color: #888888;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

.time {
  font-size: 13px;
  color: #090909;
  margin-bottom: 6px;
}


</style>