<template>
    <div style="width: 100%; padding:20px">
        <el-row>
            <el-col
              v-for="post in starPosts"
              :key="post.targetId"
              :span="6"
              
            >
              <el-card shadow="hover" class="m-1">

                <div style="display: flex;justify-content: space-between;">
                    <span>{{post.title}}</span>
                    <span><el-button  class="button" @click="$router.push('/post/'+post.targetId)"
                        ><el-icon :size="larger"><Right /></el-icon></el-button></span>
                </div>
              </el-card>
            </el-col>
          </el-row>
    </div>
</template>

<script>
import axios from 'axios';
import { useStore } from "vuex";
export default{
    data() {
        return {
            userId: useStore().state.userId,
            starPosts:[],
        }
    },
    methods: {
        async getStarPosts(){
            try {
                // Replace the URL with your API endpoint to fetch posts
                const response = await axios.get("http://localhost:9090/users/stars", {
                    params: {
                        userId: this.userId, // assuming userId is available in `this`
                    },
                });

                if (response.data.code === 200) {
                    this.starPosts = response.data.data.filter(post => post.starType === 1);
                    this.starPosts.forEach(post => {
                        post.type = "帖子";
                    });
                } else {
                alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch user posts:", error);
            }
        },
        
    },
    mounted() {
        this.getStarPosts()
    },

}
</script>