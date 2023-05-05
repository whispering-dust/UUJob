<template>
  <el-container style="height: 100%; width: 100%;">
    <el-aside width="22%">
      <div class="search-bar">
        <el-input
          v-model="input"
          placeholder="搜索聊天"
          @input="searchChats"
          :prefix-icon="Search"
        ></el-input>
      </div>
      <el-list class="chat-list">
        <el-list-item
          v-for="chat in filteredChats"
          :key="chat.id"
          @click="selectChat(chat.id)"
        >
          <el-card shadow="hover" class="mr-3 mt-1">
            <el-container>
              <el-aside style="padding: 0px; background-color: white" width="30%">
                <div class="user-avatar">{{ chat.userName.slice(0, 1) }}</div>
              </el-aside>
              <el-main style="padding: 0px;">
                <div class="chat-info">
                  <div class="chat-header">
                    <div style="overflow: hidden; height:20px;max-width:60px">
                      {{ chat.userName }}
                    </div>
                    <div class="chat-date">
                      {{ chat.date }}
                    </div>
                  </div>
                  <div style="overflow: hidden; height:20px">{{ chat.lastMessage }}</div>
                </div>
              </el-main>
            </el-container>
          </el-card>
        </el-list-item>
      </el-list>
    </el-aside>

    <!-- 聊天室 -->
    <el-main>
      <router-view ></router-view>
    </el-main>
  </el-container>
</template>

<script >
import { Search } from "@element-plus/icons-vue";
import { reactive, toRefs, watch, ref, computed } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

export default {
  data() {
    return {
      router:useRouter(),
      Search,
      input: "",
      userChatList: [
        {
          id: 1,
          userId: "111",
          userName: "张张张张张张张张张张",
          date: '2023-04-05',
          lastMessage: "这是张三的最后一条消息",
        },
        {
          id: 2,
          userId: "222",
          userName: "张4",
          date: '2023-02-05',
          lastMessage: "放学别走",
        },
        {
          id: 3,
          userId: "333",
          userName: "张5",
          date: '2023-04-06',
          lastMessage: "吃我闪焰冲锋",
        },
      ],
      filteredChats: [],
      selectedChat: {},
    };
  },
  methods: {
    async getUserChatList() {
      try {
        // Replace the URL with your API endpoint to fetch chats
        const response = await axios.get("http://localhost:9090/chats", {
          params: {
            userId: this.userId,
          },
        });

        if (response.data.code === 200) {
          this.userChatList = response.data.data;
          this.filteredChats = this.userChatList;
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch chat list:", error);
      }
    },
    selectChat(chatId) {
      // alert(chatId)
      this.selectedChat = this.userChatList.find((chat) => chat.id === chatId);
      this.router.replace('')
      this.router.replace("/myspace/chatList/chatRoom/"+chatId)
    },
    searchChats() {
      if (this.input) {
        this.filteredChats = this.userChatList.filter((chat) =>
          chat.userName.toLowerCase().includes(this.input.toLowerCase())
        );
      } else {
        this.filteredChats = this.userChatList;
      }
    },
  },
  mounted() {
    this.filteredChats = this.userChatList;
    //this.getUserChatList();
  },
};
</script>

<style scoped>
.el-aside {
  background-color: #f5f7fa;
}

.search-bar {
  padding: 20px;
}

.chat-list {
  height: calc(100% - 70px);
  overflow-y: auto;
  }
  
  .user-avatar {
  background-color: #409eff;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  color: #fff;
  }
  
  .chat-info {
  display: flex;
  flex-direction: column;
  }
  
  .el-list {
  display: flex;
  flex-direction: column;
  }
  .chat-header{
    display: flex;
    justify-content:space-between;
  }
  .chat-date{
    color: #409eff;
  }
  </style>