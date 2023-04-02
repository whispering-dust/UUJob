<template>
  <div id="app">
    <!-- 其他页 -->
    <myHeader v-if="$route.meta.keepAlive"></myHeader>
    <!-- 登录页 -->
    <router-view ></router-view>
   </div>
   <footer class="pt-5 pb-3 footer  footer-dark bg-tertiary">
    <div class="text-center">
        &copy; 2023 求职交流平台. All rights reserved.
    </div>
  </footer>
</template>

<script>

import  myHeader  from "@/components/Header.vue";
export default {
  name: 'App',
  components: 
  {
    myHeader
  }
}
</script>

<style>
</style>
