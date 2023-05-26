<template>
    <body class="bg-light main">
        <div class="box">
            <div class="container">
                <!-- 广告位轮播 -->
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel"
                    style=" height: 250px; overflow: hidden; ">
                    <ol class="carousel-indicators">
                        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img class="d-block w-100" src="../../public/assets/images/prv/img-2.jpg" alt="First slide">
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="../../public/assets/images/prv/img-1.jpg" alt="Second slide">
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="../../public/assets/images/prv/img-3.jpg" alt="Third slide">
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>

                <el-menu :default-active="activeIndex" class="el-menu" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="0">推荐</el-menu-item>
                    <el-menu-item index="1">关注</el-menu-item>
                    <el-menu-item index="2">资讯</el-menu-item>
                    <el-menu-item index="3">课程</el-menu-item>
                </el-menu>

                <div class="post-area row">
                    <div class="post-list pt-1 col-8 pl-0">
                        <div v-for="post in posts" :key="post.id" class="post-item">
                            <el-card shadow="hover" class="card mb-2">
                                <div class="card-header p-2">
                                    <div class="row align-items-center">
                                        <div class="col-8">
                                            <h4 class="heading h5 ml-2 mb-0 ">{{ post.title }}</h4>
                                        </div>
                                        <div class="col-4">
                                            <div class="card-icon-actions text-right">
                                                <a href="#" class="favorite" data-toggle="tooltip"
                                                    data-original-title="Save to favorites"><i class="fas fa-star"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <p class="card-text" v-html="this.formattedPostText(post.content)"></p>
                                </div>
                                <div class="card-footer">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <el-button @click="enterPost(post.id)" color="black" 
                                                style="width: 100px;">详情</el-button>
                                        </div>
                                        <div class="col-6 text-right">
                                            <ul class="list-inline mb-0">
                                                <li class="list-inline-item pr-2">
                                                    <el-icon>
                                                        <View />
                                                    </el-icon> 750
                                                </li>
                                                <li class="list-inline-item ">
                                                    <span class="text-muted">{{ post.date }}</span>
                                                </li>
                                            </ul>

                                        </div>
                                    </div>
                                </div>
                            </el-card>
                        </div>
                    </div>

                    <div class="post-right pt-1 col-4 pr-0">
                        <div class="card funtion">
                            <div class="card-header">
                                <h4>功能面板</h4>
                            </div>
                            <div class="card-body">
                                <el-tooltip class="box-item" effect="dark" content="发布帖子" placement="top">
                                    <router-link to="/forum/publishPost">
                                        <el-button circle size="large">
                                            <el-icon>
                                                <document />
                                            </el-icon>
                                        </el-button>
                                    </router-link>
                                </el-tooltip>
                            </div>
                        </div>

                        <div class="card funtion">
                            <div class="card-header">
                                <h4>热点</h4>
                            </div>
                            <div class="card-body">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</template>

<script>
import { ref } from 'vue';
import {
    Document,
    Menu as IconMenu,
    User,
    Setting,
    View,
} from '@element-plus/icons-vue';
import axios from "axios";

export default {
    data() {

        const activeIndex = ref("0");
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
    computed: {

    },
    methods: {
        async getPosts() {
            let that = this;
            //首先从user表里面获取部分信息
            axios({
                method: "get",
                url: "http://localhost:9090/posts/basis",
                params: {

                }
                // data: {
                //     //参数自己接
                //     id: that.useStore().state.userId,
                // },
            }).then(function (response) {
                if (response.data.code === 200) {
                    that.posts = [];
                    response.data.data.forEach(element => {
                        that.posts.push(
                            {
                                id: element.id,
                                title: element.title,
                                date: element.date,
                                content: element.content,
                            }
                        )
                    });


                    console.log(response);
                }
                else {
                    alert(response.data.msg);
                    console.log(response);

                }
            });



        },
        formattedPostText(content) {
            var text = content.replace(/\n/g, " ");
            text = text.replace(/<[^>]*>/g, " ");//去除文字的<...></...>标签
            text = text.slice(0, 50) + "...";
            return text;
        },
        enterPost(id) {
            this.$router.push('/post/' + id)
        },
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        }
    },
    mounted() {
        this.getPosts();

    },
}
</script>


<style scoped>
.post-area {
    width: 100%;
    margin: 0px;
}

.post-list {
    width: 100%;
}

.funtion {
    margin-bottom: 10px;
}

.main {
    background-color: rgb(200, 137, 137);
    width: 100%;
    height: 100%;

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