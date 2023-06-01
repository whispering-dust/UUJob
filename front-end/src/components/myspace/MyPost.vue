<template>
  <el-tabs v-model="activeName" class="demo-tabs" style="height: 100%; width: 100%;">
    <el-tab-pane name="posts">
      <template #label>
        <span class="custom-tabs-label mt-0">
          <el-icon>
            <Document />
          </el-icon>
          <span>我的帖子</span>
        </span>
      </template>

      <el-container style="height: 100%; width: 100%;">
        <el-aside width="22%">
          <el-scrollbar>
            <div class="search-bar">
              <el-input v-model="input" placeholder="搜索帖子" @input="searchPosts" prefix-icon="Search"></el-input>
            </div>
            <el-scrollbar height="700px">
              <div class="post-list" v-for="post in filteredPosts" :key="post.id" @click="selectPost(post.id)">
                <el-card shadow="hover" class="mr-3 mt-1">
                  <el-container>
                    <el-aside style="padding: 0px; background-color: white" width="30%">
                      <div class="user-avatar">{{ post.title.slice(0, 1) }}</div>
                    </el-aside>
                    <el-main style="padding: 0px;">
                      <div class="post-info">
                        <div class="post-header">
                          <div style="overflow: hidden; height:20px;width:80px">
                            {{ post.title }}
                          </div>
                          <div class="post-date">
                            {{ post.date }}
                          </div>
                        </div>
                        <div style="overflow: hidden; height:20px">{{ post.content.slice(0, 10) }}</div>
                      </div>
                    </el-main>
                  </el-container>
                </el-card>
              </div>
            </el-scrollbar>

          </el-scrollbar>
        </el-aside>

        <!-- 帖子详情 -->
        <el-main>
          <el-card class="postcard mb-5" style="min-height: 500px;" v-if="selectedPost && selectedPost.postId">
            <PostCard :targetPostId="selectedPost.postId" :flag="false"></PostCard>
          </el-card>
        </el-main>
        <el-aside width="10%">
          <div class="action-buttons">
            <el-button class="ml-2 mt-2" style="width: 75%;" type="danger" @click="deletePost"><el-icon class="mr-1">
                <DeleteFilled />
              </el-icon>删除帖子</el-button>
          </div>
        </el-aside>
      </el-container>
    </el-tab-pane>

    <el-tab-pane name="comments">
      <template #label>
        <span class="custom-tabs-label">
          <el-icon>
            <ChatLineSquare />
          </el-icon>
          <span>我的评论</span>
        </span>
      </template>
      <!-- 评论组件 -->
      <div class="container pt-3" style="width: 80%;">
        <el-scrollbar height="700px">
          <div class="comments p-2 mb-2" v-for="mycomment in myComments">
            <div class="comment-top p-0 m-0">
              <div class="comment-top-left">
                <p class="pr-3" style="display: flex;">
                  <strong class="pr-2">用户名:</strong>{{ mycomment.userName }}
                </p>
                <p>
                  <strong class="pr-2">回复数</strong>({{ mycomment.responseNum }})
                </p>
              </div>
              <div class="comment-top-right">
                发布时间：{{ mycomment.date }}
              </div>
            </div>
            <div class="comment-content p-0 mt-2">
              <p class="pr-3">评论内容:</p>
              {{ mycomment.content }}
            </div>
            <el-divider />
          </div>
        </el-scrollbar>
      </div>
    </el-tab-pane>
    <el-tab-pane name="responses">
      <template #label>
        <span class="custom-tabs-label">
          <el-icon>
            <ChatDotSquare />
          </el-icon>
          <span>我的回复</span>
        </span>
      </template>
      <!-- 回复组件 -->
      <div class="container pt-3" style="width: 80%;">
        <el-scrollbar height="700px">
          <div class="responses p-2 mb-2" v-for="myResponse in myResponses">
            <div class="response-top p-0 m-0">
              <div class="response-top-left">
                <p class="pr-3" style="display: flex;">
                  <strong class="pr-2">用户名:</strong>{{ myResponse.publisherName }}
                </p>
                <p>
                  <strong class="pr-2">@</strong>{{ myResponse.targetUserName }}
                </p>
              </div>
              <div class="response-top-right">
                发布时间：{{ myResponse.date }}
              </div>
            </div>
            <div class="response-content p-0 mt-2">
              <p class="pr-3">评论内容:</p>
              {{ myResponse.content }}
            </div>
            <el-divider />
          </div>
        </el-scrollbar>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { Search } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import { useRouter } from "vue-router";
import PostCard from '@/components/forum/PostCard.vue'

export default {
  components: {
    PostCard,
  },
  data() {
    return {
      Search,
      input: "",
      userId: useStore().state.userId,
      userPosts: [
        {
          id: 1,
          authorId: "111",
          authorName: "张三",
          date: '2023-04-05',
          content: "这是张三的第一篇帖子cZcZc",
          title: "究极大爆炸"
        },
      ],
      filteredPosts: [
        {
          id: 1,
          authorId: "111",
          authorName: "张三",
          date: '2023-04-05',
          content: "这是张三的第一篇帖子cZcZc",
          title: "究极大爆炸"
        },
      ],
      selectedPost: {
        postId: null,
      },
      activeName: "posts",
      myComments: [
        {
          userName: "username",
          content: "content",
          date: "2020-2-3",
          responseNum: 2
        }
      ],
      myResponses: [],
    };
  },
  methods: {
    async getUserPosts() {
      try {
        // Replace the URL with your API endpoint to fetch posts
        const response = await axios.get("http://localhost:9090/users/posts", {
          params: {
            userId: this.userId, // assuming userId is available in `this`
          },
        });

        if (response.data.code === 200) {
          this.userPosts = [];
          console.log(response.data.data);
          const posts = response.data.data;
          for (let i in posts) {
            if (posts[i].status == 1) {
              this.userPosts.push(posts[i])
            }
          }
          for (let i in this.userPosts) {
            var date = new Date(this.userPosts[i].date);
            this.userPosts[i].date = date.getFullYear() + "-" + (date.getMonth() + 1).toString().padStart(2, '0') + "-" + date.getDate().toString().padStart(2, '0');
          }
          for (let i in this.userPosts) {
            // 使用正则表达式去除HTML标签
            this.userPosts[i].content = this.userPosts[i].content.replace(/<[^>]+>/g, '');
          }

          this.filteredPosts = this.userPosts;
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch user posts:", error);
      }
    },
    selectPost(postId) {
      this.selectedPost.postId = postId
    },
    searchPosts() {
      if (this.input) {
        this.filteredPosts = this.userPosts.filter((post) =>
          post.title.toLowerCase().includes(this.input.toLowerCase())
        );
      } else {
        this.filteredPosts = this.userPosts;
      }
    },
    async deletePost() {
      if (this.selectedPost.postId == null) {
        alert("请先选择要删除的帖子");
        return;
      }

      try {
        // Replace the URL with your API endpoint to delete a post
        const response = await axios.delete(`http://localhost:9090/posts`, {
          params: {
            postId: this.selectedPost.postId
          }
        });

        if (response.data.code === 200) {
          this.userPosts = this.userPosts.filter(post => post.id !== this.selectedPost.postId);
          this.searchPosts();
          this.selectedPost.postId = null;
          alert("帖子已成功删除");
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to delete the post:", error);
      }
    },
    async getMyComments() {
      try {
        // Replace the URL with your API endpoint to delete a post
        const response = await axios.get(`http://localhost:9090/users/comments`, {
          params: {
            userId: this.userId
          }
        });

        if (response.data.code === 200) {
          this.myComments = response.data.data
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to delete the post:", error);
      }
    },
    async getMyResponses() {
      try {
        // Replace the URL with your API endpoint to delete a post
        const response = await axios.get(`http://localhost:9090/users/responses`, {
          params: {
            userId: this.userId
          }
        });

        if (response.data.code === 200) {
          this.myResponses = response.data.data
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to delete the post:", error);
      }
    }
  },
  mounted() {
    this.getUserPosts();
    this.getMyComments();
    this.getMyResponses();
  },
};
</script>


<style scoped>
.el-aside {
  background-color: #f5f7fa;
}

.search-bar {
  padding: 20px;
}


.post-list {
  height: calc(100% - 70px);
  overflow-y: auto;
}

.user-avatar {
  background-color: #2f2d2d;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  color: #fff;
}

.post-info {
  display: flex;
  flex-direction: column;
}

.el-list {
  display: flex;
  flex-direction: column;
}

.post-header {
  display: flex;
  justify-content: space-between;
}

.post-date {
  color: #409eff;
  font-size: small;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
  background-color: rgb(66, 74, 92);
  height: 100%;
  padding-top: 50px;
}

.demo-tabs>.el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

::v-deep .el-tabs__header {
  margin: 0 !important;
}

.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}

.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}

.comments {
  width: 100%;
}

.comment-top {
  display: flex;
  justify-content: space-between;
}

.comment-top-left {
  display: flex;
  text-align: center;
}

.comment-top-right {
  display: flex;
  margin-right: 20px;
}

.comment-content {
  display: flex;
}

.responses {
  width: 100%;
}

.response-top {
  display: flex;
  justify-content: space-between;
}

.response-top-left {
  display: flex;
  text-align: center;
}

.response-top-right {
  display: flex;
  margin-right: 20px;
}

.response-content {
  display: flex;
}
</style>
