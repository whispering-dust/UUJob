<template>
  <div class="chatroom">
    <div class="messages">
      <el-list>
        <el-list-item class="mb-2" v-for="message in messages" :key="message.id" :class="{'my-message': message.isMine, 'their-message': !message.isMine}">
          <img class="avatar" :src="message.avatar" alt="user" />
          <div class="message-content" :class="{'my-bubble': message.isMine, 'their-bubble': !message.isMine}">
            <div v-if="message.type === 'text'">
              {{ message.content }}
            </div>
            <div v-if="message.type === 'image'">
              <img :src="message.content" class="uploaded-image" @click="downloadFile(message.content)" />
            </div>
            <div v-if="message.type === 'file'">
              <a :href="message.content" download @click="downloadFile(message.content)">
                {{ message.fileName }}
              </a>
            </div>
          </div>
        </el-list-item>
      </el-list>
    </div>

    <div class="input-area">
      <div class="button-area mb-1">
        <div>
          <input type="file" ref="fileInput" class="file-input" @change="uploadFile" />
          <el-button size="large" @click="openFilePicker" circle><el-icon size="large"><DocumentAdd /></el-icon></el-button>   
          <el-button size="large" @click="openFilePicker" circle><el-icon size="large"><Picture /></el-icon></el-button>
        </div>
        <el-button style="width: 100px;" color="#009080" @click="sendMessage"><el-icon><Position /></el-icon>发送</el-button>
      </div>
      <textarea style="overflow-y: auto;" v-model="newMessage" placeholder="输入消息..." @keydown.enter="sendMessage"></textarea>
    </div>
  </div>
</template>

<script>
import { useRoute } from 'vue-router';

export default {
  data() {
    return {
      targetUserId: useRoute().params.userId,
      messages: [
        // 示例聊天记录
        {
          id: '111',
          content: 'Hi, how are you?Hi, how are you?Hi, how are you?Hi, how are you?Hi, how are you?Hi, how are you?',
          type: 'text',
          isMine: false,
          avatar: 'https://via.placeholder.com/40',
        },
        {
          id: '111',
          content: 'Hi, how are you?Hi, how are you?Hi, how are you?Hi, how are you?Hi, how are you?Hi, how are you?',
          type: 'text',
          isMine: false,
          avatar: 'https://via.placeholder.com/40',
        },
        {
          id: '112',
          content: 'I\'m good, thanks!',
          type: 'text',
          isMine: true,
          avatar: 'https://via.placeholder.com/40',
        },
        {
          id: '112',
          content: 'I\'m good, thanks!',
          type: 'text',
          isMine: true,
          avatar: 'https://via.placeholder.com/40',
        },
      ],
      newMessage: '',
      fileInput: null,
    };
  },
  methods: {
    sendMessage() {
      // TODO: 发送消息到服务器
      this.newMessage = '';
    },
    openFilePicker() {
      this.$refs.fileInput.click();
    },
    uploadFile(event) {
      // TODO: 上传文件到服务器
      // 模拟上传成功后，将文件/图片添加到聊天记录
      this.messages.push({
        id: Date.now(),
        type: 'file',
        content: URL.createObjectURL(event.target.files[0]),
        fileName: event.target.files[0].name,
        isMine: true,
      });
      event.target.value = null;
    },
    downloadFile(url) {
      // TODO: 下载文件
    },
  },
  watch: {
    '$route.params.userId': {
      immediate: true,
      handler(userId) {
        this.targetUserId = userId;
        alert(userId)
      },
    },},
  mounted(){
    
    },
};
</script>

<style scoped>
.chatroom {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.messages {
  overflow: auto;
  height: 50%;
  padding: 1rem;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.el-list-item {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 1rem;
}

.my-message .avatar{
  float: right;
  margin-left: 20px;
}
.my-message {
  width: 100%;
  float: left;
}
.their-message{
  width: 100%;
  display: flex;
  justify-content: flex-start;
}
.their-message .avatar{
  margin-right: 20px;
}

.their-message .message-content {
  max-width: 40%;
  border-radius: 5px;
  padding: 0.5rem;
  position: relative;
}

.my-message .message-content {
  max-width: 40%;
  border-radius: 5px;
  padding: 0.5rem;
  position: relative;
  float: right;
}

.my-bubble {
  background-color: teal;
  color: #fff;
  border: 1px solid teal;
  border-radius: 5px 5px 0 5px;
  margin-right: 0px;
}

.my-bubble::after {
  content: '';
  position: absolute;
  width: 0;
  height: 0;
  top: 13px;
  right: -10px;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-left: 10px solid teal;
}

.their-bubble {
  background-color: #fff;
  color: #333;
  border: 1px solid teal;
  border-radius: 5px 5px 5px 0;
}

.their-bubble::before {
  content: '';
  position: absolute;
  width: 0;
  height: 0;
  top: 13px;
  left: -15px;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-right: 15px solid teal;
}

.their-bubble::after {
  content: '';
  position: absolute;
  width: 0;
  height: 0;
  top: 13px;
  left: -13px;
  border-top: 10px solid transparent;
  border-bottom: 10px solid transparent;
  border-right: 15px solid #fff;
}

.uploaded-image {
  max-width: 200px;
  cursor: pointer;
}

.input-area {
  height: 50%;
  padding: 1rem;
}

.file-input {
  display: none;
}

.button-area {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

textarea {
  width: 100%;
  margin-right: 1rem;
  outline: none;
  height: 80%;
}
</style>