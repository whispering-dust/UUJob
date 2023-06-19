<template>
    <div class="post-header">
        <div style="display:flex;align-items: center;">
            <el-avatar src="avatar.png" :size="50"></el-avatar>
            <div class="user-info">
                <div class="user-name mr-2 h5">{{ postUser.userName }}</div>
                <div class="user-degree">{{ postUser.role }}</div>
                <el-tag class="ml-2" type="success">{{ postTag }}</el-tag>
            </div>
        </div>
        <div class="post-time">发布时间：{{ postTime }}</div>
    </div>
    <div class="post-info container">
        <div class="h4">{{ postTitle }}</div>
        <div class="post-content" v-html="formattedPostText"></div>
    </div>
    <div class="post-actions" v-if="flag == true">
        <el-button link @click="changeStarStatus">
            <el-icon size="large" v-if="!isStar">
                <Star />
            </el-icon> <el-icon size="large" v-else>
                <StarFilled />
            </el-icon></el-button>
        <el-button link>
            <el-icon size="large">
                <Share />
            </el-icon>
        </el-button>
        <el-button link @click="reportDialogTableVisible = !reportDialogTableVisible">
            <el-icon size="large">
                <WarnTriangleFilled />
            </el-icon>
        </el-button>
    </div>

    <el-dialog v-model="reportDialogTableVisible" title="举报" width="600px">
        <el-input v-model="reportContent" :rows="4" type="textarea" placeholder="Please input" class="mb-3" />
        <el-button @click="submitReport" type="primary">提交</el-button>
    </el-dialog>
</template>

<script>
import { useStore } from "vuex";
import { ElMessage } from 'element-plus'
import axios from "axios";
export default {
    name: 'PostCard',
    props: {
        targetPostId: {
            type: Number,
            default: 'null'
        },
        flag: {
            type: Boolean,
            default: true
        }
    },
    data() {
        const successStar = () => {
            ElMessage({
                message: '收藏成功！',
                type: 'success',
            })
        }

        const failStar = () => {
            ElMessage.error('收藏失败')
        }

        const successDeleteStar = () => {
            ElMessage({
                message: '取消收藏成功！',
                type: 'success',
            })
        }

        const failDeleteStar = () => {
            ElMessage.error('取消收藏失败')
        }
        return {
            successStar,
            failStar,
            successDeleteStar,
            failDeleteStar,
            reportContent: "",
            reportDialogTableVisible: false,
            store: useStore(),
            userLike: false,
            userCollected: false,
            postUser: {
                userId: null,
                userName: '用户1',
                role: '招聘者',
            },
            postContent: '帖子内容',
            postTime: '2023-05-06',
            postTag: '技术分享',
            postTitle: '',
            starId: -1,
            isStar: false,
        }
    },
    computed: {
        formattedPostText() {
            return this.postContent.replace(/\n/g, "<br>");
        },
    },
    methods: {
        changeStarStatus() {
            if (this.isStar) {
                this.deleteStar();
            }
            else {
                this.addStar();
            }
        },
        addStar() {
            let that = this;
            console.log("添加收藏！", 'post:', that.targetPostId, 'user:', that.store.state.userId)
            axios({
                method: "post",
                url: "http://localhost:9090/stars/posts",
                data: {
                    targetId: that.targetPostId,
                    userId: that.store.state.userId
                }
            }).then(function (response) {
                if (response.data.code == 200) {
                    console.log(response.data.data);
                    that.successStar();
                    that.isStar = !that.isStar;
                } else {
                    alert("error");
                    that.failStar();
                }
            })
        },
        getStarStatus() {
            let that = this;
            axios({
                method: "post",
                url: "http://localhost:9090/stars",
                data: {
                    userId: that.store.state.userId,
                    targetId: that.targetPostId,
                    starType: 1
                },
            }).then(function (response) {
                if (response.data.code == 200) {
                    var data = response.data.data
                    that.starId = data;
                    console.log('收藏Id', that.starId)
                    if (that.starId != -1) {
                        that.isStar = true;
                    }
                    else {
                        that.isStar = false;
                    }
                } else {
                    alert("error");
                }

            })
        },
        deleteStar() {
            let that = this;
            axios({
                method: "delete",
                url: "http://localhost:9090/stars",
                params: {
                    id: that.starId,
                }
            }).then(function (response) {
                if (response.data.code == 200) {

                    that.successDeleteStar();
                    that.isStar = !that.isStar;
                } else {
                    alert("error");
                    that.failDeleteStar();
                }
            })
        },
        async submitReport() {
            try {
                // Replace the URL with your API endpoint to fetch chats
                const response = await axios.post("http://localhost:9090/reports/jobs", {
                    content: this.reportContent,
                    reporterId: this.store.state.userId,
                    targetId: this.postUser.userId
                });

                if (response.data.code === 200) {
                    this.$message.success('举办成功');
                    this.reportDialogTableVisible = !this.reportDialogTableVisible;
                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch chat list:", error);
            }
        },
        async getPostInfo() {
            try {
                // Replace the URL with your API endpoint to fetch chats
                const response = await axios.get("http://localhost:9090/posts/detail", {
                    params: {
                        id: this.targetPostId,
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

                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch chat list:", error);
            }
        },
        async getPostUser() {
            try {
                const response = await axios.get("http://localhost:9090/users", {
                    params: {
                        id: this.postUser.userId,
                    },
                });

                if (response.data.code === 200) {
                    console.log(response.data.data);
                    this.postUser.userName = response.data.data.userName
                    if (response.data.data.role == '1') {
                        this.postUser.role = '招聘者'
                    } else {
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
    mounted() {
        this.getPostInfo()
        this.getStarStatus()
        //alert('postCard'+this.targetPostId)

    },
    watch: {
        targetPostId(newPostId, oldPostId) {
            // Only fetch new post if the ID actually changes
            if (newPostId !== oldPostId) {
                this.getPostInfo();
            }
        },
    },
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
    width: 100%;
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

.post-content {
    min-height: 100px;
}
</style>
