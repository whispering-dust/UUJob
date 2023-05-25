<template>
    <el-card class="password-card">
      <el-form :model="passwordForm" label-width="100px" ref="passwordForm" @submit.native.prevent="submitForm">
        <el-form-item label="当前密码" prop="currentPassword" :rules="[{ required: true, message: '请输入当前密码' }]">
          <el-input type="password" v-model="passwordForm.currentPassword" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword" :rules="passwordRules">
          <el-input type="password" v-model="passwordForm.newPassword" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmNewPassword" :rules="confirmPasswordRules">
          <el-input type="password" v-model="passwordForm.confirmNewPassword" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </template>
  
  <script>
  export default {
    name: 'Password',
    data() {
      const validatePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'));
        } else if (value.length < 8) {
          callback(new Error('密码长度不能小于8位'));
        } else {
          if (this.passwordForm.confirmNewPassword !== '') {
            this.$refs.passwordForm.validateField('confirmNewPassword');
          }
          callback();
        }
      };
      const validateConfirmPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入新密码'));
        } else if (value !== this.passwordForm.newPassword) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      };
      return {
        passwordForm: {
          currentPassword: '',
          newPassword: '',
          confirmNewPassword: '',
        },
        passwordRules: [{ validator: validatePassword, trigger: 'blur' }],
        confirmPasswordRules: [{ validator: validateConfirmPassword, trigger: 'blur' }],
      };
    }, 
    methods: {
      submitForm() {
        this.$refs.passwordForm.validate(async (valid) => {
          if (valid) {
            // 提交表单数据到后端
            console.log('表单数据: ', this.passwordForm);
  
            // 提交成功后，清空表单数据
            this.passwordForm.currentPassword = '';
            this.passwordForm.newPassword = '';
            this.passwordForm.confirmNewPassword = '';
  
            this.$message.success('密码修改成功');
          } else {
            console.log('表单验证失败');
            return false;
          }
        });
      },
    },
  };
  </script>
  
  <style scoped>
  .password-card {
    width: 600px;
    margin: 0 auto;
    margin-top: 100px;
    height: 300px;
  }
  </style>
  