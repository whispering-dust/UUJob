<template>
  <section class="py-xl bg-cover bg-size--cover" data-spotlight="fullscreen" style="background-image: url('../assets/images/backgrounds/img-1.jpg')">
    <span class="mask bg-dark alpha-6">      
    </span>
    <div class="container d-flex align-items-center no-padding">
      <div class="col">
        <div class="row justify-content-center">
          <div class="col-lg-6">
            <ul class="nav nav-tabs" id="authTab" role="tablist">
              <li class="nav-item">
                <a class="nav-link active" id="login-tab" data-toggle="tab" href="#login" role="tab" aria-controls="login" aria-selected="true">登录</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" id="register-tab" data-toggle="tab" href="#register" role="tab" aria-controls="register" aria-selected="false">注册</a>
              </li>
            </ul>
            <div class="tab-content" id="authTabContent">
              <div class="tab-pane fade show active" id="login" role="tabpanel" aria-labelledby="login-tab">
                <!-- 登录表单内容 -->
                <LoginForm :loginUser="loginUser" :rules="rules" />  
              </div>
              <div class="tab-pane fade" id="register" role="tabpanel" aria-labelledby="register-tab">
                <!-- 注册表单内容 -->
                <RegisterForm :registerUser="registerUser" :registerRules="registerRules"  @showLoginTab="showLoginTab"/>
              </div>
            </div>
            
            
          </div>
        </div>
      </div>
    </div>
    </section>

</template>

<script>
// 暂时没采用lang='ts'（会改变this造成一系列问题，暂时由于未知原理机制而未解决）
import {ref,getCurrentInstance} from "vue"

//数据模型和校验规则导入
import { loginUser,rules } from "@/utils/loginValidators";
import{registerUser,registerRules}from "@/utils/registerValidators"

//组件输入
import LoginForm from "@/components/LoginForm.vue";
import RegisterForm from "@/components/RegisterForm.vue";

export default{
  name:"LoginRegister",
  components:{LoginForm,RegisterForm},
  data(){
    return{
      loginState:'block',
      registerState:'none'
    }
  },
  methods:{
    showLoginTab() {
      this.$message.success("注册成功")
      const authTab = document.getElementById('authTab');
      const loginTab = document.getElementById('login-tab');
      const loginForm = document.getElementById('login');

      authTab.classList.add('show');
      authTab.classList.remove('hide');
      loginTab.classList.add('active');
      loginForm.classList.add('show', 'active');

      const registerTab = document.getElementById('register-tab');
      const registerForm = document.getElementById('register');

      registerTab.classList.remove('active');
      registerForm.classList.remove('show', 'active');
    },
  },
  setup() {
  // @ts-ignore
    const signUpMode = ref(false);
    return {
      signUpMode,
      loginUser, 
      rules,
      registerUser,
      registerRules,
    };
  },

  

};
// <div class="container" :class="{'sign-up-mode':signUpMode}">

// <div class="forms-container">
//     <div class="signin-signup loginObj" ref="loginComponent" :style="{display:loginState}">

//         <LoginForm :loginUser="loginUser" :rules="rules" />                          
//     </div>  
//     <div class="signin-signup registerObj" ref="registerComponent" :style="{display:registerState}">

//         <RegisterForm :registerUser="registerUser" :registerRules="registerRules"/>
//     </div>
// </div> 
// <!-- 左右切换动画 -->
// <div class="panels-container">
//     <div class="panel left-panel">
//         <div class="content">
//             <button @click="modifyState('register');signUpMode = !signUpMode" class="btn transparent">注册</button>
//         </div>
//         <!-- <img src="@/assets/img/log.svg" class="image" alt="" /> -->
//     </div>

//     <div class="panel right-panel">
//         <div class="content">
//             <button @click="modifyState('login');signUpMode = !signUpMode" class="btn transparent">登录</button>
//         </div>
//         <!-- <img src="@/assets/img/register.svg" class="image" alt="" /> -->
//     </div>
// </div>
// </div>
</script>

<style>



</style>

