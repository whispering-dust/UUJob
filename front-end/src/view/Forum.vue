<template>
    <body class="bg-light">
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
                    <p class="card-header">{{ post.date }}</p>
                    <div class="card-body bg-white">
                        <h5 class="card-title">{{ post.title }}</h5>
                        <p class="card-text">{{ post.content }}</p>
                        <el-button type="primary">详情</el-button>
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
            }
        ]
    }
  }
}
</script>


<style>
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