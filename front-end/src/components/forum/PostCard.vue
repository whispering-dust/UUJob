<template>
<el-card class="postcard">
    <div class="post-header">
    <div style="display:flex;align-items: center;">
        <el-avatar src="avatar.png" size="50"></el-avatar>
    <div class="user-info">
        <div class="user-name mr-2">{{postUser.userName}}</div>
        <div class="user-degree">{{postUser.education}}</div>
    </div>
    </div>
    <div class="post-time">发布时间：{{postTime}}</div>
    </div>
    <div class="post-info">  
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
export default {
    name: 'PostCard',
    data(){

        return {
            userLike: false,
            userCollected: false,
            postUser:{
                userName:'用户1',
                education:'本科',
            },
            postContent:'帖子内容',
            postTime:'2023-05-06',
        }
    },
    methods: {
        
    },
    mounted(){
        
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
</style>
