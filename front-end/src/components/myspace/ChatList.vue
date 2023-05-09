<template>
  <el-container style="height: 100%; width: 100%;">
    <el-aside width="22%">
      <div class="search-bar">
        <el-input v-model="input" placeholder="搜索聊天" @input="searchChats" :prefix-icon="Search"></el-input>
      </div>
      <el-list class="chat-list">
        <el-list-item v-for="chat in filteredChats" :key="chat.id" @click="selectChat(chat.id)">
          <el-card shadow="hover" class="mr-3 mt-1">
            <el-container>
              <el-aside style="padding: 0px; background-color: white" width="30%">
                <div class="user-avatar">{{ chat.contactName.slice(0, 1) }}</div>
              </el-aside>
              <el-main style="padding: 0px;">
                <div class="chat-info">
                  <div class="chat-header">
                    <div style="overflow: hidden; height:20px;width:80px">
                      {{ chat.contactName }}
                    </div>
                    <div class="chat-date">
                      {{ chat.lastMessageTime }}
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
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script >
import { Search } from "@element-plus/icons-vue";
import { reactive, toRefs, watch, ref, computed } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  data() {
    return {
      router: useRouter(),
      Search,
      input: "",
      userId: useStore().state.userId,
      userChatList: [
        {
          id: 1,
          contactId: "111",
          contactName: "张张张张张张张张张张",
          lastMessageTime: '2023-04-05',
          lastMessage: "这是张三的最后一条消息",
          isRead: false
        },
        {
          id: 2,
          contactId: "222",
          contactName: "张4",
          lastMessageTime: '2023-02-05',
          lastMessage: "放学别走",
          isRead: false
        },
        {
          id: 3,
          contactId: "333",
          contactName: "张5",
          lastMessageTime: '2023-04-06',
          lastMessage: "吃我闪焰冲锋",
          isRead: true
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
        const response = await axios.get("http://localhost:9090/conversations/basis", {
          params: {
            userId: this.userId,
          },
        });
        if (response.data.code === 200) {
          this.userChatList = response.data.data;
          console.log('接口返回后的数据', this.userChatList)
          for (let i in this.userChatList) {
            var date = new Date(this.userChatList[i].lastMessageTime);
            this.userChatList[i].lastMessageTime = date.getFullYear() + "-" + (date.getMonth() + 1).toString().padStart(2, '0') + "-" + date.getDate().toString().padStart(2, '0');
          }

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
      this.router.replace("/myspace/chatList/chatRoom/" + chatId+"/"+this.selectedChat.contactId)
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
    //console.log("当前用户id是", this.userId);
    this.filteredChats = this.userChatList;
    this.getUserChatList();
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

.chat-header {
  display: flex;
  justify-content: space-between;
}

.chat-date {
  color: #409eff;
}
</style>