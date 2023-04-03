<template>
    <body class="bg-light main">
        <div class="box">
            <div class="container">
                <!-- 广告位轮播 -->
                <div id="carouselExampleSlidesOnly" class="carousel slide my-carousel pt-1 " data-ride="carousel" style=" height: 250px; overflow: hidden; ">
                   <div class="card bg-dark alpha-container text-white border-0 overflow-hidden">
                       <div class="carousel-inner">
                           <div class="carousel-item active">
                               <img class="d-block w-100" src="../../public/assets/images/prv/img-2.jpg" alt="First slide">
                           </div>
                           <div class="carousel-item">
                               <img class="d-block w-100" src="../../public/assets/images/prv/img-1.jpg" alt="Second slide">
                           </div>
                           <div class="carousel-item">
                               <img class="d-block w-100" src="../../public/assets/images/prv/img-3.jpg" alt="Second slide">
                           </div>
                           <div class="carousel-item">
                               <img class="d-block w-100" src="../../public/assets/images/prv/img-4.jpg" alt="Second slide">
                           </div>
                       </div>
   
                   </div>
   
               </div>
   
               <el-menu
                   :default-active="activeIndex"
                   class="el-menu"
                   mode="horizontal"
                   @select="handleSelect"
               >
                   <el-menu-item index="0">推荐</el-menu-item>
                   <el-menu-item index="1">关注</el-menu-item>
                   <el-menu-item index="2">资讯</el-menu-item>
                   <el-menu-item index="3">课程</el-menu-item>
               </el-menu>
               
               <div class="post-area">
                   <div class="post-list pt-1">
                   <div v-for="post in posts" :key="post.id" class="post-item">
                    <div class="card">
                        <div class="card-header">
                            <div class="row align-items-center">
                                <div class="col-8">
                                    <h4 class="heading h5 mb-0">{{ post.title }}</h4>
                                </div>
                                <div class="col-4">
                                    <div class="card-icon-actions text-right">
                                        <a href="#" class="favorite" data-toggle="tooltip" data-original-title="Save to favorites"><i class="fas fa-star"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <p class="card-text">{{ post.content }}</p>
                        </div>
                        <div class="card-footer">
                            <div class="row align-items-center">
                                <div class="col-6">
                                    <el-button type="primary">详情</el-button>
                                </div>
                                <div class="col-6 text-right">
                                    <span class="text-muted">{{ post.date }}</span>
                                </div>
                            </div>
                        </div>           
                       </div>
                   </div>
                   </div>
               </div>        
           </div>
        </div>
    
    </body>
</template>

<script>
import {ref } from 'vue';
export default {
  data() {

    const activeIndex = ref(0); 
    return {
    
        activeIndex,
        posts: [
            {
            id: 1,
            title: '如何学习编程？',
            date: '2022-10-01',
            content: '学习编程的最好方法是......'
            },
            {
            id: 2,
            title: '如何学好英语？',
            date: '2022-10-02',
            content: '学好英语的秘诀是......'
            },
            {
            id: 3,
            title: '如何学习编程？',
            date: '2022-10-03',
            content: '学习编程的最好方法是......'
            },
            
        ]
    }
  },
  mounted(){

  },
  methods: {
    
  },
}
</script>


<style scoped>
.post-area{
    width: 100%;
}
.main{
    background-color: rgb(200, 137, 137);
    width: 100%;
    height: 100%;
    background-image: url('../../public/assets/images/prv/img-1-800x600.jpg');
}
.box{
    background-color: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(20px);
}
.nav-pills .nav-link.active,
.nav-pills .show>.nav-link {
  color: #fff;
  background-color: #6c757d;
}

.nav-pills .nav-link {
  border-radius: 0.25rem;
}

.nav-pills .nav-link:hover {
  color: #fff;
  background-color: #6c757d;
}

.nav-pills .nav-link.disabled {
  color: #6c757d;
  background-color: #f8f9fa;
}

.nav-pills .nav-item.show .nav-link,
.nav-pills .nav-link.active {
  color: #fff;
  background-color: #6c757d;
  border-color: #6c757d;
}
</style>