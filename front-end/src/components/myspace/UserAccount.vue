<template>
    <el-card class="account-card">
      <el-form :model="accountForm" label-width="100px" ref="accountForm">
        <el-form-item label="头像">
          <div class="avatar-container">
            <img :src="accountForm.avatar" alt="avatar" class="avatar" />
            <input type="file" @change="uploadAvatar" class="avatar-upload" />
          </div>
        </el-form-item>
        <el-form-item label="账号" >
          <el-input v-model="accountForm.account" :disabled="true" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="accountForm.nickname" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="accountForm.email" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="等级" >
          <el-input v-model="accountForm.level" :disabled="true" style="width: 50px;"></el-input>
        </el-form-item>
        <el-form-item label="信用状态">
          <el-input v-model="accountForm.creditStatus" :disabled="true" style="width: 100px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </template>
  
  <script>
  export default {
    name: 'userAccount',
    data() {
      return {
        accountForm: {
          avatar: 'https://example.com/default-avatar.jpg',
          account: 'user123',
          nickname: '张三',
          email: 'zhangsan@example.com',
          level: 1,
          creditStatus: '良好',
        },
      };
    },
    methods: {
      async uploadAvatar(e) {
        const file = e.target.files[0];
        if (!file) return;
        const formData = new FormData();
        formData.append('file', file);
  
        try {
          // 请将下面的 URL 替换为您的后端上传接口
          const response = await this.$http.post('https://example.com/upload', formData);
          this.accountForm.avatar = response.data.url;
          this.$message.success('头像上传成功');
        } catch (error) {
          this.$message.error('头像上传失败，请稍后重试');
        }
      },
      submitForm() {
        // 提交表单数据到后端
        console.log('表单数据: ', this.accountForm);
      },
    },
    mounted() {
        
    },
  };
  </script>
  
  <style scoped>
  .account-card {
    width: 700px;
    margin: 100px auto;
  }
  
  .avatar-container {
    position: relative;
    display: inline-block;
  }
  
  .avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }
  
  .avatar-upload {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
  }
  </style>
  