<template>
  <el-container style="height: 100%; width: 100%;">
    <el-aside width="22%">
      <el-scrollbar>
        <div class="search-bar">
          <el-input v-model="input" placeholder="搜索帖子" @input="searchPosts" :prefix-icon="Search"></el-input>
        </div>
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
                  <div style="overflow: hidden; height:20px">{{ post.content.slice(0, 10)}}</div>
                </div>
              </el-main>
            </el-container>
          </el-card>
        </div>
      </el-scrollbar>
    </el-aside>

    <!-- 帖子详情 -->
    <el-main>
      <!-- 需要自行添加帖子详情的内容 -->
      <el-card class="postcard mb-5" v-if="selectedPost && selectedPost.postId">
        <PostCard :targetPostId="selectedPost.postId" :flag="false"></PostCard>
    </el-card>
    </el-main>
    <el-aside width="10%">
        <div class="action-buttons">
          <el-button class="ml-2 mt-2" style="width: 75%;" type="danger" @click="deletePost"><el-icon class="mr-1"><DeleteFilled /></el-icon>删除帖子</el-button>
        </div>
      </el-aside>
  </el-container>
</template>

<script>
import { Search } from "@element-plus/icons-vue";
import { ref } from "vue";
import{useStore} from "vuex";
import axios from "axios";
import { useRouter } from "vue-router";
import PostCard from '@/components/forum/PostCard.vue'

export default {
    components:{
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
        {
            id: 2,
            authorId: "222",
            authorName: "李四",
            date: '2023-02-05',
            content: "这是李四的第一篇帖子",
            title: "还没爆炸"
        },
        {
            id: 3,
            authorId: "333",
            authorName: "王五",
            date: '2023-04-06',
            content: "说个屁屁",
            title: "java经验分享"
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
        {
            id: 2,
            authorId: "222",
            authorName: "李四",
            date: '2023-02-05',
            content: "这是李四的第一篇帖子",
            title: "还没爆炸"
        },
        {
            id: 3,
            authorId: "333",
            authorName: "王五",
            date: '2023-04-06',
            content: "说个屁屁",
            title: "java经验分享"
        },
      ],
      selectedPost:{
        postId: null,
      },
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
          console.log(response.data.data);
          this.userPosts = response.data.data.PostList;
          for (let i in this.userPosts) {
            var date = new Date(this.userPosts[i].date);
            this.userPosts[i].date = date.getFullYear() + "-" + (date.getMonth() + 1).toString().padStart(2, '0') + "-" + date.getDate().toString().padStart(2, '0');
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
        const response = await axios.delete(`http://localhost:9090/posts`,{
            params:{
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
  },
  mounted() {
    this.getUserPosts();
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
</style>
