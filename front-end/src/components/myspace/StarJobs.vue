<template>
    <div style="width: 100%; padding:20px">
        <el-row>
            <el-col
              v-for="job in starJobs"
              :key="job.targetId"
              :span="6"
              
            >
              <el-card shadow="hover" class="m-1">

                <div style="display: flex;justify-content: space-between;">
                    <span>{{job.title}}</span>
                    <span><el-button text class="button" @click="$router.push('/job/'+job.targetId)"
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
            starJobs:[],
        }
    },
    methods: {
        async getStarJobs(){
            try {
                // Replace the URL with your API endpoint to fetch Jobs
                const response = await axios.get("http://localhost:9090/users/stars", {
                    params: {
                        userId: this.userId, // assuming userId is available in `this`
                    },
                });

                if (response.data.code === 200) {
                    this.starJobs = response.data.data.filter(job => job.starType === 0);
                    this.starJobs.forEach(job => {
                        job.type = "岗位";
                    });
                } else {
                alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch user Jobs:", error);
            }
        },
        
    },
    mounted() {
        this.getStarJobs()
    },

}
</script>