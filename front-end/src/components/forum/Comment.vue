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
    <h4>评论({{commentList.length}})</h4>
    <div v-if="commentList.length == 0">快来写下你的第一条评论吧🤠</div>
    <div class="mb-2"
        v-for="(comment, index) in commentList"
        :key="index"
    >
        <div class="comment-item">
            <el-avatar class="mr-2" src="avatar.png" size="50"></el-avatar>
            <div class="comment-right">
                <div style="display: flex; height:20px">
                    <p class="mr-2">{{ comment.userName }}</p>
                    <p style="color: dodgerblue;">{{ comment.date }}</p>               
                </div>
                <div>
                    {{ comment.content }}
                </div>
            </div>
        
        </div>
        <div class="response">
            <div class="response-button">
                <el-button type="text" @click="isResponse.id=comment.id">回复</el-button>
            </div>
            <div class="response-area">
                <el-input v-if="isResponse.id==comment.id"
                    class="mr-2"
                    type="textarea"
                    :rows="2"
                    style="width: 80%;"
                    placeholder="添加评论"
                    v-model="responseContent"
                ></el-input>
                <el-button @click="replyComment(comment)" v-if="isResponse.id==comment.id">确认</el-button>
                <div class="response-list" v-if="comment.responses">
                    <div
                    class="response-item"
                    v-for="response in comment.responses"
                    :key="response.responseId"
                    >
                    <el-avatar class="mr-2" src="avatar.png" size="40"></el-avatar>
                    <div class="response-right">
                        <div style="display: flex; height: 20px">
                        <p class="mr-2">{{ response.publisherName }}</p>
                        <p style="color: dodgerblue;">{{ response.date }}</p>
                        </div>
                        <div>
                        {{ response.content }}
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </el-card>
</div>
</template>

<script>
import { useStore } from "vuex";
import axios from "axios";
export default {
    name: "CommentSection",
    props:{
      postId: {
            type: String,
            default: 'null'
      },
    },
    data() {
        return {
        isResponse: {
            id:null
        },
        userId: useStore().state.userId,
        commentContent: "",
        responseContent:"",
        commentList: [
            {
                id:1,
                userName: "用户1",
                avatar: "avatar1.png",
                date: "2023-05-06 10:30",
                content: "评论内容1评论内容1评论内容1评论内容1评论内容1",
                responses: [
                    {
                        responseId: 1,
                        publisherName: "用户2",
                        avatar: "avatar2.png",
                        date: "2023-05-06 10:45",
                        content: "回复评论1"
                    },
                    {
                        responseId: 2,
                        publisherName: "用户3",
                        avatar: "avatar3.png",
                        date: "2023-05-06 11:00",
                        content: "回复评论1-2"
                    }

                ],
            },
            {
                id:2,
                userName: "用户2",
                avatar: "avatar2.png",
                date: "2023-05-06 10:45",
                content: "评论内容2",
                responses: [
                    {
                        responseId: 1,
                        publisherName: "用户1",
                        avatar: "avatar1.png",
                        date: "2023-05-06 10:50",
                        content: "回复评论2"
                    }
                ]
            }
        ],

        };
    },
    methods: {
        async submitComment() {
        // 添加评论逻辑
            try {
                // Replace the URL with your API endpoint to fetch chats
                const response = await axios.post("http://localhost:9090/posts/comments", {
                    publisherId: this.userId,
                    content: this.commentContent,
                    postId: this.postId
                });

                if (response.data.code === 200) {
                    this.$message.success('发布成功')
                    this.commentContent=''
                    this.getCommentList()
                }else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch comment list:", error);
            }
        },
        async replyComment(comment) {
        // 回复评论逻辑
            try {
                // Replace the URL with your API endpoint to fetch chats
                const response = await axios.post("http://localhost:9090/comments/responses", {
                    publisherId: this.userId,
                    content: this.responseContent,
                    commentId: comment.id
                });

                if (response.data.code === 200) {
                    this.$message.success('发布成功')
                    this.commentContent=''
                    this.isResponse.id = null
                    this.getResponsList(comment.id)
                }else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch comment list:", error);
            }
        },
        async getCommentList(){
            try {
                // Replace the URL with your API endpoint to fetch chats
                const response = await axios.get("http://localhost:9090/posts/detail", {
                    params: {
                        id: this.postId,
                    },
                });

                if (response.data.code === 200) {
                    this.commentList = response.data.data.commentList
                    this.commentList.forEach(comment => {
                        this.getResponsList(comment.id)
                    });
                }else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch comment list:", error);
            }
        },
        async getResponsList(commentId){
            try {
                const response = await axios.get("http://localhost:9090/comments/responses", {
                    params: {
                        id: commentId,
                    },
                });

                if (response.data.code === 200) {
                    this.commentList.forEach(comment => {
                        if(comment.id == commentId){
                            comment.responses = response.data.data
                        }
                    });
                    
                }
            } catch (error) {
                console.error("Failed to fetch responcse list:", error);
            }
        },
    },
    mounted() {
        this.getCommentList()
    },
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
.response-list {
    margin-top: 8px;
    background-color: rgb(218, 222, 225);
    border-radius: 10px;
    width: 80%;
    padding: 8px;
  }
  .response-item {
    display: flex;
    margin-bottom: 10px;
  }
  .response-right {
    display: flex;
    flex-direction: column;
  }
  .response-area{
    width: 100%;
  }
</style>