<template>
<div class="comment-section">
    <div class="add-comment">
    <el-avatar class="mr-2" src="avatar.png" size="50"></el-avatar>
    <el-input
        class="mr-2"
        type="textarea"
        :rows="2"
        placeholder="添加评论"
        v-model="commentContent"
    ></el-input>
    <el-button type="primary" @click="submitComment">评论</el-button>
    </div>
    <el-card class="comment-list">
    <div class="mb-2"
        v-for="(comment, index) in comments"
        :key="index"
    >
        <div class="comment-item">
            <el-avatar class="mr-2" src="avatar.png" size="50"></el-avatar>
            <div class="comment-right">
                <div style="display: flex; height:20px">
                    <p class="mr-2">{{ comment.userName }}</p>
                    <p style="color: dodgerblue;">{{ comment.timestamp }}</p>               
                </div>
                <div>
                    {{ comment.content }}
                </div>
            </div>
        
        </div>
        <div class="response">
            <div class="response-button">
                <el-button type="text" @click="replyComment(index)">回复</el-button>
            </div>
            <div class="response-area mt-2" v-for="respone in responses">
                {{respone.content}}
            </div>
        </div>
    </div>
    </el-card>
</div>
</template>

<script>
export default {
    name: "CommentSection",
    data() {
        return {
        commentContent: "",
        comments: [
            {
                commentId:1,
                userName: "用户1",
                avatar: "avatar1.png",
                timestamp: "2023-05-06 10:30",
                content: "评论内容1评论内容1评论内容1评论内容1评论内容1"
            },
            {
                commentId:2,
                userName: "用户2",
                avatar: "avatar2.png",
                timestamp: "2023-05-06 10:45",
                content: "评论内容2"
            }
        ],
        responses:[
            {
                commentId:1,
                responseId:1,
                userName: "用户2",
                avatar: "avatar2.png",
                timestamp: "2023-05-06 10:45",
                content: "评论内容2"
            },
            {
                commentId:1,
                responseId:1,
                userName: "用户2",
                avatar: "avatar2.png",
                timestamp: "2023-05-06 10:45",
                content: "评论内容2"
            }
        ]

        };
    },
    methods: {
        submitComment() {
        // 添加评论逻辑
        },
        replyComment(index) {
        // 回复评论逻辑
        }
    }
};
</script>

<style scoped>
.comment-section {
margin-bottom: 16px;
}
.add-comment {
padding: 10px;
background-color: aliceblue;
margin-bottom: 24px;
display: flex;
border-radius: 15px;
align-items: flex-start;
}
.el-input {
margin-bottom: 8px;
}
.comment-list {
margin-top: 24px;
}
.comment-item{
    display: flex;
}
.comment-right{
    display: flex;
    flex-direction: column;
}
.response{
    margin-left: 5%;
    display: flex;
}
.response-button{
    margin-right: 20px;
}
.response-area{
    background-color: rgb(218, 222, 225);
    width: 80%;
    display: flex;
    flex-direction: column;
}
</style>