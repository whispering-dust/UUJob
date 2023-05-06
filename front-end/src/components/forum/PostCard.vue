<template>
<el-card class="postcard">
    <div class="post-header">
    <div style="display:flex;align-items: center;">
        <el-avatar src="avatar.png" size="50"></el-avatar>
    <div class="user-info">
        <div class="user-name mr-2 h5">{{postUser.userName}}</div>
        <div class="user-degree">{{postUser.role}}</div>
        <el-tag class="ml-2" type="success">{{postTag}}</el-tag>
    </div>
    </div>
    <div class="post-time">发布时间：{{postTime}}</div>
    </div>
    <div class="post-info container">  
        <div class="h4">{{postTitle}}</div>
        <div class="post-content">{{postContent}}</div>
    </div>
    <div class="post-actions">
        <el-button link ><font-awesome-icon :icon="['fa', 'thumbs-up']" ></font-awesome-icon></el-button>
        <el-button link  @click="this.userCollected = !this.userCollected"><el-icon size="large" v-if="!userCollected"><Star /></el-icon> <el-icon size="large"  v-else><StarFilled /></el-icon></el-button>
        <el-button link ><el-icon size="large"><Share /></el-icon></el-button>
        <el-button link  ><el-icon size="large"><WarnTriangleFilled /></el-icon></el-button>
    </div>
</el-card>
</template>

<script>
import { useStore } from "vuex";
import { useRoute } from 'vue-router';
import axios from "axios";
export default {
    name: 'PostCard',
    data(){

        return {
            postId: useRoute().params.postId,
            userLike: false,
            userCollected: false,
            postUser:{
                userId: null,
                userName:'用户1',
                role:'招聘者',
            },
            postContent:'帖子内容',
            postTime:'2023-05-06',
            postTag:'技术分享',
            postTitle:'',
        }
    },
    methods: {
        async getPostInfo(){
            try {
                // Replace the URL with your API endpoint to fetch chats
                const response = await axios.get("http://localhost:9090/posts/detail", {
                    params: {
                        id: this.postId,
                    },
                });

                if (response.data.code === 200) {
                    this.postContent = response.data.data.content
                    this.postTime = response.data.data.date
                    this.postTitle = response.data.data.title
                    this.postUser.userId = response.data.data.publisherId

                    switch (response.data.data.type) {
                        case 1:
                            this.postTag = '技术交流'
                            break;
                        case 2:
                            this.postTag = '求职经验'
                            break;
                        case 3:
                            this.postTag = '行业动态'
                            break;
                        default:
                            this.postTag = '其他'
                            break;
                    }
                    this.getPostUser()

                }else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch chat list:", error);
            }
        },
        async getPostUser(){
            try {
                const response = await axios.get("http://localhost:9090/users", {
                params: {
                    id: this.postUser.userId,
                },
                });

                if (response.data.code === 200) {
                    console.log(response.data.data);
                    this.postUser.userName = response.data.data.userName
                    if(response.data.data.role == '1'){
                        this.postUser.role = '招聘者'
                    }else{
                        this.postUser.role = '求职者'
                    }

                } else {
                alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch user:", error);
            }
        }
        
    },
    mounted(){
        this.getPostInfo()
       
        
    }
};
</script>

<style scoped>
.postcard {
margin-bottom: 16px;
}
.post-header {
display: flex;
align-items: center;
justify-content: space-between;
width:100%;
margin-bottom: 12px;
}
.user-info {
    display: flex;
    margin-left: 12px;
}
.post-info {
margin-bottom: 12px;
}
.post-actions {
display: flex;
justify-content: flex-start;
}
.post-content{
    min-height: 100px;
}
</style>
