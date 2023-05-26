<template>
  <el-scrollbar>
    <div class="main">
      <!-- :style="GetWindowHeight" -->
      <!-- 其他页 -->
      <myHeader v-if="$route.meta.keepAlive"></myHeader>
      <!-- 登录页 -->
      <div class="router-view" style=" height: 90%">
        <router-view ></router-view>
      </div>
     
  
     </div>
     <footer class="pt-5 pb-3 footer  footer-dark bg-tertiary" >
      <div class="text-center">
          &copy; 2023 求职交流平台. All rights reserved.
      </div>
    </footer>
  </el-scrollbar>
</template>

<script>

import  myHeader  from "@/components/Header.vue";
export default {
  name: 'App',
  components: 
  {
    myHeader
  },
  data () {
    // 这里存放数据
    return {
      GetWindowHeight: {
        height: ''
      }
    }
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created () {
    window.addEventListener('resize', this.getHeight) // 注册监听器
    this.getHeight() // 页面创建时先调用一次
  },
  // 生命周期 - 销毁完成
  destroyed () {
    window.removeEventListener('resize', this.getHeight)
  }, 
  // 方法集合
  methods: {
    getHeight () {
      // 获取浏览器高度，减去顶部导航栏的值70（可动态获取）,再减去head-DIV高度值80
      this.GetWindowHeight.height = window.innerHeight - 70 + 'px'
    }
  }

}
</script>

<style scoped>
.main{
  min-height: 800px;
}
</style>
